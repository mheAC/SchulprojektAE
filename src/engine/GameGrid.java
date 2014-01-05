package engine;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;


// TODO: Javadoc kontrollieren
/**
 * This is a helper class for being able to generate a gaming table
 * Also this class stores the list of squares our grid got.
 */

public class GameGrid implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	// Dimension vars for the grid
	/** The rows. */
	private int cols, rows;
	
	// Datastore var for the squares
	/** The squares. */
	private ArrayList<SquareBase> squares;
	
	//Loghandler
	private LogHandler lh;

	/**
	 * get the size of the gamegrid.
	 *
	 * @return the grid size
	 */
	public Dimension getGridSize(){
		Dimension dim = new Dimension(this.cols,this.rows);
		return dim;
	}
	
	/**
	 * Change size of the gamegrid.
	 *
	 * @param dim the new grid size
	 */
	public void setGridSize(Dimension dim){
		this.rows = dim.height;
		this.cols = dim.width;
	}
	
	/**
	 * Initialize GameGrid with default amount (10) of columns and rows.
	 */
	public GameGrid() {
		// Default grid dimensions
		lh = new LogHandler();
		this.cols = 10;
		this.rows = 10;
		generateSquares();
	}
	
	/**
	 * Initialize GameGrid with given amount of columns and rows.
	 *
	 * @param cols the cols
	 * @param rows the rows
	 */
	public GameGrid(int cols, int rows) {
		lh = new LogHandler();
		this.cols = cols;
		this.rows = rows;
		generateSquares();
	}
	
	/**
	 * Method to generate a square list once.
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
	 * This method will assign the x and y coordinates to any square in the list.
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
	 *
	 * @return the squares
	 */
	public ArrayList<SquareBase> getSquares() {
		return this.squares;
	}
	
	/**
	 * Gets the square.
	 *
	 * @param posX the pos x
	 * @param posY the pos y
	 * @return the square
	 */
	public SquareBase getSquare(int posX,int posY){
		SquareBase square = this.squares.get((this.cols*posY)+posX);
		return square;
	}
	
	/**
	 * Only get RAYSquares.
	 *
	 * @return the ray squares
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
	 * Only get NUMBERSquares.
	 *
	 * @return the number squares
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
	 * Engine methods.
	 *
	 * @return true, if successful
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
	 * Find any Square that may contain a ray coliding with the given Square.
	 *
	 * @return the coliding squares
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
	 * Get all Squares that could possibly be alighthed by the Light Source Square (Number Square).
	 *
	 * @param square the square
	 * @return the enlightable squares
	 */
	public ArrayList<RaySquare> getEnlightableSquares(NumberSquare square) {
		ArrayList<RaySquare> tempList = new ArrayList<RaySquare>();
		for(RaySquare s : this.getRaySquares()) {
			if(square.canEnlight(s))
				tempList.add(s);
		}
		return tempList;
	}
	
	/**
	 * Enlight all Squares between source and target.
	 *
	 * @param lightSource the light source
	 * @param target square
	 * @return boolean, whether squares were enlighted
	*/
	public boolean enlight(NumberSquare lightSource, SquareBase target){
		ArrayList<SquareBase> squares = this.getEnlightWay(lightSource,target);
		
		this.unenlight(lightSource, target.getRelativePositionTo(lightSource));
		for(int i = 0; i < squares.size(); i++) {
			SquareBase square = squares.get(i);
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsRaySquare(lightSource));
		}
		if(squares.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param a NumberSquare as lightSource
	 * @param side as String (left,right,above,underneath)
	 */
	public void unenlight(NumberSquare lightSource, String side){
		for(RaySquare square : lightSource.getEnlightedSquares(side)){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
			lightSource.removeEnlightedSquare(square);
		}
	}
	
	/**
	 * 
	 * @param lightSource
	 */
	public void unenlight(NumberSquare lightSource){
		for(RaySquare square : lightSource.getEnlightedSquares()){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
			lightSource.removeEnlightedSquare(square);
		}
	}
	
	
	/**
	 * 
	 * @param square
	 * @return
	 * unenlights a square and all squares that can't be enlighted anymore
	 */
	public boolean unenlight(RaySquare square){
		if(square.getClass() == RaySquare.class){
			ArrayList<RaySquare> squaresToUnenlight;
			String relativPositionToLightsource = square.getRelativePositionTo(square.getLightSource());
			squaresToUnenlight = square.getLightSource().getEnlightedSquares(relativPositionToLightsource);
			for(RaySquare tmpSquare : squaresToUnenlight){
				if(tmpSquare.getRelativePositionTo(square) == relativPositionToLightsource){
					this.setSquare(tmpSquare.getPositionX(), tmpSquare.getPositionY(), tmpSquare.getAsUntypedSquare());
					tmpSquare.getLightSource().removeEnlightedSquare(tmpSquare);
				}
			}
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
			square.getLightSource().removeEnlightedSquare(square);
			return true;
		}else{
			return false;
		}
	}
	
  /**
   * Get all Squares that can be enlighted between the source and the target.
   *
   * @param lightSource the light source
   * @param target the target
   * @return arraylist with all squares that can be enlighted between source and target
   */
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
	          if(tempSquare.getClass() == UntypedSquare.class || (tempSquare.getClass() == RaySquare.class && ((RaySquare)tempSquare).getLightSource() == lightSource))
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
	          if(tempSquare.getClass() == UntypedSquare.class || (tempSquare.getClass() == RaySquare.class && ((RaySquare)tempSquare).getLightSource() == lightSource)){
	            squares.add(tempSquare);
	          }
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
          if(tempSquare.getClass() == UntypedSquare.class || (tempSquare.getClass() == RaySquare.class && ((RaySquare)tempSquare).getLightSource() == lightSource))
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
	          if(tempSquare.getClass() == UntypedSquare.class || (tempSquare.getClass() == RaySquare.class && ((RaySquare)tempSquare).getLightSource() == lightSource))
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
	
	/**
	 * Check if it is possible to enlight squares between source and target.
	 *
	 * @param lightSource the light source
	 * @param target the target
	 * @return boolean, whether it is possible to enlight suqares between source and target
	 */
	public boolean canEnlightWay(NumberSquare lightSource, SquareBase target){
    if(this.getEnlightWay(lightSource, target).size()>0){
      return true;
    }else
      return false;
	}

	
	public LogHandler getLoghandler() {
		return lh;
	}
	
	public void log(){
		this.lh.setStack(this);
	}
	
	/**
	 * Replace a Squarebase at x and y coordinates with another square.
	 *
	 * @param x the x
	 * @param y the y
	 * @param square the square
	 */
	public void setSquare(int x, int y, SquareBase square){
		
		//System.out.println(this.squares.get((this.cols*y)+x).getPositionY()+" "+this.squares.get((this.cols*y)+x).getPositionX());
		this.squares.set((this.cols*y)+x, square);
	}

	/**
	 * Adds one row of untyped squares.
	 */
	public void addRow() {
		this.addRow(1);
	}
	
	/**
	 * Add given amount of rows to gamegrid.
	 *
	 * @param count the count
	 */
	public void addRow(int count) {
		Dimension dim = this.getGridSize();
		int width = (int)dim.getWidth();
		int height = (int)dim.getHeight();
		
		for (int ff = 0; ff < count; ff++) {
			for (int ii = 0; ii < width; ii++) {
				squares.add(new UntypedSquare(height+count, ii));
			}
		}
		
		this.setGridSize(new Dimension(width, height+count));
	}
	
	/**
	 * Removes one row.
	 */
	public void removeRow() {
		this.removeRow(1);
	}
	
	/**
	 * Removes given amount of rows.
	 *
	 * @param count the count
	 */
	public void removeRow(int count) {
		Dimension dim = this.getGridSize();
		int width = (int)dim.getWidth();
		int height = (int)dim.getHeight();
		
		for (int ff = 0; ff < count; ff++) {
			for (int ii = (squares.size()-1); ii >= (squares.size() - (int)dim.getWidth()); ii--) {
				squares.remove(ii);
			}
		}
		
		this.setGridSize(new Dimension(width, height-count));
	}
	
	/**
	 * Adds one column to gamegrid.
	 */
	public void addColumn() {
		this.addColumn(1);
	}
	
	/**
	 * Adds given amount of columns to the gamegrid.
	 *
	 * @param count the count
	 */
	public void addColumn(int count) {
		for (int ff = 0; ff < count; ff++) {
			Dimension dim = this.getGridSize();
			for (int ii = squares.size();ii>0;ii=(ii-(int)dim.getWidth())) {
				squares.add(ii,new UntypedSquare((int)dim.getHeight()-ff, (int)dim.getWidth()));
			}
			this.setGridSize(new Dimension((int)dim.getWidth()+(ff+1), (int)dim.getHeight()));
		}
	}
	
	/**
	 * Removes one column.
	 */
	public void removeColumn() {
		this.removeColumn(1);
	}
	
	/**
	 * Removes given amount of columns from the gamegrid.
	 *
	 * @param count the count
	 */
	public void removeColumn(int count) {
		for (int ff = 0; ff < count; ff++) {
			Dimension dim = this.getGridSize();
			for (int ii = (squares.size()-1); ii > 0; ii=(ii-(int)dim.getHeight())) {
				squares.remove(ii);
			}
			this.setGridSize(new Dimension((int)dim.getWidth()-(ff+1), (int)dim.getHeight()));
		}
	}
}