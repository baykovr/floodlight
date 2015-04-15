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
	
	private final String preamble = "[FRESCO]";
	
	public FrescoLogger()
	{
		log = Logger.getLogger( FrescoLogger.class.getName());
	}
	public void logInfo(String where, String msg)
	{
		if(FrescoCoreSettings.Log_via_stdout)
			{System.out.println(preamble+"[Info]"+" "+where+" "+msg);}	
		else
			{log.info(preamble+" "+where+" "+msg);}
	}
	public void logWarn(String where, String msg)
	{
		if(FrescoCoreSettings.Log_via_stdout)
			{System.out.println(preamble+"[Warn]"+" "+where+" "+msg);}	
		else
			{log.warning(preamble+" "+where+" "+msg);}
	}
	public void logErro(String where, String msg)
	{
		if(FrescoCoreSettings.Log_erro)
		{
			if(FrescoCoreSettings.Log_via_stdout)
				{System.out.println(preamble+"[Erro]"+" "+where+" "+msg);}	
			else
				{log.severe(preamble+" "+where+" "+msg);}
		}
	}
}
