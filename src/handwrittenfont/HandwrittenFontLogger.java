package handwrittenfont;

import java.util.logging.Logger;

public class HandwrittenFontLogger
{
	Logger log;
	public HandwrittenFontLogger(String logger)
	{
		log = Logger.getLogger(logger);
	}
	
	private String getMessage(String msg)
	{
		return "[HandwrittenFont] " + msg;
	}
	
	public void info(String message)
	{
		log.info(getMessage(message));
	}
	
	public void severe(String message)
	{
		log.severe(getMessage(message));
	}
	
	public void warning(String message)
	{
		log.warning(getMessage(message));
	}
}
