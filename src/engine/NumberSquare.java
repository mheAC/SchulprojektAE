package engine;

public class NumberSquare implements SquareBase {

	private int number;
	
	public NumberSquare(int num) {
		this.number = num;
	}

	@Override
	public Direction getDirection() {
		return null; // number Squares don't have any direction... this method should never be called on a NumberSquare. Determine this via isNumberSquare()
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return this.number;
	}

	@Override
	public boolean isNumberSquare() {
		return true;
	}

}
