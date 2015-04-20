package net.floodlightcontroller.fresco.modules;

import org.projectfloodlight.openflow.protocol.OFMessage;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.fresco.FrescoGlobalTable;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;

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
	
	@Override
	public void run(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		System.out.println("[EXEC] portcmp");
		
		// Grab Incoming Packet Port
		
		Ethernet eth =IFloodlightProviderService.bcStore.get(cntx,IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		
		if(eth.getEtherType() == Ethernet.TYPE_IPv4)
		{
                IPv4 ipPkt = (IPv4)eth.getPayload();
                System.out.println("SOURCE "+ipPkt.getSourceAddress());
		}
		
		
		if(parameter.equals(input))
		{
			globalTable.updateVar("portcmp_result", "1");
		}
		else
		{
			globalTable.updateVar("portcmp_result", "0");
		}
	}
	// portcmp does not make a decision on its own,
	// although you could easily make it do so...
	// that's not the point, however.

	@Override
	public Command runEval(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		// TODO Auto-generated method stub
		return Command.CONTINUE;
	}
}
