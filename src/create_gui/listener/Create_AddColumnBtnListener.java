package create_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;

public class Create_AddColumnBtnListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) e.getSource()).getRootPane().getParent();
		// TODO: CreateGUIController.addColumn(mainwindow);
		CreateGUIController.addColumn(mainwindow);
		for(int i=0;i<mainwindow.getGameGrid().getSquares().size();i++){
			System.out.println("X: " + mainwindow.getGameGrid().getSquares().get(i).getPositionX() + " Y: " + mainwindow.getGameGrid().getSquares().get(i).getPositionY());
		}
	}

	public Create_AddColumnBtnListener() {
	}

}
