package play_gui;


import java.awt.Color;
import java.io.File;

import engine.GameGrid;
import engine.NumberSquare;
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
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid);
				}
			}
		} catch (NullPointerException e){
			
		} catch (Exception e) {
			mainWindow.showAlert("Spiel konnte nicht geladen werden.");
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
		
		if(mainWindow.hasActiveCell()){
			JGameSquare activeCell = mainWindow.getActiveCell();
			NumberSquare activeSquare = (NumberSquare) activeCell.getRepresentedSquare();
			mainWindow.clearHover();
			if(cell.isInRowWith(activeCell)){
				JGameSquare tempCell = cell;
				
				//hover all cells on the right side
				while(tempCell.getPosx()>activeCell.getPosx()){
					if(activeSquare.canEnlight(tempCell.getRepresentedSquare())){
						tempCell.setBackground(Color.LIGHT_GRAY);
					}
					tempCell = mainWindow.getCellByPosition(tempCell.getPosx()-1,tempCell.getPosy());
				}
				
				//hover all cells on the left side
				while(tempCell.getPosx()<activeCell.getPosx()){
					if(activeSquare.canEnlight(tempCell.getRepresentedSquare())){
						tempCell.setBackground(Color.LIGHT_GRAY);
					}
					tempCell = mainWindow.getCellByPosition(tempCell.getPosx()+1,tempCell.getPosy());
				}
			}else if(cell.isInColoumnWith(activeCell)){
				JGameSquare tempCell = cell;
				
				//hover all cells on the right side
				while(tempCell.getPosy()>activeCell.getPosy()){
					if(activeSquare.canEnlight(tempCell.getRepresentedSquare())){
						tempCell.setBackground(Color.LIGHT_GRAY);
					}
					tempCell = mainWindow.getCellByPosition(tempCell.getPosx(),tempCell.getPosy()-1);
				}
				
				//hover all cells on the left side
				while(tempCell.getPosy()<activeCell.getPosy()){
					if(activeSquare.canEnlight(tempCell.getRepresentedSquare())){
						tempCell.setBackground(Color.LIGHT_GRAY);
					}
					tempCell = mainWindow.getCellByPosition(tempCell.getPosx(),tempCell.getPosy()+1);
				}
			}
		}
	}
	
	//fired when mouse exited a grid cell
	public static void gridCellExited(JGameSquare cell, MainWindow mainWindow) {
	}
}
