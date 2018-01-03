package handwrittenfont;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HandwrittenFontPreviewView extends JPanel
{
	JLabel letterTitle;
	HandwrittenFontController theController;
//	JScrollPane scrollPanel;
	public HandwrittenFontPreviewView(HandwrittenFontController controller)
	{
		theController = controller;
		this.setPreferredSize(new Dimension(2000, 800));
//		this.setSize(new Dimension(1000, 725));
		setLayout(null);
		
		createPreview();
	}
	
	private void createPreview()
	{
		Map<Letter, Map<Set<BezierCurve>, Integer>> letterCurves = theController.getCurveMap();
		int x = 0;
		int y = 0;
		int spacer = 10;
		for(int i = 0; i < Letter.values().length; i++)
		{
			
			Letter theLetter = Letter.values()[i];
			Map<Set<BezierCurve>, Integer> currentLetter = letterCurves.get(theLetter);
			DrawPanel drawPanel = new DrawPanel(currentLetter);
			drawPanel.setBackground(Color.LIGHT_GRAY);
			if(i == 0)
			{
				drawPanel.setBounds(0,0,theController.getWidth(), theController.getHeight());
				add(drawPanel);
			}
			else
			{
				x += (spacer + theController.getWidth());
				
				if(i % 3 == 0)
				{
					y += (spacer + theController.getHeight());
					x = 0;
				}
				
				drawPanel.setBounds(x,y,theController.getWidth(), theController.getHeight());
				add(drawPanel);
			}
			
			revalidate();
		}
		this.setPreferredSize(new Dimension(x + theController.getWidth(), y + theController.getHeight()));
	}
}
