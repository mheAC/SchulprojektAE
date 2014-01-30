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

// TODO: Javadoc kontrollieren
/**
 * The Class StorageHandler.
 */
public class StorageHandler {
	
	/** The use json. */
	private boolean useJson;
	
	/**
	 * Instantiates a new storage handler.
	 */
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


	public void persist(GameGrid gGrid, String filePath ) throws FileNotFoundException, IOException {
		if(filePath.lastIndexOf(".ysams") + 1 <= 0)
			filePath = filePath+".ysams";
		if(useJson){
			Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

		        public boolean shouldSkipClass(Class<?> clazz) {
		            return false;//clazz == LogHandler.class;
		        }

		        /**
		          * Custom field exclusion goes here
		          */
		        public boolean shouldSkipField(FieldAttributes f) {
		            return (f.getName().equals("lightSource") || f.getName().equals("englighted_squares"));
		        }

		     }).serializeNulls()
		     .create();
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
	 * Load data from file.
	 *
	 * @param filePath the file path
	 * @return the game grid
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
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
	       
	        if(!gameGrid.runningGame){
	        	gameGrid.resetToPlaymode();
	        }
	        gameGrid.restoreConsistence();
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
	
	public void delete(String filePath) throws IOException, ClassNotFoundException{
		File file = new File(filePath);
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * Just delegates the call..
	 *
	 * @param filePath the file path
	 * @return the game grid
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public GameGrid load(File filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		if (filePath != null) {
			return this.load(filePath.getAbsolutePath());
		} else {
			// Could not load, dialog was probably canceled
			return null;
		}
	}
	
	/**
	 * This method just delegates to saveArrayListToFile(ArrayList<SquareBase> sbase, String filePath ).
	 *
	 * @param gGrid ArrayList with SquareBase Objects
	 * @param filePath the path to the File to write to
	 */
	public void persist(GameGrid gGrid, File filePath ) {
		if (filePath != null) {
			try {
				this.persist(gGrid, filePath.getAbsolutePath());
			} catch (FileNotFoundException e) { e.printStackTrace();
			} catch (IOException e) { e.printStackTrace(); }
		} else {
			// Did not save, selection was probably canceled.
		}
	}
	
	
}
