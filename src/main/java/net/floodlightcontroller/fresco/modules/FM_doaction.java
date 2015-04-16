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
import net.floodlightcontroller.fresco.FrescoGlobalTable;

public class FM_doaction extends AbstractFrescoModule {

	public FM_doaction(
			FrescoGlobalTable newGlobalTable,
			String newInput,String newOutput,
			String newParameter,
			String newEvent,
			AbstractFrescoModuleAction newAction)
	{
		super(newGlobalTable,newInput,newOutput,newParameter,newEvent,newAction);
		// TODO Auto-generated constructor stub
		name = "doaction";
	}
	public void run()
	{
		
	}
}
