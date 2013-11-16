package gui.validators;

import gui.elements.GameDimensionInput;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ValidatorBase implements DocumentListener{
	
	protected int maxNumber;
	protected int minNumber;
	private GameDimensionInput element;
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		validate();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		validate();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		validate();
	}
	
	public void setElement(GameDimensionInput element){
		this.element = element;
	}
	
	public void validate(){
		boolean valid = true;
		try{
			if(Integer.parseInt(this.element.getText()) > this.maxNumber)
				valid = false;
		}
		catch(NullPointerException exception){}
		catch(NumberFormatException exception){
			this.element.setValid(false);
		}
		
		try{
			if(Integer.parseInt(this.element.getText()) < this.minNumber)
				valid = false;
		}
		catch(NullPointerException exception){}
		catch(NumberFormatException exception){
			this.element.setValid(false);
		}
		this.element.setValid(valid);
	}
	
	public GameDimensionInput getElement(){
		return this.element;
	}

}
