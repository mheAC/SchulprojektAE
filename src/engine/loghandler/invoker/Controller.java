/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine.loghandler.invoker;

import java.util.Stack;

import engine.GameGrid;
import engine.loghandler.commands.Command;
import engine.loghandler.commands.NoCommand;

/**
 *
 *
 */
public class Controller {
	private Command[] commands = new Command[1];
	
	private Stack<Command> undo = new Stack<Command>();
	
	public Controller() {
		for(Command c: commands){
			c = new NoCommand();
		}
	}
	public void fillStack(GameGrid gg){
		System.out.println("Loghandler gg added");
		this.commands[1].execute(gg);
	}
	public void undoStack(GameGrid gg){
		System.out.println("Loghandler gg del");
		this.commands[1].undo(gg);
	}
	

}
