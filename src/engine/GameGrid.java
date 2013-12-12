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

	// Dimension vars for the grid
	private int cols, rows;
	
	// Datastore var for the squares
	private ArrayList<SquareBase> squares;
	
	//Loghandler
	//private LogHandler lh;

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
		//lh = new LogHandler();
		this.cols = 10;
		this.rows = 10;
		generateSquares();
	}
	
	public GameGrid(int cols, int rows) {
		//lh = new LogHandler();
		this.cols = cols;
		this.rows = rows;
		generateSquares();
	}
	
	/**
	 * Method to generate a square list once
	 */
	private void generateSquares() {
		this.squares = new ArrayList<SquareBase>();
		int x=0;
		int y=0;
		for(int i = 0; i < this.cols * this.rows; i++) {
			this.squares.add(new UntypedSquare(x,y));
			x++;
			if(x % this.cols == 0 && x != 0) {
				y++;
				x=0;
			}
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
	
	/*
	public void generateSquaresBigMiddleTest() {
		// DUMMY GAME DATA GRID BUILDING
		this.squares = new ArrayList<SquareBase>();
		for(int i = 0; i < this.cols * this.rows; i++) {
			if(i!=31)
				this.squares.add(new RaySquare());
			else
				this.squares.add(new NumberSquare( 3 ));
			
		}
	}*/
	
	/*
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
	*/
	
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
		SquareBase square = this.squares.get((this.cols*posY)+posX);
		return square;
	}
	
	/**
	 * Only get RAYSquares
	 * @return
	 */
	public ArrayList<RaySquare> getRaySquares () {
		ArrayList<RaySquare> rsl = new ArrayList<RaySquare>(); // Temp list
		for(SquareBase s : this.squares) { // iterate over the complete list of squares (also number squares)
			if(s.isRaySquare()) { // filter the iteration for RaySquares only
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
			if(s.isNumberSquare()) {
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
	
  public boolean enlight(NumberSquare lightSource, SquareBase target){
    ArrayList<SquareBase> squares = this.getEnlightWay(lightSource,target);
    if(lightSource.getPositionY() == target.getPositionY()){
      for(SquareBase square : squares) {
    	this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsRaySquare(Direction.HORIZONTAL));
      }
    }else if(lightSource.getPositionX() == target.getPositionX()){
      for(SquareBase square : squares) {
        this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsRaySquare(Direction.VERTICAL));
      }
    }
    lightSource.setNumber(lightSource.getNumber()-squares.size());
    if(squares.size()>0){
      return true;
    }else{
      return false;
    }
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
          tempSquare = this.getSquare(x, target.getPositionY());
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
          tempSquare = this.getSquare(x, target.getPositionY());
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
          tempSquare = this.getSquare(target.getPositionX(), y);
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
          tempSquare = this.getSquare(target.getPositionX(), y);
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

	/*
	public LogHandler getLoghandler() {
		return lh;
	}*/
	
	public void setSquare(int x, int y, SquareBase square){
		this.squares.set(y*this.rows+x, square);
	}
}