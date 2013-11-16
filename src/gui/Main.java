package gui;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import engine.*;

public class Main implements ChangeListener, ActionListener, MouseListener {
	private StartWindow configWin;
	private MainWindow mainWin;
	private StorageHandler stH;
	private FileFilter ff;
	
	private Properties properties;
	
	// Storage for current gameData
	GameGrid gg;
	
	public Main() throws Exception {
		// props
		this.properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("config.cfg"));
		properties.load(stream);
		stream.close();
		
		// the start / config window
		this.configWin = new StartWindow();
		this.configWin.show();
		
		// Store handler for beeing able to get drafts back
		this.stH = new StorageHandler();
		
		// Add some listener
		this.configWin.getokActionBtn().addActionListener(this);
		this.configWin.getLoadBtn().addActionListener(this);
		this.configWin.getInfoBtn().addActionListener(this);
		
		//set filefilter for loadBtn
		ff = new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return ".ysams";
			}
			
			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// create a new instance of our mainwindow so we dont have old stuff on the panes any more
		this.mainWin = new MainWindow(); // this will now be shown yet (.setVisible needed first)
		
		/*
		 * Handling for GENERATED MainWindow
		 */
		if(e.getActionCommand() == this.configWin.getokActionBtn().getActionCommand()){
			if(!this.configWin.widthInput.isValid())
				this.configWin.widthInput.setRed();
			
			if(!this.configWin.heightInput.isValid())
				this.configWin.heightInput.setRed();
			
			if(this.configWin.widthInput.isValid() && this.configWin.heightInput.isValid()){
				// Create the main game window			
				int width = Integer.parseInt(this.configWin.widthInput.getText());
				int height = Integer.parseInt(this.configWin.heightInput.getText());
				mainWin.setCols(width);
				mainWin.setRows(height);
				gg = new GameGrid(width,height);
				gg.generateSquares();
				gg.asignSquareCoordinates();
				mainWin.setGameGridData(gg);
				mainWin.buildWindow();
			}
		}
		/*
		 * Handling for LOADING drafted MainWindow
		 */
		else if(e.getActionCommand() == this.configWin.getLoadBtn().getActionCommand()){
			JOpenFileDialog fch = new JOpenFileDialog();
			
			fch.setFileFilter(ff);
			if(fch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					gg = stH.load(fch.getSelectedFile());
				} catch (FileNotFoundException e1) { e1.printStackTrace();
				} catch (ClassNotFoundException e1) { e1.printStackTrace();
				} catch (IOException e1) { e1.printStackTrace(); }
			}
			else // filechooser cancled
				return;
		}
		/*
		 * Handling for SAVING drafts
		 */
		else if(e.getActionCommand().equals(this.mainWin.getSaveBtn().getActionCommand())) {
			String fileName = JOptionPane.showInputDialog("Unter welchem Namen soll die Datei gespeichert werden?");
			if(!fileName.equals("")){
				try {
					this.stH.persist(gg, properties.getProperty("saveGamePath")+(fileName+".ysams"));
				} catch (Exception e3) { e3.printStackTrace(); }
			}
			return; // break here 
		}
		/*
		 * Handling of INFO button
		 */
		else if(e.getActionCommand().equals(this.configWin.getInfoBtn().getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Lichtstrahlen Spiel  - AE@BWV-AAchen | 2013\n\n"
												+ "Gruppe:\n"
												+ "\tBassauer\n"
												+ "\tCongar\n"
			);
			return;
		}
		
		/*
		 * Common actions for new Windows (creating a grid window with either generated data or loaded)
		 */
		
		// show the window
		// Add Panel listener
		for(Component p : mainWin.getMainPanel().getComponents()) {
			JPanel pan = (JPanel)p;
			pan.addMouseListener(this);
		}
		// Add some other listener
		mainWin.getSaveBtn().addActionListener(this);
	}

	/*
	 * Handle the Click onto a JGameSquare (JPanel)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent(); // the panel that has been clicked
		SquareBase s = gs.getRepresentedSquare();
		
		// "Cast" to the desired type
		if(s.getClass().equals(new UntypedSquare().getClass())) {
			if(e.getClickCount() == 1 ) { // Single click: right / left -> Ray Square				
				RaySquare tempRs = s.getAsRaySquare();
				s = tempRs; // overwrite the old Square Object with the new one
			}
			else { // Double click: Number Square
				NumberSquare tempNs = s.getAsNumberSquare();
				s = tempNs;
			}
		}
		else if(s.getClass().equals(new NumberSquare().getClass())) {
			// Convert Number Square to other classes
		}

		// Change a val
		if(s.getClass().equals(new NumberSquare().getClass())) {
			// Number val
			 // catch abort
            String zahlText = JOptionPane.showInputDialog("Zahl?");
            if( zahlText == null) {
                   
            } else {
                    int num = Integer.parseInt(zahlText);
                    ((NumberSquare)s).setNumber(num);
                    ((JLabel)gs.getComponent(0)).setText(zahlText);
            }
			gs.clearPaint(); // remove previous lines
			gs.getTextLabel().setText(zahlText);
		} 
		else if(s.getClass().equals(new RaySquare().getClass())) {
			// Direction
			if(e.getButton() == MouseEvent.BUTTON1) { // BUTTON1 = left mouse
				((RaySquare)s).setDirection(Direction.HORIZONTAL);
				//paint a h line
				gs.drawLine(Direction.HORIZONTAL);
			}
			else if(e.getButton() == MouseEvent.BUTTON3) { // BUTTON2 = right mouse
				((RaySquare)s).setDirection(Direction.VERTICAL);
				//paint a v line
				gs.drawLine(Direction.VERTICAL);
			}
		}
		// Save changes on the square to the model
		this.gg.getSquares().set(gs.getPosition(), s);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
