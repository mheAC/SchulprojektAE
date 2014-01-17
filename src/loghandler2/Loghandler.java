package loghandler2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import engine.GameGrid;
import engine.storage_handler.StorageHandler;

public class Loghandler {
	private ArrayList<Long> steps;
	
	public Loghandler(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.steps = new ArrayList<Long>();
		this.log(gameGrid);
	}
	
	public void log(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		Date now = new Date();  	
		Long longTime = new Long(now.getTime()/1000);
		StorageHandler storageHandler = new StorageHandler();
		gameGrid.runningGame = true;
		storageHandler.persist(gameGrid, "tmp"+System.getProperty("file.separator")+longTime);
		this.steps.add(longTime);
	}
	
	public GameGrid back(){
		StorageHandler storageHandler = new StorageHandler();
		GameGrid last = null;
		try {
			last = storageHandler.load("tmp"+System.getProperty("file.separator")+this.steps.get(this.steps.size()-1)+".ysams");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.steps.remove(this.steps.size()-1);
		return last;
	}
}
