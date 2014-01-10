package gui;

import java.awt.*;

import javax.swing.*;

import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.loghandler.receiver.LogHandler;


// TODO: Javadoc kontrollieren
/**
 * The Class MainWindow.
 */
public class MainWindow {

	/** The cols. */
	int rows, cols;
	
	/** The main frame. */
	private JFrame mainFrame;
	
	/** The main panel. */
	private JPanel mainPanel;
	
	
	/** The remove width btn. */
	private JMenuItem saveBtn,
						solveGridBtn,
						checkSolveableBtn,addWidthBtn,addHeightBtn,removeHeightBtn,removeWidthBtn;
	
	/** The main menu. */
	JMenuBar mainMenu;
	
	/** The submenu riddle size. */
	JMenu submenuMenuContainer, submenuFile, submenuRiddle, submenuRiddleSize;
	
	/** The data. */
	private GameGrid data;

	/**
	 * Default constructor.
	 */
	public MainWindow() {

		mainFrame = new JFrame("Lichststrahlen Spiel");
		mainPanel = new JPanel();
		
		// Menu stuff
		mainMenu = new JMenuBar(); // container for categorizing sub menus
		
		submenuMenuContainer = new JMenu("Men?"); // main menu categorizer which is directly shown on the menu bar
		
		submenuFile = new JMenu("Datei"); // sub menus within the main categorizer
		submenuRiddle = new JMenu("R?tsel");
		submenuRiddleSize = new JMenu("Gr??e");
		
		// Buttons for the menus
		saveBtn = new JMenuItem("Entwurf in Datei speichern"); 
		solveGridBtn = new JMenuItem("Das R?tsel automatisch l?sen!");
		checkSolveableBtn = new JMenuItem("ist das R?sel l?sbar?");
		
		addHeightBtn = new JMenuItem("Zeile hinzuf?gen");
		removeHeightBtn = new JMenuItem("Zeile entfernen");
		
		addWidthBtn = new JMenuItem("Spalte hinzuf?gen");
		removeWidthBtn = new JMenuItem("Spalte entfernen");
	}
	
	
	
	/**
	 * Builds the window.
	 */
	public void buildWindow(){
		GridLayout lo = new GridLayout(rows,cols);
		
		mainPanel.setLayout(lo);
		
		for(int i = 0 ; i < data.getGridSize().width*data.getGridSize().height; i++) {
			SquareBase s = data.getSquares().get(i);
			
			JGameSquare pTmp = new JGameSquare();
			pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
			pTmp.setPosition(i);
			pTmp.setPosx(s.getPositionX());
			pTmp.setPosy(s.getPositionY());
			
			// drawing needs to be done afterwards. We use grapics of the JPanel and getGraphics will return null unless there is anything to be shown (visible)
			if(s.getClass().equals(new NumberSquare(0,0).getClass()))
				pTmp.getTextLabel().setText(s.getPrintableValue()); // For numbers are not drawn, we are able to set them here...
			
			mainPanel.add(pTmp); // add the panel
		}
		
		// Add the panel to the main frame
		mainFrame.add(mainPanel);

		
		/*
		 *  Assign every layer of the menu to its position
		 */
		// add buttons
		submenuFile.add(saveBtn);
		
		submenuRiddleSize.add(addHeightBtn);
		submenuRiddleSize.add(removeHeightBtn);
		submenuRiddleSize.add(addWidthBtn);
		submenuRiddleSize.add(removeWidthBtn);
		
		submenuRiddle.add(checkSolveableBtn);
		submenuRiddle.add(solveGridBtn);
		
		// add submenus to their categorizer / parent menu
		submenuMenuContainer.add(submenuFile);
		submenuMenuContainer.add(submenuRiddle);
		submenuRiddle.add(submenuRiddleSize);
		
		// add the absolute parent menu / categorizer to to the JMenuBar
		mainMenu.add(submenuMenuContainer);
		
		// Add the menuBar to the frame
		mainFrame.add(mainMenu, BorderLayout.NORTH);
		
		// Rest stuff for displaying
		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		//mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		// Afterwards draw the lines to RaySquares
		for(Component c : mainPanel.getComponents()) {
			JGameSquare gs = (JGameSquare)c; // every element contained is a JGameSquare
			if(gs.getRepresentedSquare().getClass().equals(new RaySquare(0,0).getClass())) {
				RaySquare rs = (RaySquare)gs.getRepresentedSquare();
				gs.setText(rs.getPrintableValue()); // TODO as soon as drawing works here, this line can be removed
				gs.drawLine(rs.getDirection(), this.data.getLogHandler(), data ,this.data.getController()); // for rays of ray squares
			}
		}
		mainPanel.repaint();
	}
	
	/**
	 * Gets the main panel.
	 *
	 * @return the main panel
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	/**
	 * Gets the game grid data.
	 *
	 * @return the game grid data
	 */
	public GameGrid getGameGridData() {
		return data;
	}

	/**
	 * Sets the game grid data.
	 *
	 * @param dt the new game grid data
	 */
	public void setGameGridData(GameGrid dt) {
		this.data = dt;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Gets the j frame.
	 *
	 * @return the j frame
	 */
	public JFrame getJFrame(){
		return mainFrame;
	}
	
	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Gets the save btn.
	 *
	 * @return the save btn
	 */
	public JMenuItem getSaveBtn() {
		return saveBtn;
	}
	
	/**
	 * Gets the pos x.
	 *
	 * @return the pos x
	 */
	public int getPosX(){
		return 0;
	}
	
	/**
	 * Gets the j game square at.
	 *
	 * @param col the col
	 * @param row the row
	 * @return the j game square at
	 */
	public JGameSquare getJGameSquareAt(int col,int row){
		int count = 0;
		for(int i=0;i<this.getRows();i++)
			for(int j=0;j<this.getCols();j++){
				if(j==col && i==row){
					return (JGameSquare)this.getMainPanel().getComponent(count);
				}
				count++;
			}
		return null;
	}
	
	/**
	 * Gets the positiv diff from col to end.
	 *
	 * @param position the position
	 * @return the positiv diff from col to end
	 */
	public int getPositivDiffFromColToEnd(int position){
		int tmp = 0;
		int diff = 0;
		boolean ermittelt = false;
		for(int i=0;i<this.getRows();i++){
			for(int j=0;j<this.getCols();j++){
				tmp++;
				if(tmp > position){
					diff++;
					ermittelt = true;
				}
			}
			if(ermittelt){
				break;
			}
		}
		return diff;
	}
	
	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Sets the cols.
	 *
	 * @param cols the new cols
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Gets the adds the width btn.
	 *
	 * @return the release active cell
	 */
	public JMenuItem getAddWidthBtn() {
		return addWidthBtn;
	}

	/**
	 * Sets the adds the width btn.
	 *
	 * @param addWidthBtn the new adds the width btn
	 */
	public void setAddWidthBtn(JMenuItem addWidthBtn) {
		this.addWidthBtn = addWidthBtn;
	}

	/**
	 * Gets the adds the height btn.
	 *
	 * @return the cell by position
	 */
	public JMenuItem getAddHeightBtn() {
		return addHeightBtn;
	}

	/**
	 * Sets the adds the height btn.
	 *
	 * @param addHeightBtn the new ${e.g(1).rsfl()}
	 */
	public void setAddHeightBtn(JMenuItem addHeightBtn) {
		this.addHeightBtn = addHeightBtn;
	}

	/**
	 * Gets the removes the height btn.
	 *
	 * @return the ${e.g(1).rsfl()}
	 */
	public JMenuItem getRemoveHeightBtn() {
		return removeHeightBtn;
	}

	/**
	 * Sets the removes the height btn.
	 *
	 * @param removeHeightBtn the new removes the height btn
	 */
	public void setRemoveHeightBtn(JMenuItem removeHeightBtn) {
		this.removeHeightBtn = removeHeightBtn;
	}

	/**
	 * Gets the removes the width btn.
	 *
	 * @return the clear hover
	 */
	public JMenuItem getRemoveWidthBtn() {
		return removeWidthBtn;
	}

	/**
	 * Sets the removes the width btn.
	 *
	 * @param removeWidthBtn the new removes the width btn
	 */
	public void setRemoveWidthBtn(JMenuItem removeWidthBtn) {
		this.removeWidthBtn = removeWidthBtn;
	}
}
