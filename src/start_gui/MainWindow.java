package start_gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.omg.CORBA.Object;

import engine.GameGrid;
import engine.NumberSquare;
import engine.SquareBase;

public class MainWindow implements MouseListener{
	int rows, cols;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private GridBagConstraints c;
	private Dimension dim;
	private ArrayList<JPanel> panelList;

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
		panelList = new ArrayList<JPanel>();
	}
	
	public void buildWindow(){
		
		dim.setSize(cols*20, rows*20);
		GridLayout lo = new GridLayout(rows,cols);
		
		c = new GridBagConstraints();
		mainPanel.setMinimumSize(dim);
		mainFrame.setMinimumSize(dim);
		mainPanel.setLayout(lo);
		for (int i = 0; i < rows; i++) {
			  for (int j = 0; j < cols; j++) {
				  c.gridx = j;
				  c.gridy = i;
				  JPanel pTmp = new JPanel();
				  Border border = new BevelBorder( BevelBorder.RAISED );
				  pTmp.setBorder(border);
				  pTmp.addMouseListener(this);
				  panelList.add(pTmp);
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
		//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
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
		JOptionPane.showMessageDialog(null, "X: " + point.x + "\nY: " + point.y, "Location", JOptionPane.OK_OPTION);
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
