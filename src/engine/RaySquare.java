package engine;

import java.io.Serializable;

// TODO: Javadoc kontrollieren
/**
 * The Class RaySquare.
 */
public class RaySquare extends SquareBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The direction. */
	Direction direction;
	
	private NumberSquare lightSource;
	
	/**
	 * Instantiates a new ray square.
	 *
	 * @param posx the posx
	 * @param posy the posy
	 */
	public RaySquare(int posx, int posy) {
		super(posx,posy);
		this.direction = Direction.UNSET; // default direction
	}
	
	public RaySquare(int posx, int posy, NumberSquare lightSource){
		super(posx,posy);
		this.lightSource = lightSource;
		if(this.lightSource.getPositionX()==this.getPositionX()){
			direction = Direction.HORIZONTAL;
		}else if(this.lightSource.getPositionY()==this.getPositionY()){
			direction = Direction.VERTICAL;
		}else{
			direction = Direction.UNSET;
		}
		lightSource.addEnlightedSquare(this);
	}
	
	/**
	 * Instantiates a new ray square.
	 *
	 * @param d the d
	 * @param posx the posx
	 * @param posy the posy
	 */
	public RaySquare(Direction d, int posx, int posy) {
		super(posx,posy);
		this.direction = d;
	}

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the direction.
	 *
	 * @param d the new direction
	 */
	public void setDirection(Direction d) {
		this.direction = d;
	}
	
	/**
	 * Not yet tested!.
	 *
	 * @param ns the ns
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

	/**
	 * @see engine.SquareBase#getPrintableValue()
	 */
	@Override
	public String getPrintableValue() {
		return this.direction.equals(Direction.HORIZONTAL) ? "-" : (this.direction.equals(Direction.VERTICAL) ? "|" : " ");
	}
	
	public NumberSquare getLightSource(){
		return this.lightSource;
	}
	
	/**
	 * @see engine.SquareBase#isRaySquare()
	 */
	public boolean isRaySquare(){
		return true;
	}

}
