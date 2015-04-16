package net.floodlightcontroller.fresco.modules;

public class FrescoModuleActionCall extends AbstractFrescoModuleAction{
	public String moduleToCall;
	
	public FrescoModuleActionCall(String newModuleToCall)
	{
		moduleToCall = newModuleToCall;
	}
}
