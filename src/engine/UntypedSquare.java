package engine;

import java.io.*;

/**
 * This is a generic Square for fields which dont have a Type yet
 */

public class UntypedSquare extends SquareBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UntypedSquare(int posx, int posy){
		super(posx,posy);
	}

	@Override
	public String getPrintableValue() {
		return "";
	}
	
	public boolean isUntypedSquare(){
		return true;
	}

}
