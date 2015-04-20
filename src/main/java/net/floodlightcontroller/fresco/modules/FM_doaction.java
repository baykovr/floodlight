package net.floodlightcontroller.fresco.modules;

import org.projectfloodlight.openflow.protocol.OFMessage;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFSwitch;
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
		name = "doaction";
	}
	@Override
	public void run(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		
	}
	@Override
	public Command runEval(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		System.out.println("[EXEC] doaction");
		// Process the action
		if(action instanceof FrescoModuleActionEval)
		{
			((FrescoModuleActionEval) action).setValueTable(globalTable.valueTable);
			String returnValue = ((FrescoModuleActionEval) action).evaluate();
			if(returnValue == null)
			{
				System.out.println("[Error] [doaction] returnValue is null.");
				return Command.CONTINUE;
			}
			
			if(returnValue.equals("ALLOW"))
			{
				System.out.println("[ALLOW] [doaction] returnValue is ALLOW.");
				return Command.CONTINUE;
			}
			else if(returnValue.equals("DENY"))
			{
				System.out.println("[DROP] [doaction] returnValue is DROP.");
				return Command.STOP;
			}
			else
			{
				System.out.println("[ERROR DOACTIOn] Unhandled returnValue in doaction : "+returnValue);
				return Command.CONTINUE;
			}
		}
		else
		{
			return Command.CONTINUE;
		}
	}
}
