package start_gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.RaySquare;
import engine.StorageHandler;

public class Main implements ChangeListener, ActionListener, MouseListener {
	private StartWindow configWin;
	private MainWindow mainWin;
	private StorageHandler sh;
	
	public Main() {
		this.configWin = new StartWindow();
		this.configWin.show();
		
		// Add some listener
		this.configWin.getWidthSlider().addChangeListener(this);
		this.configWin.getHeightSlider().addChangeListener(this);
		this.configWin.getokActionBtn().addActionListener(this);
		
		this.mainWin = new MainWindow();
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Create the main game window
		mainWin.setCols(this.configWin.getGridwidth());
		mainWin.setRows(this.configWin.getGridheight());

		mainWin.buildWindow();
		// Add Panel listener
		for(Component p : mainWin.getMainPanel().getComponents()) {
			JPanel pan = (JPanel)p;
			pan.addMouseListener(this);
		}

		//this.configWin.getFrame().dispose();
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
		
		String print = new String("Dieses k�stchen befindet sich an:\nx: "+ (gs.getRepresentedSquare().getPositionX()+1)+"\ny: "+(gs.getRepresentedSquare().getPositionY()+1));
		print += "\nEs enth�lt momentan: " + ((JLabel)gs.getComponent(0)).getText();
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
