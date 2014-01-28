/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine.loghandler.commands;

import engine.GameGrid;

/**
 *
 *
 */
public class NoCommand implements Command{

	/* (non-Javadoc)
	 * @see engine.loghandler.commands.Command#execute()
	 */
	@Override
	public void execute(GameGrid gg) {
		System.out.println("Kein Command gewählt.");
	}

	/* (non-Javadoc)
	 * @see engine.loghandler.commands.Command#undo()
	 */
	@Override
	public GameGrid undo() {
		System.out.println("Kein Command gewählt.");
		return null;
	}
	
}
