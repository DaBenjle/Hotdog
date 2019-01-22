package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class Main
{
	private JFrame frame;
	private JPanel menu, price;
	private static DecimalFormat df;
	
	public static void main(String[] args)
	{
		df = new DecimalFormat("0.00");
		new Main();
	}
	
	public Main()
	{
		initFrame();
		setupMenu();
		addPricePanel();
	}
	
	public void addPricePanel()
	{
		price = new JPanel();
		
	}
	
	private void setupMenu()
	{
		menu = new JPanel(new GridLayout(0, 3, 5, 5));
		for(Foods cur : Foods.values())
		{
			addListenersToComponents(cur.panel, new UpdateListener());
			menu.add(cur.panel);
		}
		frame.add(menu, BorderLayout.CENTER);
		frame.pack();
		frame.repaint();
	}
	
	public static void addListenersToComponents(Container parent, ActionListener listener)
	{
		for(Component comp : parent.getComponents())
		{
			if(comp instanceof JButton)
			{
				JButton btn = (JButton)comp;
				btn.addActionListener(listener);
			}
			else if(comp instanceof Container)
			{
				Container parent2 = (Container)comp;
				addListenersToComponents(parent2, listener);
			}
		}
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
		hotdog("Hotdog", 2.5f), burger("Hamburger", 5), fries("Fries", 2), brat("Brat", 3.5f), soda("Soda", 2), water("Water", 0);
		
		private static HashMap<String, Foods> map = new HashMap<>();
		
		private int count = 0;
		private String str;
		private float price;
		private JPanel panel;
		private JButton addBtn, subBtn;
		
		private Foods(String str, float price)
		{
			this.str = str;
			this.price = price;
			addBtn = new JButton("+");
			addBtn.addActionListener((ActionEvent e) -> 
			{
				JButton source = (JButton)e.getSource();
				Foods type = Foods.getValue(source.getActionCommand());
				type.count++;
			});
			addBtn.setActionCommand(str);
			addBtn.setEnabled(true);
			
			subBtn = new JButton("-");
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
			
			JLabel name = new JLabel(str, JLabel.CENTER);
			name.setBackground(new Color(108, 192, 229));
			name.setOpaque(true);
			
			JLabel priceL = new JLabel('$' + df.format(price), JLabel.CENTER);
			priceL.setBackground(new Color(108, 192, 229));
			priceL.setOpaque(true);
			
			panel = new JPanel(new GridLayout(0, 1));
			panel.add(name);
			panel.add(priceL);
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
	
	public class UpdateListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() instanceof JButton)
			{
				JButton b = (JButton)e.getSource();
				System.out.println(Foods.getValue(b.getName()) + ": " +Foods.getValue(b.getName()).count);
				frame.pack();
				frame.repaint();
			}
		}
	}
}
