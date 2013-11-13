package start_gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import engine.SquareBase;

public class JGameSquare extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SquareBase representingSquare;
	private Border border;
	
	public JGameSquare(){
		super();
		border = BorderFactory.createLineBorder(Color.black);
		this.setBorder(border);
	}
	
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	public void setRepresentingSquare(SquareBase representingSquare) {
		this.representingSquare = representingSquare;
	}
	
}
