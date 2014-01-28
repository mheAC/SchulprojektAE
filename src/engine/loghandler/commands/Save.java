/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package engine.loghandler.commands;

import engine.GameGrid;
import engine.loghandler.receiver.LogHandler;

/**
 *
 *
 */
public class Save implements Command{
	
	private LogHandler save;
	
	public Save(LogHandler saveobj){
		this.save = saveobj;

	}

	/* (non-Javadoc)
	 * @see engine.loghandler.commands.Command#execute()
	 */
	@Override
	public void execute(GameGrid gg) {
		this.save.setStack(gg);
		
	}

	/* (non-Javadoc)
	 * @see engine.loghandler.commands.Command#undo()
	 */
	@Override
	public GameGrid undo() {
		return this.save.delStack();
	}
	
}
