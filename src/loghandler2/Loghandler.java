package loghandler2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import engine.GameGrid;
import engine.storage_handler.StorageHandler;

public class Loghandler {
	private ArrayList<Long> steps;
	long logZeit =0;
	
	/*
	 * Author: Andreas Soiron
	 * initializes a log handler object for the given gamegrid
	 */
	public Loghandler(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.steps = new ArrayList<Long>();
		this.log(gameGrid);
	}
	
	/*
	 * Author: Andreas Soiron
	 * initializes a loghandler with a list of steps
	 */
	public Loghandler(ArrayList<Long> steps) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.steps = steps;
	}
	
	/*
	 * Author: Andreas Soiron
	 * logs the current state of the gamegrid
	 */
	public void log(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		Date now = new Date();  	
		Long longTime = new Long(now.getTime()/1000);
		logZeit = longTime;
		StorageHandler storageHandler = new StorageHandler();
		gameGrid.runningGame = true;
		File dir = new File("tmp");
		dir.mkdir();
		storageHandler.persist(gameGrid, "tmp"+System.getProperty("file.separator")+longTime);
		this.steps.add(longTime);
		
		
	}
	
	public long logTime(){
		return logZeit;
	}
	
	
	/*
	 * Author: Andreas Soiron
	 * returns the last gamegrid and removes the current one from the stack
	 */
	public GameGrid back(){
		StorageHandler storageHandler = new StorageHandler();
		GameGrid last = null;
		try {
			last = storageHandler.load("tmp"+System.getProperty("file.separator")+this.steps.get(this.steps.size()-1)+".ysams");
			storageHandler.delete("tmp"+System.getProperty("file.separator")+this.steps.get(this.steps.size()-1)+".ysams");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.steps.remove(this.steps.size()-1);
		return last;
		
	}
	
	
}
