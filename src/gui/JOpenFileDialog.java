package gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

// TODO: Javadoc kontrollieren
/**
 * The Class JOpenFileDialog.
 */
public class JOpenFileDialog extends JFileChooser {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new j open file dialog.
	 */
	public JOpenFileDialog() {
		super();
		
		// set some custom stuff
		setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		setDialogType(JFileChooser.OPEN_DIALOG);
		setCurrentDirectory(new File("./SaveGame")); // TODO this is not placed well here!
		
		/*
		 * File Filters
		 */
		setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return ".ysams";
			}
			
			@Override
			public boolean accept(File arg0) {
				if(arg0.getAbsolutePath().endsWith(".ysams"))
					return true;
				else
					return false;
			}
		});
	}

}
