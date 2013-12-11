/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine;

import gui.JGameSquare;

import java.util.Stack;

/**
 *
 *
 */
public class LogHandler {
	private Stack<JGameSquare> stack1;
	private Stack<JGameSquare> stack2;
	
	public LogHandler() {
		stack1 = new Stack<JGameSquare>();
		stack2 = new Stack<JGameSquare>();
	}
	
	public void setStack(JGameSquare gs){
		System.out.println(gs.toString());
		stack1.push(gs);
	}
	public JGameSquare getBack(){
		JGameSquare gs;
		gs = stack1.pop();
		stack2.push(gs);
		return gs;
	}
	public JGameSquare getForward(){
		JGameSquare gs;
		gs = stack2.pop();
		stack1.push(gs);
		return gs;
	}
	
}
