package start_gui;

import java.io.IOException;
import java.util.Scanner;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;

public class Main {

	public Main() throws IOException {
		Scanner is = new Scanner(System.in);
		
		System.out.println("RUDIMENTÄRE TEST AUSGABE / GENERIERUNG");
		System.out.println("Wie viele Zeilen? (Max 15)");
		int rows = is.nextInt();
		System.out.println("Wie viele Spalten? (Max 15)");
		int cols = is.nextInt();
		
		if(rows > 15)
			rows = 10;
		if(cols > 15)
			cols = 10;
		
		//int cols = 10, rows = 10;
		
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
	}
	
	public static void main(String[] args) {
		try {
			//new Main();
			StartWindow neu = new StartWindow();
			neu.show();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
