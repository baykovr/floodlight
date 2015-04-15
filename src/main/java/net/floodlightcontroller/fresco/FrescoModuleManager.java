package net.floodlightcontroller.fresco;

import java.util.ArrayList;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IListener.Command;
import net.floodlightcontroller.fresco.modules.AbstractFrescoModule;
import net.floodlightcontroller.fresco.modules.FM_portcmp;
import net.floodlightcontroller.fresco.modules.FrescoModuleAction;
import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

/*
 * FRESCO Module Manager
 * 
 * Holds fresco modules.
 * */

public class FrescoModuleManager 
{
	private ArrayList<AbstractFrescoModule> modules;
	private FrescoGlobalTable globalTable;
	private FrescoLogger fLogger;
	
	public FrescoModuleManager(FrescoLogger flogger)
	{
		fLogger = flogger;
		modules = new ArrayList<AbstractFrescoModule>();
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
		
		
		for(FrescoModuleAction moduleAction : globalTable.modActions)
		{
			System.out.println(moduleAction.toString());
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
		}
			if(input != null && output != null && parameter != null && event != null)
			{
				switch(moduleName)
				{
					case "portcmp":
					{
						FM_portcmp portcmp = new FM_portcmp(input,output,parameter,event,"none");
						
						modules.add(portcmp);
						
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
					modules.get(0).run();
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
	public void add(AbstractFrescoModule module)
	{
		modules.add(module);
	}

	public void load()
	{
		for(AbstractFrescoModule module : modules)
		{
			
		}
	}

	public void unload(AbstractFrescoModule module)
	{
		
	}
}
