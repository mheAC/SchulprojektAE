package engine.storage_handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.*;

import engine.GameGrid;
import engine.SquareBase;

public class StorageHandler {
	
	private boolean useJson;
	
	public StorageHandler(){
		Properties properties = new Properties();
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream("config.cfg"));
			properties.load(stream);
			if(properties.getProperty("saveMethodJson").equals("true")){
				this.useJson = true;
			}else{
				this.useJson = false;
			}
		} catch (IOException e) {
			this.useJson = true;
		}
		
	}

	/**
	 * Persist data to file
	 * @param gGrid
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void persist(GameGrid gGrid, String filePath ) throws FileNotFoundException, IOException {
		if(useJson){
			Gson gson = new Gson();
			PrintWriter writer = new PrintWriter(filePath, "UTF-8");
			writer.println(gson.toJson(gGrid));
			writer.close();
		}else{
            ObjectOutputStream o_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			o_out.writeObject(gGrid);
			o_out.flush();
			o_out.close();
		}
	}
	
	/**
	 * Load data from file
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GameGrid load(String filePath) throws IOException, ClassNotFoundException {

		if(useJson){
			Scanner scanner = new Scanner(new FileReader(filePath));
			String jsonDump = "";
	        while (scanner.hasNextLine()) {
	        	jsonDump += scanner.nextLine();
	        }
	        scanner.close();
	        Gson gson = new GsonBuilder().registerTypeAdapter(SquareBase.class, new SquareBaseDeserializer()).create();
	        GameGrid gameGrid = gson.fromJson(jsonDump, GameGrid.class);
	        
	        return gameGrid;
		}else{
			ObjectInputStream o_in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		    try {
		    	GameGrid gameGrid = (GameGrid) o_in.readObject();
		        return gameGrid;
		    }
		    finally {
		    	o_in.close();
		    }
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
