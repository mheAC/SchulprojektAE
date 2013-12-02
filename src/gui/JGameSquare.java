package gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import engine.*;

public class JGameSquare extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private SquareBase representingSquare;
	private int position, posx, posy;	
	private String content = null;
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
