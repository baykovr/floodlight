package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import net.floodlightcontroller.core.IFloodlightProviderService;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;

/* Log incoming packets/messages for debugging purposes. */

public class FrescoLog implements IFloodlightModule, IOFMessageListener {
	
	protected IFloodlightProviderService floodlightProvider;
	
	private static final Logger log = Logger.getLogger( FrescoLog.class.getName());
	
	// TODO : Specify types to log in FRESCO core.
	protected final OFType[] OFTypes_toLog = new OFType[]
	{
			OFType.PACKET_IN
	};
	
    @Override
    public String getName() {
        return FrescoLog.class.getName();
    }

    @Override
    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public net.floodlightcontroller.core.IListener.Command receive(
            IOFSwitch sw, OFMessage msg, FloodlightContext cntx) 
    {
       log.info("[ PKT ] got a new packet");
        return null;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
    	// Add ourself to module loading system
        Collection<Class<? extends IFloodlightService>> l =
                new ArrayList<Class<? extends IFloodlightService>>();
        l.add(IFloodlightProviderService.class);
        return l;
    }
    
    @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(FloodlightModuleContext context)
            throws FloodlightModuleException {
    	floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
    }

    @Override
    public void startUp(FloodlightModuleContext context) 
    {
    	for( OFType OFType_toLog: OFTypes_toLog)
    	{
    		try
    		{
        		floodlightProvider.addOFMessageListener(OFType_toLog, this);
        	}
        	catch (Exception e)
    		{
        		log.severe("[erro] adding "+OFType_toLog.toString()+" message listener : "+e);
        	}
    	}
    }
}

