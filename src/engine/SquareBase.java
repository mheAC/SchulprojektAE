package engine;

/**
 * Commons for all Square Typed. This Interface enables us to dynamically extend the amount of different Square Types if needed
 */

public abstract class SquareBase {
	
	private int posX, posY;
	
	public SquareBase(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
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
		RaySquare rs = new RaySquare(this.posX,this.posX);
		return rs;
	}
	
	public RaySquare getAsRaySquare(Direction direction) {
		RaySquare rs = new RaySquare(direction,this.posX,this.posX);
		return rs;
	}
	
	public NumberSquare getAsNumberSquare() {
		NumberSquare ns = new NumberSquare(posX,posY);
		return ns;
	}
	
	public UntypedSquare getAsUntypedSquare() {
		UntypedSquare us = new UntypedSquare(this.posX,this.posY);
		return us;
	}
	
	public boolean isNumberSquare(){
		return false;
	}
	
	public boolean isRaySquare(){
		return false;
	}
	
	public boolean isUntypedSquare(){
		return false;
	}
	
}
