package engine;

import java.io.*;
import java.util.*;

public class StorageHandler {
	private static String filename;
	private ObjectInputStream o_in;
	private ObjectOutputStream o_out;
	
	public StorageHandler() {
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		StorageHandler.filename = filename;
	}

	
	public void saveArrayListToFile(ArrayList<SquareBase> sbase ) throws FileNotFoundException, IOException
	{
		String filePath = getFilename();
		o_out= new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		o_out.writeObject(sbase);
		o_out.close();
	}
	
	public ArrayList<SquareBase> loadArrayListFromFile(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		o_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
	    try
	    {
	        return (ArrayList<SquareBase>) o_in.readObject();
	    }
	    finally
	    {
	    	o_in.close();
	    }
	}
}
