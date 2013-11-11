package start_gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Scanner;

import engine.GameGrid;
import engine.NumberSquare;
import engine.SquareBase;

public class MainWindow {
	int rows, cols;
	Rectangle2D rect;

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
	}
	
	public void execute(){
		if(cols != 0 && rows != 0){
			GameGrid grid = new GameGrid(cols, rows);
			grid.generateSquares(); // Only call once if you dont want the generated squares to be overwritten
			int i=0;
			for(SquareBase s : grid.getSquares()) {
				System.out.print(" | ");
				if(s.getClass().equals(new NumberSquare().getClass())) // if we got a Number Square
					System.out.print(((NumberSquare)s).getNumber());
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
		}else
			System.out.println("Cols / Rows are not set!!");
	}
}
