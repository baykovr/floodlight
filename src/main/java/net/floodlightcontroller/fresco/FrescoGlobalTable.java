package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.HashMap;

import net.floodlightcontroller.fresco.modules.FrescoModuleAction;
import net.floodlightcontroller.fresco.modules.FrescoModuleAction.FR_ActionType;
import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

// TODO : Error reporting

public class FrescoGlobalTable 
{
	// Order in which modules are executed
	protected ArrayList<String> modCallOrder;
	
	// Table of variables, name and values
	// which the modules will update/reference
	protected HashMap<String,String> valueTable;
	
	// Contains module attribute entries
	protected ArrayList<FrescoModuleAttribute> modAttributes;
	
	protected ArrayList<FrescoModuleAction>modActions; 
	
	public FrescoGlobalTable()
	{
		modCallOrder  = new ArrayList<String>();
		modAttributes = new ArrayList<FrescoModuleAttribute>();
		modActions    = new ArrayList<FrescoModuleAction>();
		valueTable    = new HashMap<String,String>();
	}
	
	public void initValueTable()
	{
		for(FrescoModuleAttribute moduleAttr : modAttributes)
		{
			
			if( (moduleAttr.attrName.equals("input") || moduleAttr.attrName.equals("ouput"))
					&& !moduleAttr.attrValue.equals("none")
			  )
			{
				
				addVar(moduleAttr.attrValue);
			}
			else
			{
				// TODO : log ignored attributes
			}	
			System.out.println(moduleAttr.toString());
		}
	}
	
	// Adding is done during initialization
	private void addVar(String variable)
	{
		if(!valueTable.containsKey(variable))
		{
			valueTable.put(variable,null);
		}
	}
	
	// Updates will be done by the modules
	public void updateVar(String variable,String value)
	{
		if(valueTable.containsKey(variable))
		{
			valueTable.put(variable,value);
		}
	}
	
	public void addModuleAttribute(String moduleName,String modAttrName,String modAttrValue)
	{
		if( moduleName.isEmpty()  ||modAttrName.isEmpty() || modAttrValue.isEmpty())
		{
			System.out.println("Warning: null/empty attribute name/value in script.");
			//TODO : Additional logging, print variables...
		}
		else
		{
			modAttributes.add(new FrescoModuleAttribute(moduleName,modAttrName,modAttrValue));
		}
	}
	public void addModuleAction(String moduleName,FR_ActionType actionType, String actionValue)
	{
		if(moduleName.isEmpty() || actionType == null || actionValue == null)
		{
			System.out.println("Warning: null/empty module action name/value/type in script.");
		}
		else
		{
			modActions.add(new FrescoModuleAction(moduleName,actionType,actionValue));
		}
	}
}
