package start_gui;

import java.io.IOException;
import java.util.Scanner;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;

public class Main {
	static StartWindow neu = new StartWindow();
	int rows, cols;

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

	public Main() throws IOException {
		//Backup was created. Backup => Lichtstrahlen/src/start_gui/Main.java.backup.txt
	}
	
	public static void main(String[] args) {
		try {
			//new Main();
			neu.show();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
