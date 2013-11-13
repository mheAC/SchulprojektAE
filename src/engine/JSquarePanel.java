package engine;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class JSquarePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Border border;
	
	public JSquarePanel(){
		border = BorderFactory.createLineBorder(Color.black);
		this.setBorder(border);
	}

}
