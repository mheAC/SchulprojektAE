package create_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;

public class Create_RemoveRowBtnListener implements ActionListener {

	public Create_RemoveRowBtnListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) e.getSource()).getRootPane().getParent();
		// TODO: CreateGUIController.removeRow(mainwindow);
	}

}
