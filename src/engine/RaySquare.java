package engine;

import java.io.Serializable;

public class RaySquare extends SquareBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Direction direction;
	
	public RaySquare(int posx, int posy) {
		super(posx,posy);
		this.direction = Direction.UNSET; // default direction
	}
	
	public RaySquare(Direction d, int posx, int posy) {
		super(posx,posy);
		this.direction = d;
	}

	public Direction getDirection() {
		return this.direction;
	}
	
	public void setDirection(Direction d) {
		this.direction = d;
	}
	
	/**
	 * Not yet tested!
	 * @param ns
	 * @return Direction
	 */
	public Direction getRayDirectionForLightSource(NumberSquare ns) {
		if(this.getPositionX() == ns.getPositionX() )
			return Direction.HORIZONTAL;
		else if(this.getPositionY() == ns.getPositionY() )
			return Direction.VERTICAL;
		else
			return null;
	}

	@Override
	public String getPrintableValue() {
		return this.direction.equals(Direction.HORIZONTAL) ? "-" : (this.direction.equals(Direction.VERTICAL) ? "|" : " ");
	}
	
	public boolean isRaySquare(){
		return true;
	}

}
