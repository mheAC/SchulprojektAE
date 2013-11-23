package play_gui.listener;

import gui.JGameSquare;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import play_gui.MainWindow;
import play_gui.PlayGuiController;

public class GameGridCellListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellClicked( (JGameSquare) event.getSource(), mainwindow);
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.gridCellEntered( (JGameSquare) event.getSource(), mainwindow);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
