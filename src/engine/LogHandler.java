/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import java.util.Stack;

// TODO: Javadoc kontrollieren
/**
 * The Class LogHandler.
 */
public class LogHandler {
	
	/** The max number. */
	private Stack<GameGrid> stack1;
	
	/** The stack2. */
	private Stack<GameGrid> stack2;
	
	/**
	 * Instantiates a new log handler.
	 */
	public LogHandler() {
		stack1 = new Stack<GameGrid>();
		stack2 = new Stack<GameGrid>();
	}
	
	/**
	 * Sets the stack.
	 *
	 * @param gs the new stack
	 */
	public void setStack(GameGrid gs){
		
		stack1.push(gs);
	}
	
	/**
	 * Gets the back.
	 *
	 * @return the back
	 */
	public GameGrid getBack(){
		GameGrid gs;
		gs = stack1.pop();
		stack2.push(gs);
		return gs;
	}
	
	/**
	 * Gets the forward.
	 *
	 * @return the forward
	 */
	public GameGrid getForward(){
		GameGrid gs;
		gs = stack2.pop();
		stack1.push(gs);
		return gs;
	}
	
}
