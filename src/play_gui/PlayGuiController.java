package play_gui;


import java.awt.Color;
import java.io.File;
import java.util.Iterator;

import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.storage_handler.StorageHandler;
import gui.JGameSquare;


public class PlayGuiController {
	
	/*
	 * Author: Andreas Soiron
	 * triggered when new game button is cliked (loades a new game and renderes the game grid)
	 */
	public static void newGame(MainWindow mainWindow,File file){
		StorageHandler storageHandler = new StorageHandler();
		try {
			GameGrid gameGrid = storageHandler.load(file);
			
			if(gameGrid.getSquares().size()==0)
				throw new Exception();
			if(!mainWindow.setGameGrid(gameGrid)){
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mainWindow.showAlert("Spiel konnte nicht geladen werden.");
		}
	}
	
	/*
	 * Author: Andreas Soiron
	 * triggered when save game button is cliked (saves the game to a file)
	 */
	public static void saveGame(MainWindow mainWindow,File file){
		GameGrid gameGrid = mainWindow.getGameGrid();
		gameGrid.runningGame = true;
		StorageHandler storageHandler = new StorageHandler();
		storageHandler.persist(mainWindow.getGameGrid(), file);
	}


	/*
	 * Author: Andreas Soiron
	 * fired when a grid cell is clicked (activates the cell or draws a line)
	 */
	public static void gridCellClicked(JGameSquare cell, MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}else if(mainWindow.hasActiveCell()){
			boolean enlighted = mainWindow.getGameGrid().enlight(((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()), cell.getRepresentedSquare());
			if(enlighted){
				mainWindow.repaintGameGrid();
				try {
					mainWindow.getGameGrid().log();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(mainWindow.getGameGrid().getUntypedSquares().size()==0){
					mainWindow.showAlert("Sie haben das Spiel gelöst!");
				}
			}else if(square.isRaySquare()){
				//doesent work :(
				//NumberSquare lightSource = ((RaySquare) square).getLightSource();
				//mainWindow.setActiveCell(mainWindow.getCellByPosition(lightSource.getPositionX(), lightSource.getPositionY()));
				//gridCellEntered(cell,mainWindow);
			}else{
				mainWindow.releaseActiveCell();
			}
		}
	}
	
	/*
	 * Author: Andreas Soiron
	 * fired when a grid cell is right clicked (remves rays)
	 */
	public static void gridCellRightClicked(JGameSquare cell, MainWindow mainWindow){
		if(cell.getRepresentedSquare().isRaySquare() && ((RaySquare) cell.getRepresentedSquare()).getLightSource() == mainWindow.getActiveCell().getRepresentedSquare()){
			mainWindow.getGameGrid().unenlight((RaySquare) cell.getRepresentedSquare());
			mainWindow.repaintGameGrid();
		}
	}

	
	/*
	 * Author: Andreas Soiron
	 * fired when mouse enters a grid cell
	 */
	public static void gridCellEntered(JGameSquare cell, MainWindow mainWindow) {
		if(mainWindow.hasActiveCell()){
			mainWindow.clearHover();
			Iterator<JGameSquare> squaresIterator = mainWindow.getUntypedCellsToActive(cell).iterator();
			while(squaresIterator.hasNext()){
				squaresIterator.next().setBackground(Color.LIGHT_GRAY);
			}
		}
	}
	
	/*
	 * Author: Andreas Soiron
	 * fired when the back button ist clicked, loads the last grid and sets it to the view
	 */
	public static void stepBack(MainWindow mainWindow){
		try {
			GameGrid oldGrid = mainWindow.getGameGrid().stepBack();
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(oldGrid);
		} catch (NullPointerException e){
			System.out.println("reset to play mode");
			//If there is no step back reset the Grid
			GameGrid oldGrid = mainWindow.getGameGrid();
			oldGrid.resetToPlaymode();
			mainWindow.clearGameGrid();
			try {
				mainWindow.setGameGrid(oldGrid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Author: Andreas Soiron
	 * fired when mouse exited a grid cell (does nothing)
	 */
	public static void gridCellExited(JGameSquare cell, MainWindow mainWindow) {
	}
	
	/*
	 * Author: Andreas Soiron
	 * fired when track button is clicked
	 */
	public static void savepoint( MainWindow mainWindow) {
		try{
			mainWindow.savepoint();
		}catch(Exception e){
			mainWindow.showAlert("Savepoint konnte nicht gesetzt oder geladen werden");
		}
	}
}
