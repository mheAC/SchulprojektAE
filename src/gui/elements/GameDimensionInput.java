package gui.elements;

import java.awt.Color;
import java.text.Format;
import java.text.NumberFormat;

import gui.validators.ValidatorBase;

import javax.swing.JFormattedTextField;
import javax.swing.event.ChangeListener;

// TODO: Javadoc kontrollieren
/**
 * The Class GameDimensionInput.
 */
public class GameDimensionInput extends JFormattedTextField{
	
	/** The valid. */
	private boolean valid = false;
	
	/**
	 * Instantiates a new game dimension input.
	 */
	public GameDimensionInput(){
		super(NumberFormat.getInstance());
		//this.setText("10");
	}
	
	/**
	 * Adds the validator.
	 *
	 * @param validator the validator
	 */
	public void addValidator(ValidatorBase validator){
		validator.setElement(this);
		this.getDocument().addDocumentListener(validator);
	}
	
	/**
	 * @see java.awt.Component#isValid()
	 */
	public boolean isValid(){
		return this.valid;
	}
	
	/**
	 * Sets the red.
	 */
	public void setRed(){
		this.setBackground(Color.RED);
	}
	
	/**
	 * Sets the white.
	 */
	public void setWhite(){
		this.setBackground(Color.WHITE);
	}
	
	/**
	 * Sets the valid.
	 *
	 * @param valid the new valid
	 */
	public void setValid(boolean valid){
		if(valid)
			this.setWhite();
		this.valid = valid;
	}
	
	/**
	 * @see javax.swing.text.JTextComponent#getText()
	 */
	public String getText(){
		return super.getText().replace(".", "");
	}

}
