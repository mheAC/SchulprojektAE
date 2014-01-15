package loghandler2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import engine.GameGrid;

public class Loghandler {
	private ArrayList<GameGrid> steps;
	
	public Loghandler(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.steps = new ArrayList<GameGrid>();
		this.steps.add(gameGrid.copy());
	}
	
	public void log(GameGrid gameGrid) throws FileNotFoundException, IOException, ClassNotFoundException{
		this.steps.add(gameGrid.copy());
	}
	
	public GameGrid back(){
		GameGrid last = this.steps.get(this.steps.size()-1);
		this.steps.remove(this.steps.size()-1);
		return last;
	}
}
