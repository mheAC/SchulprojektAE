package engine;

import java.io.*;
import java.util.*;

public class StorageHandler {

	/**
	 * This method just delegates to saveArrayListToFile(ArrayList<SquareBase> sbase, String filePath )
	 * @param gGrid ArrayList with SquareBase Objects
	 * @param filePath the path to the File to write to
	 */
	public void saveArrayListToFile(GameGrid gGrid, File filePath ) {
		try {
			this.saveArrayListToFile(gGrid, filePath.getAbsolutePath());
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void saveArrayListToFile(GameGrid gGrid, String filePath ) throws FileNotFoundException, IOException {
		ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		o_out.writeObject(gGrid);
		o_out.close();
	}
	
	@SuppressWarnings("unchecked")
	public GameGrid loadArrayListFromFile(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream o_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
	    try {
	        return (GameGrid) o_in.readObject();
	    }
	    finally {
	    	o_in.close();
	    }
	}
	
	public GameGrid loadArrayListFromFile(File filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		return this.loadArrayListFromFile(filePath.getAbsolutePath());
	}
}
