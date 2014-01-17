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
	
	//triggered when new game button is cliked (loades a new game and renderes the game grid

	public static void newGame(MainWindow mainWindow,File file){
		StorageHandler storageHandler = new StorageHandler();
		try {
			GameGrid gameGrid = storageHandler.load(file);
			boolean removeRays = true;
			if(gameGrid.runningGame)
				removeRays = false;
			
			if(gameGrid.getSquares().size()==0)
				throw new Exception();
			if(!mainWindow.setGameGrid(gameGrid,removeRays)){
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid,removeRays);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mainWindow.showAlert("Spiel konnte nicht geladen werden.");
		}
	}
	
	public static void saveGame(MainWindow mainWindow,File file){
		GameGrid gameGrid = mainWindow.getGameGrid();
		gameGrid.runningGame = true;
		StorageHandler storageHandler = new StorageHandler();
		storageHandler.persist(mainWindow.getGameGrid(), file);
	}

	//fired when a grid cell is clicked (activates the cell)

	public static void gridCellClicked(JGameSquare cell, MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}else if(mainWindow.hasActiveCell()){
			boolean enlighted = mainWindow.getGameGrid().enlight(((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()), cell.getRepresentedSquare());
			if(enlighted){
				mainWindow.repaintGameGrid();
				try {
					mainWindow.getLoghandler().log(mainWindow.getGameGrid());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	
	public static void gridCellRightClicked(JGameSquare cell, MainWindow mainWindow){
		if(cell.getRepresentedSquare().isRaySquare() && ((RaySquare) cell.getRepresentedSquare()).getLightSource() == mainWindow.getActiveCell().getRepresentedSquare()){
			mainWindow.getGameGrid().unenlight((RaySquare) cell.getRepresentedSquare());
			mainWindow.repaintGameGrid();
		}
	}

	//fired when mouse enters a grid cell

	public static void gridCellEntered(JGameSquare cell, MainWindow mainWindow) {
		if(mainWindow.hasActiveCell()){
			mainWindow.clearHover();
			Iterator<JGameSquare> squaresIterator = mainWindow.getUntypedCellsToActive(cell).iterator();
			while(squaresIterator.hasNext()){
				squaresIterator.next().setBackground(Color.LIGHT_GRAY);
			}
		}
	}
	
	public static void stepBack(MainWindow mainWindow){
		try {
			GameGrid oldGrid = mainWindow.getLoghandler().back();
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(oldGrid, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//fired when mouse exited a grid cell

	public static void gridCellExited(JGameSquare cell, MainWindow mainWindow) {
	}
}
