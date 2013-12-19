package engine;

import java.io.*;

// TODO: Javadoc kontrollieren
/**
 * This is a generic Square for fields which dont have a Type yet.
 */

public class UntypedSquare extends SquareBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new untyped square.
	 *
	 * @param posx the posx
	 * @param posy the posy
	 */
	public UntypedSquare(int posx, int posy){
		super(posx,posy);
	}

	/**
	 * @see engine.SquareBase#getPrintableValue()
	 */
	@Override
	public String getPrintableValue() {
		return "";
	}
	
	/**
	 * @see engine.SquareBase#isUntypedSquare()
	 */
	public boolean isUntypedSquare(){
		return true;
	}

}
