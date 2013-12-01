package engine;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is a helper class for being able to generate a gaming table 
 * Also this class stores the list of squares our grid got
 */

public class GameGrid implements Serializable{

	private static final long serialVersionUID = 1L;

	// Dimension vars for the grid
	private int cols, rows;
	
	// Datastore var for the squares
	private SquareBase[][] squares;

	public Dimension getGridSize(){
		Dimension dim = new Dimension(this.cols,this.rows);
		return dim;
	}
	
	public void setGridSize(Dimension dim){
		SquareBase[][] squares = new SquareBase[dim.height][dim.width];
		for(int y = 0; y < this.cols; y++) {
			for(int x = 0; x < this.rows; x++)
				try{
					squares[y][x] = this.squares[y][x];
				}catch(Exception e){
					e.printStackTrace();
				}
		}
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
		this.squares = new SquareBase[cols][rows];
		for(int y = 0; y < this.cols; y++) {
			for(int x = 0; x < this.rows; x++)
			this.squares[y][x] = new UntypedSquare(x,y);
		}
	}
	
	
	public void generateSquaresBigMiddleTest() {
		// DUMMY GAME DATA GRID BUILDING		
		this.squares = new SquareBase[cols][rows];
		for(int y = 0; y < this.cols; y++) {
			for(int x = 0; x < this.rows; x++)
				if(y!=31)
					this.squares[y][x] = new UntypedSquare(x,y);
				else
					this.squares[y][x] = new NumberSquare( 3, x, y );
		}
	}
	
	/**
	 * Returns the generated Square Collection. This method may be called more than just one time. It does not change any data 
	 * @return
	 */
	public SquareBase[][] getSquares() {
		return this.squares;
	}
	
	public ArrayList<SquareBase> getSquaresAsList() {
		ArrayList<SquareBase> list = new ArrayList<SquareBase>();
		for(int y = 0; y < this.cols; y++)
			for(int x = 0; x < this.rows; x++)
				list.add(this.squares[y][x]);
		return list;
	}

	
	public SquareBase getSquare(int posX,int posY){
		return this.squares[posX][posY];
	}
	
	public void setSquare(int posX, int posY, SquareBase square){
		this.squares[posY][posX] = square;
	}
	
	public void setSquare(SquareBase square){
		this.squares[square.getPositionY()][square.getPositionX()] = square;
	}
	
	/**
	 * Only get RAYSquares
	 * @return
	 */	
	public ArrayList<RaySquare> getRaySquares () {		
		ArrayList<RaySquare> list = new ArrayList<RaySquare>();
		for(int y = 0; y < this.cols; y++)
			for(int x = 0; x < this.rows; x++){
				if(this.squares[y][x].getClass().equals(new RaySquare(0,0).getClass()))
					list.add((RaySquare) this.squares[y][x]);
			}
				
		return list;
	}
	
	/**
	 * Only get NUMBERSquares
	 * @return
	 */
	public ArrayList<NumberSquare> getNumberSquares () {
		ArrayList<NumberSquare> list = new ArrayList<NumberSquare>();
		for(int y = 0; y < this.cols; y++)
			for(int x = 0; x < this.rows; x++){
				if(this.squares[y][x].getClass().equals(new NumberSquare(0,0).getClass()))
					list.add((NumberSquare) this.squares[y][x]);
			}
				
		return list;
	}
	
	/**
	 * Engine methods
	 * @return
	 */
	public boolean solveCurrentRays() {
		boolean everythingSolved = false;
		while(!everythingSolved) {
			for(RaySquare s : this.getRaySquares()) { // iterate over the list of RaySquares
				if(s.getDirection().equals(Direction.UNSET)) { // only take a look at RaySquares which do not have a direction yet
					getColidingSquares();
				}
			}
		}
		return true;
	}
	
	public ArrayList<SquareBase> getEnlightWay(NumberSquare lightSource, SquareBase target){
		ArrayList<SquareBase> squares = new ArrayList<SquareBase>();
		//target is in same row with lightsource
		if(lightSource.getPositionY() == target.getPositionY()){
			//target is on the right side from lightsource and is reachable by lightsource
			if(target.getPositionX()>lightSource.getPositionX() && lightSource.canEnlight(target)){
				SquareBase tempSquare = target;
				int x = target.getPositionX();
				while(tempSquare.getPositionX()>lightSource.getPositionX()){
					//only can be enlight if untyped square
					if(tempSquare.getClass() == UntypedSquare.class)
						squares.add(tempSquare);
					else{
						squares = new ArrayList<SquareBase>();
						break;
					}
					x --;
					tempSquare = this.squares[target.getPositionY()][x];
				}
			}
			//target is on the left side from lightsource and is reachable by lightsource
			else if(target.getPositionX()<lightSource.getPositionX() && lightSource.canEnlight(target)){
				SquareBase tempSquare = target;
				int x = target.getPositionX();
				while(tempSquare.getPositionX()<lightSource.getPositionX()){
					if(tempSquare.getClass() == UntypedSquare.class)
						squares.add(tempSquare);
					else{
						squares = new ArrayList<SquareBase>();
						break;
					}
					x ++;
					tempSquare = this.squares[target.getPositionY()][x];
				}
			}
		}
		//target is in the same coloumn with light source
		else if(lightSource.getPositionX() == target.getPositionX()){
			//target is above the lightsource and is reachable by lightsource
			if(target.getPositionY()<lightSource.getPositionY() && lightSource.canEnlight(target)){
				SquareBase tempSquare = target;
				int y = target.getPositionY();
				while(tempSquare.getPositionY()<lightSource.getPositionY()){
					//only can be enlight if untyped square
					if(tempSquare.getClass() == UntypedSquare.class)
						squares.add(tempSquare);
					else{
						squares = new ArrayList<SquareBase>();
						break;
					}
					y ++;
					tempSquare = this.squares[y][target.getPositionX()];
				}
			}
			//target is on the under the lightsource and is reachable by lightsource
			else if(target.getPositionY()>lightSource.getPositionY() && lightSource.canEnlight(target)){
				SquareBase tempSquare = target;
				int y = target.getPositionY();
				while(tempSquare.getPositionY()>lightSource.getPositionY()){
					if(tempSquare.getClass() == UntypedSquare.class)
						squares.add(tempSquare);
					else{
						squares = new ArrayList<SquareBase>();
						break;
					}
					y --;
					tempSquare = this.squares[y][target.getPositionX()];
				}
			}
		}
		return squares;
	}
	
	public boolean canEnlightWay(NumberSquare lightSource, SquareBase target){
		if(this.getEnlightWay(lightSource, target).size()>0){
			return true;
		}else
			return false;
	}
	
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
}
