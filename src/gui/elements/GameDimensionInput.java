package gui.elements;

import java.awt.Color;
import java.text.Format;
import java.text.NumberFormat;

import gui.validators.ValidatorBase;

import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeListener;

public class GameDimensionInput extends JFormattedTextField{
	
	private boolean valid = false;
	
	public GameDimensionInput(){
		super(NumberFormat.getInstance());
		//this.setText("10");
	}
	
	public void addValidator(ValidatorBase validator){
		validator.setElement(this);
		this.getDocument().addDocumentListener(validator);
	}
	
	public boolean isValid(){
		return this.valid;
	}
	
	public void setRed(){
		this.setBackground(Color.RED);
	}
	
	public void setWhite(){
		this.setBackground(Color.WHITE);
	}
	
	public void setValid(boolean valid){
		if(valid)
			this.setWhite();
		this.valid = valid;
	}
	
	public String getText(){
		return super.getText().replace(".", "");
	}

}
