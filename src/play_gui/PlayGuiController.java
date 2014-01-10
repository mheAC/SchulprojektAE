package play_gui;


import java.awt.Color;
import java.io.File;
import java.util.EmptyStackException;
import java.util.Iterator;

import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.storage_handler.StorageHandler;
import gui.JGameSquare;

// TODO: Javadoc kontrollieren
/**
 * The Class PlayGuiController.
 */
public class PlayGuiController {
	
	//triggered when new game button is cliked (loades a new game and renderes the game grid
	/**
	 * New game.
	 *
	 * @param mainWindow the main window
	 * @param file the file
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
			mainWindow.showAlert("Spiel konnte nicht geladen werden.");
		}
	}
	
	public static void saveGame(MainWindow mainWindow,File file){
		System.out.println(file);
		StorageHandler storageHandler = new StorageHandler();
		storageHandler.persist(mainWindow.getGameGrid(), file);
	}

	//fired when a grid cell is clicked (activates the cell)
	/**
	 * Grid cell clicked.
	 *
	 * @param cell the cell
	 * @param mainWindow the main window
	 */
	public static void gridCellClicked(JGameSquare cell, MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}else if(mainWindow.hasActiveCell()){
			boolean enlighted = mainWindow.getGameGrid().enlight(((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()), cell.getRepresentedSquare());
			if(enlighted){
				mainWindow.repaintGameGrid();
				mainWindow.getGameGrid().getController().fillStack(mainWindow.getGameGrid());
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
	/**
	 * Grid cell entered.
	 *
	 * @param cell the cell
	 * @param mainWindow the main window
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
	
	public static void stepBack(MainWindow mainWindow){
		GameGrid gg;
		try {
			gg = mainWindow.getGameGrid().getController().undoStack(mainWindow.getGameGrid());
			mainWindow.setGameGrid(gg);
			mainWindow.repaintGameGrid();
			
		} catch (NullPointerException e) {
			System.out.println("Loghandler not filled");
		}
		
	}
	
	//fired when mouse exited a grid cell
	/**
	 * Grid cell exited.
	 *
	 * @param cell the cell
	 * @param mainWindow the main window
	 */
	public static void gridCellExited(JGameSquare cell, MainWindow mainWindow) {
	}
}
