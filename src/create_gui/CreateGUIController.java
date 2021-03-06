package create_gui;


import java.awt.Color;
import java.io.File;
import java.util.Iterator;

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
		if(mainWindow.getGameGrid().getUntypedSquares().size()==0)
			mainWindow.getGameGrid().runningGame = false;
		else
			mainWindow.getGameGrid().runningGame = true;
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
				//mainWindow.repaint();
				try {
					mainWindow.getLoghandler().log(mainWindow.getGameGrid());
					//mainWindow.repaint();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(square.isRaySquare()){
				//mainWindow.repaint();
				//doesent work :(
				//NumberSquare lightSource = ((RaySquare) square).getLightSource();
				//mainWindow.setActiveCell(mainWindow.getCellByPosition(lightSource.getPositionX(), lightSource.getPositionY()));
				//gridCellEntered(cell,mainWindow);
			}else{
				int num = ((NumberSquare)mainWindow.getActiveCell().getRepresentedSquare()).getOriginalNumber();
				if(num == -1){
					mainWindow.getActiveCell().setRepresentingSquare(mainWindow.getGameGrid().deleteLightsource((NumberSquare) mainWindow.getActiveCell().getRepresentedSquare()));
					mainWindow.repaintGameGrid();
				}
				mainWindow.releaseActiveCell();
				
				//mainWindow.repaint();
			}
		}else if(square.isUntypedSquare()){
			cell.setRepresentingSquare(mainWindow.getGameGrid().createLightsource((UntypedSquare)cell.getRepresentedSquare()));
			((NumberSquare) cell.getRepresentedSquare()).changeEditorMode();
			mainWindow.setActiveCell(cell);
			//mainWindow.repaintGameGrid();
			//mainWindow.repaint();
		}
	}
	
	public static void gridCellRightClicked(JGameSquare cell, Create_MainWindow mainWindow){
		if(cell.getRepresentedSquare().isRaySquare() && ((RaySquare) cell.getRepresentedSquare()).getLightSource() == mainWindow.getActiveCell().getRepresentedSquare()){
			mainWindow.getGameGrid().unenlight((RaySquare) cell.getRepresentedSquare());
			mainWindow.repaintGameGrid();
		}else if(cell.getRepresentedSquare().isNumberSquare()){
			mainWindow.releaseActiveCell();
			mainWindow.getGameGrid().unenlightCREATE((NumberSquare) cell.getRepresentedSquare());
			cell.setRepresentingSquare(mainWindow.getGameGrid().deleteLightsource((NumberSquare)cell.getRepresentedSquare()));
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
				mainWindow.releaseActiveCell();
				mainWindow.repaintGameGrid();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//fired when mouse exited a grid cell

	public static void gridCellExited(JGameSquare cell, Create_MainWindow mainWindow) {
	}
	
    public static void addRow(Create_MainWindow mainWindow) {
        addRow(1, mainWindow);
        mainWindow.repaintGameGrid();
	}
	
	public static void addRow(int count, Create_MainWindow mainWindow) {
		GameGrid gg = mainWindow.getGameGrid();
        gg.addRow(count);
        try {
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(gg, true);
			mainWindow.repaintGameGrid();
			mainWindow.setSize(getNewGGWidth(gg), getNewGGHeight(gg));
			Create_StartWindow.setWidth(gg.getGridSize().width);
			Create_StartWindow.setHeight(gg.getGridSize().height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeRow(Create_MainWindow mainWindow) {
	        removeRow(1, mainWindow);
	}
	
	public static void removeRow(int count, Create_MainWindow mainWindow) {
		GameGrid gg = mainWindow.getGameGrid();
        gg.removeRow(count);
        try {
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(gg, true);
			mainWindow.repaintGameGrid();
			mainWindow.setSize(getNewGGWidth(gg), getNewGGHeight(gg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addColumn(Create_MainWindow mainWindow) {
	        addColumn(1, mainWindow);
	        mainWindow.repaintGameGrid();
	}
	
	public static void addColumn(int count, Create_MainWindow mainWindow) {
		GameGrid gg = mainWindow.getGameGrid();
        gg.addColumn(count);
        try {
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(gg, true);
			mainWindow.repaintGameGrid();
			mainWindow.setSize(getNewGGWidth(gg), getNewGGHeight(gg));
			mainWindow.repaintGameGrid();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeColumn(Create_MainWindow mainWindow) {
        removeColumn(1, mainWindow);
	}
	
	public static void removeColumn(int count, Create_MainWindow mainWindow) {
		GameGrid gg = mainWindow.getGameGrid();
        gg.removeColumn(count);
        try {
			mainWindow.clearGameGrid();
			mainWindow.setGameGrid(gg, true);
			mainWindow.repaintGameGrid();
			mainWindow.setSize(getNewGGWidth(gg), getNewGGHeight(gg));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getNewGGWidth(GameGrid gg){
		int w = 0;
		
		if(gg.getGridSize().width*30 > 500)
			w = gg.getGridSize().width*30;
		else
			w = 500;
		return w;
	}
	
	private static int getNewGGHeight(GameGrid gg){
		int h = 0;
		
		if(gg.getGridSize().height*30 > 500)
			h = gg.getGridSize().height*30;
		else
			h = 500;
		return h;
	}
}
