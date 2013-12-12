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
	private String content = null;
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
	
	public JGameSquare(SquareBase representingSquare){
		this.representingSquare = representingSquare;
		this.posx = this.representingSquare.getPositionX();
		this.posy = this.representingSquare.getPositionY();

		set = false;
		bg = this.getBackground();
		setBg(bg);
		border = BorderFactory.createLineBorder(Color.black);
		this.setBorder(border);
		
		this.textLabel = new JLabel();
		
		this.add(this.textLabel);
		
		this.setPreferredSize(new Dimension(30,30)); // make any panel have a nice size
		
		if(this.getClass().equals(new NumberSquare(0,0).getClass()))
			this.getTextLabel().setText(representingSquare.getPrintableValue()); // For numbers are not drawn, we are able to set them here...
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

	public void drawLine(Direction e, LogHandler lh){
		switch(e){
			case VERTICAL:
				lh.setStack(this);
				//clearPaint();
				drawVertikal();
			break;
			
			case HORIZONTAL:
				lh.setStack(this);
				//clearPaint();
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
	
	public boolean isInRowWith(JGameSquare square){
		return this.posy == square.getPosy(); 
	}
	
	public boolean isInColoumnWith(JGameSquare square){
		return this.posx == square.getPosx(); 
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
	public String getContent(){
		return content;
	}
}
