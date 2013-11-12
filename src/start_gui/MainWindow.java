package start_gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import engine.GameGrid;
import engine.NumberSquare;
import engine.SquareBase;

public class MainWindow implements MouseListener{
	int rows, cols;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private GridBagConstraints c;
	private Dimension dim;
	private HashMap<JPanel, String> panelIdList;
	private HashMap<String, GridBagConstraints> cellList;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public MainWindow() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		dim = new Dimension();
		panelIdList = new HashMap<JPanel, String>();
		cellList  = new HashMap<String, GridBagConstraints>();
	}
	
	public void buildWindow(){
		
		dim.setSize(cols*20, rows*20);
		GridLayout lo = new GridLayout(rows,cols);

		mainPanel.setMinimumSize(dim);
		mainFrame.setMinimumSize(dim);
		mainPanel.setLayout(lo);
		int id = 0;
		for (int i = 1; i <= rows; i++) {
			  for (int j = 1; j <= cols; j++) {
				  c = new GridBagConstraints();
				  c.gridx = j;
				  c.gridy = i;
				  JPanel pTmp = new JPanel();
				  Border border = new BevelBorder( BevelBorder.RAISED );
				  pTmp.setBorder(border);
				  pTmp.addMouseListener(this);
				  panelIdList.put(pTmp,id+"");
				  cellList.put(id+"", c);
				  id++;
				  mainPanel.add(pTmp,c);
			  }
		}
		mainFrame.add(mainPanel);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e)
            {
                StartWindow swin = new StartWindow();
                swin.show();
                e.getWindow().dispose();
            }
		});
		mainFrame.setLocationRelativeTo(null);
	}
	public void execute(){
		if(cols != 0 && rows != 0){
			GameGrid grid = new GameGrid(cols, rows);
			grid.generateSquares(); // Only call once if you dont want the generated squares to be overwritten
			int i=0;
			for(SquareBase s : grid.getSquares()) {
				System.out.print(" | ");
				if(s.getClass().equals(new NumberSquare().getClass())){ // if we got a Number Square
					System.out.print(((NumberSquare)s).getNumber());
				}
				else {
					/*if(((RaySquare)s).getDirection().equals(Direction.HORIZONTAL)) // draw some ascii for both the directions
						System.out.print('-');
					else
						System.out.print('/');*/
					System.out.print(' ');
				}
				if(++i%rows == 0) {
					System.out.println("\n==========================================");
				}
			}
			buildWindow();
			mainFrame.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "Cols / Rows are not set!!", "Fehler",  JOptionPane.OK_OPTION);
			mainFrame.dispose();
			StartWindow neu = new StartWindow();
			neu.show();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JPanel pp = (JPanel)arg0.getComponent();
		Point point = pp.getLocation();
		String cellid = panelIdList.get(pp);
		GridBagConstraints cell = cellList.get(cellid);
		JOptionPane.showMessageDialog(null, "Location:\n\n\tX: " + point.x + "\n\tY: " + point.y +
											"\nCell:\n\n\tRow: "+cell.gridy + "\n\tColumn: "+cell.gridx, "Location", JOptionPane.OK_OPTION);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
