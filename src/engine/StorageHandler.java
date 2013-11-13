package engine;

import java.io.*;
import java.util.*;

public class StorageHandler {

	/**
	 * This method just delegates to saveArrayListToFile(ArrayList<SquareBase> sbase, String filePath )
	 * @param sbase ArrayList with SquareBase Objects
	 * @param filePath the path to the File to write to
	 */
	public void saveArrayListToFile(ArrayList<SquareBase> sbase, File filePath ) {
		try {
			this.saveArrayListToFile(sbase, filePath.getAbsolutePath());
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void saveArrayListToFile(ArrayList<SquareBase> sbase, String filePath ) throws FileNotFoundException, IOException {
		ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		o_out.writeObject(sbase);
		o_out.close();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SquareBase> loadArrayListFromFile(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream o_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
	    try {
	        return (ArrayList<SquareBase>) o_in.readObject();
	    }
	    finally {
	    	o_in.close();
	    }
	}
	
	public ArrayList<SquareBase> loadArrayListFromFile(File filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		return this.loadArrayListFromFile(filePath.getAbsolutePath());
	}
}
