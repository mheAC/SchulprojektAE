//This is a backup file from Main.java
//created by YC - 11.11.2013 at 13:48

package test;

import java.io.IOException;
import java.util.Scanner;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;

public class TestConsolePrint {

	public TestConsolePrint() throws IOException {
		Scanner is = new Scanner(System.in);
		
		//System.out.println("RUDIMENTÄRE TEST AUSGABE / GENERIERUNG");
		//System.out.println("Wie viele Zeilen? (Max 15)");
		//int rows = is.nextInt();
		//System.out.println("Wie viele Spalten? (Max 15)");
		//int cols = is.nextInt();
		
		int rows = 3;
		int cols = 4;
		
		GameGrid grid = new GameGrid(cols, rows);
		grid.generateSquares(); 
		int i=0;
		for(SquareBase s : grid.getSquares()) {
			System.out.print(" | ");
			if(s.getClass().equals(new NumberSquare().getClass()))
				System.out.print(((NumberSquare)s).getNumber());
			else {
				/*if(((RaySquare)s).getDirection().equals(Direction.HORIZONTAL)) // draw some ascii for both the directions
					System.out.print('-');
				else
					System.out.print('/');*/
				System.out.print(' ');
			}
			if(++i%4 == 0) {
				System.out.println("\n=================");
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new TestConsolePrint();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
