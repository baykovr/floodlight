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
	private FrescoCallGraph callgraph;
	
	public FrescoModuleManager()
	{
		
	}
	
	public void set_callgraph(FrescoCallGraph callgraph)
	{
		this.callgraph = callgraph;
	}
	
	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		if(callgraph != null)
		{
			// process call graph
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
