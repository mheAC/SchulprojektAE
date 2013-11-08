package engine;

/**
 * Commons for all Square Typed. This Interface enables us to dynamically extend the amount of different Square Types if needed
 *
 */

public abstract class SquareBase {

	private int posX, posY;
	
	//public abstract Object getSqueryType();
	
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
	
}
