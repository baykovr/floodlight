package net.floodlightcontroller.fresco;

import java.util.ArrayList;

/*
 * FRESCO Module Manager
 * 
 * Holds fresco modules.
 * */

public class FrescoModuleManager 
{
	ArrayList<AbstractFrescoModule> modules;
	
	public FrescoModuleManager()
	{
		
	}
	public void add(AbstractFrescoModule module)
	{
		modules.add(module);
	}
	public void load(AbstractFrescoModule module)
	{
		
	}
	public void unload(AbstractFrescoModule module)
	{
		
	}
}
