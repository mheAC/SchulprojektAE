/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import gui.JGameSquare;

import java.awt.Event;
import java.util.Stack;

/**
 *
 *
 */
public class LogHandler {
	Stack<JGameSquare> stack = new Stack<JGameSquare>();
	
	
	public void setHistory(JGameSquare gs){
		stack.push(gs);
	}
	public JGameSquare getHistory(){
		JGameSquare gs;
		gs = stack.pop();
		return gs;
	}
	
}
