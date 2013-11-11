package start_gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StartWindow implements ChangeListener {
	private static final int breite = 300;
	private static final int hoehe  = 300;
	private static final int minSize=  15;
	private static final int maxSize=  20;
	private static final int steps  =   1;
	private static final int startpos= 5;
	private static final String filename = "Errlog.txt";
	private FileWriter fstream ;
	private JLabel sizeLbl;
	private JLabel sliderLbl;
	private JButton mybtn;
	private Font font;
	private Dimension dim;
	
	private JFrame frame;
	private JPanel panel;
	private GridLayout myLayout;
	private JSlider slider;
	private boolean created;
	
	public StartWindow(){
		try{
			//FileWriter und BufferedWriter wird erstellt um bestimmte Fehlermeldungen zu protokollieren
			fstream = new FileWriter(filename);
			
			//Erstelle Button zum �ffnen der MainWindow
			mybtn = new JButton("Open");
			
			//Create event listener for mybtn
			
			
			//Initialisiere alle Variablen f�r das Fenster
			//sliderLbl => Zeigt an welche Gr��e man gew�hlt hat
			sliderLbl = new JLabel("15x15");
			
			//sizeLbl => erkl�rt sich von selbst :)
			sizeLbl = new JLabel("W�hlen Sie eine Gr��e aus (Breite x H�he) :");
			
			//frame => frame wird erstellt und bekommt eine �berschrift
			frame = new JFrame("WALLS Startparameter");
			
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
			
			//Dem Slider wird ein Listener zugewiesen.
			slider.addChangeListener(this);
			
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
			
			//Panel wird dem frame �bergeben.
			frame.add(panel);
			
			//GridLayout wird dem panel �bergeben
			panel.setLayout(myLayout);
			created = true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			created = false;
		}
		
		
	}
	
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	    	int size = source.getValue();
	    	String forlbl = new String(size + "x" + size);
	    	sliderLbl.setText(forlbl);
	    }
	}
	
	public void show(){
		frame.setVisible(created);
	}

}
