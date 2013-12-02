package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import actionListener.InfoButton;
import actionListener.JGameSquareFirstClick;

import engine.*;
import gui.JOpenFileDialog;
import gui.MainWindow;
import gui.StartWindow;

public class Main implements ChangeListener, ActionListener, GridChangeListener {
	
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
		this.configWin.getokActionBtn().addActionListener(this);
		this.configWin.getLoadBtn().addActionListener(this);
		this.configWin.getInfoBtn().addActionListener(new InfoButton());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// create a new instance of our mainwindow so we dont have old stuff on the panes any more
		this.mainWin = new MainWindow(); // this will now be shown yet (.setVisible needed first)
		/*
		 * Handling for GENERATED MainWindow
		 */
		if(e.getActionCommand() == this.configWin.getokActionBtn().getActionCommand()){
			if(!this.configWin.widthInput.isValid()) {
				this.configWin.widthInput.setRed();
				return; // prevent nullpointer exception due going on with the code
			}	
			if(!this.configWin.heightInput.isValid()) {
				this.configWin.heightInput.setRed();
				return; // prevent nullpointer exception due going on with the code
			}	
			
			if(this.configWin.widthInput.isValid() && this.configWin.heightInput.isValid()){
				// Create the main game window			
				int width = Integer.parseInt(this.configWin.widthInput.getText());
				int height = Integer.parseInt(this.configWin.heightInput.getText());
				gg = new GameGrid(width,height);
				gg.setGridChangeListener(this);
				gg.generateSquares();
				gg.asignSquareCoordinates();
			}
		}
		
		/*
		 * Handling for LOADING drafted MainWindow
		 */
		else if(e.getActionCommand() == this.configWin.getLoadBtn().getActionCommand()){
			JOpenFileDialog fch = new JOpenFileDialog();
			
			if(fch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					// gg -> of type GameGrid
					gg = stH.load(fch.getSelectedFile());
				} catch (Exception e1) { e1.printStackTrace(); }
			}
			else // filechooser cancled
				return;
		}
		/*
		 * Handling for SAVING drafts
		 */
		else if(e.getActionCommand().equals(this.mainWin.getSaveBtn().getActionCommand())) {
			String fileName = JOptionPane.showInputDialog("Unter welchem Namen soll die Datei gespeichert werden?");
			if(fileName != null){
				try {
					this.stH.persist(gg, properties.getProperty("saveGamePath")+(fileName+".ysams"));
				} catch (Exception e3) { e3.printStackTrace(); }
			}
			return; // break here 
		}
		
		/*
		 * Common actions for new Windows (creating a grid window with either generated data or loaded)
		 */
		//gg.setGridChangeListener(this);
		
        // set data to the frame
        mainWin.setCols(gg.getGridSize().width);
        mainWin.setRows(gg.getGridSize().height);
        //set max count of Cols
        //maxAvailableCols = gg.getGridSize().width * gg.getGridSize().height;
       // mainWin.setGameGridData(gg);
		
		// show the window
		mainWin.setGameGridData(gg);
		mainWin.buildWindow();
		
		// Add Listeners
		JGameSquareFirstClick al = new JGameSquareFirstClick(); // create external actionlistener
		al.setMainWin(mainWin);
		for(Component p : mainWin.getMainPanel().getComponents()) {
			JPanel pan = (JPanel)p;
			pan.addMouseListener(al);
		}
		// Add some other listener
		mainWin.getSaveBtn().addActionListener(this);
		mainWin.getAddHeightBtn().addActionListener(this);
		mainWin.getAddWidthBtn().addActionListener(this);
		mainWin.getRemoveHeightBtn().addActionListener(this);
		mainWin.getRemoveWidthBtn().addActionListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		String width = "0";
		String height= "0";
		if(this.configWin.getWidthInput().getText()!=null)
			width = this.configWin.getWidthInput().getText();
		if(this.configWin.getHeightInput().getText()!=null)
			height= this.configWin.getHeightInput().getText();
		String dimLbl = width + "x" + height;
		this.configWin.getSliderLbl().setText(dimLbl);
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void gridChanged() {
		System.out.println("Grid Changed!");
		this.mainWin.repaint();
	}
	
	
}
