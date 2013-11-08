package engine;

import java.util.ArrayList;


/**
 * This is a helper class for beeing able to generate a gaming table 
 *
 */

public class GameGrid {

	// Dimension vars for the grid
	private int cols, rows;
	
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
	 * 
	 * @return
	 */
	public ArrayList<SquareBase> getSquares() { // TODO may be a Array instead of an ArrayList.. change this
		// DUMMY GAME DATA GRID BUILDING
		ArrayList<SquareBase> tempList = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			if(Math.random()<0.2)
				tempList.add(new RaySquare());
			else
				tempList.add(new NumberSquare( new Double(Math.random() * ( 15 - 1 )).intValue() ));
			
		}
		return tempList;
	}

}
