package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
		price.setLayout(new GridLayout(0, 2));
		for(Foods fd : Foods.values())
		{
			price.add(new PriceLabel(fd));
		}
		price.add(new JLabel()
				{
					private float getTotal()
					{
						float total = 0;
						for(Foods cur : Foods.values())
						{
							total += cur.price * cur.count;
						}
						return total;
					}
					
					@Override
					public void paint(Graphics g)
					{
						setText("Total: $" + df.format(getTotal()));
						super.paint(g);
					}
				});
		frame.add(price, BorderLayout.EAST);
		frame.pack();
		frame.repaint();
	}
	
	private void setupMenu()
	{
		UpdateListener ul = new UpdateListener();
		menu = new JPanel(new GridLayout(0, 3, 5, 5));
		for(Foods cur : Foods.values())
		{
			cur.setUpdateListener(ul);
			menu.add(cur.panel);
		}
		frame.add(menu, BorderLayout.CENTER);
		frame.pack();
		frame.repaint();
	}

	private void initFrame()
	{
		frame = new JFrame("Hotdog");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.pack();
		frame.setVisible(true);
		frame.setSize(new Dimension(2000, 500));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.repaint();
	}
	
	public static enum Foods
	{
		hotdog("Hotdog", 2.5f), burger("Hamburger", 5), test("Test", 96.221f), fries("Fries", 2), brat("Brat", 3.5f), soda("Soda", 2), water("Water", 0);
		
		private static HashMap<String, Foods> map = new HashMap<>();
		
		private int count = 0;
		private String str;
		private float price;
		private JPanel panel;
		private JButton addBtn, subBtn;
		private UpdateListener ul = null;
		
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
				if(type.ul != null)
				{
					ul.actionPerformed(e);
				}
				else
				{
					System.err.println("");
				}
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
				if(type.ul != null)
				{
					ul.actionPerformed(e);
				}
				else
				{
					System.err.println("");
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
		
		public void setUpdateListener(UpdateListener input)
		{
			ul = input;
		}
	}
	
	public class UpdateListener implements ActionListener
	{
		//Repaints entire window, with button press, not just the button
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() instanceof JButton)
			{
				JButton b = (JButton)e.getSource();
				frame.pack();
				frame.repaint();
			}
		}
	}
	
	public static class PriceLabel extends JLabel
	{
		Foods fd;
		public PriceLabel(Foods fd)
		{
			super();
			this.fd = fd;
			this.setText(fd.toString() + ": " + fd.count + " at $" + df.format(fd.getPrice()) + " each.");
		}
		
		@Override
		public void paint(Graphics g)
		{
			this.setText(fd.toString() + ": " + fd.count + " at $" + df.format(fd.getPrice()) + " each.");
			super.paint(g);
		}
	}
}
