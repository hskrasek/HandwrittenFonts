package handwrittenfont;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class HandwrittenFontProcessView extends JPanel
{
	private JProgressBar progressBar;
	private JLabel processingInfo;
	private JButton previewFont;
	static final int MIN = 0;
	static final int MAX = 70;
	
	public HandwrittenFontProcessView()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints progressC = new GridBagConstraints();
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(MIN);
		progressBar.setMaximum(MAX);
		progressBar.setStringPainted(true);
		progressC.fill = GridBagConstraints.HORIZONTAL;
		progressC.ipady = 40;
		progressC.weightx = 0;
		progressC.gridwidth = 3;
		progressC.gridx = 0;
		progressC.gridy = 1;
		add(progressBar, progressC);
		
		GridBagConstraints infoC = new GridBagConstraints();
		processingInfo = new JLabel("Filler Text");
		infoC.fill = GridBagConstraints.HORIZONTAL;
		infoC.ipady = 25;
		infoC.weightx = 0;
		infoC.weighty = 0;
		infoC.gridwidth = 3;
		infoC.gridx = 0;
		infoC.gridy = 2;
		add(processingInfo, infoC);
	}
	
	public void updateProgress(int percent)
	{
		progressBar.setValue(percent);
	}
	
	public void updateInfo(String message)
	{
		if(progressBar.getValue() == MAX)
		{
			processingInfo.setText("DONE!");
		}
		else
		{
			processingInfo.setText(message);
		}
	}
}
