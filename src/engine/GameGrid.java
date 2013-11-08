package engine;

import java.util.ArrayList;


/**
 * This is a helper class for being able to generate a gaming table 
 *
 */

public class GameGrid {

	// Dimension vars for the grid
	private int cols, rows;
	
	// Datastore var for the squares
	private ArrayList<SquareBase> squares;
	
	public GameGrid() {
		// Default grid dimensions
		this.cols = 10;
		this.rows = 10;
	}
	
	public GameGrid(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
	}
	
	/**
	 * Method to generate a square list once
	 */
	public void generateSquares() {
		// DUMMY GAME DATA GRID BUILDING
		//ArrayList<SquareBase> tempList = new ArrayList<SquareBase>();
		this.squares = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			if(Math.random()>0.2)
				//tempList.add(new RaySquare(Math.random()>0.5?Direction.HORIZONTAL:Direction.VERTICAL));
				this.squares.add(new RaySquare());
			else
				this.squares.add(new NumberSquare( new Double(Math.random() * ( 9 - 1 )).intValue() ));
			
		}
	}
	
	/**
	 * Returns the generated Square Collection. This method may be called more than just one time. It does not change any data 
	 * @return
	 */
	public ArrayList<SquareBase> getSquares() { // TODO may be a Array instead of an ArrayList.. change this later perhaps
		return this.squares;
	}

}
