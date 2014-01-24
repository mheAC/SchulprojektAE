package play_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import play_gui.MainWindow;
import play_gui.PlayGuiController;


public class BackBtnListener implements ActionListener{


	@Override
	public void actionPerformed(ActionEvent event) {
		PlayGuiController.stepBack((MainWindow) ((JComponent) event.getSource()).getRootPane().getParent());
	}

}
