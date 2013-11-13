package engine;

import java.io.*;
import java.util.*;

public class StorageHandler {
	private static String filename;
	private ObjectInputStream o_in;
	private ObjectOutputStream o_out;
	
	public StorageHandler() {
	}

	public static String getFilename() {
		return filename;
	}

	public static void setFilename(String filename) {
		StorageHandler.filename = filename;
	}

	public ObjectInputStream getO_in() {
		return o_in;
	}

	public void setO_in(ObjectInputStream o_in) {
		this.o_in = o_in;
	}

	public ObjectOutputStream getO_out() {
		return o_out;
	}

	public void setO_out(ObjectOutputStream o_out) {
		this.o_out = o_out;
	}

	
	public void saveArrayListToFile(ArrayList<SquareBase> books ) throws FileNotFoundException, IOException
	{
		String filePath = getFilename();
		o_out= new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		o_out.writeObject(books);
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
