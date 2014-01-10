/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine.loghandler.receiver;

import java.util.Stack;

import org.omg.CORBA.PRIVATE_MEMBER;

import engine.GameGrid;

// TODO: Javadoc kontrollieren
/**
 * The Class LogHandler.
 */
public class LogHandler {
	
	/** The max number. */
	private Stack<GameGrid> stack1;
	
	/** The stack2. */
//	private Stack<GameGrid> stack2;
	
	/**
	 * Instantiates a new log handler.
	 */
	public LogHandler() {
		stack1 = new Stack<GameGrid>();
//		stack2 = new Stack<GameGrid>();
	}
	
	/**
	 * Sets the stack.
	 *
	 * @param gs the new stack
	 */
	public void setStack(GameGrid gg){
		
		stack1.push(gg);
	}
	public GameGrid delStack(GameGrid gg){
		GameGrid ggrid;
		ggrid = stack1.pop();
		return ggrid;
	}
	
//	/**
//	 * Gets the back.
//	 *
//	 * @return the back
//	 */
//	public GameGrid getBack(){
//		GameGrid gg;
//		gg = stack1.pop();
//		stack2.push(gg);
//		return gg;
//	}
//	
//	/**
//	 * Gets the forward.
//	 *
//	 * @return the forward
//	 */
//	public GameGrid getForward(){
//		GameGrid gg;
//		gg = stack2.pop();
//		stack1.push(gg);
//		return gg;
//	}
//	
}
