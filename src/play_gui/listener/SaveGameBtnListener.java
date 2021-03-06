package play_gui.listener;

import gui.JOpenFileDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import play_gui.MainWindow;
import play_gui.PlayGuiController;


public class SaveGameBtnListener implements ActionListener{

	/*
	 * Author: Andreas Soiron
	 * fired when the save game button is clicked (fires the saveGame on the controller)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JOpenFileDialog fileDialog = new JOpenFileDialog();
		fileDialog.showSaveDialog(fileDialog);
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.saveGame(mainwindow, fileDialog.getSelectedFile());
	}

}
