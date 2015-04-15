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
 * @action    : some script action
 * */

public class FM_doaction extends AbstractFrescoModule {

	public FM_doaction(String newInput,
			String newOutput,String newParameter,String newEvent,String newAction)
	{
		super(newInput,newOutput,newParameter,newEvent,newAction);
		// TODO Auto-generated constructor stub\
		name = "doaction";
	}
	public void run()
	{
		
	}
}
