package net.floodlightcontroller.fresco;

public class FrescoCoreSettings {
	// System wide logger, independent of FrescoCore
		// OFLog Settings
		public final static boolean OFLog_DBG = false;
	
	//FRESCO Core Logger Settings
	//	specifying use sysout will utilize stdout 
	//	instead of java logger.
		public final static boolean Log_via_stdout = true;
		public final static boolean Log_info = true;
		public final static boolean Log_warn = true;
		public final static boolean Log_erro = true;
		
	//Parser Debug Information
		public final static boolean Parser_dbg     = true;
		public final static boolean Parser_verbose = true;
}
