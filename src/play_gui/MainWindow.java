package play_gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import engine.GameGrid;
import engine.NumberSquare;
import engine.SquareBase;
import gui.JGameSquare;
import play_gui.listener.GameGridCellListener;
import play_gui.listener.NewGameBtnListener;


public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JToolBar toolbar; //the Toolbar on the top of the window
	private GameGrid gameGrid; //a GameGrid object
	private JPanel gameGridPanel; //panel that houlds the GameGrid
	private JLabel backgroundImage; //Background image which is only displayed when no grid is loaded
	private JGameSquare activeCell; //the currently active(clicked) cell
	
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
			
			for(int i = 0 ; i < gameGrid.getSquares().size(); i++) {
				SquareBase s = gameGrid.getSquares().get(i);
				
				JGameSquare pTmp = new JGameSquare();
				pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
				pTmp.setPosition(i);
				pTmp.setPosx(s.getPositionX());
				pTmp.setPosy(s.getPositionY());
				pTmp.addMouseListener(new GameGridCellListener(pTmp));
				pTmp.setBackground(Color.WHITE);
				// drawing needs to be done afterwards. We use grapics of the JPanel and getGraphics will return null unless there is anything to be shown (visible)
				if(s.getClass().equals(new NumberSquare().getClass()))
					pTmp.getTextLabel().setText(s.getPrintableValue()); // For numbers are not drawn, we are able to set them here...
				
				this.gameGridPanel.add(pTmp); // add the panel
			}
			
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
		this.activeCell.setBackground(Color.GRAY);
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
	
	public void clearHover(){
		Component[] components = this.gameGridPanel.getComponents();
		for(int i=0;i < components.length; i++){
			if(this.hasActiveCell() && this.activeCell.getPosition() != i)
				components[i].setBackground(Color.WHITE);
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
			this.toolbar = new JToolBar();
			this.toolbar.setFloatable(false);
			this.toolbar.add(newGameBtn);
		}
		return this.toolbar;
	}
}