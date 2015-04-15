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
	
	private boolean core_ready;
	
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
    	
    	fLogger.log.info("[ CORE ] got a new packet");
    	
    	return fModManager.receive(sw, msg, cntx);
    	//return Command.CONTINUE;
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
    	fLogger            = new FrescoLogger();
    	fParser            = new FrescoParser(fLogger);
    	fModManager        = new FrescoModuleManager(fLogger);
    	core_ready         = true;
    	
    	// Verify components initialized properly
		if(fLogger == null)
		{
			fLogger.logErro("FRESCO CORE DBG","failed to initiallize logger.");
			core_ready = false;
			System.out.println("FRESCO CRITIAL Logger Creation Failed!");
		}
		else
		{
			fLogger.logInfo("CORE","initialization...");
		}
		
		if(fParser == null)
		{
			fLogger.logErro("FRESCO CORE DBG","failed to initiallize parser.");
			core_ready = false;
		}
		if(fModManager == null)
		{
			fLogger.logErro("FRESCO CORE DBG","failed to initiallize fresco module manager.");
			core_ready = false;
		}

    	
    }

    @Override
    public void startUp(FloodlightModuleContext context) 
    {
    	String scriptFile = "/home/baykovr/Git/nuovo-fresco/test-script.fre";
    	
    	if(!core_ready)
    	{
    		fLogger.logErro("CORE"," not in ready state, aborting startup.");
    	}
    	else
    	{
    		fLogger.logInfo("CORE","startUp...");
    	}
    	
		/* startUp FRESCO
		 * 1. construct CallGraph from script
		 * 2. pass CallGraph to module manager to register
		 * 3. register event hooks 
		 * */
		FrescoGlobalTable cg_from_script = null;
		try
    	{
			cg_from_script = fParser.parse(scriptFile);
			
			if(cg_from_script != null)
			{
				fModManager.setGlobalTable(cg_from_script); // TODO : .add_callgraph (support multiple)
				fModManager.procGlobalTable();
			}
			else
			{
				fLogger.logErro("CORE"," CallGraph construction failed for "+scriptFile);
			}
    	}
    	catch(Exception e)
    	{
    		fLogger.logErro("CORE"," CallGraph general failure : "+e);
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
