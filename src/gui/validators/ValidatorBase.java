package gui.validators;

import gui.elements.GameDimensionInput;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// TODO: Javadoc kontrollieren
/**
 * The Class ValidatorBase.
 */
public class ValidatorBase implements DocumentListener{
	
	/** The max number. */
	protected int maxNumber;
	
	/** The min number. */
	protected int minNumber;
	
	/** The element. */
	private GameDimensionInput element;
	
	/**
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		validate();
	}

	/**
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		validate();
	}

	/**
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		validate();
	}
	
	/**
	 * Sets the element.
	 *
	 * @param element the new element
	 */
	public void setElement(GameDimensionInput element){
		this.element = element;
	}
	
	/**
	 * Validate.
	 */
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
	
	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public GameDimensionInput getElement(){
		return this.element;
	}

}
