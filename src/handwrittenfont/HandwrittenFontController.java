package handwrittenfont;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class HandwrittenFontController
{
	HandwrittenFontLogger log = new HandwrittenFontLogger("HandWrittenFont");
	HandwrittenFontRootView rootView;
	GridProcessor gridProcessor;
	CurveGenerator curveGenerator;
	FontBuilder fontBuilder;
	Map<Letter, Map<Set<BezierCurve>, Integer>> curveMap = new HashMap<Letter, Map<Set<BezierCurve>, Integer>>();
	int width = 0;
	int height = 0;
	
	public void generateCurvesForFont(File gridImage)
	{
		gridProcessor = new GridProcessorImpl(gridImage);
		curveGenerator = new CurveGeneratorImpl(gridProcessor);
		final Letter[] letterArray = Letter.values();
		int count = 0;
//		Thread testThread = new Thread(new Runnable()
//		{
//			public void run()
//			{
//				for(int i = 0; i < letterArray.length; i++)
//				{
//					final Letter currentLetter = letterArray[i];
////					System.out.println("Thread i: " + i + "CurrentLetter: " + currentLetter);
//					curveMap.put(currentLetter, curveGenerator.getCurvesOfLetter(currentLetter));
//					try {
//						Thread.sleep(50);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//		testThread.start();
		
		for(int j = 0; j < letterArray.length; j++)
		{
			try {
				rootView.updateProgressInfo("Now processing Letter: " + letterArray[j]);
				Letter currentLetter = letterArray[j];
				curveMap.put(currentLetter, curveGenerator.getCurvesOfLetter(currentLetter));
				rootView.updateProgress(j+1);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		

		width = gridProcessor.getLetterImage(Letter.A).getWidth();
		height = gridProcessor.getLetterImage(Letter.A).getHeight();
		return;
	}
	
	public void writeFontfile(File fontFile)
	{
		fontBuilder = new FontBuilderImpl();
		fontBuilder.makeFile(fontFile, curveMap);
		try
		{
			JOptionPane.showMessageDialog(rootView, "Font Exported! Located at:\n" + fontFile.getCanonicalPath(), "Completed!", JOptionPane.INFORMATION_MESSAGE);
//			JOptionPane.showInternalMessageDialog(, "Font file written to: " + fontFile.getAbsolutePath(), "Font File Written!", JOptionPane.INFORMATION_MESSAGE);
		} catch (HeadlessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Map<Letter, Map<Set<BezierCurve>, Integer>> getCurveMap() {
		return curveMap;
	}

	public CurveGenerator getCurveGenerator() {
		return curveGenerator;
	}
	
	public void openGridInBrowser(String theURL)
	{
		String os = System.getProperty("os.name");
		Runtime runtime=Runtime.getRuntime();
		try
		{
			if(os.startsWith("Windows"))
			{
				String cmd = "rundll32 url.dll,FileProtocolHandler "+ theURL;
				Process winBrowser = runtime.exec(cmd);
			}
			else if(os.startsWith("Mac OS"))
			{
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openUrl = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
				openUrl.invoke(null, new Object[]{theURL});
			}
			else
			{
				String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
				if (runtime.exec(new String[] {"which", browsers[count]}).waitFor() == 0)
				browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else 
					runtime.exec(new String[] {browser, theURL});
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startGui()
	{
		String os = System.getProperty("os.name");
		if(os.startsWith("Mac OS"))
		{
			try {
	            System.setProperty("apple.laf.useScreenMenuBar", "true");
	            
	        } catch (Exception e) {
	            // try the older menu bar property
	            java.lang.System.setProperty("com.apple.macos.useScreenMenuBar", "true");
	        }
		}
		else
		{
			try
			{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		try
//		{
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//		} catch (ClassNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				rootView = new HandwrittenFontRootView(HandwrittenFontController.this);

				rootView.setVisible(true);
			}
		});
	}
	
	public void updateProgress(int x)
	{
		rootView.updateProgress(x);
	}
}
