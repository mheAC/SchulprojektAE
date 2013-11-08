package engine;

public class RaySquare extends SquareBase {

	Direction direction;
	
	public RaySquare() {
		this.direction = Direction.HORIZONTAL; // default direction
	}
	
	public RaySquare(Direction d) {
		this.direction = d;
	}

	public Direction getDirection() {
		return this.direction;
	}

}
