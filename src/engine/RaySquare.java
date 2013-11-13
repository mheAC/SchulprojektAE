package engine;

import java.io.Serializable;

public class RaySquare extends SquareBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Direction direction;
	
	public RaySquare() {
		this.direction = Direction.UNSET; // default direction
	}
	
	public RaySquare(Direction d) {
		this.direction = d;
	}

	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction d) {
		this.direction = d;
	}

	@Override
	public String getPrintableValue() {
		return this.direction.equals(Direction.HORIZONTAL) ? "-" : "|";
	}

}
