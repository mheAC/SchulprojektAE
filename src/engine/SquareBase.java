package engine;

/**
 * Commons for all Square Typed. This Interface enables us to dynamically extend the amount of different Square Types if needed
 */

public abstract class SquareBase {
	
	private int posX, posY;
	
	protected GridChangeListener gcl;
	
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
		return (RaySquare) getGenericSquareWithCopiedAttributes(new RaySquare());
	}
	
	public NumberSquare getAsNumberSquare() {
		return (NumberSquare) getGenericSquareWithCopiedAttributes(new NumberSquare());
	}
	
	public UntypedSquare getAsUntypedSquare() {
		return (UntypedSquare) getGenericSquareWithCopiedAttributes(new UntypedSquare());
	}
	
	/*
	 * Here's the "copy magic"
	 */
	private SquareBase getGenericSquareWithCopiedAttributes(SquareBase s) {
		s.setPositionX(posX);
		s.setPositionY(posY);
		s.setGridChangeListener(gcl);
		return s;
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
	
	public void setGridChangeListener(GridChangeListener gcl) {
		this.gcl = gcl;
	}
	
}
