package gui;

import gui.elements.GameDimensionInput;
import gui.validators.*;

import java.text.NumberFormat;
import java.awt.*;

import javax.swing.*;

// TODO: Javadoc kontrollieren
/**
 * The Class StartWindow.
 */
public class StartWindow {
	
	// Helper vars for the configuration
	/** The gridwidth. */
	private int gridwidth = 0;
	
	/** The gridheight. */
	private int gridheight= 0;
	
	// Local helper vars
	/** The created. */
	private boolean created;
	
	// Beans
	/** The size lbl. */
	private JLabel sizeLbl;
	
	/** The dimension lbl. */
	private JLabel dimensionLbl;
	
	/** The ok action btn. */
	private JButton okActionBtn;
	
	/** The load btn. */
	private JButton loadBtn;
	
	/** The info btn. */
	private JButton infoBtn;
	
	/** The start tool bar. */
	private JToolBar startToolBar;
	
	/** The frame. */
	private JFrame frame;
	
	/** The panel. */
	private JPanel panel;
	
	/** The width lbl. */
	private JLabel widthLbl;
	
	/** The height lbl. */
	private JLabel heightLbl;
	
	/** The width input. */
	public GameDimensionInput widthInput;
	
	/** The height input. */
	public GameDimensionInput heightInput;

	/**
	 * Show.
	 */
	public void show() {
		try{
			
			//Erstelle Button zum ?ffnen der MainWindow
			okActionBtn = new JButton("Generieren");
			//Create a Load Button to Load the Savegames
			//It opens a JFileChooser
			loadBtn = new JButton("Entwurf laden");
			infoBtn = new JButton("?ber...");
			startToolBar = new JToolBar();
			startToolBar.setFloatable(false);
			startToolBar.add(loadBtn);
			startToolBar.add(infoBtn);
			
			//Slider f?r die Auswahl der Gr??e wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			widthInput = new GameDimensionInput();
			widthInput.addValidator(new DimensionValidator());
			widthInput.setText("15");
			heightInput = new GameDimensionInput();
			heightInput.addValidator(new DimensionValidator());
			heightInput.setText("15");
			
			//Initialisiere alle Variablen f?r das Fenster
			//sliderLbl => Zeigt an welche Gr??e man gew?hlt hat
			dimensionLbl = new JLabel(widthInput.getText()+"x"+heightInput.getText());
			heightLbl = new JLabel("H\u00F6he in rows:");
			widthLbl  = new JLabel("Breite in cols:");
			
			//sizeLbl => erkl?rt sich von selbst :)
			sizeLbl = new JLabel("W\u00E4hlen Sie eine Gr\u00F6\u00DFe aus (Breite x H\u00F6he) : min.4 max.25");
			
			//frame => frame wird erstellt und bekommt eine ?berschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel f?r das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout f?r das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			GridLayout myLayout = new GridLayout(7, 1);
			
			
			//Schriftart wurde gew?hlt und erstellt.
			Font font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			dimensionLbl.setFont(font);
			sizeLbl.setFont(font);
			widthLbl.setFont(font);
			heightLbl.setFont(font);	
				
			//Erkl?rt sich eigentlich auch von selbst :P
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Komponenten werden dem Panel hinzugef?gt.
			panel.add(sizeLbl);
			panel.add(widthLbl);
			panel.add(widthInput);
			panel.add(heightLbl);
			panel.add(heightInput);
			panel.add(dimensionLbl);
			panel.add(okActionBtn);
			
			//Panel wird dem frame ?bergeben.
			frame.getContentPane().add(startToolBar, BorderLayout.NORTH);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			
			//GridLayout wird dem panel ?bergeben
			panel.setLayout(myLayout);
			created = true;
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(created); // show the window
		}
		catch(Exception e){
			System.out.println(e.getMessage()); // ouch
			created = false;
		}
	}

	/*
	 * Simple getter and setter for class attribs
	 */
	/**
	 * Gets the ok action btn.
	 *
	 * @return the ok action btn
	 */
	public JButton getokActionBtn() {
		return okActionBtn;
	}
	
	/**
	 * Gets the slider lbl.
	 *
	 * @return the slider lbl
	 */
	public JLabel getSliderLbl() {
		return dimensionLbl;
	}
	
	/**
	 * Gets the frame.
	 *
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/*
	 * Data vars -> getter & setter
	 */
	
	/**
	 * Gets the width input.
	 *
	 * @return the width input
	 */
	public GameDimensionInput getWidthInput() {
		return widthInput;
	}

	/**
	 * Gets the height input.
	 *
	 * @return the height input
	 */
	public GameDimensionInput getHeightInput() {
		return heightInput;
	}
	
	/**
	 * Gets the load btn.
	 *
	 * @return the load btn
	 */
	public JButton getLoadBtn() {
		return loadBtn;
	}
	
	/**
	 * Gets the info btn.
	 *
	 * @return the info btn
	 */
	public JButton getInfoBtn() {
		return infoBtn;
	}
	/*
	public String getDifficult() {
		int cols;
		int rows;
		int res;
		String difficult = null;
		cols = this.getGridwidth();
		rows = this.getGridheight();
		res = cols*rows;
		if (res <= 25){
			difficult = "Leicht";
		}
		if (res >= 25){
			difficult = "Mittel";
		}
		if (res >= 50) {
			difficult = "Schwierig";
		}
		if (res >= 100) {
			difficult = "Sehr Schwierig";
		}
		return difficult;
	}*/
}
