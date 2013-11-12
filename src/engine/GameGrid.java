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
	/*public void generateSquares() {
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
	}*/
	public void generateSquares() {
		// DUMMY GAME DATA GRID BUILDING
		this.squares = new ArrayList<SquareBase>();
		
		this.cols = 4;
		this.rows = 3;

		this.squares.add(new RaySquare());
		this.squares.add(new RaySquare());
		this.squares.add(new RaySquare());
		this.squares.add(new NumberSquare(2));
		this.squares.add(new RaySquare());
		this.squares.add(new RaySquare());
		this.squares.add(new NumberSquare(2));
		this.squares.add(new RaySquare());
		this.squares.add(new NumberSquare(5));
		this.squares.add(new RaySquare());
		this.squares.add(new RaySquare());
		this.squares.add(new RaySquare());
		
		// Assign the positon
		int x=0;
		int y=0;
		for(SquareBase s: this.squares) {
			s.setPositionX(x);
			s.setPositionY(y);
			//System.out.println(" X: " + x + " Y: "+y);
			y++;
			if(y%4 == 0 && y != 0) {
				x++;
				y=0;
			}
		}
	}
	
	/**
	 * Returns the generated Square Collection. This method may be called more than just one time. It does not change any data 
	 * @return
	 */
	public ArrayList<SquareBase> getSquares() {
		return this.squares;
	}
	
	/**
	 * Only get RAYSquares
	 * @return
	 */
	public ArrayList<RaySquare> getRaySquares () {
		ArrayList<RaySquare> rsl = new ArrayList<RaySquare>(); // Temp list
		for(SquareBase s : this.squares) { // iterate over the complete list of squares (also number squares)
			if(s.getClass().equals(new RaySquare().getClass())) { // filter the iteration for RaySquares only
				RaySquare rs = (RaySquare)s; // we are working with ray squares here, so lets create a reference to a dedicated object instance instead of working with a generic class
				rsl.add(rs);
			}
		}
		return rsl;
	}
	
	/**
	 * Only get NUMBERSquares
	 * @return
	 */
	public ArrayList<NumberSquare> getNumberSquares () {
		ArrayList<NumberSquare> nsl = new ArrayList<NumberSquare>();
		for(SquareBase s : this.squares) {
			if(s.getClass().equals(new NumberSquare().getClass())) {
				NumberSquare ns = (NumberSquare)s;
				nsl.add(ns);
			}
		}
		return nsl;
	}
	
	public boolean solveCurrentRays() {
		boolean everythingSolved = false;
		while(!everythingSolved) {
			for(RaySquare s : this.getRaySquares()) { // iterate over the list of RaySquares
				if(s.getDirection().equals(Direction.UNSET)) { // only take a look at RaySquares which do not have a direction yet
					getColidingSquares(s);
				}
			}
		}
		return true;
	}
	
	/**
	 * Find any Square that may contain a ray coliding with the given Square
	 * @return
	 */
	public ArrayList<SquareBase> getColidingSquares(SquareBase square) {
		ArrayList<SquareBase> tempList = new ArrayList<SquareBase>();
		for(SquareBase s : this.squares) {
			
		}
		return null;
	}
	
	/**
	 * Get all Squares that could possibly be alighthed by the Light Source Square (Number Square)
	 * @param squares
	 * @return
	 */
	public ArrayList<SquareBase> getEnlightableSquares(NumberSquare square) {
		ArrayList<SquareBase> tempList = new ArrayList<SquareBase>();
		for(SquareBase s : this.squares) {
			
		}
		return null;
	}
}
