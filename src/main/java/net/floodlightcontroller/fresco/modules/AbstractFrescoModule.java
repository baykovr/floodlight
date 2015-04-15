package net.floodlightcontroller.fresco.modules;

import net.floodlightcontroller.core.IListener.Command;

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
 * @action    : a script action
 * */
public abstract class AbstractFrescoModule {
	
	public enum Action { ALLOW, DROP }
	
	public String name, 
	input, output, 
	parameter, 
	event, 
	action;
	
	public AbstractFrescoModule(String newInput,
			String newOutput,String newParameter,String newEvent,String newAction)
	{
		// TODO : Surely we'll need to adjust action etc
		input = newInput;
		output = newOutput;
		parameter = newParameter;
		event = newEvent;
		action = newAction;
	}
	
	public String getName()
	{
		return name;
	}
	
	public abstract void run();
}
