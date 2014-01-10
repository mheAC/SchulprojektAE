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
public interface Command {
	public void execute(GameGrid gg);
	public GameGrid undo(GameGrid gg);
}
