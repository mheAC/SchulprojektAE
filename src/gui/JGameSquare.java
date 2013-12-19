package gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;

import engine.*;

// TODO: Javadoc kontrollieren
/**
 * The Class JGameSquare.
 */
public class JGameSquare extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The representing square. */
	private SquareBase representingSquare;
	
	/** The posy. */
	private int position, posx, posy;	
	
	/** The bg. */
	private Color bg;
	
	/** The set. */
	private boolean set;
	
	/** The content. */
	private String content = null;
	
	/** The border. */
	private Border border;
	
	/** The text label. */
	private JLabel textLabel;
	
	/**
	 * Instantiates a new j game square.
	 */
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
	
	/**
	 * Instantiates a new j game square.
	 *
	 * @param representingSquare the representing square
	 */
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
	
	/**
	 * Checks if is sets the.
	 *
	 * @return true, if is sets the
	 */
	public boolean isset(){
		return set;
	}
	
	/**
	 * Gets the represented square.
	 *
	 * @return the represented square
	 */
	public SquareBase getRepresentedSquare() {
		return representingSquare;
	}
	
	/**
	 * Sets the representing square.
	 *
	 * @param representingSquare the new representing square
	 */
	public void setRepresentingSquare(SquareBase representingSquare) {
		//set = true;
		this.representingSquare = representingSquare;
	}

	/**
	 * Draw line.
	 *
	 * @param e the e
	 * @param lh the lh
	 */
	public void drawLine(Direction e, LogHandler lh){
		switch(e){
			case VERTICAL:
				lh.setStack(this);
				clearPaint();
				drawVertikal();
			break;
			
			case HORIZONTAL:
				lh.setStack(this);
				clearPaint();
				drawHorizontal();
			break;
			
			default:
			break;
		}
	}
	
	/**
	 * draw - to the panel.
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
	 * draw | to the panel.
	 *
	 * @return the bg
	 */

	public Color getBg() {
		return bg;
	}

	/**
	 * Sets the bg.
	 *
	 * @param bg the new bg
	 */
	public void setBg(Color bg) {
		this.bg = bg;
		this.setBackground(bg);
	}
	
	/**
	 * Draw vertikal.
	 */
	private void drawVertikal() {
		Graphics2D g = (Graphics2D)this.getGraphics();
		Dimension dim = this.getSize();
		g.setStroke(new BasicStroke(5));
		g.drawLine(dim.width/2, 0, dim.width/2, dim.height);
		content = "Vertikal";		
	}
	
	/**
	 * Clear paint.
	 */
	public void clearPaint() {
		Graphics foo = this.getGraphics();
		foo.clearRect(1, 1, this.getSize().height-2, this.getSize().width-2);
	}
	
	/**
	 * Checks if is in row with.
	 *
	 * @param square the square
	 * @return true, if is in row with
	 */
	public boolean isInRowWith(JGameSquare square){
		return this.posy == square.getPosy(); 
	}
	
	/**
	 * Checks if is in coloumn with.
	 *
	 * @param square the square
	 * @return true, if is in coloumn with
	 */
	public boolean isInColoumnWith(JGameSquare square){
		return this.posx == square.getPosx(); 
	}
	
	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Gets the text label.
	 *
	 * @return the text label
	 */
	public JLabel getTextLabel() {
		return textLabel;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText (String text) {
		this.getTextLabel().setText(text);
		content = text;
	}
	
	/**
	 * Gets the posx.
	 *
	 * @return the posx
	 */
	public int getPosx() {
		return posx;
	}

	/**
	 * Sets the posx.
	 *
	 * @param posx the new posx
	 */
	public void setPosx(int posx) {
		this.posx = posx;
	}

	/**
	 * Gets the posy.
	 *
	 * @return the posy
	 */
	public int getPosy() {
		return posy;
	}

	/**
	 * Sets the posy.
	 *
	 * @param posy the new posy
	 */
	public void setPosy(int posy) {
		this.posy = posy;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent(){
		return content;
	}
}