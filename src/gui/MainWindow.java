package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;


public class MainWindow {
	
	int rows, cols;
	private JFrame mainFrame;
	private JPanel mainPanel;
	
	private JMenuItem saveBtn,
						solveGridBtn,
						checkSolveableBtn;
	
	JMenuBar mainMenu;
	JMenu submenuMenuContainer, submenuFile, submenuRiddle;
	
	private GameGrid data;

	/**
	 * Default constructor
	 */
	public MainWindow() {
		mainFrame = new JFrame("Lichststrahlen Spiel");
		mainPanel = new JPanel();
		
		// Menu stuff
		mainMenu = new JMenuBar(); // container for categorizing sub menus
		
		submenuMenuContainer = new JMenu("Menü"); // main menu categorizer which is directly shown on the menu bar
		
		submenuFile = new JMenu("Datei"); // sub menus within the main categorizer
		submenuRiddle = new JMenu("Rätsel");
		
		// Buttons for the menus
		saveBtn = new JMenuItem("Entwurf in Datei speichern"); 
		solveGridBtn = new JMenuItem("Das Rätsel automatisch lösen!");
		checkSolveableBtn = new JMenuItem("ist das Räsel lösbar?");
	}
	
	public void buildWindow(){
		GridLayout lo = new GridLayout(rows,cols);
		
		mainPanel.setLayout(lo);
		
		for(int i = 0 ; i < data.getSquares().size() ; i++) {
			SquareBase s = data.getSquares().get(i);
			
			JGameSquare pTmp = new JGameSquare();
			pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
			pTmp.setPosition(i);
			
			// ALGO TEST
			//if(data.getColidingSquares().get(s) == 1) // render a red boarder to any GameSquare that has only one ray hitting it
				//pTmp.setBorder(BorderFactory.createLineBorder(Color.RED));
			// END ALGO TEST
			
			// drawing needs to be done afterwards. We use grapics of the JPanel and getGraphics will return null unless there is anything to be shown (visible)
			if(s.getClass().equals(new NumberSquare().getClass()))
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
		
		submenuRiddle.add(checkSolveableBtn);
		submenuRiddle.add(solveGridBtn);
		
		// add submenus to their categorizer / parent menu
		submenuMenuContainer.add(submenuFile);
		submenuMenuContainer.add(submenuRiddle);
		
		// add the absolute parent menu / categorizer to to the JMenuBar
		mainMenu.add(submenuMenuContainer);
		
		// Add the menuBar to the frame
		mainFrame.add(mainMenu, BorderLayout.NORTH);
		
		// Rest stuff for displaying
		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		// Afterwards draw the lines to RaySquares
		for(Component c : mainPanel.getComponents()) {
			JGameSquare gs = (JGameSquare)c; // every element contained is a JGameSquare
			if(gs.getRepresentedSquare().getClass().equals(new RaySquare().getClass())) {
				RaySquare rs = (RaySquare)gs.getRepresentedSquare();
				gs.setText(rs.getPrintableValue()); // TODO as soon as drawing works here, this line can be removed
				gs.drawLine(rs.getDirection()); // for rays of ray squares
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
}
