package engine;

public interface SquareBase {

	public Direction getDirection(); 	// only for RaySquares
	public int getNumber();				// only for NumberSquares
	public boolean isNumberSquare();	// the inheriting class may tell wether it is or not
	
	
}
