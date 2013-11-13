package start_gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import engine.GameGrid;
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
		GridLayout lo = new GridLayout(rows,cols);
		
		mainPanel.setLayout(lo);
		
		//3D - BorderView for our Panels
		//Border border = new BevelBorder( BevelBorder.RAISED );
		for(SquareBase s : data.getSquares()) {
			  JGameSquare pTmp = new JGameSquare();
			  pTmp.setRepresentingSquare(s);
			  pTmp.setLayout(new FlowLayout());
			  //pTmp.setBorder(border);
			  pTmp.add(new JLabel(s.getPrintableValue()));
			  mainPanel.add(pTmp);
		}
		
		mainFrame.add(mainPanel);
		mainToolBar.add(saveBtn);
		mainToolBar.add(solveGridBtn);
		mainToolBar.add(checkSolveableBtn);
		mainToolBar.setFloatable(false);
		mainFrame.add(mainToolBar, BorderLayout.NORTH);
		//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}

}
