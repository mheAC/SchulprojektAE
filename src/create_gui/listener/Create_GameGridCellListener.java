/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package create_gui.listener;


import gui.JGameSquare;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;



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
public class Create_GameGridCellListener implements MouseListener{
	
	/** The cell. */
	private JGameSquare cell;
	
	/**
	 * Instantiates a new game grid cell listener.
	 *
	 * @param cell the cell
	 */
	public Create_GameGridCellListener(JGameSquare cell){
		this.cell = cell;
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		//right click
		if(event.getModifiers() == 4){
			CreateGUIController.gridCellRightClicked( cell, mainwindow);
		}
		//left click
		else if(event.getModifiers() == 16){
			CreateGUIController.gridCellClicked( cell, mainwindow);
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent event) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		CreateGUIController.gridCellEntered( cell, mainwindow);
		
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent event) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		CreateGUIController.gridCellExited( cell, mainwindow);	
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

