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
import play_gui.listener.FileDropListener;
import play_gui.listener.GameGridCellListener;
import play_gui.listener.NewGameBtnListener;


// TODO: Javadoc kontrollieren
/**
 * The Class MainWindow.
 */
public class MainWindow extends JFrame{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The toolbar. */
	private JToolBar toolbar; //the Toolbar on the top of the window
	
	/** The game grid. */
	private GameGrid gameGrid; //a GameGrid object
	
	/** The game grid panel. */
	private JPanel gameGridPanel; //panel that houlds the GameGrid
	
	/** The background image. */
	private JLabel backgroundImage; //Background image which is only displayed when no grid is loaded
	
	/** The active cell. */
	private JGameSquare activeCell; //the currently active(clicked) cell
	
	/** The log handler. */
	private LogHandler logHandler; // a log handler instale
	
	//constructor renders a window with no game grid
	/**
	 * Instantiates a new main window.
	 */
	public MainWindow(){
		super("Lichtstrahlen");
		
		//initialize Loghandler
		this.logHandler = new LogHandler();
		
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
	/**
	 * Clear game grid.
	 */
	public void clearGameGrid(){
		this.gameGrid = null;
		this.gameGridPanel.setVisible(false);
		this.gameGridPanel = null;
		this.repaint();
	}
	
	//sets the game grid and renders it
	/**
	 * Sets the game grid.
	 *
	 * @param gameGrid the game grid
	 * @return true, if successful
	 */
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
	
	/**
	 * Repaint game grid.
	 */
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
				else if(s.getClass() == RaySquare.class){
					//TODO: autsource this!
					String imageType = "horizontal-line.png";
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
	/**
	 * Show alert.
	 *
	 * @param message the message
	 */
	public void showAlert(String message) {
		JOptionPane.showMessageDialog(this,message,"",JOptionPane.PLAIN_MESSAGE);
	}
	
	//shows a message in the console which can be answered with y/n returns true or false
	/**
	 * Show confirm.
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	public boolean showConfirm(String message){
		if(JOptionPane.showConfirmDialog(this,message,"",JOptionPane.YES_NO_OPTION)==1)
			return false;
		else
			return true;
	}
	
	//removes backgroundcolor from currently active cell and set a new one
	/**
	 * Sets the active cell.
	 *
	 * @param cell the new active cell
	 */
	public void setActiveCell(JGameSquare cell){
		if(this.activeCell != null){
			this.activeCell.setBackground(Color.WHITE);
		}
		this.activeCell = cell;
		this.activeCell.setBackground(Color.GRAY);
	}
	
	/**
	 * Release active cell.
	 */
	public void releaseActiveCell(){
		if(this.activeCell != null){
			this.activeCell.setBackground(Color.WHITE);
			this.activeCell = null;
		}
	}
	

	//loades the background image and returns true if it could be loaded, otherwise it returns false
	/**
	 * Load background image.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
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
	/**
	 * Sets the background hover.
	 *
	 * @param hover the new background hover
	 */
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
	
	
	/**
	 * Gets the cell by position.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the cell by position
	 */
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
	
	/**
	 * Gets the untyped cells to active.
	 *
	 * @param cell the cell
	 * @return the untyped cells to active
	 */
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
	
	/**
	 * Clear hover.
	 */
	public void clearHover(){
		Component[] components = this.gameGridPanel.getComponents();
		for(int i=0;i < components.length; i++){
			if(this.hasActiveCell() && this.activeCell.getPosition() != i && !(components[i].getClass() == JGameSquare.class && ((JGameSquare) components[i]).getRepresentedSquare().isRaySquare())){
				components[i].setBackground(Color.WHITE);
			}
		}
	}
	
	//getterst/setters
	
	//returns true if an active cell is set
	/**
	 * Checks for active cell.
	 *
	 * @return true, if successful
	 */
	public boolean hasActiveCell(){
		if(this.activeCell != null){
			return true;
		}else{
			return false;
		}
	}
	
	//returns the currently activated cell
	/**
	 * Gets the active cell.
	 *
	 * @return the active cell
	 */
	public JGameSquare getActiveCell(){
		return this.activeCell;
	}
	
	//returns the currently loaded grid (could be null if no grid is set)
	/**
	 * Gets the game grid.
	 *
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return this.gameGrid;
	}
	
	//returns the toolbar and creates a new one when no toolbar is set
	/**
	 * Gets the toolbar.
	 *
	 * @return the toolbar
	 */
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
	
	//returns the loghandler
	/**
	 * Gets the log handler.
	 *
	 * @return the log handler
	 */
	public LogHandler getLogHandler(){
		return this.logHandler;
	}
}