package net.floodlightcontroller.fresco.modules;

import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.fresco.FrescoGlobalTable;

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

public class FM_portcmp extends AbstractFrescoModule{

	public FM_portcmp(
			FrescoGlobalTable newGlobalTable,
			String newInput,String newOutput,
			String newParameter,
			String newEvent,
			AbstractFrescoModuleAction newAction)
	{
		super(newGlobalTable,newInput,newOutput,newParameter,newEvent,newAction);
		
		name = "portcmp";
	}
	public void run()
	{
		System.out.println("[EXEC] portcmp");
		if(parameter.equals(input))
		{
			output = "1";
		}
		else
		{
			output = "0";
		}
	}
}
