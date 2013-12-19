/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import gui.JGameSquare;

import java.util.Stack;

// TODO: Javadoc kontrollieren
/**
 * The Class LogHandler.
 */
public class LogHandler {
	
	/** The max number. */
	private Stack<JGameSquare> stack1;
	
	/** The stack2. */
	private Stack<JGameSquare> stack2;
	
	/**
	 * Instantiates a new log handler.
	 */
	public LogHandler() {
		stack1 = new Stack<JGameSquare>();
		stack2 = new Stack<JGameSquare>();
	}
	
	/**
	 * Sets the stack.
	 *
	 * @param gs the new stack
	 */
	public void setStack(JGameSquare gs){
		System.out.println(gs.toString());
		stack1.push(gs);
	}
	
	/**
	 * Gets the back.
	 *
	 * @return the back
	 */
	public JGameSquare getBack(){
		JGameSquare gs;
		gs = stack1.pop();
		stack2.push(gs);
		return gs;
	}
	
	/**
	 * Gets the forward.
	 *
	 * @return the forward
	 */
	public JGameSquare getForward(){
		JGameSquare gs;
		gs = stack2.pop();
		stack1.push(gs);
		return gs;
	}
	
}
