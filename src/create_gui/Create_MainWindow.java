package create_gui;


	import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.*;

import play_gui.listener.GameGridCellListener;

import create_gui.listener.Create_AddColumnBtnListener;
import create_gui.listener.Create_AddRowBtnListener;
import create_gui.listener.Create_FileDropListener;
import create_gui.listener.Create_RemoveColumnBtnListener;
import create_gui.listener.Create_RemoveRowBtnListener;
import engine.*;
import gui.JGameSquare;
import create_gui.listener.*;
import loghandler2.*;


	public class Create_MainWindow extends JFrame{
		
		private static final long serialVersionUID = 1L;
		//private JToolBar toolbar; //the Toolbar on the top of the window
		private JMenuBar menubar;
		
		private GameGrid gameGrid; //a GameGrid object
		private JPanel gameGridPanel; //panel that houlds the GameGrid
		private JLabel backgroundImage; //Background image which is only displayed when no grid is loaded
		private JGameSquare activeCell; //the currently active(clicked) cell
		private Loghandler loghandler; //the loghandler for stepback functions
		
		//constructor renders a window with no game grid
		public Create_MainWindow(){
			super("Lichtstrahlen");
			
			//initialize panel
			JPanel panel = new JPanel();
			//GridLayout layout = new GridLayout(7, 1);
			GridLayout layout = new GridLayout();
			panel.setLayout(layout);
			
			//initialize frame
			this.getContentPane().add(this.getToolbar(), BorderLayout.NORTH);
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//this.setSize(500,500);
//			if(Create_StartWindow.getWidth()>0 || Create_StartWindow.getHeight() > 0 ){
//				int width = Create_StartWindow.getWidth(), height = Create_StartWindow.getHeight();
//				this.setSize(width*30, height*30);
//			}
//			else
			this.setSize(500,500);
			//this.pack();
			if(this.loadBackgroundImage("assets"+System.getProperty("file.separator")+"lamp.png")){
				this.getContentPane().add(this.backgroundImage);
			}
			
			//Enable file drop
			DropTarget dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, null);
			try {
				dropTarget.addDropTargetListener(new Create_FileDropListener(this));
			} catch (TooManyListenersException e) {
				e.printStackTrace();
			}
			
			this.setResizable(true);
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
		
		public boolean setGameGrid(GameGrid gameGrid, boolean resetLoghandler) throws FileNotFoundException, IOException, ClassNotFoundException{
			if(resetLoghandler){
				this.loghandler = new Loghandler(gameGrid);
			}
			
			return this.setGameGrid(gameGrid);
		}
		
		//sets the game grid and renders it
		public boolean setGameGrid(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
			if(this.loghandler == null){
				this.loghandler = new Loghandler(gameGrid);
			}
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
				for(Component component : this.menubar.getComponents()){
					component.setVisible(true);
				}
				
				// Add the panel to the main frame
				this.gameGridPanel.validate();
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
					pTmp.addMouseListener(new Create_GameGridCellListener(pTmp));
					pTmp.setBackground(Color.WHITE);
					pTmp.setRepresentingSquare(s);
					if(s.getClass() == NumberSquare.class)
						pTmp.getTextLabel().setText(""+(((NumberSquare ) s).getOriginalNumber()));
						if(this.hasActiveCell() && this.getActiveCell().getRepresentedSquare() == pTmp.getRepresentedSquare()){
							this.setActiveCell(pTmp);
						}
					else if(s.getClass() == RaySquare.class){
						
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
				
				
				this.setResizable(true);
				//this.setSize(500,501);
				//this.setSize(500,500);
				this.gameGridPanel.validate();
				this.validate();
				this.pack();
//				if(Create_StartWindow.getWidth()>0 || Create_StartWindow.getHeight() > 0 ){
//					int width = Create_StartWindow.getWidth(), height = Create_StartWindow.getHeight();
//					this.setSize(width*30, height*30);
//				}
//				else
//					this.setSize(500,500);
				this.setResizable(false);
				//this.gameGridPanel.repaint();
			}
		}
		public void removeall(){
			this.gameGridPanel.removeAll();
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
				ArrayList<SquareBase> squares = this.gameGrid.getEnlightWayUntype(((NumberSquare) this.activeCell.getRepresentedSquare()), cell.getRepresentedSquare());
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
		public JMenuBar getToolbar(){
			if(menubar == null){
				JButton newGameBtn = new JButton("Neues Spiel");
				newGameBtn.addActionListener(new Create_NewGameBtnListener());
				JMenu extras = new JMenu("Extras");
				
				
				
				JButton backBtn = new JButton("Rückgängig");
				backBtn.addActionListener(new Create_BackBtnListener());
				
				JButton saveGameBtn = new JButton("Speichern");
				saveGameBtn.addActionListener(new Create_SaveGameBtnListener());
                
				JButton addHeightBtn = new JButton("Zeile +1");
				addHeightBtn.addActionListener(new Create_AddRowBtnListener());
				
				JButton removeHeightBtn = new JButton("Zeile -1");
				removeHeightBtn.addActionListener(new Create_RemoveRowBtnListener());
                
				JButton addWidthBtn = new JButton("Spalte +1");
				addWidthBtn.addActionListener(new Create_AddColumnBtnListener());
				
				JButton removeWidthBtn = new JButton("Spalte -1");
				removeWidthBtn.addActionListener(new Create_RemoveColumnBtnListener());

				
				//extras.add(backBtn);
				extras.add(saveGameBtn);
				extras.add(addHeightBtn);
				extras.add(removeHeightBtn);
				extras.add(addWidthBtn);
				extras.add(removeWidthBtn);
				
				extras.setEnabled(true);
				extras.setVisible(true);
				extras.validate();

				this.menubar = new JMenuBar();
				//this.menubar.setFloatable(false);
				this.menubar.add(newGameBtn);
				this.menubar.add(extras);
				this.menubar.validate();
//				this.menubar.add(backBtn);
//				this.menubar.add(saveGameBtn);
//				
//				this.menubar.add(addHeightBtn);
//				this.menubar.add(removeHeightBtn);
//				this.menubar.add(addWidthBtn);
//				this.menubar.add(removeWidthBtn);
				
				//hide unusable buttons before load
				backBtn.setVisible(true);
				saveGameBtn.setVisible(true);
				addHeightBtn.setVisible(true);
				addWidthBtn.setVisible(true);
				removeHeightBtn.setVisible(true);
				removeWidthBtn.setVisible(true);
			}
			return this.menubar;
		}
		
		public Loghandler getLoghandler(){
			return this.loghandler;
		}
	}

