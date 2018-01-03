package handwrittenfont;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

public class DrawPanel extends JPanel
{
	double t;
	double k = .025;
	Graphics2D g2;
	Map<Set<BezierCurve>, Integer> curvesWithThickness;
	Iterator<Set<BezierCurve>> curveSet;
	
	public DrawPanel(Map<Set<BezierCurve>, Integer> curves) 
	{
		setBackground(Color.LIGHT_GRAY);
		curvesWithThickness = curves;
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
		curveSet = curvesWithThickness.keySet().iterator();
		
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
//					System.out.println("Current curve: " + currentCurve);
					List<ControlPoint> currentCrvPoints = currentCurve.getControlPointsOfCurve();
//					System.out.println("Current Control Points: " + currentCrvPoints);
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
//					System.out.println("Current X1: " + x1);
					y1 = currentCrvPoints.get(0).getY();
					x2 = currentCrvPoints.get(1).getX();
					y2 = currentCrvPoints.get(1).getY();
					
//					System.out.println(String.format("Testing - X1: %f Y1: %f X2: %f Y2: %f", (x1 % 200), (y1%200), (x2%200), (y2%200)));
					
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
//					for(t = k; t <= 1; t += k)
//					{
////						x2 = (currentCrvPoints.get(0).getX() + t * (-currentCrvPoints.get(0).getX() * 3 + t * (3 * currentCrvPoints.get(0).getX() - currentCrvPoints.get(0).getX() * t)))
////								+ t * (3 * currentCrvPoints.get(1).getX() + t * (-6 * currentCrvPoints.get(1).getX() + currentCrvPoints.get(1).getX() * 3 * t))
////								+ t * t * (currentCrvPoints.get(2).getX() * 3 - currentCrvPoints.get(2).getX() * 3 * t)
////								+ currentCrvPoints.get(3).getX() * t * t * t;
////						
////						y2 = (currentCrvPoints.get(0).getY() + t * (-currentCrvPoints.get(0).getY() * 3 + t * (3 * currentCrvPoints.get(0).getY() - currentCrvPoints.get(0).getY() * t)))
////								+ t * (3 * currentCrvPoints.get(1).getY() + t * (-6 * currentCrvPoints.get(1).getY() + currentCrvPoints.get(1).getY() * 3 * t))
////								+ t * t * (currentCrvPoints.get(2).getY() * 3 - currentCrvPoints.get(2).getY() * 3 * t)
////								+ currentCrvPoints.get(3).getY() * t * t * t;
//						x2 = (currentCrvPoints.get(0).getX() + t * (-currentCrvPoints.get(0).getX() * 3 + t * (3 * currentCrvPoints.get(0).getX() - currentCrvPoints.get(0).getX() * t)))
//								+ t * (3 * currentCrvPoints.get(1).getX() + t * (-6 * currentCrvPoints.get(1).getX() + currentCrvPoints.get(1).getX() * 3 * t))
//								+ t * t * (currentCrvPoints.get(0).getX() * 3 - currentCrvPoints.get(0).getX() * 3 * t)
//								+ currentCrvPoints.get(0).getX() * t * t * t;
//						
//						y2 = (currentCrvPoints.get(0).getY() + t * (-currentCrvPoints.get(0).getY() * 3 + t * (3 * currentCrvPoints.get(0).getY() - currentCrvPoints.get(0).getY() * t)))
//								+ t * (3 * currentCrvPoints.get(1).getY() + t * (-6 * currentCrvPoints.get(1).getY() + currentCrvPoints.get(1).getY() * 3 * t))
//								+ t * t * (currentCrvPoints.get(0).getY() * 3 - currentCrvPoints.get(0).getY() * 3 * t)
//								+ currentCrvPoints.get(0).getY() * t * t * t;
//						g2.setStroke(new BasicStroke(3));
//						g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
//						x1 = x2;
//						y1 = y2;
//					}
				}
			}
		}
		catch(NullPointerException npe)
		{
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.red);
			Font font = new Font("Arial", Font.PLAIN, 14);
			g2.setFont(font);
//			System.out.println("Length of String: " + new StringBuilder("No Curves for the selected Letter").length());
//			System.out.println("X Coordinate: " + (getWidth()/2 - (new StringBuilder("No Curves for the selected Letter").length() * 2)));
			g2.drawString("No Curves for the selected Letter", (getWidth()/2 - (new StringBuilder("No Curves for the selected Letter").length() * 2)), getHeight()/2);
		}
		
		
//		for(int i = 0; i < numPoints; i++)
//		{
//			g.fillOval((int)controlPoints.get(i).getX() - 2, (int)controlPoints.get(i).getY() - 2, 4, 4);
//			if(numPoints >=1 && i < (numPoints - 1))
//			{
//				g2.setStroke(new BasicStroke(3));
//				g2.setColor(Color.black);
//				g2.drawLine((int)controlPoints.get(i).getX(), (int)controlPoints.get(i).getY(), (int)controlPoints.get(i + 1).getX(), (int)controlPoints.get(i + 1).getY());
//			}
//		}
//		if(numPoints == 4)
//		{
//			double x1, x2, y1, y2;
//			x1 = controlPoints.get(0).getX();
//			y1 = controlPoints.get(0).getY();
//			for(t = k; t <= 1; t += k)
//			{
//				x2 = (controlPoints.get(0).getX() + t * (-controlPoints.get(0).getX() * 3 + t * (3 * controlPoints.get(0).getX() - controlPoints.get(0).getX() * t)))
//					+ t * (3 * controlPoints.get(1).getX() + t * (-6 * controlPoints.get(1).getX() + controlPoints.get(1).getX() * 3 * t))
//					+ t * t * (controlPoints.get(2).getX() * 3 - controlPoints.get(2).getX() * 3 * t)
//					+ controlPoints.get(3).getX() * t * t * t;
//				
//				y2 = (controlPoints.get(0).getY() + t * (-controlPoints.get(0).getY() * 3 + t * (3 * controlPoints.get(0).getY() - controlPoints.get(0).getY() * t)))
//				+ t * (3 * controlPoints.get(1).getY() + t * (-6 * controlPoints.get(1).getY() + controlPoints.get(1).getY() * 3 * t))
//				+ t * t * (controlPoints.get(2).getY() * 3 - controlPoints.get(2).getY() * 3 * t)
//				+ controlPoints.get(3).getY() * t * t * t;
//				
//				g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
//				x1 = x2;
//				y1 = y2;
//			}
//		}
	}
}
