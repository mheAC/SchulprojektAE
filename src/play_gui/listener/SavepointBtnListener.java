package play_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import play_gui.MainWindow;
import play_gui.PlayGuiController;

public class SavepointBtnListener implements ActionListener {
	
	/*
	 * Author: Andreas Soiron
	 * fires the step back method on the controller
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		PlayGuiController.savepoint((MainWindow) ((JComponent) event.getSource()).getRootPane().getParent());
	}

}
