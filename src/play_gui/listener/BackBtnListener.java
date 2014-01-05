package play_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
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
public class BackBtnListener implements ActionListener{

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		PlayGuiController.stepBack((MainWindow) ((JComponent) event.getSource()).getRootPane().getParent());
	}

}
