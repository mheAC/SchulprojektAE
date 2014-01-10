/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine.loghandler.invoker;

import java.util.Stack;

import engine.GameGrid;
import engine.loghandler.commands.Command;
import engine.loghandler.commands.NoCommand;
import engine.loghandler.commands.Save;

/**
 *
 *
 */
public class Controller {
	private Command[] commands = new Command[1];
	
	public void setCommands(Command cm) {
		commands[1] = cm;
	}
	
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
	public GameGrid undoStack(GameGrid gg){
		System.out.println("Loghandler gg del");
		gg = this.commands[1].undo(gg);
		return gg;
	}
	

}
