package play_gui;


import java.io.File;

import engine.GameGrid;
import engine.SquareBase;
import engine.StorageHandler;
import gui.JGameSquare;

public class PlayGuiController {
	
	//triggered when new game button is cliked (loades a new game and renderes the game grid
	public static void newGame(MainWindow mainWindow,File file){
		StorageHandler storageHandler = new StorageHandler();
		try {
			GameGrid gameGrid = storageHandler.load(file);
			if(!mainWindow.setGameGrid(gameGrid)){
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen (y/n)")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid);
				}
			}
		} catch (Exception e) {
			mainWindow.showAlert("Spiel konnte nicht geladen werden. ("+e+")");
		}
	}

	//fired when a grid cell is clicked (activates the cell)
	public static void gridCellClicked(JGameSquare cell, MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}
	}

	//fired when mouse enters a grid cell
	public static void gridCellEntered(JGameSquare cell, MainWindow mainWindow) {
		/*
		SquareBase sqaure = cell.getRepresentedSquare();
		if(mainWindow.hasActiveCell() && ((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()).canEnlight(sqaure)){
			cell.setBackground(Color.LIGHT_GRAY);
		}
		*/
	}
}
