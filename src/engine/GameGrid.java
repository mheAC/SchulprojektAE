package engine;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import loghandler2.Loghandler;


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
	
	public boolean runningGame = false;
	
	//Loghandler
	public Loghandler loghandler;
	
	/*
	 * Author: Andreas Soiron
	 * Logs the current state of the Grid
	 */
	public void log() throws FileNotFoundException, IOException, ClassNotFoundException{
		if(this.loghandler == null){
			this.loghandler = new Loghandler(this);
		}
		this.loghandler.log(this);
	}
	
	/*
	 * Author: Andreas Soiron
	 * Returns the last state of the Grid
	 */
	public GameGrid stepBack(){
		return this.loghandler.back();
	}
	public long logTime(){
		return this.loghandler.logTime();
		
	}

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
		//lh = new LogHandler();
		//cr = new Controller();
		//sv = new Save(lh);
		//cr.setCommands(sv);
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
		this.cols = cols;
		this.rows = rows;
		generateSquares();
	}
	
	/**
	 * Author: Andreas Soiron
	 * Initialize GameGrid with a list of squares.
	 *
	 * @param cols the cols
	 * @param rows the rows
	 */
	public GameGrid(int cols, int rows, ArrayList<SquareBase> squares) {
		this.cols = cols;
		this.rows = rows;
		this.squares = squares;
	}
	
	/**
	 * Method to generate a square list once.
	 */
	private void generateSquares() {
		this.squares = new ArrayList<SquareBase>();
		//int x=0;
		//int y=0;
		for(int yy=0; yy<this.rows;yy++){
			for(int xx=0; xx<this.cols; xx++){
				this.squares.add(new UntypedSquare(xx,yy));
			}
			
		}
		/*for(int i = 0; i < this.cols * this.rows; i++) {
			this.squares.add(new UntypedSquare(x,y));
			x++;
			if(x % this.cols == 0 && x != 0) {
				y++;
				x=0;
			}
		}*/

	}
	
	/*
	 * Author: Andreas Soiron
	 * After a Grid is loaded from Json the assignment from raysquares to Lightsources isn't restored
	 * This function assignes lightsources to raysquares
	 */
	public void restoreConsistence(){
		for(RaySquare square : this.getRaySquares()){
			if(!square.hasLightsource()){
				System.out.println( square.getLightsourcePositionX());
				square.assignLightSource((NumberSquare) this.getSquare(square.getLightsourcePositionX(), square.getLightsourcePositionY()));
			}
		}
	}
	
	/*
	 * Author: Andreas Soiron
	 * Removes all Raysquares and resets Lightsources to there original Number
	 */
	public void resetToPlaymode(){
		for(RaySquare square : this.getRaySquares()){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
		}
	}
	
	
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
	
	
	public ArrayList<SquareBase> getSquares() {
		return this.squares;
	}
	
	/*
	 * Author: Andreas Soiron
	 * Returns the square at the given position
	 */
	public SquareBase getSquare(int posX,int posY){
		int position = (this.cols*posY)+posX;
		if(position>0){
			SquareBase square = this.squares.get(position);
			return square;
		}else{
			return null;
		}
	}
	
	
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
	
	
	public ArrayList<RaySquare> getEnlightableSquares(NumberSquare square) {
		ArrayList<RaySquare> tempList = new ArrayList<RaySquare>();
		for(RaySquare s : this.getRaySquares()) {
			if(square.canEnlight(s))
				tempList.add(s);
		}
		return tempList;
	}
	
	/*
	 * Author: Andreas Soiron
	 * Englights a square for Playmode
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
	
	public boolean enlightuntyped(NumberSquare lightSource, SquareBase target){
		ArrayList<SquareBase> squares = this.getEnlightWayUntype(lightSource,target);
		
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
	


	public NumberSquare createLightsource(UntypedSquare ut){
		NumberSquare newSquare = ut.getAsNumberSquare();
		this.setSquare(ut.getPositionX(), ut.getPositionY(), newSquare);
		return newSquare;
	}
	
	public UntypedSquare deleteLightsource(NumberSquare ns){
		UntypedSquare newUntyped = ns.getAsUntypedSquare();
		this.setSquare(ns.getPositionX(), ns.getPositionY(), newUntyped);
		return newUntyped;
	}
	
	/*
	 * Author: Andreas Soiron
	 * removes all raysquares assigned to the given lightsource on the given side of the lightsource
	 * use left,above,right,underneath for side
	 * Use for playmode
	 */
	public void unenlight(NumberSquare lightSource, String side){
		for(RaySquare square : lightSource.getEnlightedSquares(side)){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
			lightSource.removeEnlightedSquare(square);
		}
	}
	
	/*
	 * Author: Andreas Soiron
	 * Removes all Raysquares that are assigned to the given lightsource
	 */
	public void unenlight(NumberSquare lightSource){
		for(RaySquare square : lightSource.getEnlightedSquares()){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
			lightSource.removeEnlightedSquare(square);
		}
	}
	public void unenlightCREATE(NumberSquare lightSource){
		for(RaySquare square : lightSource.getEnlightedSquares()){
			this.setSquare(square.getPositionX(), square.getPositionY(), square.getAsUntypedSquare());
		}
	}
	
	
	/*
	 * Author: Andreas Soiron
	 * Resets the given Raysquare to a untyped square
	 * automatically resets all squares that cant be englighted by the soruce after reset
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
	
	/*
	 * Author: Andreas Soiron
	 * Returns a list of squares that have to be enlighted to englight the given square by the given lightsource
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
	
	public ArrayList<SquareBase> getEnlightWayUntype(NumberSquare lightSource, SquareBase target){
		  ArrayList<SquareBase> squares = new ArrayList<SquareBase>();
		  //target is in same row with lightsource
		  if(lightSource.getPositionY() == target.getPositionY()){
		    //target is on the right side from lightsource and is reachable by lightsource
		    if(target.getPositionX()>lightSource.getPositionX()){
		      SquareBase tempSquare = target;
		      int x = target.getPositionX();
		      while(tempSquare.getPositionX()>lightSource.getPositionX()){
		          //only can be enlight if untyped square
		          if(tempSquare.getClass() == UntypedSquare.class || ((tempSquare.getClass() == RaySquare.class && lightSource.isNumberSquare())  && ((RaySquare)tempSquare).getLightSource().getPositionX() == lightSource.getPositionX() && ((RaySquare)tempSquare).getLightSource().getPositionY() == lightSource.getPositionY()))
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
		    else if(target.getPositionX()<lightSource.getPositionX()){
		        SquareBase tempSquare = target;
		        int x = target.getPositionX();
		        while(tempSquare.getPositionX()<lightSource.getPositionX()){
		          if(tempSquare.getClass() == UntypedSquare.class || ((tempSquare.getClass() == RaySquare.class && lightSource.isNumberSquare())  && ((RaySquare)tempSquare).getLightSource().getPositionX() == lightSource.getPositionX() && ((RaySquare)tempSquare).getLightSource().getPositionY() == lightSource.getPositionY())){
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
		    if(target.getPositionY()<lightSource.getPositionY()){
	        SquareBase tempSquare = target;
	        int y = target.getPositionY();
	        while(tempSquare.getPositionY()<lightSource.getPositionY()){
	          //only can be enlight if untyped square
	          if(tempSquare.getClass() == UntypedSquare.class || ((tempSquare.getClass() == RaySquare.class && lightSource.isNumberSquare())  && ((RaySquare)tempSquare).getLightSource().getPositionX() == lightSource.getPositionX() && ((RaySquare)tempSquare).getLightSource().getPositionY() == lightSource.getPositionY()))
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
		    else if(target.getPositionY()>lightSource.getPositionY()){
		        SquareBase tempSquare = target;
		        int y = target.getPositionY();
		        // TODO: Check why nullpointer exceptions happen here after changing grid size
		        while(tempSquare.getPositionY()>lightSource.getPositionY()){
		          if(tempSquare.getClass() == UntypedSquare.class || ((tempSquare.getClass() == RaySquare.class && lightSource.isNumberSquare())  && ((RaySquare)tempSquare).getLightSource().getPositionX() == lightSource.getPositionX() && ((RaySquare)tempSquare).getLightSource().getPositionY() == lightSource.getPositionY()))
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
	
	public void setSquare(int x, int y, SquareBase square){
		this.squares.set((this.cols*y)+x, square);
	}

	
	public void addRow() {
		this.addRow(1);
	}
	
	
	public void addRow(int count) {
		Dimension dim = this.getGridSize();
		int width = (int)dim.getWidth();
		int height = (int)dim.getHeight();
		
		for (int ff = 0; ff < count; ff++) {
			for (int ii = 0; ii < width; ii++) {
				squares.add(new UntypedSquare(ii, height));
			}
		}
		
		this.setGridSize(new Dimension(width, height+count));
	}
	

	public void removeRow() {
		this.removeRow(1);
	}
	
	

	public void removeRow(int count) {
		Dimension dim = this.getGridSize();
		int startsize = squares.size();
		int width = (int)dim.getWidth();
		int height = (int)dim.getHeight();
		
		int targetsize = startsize-width;
		
		for (int ff = 0; ff < count; ff++) {
			for (int ii = (startsize-1); ii >= targetsize; ii--) {
				squares.remove(ii);
			}
		}
		
		this.setGridSize(new Dimension(width, height-count));
	}
	

	public void addColumn() {
		this.addColumn(1);
	}
	

	
	
	public void addColumntest(int count){
		for (int ff = 0; ff < count; ff++) {
			Dimension dim = this.getGridSize();
			int width = (int)dim.getWidth();
			/*int i=0, c=0;
			while(true){
				if(i>=this.squares.size())
					break;
				int posx = this.squares.get(i).getPositionX();
				int posy = this.squares.get(i).getPositionY();
				if(posx == width-1){
					int index = (posx+1)*(posy+1);
					
					squares.add(index+c,new UntypedSquare(width, posy));
					c++;
					i++;
				}
				else
					i++;
			}*/
			for (int ii = squares.size();ii>0;ii=(ii-(int)dim.getWidth())) {
				squares.add(ii,new UntypedSquare((int)dim.getHeight()-ff, (int)dim.getWidth()));
			}
			this.setGridSize(new Dimension((int)dim.getWidth()+(ff+1), (int)dim.getHeight()));}
	}
	public void addColumn(int count) {
		for (int ff = 0; ff < count; ff++) {
			Dimension dim = this.getGridSize();
			int width = (int)dim.getWidth();
			/*int i=0, c=0;
			while(true){
				if(i>=this.squares.size())
					break;
				int posx = this.squares.get(i).getPositionX();
				int posy = this.squares.get(i).getPositionY();
				if(posx == width-1){
					int index = (posx+1)*(posy+1);
					
					squares.add(index+c,new UntypedSquare(width, posy));
					c++;
					i++;
				}
				else
					i++;
			}*/
			for (int ii = squares.size();ii>0;ii=(ii-(int)dim.getWidth())) {
				squares.add(ii,new UntypedSquare((int)dim.getHeight()-ff, (int)dim.getWidth()));
			}
			this.setGridSize(new Dimension((int)dim.getWidth()+(ff+1), (int)dim.getHeight()));
			
		}
	}

	public void removeColumn() {
		this.removeColumn(1);
	}
	

	public void removeColumn(int count) {
		for (int ff = 0; ff < count; ff++) {
			Dimension dim = this.getGridSize();
			int width = (int)dim.getWidth();
			for (int ii = (squares.size()-1); ii >= 0; ii=(ii-width)) {
				squares.remove(ii);
			}
			this.setGridSize(new Dimension((int)dim.getWidth()-(ff+1), (int)dim.getHeight()));
		}
	}

}
