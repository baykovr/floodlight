package net.floodlightcontroller.fresco.modules;

/*
 * FRESCO Module Specification
 * 
 * 
 * @name : module name used to invoke module
 * 		 : unique value
 * 
 * @input     : input value name
 * @output    : output value name
 * @parameter : configuration parameter
 * @event     : even which triggers execution
 * @action    : some script action
 * */
public abstract class AbstractFrescoModule {
	
	public enum Action { ALLOW, DROP }
	
	private String name, 
	input, output, 
	parameter, 
	event, 
	action;
	
	public AbstractFrescoModule(String name)
	{
		this.name = name; 
	}
	
	public String getName()
	{
		return name;
	}
	
	public void action()
	{
		
	}
	
}
