/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import gui.JGameSquare;

import java.awt.Color;

/**
 *
 *
 */
public class Move {
	private int posx;
	private int posy;
	private String content;
	private Color bg;
	
	private LogHandler lh = new LogHandler();
	
	public void setHistory(JGameSquare gs){
		Move move = new Move();
		posx = gs.getPosx();
		posy = gs.getPosy();
		bg = gs.getBg();
		content = gs.getContent();
		lh.setStack(move);
	}
}
