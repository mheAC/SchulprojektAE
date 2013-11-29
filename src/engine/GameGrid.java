package engine;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;


/**
 * This is a helper class for being able to generate a gaming table 
 * Also this class stores the list of squares our grid got
 */

public class GameGrid implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private GridChangeListener gcl;

	// Dimension vars for the grid
	private int cols, rows;
	
	// Datastore var for the squares
	private ArrayList<SquareBase> squares;
	
	public Dimension getGridSize(){
		Dimension dim = new Dimension(this.cols,this.rows);
		return dim;
	}
	
	public void setGridSize(Dimension dim){
		this.rows = dim.height;
		this.cols = dim.width;
	}
	
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
		this.squares = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			this.squares.add(new UntypedSquare());
		}
	}
	
	
	/*public void generateSquares() {
		// DUMMY GAME DATA GRID BUILDING
		//ArrayList<SquareBase> tempList = new ArrayList<SquareBase>();
		this.squares = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			if(Math.random()>0.2)
				this.squares.add(new RaySquare(Math.random()>0.5?Direction.HORIZONTAL:Direction.VERTICAL));
				//this.squares.add(new RaySquare());
			else
				this.squares.add(new NumberSquare( new Double(Math.random() * ( 9 - 1 )).intValue() ));
			
		}
	}*/
	
	public void generateSquaresBigMiddleTest() {
		// DUMMY GAME DATA GRID BUILDING
		this.squares = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			if(i!=31)
				this.squares.add(new RaySquare());
			else
				this.squares.add(new NumberSquare( 3 ));
			
		}
	}
	
	public void generateSquaresTEST() { // Only a testing method
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
		
        asignSquareCoordinates();
	}
	
	/**
	 * This method will assign the x and y coordinates to any square in the list
	 */
	public void asignSquareCoordinates() { // TODO this should happen automatically!! (make it also private)
		int x=0;
		int y=0;
		for(SquareBase s: this.squares) {
			s.setPositionX(x);
			s.setPositionY(y);
			x++;
			if(x % this.cols == 0 && x != 0) {
				y++;
				x=0;
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
	
	public SquareBase getSquare(int posX,int posY){
		SquareBase square = null;
		java.util.Iterator<SquareBase> squaresIterator = this.squares.iterator();
		while(squaresIterator.hasNext()){
			SquareBase s = squaresIterator.next();
			System.out.println(s.getPositionX()+ " " +s.getPositionY());
			if(s.getPositionX() == posX && s.getPositionY() == posY){
				square = s;
			}
		}
		return square;
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
	
	/**
	 * Engine methods
	 * @return
	 */
	/*public boolean solveCurrentRays() {
		boolean everythingSolved = false;
		while(!everythingSolved) {
			for(RaySquare s : this.getRaySquares()) { // iterate over the list of RaySquares
				if(s.getDirection().equals(Direction.UNSET)) { // only take a look at RaySquares which do not have a direction yet
					getColidingSquares();
				}
			}
		}
		return true;
	}*/
	
	/**
	 * Find any Square that may contain a ray coliding with the given Square
	 * @return
	 */
	public HashMap<RaySquare, Integer> getColidingSquares() {
		HashMap<RaySquare, Integer> matches = new HashMap<RaySquare, Integer>();
		int count = 1;
		
		for(NumberSquare ns : this.getNumberSquares()) {
			count = 1;
			for(RaySquare rs : this.getRaySquares()) {
				if(ns.canEnlight(rs)) {
					if(matches.containsKey(rs))
						count = matches.get(rs) + 1;
					else
						count = 1;
					matches.put(rs, count); 
				}
			}
		}
		return matches;
	}
	
	/**
	 * Get all Squares that could possibly be alighthed by the Light Source Square (Number Square)
	 * @param squares
	 * @return
	 */
	public ArrayList<RaySquare> getEnlightableSquares(NumberSquare square) {
		ArrayList<RaySquare> tempList = new ArrayList<RaySquare>();
		for(RaySquare s : this.getRaySquares()) {
			if(square.canEnlight(s))
				tempList.add(s);
		}
		return tempList;
	}
	
	public void setGridChangeListener(GridChangeListener gcl) {
		this.gcl = gcl;
	}
	
}
