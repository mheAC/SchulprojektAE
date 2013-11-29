/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import java.util.Stack;

/**
 *
 *
 */
public class LogHandler {
	Stack<Move> stack1 = new Stack<Move>();
	Stack<Move> stack2 = new Stack<Move>();
	
	
	public void setStack(Move move){
		
		stack1.push(move);
	}
	public Move getBack(){
		Move move;
		move = stack1.pop();
		stack2.push(move);
		return move;
	}
	public Move getForward(){
		Move move;
		move = stack2.pop();
		stack1.push(move);
		return move;
	}
	
}
