package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import engine.*;
import gui.JGameSquare;
import gui.JOpenFileDialog;
import gui.MainWindow;
import gui.StartWindow;

public class Main implements ChangeListener, ActionListener, MouseListener, CaretListener {
	private StartWindow configWin;
	private MainWindow mainWin;
	private StorageHandler stH;
	private SquareBase beginDraw;
	private SquareBase endDraw;
	private SquareBase NumberPos;
	private int iNumberPos;
	private Color defaultColor;
	private Color clrBegin;
	private Color clrEnd;
	private boolean drawing;
	private boolean wasBlue;
	private int drawCount;
	private Properties properties;
	private int maxAvailableCols;
	
	// Storage for current gameData
	GameGrid gg;
	
	public Main() throws Exception {
		//for Mouse Motion Listener
		beginDraw = null;
		endDraw = null;
		drawCount = 0;
		NumberPos = null;
		drawing = false;
		clrBegin = null;
		clrEnd = null;
		iNumberPos = 0;
		
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
		this.configWin.getInfoBtn().addActionListener(this);
		this.configWin.getHeightInput().addCaretListener(this);
		this.configWin.getWidthInput().addCaretListener(this);
	}
	
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// create a new instance of our mainwindow so we dont have old stuff on the panes any more
		this.mainWin = new MainWindow(); // this will now be shown yet (.setVisible needed first)
		defaultColor = this.mainWin.getMainPanel().getBackground();
		/*
		 * Handling for GENERATED MainWindow
		 */
		if(e.getActionCommand() == this.configWin.getokActionBtn().getActionCommand()){
			if(!this.configWin.widthInput.isValid() || !this.configWin.heightInput.isValid()) {
				this.configWin.widthInput.setRed();
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
		 * Handling of INFO button
		 */
		else if(e.getActionCommand().equals(this.configWin.getInfoBtn().getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Lichtstrahlen Spiel  - AE@BWV-AAchen | 2013\n\n"
												+ "Gruppe:\n"
												+ "\tBassauer\n"
												+ "\tCongar\n"
												+ "\tHerpers\n"
												+ "\tGriesbach\n"
												+ "\tBolz\n"
			);
			return;
		}
		
		/*
		 * Common actions for new Windows (creating a grid window with either generated data or loaded)
		 */
        // set data to the frame
        mainWin.setCols(gg.getGridSize().width);
        mainWin.setRows(gg.getGridSize().height);
        //set max count of Cols
        maxAvailableCols = gg.getGridSize().width * gg.getGridSize().height;
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
			if(drawing){
				
				if(drawCount == 0){
					((JGameSquare)e.getComponent()).setBackground(Color.GREEN);
					int beginpos = ((JGameSquare)e.getComponent()).getPosition();
					beginDraw = s;
					if(beginDraw.getPositionX() == NumberPos.getPositionX()){
						int diffy = 0;
						if(beginDraw.getPositionY()>NumberPos.getPositionY()){
							diffy = beginDraw.getPositionY()-NumberPos.getPositionY();
							for(int i=0;i<gg.getGridSize().height*gg.getGridSize().width;i++){
								if(this.mainWin.getMainPanel().getComponent(i).getBackground().equals(Color.BLUE))
									this.mainWin.getMainPanel().getComponent(i).setBackground(defaultColor);
							}
							for(int i=beginpos, j=0;j<diffy;i=i-this.mainWin.getCols(),j++){
								this.mainWin.getMainPanel().getComponent(i).setBackground(Color.GREEN);
							}
							
						}
						else {
							diffy = NumberPos.getPositionY()-beginDraw.getPositionY();
							for(int i=0;i<gg.getGridSize().height*gg.getGridSize().width;i++){
								if(this.mainWin.getMainPanel().getComponent(i).getBackground().equals(Color.BLUE))
									this.mainWin.getMainPanel().getComponent(i).setBackground(defaultColor);
							}
							for(int i=beginpos, j=0;j<diffy;i=i+this.mainWin.getCols(),j++){
								this.mainWin.getMainPanel().getComponent(i).setBackground(Color.GREEN);
							}
						}
					}
					else if(beginDraw.getPositionY() == NumberPos.getPositionY()){}
					wasBlue = false;
					drawCount++;
				}
				else if(drawCount == 1){
					endDraw = s;
					((JGameSquare)e.getComponent()).setBackground(Color.RED);
					setRaysForNumber(beginDraw, endDraw);
					e.getComponent().setBackground(defaultColor);
					drawCount = 0;
					drawing = false;
				}
			}
			/*else{
				RaySquare tempRs = s.getAsRaySquare();
				s = tempRs; // overwrite the old Square Object with the new one
			}*/
		}
		else if(!drawing && e.getButton() == MouseEvent.BUTTON3){ // Double click: Number Square
			NumberSquare tempNs = s.getAsNumberSquare();
			s = tempNs;
			gs.setRepresentingSquare(s);
			((JGameSquare)e.getSource()).clearPaint();
			((JGameSquare)e.getSource()).setText("?");
			System.out.println("Max Cols: "+maxAvailableCols);
			//JOptionPane.showMessageDialog(null, "Select Number!");
			//System.out.println(beginDraw);
			//drawing = true;
		}
		if(!drawing){
			if(s.getClass().equals(new NumberSquare().getClass())) {
				int num = 0;
				do{
		            String zahlText = JOptionPane.showInputDialog("Zahl?");
		            if( zahlText != null) { // catch abort
		            	num = Integer.parseInt(zahlText); // Number val
		            	maxAvailableCols -= (num+1);
		                ((NumberSquare)s).setNumber(num);
		                ((JLabel)gs.getComponent(0)).setText(zahlText);
		                NumberPos = s;		  
		                int currpos = gs.getPosition();
		                iNumberPos = currpos;
		                for(int i=currpos-num;i<=currpos+num;i++){
		                	if(i!=currpos)
		                		this.mainWin.getMainPanel().getComponent(i).setBackground(Color.BLUE);
		                }
		                for(int i=currpos-(this.mainWin.getCols()*num);i<=currpos+(this.mainWin.getCols()*num);i=i+this.mainWin.getCols()){
		                	if(i!=currpos)
		                		this.mainWin.getMainPanel().getComponent(i).setBackground(Color.BLUE);
		                }
		                clrBegin = Color.GREEN;
		                clrEnd   = Color.RED;
		                drawing = true;
		            }
		            else{
		            	num = 0;
		                ((JLabel)gs.getComponent(0)).setText("");
		            }
		            	
					gs.clearPaint(); // remove previous lines
					gs.getTextLabel().setText(zahlText);
				}while(num>(this.mainWin.getCols()+this.mainWin.getRows()-2));
			} 
			/*else if(s.getClass().equals(new RaySquare().getClass())) {
				// Direction
				if(e.getButton() == MouseEvent.BUTTON1) { // BUTTON1 = left mouse
					((RaySquare)s).setDirection(Direction.HORIZONTAL);
					//paint a h line
					gs.drawLine(Direction.HORIZONTAL);
				}
				else if(e.getButton() == MouseEvent.BUTTON3) { // BUTTON3 = right mouse
					((RaySquare)s).setDirection(Direction.VERTICAL);
					//paint a v line
					gs.drawLine(Direction.VERTICAL);
				}
			}*/
		}
		// Save changes on the square to the model
		this.gg.getSquares().set(gs.getPosition(), s);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(drawing){
			if(drawCount==0){
				if(e.getComponent().getBackground().equals(Color.BLUE)){
					wasBlue = true;
				}
				e.getComponent().setBackground(Color.GREEN);
			}
			else{
				if(e.getComponent().getBackground().equals(Color.BLUE)){
					wasBlue = true;
				}
				if(e.getComponent().getBackground().equals(Color.GREEN))
				e.getComponent().setBackground(Color.RED);
			}
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(drawing){
			if(e.getComponent().getBackground().equals(Color.GREEN) || e.getComponent().getBackground().equals(Color.RED))
				e.getComponent().setBackground(defaultColor);
				if(drawCount == 1 && e.getComponent().getBackground().equals(Color.GREEN)){
					e.getComponent().setBackground(Color.GREEN);
				}
				else if(drawCount == 1 && e.getComponent().getBackground().equals(Color.RED)){
					e.getComponent().setBackground(Color.RED);
				}
					
			if(wasBlue){
				e.getComponent().setBackground(Color.BLUE);
				wasBlue = false;
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

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

	@Override
	public void caretUpdate(CaretEvent arg0) {
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
	
	private void setRaysForNumber(SquareBase begin, SquareBase end){
		int beginy = begin.getPositionY();
		int beginx = begin.getPositionX();
		int endy = end.getPositionY();
		int endx = end.getPositionX();
		int numy = NumberPos.getPositionY();
		int numx = NumberPos.getPositionX();
		//if(){}
		System.out.println("Begin:\nX:\t"+begin.getPositionX()+"\nY:\t"+begin.getPositionY());
		System.out.println("End:\nX:\t"+end.getPositionX()+"\nY:\t"+end.getPositionY());
	}
}
