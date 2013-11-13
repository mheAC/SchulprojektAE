package start_gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import engine.*;

public class Main implements ChangeListener, ActionListener, MouseListener {
	private StartWindow configWin;
	private MainWindow mainWin;
	private StorageHandler stH;
	
	public Main() {
		this.configWin = new StartWindow();
		this.stH = new StorageHandler();
		this.configWin.show();
		
		// Add some listener
		this.configWin.getWidthSlider().addChangeListener(this);
		this.configWin.getHeightSlider().addChangeListener(this);
		this.configWin.getokActionBtn().addActionListener(this);
		this.configWin.getLoadBtn().addActionListener(this);
		
		this.mainWin = new MainWindow();
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == this.configWin.getokActionBtn().getActionCommand()){
			// Create the main game window
			mainWin.setCols(this.configWin.getGridwidth());
			mainWin.setRows(this.configWin.getGridheight());
			//this.configWin.getFrame().dispose();
		}
		else if(e.getActionCommand() == this.configWin.getLoadBtn().getActionCommand()){
			JFileChooser fch = new JFileChooser();
			fch.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fch.setDialogType(JFileChooser.OPEN_DIALOG);
			fch.setCurrentDirectory(new File("./SaveGame"));
			fch.showOpenDialog(configWin.getFrame());
			File sf = fch.getSelectedFile();
			try {
				GameGrid gg = (GameGrid)stH.loadArrayListFromFile(sf);
				mainWin.setCols(gg.getGridSize().width);
				mainWin.setRows(gg.getGridSize().height);
				mainWin.setGameGridData(gg);
				//mainWin.buildWindow();
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
		mainWin.buildWindow();
		// Add Panel listener
		for(Component p : mainWin.getMainPanel().getComponents()) {
			JPanel pan = (JPanel)p;
			pan.addMouseListener(this);
		}
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
	public void mouseClicked(MouseEvent e) {
		/*JPanel pp = (JPanel)e.getComponent();
		Point point = pp.getLocation();
		JOptionPane.showMessageDialog(null, "X: " + point.x + "\nY: " + point.y, "Location", JOptionPane.OK_OPTION);*/
		
		// ((JLabel)((JPanel)e.getComponent()).getComponent(0)).getText()
		JGameSquare gs = (JGameSquare)e.getComponent();
		
		String print = new String("Dieses kästchen befindet sich an:\nx: "+ (gs.getRepresentedSquare().getPositionX()+1)+"\ny: "+(gs.getRepresentedSquare().getPositionY()+1));
		print += "\nEs enthält momentan: " + ((JLabel)gs.getComponent(0)).getText();
		if(gs.getClass().equals(new RaySquare().getClass()));
		print += "\nEs handelt sich um ein: " + gs.getRepresentedSquare().getClass().getSimpleName();
		
		JOptionPane.showMessageDialog(null, print);
			
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
