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

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public MainWindow() {
		mainFrame = new JFrame("Lichststrahlen  Spiel");
		mainPanel = new JPanel();
	}
	
	public void buildWindow(){
		GridLayout lo = new GridLayout(rows,cols);
		
		mainPanel.setLayout(lo);
		/*for (int i = 0; i < rows; i++) { // old version, which ignored the data model / which was the problem and reason for rewriting
			  for (int j = 0; j < cols; j++) {
				  JPanel pTmp = new JPanel();
				  Border border = new BevelBorder( BevelBorder.RAISED );
				  pTmp.setBorder(border);
				  mainPanel.add(pTmp);
			  }
		}*/
		
		GameGrid data = new GameGrid(cols, rows);
		data.generateSquares();
		data.asignSquareCoordinates();
		
		Border border = new BevelBorder( BevelBorder.RAISED );
		for(SquareBase s : data.getSquares()) {
			  JPanel pTmp = new JPanel();
			  pTmp.setLayout(new FlowLayout());
			  pTmp.setBorder(border);
			  pTmp.add(new JLabel(s.getPrintableValue()));
			  mainPanel.add(pTmp);
		}
		
		mainFrame.add(mainPanel);
		//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}

}
