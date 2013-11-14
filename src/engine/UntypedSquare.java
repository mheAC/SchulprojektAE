package engine;

import java.io.Serializable;

/**
 * This is a generic Square for fields which dont have a Type yet
 */

public class UntypedSquare extends SquareBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getPrintableValue() {
		return "";
	}

}
