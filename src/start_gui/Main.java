package start_gui;

import java.io.IOException;
import java.util.Scanner;

import engine.GameGrid;
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
		int i=0;
		for(SquareBase s : grid.getSquares()) {
			System.out.print(" | ");
			System.out.print(s.isNumberSquare() ? s.getNumber() : " ");
			if(++i%rows == 0) {
				System.out.println("\n==========================================");
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
