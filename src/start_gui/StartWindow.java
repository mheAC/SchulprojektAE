package start_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class StartWindow {
	private int gridwidth = 0;
	private int gridheight= 0;
	private static final int breite = 400;
	private static final int hoehe  = 400;
	private static final int minSize=  3;
	private static final int maxSize=  20;
	private static final int steps  =   1;
	private static final int startpos= 15;
	private JLabel sizeLbl;
	private JLabel sliderLbl;
	private JButton okActionBtn;
	private JButton loadBtn;
	private JToolBar startToolBar;
	private Font font;
	private Dimension dim;
	private JFrame frame;
	private JPanel panel;
	private GridLayout myLayout;
	private JLabel widthLbl;
	private JLabel heightLbl;
	private JSlider widthSlider;
	private JSlider heightSlider;
	private boolean created;
	
	public void show() {
		try{
			//Set default values 15
			setGridheight(15);
			setGridwidth(15);
			
			//Erstelle Button zum öffnen der MainWindow
			okActionBtn = new JButton("Generieren");
			//Create a Load Button to Load the Savegames
			//It opens a JFileChooser
			loadBtn = new JButton("Entwurf laden");
			startToolBar = new JToolBar();
			startToolBar.setFloatable(false);
			startToolBar.add(loadBtn);
			
			//Initialisiere alle Variablen für das Fenster
			//sliderLbl => Zeigt an welche Größe man gewählt hat
			sliderLbl = new JLabel("15x15");
			heightLbl = new JLabel("Höhe in rows:");
			widthLbl  = new JLabel("Breite in cols:");
			
			//sizeLbl => erklärt sich von selbst :)
			sizeLbl = new JLabel("Wählen Sie eine Größe aus (Breite x Höhe) :");
			
			//frame => frame wird erstellt und bekommt eine Überschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel für das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout für das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			myLayout = new GridLayout(7, 1);
			
			//Slider für die Auswahl der Größe wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			widthSlider = new JSlider(JSlider.HORIZONTAL, minSize, maxSize, startpos);
			heightSlider= new JSlider(JSlider.HORIZONTAL, minSize, maxSize, startpos);
			
			//Eine Hilfsvariable dim (Dimension) wird erstellt.
			dim = new Dimension(breite, hoehe);
			
			//Schriftart wurde gewählt und erstellt.
			font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			sliderLbl.setFont(font);
			sizeLbl.setFont(font);
			widthLbl.setFont(font);
			heightLbl.setFont(font);
			widthSlider.setFont(font);
			heightSlider.setFont(font);
			
			//Zahl wird in "steps"-Schitten angezeigt => Slider
			widthSlider.setMajorTickSpacing(steps);
			heightSlider.setMajorTickSpacing(steps);
			
			//Zeigt in "steps"-Schritten die einzelnen Punkte => Slider
			widthSlider.setMinorTickSpacing(steps);
			heightSlider.setMinorTickSpacing(steps);
			
			//Anzeige wird auf true gesetzt um die Zahlen und Striche anzuzeigen.
			widthSlider.setPaintTicks(true);
			widthSlider.setPaintLabels(true);
			heightSlider.setPaintTicks(true);
			heightSlider.setPaintLabels(true);			
			
			
			//Erklärt sich eigentlich auch von selbst :P
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Minimum Größe für panel und frame wird gesetzt
			frame.setMinimumSize(dim);
			panel.setMinimumSize(dim);
			
			//Bevorzugte Größe für panel und frame wird gesetzt
			frame.setPreferredSize(dim);
			panel.setPreferredSize(dim);
			
			//Komponenten werden dem Panel hinzugefügt.
			panel.add(sizeLbl);
			panel.add(widthLbl);
			panel.add(widthSlider);
			panel.add(heightLbl);
			panel.add(heightSlider);
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

	public JSlider getWidthSlider() {
		return widthSlider;
	}
	
	public JSlider getHeightSlider() {
		return heightSlider;
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

	public JButton getLoadBtn() {
		return loadBtn;
	}
}
