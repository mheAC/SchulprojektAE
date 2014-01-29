package create_gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Create_AboutBtnListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		final ImageIcon ii = new ImageIcon(this.getClass().getResource(
				"/gui/elements/resources/edit.png"));
		StringBuilder text = new StringBuilder(
				"Lichtstrahlen Spiel  - AE@BWV-Aachen | 2014\n\nGruppe:\n");
		List<String> arr = new ArrayList<String>();
		arr.add("\tYusuf Congar\n");
		arr.add("\tBassauer\n");
		arr.add("\tBolz\n");
		arr.add("\tGriesbach\n");
		arr.add("\tHerpers\n");
		arr.add("\tSoiron\n");
		while (!arr.isEmpty()) {
			int pos = (int) (arr.size() * Math.random()); // make it random!
			text.append(arr.get(pos));
			arr.remove(pos);

		}
		JOptionPane.showMessageDialog(null, text.toString(), "Lichtstrahlen", JOptionPane.INFORMATION_MESSAGE, ii);
		return;

	}
}
