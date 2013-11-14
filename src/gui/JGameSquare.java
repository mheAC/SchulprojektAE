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
	private Dimension dim;
	
	private Border border;
	
	public JGameSquare(){
		super();
		border = BorderFactory.createLineBorder(Color.black);
		dim = getDimension();
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
			drawVertikal(this);
			break;
		case HORIZONTAL:
			drawHorizontal(this);
			break;
		}
	}
	
	private Dimension getDimension(){
		if(this.dim == null)
			this.dim = new Dimension(30,30);
		
		return this.dim;
	}
	
	public void setDimension(Dimension di){
		this.dim = di;
	}
	
	private void drawVertikal(JGameSquare gs){
		Graphics2D g = (Graphics2D)this.getGraphics();
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, 15, 30, 15);
	}
	
	private void drawHorizontal(JGameSquare gs){
		Graphics2D g = (Graphics2D)this.getGraphics();
		g.setStroke(new BasicStroke(5));
		g.drawLine(15,0,15,30);
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
}
