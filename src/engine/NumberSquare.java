package engine;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Javadoc kontrollieren
/**
 * The Class NumberSquare.
 */
public class NumberSquare extends SquareBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The number. */
	private int number;
	
	/** The original_number. */
	private int original_number;
	
	/** List of squares that are enlighted by this lightsource */
	private ArrayList<RaySquare> englighted_squares = new ArrayList<RaySquare>();
	
	/**
	 * Init an instance with the given value.
	 *
	 * @param num the num
	 * @param posX the pos x
	 * @param posY the pos y
	 */
	public NumberSquare(int num, int posX, int posY) {
		super(posX,posY);
		this.number = num;
		this.original_number = num;
	}
	
	/**
	 * Use this constructor only in case you need to modify the number afterwards.
	 * Never use it when you already know the num when instancing an object of this class
	 *
	 * @param posX the pos x
	 * @param posY the pos y
	 */
	public NumberSquare(int posX, int posY) {
		super(posX,posY);
		this.number = -1;
		this.original_number = -1;
	}

	/**
	 * Gets the number.
	 *
	 * @return int the Number of this instance
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Afterwards modify the num. Relevant for beeing able to change settings in the GUI
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Gets the original number.
	 *
	 * @return the original number
	 */
	public int getOriginalNumber(){
		return this.original_number;
	}
	
	/**
	 * Check wether a given Square (any inheriting from SquareBase) could be enlighted by this NumberSquare according to their position.
	 *
	 * @param rs this should be a RaySquare! But if given a NumSquare this method will return false (as expected for number squares can not be enlighted)
	 * @return true / false for if the given square could be enlighted
	 */
	public boolean canEnlight(SquareBase rs) {
		if(rs.getClass() == NumberSquare.class)
			return false; // number squares can never be enlighted
		
		//if(this.getPositionX() == rs.getPositionX() && this.getPositionY() == rs.getPositionY())
			//return false; // of course the number square its self can not be enlighted
		
		if(
				( // first part: check x
					// check the left hand side of the light source
					rs.getPositionX() <= this.getPositionX() && rs.getPositionX() >= this.getPositionX() - (this.getNumber() + this.getEnlightedSquares("left").size()) // range
					|| // or
					// check the right hand side...
					rs.getPositionX() >= this.getPositionX() && rs.getPositionX() <= this.getPositionX() + (this.getNumber() + this.getEnlightedSquares("right").size())
				)
				&& // and
				( // second part: check y
					// check the bottom side
					rs.getPositionY() >= this.getPositionY() && rs.getPositionY() <= this.getPositionY() + (this.getNumber() + this.getEnlightedSquares("underneath").size())
					|| // ...
					// check the upper side
					rs.getPositionY() <= this.getPositionY() && rs.getPositionY() >= this.getPositionY() - (this.getNumber() + this.getEnlightedSquares("above").size())
				)
				&& // ...
				(
					// make sure the coordinate lies somewhere on the x and y axis so we get a cross match insead of a square 
					rs.getPositionX() == this.getPositionX()
					||
					rs.getPositionY() == this.getPositionY()
				)
			) // end of the big if
			return true;
		
		// Otherwise the given square can't be enlighted by this numSquare instance
		return false;
	}

	/**
	 * @see engine.SquareBase#getPrintableValue()
	 */
	@Override
	public String getPrintableValue() {
		return Integer.toString(this.getNumber());
	}
	
	/**
	 * @see engine.SquareBase#isNumberSquare()
	 */
	public boolean isNumberSquare(){
		return true;
	}
	
	/**
	 * 
	 * @return a list of squares that are englighted by this lightsource
	 */
	public ArrayList<RaySquare> getEnlightedSquares(){
		return this.englighted_squares;
	}
	
	/**
	 * 
	 * @param a side (left,right,above,underneath)
	 * @return a list of squares that are enlighted by this lightsource and on the specified side
	 */
	public ArrayList<RaySquare> getEnlightedSquares(String side){
		ArrayList<RaySquare> squares = new ArrayList<RaySquare>();
		for(RaySquare square : this.englighted_squares){
			if(side.equals("left") && square.getPositionY() == this.getPositionY() && square.getPositionX() < this.getPositionX()){
				squares.add(square);
			}else if(side.equals("right") && square.getPositionY() == this.getPositionY() && square.getPositionX() > this.getPositionX()){
				squares.add(square);
			}else if(side.equals("above") && square.getPositionX() == this.getPositionX() && square.getPositionY() < this.getPositionY()){
				squares.add(square);
			}else if(side.equals("underneath") && square.getPositionX() == this.getPositionX() && square.getPositionY() > this.getPositionY()){
				squares.add(square);
			}
		}
		return squares;
	}
	
	/**
	 * 
	 * @param a square to add to the enlighed list
	 */
	public void addEnlightedSquare(RaySquare square){
		this.englighted_squares.add(square);
		this.number = this.original_number - this.englighted_squares.size();
	}
	
	/**
	 * 
	 * @param a square to remove from the enlighted list
	 */
	public void removeEnlightedSquare(RaySquare square){
		this.englighted_squares.remove(square);
		this.number = this.original_number - this.englighted_squares.size();
	}
	
	/**
	 * 
	 * @param an index on which a square should be removed from the enlighted list
	 */
	public void removeEnlightedSquare(int index){
		this.englighted_squares.remove(index);
		this.number = this.original_number - this.englighted_squares.size();
	}
	
	public void clearEnlightedSquares(){
		this.englighted_squares = new ArrayList<RaySquare>();
	}

}
