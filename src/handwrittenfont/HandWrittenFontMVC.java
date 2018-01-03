package handwrittenfont;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarFile;

public class HandWrittenFontMVC 
{
	public static void main(String args[])
	{
		HandwrittenFontController controller = new HandwrittenFontController();
//		setupDemonstration();
		controller.startGui();
	}
	
	private static void setupDemonstration()
	{
//		JarFile thisGuy = new JarFile(null);
//		File demoGrid = new File();
//		File blankGrid = new File("images/HandwrittenFontGrid.jpeg");
//		if(demoGrid.exists())
//		{
//			System.out.println("afs");
//		}
//		else
//		{
//			System.out.println("Baradsfa");
//		}
//		String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "HWFG Demo";
//		File demoDir = new File(desktopPath);
//		InputStream in = null;
//		try {
//			in = Class.forName("handwrittenfont.HandWrittenFontMVC").getResourceAsStream("images/demonstrationGrid.jpeg");
//		} catch (ClassNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		OutputStream out = null;
//		try {
//			out = new FileOutputStream(desktopPath + File.separator + demoGrid.getName());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		byte[] buf = new byte[1024];
//		int len;
//		try {
//			while ((len = in.read(buf)) > 0) 
//			{
//				out.write(buf, 0, len);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(desktopPath);
	}
}
