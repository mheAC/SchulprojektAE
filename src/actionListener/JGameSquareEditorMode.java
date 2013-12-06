package actionListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import gui.JGameSquare;

public class JGameSquareEditorMode implements MouseListener {

	NumberSquare lightSource;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent(); // the panel that has been clicked
		RaySquare s = gs.getRepresentedSquare().getAsRaySquare();

		if(lightSource.canEnlight(s))
			gs.setBackground(Color.blue);
	}
	
	public void setLightSource(NumberSquare lightSource) {
		this.lightSource = lightSource;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent();
		RaySquare s = gs.getRepresentedSquare().getAsRaySquare();

		if(lightSource.canEnlight(s) && !gs.getBackground().equals(Color.BLUE))
			gs.setBackground(Color.CYAN);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JGameSquare gs = (JGameSquare)e.getComponent(); // the panel that has been clicked
		RaySquare s = gs.getRepresentedSquare().getAsRaySquare();

		if(lightSource.canEnlight(s) && !gs.getBackground().equals(Color.BLUE))
			gs.setBackground(Color.GREEN);
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

}