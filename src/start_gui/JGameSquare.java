package start_gui;

import java.awt.Color;
import java.awt.Dimension;

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
		
		this.setPreferredSize(new Dimension(30,30)); // make any panel have a nice size
	}
	
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	public void setRepresentingSquare(SquareBase representingSquare) {
		this.representingSquare = representingSquare;
	}
	
}
