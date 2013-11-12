package start_gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class StartWindow {
	private int gridwidth = 0;
	private int gridheight= 0;
	private static final int breite = 300;
	private static final int hoehe  = 300;
	private static final int minSize=  15;
	private static final int maxSize=  20;
	private static final int steps  =   1;
	private static final int startpos= 15;
	private JLabel sizeLbl;
	private JLabel sliderLbl;
	private JButton okActionBtn;
	private Font font;
	private Dimension dim;
	private JFrame frame;
	private JPanel panel;
	private GridLayout myLayout;
	private JSlider slider;
	private boolean created;
	
	public void show() {
		try{
			//Set default values 15
			setGridheight(15);
			setGridwidth(15);
			
			//Erstelle Button zum �ffnen der MainWindow
			okActionBtn = new JButton("Generieren");
			
			//Initialisiere alle Variablen f�r das Fenster
			//sliderLbl => Zeigt an welche Gr��e man gew�hlt hat
			sliderLbl = new JLabel("15x15");
			
			//sizeLbl => erkl�rt sich von selbst :)
			sizeLbl = new JLabel("W�hlen Sie eine Gr��e aus (Breite x H�he) :");
			
			//frame => frame wird erstellt und bekommt eine �berschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel f�r das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout f�r das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			myLayout = new GridLayout(4, 1);
			
			//Slider f�r die Auswahl der Gr��e wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			slider = new JSlider(JSlider.HORIZONTAL, minSize, maxSize, startpos);
			
			//Eine Hilfsvariable dim (Dimension) wird erstellt.
			dim = new Dimension(breite, hoehe);
			
			//Schriftart wurde gew�hlt und erstellt.
			font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			sliderLbl.setFont(font);
			sizeLbl.setFont(font);
			slider.setFont(font);
			
			//Zahl wird in "steps"-Schitten angezeigt => Slider
			slider.setMajorTickSpacing(steps);
			
			//Zeigt in "steps"-Schritten die einzelnen Punkte => Slider
			slider.setMinorTickSpacing(steps);
			
			//Anzeige wird auf true gesetzt um die Zahlen und Striche anzuzeigen.
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			
			
			//Erkl�rt sich eigentlich auch von selbst :P
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Minimum Gr��e f�r panel und frame wird gesetzt
			frame.setMinimumSize(dim);
			panel.setMinimumSize(dim);
			
			//Bevorzugte Gr��e f�r panel und frame wird gesetzt
			frame.setPreferredSize(dim);
			panel.setPreferredSize(dim);
			
			//Komponenten werden dem Panel hinzugef�gt.
			panel.add(sizeLbl);
			panel.add(slider);
			panel.add(sliderLbl);
			panel.add(okActionBtn);
			
			//Panel wird dem frame �bergeben.
			frame.getContentPane().add(panel);
			
			//GridLayout wird dem panel �bergeben
			panel.setLayout(myLayout);
			created = true;
			
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

	public JSlider getSlider() {
		return slider;
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
	public int getGridwidth() {
		return gridwidth;
	}

	public void setGridwidth(int gridwidth) {
		this.gridwidth = gridwidth;
	}

	public int getGridheight() {
		return gridheight;
	}

	public void setGridheight(int gridheight) {
		this.gridheight = gridheight;
	}
}
