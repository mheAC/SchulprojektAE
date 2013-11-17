package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import engine.Direction;
import engine.GameGrid;
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
		GridLayout lo = new GridLayout(cols,rows);
		
		mainPanel.setLayout(lo);
		
		for(int i = 0 ; i < data.getSquares().size() ; i++) {
			SquareBase s = data.getSquares().get(i);
			
			JGameSquare pTmp = new JGameSquare();
			pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
			pTmp.setPosition(i);
			
			// ALGO TEST
			//if(data.getColidingSquares().get(s) == 1)
				//pTmp.setBorder(BorderFactory.createLineBorder(Color.RED));
			// END ALGO TEST
			
			// draw if needed
			//if(s.getClass().equals(new RaySquare().getClass()))
				//pTmp.drawLine( ((RaySquare)s).getDirection() ); // crash! grapics for the jpanel seems to be uninited
			//else
				//pTmp.add(new JLabel(s.getPrintableValue())); // add a temporary JLabel to the panel
				pTmp.getTextLabel().setText(s.getPrintableValue());
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

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

}
