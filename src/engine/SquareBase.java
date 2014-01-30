package engine;

// TODO: Javadoc kontrollieren
/**
 * Commons for all Square Typed. This Interface enables us to dynamically extend the amount of different Square Types if needed
 */

public abstract class SquareBase {
	
	/** The pos y. */
	private int posX, posY;
	
	/**
	 * Instantiates a new square base.
	 *
	 * @param posX the pos x
	 * @param posY the pos y
	 */
	public SquareBase(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	/**
	 * Gets the position x.
	 *
	 * @return the position x
	 */
	public int getPositionX() {
		return posX;
	}
	
	/**
	 * Gets the position y.
	 *
	 * @return the position y
	 */
	public int getPositionY(){
		return posY;
	}
	
	/**
	 * Sets the position x.
	 *
	 * @param x the new position x
	 */
	public void setPositionX(int x) {
		posX = x;
	}
	
	/**
	 * Sets the position y.
	 *
	 * @param y the new position y
	 */
	public void setPositionY(int y) {
		posY = y;
	}
	
	/**
	 * Gets the printable value.
	 *
	 * @return the printable value
	 */
	public abstract String getPrintableValue();
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getPrintableValue();
	}
	
	/*
	 * "Casting" methods
	 */
	/**
	 * Gets the as ray square.
	 *
	 * @return the as ray square
	 */
	public RaySquare getAsRaySquare() {
		RaySquare rs = new RaySquare(this.posX,this.posX);
		return rs;
	}
	
	/**
	 * Gets the as ray square.
	 *
	 * @param direction the direction
	 * @return the as ray square
	 */
	public RaySquare getAsRaySquare(Direction direction) {
		RaySquare rs = new RaySquare(direction,this.posX,this.posX);
		return rs;
	}
	
	/*
	 * Author: Andreas Soiron
	 * converts the square to a raysquare
	 */
	public RaySquare getAsRaySquare(NumberSquare lightSource) {
		RaySquare rs = new RaySquare(this.posX,this.posY,lightSource);
		return rs;
	}
	
	/**
	 * Gets the as number square.
	 *
	 * @return the as number square
	 */
	public NumberSquare getAsNumberSquare() {
		NumberSquare ns = new NumberSquare(posX,posY);
		return ns;
	}
	
	/**
	 * Gets the as untyped square.
	 *
	 * @return the as untyped square
	 */
	public UntypedSquare getAsUntypedSquare() {
		UntypedSquare us = new UntypedSquare(this.posX,this.posY);
		return us;
	}
	
	/**
	 * Checks if is number square.
	 *
	 * @return true, if is number square
	 */
	public boolean isNumberSquare(){
		return false;
	}
	
	/**
	 * Checks if is ray square.
	 *
	 * @return true, if is ray square
	 */
	public boolean isRaySquare(){
		return false;
	}
	
	/**
	 * Checks if is untyped square.
	 *
	 * @return true, if is untyped square
	 */
	public boolean isUntypedSquare(){
		return false;
	}
	
	/*
	 * Author: Andreas Soiron
	 * returns a position as string which represents the position relative to another square
	 */
	public String getRelativePositionTo(SquareBase square){
		if(square.getPositionY() == this.getPositionY() && this.getPositionX() < square.getPositionX()){
			return "left";
		}else if(square.getPositionY() == this.getPositionY() && this.getPositionX() > square.getPositionX()){
			return "right";
		}else if(square.getPositionX() == this.getPositionX() && this.getPositionY() < square.getPositionY()){
			return "obove";
		}else if(square.getPositionX() == this.getPositionX() && this.getPositionY() > square.getPositionY()){
			return "underneath";
		}else{
			return "undefined";
		}
	}
	
}
