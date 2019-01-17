package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main
{
	JFrame frame;
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public Main()
	{
		initFrame();
		setupMenu();
	}
	
	private void setupMenu()
	{
		JPanel menu = new JPanel(new GridLayout());
		for(JButton cur : Buttons)
		{
			
		}
	}
	
	private void initFrame()
	{
		frame = new JFrame("Hotdog");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.pack();
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.repaint();
	}
	
	private static enum Buttons
	{
		
	}

}
