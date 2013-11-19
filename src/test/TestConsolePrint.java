package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
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
		GameGrid foo = new GameGrid(4,3);
		foo.generateSquaresTEST();
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
		String file = "SaveGame/test_4x4.ysams";
		
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
	
	private void test_print_grid (GameGrid grid, int cols) {
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
	}
	
	public void test_colision () {
		GameGrid g = new GameGrid();
		g.generateSquaresTEST();
		g.asignSquareCoordinates();
		
		int cols = 4;
		
		/*for(RaySquare rs :  g.getColidingSquares()) {
			//System.out.println("x: " + rs.getPositionX() + " - y: " + rs.getPositionY());
			System.out.println(rs.hashCode());
		}*/
		
	    /*Iterator it = g.getColidingSquares().entrySet().iterator();
	    int i = 1;
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.print(pairs.getKey().hashCode() + " => " + pairs.getValue() + " | ");
	        if(i % 4 == 0)
	        	System.out.println("");
	        i++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }*/
	    
		// print the data
		this.test_print_grid(g, cols);
		System.out.println("");
		
		// Test colision
		int i = 1;
	    for(SquareBase s : g.getSquares()) {
	    	if(g.getColidingSquares().containsKey(s))
	    		System.out.print("Kolisionen: " + g.getColidingSquares().get(s) + " | ");
	    	else
	    		System.out.print("Strahler:   " + s + " | ");
	    	
	        if(i % cols == 0)
	        	System.out.println("");
	        i++;
	    }
		
	}
	
	public static void main(String[] args) {
		try {
			TestConsolePrint o = new TestConsolePrint();
			
			/**
			 * CALL DESIRED TEST METHOD
			 */
			//o.test_storeage();
			o.test_colision();
			
		} catch (Exception e) { e.printStackTrace(); }
	}

}
