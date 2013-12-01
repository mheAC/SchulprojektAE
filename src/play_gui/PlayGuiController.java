package play_gui;


import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import engine.Direction;
import engine.GameGrid;
import engine.SquareBase;
import engine.storage_handler.StorageHandler;
import gui.JGameSquare;

public class PlayGuiController {
	
	//triggered when new game button is cliked (loades a new game and renderes the game grid
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
	public static void gridCellClicked(JGameSquare cell, MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}else if(mainWindow.hasActiveCell()){
			ArrayList<JGameSquare> cells = mainWindow.getUntypedCellsToActive(cell);
			if(cells.isEmpty()){
				mainWindow.releaseActiveCell();
			}else{
				Iterator<JGameSquare> cellsIterator = cells.iterator();
				while(cellsIterator.hasNext()){
					JGameSquare tmpCell = cellsIterator.next();
					if(cell.isInColoumnWith(mainWindow.getActiveCell()))
						tmpCell.drawLine(Direction.VERTICAL, mainWindow.getGameGrid().getLoghandler());
					else if(cell.isInRowWith(mainWindow.getActiveCell()))
						tmpCell.drawLine(Direction.HORIZONTAL, mainWindow.getGameGrid().getLoghandler());
				}	
			}
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
	
	//fired when mouse exited a grid cell
	public static void gridCellExited(JGameSquare cell, MainWindow mainWindow) {
	}
}
