package controller;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import actionListener.InfoButton;

import engine.*;
import gui.JGameSquare;
import gui.JOpenFileDialog;
import gui.MainWindow;
import gui.StartWindow;

public class Main implements ChangeListener, ActionListener, MouseListener, GridChangeListener {
	
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
		gg.setGridChangeListener(this);
		
        // set data to the frame
        mainWin.setCols(gg.getGridSize().width);
        mainWin.setRows(gg.getGridSize().height);
        //set max count of Cols
        //maxAvailableCols = gg.getGridSize().width * gg.getGridSize().height;
       // mainWin.setGameGridData(gg);
		
		// show the window
		mainWin.setGameGridData(gg); // DARE YOU MOVING THIS LINES AGAIN!!
		mainWin.buildWindow();
		
		// Add Listeners
		for(Component p : mainWin.getMainPanel().getComponents()) {
			JPanel pan = (JPanel)p;
			pan.addMouseListener(this);
		}
		// Add some other listener
		mainWin.getSaveBtn().addActionListener(this);
		mainWin.getAddHeightBtn().addActionListener(this);
		mainWin.getAddWidthBtn().addActionListener(this);
		mainWin.getRemoveHeightBtn().addActionListener(this);
		mainWin.getRemoveWidthBtn().addActionListener(this);
	}

	/*
	 * Handle the Click onto a JGameSquare (JPanel)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent(); // the panel that has been clicked
		SquareBase s = gs.getRepresentedSquare();
		// "Cast" to the desired type of class
		if(e.getButton() == MouseEvent.BUTTON1 ) { // Single click: right / left -> Ray Square	

		}
		else if(e.getButton() == MouseEvent.BUTTON3) { // Double click: Number Square
		
		}
		else
			System.out.println(gs.getRepresentedSquare().toString());
	
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

	public void gridChanges() {
		System.out.println("Grid Changed!");
	}
	
	
}
