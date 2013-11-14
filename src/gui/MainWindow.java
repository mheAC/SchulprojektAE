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
	private JToolBar mainToolBar;
	private JButton saveBtn,
					solveGridBtn,
					checkSolveableBtn;
	private GameGrid data;

	/**
	 * Default constructor
	 */
	public MainWindow() {
		mainFrame = new JFrame("Lichststrahlen Spiel");
		mainPanel = new JPanel();
		mainToolBar = new JToolBar();
		saveBtn = new JButton("Entwurf speichern");
		solveGridBtn = new JButton("Rätsel lösen");
		checkSolveableBtn = new JButton("Rätsel lösbar?");
	}
	
	public void buildWindow(){
		GridLayout lo = new GridLayout(cols,rows);
		
		mainPanel.setLayout(lo);
		
		//for(SquareBase s : data.getSquares()) {
		for(int i = 0 ; i < data.getSquares().size() ; i++) {
			SquareBase s = data.getSquares().get(i);
			
			JGameSquare pTmp = new JGameSquare();
			pTmp.setRepresentingSquare(s); // important: store the SquareObject within this bean
			pTmp.setPosition(i);
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
		
		// add the beans
		mainToolBar.add(saveBtn);
		mainToolBar.add(solveGridBtn);
		mainToolBar.add(checkSolveableBtn);
		mainToolBar.setFloatable(false);
		mainFrame.add(mainToolBar, BorderLayout.NORTH);
		
		// Rest stuff for displaying
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

	public JButton getSaveBtn() {
		return saveBtn;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

}
