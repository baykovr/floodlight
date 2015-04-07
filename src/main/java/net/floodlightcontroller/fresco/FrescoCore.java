package net.floodlightcontroller.fresco;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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


public class FrescoCore implements IFloodlightModule, IOFMessageListener {
	
	private FrescoParser fParser;
	private FrescoLogger fLogger;
	private FrescoModuleManager fModManager;
	
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
        return null;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() 
    {
        // TODO Auto-generated method stub
        return null;
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
    	/*
    	 * Core init
    	 * */
    	fLogger = new FrescoLogger();
    	fParser = new FrescoParser(fLogger);
    	fModManager = new FrescoModuleManager();
    	
    	fLogger.logInfo("init");
    }

    @Override
    public void startUp(FloodlightModuleContext context) 
    {
    	if(fLogger!=null)
    	{
    		fLogger.logInfo("startup");
    	}
    	else if (fModManager!=null)
    	{
    		// TODO Module Loading
    		FrescoModule_PortCheck portchecker = new FrescoModule_PortCheck("module_portcmd");
    		if(portchecker!=null)
    		{
    			fModManager.add(portchecker);
    		}
    		else
    		{
    			fLogger.logErro("failed to load portchecker");
    		}
    	}
    	else
    	{
    		System.out.println("FRESCO CRITICAL : "
    				+ "One or more core components is unavailable");
    	}
    	
    	try
    	{
    		fParser.parse("/home/baykovr/Git/nuovo-fresco/test-script.fre");
    		// Load Script
    		// Script Determines which modules to load
    		// load modules
    		// run
    		
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
}
