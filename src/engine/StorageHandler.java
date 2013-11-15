package engine;

import java.io.*;

public class StorageHandler {

	/**
	 * Persist data to file
	 * @param gGrid
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void persist(GameGrid gGrid, String filePath ) throws FileNotFoundException, IOException {
		ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		o_out.writeObject(gGrid);
		o_out.flush();
		o_out.close();
	}
	
	/**
	 * Load data from file
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GameGrid load(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream o_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
	    try {
	        return (GameGrid) o_in.readObject();
	    }
	    finally {
	    	o_in.close();
	    }
	}
	
	/**
	 * Just delegates the call..
	 */
	public GameGrid load(File filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		return this.load(filePath.getAbsolutePath());
	}
	
	/**
	 * This method just delegates to saveArrayListToFile(ArrayList<SquareBase> sbase, String filePath )
	 * @param gGrid ArrayList with SquareBase Objects
	 * @param filePath the path to the File to write to
	 */
	public void persist(GameGrid gGrid, File filePath ) {
		try {
			this.persist(gGrid, filePath.getAbsolutePath());
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	
}
