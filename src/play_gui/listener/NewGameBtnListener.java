package play_gui.listener;

import gui.JOpenFileDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import play_gui.MainWindow;
import play_gui.PlayGuiController;


public class NewGameBtnListener implements ActionListener{


	@Override
	public void actionPerformed(ActionEvent event) {
		JOpenFileDialog fileDialog = new JOpenFileDialog();
		fileDialog.showOpenDialog(fileDialog);
		MainWindow mainwindow = (MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		PlayGuiController.newGame(mainwindow, fileDialog.getSelectedFile());
	}

}
