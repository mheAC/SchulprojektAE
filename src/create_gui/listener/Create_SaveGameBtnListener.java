/**
 * 
 *@author Michael Herpers (michael.herpers@gmail.com)
 */
package create_gui.listener;


import gui.JOpenFileDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

import create_gui.CreateGUIController;
import create_gui.Create_MainWindow;
import play_gui.MainWindow;
import play_gui.PlayGuiController;

// TODO: Javadoc kontrollieren
/**
 * The listener interface for receiving newGameBtn events.
 * The class that is interested in processing a newGameBtn
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addNewGameBtnListener<code> method. When
 * the newGameBtn event occurs, that object's appropriate
 * method is invoked.
 *
 * @see NewGameBtnEvent
 */
public class Create_SaveGameBtnListener implements ActionListener{

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JOpenFileDialog fileDialog = new JOpenFileDialog();
		fileDialog.showSaveDialog(fileDialog);
		Create_MainWindow mainwindow = (Create_MainWindow) ((JComponent) event.getSource()).getRootPane().getParent();
		CreateGUIController.saveGame(mainwindow, fileDialog.getSelectedFile());
	}

}
