package play_gui.listener;

import gui.JGameSquare;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import play_gui.MainWindow;
import play_gui.PlayGuiController;


public class GameGridCellListener implements MouseListener{
	
	private JGameSquare cell;
	
	
	public GameGridCellListener(JGameSquare cell){
		this.cell = cell;
	}

	/*
	 * Author: Andreas Soiron
	 * fired when the cell is clicked (fires the gridCellClicked or gridCellRightClicked method on the controller)
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		//right click
		if(event.getModifiers() == 4){
			PlayGuiController.gridCellRightClicked( cell, mainwindow);
		}
		//left click
		else if(event.getModifiers() == 16){
			PlayGuiController.gridCellClicked( cell, mainwindow);
		}
	}

	/*
	 * Author: Andreas Soiron
	 * fired when the mouse enteres the cell (fires the gridCellEntered on the controller)
	 */
	@Override
	public void mouseEntered(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellEntered( cell, mainwindow);
	}


	/*
	 * Author: Andreas Soiron
	 * fired when the mouse exites the cell (fires the gridCellEntered on the controller)
	 */
	@Override
	public void mouseExited(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellExited( cell, mainwindow);		
	}

	/*
	 * Author: Andreas Soiron
	 * fired when the mouse clickes the cell (does nothig)
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * Author: Andreas Soiron
	 * fired when the mouse clickes the cell (does nothig)
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
