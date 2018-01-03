package handwrittenfont;

import java.awt.Color;
import com.apple.eawt.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class HandwrittenFontRootView extends JFrame
{
	private Container contentPane;
	private HandwrittenFontProcessView processingView;
	private JTextArea messageView;
	private HandwrittenFontDebugView debugView;
	private HandwrittenFontPreviewView previewView;
	private JMenuBar menuBar;
	private JPanel initialPanel;
	private JMenu fileMenu, helpMenu;
	private HandwrittenFontController theController;
	
	public HandwrittenFontRootView(HandwrittenFontController controller)
	{
		theController = controller;
		contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Handwritten Font Generator");
		setSize(1000, 725);
		setResizable(false);
		menuBar = new JMenuBar();
		setupMenus();
		setJMenuBar(menuBar);
		
		GridBagConstraints buttonC = new GridBagConstraints();
		GridBagConstraints labelC = new GridBagConstraints();
		
		initialPanel = new JPanel(new GridBagLayout());
		initialPanel.setBounds(0, 0, 1000, 725);
		
		JLabel messageLabel = new JLabel("Please select a image of the provided Character Grid");
		labelC.ipadx = 10;
		labelC.ipady = 10;
		labelC.fill = GridBagConstraints.HORIZONTAL;
		labelC.anchor = GridBagConstraints.PAGE_START;
		labelC.gridx = 1;
		labelC.gridy = 1;
		labelC.gridwidth = 1;
		initialPanel.add(messageLabel, labelC);
		
		JButton browseButton = new JButton("Select Handwriting Image");
		browseButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				selectImage();
			}
			
		});
		buttonC.fill = GridBagConstraints.HORIZONTAL;
		buttonC.ipady = 25;
		buttonC.ipadx = 25;
		buttonC.weighty = 0;
		buttonC.anchor = GridBagConstraints.PAGE_END;
		buttonC.gridx = 1;
		buttonC.gridy = 2;
		buttonC.gridwidth = 1;
		initialPanel.add(browseButton,buttonC);
		contentPane.add(initialPanel);
		
		
		processingView = new HandwrittenFontProcessView();
		processingView.setBounds(0, 0, 1000, 725);
	}
	
	private void setupMenus()
	{
		JMenuItem item;
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		item = new JMenuItem("Open");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				selectImage();
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileMenu.add(item);
		
		item = new JMenuItem("Export");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser saveFont = new JFileChooser();
				int retValue = saveFont.showSaveDialog(HandwrittenFontRootView.this);
				
				if(retValue == JFileChooser.APPROVE_OPTION)
				{
					File fontFile = saveFont.getSelectedFile();
					theController.writeFontfile(fontFile);
				}
			}
		});
		fileMenu.add(item);
		fileMenu.addSeparator();
		
		item = new JMenuItem("Quit");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileMenu.add(item);
		
		JMenu debugMenu = new JMenu("Debug");
		
		JCheckBoxMenuItem debugVisible = new JCheckBoxMenuItem("Show Debug Frame");
		debugMenu.add(debugVisible);
		
		JCheckBoxMenuItem onlyBlackCurves = new JCheckBoxMenuItem("Only black curves");
		debugMenu.add(onlyBlackCurves);
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(debugMenu);
		
		item = new JMenuItem("Print Grid");
		item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theController.openGridInBrowser("http://dl.dropbox.com/u/19523406/HandwrittenFontGrid.jpeg");
			}
		});
		helpMenu.add(item);
		
//		item = new JMenuItem("Help");
//		item.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				HelpView test = new HelpView("HWF", "/Users/hunterskrasek/Dropbox/workspace/HandwrittenFonts/How to use the Public folder.rtf");
//				test.setVisible(true);
//			}
//		});
//		helpMenu.add(item);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}
	
	private void selectImage()
	{
		JFileChooser gridFileChooser= new JFileChooser();
		gridFileChooser.setPreferredSize(new Dimension(675,650));
		gridFileChooser.addChoosableFileFilter(new ImageFilter());
		gridFileChooser.setAccessory(new ImagePreview(gridFileChooser));
		
		int retValue = gridFileChooser.showOpenDialog(null);
		if(retValue == JFileChooser.APPROVE_OPTION)
		{
			theController.log.info("File Chosed");
			File gridImage = gridFileChooser.getSelectedFile();
			try
			{
				theController.generateCurvesForFont(gridImage);
			}
			catch(NullPointerException npe)
			{
				JOptionPane.showMessageDialog(this, "Image to distorted, please retake picture", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			switchToProcessView();
		}
		else
		{
			
		}
	}
	
	private void switchToProcessView()
	{
		theController.log.info("Switched Views");
		initialPanel.setVisible(false);
		contentPane.remove(initialPanel);
		
		GridBagConstraints buttonC = new GridBagConstraints();
		JButton previewFont = new JButton("Preview Font");
		previewFont.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				switchToPreview();
			}
		});
		buttonC.fill = GridBagConstraints.HORIZONTAL;
		buttonC.ipady = 25;
		buttonC.weightx = 0;
		buttonC.weighty = 0;
		buttonC.gridwidth = 3;
		buttonC.gridx = 0;
		buttonC.gridy = 3;
		processingView.add(previewFont, buttonC);
		
		contentPane.add(processingView);
	}
	
	private void switchToPreview()
	{
		previewView = new HandwrittenFontPreviewView(theController);
		
		JScrollPane scrollPanel = new JScrollPane(previewView);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setBounds(0, 0, 995, 675);
		
		processingView.setVisible(false);
		contentPane.remove(processingView);
		contentPane.add(scrollPanel);
	}
	
	public void updateProgress(int percent)
	{
		processingView.updateProgress(percent);
	}
	
	public void updateProgressInfo(String message)
	{
		processingView.updateInfo(message);
	}
}
