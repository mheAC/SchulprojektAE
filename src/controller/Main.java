package controller;

import engine.Direction;
import engine.GameGrid;
import engine.NumberSquare;
import engine.RaySquare;
import engine.SquareBase;
import engine.UntypedSquare;
import engine.storage_handler.StorageHandler;
import gui.JGameSquare;
import gui.JOpenFileDialog;
import gui.MainWindow;
import gui.StartWindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO: Javadoc kontrollieren
/**
 * The Class Main.
 */
public class Main implements ChangeListener, ActionListener, MouseListener , CaretListener {
	
	/** The config win. */
	private StartWindow configWin;
	
	/** The main win. */
	private MainWindow mainWin;
	
	/** The st h. */
	private StorageHandler stH;
	
	/** The begin draw. */
	private SquareBase beginDraw;
	
	/** The end draw. */
	private SquareBase endDraw;
	
	/** The Number pos. */
	private SquareBase NumberPos;
	
	/** The drawing. */
	private boolean drawing;
	
	/** The draw count. */
	private int drawCount;
	
	/** The gs pos. */
	private int gsPos;
	
	/** The Field length. */
	private int FieldLength;
	
	/** The properties. */
	private Properties properties;
	
	/** The MousePressed GameSquare */
	private JGameSquare firstGS;
	private SquareBase firstSB;
	
	/** The MouseReleased GameSquare */
	private JGameSquare lastGS;
	private SquareBase lastSB;
	
	/** To count the Lines */
	private int startpos;
	private int endpos;
	//private int maxAvailableCols;
	
	// Storage for current gameData
	/** The gg. */
	GameGrid gg;
	
	/**
	 * Instantiates a new main.
	 *
	 * @throws Exception the exception
	 */
	public Main() throws Exception {
		new JGameSquare().getBackground();
		beginDraw = null;
		gsPos = 0;
		FieldLength = 0;
		endDraw = null;
		drawCount = 0;
		NumberPos = null;
		drawing = false;
		firstGS = null;
		lastGS = null;
		startpos = 0;
		endpos = 0;
		lastSB = null;
		firstSB = null;
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
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			new Main();
		} catch (Exception e) { e.printStackTrace(); }
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
			/*String fileName = JOptionPane.showInputDialog("Unter welchem Namen soll die Datei gespeichert werden?");
			if(fileName != null){
				try {
					this.stH.persist(gg, properties.getProperty("saveGamePath")+(fileName+".json"));
				} catch (Exception e3) { e3.printStackTrace(); }
			}
			return; // break here */
			// parent component of the dialog
			JFrame savedialog = new JFrame();
			 
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Unter welchem Namen soll die Datei gespeichert werden?");   
			 
			int userSelection = fileChooser.showSaveDialog(savedialog);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = new File( fileChooser.getSelectedFile().getPath()+"json");
			    this.stH.persist(gg, fileToSave);
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			}
			
		}
		/*
		 * Handling of add row btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getAddHeightBtn().getActionCommand())) {
			gg.addRow();
			return;
		}
		/*
		 * Handling of remove row btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getRemoveHeightBtn().getActionCommand())) {
			gg.removeRow();
			return;
		}
		/*
		 * Handling of add col btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getAddWidthBtn().getActionCommand())) {
			// TODO: add height btn action
			gg.addColumn();
			return;
		}
		/*
		 * Handling of remove col btn
		 */
		else if(e.getActionCommand().equals(this.mainWin.getRemoveWidthBtn().getActionCommand())) {
			gg.removeColumn();
			return;
		}
		/*
		 * Handling of INFO button
		 */
		else if(e.getActionCommand().equals(this.configWin.getInfoBtn().getActionCommand())) {
			final ImageIcon ii = new ImageIcon(this.getClass().getResource("/gui/elements/resources/edit.png"));
			StringBuilder text = new StringBuilder("Lichtstrahlen Spiel  - AE@BWV-Aachen | 2013\n\nGruppe:\n"); 
			List<String> arr = new ArrayList<String>();
			arr.add("\tBassauer\n");
			arr.add("\tBolz\n");
			arr.add("\tCongar\n");
			arr.add("\tGriesbach\n");
			arr.add("\tHerpers\n");
			arr.add("\tSoiron\n");
			while(!arr.isEmpty()) {
				int pos = (int)(arr.size()*Math.random()); // make it random!
				text.append(arr.get(pos));
				arr.remove(pos);
			}
			JOptionPane.showMessageDialog(null, text.toString(), "Lichtstrahlen", JOptionPane.INFORMATION_MESSAGE, ii);
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
	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
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
						//this.gg.setSquare(NumberPos);
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
		this.gg.setSquare(s.getPositionX(),s.getPositionY(),s);
	}


        /**
         * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseEntered(MouseEvent e) {}
        
        /**
         * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseExited(MouseEvent e) {}
        
        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         */
        @Override
        public void mousePressed(MouseEvent e) {
        	if(drawing){
	        	firstGS = (JGameSquare)e.getComponent();
	        	firstSB = firstGS.getRepresentedSquare();
	        	System.out.println( firstSB.getPositionX() + " " + firstSB.getPositionY());
        	}
        }
        
        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseReleased(MouseEvent e) {
        	if(drawing){
	        	lastGS = (JGameSquare)e.getComponent();
	        	lastSB = lastGS.getRepresentedSquare();
	        	System.out.println( lastSB.getPositionX() + " " + lastSB.getPositionY());
	        	drawCount = 0;
				drawing = false;
				((NumberSquare)NumberPos).setNumber(setRaysForNumber(firstSB, lastSB));
				((JGameSquare)this.mainWin.getMainPanel().getComponent(gsPos)).getTextLabel().setText(FieldLength+"");
				FieldLength = 0;
        	}
        }
 
        /**
         * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
         */
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

	/**
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
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
	
	
	/**
	 * Reset bg color.
	 *
	 * @return true, if successful
	 */
	private boolean resetBGColor(){
		boolean trough = false;
		for(int i=0;i<this.mainWin.getRows();i++)
			for(int j=0;j<this.mainWin.getCols();j++){
				if(this.mainWin.getJGameSquareAt(j, i).getBackground().equals(Color.BLUE) ||
				   this.mainWin.getJGameSquareAt(j, i).getBackground().equals(Color.GREEN))
					//this.mainWin.getJGameSquareAt(j, i).setBg(defaultColor);
					this.mainWin.getJGameSquareAt(j, i).setBackground(Color.GRAY);
					this.mainWin.getJGameSquareAt(j, i).setRepresentingSquare(new UntypedSquare(j, i));
					trough = true;
			}
		return trough;
	}
	
	/**
	 * Sets the ray from start to end.
	 *
	 * @param start the start
	 * @param lightSource the light source
	 * @param end the end
	 * @return the int
	 */
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
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController() );
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL,i, start.height));
								FieldLength++;
							}
						}
					}
					else if(start.width<lightSource.width){
						for(int i=start.width;i < lightSource.width;i++){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.VERTICAL,i, start.height));
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
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler() ,gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,start.width, i));
								FieldLength++;
							}
						}
					}
					else if(start.height<lightSource.height){
						for(int i=start.height;i < lightSource.height;i++){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,start.width, i));
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
								this.mainWin.getJGameSquareAt(i, lightSource.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(i, lightSource.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL,i, lightSource.height));
								FieldLength++;
							}
						}
					}
					else if(lightSource.width<end.width){
						for(int i=lightSource.width+1;i <= end.width;i++){
							if(this.mainWin.getJGameSquareAt(i, lightSource.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, lightSource.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(i, lightSource.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL,i, lightSource.height));
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
								this.mainWin.getJGameSquareAt(lightSource.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(lightSource.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,lightSource.width,i));
								FieldLength++;
							}
						}
					}
					else if(lightSource.height<end.height){
						for(int i=lightSource.height;i <= end.height;i++){
							if(this.mainWin.getJGameSquareAt(lightSource.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(lightSource.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(lightSource.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,lightSource.width,i));
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
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL,i,start.height));
								FieldLength++;
							}
						}
					}
					else if(start.width<lightSource.width){
						for(int i=start.width;i < lightSource.width;i++){
							if(this.mainWin.getJGameSquareAt(i, start.height) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(i, start.height).drawLine(Direction.HORIZONTAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(i, start.height).setRepresentingSquare(new RaySquare(Direction.HORIZONTAL,i,start.height));
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
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,start.width,i));
								FieldLength++;
							}
						}
					}
					else {
						for(int i=start.height;i < lightSource.height;i++){
							if(this.mainWin.getJGameSquareAt(start.width, i) != this.mainWin.getJGameSquareAt(lightSource.width, lightSource.height)){
								this.mainWin.getJGameSquareAt(start.width, i).drawLine(Direction.VERTICAL, this.gg.getLogHandler(),gg, this.gg.getController());
								this.mainWin.getJGameSquareAt(start.width, i).setRepresentingSquare(new RaySquare(Direction.VERTICAL,start.width,i));
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
	
	/**
	 * Mark the way to number square.
	 *
	 * @param col the col
	 * @param row the row
	 */
	private void markTheWayToNumberSquare(int col, int row){
		//Zeilenweise abw???rts
		for(int i = row+1; i < this.mainWin.getRows(); i++)
			if(i != row){
				if(this.mainWin.getJGameSquareAt(col, i) != null && 
				   this.mainWin.getJGameSquareAt(col, i).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(col, i).setBackground(Color.BLUE);
				else
					break;
				
			}
		//Zeilenweise aufw???rts
		for(int i = row-1; i > -1; i--)
			if(i != row){
				if(this.mainWin.getJGameSquareAt(col, i) != null && 
				   this.mainWin.getJGameSquareAt(col, i).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(col, i).setBackground(Color.BLUE);
				else
					break;
			}
		
		//Spaltenweise vorw???rts
		for(int i = col+1; i < this.mainWin.getCols(); i++)
			if(i != col){
				if(this.mainWin.getJGameSquareAt(i, row) != null &&
				   this.mainWin.getJGameSquareAt(i, row).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(i, row).setBackground(Color.BLUE);
				else
					break;
			}
		//Spaltenweise vorw???rts
		for(int i = col-1; i > -1; i--)
			if(i != col){
				if(this.mainWin.getJGameSquareAt(i, row) != null &&
				   this.mainWin.getJGameSquareAt(i, row).getRepresentedSquare().toString() == "")
					this.mainWin.getJGameSquareAt(i, row).setBackground(Color.BLUE);
				else
					break;
			}
	}
	
	
	/**
	 * Sets the rays for number.
	 *
	 * @param begin the begin
	 * @param end the end
	 * @return the int
	 */
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
