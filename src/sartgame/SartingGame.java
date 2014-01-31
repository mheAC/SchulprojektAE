package sartgame;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Closeable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import play_gui.MainWindow;

import create_gui.Create_MainWindow;

public class SartingGame implements ActionListener{
	private JButton createBtn;
	private JButton playBtn;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SartingGame();
	}
	
	private SartingGame(){
		buildWindow();
	}
	
	private void buildWindow(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		createBtn = new JButton("Open Editor Mode");
		playBtn = new JButton("Play Game");
		
		createBtn.addActionListener(this);
		playBtn.addActionListener(this);
		
		panel.add(createBtn);
		panel.add(playBtn);
		
		frame.add(panel);
		frame.setResizable(false);
		frame.setSize(200, 100);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(createBtn)){
			new Create_MainWindow();
		}
		else if(e.getSource().equals(playBtn)){
			new MainWindow();
		}
	}
}
