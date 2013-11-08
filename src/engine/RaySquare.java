package engine;

public class RaySquare implements SquareBase {

	Direction direction;
	
	public RaySquare() {
		this.direction = Direction.HORIZONTAL;
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public int getNumber() {
		return -1; // this method shall never be called on a RaySquare... 
	}

	@Override
	public boolean isNumberSquare() {
		return false;
	}

}
