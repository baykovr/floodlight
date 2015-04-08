package net.floodlightcontroller.fresco;

import org.projectfloodlight.openflow.protocol.OFType;

public interface IFrescoEvents {
	
	/*FRESCO Supported OpenFlow Message Types*/
	public final OFType[] FR_OF_MsgTypes = new OFType[]
	{
			OFType.PACKET_IN
	};
}
