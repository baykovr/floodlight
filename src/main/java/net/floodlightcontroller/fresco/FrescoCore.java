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

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
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
            throws FloodlightModuleException {
        // TODO Auto-generated method stub
    }

    @Override
    public void startUp(FloodlightModuleContext context) 
    {
    	try
    	{
    		readScript("/home/baykovr/Git/nuovo-fresco/test-script.fre");
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    private void readScript(String filename)
    {
    	// TODO : Non-Static Loading etc...
	   File file = new File(filename); //for ex foo.txt
	   try 
	   {
		   String content = "";
		   Scanner fscanner = new Scanner(file);
		   while(fscanner.hasNextLine())
		   {
			   content = fscanner.nextLine();
		   }
	       fscanner.close();
	       
	       System.out.printf("[....] %s\n", content);
	   }
	   catch (Exception e) 
	   {
	       System.out.printf("[erro] %s : %s \n",filename,e);
	   }
    }
}
