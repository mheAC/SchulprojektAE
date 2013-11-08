package engine;

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

}
