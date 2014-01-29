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
	private Command commands;
	
	public void setCommands(Command cm) {
		commands = cm;
	}
	
	private Stack<Command> undo = new Stack<Command>();
	
	public Controller() {
		
	}
	public void fillStack(GameGrid gg){
		System.out.println("Loghandler gg added");
		commands.execute(gg);
		System.out.println(gg.toString());
		System.out.println(commands.toString());
	}
	public GameGrid undoStack(GameGrid gg){
		System.out.println("Loghandler gg del");
		gg = this.commands.undo();
		return gg;
	}
	

}
