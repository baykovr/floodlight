package net.floodlightcontroller.fresco.modules;

import org.projectfloodlight.openflow.protocol.OFMessage;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFSwitch;
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
 * @action    : a script action
 * */
public abstract class AbstractFrescoModule {
	
	protected FrescoGlobalTable globalTable;
	
	public enum Action { ALLOW, DROP }
	
	public String name, 
	input, output, 
	parameter, 
	event;
	public AbstractFrescoModuleAction action;
	
	public AbstractFrescoModule(
			FrescoGlobalTable newGlobalTable,
			String newInput,String newOutput,
			String newParameter,
			String newEvent,
			AbstractFrescoModuleAction newAction)
	{
		// TODO : Surely we'll need to adjust action etc
		globalTable = newGlobalTable;
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
	
	public abstract void run(IOFSwitch sw, OFMessage msg, FloodlightContext cntx);
	// eval is used when the module is returning an OF Command.
	public abstract Command runEval(IOFSwitch sw, OFMessage msg, FloodlightContext cntx);
}
