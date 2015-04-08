package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.floodlightcontroller.core.IFloodlightProviderService;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;


public class FrescoCore implements IFloodlightModule, IOFMessageListener, IFrescoEvents {
	
	private FrescoParser fParser;
	private FrescoLogger fLogger;
	private FrescoModuleManager fModManager;
	
	protected IFloodlightProviderService floodlightProvider;
	
    @Override
    public String getName() {
        return FrescoCore.class.getName();
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
            IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
        // TODO Auto-generated method stub
    	
    	// onReceive we must return: CONTINUE || STOP 
    	// (optionally) issue open flow message
    	
    	return Command.CONTINUE;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() 
    {
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
            throws FloodlightModuleException 
    {
    	floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
    	
    	fLogger = new FrescoLogger();
    	fParser = new FrescoParser(fLogger);
    	fModManager = new FrescoModuleManager();
    	
    	fLogger.logInfo("CORE","init");
    }

    @Override
    public void startUp(FloodlightModuleContext context) 
    {
    	if(fLogger!=null)
    	{
    		fLogger.logInfo("CORE","startup");
    	}
    	else if (fModManager!=null)
    	{
    		// Attempt to build call graph from file
    		FrescoCallGraph cg_from_script;
    		try
        	{
    			cg_from_script = fParser.parse("/home/baykovr/Git/nuovo-fresco/test-script.fre");
    			if(cg_from_script != null)
    			{
    				fModManager.set_callgraph(cg_from_script);
    			}
        	}
        	catch(Exception e)
        	{
        		fLogger.logErro("CORE"," call graph failure : "+e);
        	}
    	}
    	else
    	{
    		fLogger.logErro("FRESCO CORE CRT","failure. ");
    		if(fLogger == null)
    		{
    			fLogger.logErro("FRESCO CORE DBG","failed to initiallize logger.");
    			
    		}
    		if(fModManager == null)
    		{
    			fLogger.logErro("FRESCO CORE DBG","failed to initiallize fresco module manager.");
    		}
    		return; //do not register event listeners.
    	}
    	
    	/*Register OFMessageListeners*/
    	for( OFType OFType_toLog: FR_OF_MsgTypes)
    	{
    		try
    		{
        		floodlightProvider.addOFMessageListener(OFType_toLog, this);
        	}
        	catch (Exception e)
    		{
        		fLogger.logErro("CORE",OFType_toLog.toString()+" message listener : "+e);
        	}
    	}
    }
}
