package net.floodlightcontroller.fresco;

import java.util.logging.Logger;

/*
 * FrecoLogger : A simple wrapper of the standard java logger
 * this is just used to distinguish fresco specific messages 
 * from those of floodlight.
 * */

public class FrescoLogger 
{
	public final Logger log;
	
	private final String preamble = "[.FR.] ";
	
	public FrescoLogger()
	{
		log = Logger.getLogger( FrescoLogger.class.getName());
	}
	public void logInfo(String where, String msg)
	{
		log.info(preamble+" "+where+" "+msg);
	}
	public void logWarn(String where, String msg)
	{
		log.warning(preamble+" "+where+" "+msg);
	}
	public void logErro(String where, String msg)
	{
		log.severe(preamble+" "+where+" "+msg);
	}
}
