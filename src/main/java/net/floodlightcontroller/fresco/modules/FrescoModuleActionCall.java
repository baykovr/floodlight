package net.floodlightcontroller.fresco.modules;

public class FrescoModuleActionCall extends AbstractFrescoModuleAction{
	public String value;
	
	public FrescoModuleActionCall(String newModuleToCall)
	{
		value = newModuleToCall;
	}
}
