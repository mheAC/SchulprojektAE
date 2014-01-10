package play_gui;

import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.*;

import engine.*;
import gui.JGameSquare;
import play_gui.listener.*;


public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar; //the Toolbar on the top of the window
	private GameGrid gameGrid; //a GameGrid object
	private JPanel gameGridPanel; //panel that houlds the GameGrid
	private JLabel backgroundImage; //Background image which is only displayed when no grid is loaded
	private JGameSquare activeCell; //the currently active(clicked) cell
	private boolean saveBtnVisible = false; //indicates if the save butten is visible
	
	//constructor renders a window with no game grid
	public MainWindow(){
		super("Lichtstrahlen");
		
		//initialize panel
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(7, 1);
		panel.setLayout(layout);
		
		//initialize frame
		this.getContentPane().add(this.getToolbar(), BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,500);
		if(this.loadBackgroundImage("assets"+System.getProperty("file.separator")+"lamp.png")){
			this.getContentPane().add(this.backgroundImage);
		}
		
		//Enable file drop
		DropTarget dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
		try {
			dropTarget.addDropTargetListener(new FileDropListener(this));
		} catch (TooManyListenersException e) {
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	//cleares the game grid TODO: remove grid panel
	public void clearGameGrid(){
		this.gameGrid = null;
		this.gameGridPanel.setVisible(false);
		this.gameGridPanel = null;
		this.repaint();
	}
	
	//sets the game grid and renders it
	public boolean setGameGrid(GameGrid gameGrid){
		if(this.gameGrid == null){
			this.gameGrid = gameGrid;
			
			GridLayout gridLayout = new GridLayout(this.gameGrid.getGridSize().height,this.gameGrid.getGridSize().width);
			try{
				this.gameGridPanel.setLayout(gridLayout);
			}catch(NullPointerException e){
				this.gameGridPanel = new JPanel();
				this.gameGridPanel.setLayout(gridLayout);
			}
			
			this.repaintGameGrid();
			
			//Show save button
			this.showSaveButton();
			
			// Add the panel to the main frame
			this.add(this.gameGridPanel);
			this.backgroundImage.setVisible(false);
			this.repaint();
			return true;
		}
		else{
			return false;
		}
	}
	
	public void repaintGameGrid(){
		if(this.gameGrid != null){
			this.gameGridPanel.removeAll();
			for(SquareBase s: this.gameGrid.getSquares()) {
				JGameSquare pTmp = new JGameSquare(s);
				pTmp.addMouseListener(new GameGridCellListener(pTmp));
				pTmp.setBackground(Color.WHITE);
				pTmp.setRepresentingSquare(s);
				if(s.getClass() == NumberSquare.class)
					pTmp.getTextLabel().setText(s.getPrintableValue());
					if(this.hasActiveCell() && this.getActiveCell().getRepresentedSquare() == pTmp.getRepresentedSquare()){
						this.setActiveCell(pTmp);
					}
				else if(s.getClass() == RaySquare.class){
					//TODO: outsource this!
					String imageType = "";
					if(((RaySquare) s).getDirection() == Direction.VERTICAL){
						imageType = "horizontal-line.png";
					}
					if(((RaySquare) s).getDirection() == Direction.HORIZONTAL){
						imageType = "vertical-line.png";
					}
					if(((RaySquare) s).isLastInRay(this.gameGrid)){
						imageType = ((RaySquare) s).getPositionToLightsource()+"-break.png";
					}
			        File file = new File("assets"+System.getProperty("file.separator")+imageType);
			        BufferedImage image;
					try {
						image = ImageIO.read(file);
						pTmp.add(new JLabel(new ImageIcon(image)),0);
						pTmp.setLayout(null);
						pTmp.getComponent(0).setBounds(0, 0, 30, 30);
					} catch (IOException e) {
					}
				}
					
				
				this.gameGridPanel.add(pTmp); // add the panel
			}
			//this.gameGridPanel.repaint();
			
			//TODO make this nicer :)
			this.setResizable(true);
			this.setSize(500,501);
			this.setSize(500,500);
			this.setResizable(false);
		}
	}

	//shows a message in the console
	public void showAlert(String message) {
		JOptionPane.showMessageDialog(this,message,"",JOptionPane.PLAIN_MESSAGE);
	}
	
	//shows a message in the console which can be answered with y/n returns true or false
	public boolean showConfirm(String message){
		if(JOptionPane.showConfirmDialog(this,message,"",JOptionPane.YES_NO_OPTION)==1)
			return false;
		else
			return true;
	}
	
	//removes backgroundcolor from currently active cell and set a new one
	public void setActiveCell(JGameSquare cell){
		if(this.activeCell != null){
			this.activeCell.setBackground(Color.WHITE);
		}
		this.activeCell = cell;
		this.activeCell.setBackground(Color.LIGHT_GRAY);
	}
	
	public void releaseActiveCell(){
		if(this.activeCell != null){
			this.activeCell.setBackground(Color.WHITE);
			this.activeCell = null;
		}
	}
	

	//loades the background image and returns true if it could be loaded, otherwise it returns false
	private boolean loadBackgroundImage(String path){
        File file = new File(path);
        BufferedImage image;
		try {
			image = ImageIO.read(file);
			this.backgroundImage = new JLabel(new ImageIcon(image));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	//doesent work :( TODO fix it :)
	public void setBackgroundHover(boolean hover){
		if(hover){
			this.getContentPane().remove(this.backgroundImage);
			if(this.loadBackgroundImage("assets"+System.getProperty("file.separator")+"lamp-hover.png")){
				this.getContentPane().add(this.backgroundImage);
			}
		}else{
			this.getContentPane().remove(this.backgroundImage);
			if(this.loadBackgroundImage("assets"+System.getProperty("file.separator")+"lamp.png")){
				this.getContentPane().add(this.backgroundImage);
			}
		}
		this.repaint();
	}
	
	
	public JGameSquare getCellByPosition(int x, int y){
		Component[] cells = this.gameGridPanel.getComponents();
		JGameSquare cell = null;
		for(int i=0;i < cells.length; i++){
			try{
				JGameSquare tempCell = (JGameSquare) cells[i];
				if(tempCell.getPosx() == x && tempCell.getPosy() == y)
					cell = tempCell;
			}catch(ClassCastException e){
				System.out.println(e);
			}

		}
		return cell;
	}
	
	public ArrayList<JGameSquare> getUntypedCellsToActive(JGameSquare cell){
		ArrayList<JGameSquare> cells = new ArrayList<JGameSquare>();
		if(this.hasActiveCell()){
			ArrayList<SquareBase> squares = this.gameGrid.getEnlightWay(((NumberSquare) this.activeCell.getRepresentedSquare()), cell.getRepresentedSquare());
			for(SquareBase square : squares) {
            	cells.add(this.getCellByPosition(square.getPositionX(), square.getPositionY()));
            }
		}
		return cells;
	}
	
	public void clearHover(){
		Component[] components = this.gameGridPanel.getComponents();
		for(int i=0;i < components.length; i++){
			if(this.hasActiveCell() && this.activeCell != components[i]){
				components[i].setBackground(Color.WHITE);
			}
		}
	}
	
	//getterst/setters
	
	//returns true if an active cell is set
	public boolean hasActiveCell(){
		if(this.activeCell != null){
			return true;
		}else{
			return false;
		}
	}
	
	//returns the currently activated cell
	public JGameSquare getActiveCell(){
		return this.activeCell;
	}
	
	//returns the currently loaded grid (could be null if no grid is set)
	public GameGrid getGameGrid(){
		return this.gameGrid;
	}
	
	//returns the toolbar and creates a new one when no toolbar is set
	public JToolBar getToolbar(){
		if(toolbar == null){
			JButton newGameBtn = new JButton("Neues Spiel");
			newGameBtn.addActionListener(new NewGameBtnListener());
			JButton backBtn = new JButton("Rückgäng");
			backBtn.addActionListener(new BackBtnListener());
			this.toolbar = new JToolBar();
			this.toolbar.setFloatable(false);
			this.toolbar.add(newGameBtn);
			//this.toolbar.add(backBtn); TODO fix loghanlder
		}
		return this.toolbar;
	}
	
	public void showSaveButton(){
		if(!this.saveBtnVisible){
			JButton saveGameBtn = new JButton("Spiechern");
			saveGameBtn.addActionListener(new SaveGameBtnListener());
			this.getToolbar().add(saveGameBtn);
			this.saveBtnVisible = true;
		}
	}
	
}