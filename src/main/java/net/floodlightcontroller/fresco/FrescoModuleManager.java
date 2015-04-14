package net.floodlightcontroller.fresco;

import java.util.ArrayList;

import org.projectfloodlight.openflow.protocol.OFMessage;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.fresco.modules.AbstractFrescoModule;

/*
 * FRESCO Module Manager
 * 
 * Holds fresco modules.
 * */

public class FrescoModuleManager 
{
	private ArrayList<AbstractFrescoModule> modules;
	private FrescoCallGraph callGraph;
	private FrescoLogger fLogger;
	
	public FrescoModuleManager(FrescoLogger flogger)
	{
		fLogger = flogger;
	}
	
	public void set_callgraph(FrescoCallGraph callgraph)
	{
		this.callGraph = callgraph;
	}
	
	public void process_callgraphs()
	{
		// Print Modules
		for(String moduleName : callGraph.modCallOrder)
		{
			fLogger.logInfo("ModManager", "have module "+moduleName);
			//check if we have abstractmodule to match...
		}
		
	}
	
	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		if(callGraph != null)
		{
			// find modules with hook points and run them
			
		}
		return Command.CONTINUE;
	}
	public void add(AbstractFrescoModule module)
	{
		modules.add(module);
	}

	public void load()
	{
		for(AbstractFrescoModule module : modules)
		{
			
		}
	}

	public void unload(AbstractFrescoModule module)
	{
		
	}
}
