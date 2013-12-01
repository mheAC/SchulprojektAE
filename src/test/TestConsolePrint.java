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
import engine.storage_handler.StorageHandler;

public class TestConsolePrint {
	
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
		grid.generateSquaresBigMiddleTest();
		int i=0;
		for(SquareBase s : grid.getSquaresAsList()) {
			System.out.print(" | ");
			if(s.getClass().equals(new NumberSquare(0,0).getClass()))
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
		NumberSquare ns = (NumberSquare)grid.getSquaresAsList().get(31); // first number square of our dummy data
		for(SquareBase s : grid.getSquaresAsList()) {
			if(s.getClass().equals(new RaySquare(0,0).getClass())) {
				RaySquare rs = (RaySquare)s;
				System.out.print(Boolean.toString(ns.canEnlight(rs)).charAt(0) + " | ");
			}
			else
				System.out.print(s.getPrintableValue() + " | ");
			
			if(g++ % cols == 0)
				System.out.print("\n");
		}
		
		//System.out.println(ns.canEnlight(grid.getSquares().get(16)));
	}
	
	public void test_storeage() throws Exception {
		// Test Storage save
		GameGrid foo = new GameGrid(4,3);
		foo.generateSquaresBigMiddleTest();
		//System.out.println("Original: " + foo.getSquares().size());
		// PRINT
		for(SquareBase sq : foo.getSquaresAsList()) {
			if(sq.getClass().equals(new RaySquare(0,0).getClass())) {
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
		for(SquareBase sq : bar.getSquaresAsList()) {
			if(sq.getClass().equals(new RaySquare(0,0).getClass())) {
				RaySquare rs = (RaySquare)sq;
				System.out.println(rs.getDirection());
			}
			else {
				NumberSquare ns = (NumberSquare)sq;
				System.out.println(ns.getNumber());
			}
		}
	}
	
	private void print_grid (GameGrid grid, int cols) {
		int i=0;
		for(SquareBase s : grid.getSquaresAsList()) {
			System.out.print(" | ");
			if(s.getClass().equals(new NumberSquare(0,0).getClass()))
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
		g.generateSquaresBigMiddleTest();
		
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
		this.print_grid(g, cols); // to see how the grid looks (ascii console print)
		System.out.println("");
		
		// Test colision
		int i = 1;
	    for(SquareBase s : g.getSquaresAsList()) {
	    	if(g.getColidingSquares().containsKey(s))
	    		System.out.print("Kolisionen: " + g.getColidingSquares().get(s) + " | ");
	    	else
	    		System.out.print("Strahler:   " + s + " | ");
	    	
	        if(i % cols == 0)
	        	System.out.println("");
	        i++;
	    }
	    /* Expected:
			|[1]|[1]|[2]| 2
			================
			|[2]|[1]| 2 |[2]
			================
			| 5 |[1]|[2]|[2]
			================
	     */
	    
	    // manual test
	    System.out.println("---");
	    System.out.println("Ray Squares: " + g.getRaySquares().size());
	    
	    // last elem
	    RaySquare lastRs = g.getRaySquares().get(8); // get the last ray square (absolutely right, bottom)
	    System.out.println("Last Square (RaySquare) Data ==> x:" + (lastRs.getPositionX()+1) + " - y: " + (lastRs.getPositionY()+1) );
	    
	    // assign number squares to shorten vars
	    NumberSquare n1 = g.getNumberSquares().get(0); // 2
	    NumberSquare n2 = g.getNumberSquares().get(1); // 2
	    NumberSquare n3 = g.getNumberSquares().get(2); // 5
		
	    // print result
	    System.out.println("Numbersquare 1 can enlight the last RaySquare: " + n1.canEnlight(lastRs));
	    System.out.println("Numbersquare 2 can enlight the last RaySquare: " + n2.canEnlight(lastRs));
	    System.out.println("Numbersquare 3 can enlight the last RaySquare: " + n3.canEnlight(lastRs));
	    
	}
	
	public void test_direction_coordination() {
		GameGrid g = new GameGrid();
		g.generateSquaresBigMiddleTest();
		
		int cols = 4;
		
		print_grid(g, 4);
		
	    // assign number squares to shorten vars
	    NumberSquare n1 = g.getNumberSquares().get(0); // 2
	    NumberSquare n2 = g.getNumberSquares().get(1); // 2
	    NumberSquare n3 = g.getNumberSquares().get(2); // 5
	    
	    RaySquare firstRs = g.getRaySquares().get(0);
	    RaySquare fRs = g.getRaySquares().get(5);
	    RaySquare tRs = g.getRaySquares().get(3);
	    
	    System.out.println(firstRs.getRayDirectionForLightSource(n1));
	    
	    System.out.println(fRs.getRayDirectionForLightSource(n1));
	    
	    System.out.println(tRs.getRayDirectionForLightSource(n1)); // should get null
	}
	
	public static void main(String[] args) {
		try {
			TestConsolePrint o = new TestConsolePrint();
			
			/**
			 * CALL DESIRED TEST METHOD
			 */
			//o.test_grid_render();
			//o.test_storeage();
			//o.test_colision();
			o.test_direction_coordination();
			
		} catch (Exception e) { e.printStackTrace(); }
	}

}
