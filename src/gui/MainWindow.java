package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.UntypedSquare;


public class MainWindow {

	int rows, cols;
	private JFrame mainFrame;
	private JPanel mainPanel;
	
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
		mainFrame = new JFrame("Lichststrahlen Spiel");
		mainPanel = new JPanel();
		
		// Menu stuff
		mainMenu = new JMenuBar(); // container for categorizing sub menus
		
		submenuMenuContainer = new JMenu("Men�"); // main menu categorizer which is directly shown on the menu bar
		
		submenuFile = new JMenu("Datei"); // sub menus within the main categorizer
		submenuRiddle = new JMenu("R�tsel");
		submenuRiddleSize = new JMenu("Gr��e");
		
		// Buttons for the menus
		saveBtn = new JMenuItem("Entwurf in Datei speichern"); 
		solveGridBtn = new JMenuItem("Das R�tsel automatisch l�sen!");
		checkSolveableBtn = new JMenuItem("ist das R�sel l�sbar?");
		
		addHeightBtn = new JMenuItem("Zeile hinzuf�gen");
		removeHeightBtn = new JMenuItem("Zeile entfernen");
		
		addWidthBtn = new JMenuItem("Spalte hinzuf�gen");
		removeWidthBtn = new JMenuItem("Spalte entfernen");
	}
	
	public void buildWindow(){
		
		
		buildPanel();
		
		// Add the panel to the main frame
		mainFrame.add(mainPanel);

		
		/*
		 *  Assign every layer of the menu to its position
		 */
		// add buttons
		submenuFile.add(saveBtn);
		
		// add actions
		addHeightBtn.addActionListener(new AddHeightBtnActionListener());
		removeHeightBtn.addActionListener(new RemoveHeightBtnActionListener());
		addWidthBtn.addActionListener(new AddWidthBtnActionListener());
		removeWidthBtn.addActionListener(new RemoveWidthBtnActionListener());
		
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
	}
	
	private void buildPanel() {
		mainPanel = new JPanel();
		GridLayout lo = new GridLayout(rows,cols);
		
		mainPanel.setLayout(lo);
		
		for(int i = 0 ; i < data.getSquares().size() ; i++) {
			SquareBase s = data.getSquares().get(i);
			
			JGameSquare pTmp = new JGameSquare();
			pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
			pTmp.setPosition(i);
			pTmp.setPosx(s.getPositionX());
			pTmp.setPosy(s.getPositionY());
			
			// ALGO TEST
			//if(data.getColidingSquares().get(s) == 1) // render a red boarder to any GameSquare that has only one ray hitting it
				//pTmp.setBorder(BorderFactory.createLineBorder(Color.RED));
			// END ALGO TEST
			
			// drawing needs to be done afterwards. We use grapics of the JPanel and getGraphics will return null unless there is anything to be shown (visible)
			if(s.getClass().equals(new NumberSquare().getClass()))
				pTmp.getTextLabel().setText(s.getPrintableValue()); // For numbers are not drawn, we are able to set them here...
			
			mainPanel.add(pTmp); // add the panel
		}
		
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
	
	private class AddHeightBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO: add height btn action
			List<SquareBase> squares = data.getSquares();
			Dimension dim = data.getGridSize();
			for (int ii = 0; ii < (int)dim.getWidth();ii++) {
				squares.add(new UntypedSquare());
			}
			data.setGridSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()+1));
			data.asignSquareCoordinates();
			rows = (int)data.getGridSize().getHeight();
			cols = (int)data.getGridSize().getWidth();
			mainFrame.remove(mainPanel);
			buildPanel();
			mainFrame.add(mainPanel);
			mainFrame.repaint();
			mainFrame.pack();
			mainFrame.setVisible(true);
		}
	}
	
	private class RemoveHeightBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
            int response = JOptionPane.showConfirmDialog(mainFrame, "Beim Entfernen einer Zeile\n"
	                   +"gehen eventuell get�tigte Eingaben\n"
	                   +"verloren. Fortfahren?", "Warnung",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
            	// TODO: actually remove something
            }
		}
	}
	
	private class AddWidthBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			List<SquareBase> squares = data.getSquares();
			Dimension dim = data.getGridSize();
			for (int ii = squares.size();ii>0;ii=(ii-(int)dim.getWidth())) {
				squares.add(ii,new UntypedSquare());
			}
			data.setGridSize(new Dimension((int)dim.getWidth()+1, (int)dim.getHeight()));
			data.asignSquareCoordinates();
			rows = (int)data.getGridSize().getHeight();
			cols = (int)data.getGridSize().getWidth();
			mainFrame.remove(mainPanel);
			buildPanel();
			mainFrame.add(mainPanel);
			mainFrame.repaint();
			mainFrame.pack();
			mainFrame.setVisible(true);
		}
	}
	
	private class RemoveWidthBtnActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int response = JOptionPane.showConfirmDialog(mainFrame, "Beim Entfernen einer Spalte\n"
	                   +"gehen eventuell get�tigte Eingaben\n"
	                   +"verloren. Fortfahren?", "Warnung",
                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        if (response == JOptionPane.YES_OPTION) {
	        	// TODO: actually remove something
	        }
		}
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
}
