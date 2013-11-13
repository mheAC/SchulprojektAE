package start_gui;

import javax.swing.JPanel;

import engine.SquareBase;

public class JGameSquare extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SquareBase representingSquare;
	
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	public void setRepresentingSquare(SquareBase representingSquare) {
		this.representingSquare = representingSquare;
	}
	
}
