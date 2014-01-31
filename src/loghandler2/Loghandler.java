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
	private String savePoint;
	
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
		saveLog(String.valueOf(longTime),gameGrid);
		this.steps.add(longTime);		
	}
	
	/*
	 * Author: Andreas Soiron
	 * saves a log with the given name
	 */
	public void saveLog(String name, GameGrid gameGrid) throws FileNotFoundException, IOException{
		StorageHandler storageHandler = new StorageHandler();
		gameGrid.runningGame = true;
		File dir = new File("tmp");
		dir.mkdir();
		storageHandler.persist(gameGrid, "tmp"+System.getProperty("file.separator")+name);
	}
	
	public long logTime(){
		return logZeit;
	}
	
	
	/*
	 * Author: Andreas Soiron
	 * returns the last gamegrid and removes the current one from the stack
	 */
	public GameGrid back(){
		GameGrid last = null;
		try {
			last = loadLog(String.valueOf(this.steps.get(this.steps.size()-1)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.steps.remove(this.steps.size()-1);
		return last;
		
	}
	
	/*
	 * Author: Andreas Soiron
	 * loads a log by its name
	 */
	public GameGrid loadLog(String name) throws IOException, ClassNotFoundException{
		StorageHandler storageHandler = new StorageHandler();
		GameGrid gameGrid = storageHandler.load("tmp"+System.getProperty("file.separator")+name+".ysams");
		storageHandler.delete("tmp"+System.getProperty("file.separator")+name+".ysams");
		return gameGrid;
	}
	
	/*
	 * Author: Andreas Soiron
	 * sets a savepoint
	 */
	public void setSavePoint(GameGrid gameGrid) throws FileNotFoundException, IOException{
		Date now = new Date();
		Long longTime = new Long(now.getTime()/1000);
		this.savePoint = String.valueOf(longTime)+"savepoint";
		saveLog(String.valueOf(longTime)+"savepoint",gameGrid);
	}
	
	/*
	 * Author: Andreas Soiron
	 * loads a savepoint
	 */
	public GameGrid loadSavePoint(){
		GameGrid gameGrid = null;
		if(this.savePoint != null){
			try {
				gameGrid = loadLog(this.savePoint);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.savePoint = null;
		return gameGrid;
	}
	
	/*
	 * Author: Andreas Soiron
	 * loads a savepoint
	 */
	public boolean hasSavePoint(){
		return this.savePoint != null;
	}
	
	
}
