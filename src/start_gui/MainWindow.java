package start_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.swing.*;
import javax.swing.table.TableModel;

import engine.GameGrid;
import engine.NumberSquare;
import engine.SquareBase;

public class MainWindow {
	int rows, cols;
	private JTable myTable;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private Dimension dim;

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
		myTable = new JTable();
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		dim = new Dimension();
	}
	
	public void buildWindow(){
		
		dim.setSize(cols*20, rows*20);
		BorderLayout lo = new BorderLayout();
		mainPanel.setSize(dim);
		mainPanel.add(myTable, BorderLayout.CENTER);
		mainFrame.setMinimumSize(dim);
		mainPanel.setLayout(lo);
		mainFrame.add(mainPanel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	public void execute(){
		if(cols != 0 && rows != 0){
			GameGrid grid = new GameGrid(cols, rows);
			grid.generateSquares(); // Only call once if you dont want the generated squares to be overwritten
			int i=0;
			for(SquareBase s : grid.getSquares()) {
				System.out.print(" | ");
				if(s.getClass().equals(new NumberSquare().getClass())){ // if we got a Number Square
					System.out.print(((NumberSquare)s).getNumber());
				}
				else {
					/*if(((RaySquare)s).getDirection().equals(Direction.HORIZONTAL)) // draw some ascii for both the directions
						System.out.print('-');
					else
						System.out.print('/');*/
					System.out.print(' ');
				}
				if(++i%rows == 0) {
					System.out.println("\n==========================================");
				}
			}
			buildWindow();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.setVisible(true);
		}else
			System.out.println("Cols / Rows are not set!!");
	}
}
