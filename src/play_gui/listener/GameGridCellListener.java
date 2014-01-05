package play_gui.listener;

import gui.JGameSquare;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import play_gui.MainWindow;
import play_gui.PlayGuiController;

// TODO: Javadoc kontrollieren
/**
 * The listener interface for receiving gameGridCell events.
 * The class that is interested in processing a gameGridCell
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGameGridCellListener<code> method. When
 * the gameGridCell event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GameGridCellEvent
 */
public class GameGridCellListener implements MouseListener{
	
	/** The cell. */
	private JGameSquare cell;
	
	/**
	 * Instantiates a new game grid cell listener.
	 *
	 * @param cell the cell
	 */
	public GameGridCellListener(JGameSquare cell){
		this.cell = cell;
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
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

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellEntered( cell, mainwindow);
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellExited( cell, mainwindow);		
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
