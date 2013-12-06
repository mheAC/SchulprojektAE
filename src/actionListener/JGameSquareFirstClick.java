package actionListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.NumberSquare;
import engine.SquareBase;
import gui.JGameSquare;
import gui.MainWindow;

public class JGameSquareFirstClick implements MouseListener {

	MainWindow mainWin;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent(); // the panel that has been clicked
		SquareBase s = gs.getRepresentedSquare();

		if(e.getButton() == MouseEvent.BUTTON1 ) { // Single click: enter number
			String val = JOptionPane.showInputDialog(null, "Zahl?");
			int value = Integer.parseInt(val);

			NumberSquare ns = s.getAsNumberSquare();
			ns.setNumber(value);
			gs.setRepresentingSquare(ns); // persist
			
			//
			JGameSquareEditorMode al = new JGameSquareEditorMode();
			al.setLightSource(ns);
			//
			
			// hightlight JGameSquares that could be linked
			for(Component c : mainWin.getMainPanel().getComponents()) {
				JGameSquare gs2 = (JGameSquare)c;
				if(ns.canEnlight(gs2.getRepresentedSquare()))
					gs2.setBackground(Color.GREEN);
				else // reset color
					gs2.setBackground(Color.WHITE);
				// change mouse listener
				gs2.removeMouseListener(this);
				gs2.addMouseListener(al);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			System.out.println("rechts klick");
			
		}
	
		// Save changes on the square to the model
		//this.gg.getSquares().set(gs.getPosition(), s);
	}
	
	public void setMainWin(MainWindow mainWin) {
		this.mainWin = mainWin;
	}
	
	public MainWindow getMainWin() {
		return mainWin;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

}

