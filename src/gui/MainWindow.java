package gui;

import java.awt.*;

import javax.swing.*;

import engine.GameGrid;
import engine.LogHandler;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;


public class MainWindow {

	int rows, cols;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private LogHandler lh;
	
	private JMenuItem saveBtn,
						solveGridBtn,
						checkSolveableBtn,addWidthBtn,addHeightBtn,removeHeightBtn,removeWidthBtn;
	
	JMenuBar mainMenu;
	JMenu submenuMenuContainer, submenuFile, submenuRiddle, submenuRiddleSize;
	
	private GameGrid data;

	/**
	 * Default constructor
	 */
	public MainWindow() {
		this.lh = new LogHandler();

		mainFrame = new JFrame("Lichststrahlen Spiel");
		mainPanel = new JPanel();
		
		// Menu stuff
		mainMenu = new JMenuBar(); // container for categorizing sub menus
		
		submenuMenuContainer = new JMenu("Menü"); // main menu categorizer which is directly shown on the menu bar
		
		submenuFile = new JMenu("Datei"); // sub menus within the main categorizer
		submenuRiddle = new JMenu("Rätsel");
		submenuRiddleSize = new JMenu("Größe");
		
		// Buttons for the menus
		saveBtn = new JMenuItem("Entwurf in Datei speichern"); 
		solveGridBtn = new JMenuItem("Das Rätsel automatisch lösen!");
		checkSolveableBtn = new JMenuItem("ist das Räsel lösbar?");
		
		addHeightBtn = new JMenuItem("Zeile hinzufügen");
		removeHeightBtn = new JMenuItem("Zeile entfernen");
		
		addWidthBtn = new JMenuItem("Spalte hinzufügen");
		removeWidthBtn = new JMenuItem("Spalte entfernen");
	}
	
	public LogHandler getLogHandler(){
		return this.lh;
	}
	
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
				gs.drawLine(rs.getDirection(), this.lh); // for rays of ray squares
			}
		}
		mainPanel.repaint();
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public GameGrid getGameGridData() {
		return data;
	}

	public void setGameGridData(GameGrid dt) {
		this.data = dt;
	}

	public int getRows() {
		return rows;
	}
	
	public JFrame getJFrame(){
		return mainFrame;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}

	public JMenuItem getSaveBtn() {
		return saveBtn;
	}
	
	public int getPosX(){
		return 0;
	}
	
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
	
	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public JMenuItem getAddWidthBtn() {
		return addWidthBtn;
	}

	public void setAddWidthBtn(JMenuItem addWidthBtn) {
		this.addWidthBtn = addWidthBtn;
	}

	public JMenuItem getAddHeightBtn() {
		return addHeightBtn;
	}

	public void setAddHeightBtn(JMenuItem addHeightBtn) {
		this.addHeightBtn = addHeightBtn;
	}

	public JMenuItem getRemoveHeightBtn() {
		return removeHeightBtn;
	}

	public void setRemoveHeightBtn(JMenuItem removeHeightBtn) {
		this.removeHeightBtn = removeHeightBtn;
	}

	public JMenuItem getRemoveWidthBtn() {
		return removeWidthBtn;
	}

	public void setRemoveWidthBtn(JMenuItem removeWidthBtn) {
		this.removeWidthBtn = removeWidthBtn;
	}
}
