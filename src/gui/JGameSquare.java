package gui;

import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;

import javax.swing.*;
import javax.swing.border.Border;

import engine.*;

public class JGameSquare extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SquareBase representingSquare;
	private int position, posx, posy;	
	private String content = null;
	private Border border;
	private JLabel textLabel;
	
	public JGameSquare(){
		super();

		border = BorderFactory.createLineBorder(Color.black);
		this.setBorder(border);
		
		this.textLabel = new JLabel();
		
		this.add(this.textLabel);
		
		this.setPreferredSize(new Dimension(30,30)); // make any panel have a nice size
		
	}
	
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	public void setRepresentingSquare(SquareBase representingSquare) {
		//set = true;
		this.representingSquare = representingSquare;
	}

	/*public void drawLine(Direction e){
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
	}*/
	
	/**
	 * draw - to the panel
	 */
	/*private void drawHorizontal() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, dim.height/2, dim.width, dim.height/2);
		content = "Horizontal";
		//g.drawLine(6, dim.height/2, dim.width-6, dim.height/2); // draw a stroke that ends before the border
	}*/
	
	/**
	 * draw | to the panel
	 */
	/*private void drawVertikal() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(dim.width/2, 0, dim.width/2, dim.height);
		content = "Vertikal";		
	}*/
	
	/**
	 * !!
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		SquareBase s = getRepresentedSquare();
		
		Dimension dim = this.getSize(); // the panel sizes
		
		if(s.isNumberSquare()) {			
			FontMetrics fm =  g.getFontMetrics();
			int width = fm.stringWidth(getRepresentedSquare().toString())/2;
			int height = 13 /2;

			g.setFont(new Font("default", Font.BOLD, 16));
			g.drawString( getRepresentedSquare().toString(), dim.width/2 - width, dim.height/2+height);
		}
		else if(s.isRaySquare()) {
			RaySquare rs = (RaySquare)s;
			if(rs.getDirection() == Direction.HORIZONTAL)
				g.drawLine(0, dim.height/2, dim.width, dim.height/2);
			else
				g.drawLine(dim.width/2, 0, dim.width/2, dim.height);
		}
	}
	
	/*public void clearPaint() {
		Graphics foo = this.getGraphics();
		foo.clearRect(1, 1, this.getSize().height-2, this.getSize().width-2);
	}*/
	
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
