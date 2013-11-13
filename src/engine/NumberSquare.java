package engine;

import java.io.Serializable;

public class NumberSquare extends SquareBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int number;
	
	/**
	 * Init an instance with the given value
	 * @param num
	 */
	public NumberSquare(int num) {
		this.number = num;
	}
	
	/**
	 * Use this constructor only in case you need to modify the number afterwards. 
	 * Never use it when you already know the num when instancing an object of this class
	 */
	public NumberSquare() {
		this.number = -1;
	}

	/**
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
	 * Check wether a given Square (any inheriting from SquareBase) could be enlighted by this NumberSquare according to their position
	 * @param rs this should be a RaySquare! But if given a NumSquare this method will return false (as expected for number squares can not be enlighted)
	 * @return true / false for if the given square could be enlighted
	 */
	public boolean canEnlight(SquareBase rs) {
		if(rs.getClass().equals(new NumberSquare().getClass()))
			return false; // number squares can never be enlighted
		
		//if(this.getPositionX() == rs.getPositionX() && this.getPositionY() == rs.getPositionY())
			//return false; // of course the number square its self can not be enlighted
		
		if(
				( // first part: check x
					// check the left hand side of the light source
					rs.getPositionX() <= this.getPositionX() && rs.getPositionX() >= this.getPositionX() - this.getNumber() // range
					|| // or
					// check the right hand side...
					rs.getPositionX() >= this.getPositionX() && rs.getPositionX() <= this.getPositionX() + this.getNumber()
				)
				&& // and
				( // second part: check y
					// check the bottom side
					rs.getPositionY() >= this.getPositionY() && rs.getPositionY() <= this.getPositionY() + this.getNumber()
					|| // ...
					// check the upper side
					rs.getPositionY() <= this.getPositionY() && rs.getPositionY() >= this.getPositionY() - this.getNumber()
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

	@Override
	public String getPrintableValue() {
		return Integer.toString(this.getNumber());
	}

}
