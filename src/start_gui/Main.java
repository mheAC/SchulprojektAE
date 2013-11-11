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
	
	public static void main(String[] args) {
		try {
			//new Main();
			neu.show();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
