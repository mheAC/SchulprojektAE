package start_gui;

import java.io.File;

import javax.swing.JFileChooser;

public class JOpenFileDialog extends JFileChooser {

	private static final long serialVersionUID = 1L;
	
	public JOpenFileDialog() {
		super();
		
		// set some custom stuff
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setDialogType(JFileChooser.OPEN_DIALOG);
		setCurrentDirectory(new File("./SaveGame")); // TODO this is not placed well here!
	}

}
