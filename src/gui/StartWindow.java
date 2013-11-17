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
			
			//Erstelle Button zum öffnen der MainWindow
			okActionBtn = new JButton("Generieren");
			//Create a Load Button to Load the Savegames
			//It opens a JFileChooser
			loadBtn = new JButton("Entwurf laden");
			infoBtn = new JButton("Über...");
			startToolBar = new JToolBar();
			startToolBar.setFloatable(false);
			startToolBar.add(loadBtn);
			startToolBar.add(infoBtn);
			
			//Initialisiere alle Variablen für das Fenster
			//sliderLbl => Zeigt an welche Größe man gewählt hat
			sliderLbl = new JLabel("15x15");
			heightLbl = new JLabel("Hšhe in rows:");
			widthLbl  = new JLabel("Breite in cols:");
			
			//sizeLbl => erklärt sich von selbst :)
			sizeLbl = new JLabel("WŠhlen Sie eine Grš§e aus (Breite x Hšhe) :");
			
			//frame => frame wird erstellt und bekommt eine Überschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel für das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout für das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			GridLayout myLayout = new GridLayout(7, 1);
			
			//Slider für die Auswahl der Größe wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			widthInput = new GameDimensionInput();
			widthInput.addValidator(new DimensionValidator());
			heightInput = new GameDimensionInput();
			heightInput.addValidator(new DimensionValidator());
			
			
			//Schriftart wurde gewählt und erstellt.
			Font font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			sliderLbl.setFont(font);
			sizeLbl.setFont(font);
			widthLbl.setFont(font);
			heightLbl.setFont(font);	
				
			//Erklärt sich eigentlich auch von selbst :P
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Komponenten werden dem Panel hinzugefügt.
			panel.add(sizeLbl);
			panel.add(widthLbl);
			panel.add(widthInput);
			panel.add(heightInput);
			panel.add(heightLbl);
			panel.add(sliderLbl);
			panel.add(okActionBtn);
			
			//Panel wird dem frame übergeben.
			frame.getContentPane().add(startToolBar, BorderLayout.NORTH);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			
			//GridLayout wird dem panel übergeben
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
