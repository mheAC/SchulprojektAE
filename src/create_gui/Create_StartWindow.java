package create_gui;


	
	import engine.GameGrid;
import gui.elements.GameDimensionInput;
import gui.validators.*;

	import java.text.NumberFormat;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import create_gui.listener.Create_AboutBtnListener;
import create_gui.listener.Create_NewGameBtnListener;

	// TODO: Javadoc kontrollieren
	/**
	 * The Class StartWindow.
	 */
	public class Create_StartWindow {
		
		// Helper vars for the configuration
		private static int gridwidth = 0;
		
		private static int gridheight= 0;
		
		// Local helper vars
		private boolean created;
		
		// Beans
		private JLabel sizeLbl;
		
		private JLabel dimensionLbl;
		
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
				
				//Erstelle Button zum ?ffnen der MainWindow
				okActionBtn = new JButton("Generieren");
				okActionBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int width = Integer.parseInt(widthInput.getText());
						int height = Integer.parseInt(heightInput.getText());
						setWidth(width);
						setHeight(height);
						Create_MainWindow mainWindow = new Create_MainWindow();
						try {
							mainWindow.setGameGrid(new GameGrid(width, height));
							mainWindow.repaintGameGrid();
							frame.dispose();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				//Create a Load Button to Load the Savegames
				//It opens a JFileChooser
				loadBtn = new JButton("Entwurf laden");
				infoBtn = new JButton("?ber...");
				infoBtn.addActionListener(new Create_AboutBtnListener());
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
//				panel.add(dimensionLbl);
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
		public JButton getokActionBtn() {
			return okActionBtn;
		}
		
		public JLabel getSliderLbl() {
			return dimensionLbl;
		}
		
		public JFrame getFrame() {
			return frame;
		}

		/*
		 * Data vars -> getter & setter
		 */
		
		public GameDimensionInput getWidthInput() {
			return widthInput;
		}

		public GameDimensionInput getHeightInput() {
			return heightInput;
		}
		
		public JButton getLoadBtn() {
			return loadBtn;
		}
		
		public JButton getInfoBtn() {
			return infoBtn;
		}
		
		public static int getWidth(){
			return gridwidth;
		}
		
		public static int getHeight(){
			return gridheight;
		}
		
		public static void setWidth(int w){
			Create_StartWindow.gridwidth = w;
		}
		
		public static void setHeight(int h){
			Create_StartWindow.gridheight = h;
		}
		
}
