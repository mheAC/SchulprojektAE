package create_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JComponent;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;

public class Create_AddRowBtnListener implements ActionListener {

	public Create_AddRowBtnListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) e.getSource()).getRootPane().getParent();
		// TODO: CreateGUIController.addRow(mainwindow);
		CreateGUIController.addRow(mainwindow);
//		for(int i=0;i<mainwindow.getGameGrid().getSquares().size();i++){
//			System.out.println("X: " + mainwindow.getGameGrid().getSquares().get(i).getPositionX() + " Y: " + mainwindow.getGameGrid().getSquares().get(i).getPositionY());
//		}
		try {
			mainwindow.getLoghandler().log(mainwindow.getGameGrid());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
