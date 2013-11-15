package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.StorageHandler;

public class TestConsolePrint {

	public TestConsolePrint() throws IOException {
	}
	
	public void test_grid_render() {
		Scanner is = new Scanner(System.in);
		
		//System.out.println("RUDIMENTÄRE TEST AUSGABE / GENERIERUNG");
		//System.out.println("Wie viele Zeilen? (Max 15)");
		//int rows = is.nextInt();
		//System.out.println("Wie viele Spalten? (Max 15)");
		//int cols = is.nextInt();
		
		int rows = 9;
		int cols = 7;
		
		GameGrid grid = new GameGrid(cols, rows);
		grid.generateSquares();
		grid.asignSquareCoordinates();
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
			if(++i%cols == 0) {
				System.out.print("\n");
				for(i=0; i < cols; i++) System.out.print("====");
				System.out.print("\n");
			}
		}
		
		/*
		 *  Test the canBeEnlighted Method
		 */
		int g=1;
		NumberSquare ns = (NumberSquare)grid.getSquares().get(31); // first number square of our dummy data
		for(SquareBase s : grid.getSquares()) {
			if(s.getClass().equals(new RaySquare().getClass())) {
				RaySquare rs = (RaySquare)s;
				System.out.print(Boolean.toString(ns.canEnlight(rs)).charAt(0) + " | ");
			}
			else
				System.out.print("# | ");
			
			if(g++ % cols == 0)
				System.out.print("\n");
		}
		
		//System.out.println(ns.canEnlight(grid.getSquares().get(16)));
	}
	
	public void test_storeage() throws Exception {
		// Test Storage save
		GameGrid foo = new GameGrid(4,4);
		foo.generateSquares();
		foo.asignSquareCoordinates();
		//System.out.println("Original: " + foo.getSquares().size());
		// PRINT
		for(SquareBase sq : foo.getSquares()) {
			if(sq.getClass().equals(new RaySquare().getClass())) {
				RaySquare rs = (RaySquare)sq;
				System.out.println(rs.getDirection());
			}
			else {
				NumberSquare ns = (NumberSquare)sq;
				System.out.println(ns.getNumber());
			}
		}
		
		//String file = "C:\\Users\\serjoscha-87\\Desktop\\test.txt";
		String file = "test1.ysams";
		
		StorageHandler s = new StorageHandler();
		
		// persist
		s.persist(foo, file);
		
		System.out.println("--------------------------");
		
		/*
		 * 
		 * 
		 * LOAD DATA FROM FILE AGAIN
		 * 
		 * 
		 */
		
		GameGrid bar = s.load(file);
		//System.out.println("Restored: " + bar.getGridSize().toString());
		
		// PRINT
		for(SquareBase sq : bar.getSquares()) {
			if(sq.getClass().equals(new RaySquare().getClass())) {
				RaySquare rs = (RaySquare)sq;
				System.out.println(rs.getDirection());
			}
			else {
				NumberSquare ns = (NumberSquare)sq;
				System.out.println(ns.getNumber());
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			TestConsolePrint o = new TestConsolePrint();
			
			/**
			 * CALL DESIRED TEST METHOD
			 */
			o.test_storeage();
			
		} catch (Exception e) { e.printStackTrace(); }
	}

}
