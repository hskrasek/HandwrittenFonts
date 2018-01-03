package handwrittenfont;
/**
 * Current...this is just a Gray window.... 
 * 
 * 
 * OMG!!!! USE GRAPHICS2D for THIS PROJECT
 * @author hunterskrasek
 */
import java.awt.event.*;

import javax.swing.*;
import java.util.Iterator;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.List;

public class DemoApp extends JPanel
{
    private int r = 8;
    JLabel label1;
    JLabel label2;
    Map<BezierCurveImpl, Letter> test;
    CurvePanel curvePanel;
    JMenuBar menuBar;
    JMenu fileMenu;
    
	public DemoApp()
	{
		setBackground(Color.lightGray);
		setSize(400, 400);
		
		setName("HWFG Demo Application");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JComboBox letterList = new JComboBox(Letter.values());
		letterList.setSelectedIndex(0);
		
		letterList.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JComboBox cBox = (JComboBox)e.getSource();
				Letter selecterLetter = (Letter)cBox.getSelectedItem();
				curvePanel.setCurveToDraw(selecterLetter);
			}
		});
		
		setupMenu();
		c.anchor = GridBagConstraints.CENTER;
        c.weightx = 1.5;
        c.gridx = 2;
        c.gridy = 0;
        add(letterList, c);

        curvePanel = new CurvePanel();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add(curvePanel, c);
        curvePanel.setCurveToDraw(Letter.ZERO);
        
	}
	
	private void setupMenu()
	{
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem item = new JMenuItem("Open");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				selectFontFile();
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileMenu.add(item);
		
		item = new JMenuItem("Quit");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		fileMenu.add(item);
		
		menuBar.add(fileMenu);
		add(menuBar);
	}
	
	private void selectFontFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		int retValue = fileChooser.showOpenDialog(null);
		
		if(retValue == JFileChooser.APPROVE_OPTION)
		{
			File fontFile = fileChooser.getSelectedFile();
			FontBuilder fontBuilder = new FontBuilderImpl();
			Map<Letter, Map<Set<BezierCurve>, Integer>> temp = fontBuilder.readFile(fontFile);
			curvePanel.setLetterCurveSet(temp);
		}
	}
	
	public void createAndShowGUI() 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
		        JFrame.setDefaultLookAndFeelDecorated(false);
		        JFrame frame = new JFrame("HWFG Demo Application");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setResizable(false);
//		        frame.setLocation(400, 0);
		        JComponent newContentPane = new DemoApp();
		        newContentPane.setOpaque(true);
		        frame.setContentPane(newContentPane);
		        frame.pack();
		        frame.setVisible(true);
            }
		});
    }
	
	public static void main(String args[])
	{
		DemoApp app = new DemoApp();
		app.createAndShowGUI();
	}
	
	class CurvePanel extends JPanel
	{
		int numPoints;
		ArrayList<ControlPointImpl> controlPoints;
		double t;
		double k = .025;
		Graphics2D g2;
		Set<BezierCurve> testCurve;
		BezierCurve bezCurve;
		BezierCurve bezCurve2;
		List<ControlPoint> controlPoints2;
		Iterator<Set<BezierCurve>> curveSet;
		Random rand;
		Map<Letter, Map<Set<BezierCurve>, Integer>> letterAndCurveSet;
		public CurvePanel() 
		{
			rand = new Random();
			controlPoints = new ArrayList<ControlPointImpl>();
			numPoints = 0;
			this.setPreferredSize(new Dimension(500, 500));
			setBackground(Color.GRAY);
		}
		
		public void setCurveToDraw(Letter curveToDrawLetter)
		{
			curveSet = null;
			try
			{
				curveSet = letterAndCurveSet.get(curveToDrawLetter).keySet().iterator();
			}
			catch(NullPointerException npe)
			{
				
			}
			repaint();
		}
		
		private void debugMap()
		{
			int count = 1;
			Iterator<Letter> letterIterator = letterAndCurveSet.keySet().iterator();
			System.out.println("Map Count: " + letterAndCurveSet.size());
			while(letterIterator.hasNext())
			{
				Letter theLetter = letterIterator.next();
				Map<Set<BezierCurve>, Integer> letterEntry = letterAndCurveSet.get(theLetter);
				System.out.println("Map Count 2 "+ letterEntry.size());
				Iterator<Set<BezierCurve>> curveIt = letterEntry.keySet().iterator();
				
				while(curveIt.hasNext())
				{
					Iterator<BezierCurve> theCurveIt = curveIt.next().iterator();
					
					while(theCurveIt.hasNext())
					{
						BezierCurve currentCurve = theCurveIt.next();
						
						System.out.println("Letter: " + theLetter + " Curve #" + count + ": " + currentCurve);
						count++;
					}
					count = 1;
				}
			}
		}
		
		public void setLetterCurveSet(Map<Letter, Map<Set<BezierCurve>, Integer>> theLCSet)
		{
			letterAndCurveSet = theLCSet;
//			debugMap();
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			this.drawCurve(g);
		}
		
		void drawCurve(Graphics g)
		{
			g.setColor(Color.BLACK);
			g2 = (Graphics2D) g;
			Iterator<BezierCurve> curveIterator;
			int count = 1;
			
			try
			{
				while(curveSet.hasNext())
				{
					Set<BezierCurve> tempSet = curveSet.next();
					curveIterator = tempSet.iterator();
					if(tempSet.isEmpty())
					{
						g2.setStroke(new BasicStroke(2));
						g2.setColor(Color.RED);
						Font font = new Font("Arial", Font.PLAIN, 14);
						g2.setFont(font);
						g2.drawString("No Curves for the selected Letter", (getWidth()/2 - (new StringBuilder("No Curves for the selected Letter").length() * 2)), getHeight()/2);
					}
					while(curveIterator.hasNext())
					{
						BezierCurve currentCurve = curveIterator.next();
//						System.out.println("Current curve # " +count + " : " + currentCurve);
						List<ControlPoint> currentCrvPoints = currentCurve.getControlPointsOfCurve();
//						System.out.println("Current Control Points: " + currentCrvPoints);
						Iterator<ControlPoint> cpIterator = currentCrvPoints.iterator();
						while(cpIterator.hasNext())
						{
							ControlPoint currPoint = cpIterator.next();
							if(currentCrvPoints.size() == 2)
							{
								g.setColor(Color.BLACK);
								g.fillOval((int)currPoint.getX() - 2, (int)currPoint.getY() - 2, 3, 3);
							}
							else if(currentCrvPoints.size() == 4)
							{
								g.setColor(Color.BLACK);
								g.fillOval((int)currPoint.getX() - 2, (int)currPoint.getY() - 2, 3, 3);
							}
						}
						double x1, x2, y1, y2;
						x1 = currentCrvPoints.get(0).getX();
//						System.out.println("Current X1: " + x1);
						y1 = currentCrvPoints.get(0).getY();
						x2 = currentCrvPoints.get(1).getX();
						y2 = currentCrvPoints.get(1).getY();
						
						g2.setColor(Color.BLACK);
						int thinkness = currentCurve.getThickness();
						if(thinkness > 0)
						{
							for(int i = 0; i < thinkness; i++)
							{
								g2.drawLine((int)x1 + i, (int)y1, (int)x2 + i, (int)y2);
							}
						}
						else
						{
							for(int i = thinkness; i < 0; i++)
							{
								g2.drawLine((int)x1 + i, (int)y1, (int)x2 + i, (int)y2);
							}
						}
						count++;
					}
				}
			}
			catch(NullPointerException npe)
			{
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.red);
				Font font = new Font("Arial", Font.PLAIN, 14);
				g2.setFont(font);
//				System.out.println("Length of String: " + new StringBuilder("No Curves for the selected Letter").length());
//				System.out.println("X Coordinate: " + (getWidth()/2 - (new StringBuilder("No Curves for the selected Letter").length() * 2)));
				g2.drawString("No Curves for the selected Letter", (getWidth()/2 - (new StringBuilder("No Curves for the selected Letter").length() * 2)), getHeight()/2);
			}
		}
	}
}
