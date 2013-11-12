package start_gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StartWindow {
	private static int gridwidth = 0;
	private static int gridheight= 0;
	private static final int breite = 300;
	private static final int hoehe  = 300;
	private static final int minSize=  15;
	private static final int maxSize=  20;
	private static final int steps  =   1;
	private static final int startpos= 15;
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
			//Set default values 15
			setGridheight(15);
			setGridwidth(15);
			
			//Erstelle Button zum öffnen der MainWindow
			mybtn = new JButton("Open");
			
			//Create event listener for mybtn
			mybtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					MainWindow myMain = new MainWindow();
					myMain.setCols(getGridwidth());
					myMain.setRows(getGridheight());
					myMain.execute();
					//myMain.buildWindow();
					frame.setVisible(!created);
				}
			});
			
			//Initialisiere alle Variablen für das Fenster
			//sliderLbl => Zeigt an welche Größe man gewählt hat
			sliderLbl = new JLabel("15x15");
			
			//sizeLbl => erklärt sich von selbst :)
			sizeLbl = new JLabel("Wählen Sie eine Größe aus (Breite x Höhe) :");
			
			//frame => frame wird erstellt und bekommt eine Überschrift
			frame = new JFrame("Lichtstrahlen Startparameter");
			
			//panel für das frame wird erstellt
			panel = new JPanel();
			
			//Ein Layout für das panel wird erstellt. In diesem Fall ein GridLayout()
			//4 - > Rows, 1 -> Columns
			myLayout = new GridLayout(4, 1);
			
			//Slider für die Auswahl der Größe wird erstellt.
			//Minimum - Wert : 15, Maximum - Wert : 20;
			slider = new JSlider(JSlider.HORIZONTAL, minSize, maxSize, startpos);
			
			//Eine Hilfsvariable dim (Dimension) wird erstellt.
			dim = new Dimension(breite, hoehe);
			
			//Schriftart wurde gewählt und erstellt.
			font = new Font("Tahoma", Font.ITALIC | Font.BOLD, 15);
			
			//Schriftart wird gesetzt.
			sliderLbl.setFont(font);
			sizeLbl.setFont(font);
			slider.setFont(font);
			
			//Dem Slider wird ein Listener zugewiesen.
			slider.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					// TODO Auto-generated method stub
				    if (!slider.getValueIsAdjusting()) {
				    	int size = slider.getValue();
				    	String forlbl = new String(size + "x" + size);
				    	sliderLbl.setText(forlbl);
				    	setGridheight(size);
						setGridwidth(size);
				    }
				}
			});
			
			//Zahl wird in "steps"-Schitten angezeigt => Slider
			slider.setMajorTickSpacing(steps);
			
			//Zeigt in "steps"-Schritten die einzelnen Punkte => Slider
			slider.setMinorTickSpacing(steps);
			
			//Anzeige wird auf true gesetzt um die Zahlen und Striche anzuzeigen.
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			
			
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
			panel.add(slider);
			panel.add(sliderLbl);
			panel.add(mybtn);
			
			//Panel wird dem frame übergeben.
			frame.getContentPane().add(panel);
			
			//GridLayout wird dem panel übergeben
			panel.setLayout(myLayout);
			created = true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			created = false;
		}
		
		
	}
	
	public void show(){
		frame.setLocationRelativeTo(null);
		frame.setVisible(created);
	}

	public static int getGridwidth() {
		return gridwidth;
	}

	public static void setGridwidth(int gridwidth) {
		StartWindow.gridwidth = gridwidth;
	}

	public static int getGridheight() {
		return gridheight;
	}

	public static void setGridheight(int gridheight) {
		StartWindow.gridheight = gridheight;
	}
}
