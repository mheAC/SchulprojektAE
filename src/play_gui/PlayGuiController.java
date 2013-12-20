package play_gui;


import java.awt.Color;
import java.io.File;
import java.util.Iterator;

import engine.GameGrid;
import engine.NumberSquare;
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
			if(!mainWindow.setGameGrid(gameGrid)){
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid);
				}
			}
		} catch (NullPointerException e){
			
		} catch (Exception e) {
			//mainWindow.showAlert("Spiel konnte nicht geladen werden.");
			e.printStackTrace();
		}
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
			mainWindow.getGameGrid().enlight(((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()), cell.getRepresentedSquare());
			mainWindow.getGameGrid().log();
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
