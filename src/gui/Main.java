package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.*;

public class Main implements ChangeListener, ActionListener, MouseListener {
	private StartWindow configWin;
	private MainWindow mainWin;
	private StorageHandler stH;
	
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
		this.configWin.getWidthSlider().addChangeListener(this);
		this.configWin.getHeightSlider().addChangeListener(this);
		this.configWin.getokActionBtn().addActionListener(this);
		this.configWin.getLoadBtn().addActionListener(this);
		this.configWin.getInfoBtn().addActionListener(this);
		
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
			// Create the main game window			
			mainWin.setCols(this.configWin.getGridwidth());
			mainWin.setRows(this.configWin.getGridheight());
			gg = new GameGrid(this.configWin.getGridwidth(), this.configWin.getGridheight());
			gg.generateSquares();
			gg.asignSquareCoordinates();
		}
		/*
		 * Handling for LOADING drafted MainWindow
		 */
		else if(e.getActionCommand() == this.configWin.getLoadBtn().getActionCommand()){
			JOpenFileDialog fch = new JOpenFileDialog();
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
					this.stH.persist(gg, properties.getProperty("saveGamePath")+fileName);
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
		// set data to the frame
		mainWin.setCols(gg.getGridSize().width);
		mainWin.setRows(gg.getGridSize().height);
		mainWin.setGameGridData(gg);
		
		// show the window
		mainWin.buildWindow();
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
		JGameSquare gs = (JGameSquare)e.getComponent();
		SquareBase s = gs.getRepresentedSquare();
		
		System.out.println(e.getClickCount());
		
		// "Cast" to the desired type
		if(s.getClass().equals(new UntypedSquare().getClass())) {
			if(e.getClickCount()==1) { // Single click: right / left -> Ray Square
				RaySquare tempRs = new RaySquare();
				// Copy Some stuff from the old object to the new
				tempRs.setPositionX(s.getPositionX());
				tempRs.setPositionY(s.getPositionY());
				// overwrite the old Square Object with the new one
				s = tempRs;
			}
			else { // Double click: Number Square
				NumberSquare tempNs = new NumberSquare();
				tempNs.setPositionX(s.getPositionX());
				tempNs.setPositionY(s.getPositionY());
				s = tempNs;
			}
		}
		
		// Change a val
		if(s.getClass().equals(new NumberSquare().getClass())) {
			// Number val
			String zahlText = JOptionPane.showInputDialog("Zahl?");
			int num = Integer.parseInt(zahlText);
			((NumberSquare)s).setNumber(num);
			((JLabel)gs.getComponent(0)).setText(zahlText);
		} 
		else if(s.getClass().equals(new RaySquare().getClass())) {
			// Direction
			if(e.getButton() == MouseEvent.BUTTON1) {
				((RaySquare)s).setDirection(Direction.HORIZONTAL);
				((JLabel)gs.getComponent(0)).setText("-");
			}
			else {
				((RaySquare)s).setDirection(Direction.VERTICAL);
				((JLabel)gs.getComponent(0)).setText("|");
			}
		}
		// Save to model
		this.gg.getSquares().set(gs.getPosition(), s);
		
		/*String print = new String("Dieses kästchen befindet sich an:\nx: "+ (gs.getRepresentedSquare().getPositionX()+1)+"\ny: "+(gs.getRepresentedSquare().getPositionY()+1));
		print += "\nEs enthält momentan: " + ((JLabel)gs.getComponent(0)).getText();
		if(gs.getClass().equals(new RaySquare().getClass()));
		print += "\nEs handelt sich um ein: " + gs.getRepresentedSquare().getClass().getSimpleName();
		
		JOptionPane.showMessageDialog(null, print);*/
		
		/*switch(e.getButton()) {
			case MouseEvent.BUTTON1: // left mouse
				((JLabel)gs.getComponent(0)).setText("-");
				s.setDirection(Direction.HORIZONTAL);
			break;
			
			case MouseEvent.BUTTON3: // right mouse
				((JLabel)gs.getComponent(0)).setText("|");
				s.setDirection(Direction.VERTICAL);
			break;
		}*/
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
	    if (!this.configWin.getWidthSlider().getValueIsAdjusting()) {
	    	int width = this.configWin.getWidthSlider().getValue();
	    	int height= this.configWin.getHeightSlider().getValue();
	    	String forlbl = new String(width + "x" + height);
	    	this.configWin.getSliderLbl().setText(forlbl);
	    	this.configWin.setGridheight(height);
	    	this.configWin.setGridwidth(width);
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

}
