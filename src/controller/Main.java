package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;
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
	private boolean drawing;
	private int drawCount;
	private int gsPos;
	private int FieldLength;
	private Color defaultColor;
	private Properties properties;
	//private int maxAvailableCols;
	
	// Storage for current gameData
	GameGrid gg;
	
	public Main() throws Exception {
		//for Mouse Motion Listener
		defaultColor = new JGameSquare().getBackground();
		beginDraw = null;
		gsPos = 0;
		FieldLength = 0;
		endDraw = null;
		drawCount = 0;
		NumberPos = null;
		drawing = false;
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
		 * Handling of add row btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getAddHeightBtn().getActionCommand())) {
			// TODO: add height btn action
			List<SquareBase> squares = gg.getSquares();
			Dimension dim = gg.getGridSize();
			for (int ii = 0; ii < (int)dim.getWidth();ii++) {
				squares.add(new UntypedSquare());
			}
			gg.setGridSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()+1));
			gg.asignSquareCoordinates();
			//mainWin.setGameGridData(gg);
			//mainWin.setRows((int)gg.getGridSize().getHeight());
			//mainWin.setCols((int)gg.getGridSize().getWidth());
			//mainWin.buildWindow();
			
			// TODO: update main window to new size in gg
			this.mainWin.getMainPanel().updateUI();
 			this.mainWin.getJFrame().update(this.mainWin.getMainPanel().getGraphics());
			
			return; // break here 
		}
		/*
		 * Handling of remove row btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getRemoveHeightBtn().getActionCommand())) {
			int response = JOptionPane.showConfirmDialog(mainWin.getJFrame(), "Beim Entfernen einer Zeile\n"
	                   +"gehen eventuell get�tigte Eingaben\n"
	                   +"verloren. Fortfahren?", "Warnung",
	                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        if (response == JOptionPane.YES_OPTION) {
	        	List<SquareBase> squares = gg.getSquares();
	 			Dimension dim = gg.getGridSize();
	 			for (int ii = (squares.size()-1); ii >= (squares.size() - (int)dim.getWidth()); ii--) {
	 				squares.remove(ii);
	 			}
	 			gg.setGridSize(new Dimension((int)dim.getWidth(), (int)dim.getHeight()-1));
	 			
	 			// TODO: update main window to new size in gg
	 			this.mainWin.getMainPanel().updateUI();
	 			this.mainWin.getJFrame().update(this.mainWin.getMainPanel().getGraphics());
	 			
	        }
			return; // break here 
		}
		/*
		 * Handling of add col btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getAddWidthBtn().getActionCommand())) {
			// TODO: add height btn action
			List<SquareBase> squares = gg.getSquares();
			Dimension dim = gg.getGridSize();
			for (int ii = squares.size();ii>0;ii=(ii-(int)dim.getWidth())) {
				squares.add(ii,new UntypedSquare());
			}
			gg.setGridSize(new Dimension((int)dim.getWidth()+1, (int)dim.getHeight()));
			gg.asignSquareCoordinates();
			//mainWin.setGameGridData(gg);
			//mainWin.setRows((int)gg.getGridSize().getHeight());
			//mainWin.setCols((int)gg.getGridSize().getWidth());
			//mainWin.buildWindow();
			
			// TODO: update main window to new size in gg
			this.mainWin.getMainPanel().updateUI();
 			this.mainWin.getJFrame().update(this.mainWin.getMainPanel().getGraphics());
			return; // break here 
		}
		/*
		 * Handling of remove col btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getRemoveWidthBtn().getActionCommand())) {
			int response = JOptionPane.showConfirmDialog(mainWin.getJFrame(), "Beim Entfernen einer Spalte\n"
	                   +"gehen eventuell get�tigte Eingaben\n"
	                   +"verloren. Fortfahren?", "Warnung",
	                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        if (response == JOptionPane.YES_OPTION) {
	        	List<SquareBase> squares = gg.getSquares();
	 			Dimension dim = gg.getGridSize();
	 			for (int ii = (squares.size()-1); ii > 0; ii=(ii-(int)dim.getHeight())) {
	 				squares.remove(ii);
	 			}
	 			gg.setGridSize(new Dimension((int)dim.getWidth()-1, (int)dim.getHeight()));
	 			gg.asignSquareCoordinates();
	 			
	 			// TODO: update main window to new size in gg
	 			this.mainWin.getMainPanel().updateUI();
	 			this.mainWin.getJFrame().update(this.mainWin.getMainPanel().getGraphics());
	        }
			return; // break here 
		}
		/*
		 * Handling of INFO button
		 */
		else if(e.getActionCommand().equals(this.configWin.getInfoBtn().getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Lichtstrahlen Spiel  - AE@BWV-AAchen | 2013\n\n"
												+ "Gruppe:\n"
												+ "\tCongar\n"
												+ "\tBassauer\n"
												+ "\tHerpers\n"
												+ "\tGriesbach\n"
												+ "\tBolz\n"
												+ "\tSoiron\n"
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
        //maxAvailableCols = gg.getGridSize().width * gg.getGridSize().height;
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
		mainWin.getAddHeightBtn().addActionListener(this);
		mainWin.getAddWidthBtn().addActionListener(this);
		mainWin.getRemoveHeightBtn().addActionListener(this);
		mainWin.getRemoveWidthBtn().addActionListener(this);
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
					((JGameSquare)e.getComponent()).getPosition();
					beginDraw = s;
					if(e.getComponent().getBackground().equals(Color.BLUE)){
						((JGameSquare)e.getComponent()).setBackground(Color.GREEN);
						drawCount++;
					}
					else{
						JOptionPane.showMessageDialog(null, "Feld ist ausserhalb der Reichweite");
					}
				}
				else if(drawCount == 1){
					endDraw = s;
					if(e.getComponent().getBackground().equals(Color.BLUE) 
					|| e.getComponent().getBackground().equals(Color.GREEN) ){
						//if(resetBGColor())
						drawCount = 0;
						drawing = false;
						((NumberSquare)NumberPos).setNumber(setRaysForNumber(beginDraw, endDraw));
						((JGameSquare)this.mainWin.getMainPanel().getComponent(gsPos)).getTextLabel().setText(FieldLength+"");
						FieldLength = 0;
						this.gg.getSquares().set(gsPos, NumberPos);
					}
					else{
						JOptionPane.showMessageDialog(null, "Feld ist ausserhalb der Reichweite");
					}
				}
			}
			/*if(!drawing){
				if(s.getClass().equals(new NumberSquare().getClass())) {
					int row = s.getPositionY();
					int col = s.getPositionX();
					NumberPos = s;
					gs.clearPaint(); // remove previous lines
					gs.getTextLabel().setText("?");
					drawing = true;
	                markTheWayToNumberSquare(col,row);
				} 
			}*/
		}
		else if(!drawing && e.getButton() == MouseEvent.BUTTON3){ // Double click: Number Square
			if(!gs.isset()){
				NumberSquare tempNs = s.getAsNumberSquare();
				s = tempNs;
				gs.setRepresentingSquare(s);
				((JGameSquare)e.getSource()).clearPaint();
				//((JGameSquare)e.getSource()).setText("?");
				int row = s.getPositionY();
				int col = s.getPositionX();
				NumberPos = s;
				gs.clearPaint(); // remove previous lines
				gs.getTextLabel().setText("?");
				gsPos = gs.getPosition();
				drawing = true;
	            markTheWayToNumberSquare(col,row);
			}
			else
				System.out.println(gs.getRepresentedSquare().toString());
		}
		// Save changes on the square to the model
		this.gg.getSquares().set(gs.getPosition(), s);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

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
	
	
	private boolean resetBGColor(){
		boolean trough = false;
		for(int i=0;i<this.mainWin.getRows();i++)
			for(int j=0;j<this.mainWin.getCols();j++){
				if(this.mainWin.getJGameSquareAt(j, i).getBackground().equals(Color.BLUE) ||
				   this.mainWin.getJGameSquareAt(j, i).getBackground().equals(Color.GREEN))
					//this.mainWin.getJGameSquareAt(j, i).setBg(defaultColor);
					this.mainWin.getJGameSquareAt(j, i).clearPaint();
				trough = true;
			}
		return trough;
	}
	
	private int setRayFromStartToEnd(Dimension start, Dimension lightSource, Dimension end){
		if((start.height < lightSource.height && (end.height > lightSource.height || end.width != lightSource.width))
		|| (start.width < lightSource.width && (end.width > lightSource.width || end.height != lightSource.height))
		|| (start.height > lightSource.height && (end.height < lightSource.height || end.width != lightSource.width))
		|| (start.width > lightSource.width && (end.width < lightSource.width || end.height != lightSource.height))) {
			if(resetBGColor()){
				//Draw line from startpoint to numbersquare if it's in horizontal sight
				if(start.height == lightSource.height){
					if(start.width>lightSource.width){
						for(int i=start.width;i > lightSource.width;i--){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
					else if(start.width<lightSource.width){
						for(int i=start.width;i < lightSource.width;i++){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
				}
				//Draw line from startpoint to numbersquare if it's in vertical sight
				else if(start.width == lightSource.width){
					if(start.height>lightSource.height){
						for(int i=start.height;i > lightSource.height;i--){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
					else if(start.height<lightSource.height){
						for(int i=start.height;i < lightSource.height;i++){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
				}
				
				//Draw line from numbersquare to end if it's in horizontal sight
				if(lightSource.height == end.height){
					if(lightSource.width>end.width){
						for(int i=lightSource.width-1;i >= end.width;i--){
							if(this.mainWin.getJGameSquareAt(i, lightSource.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, lightSource.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, lightSource.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
					else if(lightSource.width<end.width){
						for(int i=lightSource.width+1;i <= end.width;i++){
							if(this.mainWin.getJGameSquareAt(i, lightSource.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, lightSource.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, lightSource.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
				}
				//Draw line from numbersquare to end if it's in vertical sight
				else if(lightSource.width == end.width){
					if(lightSource.height>end.height){
						for(int i=lightSource.height;i >= end.height;i--){
							if(this.mainWin.getJGameSquareAt(lightSource.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(lightSource.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(lightSource.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
					else if(lightSource.height<end.height){
						for(int i=lightSource.height;i <= end.height;i++){
							if(this.mainWin.getJGameSquareAt(lightSource.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(lightSource.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(lightSource.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
				}
			}
		}
		else if(end.equals(start)){
			if(resetBGColor()){
				if(start.height == lightSource.height){
					if(start.width>lightSource.width){
						for(int i=start.width;i > lightSource.width;i--){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
					else if(start.width<lightSource.width){
						for(int i=start.width;i < lightSource.width;i++){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL);
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL));
								FieldLength++;
							}
						}
					}
				}
				//Draw line from startpoint to numbersquare if it's in vertical sight
				else if(start.width == lightSource.width){
					if(start.height>lightSource.height){
						for(int i=start.height;i > lightSource.height;i--){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
					else {
						for(int i=start.height;i < lightSource.height;i++){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL);
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL));
								FieldLength++;
							}
						}
					}
				}
			}
		}
		else{
			drawCount = 1;
			drawing = true;
		}
		return FieldLength;
	}
	
	private void markTheWayToNumberSquare(int col, int row){
		//Zeilenweise abw�rts
		for(int i = row+1; i < this.mainWin.getRows(); i++)
			if(i != row){
				if(this.mainWin.getJGameSquareAt(col, i) != null && 
				   this.mainWin.getJGameSquareAt(col, i).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(col, i).setBackground(Color.BLUE);
				else
					break;
				
			}
		//Zeilenweise aufw�rts
		for(int i = row-1; i > -1; i--)
			if(i != row){
				if(this.mainWin.getJGameSquareAt(col, i) != null && 
				   this.mainWin.getJGameSquareAt(col, i).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(col, i).setBackground(Color.BLUE);
				else
					break;
			}
		
		//Spaltenweise vorw�rts
		for(int i = col+1; i < this.mainWin.getCols(); i++)
			if(i != col){
				if(this.mainWin.getJGameSquareAt(i, row) != null &&
				   this.mainWin.getJGameSquareAt(i, row).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(i, row).setBackground(Color.BLUE);
				else
					break;
			}
		//Spaltenweise vorw�rts
		for(int i = col-1; i > -1; i--)
			if(i != col){
				if(this.mainWin.getJGameSquareAt(i, row) != null &&
				   this.mainWin.getJGameSquareAt(i, row).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(i, row).setBackground(Color.BLUE);
				else
					break;
			}
	}
	
	
	private int setRaysForNumber(SquareBase begin, SquareBase end){
		int beginy = begin.getPositionY();
		int beginx = begin.getPositionX();
		int endy = end.getPositionY();
		int endx = end.getPositionX();
		int numy = NumberPos.getPositionY();
		int numx = NumberPos.getPositionX();
		
		Dimension RayStart = new Dimension(beginx, beginy);
		Dimension RayOverNumber = new Dimension(numx, numy);
		Dimension RayEnd = new Dimension(endx, endy);
		return setRayFromStartToEnd(RayStart, RayOverNumber, RayEnd);
	}
}
