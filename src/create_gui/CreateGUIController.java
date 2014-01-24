package create_gui;


import java.awt.Color;
import java.io.File;
import java.util.Iterator;

import javax.print.DocFlavor.INPUT_STREAM;

import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.UntypedSquare;
import engine.storage_handler.StorageHandler;
import gui.JGameSquare;


public class CreateGUIController {
	
	//triggered when new game button is cliked (loades a new game and renderes the game grid

	public static void newGame(Create_MainWindow mainWindow,File file){
		StorageHandler storageHandler = new StorageHandler();
		try {
			GameGrid gameGrid = storageHandler.load(file);
			if(gameGrid.getSquares().size()==0)
				throw new Exception();
			if(!mainWindow.setGameGrid(gameGrid,true)){
				if(mainWindow.showConfirm("Wollen Sie den aktuellen Spielstand verwerfen")){
					mainWindow.clearGameGrid();
					mainWindow.setGameGrid(gameGrid,true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mainWindow.showAlert("Spiel konnte nicht geladen werden.");
		}
	}
	
	public static void saveGame(Create_MainWindow mainWindow,File file){
		System.out.println(file);
		StorageHandler storageHandler = new StorageHandler();
		storageHandler.persist(mainWindow.getGameGrid(), file);
	}

	//fired when a grid cell is clicked (activates the cell)

	public static void gridCellClicked(JGameSquare cell, Create_MainWindow mainWindow) {
		SquareBase square = cell.getRepresentedSquare();
		if(square.isNumberSquare()){
			mainWindow.setActiveCell(cell);
		}else if(mainWindow.hasActiveCell()){
			boolean enlighted = mainWindow.getGameGrid().enlightuntyped(((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()), cell.getRepresentedSquare());
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
		}else if(square.isUntypedSquare()){
			cell.setRepresentingSquare(mainWindow.getGameGrid().createLightsource((UntypedSquare)cell.getRepresentedSquare()));
			((NumberSquare) cell.getRepresentedSquare()).changeEditorMode();
			mainWindow.setActiveCell(cell);
		}
	}
	
	public static void gridCellRightClicked(JGameSquare cell, Create_MainWindow mainWindow){
		if(cell.getRepresentedSquare().isRaySquare() && ((RaySquare) cell.getRepresentedSquare()).getLightSource() == mainWindow.getActiveCell().getRepresentedSquare()){
			mainWindow.getGameGrid().unenlight((RaySquare) cell.getRepresentedSquare());
			mainWindow.repaintGameGrid();
		}
	}

	//fired when mouse enters a grid cell

	public static void gridCellEntered(JGameSquare cell, Create_MainWindow mainWindow) {
		if(mainWindow.hasActiveCell()){
			mainWindow.clearHover();
			Iterator<JGameSquare> squaresIterator = mainWindow.getUntypedCellsToActive(cell).iterator();
			while(squaresIterator.hasNext()){
				squaresIterator.next().setBackground(Color.LIGHT_GRAY);
			}
		}
	}
	
	public static void stepBack(Create_MainWindow mainWindow){
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

	public static void gridCellExited(JGameSquare cell, Create_MainWindow mainWindow) {
	}
}
