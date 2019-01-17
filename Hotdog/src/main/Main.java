package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main
{
	JFrame frame;
	JPanel menu;
	
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
		menu = new JPanel(new GridLayout(0, 3, 5, 0));
		for(Foods cur : Foods.values())
		{
			menu.add(cur.panel);
		}
		frame.add(menu, BorderLayout.CENTER);
	}
	
	private void initFrame()
	{
		frame = new JFrame("Hotdog");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.pack();
		frame.setVisible(true);
		frame.setSize(new Dimension(1000, 500));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.repaint();
	}
	
	public static enum Foods
	{
		//TODO Fix prices and names
		hotdog("Hotdog", 1), burger("Hamburger", 1), fries("Fries", 1);
		
		private static HashMap<String, Foods> map = new HashMap<>();
		
		private int count = 0;
		private String str;
		private float price;
		private JPanel panel;
		private JLabel name;
		private JButton addBtn, subBtn;
		
		private Foods(String str, float price)
		{
			this.str = str;
			this.price = price;
			addBtn = new JButton("Add");
			addBtn.addActionListener((ActionEvent e) -> 
			{
				JButton source = (JButton)e.getSource();
				Foods type = Foods.getValue(source.getActionCommand());
				type.count++;
			});
			addBtn.setActionCommand(str);
			addBtn.setEnabled(true);
			
			subBtn = new JButton("Subtract");
			subBtn.addActionListener((ActionEvent e) -> 
			{
				JButton source = (JButton)e.getSource();
				Foods type = Foods.getValue(source.getActionCommand());
				if(type.count > 0)
				{
					count--;
				}
			});
			subBtn.setActionCommand(str);
			subBtn.setEnabled(true);
			
			JPanel buttons = new JPanel(new GridLayout(0, 2));
			buttons.add(addBtn);
			buttons.add(subBtn);
			
			name = new JLabel(str, JLabel.CENTER);
			name.setBackground(new Color(108, 192, 229));
			name.setOpaque(true);
			panel = new JPanel(new GridLayout(0, 1));
			panel.add(name);
			panel.add(buttons);
			panel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(251, 79, 79), 5));
			
		}
		
		static
		{
			for(Foods cur : values())
			{
				map.put(cur.str, cur);
			}
		}
		
		public static Foods getValue(String text)
		{
			return map.get(text);
		}

		@Override
		public String toString()
		{
			return str;
		}
		
		public float getPrice()
		{
			return price;
		}
	}
}
