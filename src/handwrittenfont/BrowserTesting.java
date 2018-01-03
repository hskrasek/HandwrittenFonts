package handwrittenfont;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BrowserTesting 
{
	public static void main(String args[])
	{
		openURL("http://www.google.com");
	}
	
	public static void openURL(String theURL)
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
}
