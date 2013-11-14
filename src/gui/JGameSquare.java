package gui;
//gamequare.java
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import engine.*;

public class JGameSquare extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SquareBase representingSquare;
	private int position;
	
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

	public void drawLine(Direction e){
		switch(e){
			case VERTICAL:
				clear(this);
				drawVertikal(this);
			break;
			
			case HORIZONTAL:
				clear(this);
				drawHorizontal(this);
			break;
			
			case UNSET:
				clear(this);
			break;
		}
	}
	
	/**
	 * draw - to the panel
	 * @param gs
	 */
	private void drawHorizontal(JGameSquare gs) {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, dim.height/2, dim.width, dim.height/2);
		//g.drawLine(6, dim.height/2, dim.width-6, dim.height/2); // draw a stroke that ends before the border
	}
	
	/**
	 * draw | to the panel
	 * @param gs
	 */
	private void drawVertikal(JGameSquare gs) {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(dim.width/2 ,0 , dim.width/2, dim.height);
	}
	
	private void clear(JGameSquare gs) {
		gs.getGraphics().clearRect(1, 1, this.getSize().height-2, this.getSize().width-2);
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
}
