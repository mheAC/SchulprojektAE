package engine;

/**
 * Commons for all Square Typed. This Interface enables us to dynamically extend the amount of different Square Types if needed
 */

public abstract class SquareBase {
	
	private int posX, posY;
	
	public int getPositionX() {
		return posX;
	}
	public int getPositionY(){
		return posY;
	}
	
	public void setPositionX(int x) {
		posX = x;
	}
	public void setPositionY(int y) {
		posY = y;
	}
	
	public abstract String getPrintableValue();
	
	@Override
	public String toString() {
		return this.getPrintableValue();
	}
	
	/*
	 * "Casting" methods
	 */
	public RaySquare getAsRaySquare() {
		RaySquare rs = new RaySquare();
		rs.setPositionX(posX);
		rs.setPositionY(posY);
		return rs;
	}
	
	public NumberSquare getAsNumberSquare() {
		NumberSquare ns = new NumberSquare();
		ns.setPositionX(posX);
		ns.setPositionY(posY);
		return ns;
	}
	
	public UntypedSquare getAsUntypedSquare() {
		UntypedSquare us = new UntypedSquare();
		us.setPositionX(posX);
		us.setPositionY(posY);
		return us;
	}
	
}
