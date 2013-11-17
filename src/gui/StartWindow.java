package gui;

import gui.elements.GameDimensionInput;
import gui.validators.*;

import java.text.NumberFormat;
import java.awt.*;

import javax.swing.*;

public class StartWindow {
	
	// Helper vars for the configuration
	private int gridwidth = 0;
	private int gridheight= 0;
	
	// Local helper vars
	private boolean created;
	
	// Beans
	private JLabel sizeLbl;
	private JLabel sliderLbl;
	private JButton okActionBtn;
	private JButton loadBtn;
	private JButton infoBtn;
	private JToolBar startToolBar;
	private JFrame frame;
	private JPanel panel;
	private JLabel widthLbl;
	private JLabel heightLbl;
	public GameDimensionInput widthInput;
	public GameDimensionInput heightInput;
	
	public void show() {
		try{
			
			//Erstelle Button zum �ffnen der MainWindow
			okActionBtn = new JButton("Generieren");
			//Create a Load Button to Load the Savegames
			//It opens a JFileChooser
			loadBtn = new JButton("Entwurf laden");
			infoBtn = new JButton("�ber...");
			startToolBar = new JToolBar();
			startToolBar.setFloatable(false);
			startToolBar.add(loadBtn);
			startToolBar.add(infoBtn);
			
			//Initialisiere alle Variablen f�r das Fenster
			//sliderLbl => Zeigt an welche Gr��e man gew�hlt hat
			sliderLbl = new JLabel("15x15");
			heightLbl = new JLabel("H�he in rows:");
			widthLbl  = new JLabel("Breite in cols:");
			
			//sizeLbl => erkl�rt sich von selbst :)
			sizeLbl = new JLabel("W�hlen Sie eine Gr��e aus (Breite x H�he) :");
			
			//frame => frame wird erstellt und bekommt eine �berschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel f�r das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout f�r das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			GridLayout myLayout = new GridLayout(7, 1);
			
			//Slider f�r die Auswahl der Gr��e wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			widthInput = new GameDimensionInput();
			widthInput.addValidator(new DimensionValidator());
			heightInput = new GameDimensionInput();
			heightInput.addValidator(new DimensionValidator());
			
			
			//Schriftart wurde gew�hlt und erstellt.
			Font font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			sliderLbl.setFont(font);
			sizeLbl.setFont(font);
			widthLbl.setFont(font);
			heightLbl.setFont(font);	
				
			//Erkl�rt sich eigentlich auch von selbst :P
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Komponenten werden dem Panel hinzugef�gt.
			panel.add(sizeLbl);
			panel.add(widthLbl);
			panel.add(widthInput);
			panel.add(heightInput);
			panel.add(heightLbl);
			panel.add(sliderLbl);
			panel.add(okActionBtn);
			
			//Panel wird dem frame �bergeben.
			frame.getContentPane().add(startToolBar, BorderLayout.NORTH);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			
			//GridLayout wird dem panel �bergeben
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
	public JButton getokActionBtn() {
		return okActionBtn;
	}
	
	public JLabel getSliderLbl() {
		return sliderLbl;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	/*
	 * Data vars -> getter & setter
	 */

	public JButton getLoadBtn() {
		return loadBtn;
	}
	
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
