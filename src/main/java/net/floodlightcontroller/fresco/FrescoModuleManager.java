package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.HashMap;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.fresco.modules.AbstractFrescoModule;
import net.floodlightcontroller.fresco.modules.FM_portcmp;
import net.floodlightcontroller.fresco.modules.AbstractFrescoModuleAction;
import net.floodlightcontroller.fresco.modules.FrescoModuleActionCall;
import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

/*
 * FRESCO Module Manager
 * 
 * Holds fresco modules.
 * */

public class FrescoModuleManager 
{
	private HashMap<String,AbstractFrescoModule> modules;
	private FrescoGlobalTable globalTable;
	private FrescoLogger fLogger;
	
	public FrescoModuleManager(FrescoLogger flogger)
	{
		fLogger = flogger;
		modules = new HashMap<String,AbstractFrescoModule>();
	}
	
	public void setGlobalTable(FrescoGlobalTable newGlobalTable)
	{
		globalTable = newGlobalTable;
	}
	
	
	
	public void procGlobalTable()
	{
		// TODO : Load Modules using reflection
		//      : this will simplify the code quite a bit.
		
		// Initialize modules
		for(String moduleName : globalTable.modCallOrder)
		{
			System.out.println("[MODULE] "+moduleName);
			addModule(moduleName);
		}
		
		// Print Attributes Map
		
		// Initialize global value table
		
		for(FrescoModuleAttribute moduleAttr : globalTable.modAttributes)
		{
			System.out.println(moduleAttr.toString());
		}
		
		for(String moduleName : globalTable.modActions.keySet())
		{
			System.out.println("[ACTION]"+"["+moduleName+"] "+globalTable.modActions.get(moduleName).toString());
		}
		
		// Initialization and hand off
		globalTable.initValueTable();
		
		for(String variableName : globalTable.valueTable.keySet())
		{
			System.out.println("Variable: "+variableName+" "+globalTable.valueTable.get(variableName));
		}
	}
	public void addModule(String moduleName)
	{
		//Attributes
		String input = null;
		String output = null;
		String parameter = null;
		String event = null;
		AbstractFrescoModuleAction action = null;
		
		// Search for configuration options in modAttributes
		for(FrescoModuleAttribute moduleAttr : globalTable.modAttributes)
		{
			System.out.println("[addModule] check "+moduleAttr.toString());
			
			if(moduleAttr.moduleName.equals(moduleName))
			{
				//moduleAttr.attrValue
				System.out.println("[attrName] check "+moduleAttr.attrName);
				switch(moduleAttr.attrName)
				{
				case "input":
				{
					input = moduleAttr.attrValue;
					break;
				}
				case "output":
				{
					output = moduleAttr.attrValue;
					break;
				}
				case "parameter":
				{	
					parameter = moduleAttr.attrValue;
					break;
				}
				case "event":
				{
					event = moduleAttr.attrValue;
					break;
				}
				default:
				{
					fLogger.logWarn("ModuleManager","Unknown attribute: "+moduleAttr.attrName);
					break;
				}
				
				}
			}//if()
			
			action = globalTable.modActions.get(moduleName);
			
		}
			// A module is not required to have an associated action.
			if(input != null && output != null && parameter != null && event != null)
			{
				switch(moduleName)
				{
					case "portcmp":
					{
						FM_portcmp portcmp = new FM_portcmp(globalTable,input,output,parameter,event,action);
						
						// TODO : to do type system
						modules.put("portcmp",portcmp);
						
						break;
					}
					case "doaction":
					{
						break;
					}
					default:
					{
						fLogger.logErro("ModuleManager","Requested module "+moduleName+" unsupported. (ABORTING)");
						return;
					}
				}
			}//if()
			else
			{
				fLogger.logErro("ModuleManager","Requested module "+moduleName+" is missing attributes.");
				System.out.println("Attribute Dump: "+input+" "+output+" "+ parameter+" " + event);
				return;
			}
	}

	public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx)
	{
		fLogger.log.info("[ ModuleManager ] got a new packet");
		
		if(globalTable != null)
		{
			// find modules with hook points and run them
			if(modules.size() > 0)
			{
				String event = modules.get(0).event;
				
				System.out.println("The first module, event is ... "+event);
				//OFType.PACKET_IN
				System.out.println("The incoming OF Message is "+msg.getType() );
				if(event.equals(msg.getType()))
				{
					//process module runtime
					modules.get(0).run();
					if(modules.get(0).action instanceof FrescoModuleActionCall)
					{
						
						
					}
					
				}
			}
			else
			{
				fLogger.logWarn("ModuleManager","Has no FRESCO modules loaded.");
			}
		}
		else
		{
			fLogger.logErro("ModuleManager","Has no global value table loaded!");
		}
		return Command.CONTINUE;
	}
}
