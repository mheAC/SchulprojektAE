package gui;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;

import engine.*;

public class JGameSquare extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SquareBase representingSquare;
	private int position, posx, posy;	
	private Color bg;
	private boolean set;
	private String content;
	private Border border;
	private JLabel textLabel;
	
	public JGameSquare(){
		super();
		set = false;
		bg = this.getBackground();
		setBg(bg);
		border = BorderFactory.createLineBorder(Color.black);
		this.setBorder(border);
		
		this.textLabel = new JLabel();
		
		this.add(this.textLabel);
		
		this.setPreferredSize(new Dimension(30,30)); // make any panel have a nice size
		
	}
	
	public boolean isset(){
		return set;
	}
	
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	public void setRepresentingSquare(SquareBase representingSquare) {
		//set = true;
		this.representingSquare = representingSquare;
	}

	public void drawLine(Direction e){
		switch(e){
			case VERTICAL:
				clearPaint();
				drawVertikal();
			break;
			
			case HORIZONTAL:
				clearPaint();
				drawHorizontal();
			break;
			
			default:
			break;
		}
	}
	
	/**
	 * draw - to the panel
	 */
	private void drawHorizontal() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, dim.height/2, dim.width, dim.height/2);
		content = "Horizontal";
		//g.drawLine(6, dim.height/2, dim.width-6, dim.height/2); // draw a stroke that ends before the border
	}
	
	/**
	 * draw | to the panel
	 */

	public Color getBg() {
		return bg;
	}

	public void setBg(Color bg) {
		this.bg = bg;
		this.setBackground(bg);
	}
	
	private void drawVertikal() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(dim.width/2, 0, dim.width/2, dim.height);
		content = "Vertikal";		
	}
	
	public void clearPaint() {
		Graphics foo = this.getGraphics();
		foo.clearRect(1, 1, this.getSize().height-2, this.getSize().width-2);
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public JLabel getTextLabel() {
		return textLabel;
	}
	
	public void setText (String text) {
		this.getTextLabel().setText(text);
		content = text;
	}
	
	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}
}
