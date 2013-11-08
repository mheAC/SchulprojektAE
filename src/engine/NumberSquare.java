package engine;

public class NumberSquare extends SquareBase {

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

}
