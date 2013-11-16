package gui.elements;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.*;

public class DimensionInput extends JFormattedTextField{
	public DimensionInput(NumberFormat numberFormat){
		super(numberFormat);
		//this.addActionListener(new DimensionValidater());
	}
}
