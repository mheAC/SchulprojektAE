package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class InfoButton implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Lichtstrahlen Spiel  - AE@BWV-AAchen | 2013\n\n"
				+ "Gruppe:\n"
				+ "\tCongar\n"
				+ "\tBassauer\n"
				+ "\tHerpers\n"
				+ "\tGriesbach\n"
				+ "\tBolz\n"
				+ "\tSoiron\n"
);
	}

	

}
