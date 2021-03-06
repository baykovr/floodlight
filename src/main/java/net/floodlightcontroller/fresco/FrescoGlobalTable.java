package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.HashMap;

import net.floodlightcontroller.fresco.modules.AbstractFrescoModuleAction;
import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

// TODO : Error reporting

public class FrescoGlobalTable 
{
	// Order in which modules are executed
	public ArrayList<String> modCallOrder;
	
	// Table of variables, name and values
	// which the modules will update/reference
	public HashMap<String,String> valueTable;
	
	// Contains module attribute entries
	protected ArrayList<FrescoModuleAttribute> modAttributes;
	
	protected HashMap<String,AbstractFrescoModuleAction>modActions; 
	
	public FrescoGlobalTable()
	{
		modCallOrder  = new ArrayList<String>();
		modAttributes = new ArrayList<FrescoModuleAttribute>();
		modActions    = new HashMap<String,AbstractFrescoModuleAction>(); //TODO : -> Hash to Array of Actions
		valueTable    = new HashMap<String,String>();
		
		// TODO : Refactor this. (man I wish there was a time extension...)
		//Add Basic Booleans
		valueTable.put("1","1");
		valueTable.put("0","0");
		valueTable.put("True","1");
		valueTable.put("False","0");
		valueTable.put("true","1");
		valueTable.put("false","0");
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
	public void addModuleAction(String moduleName,AbstractFrescoModuleAction newAction)
	{
		// Action is allowed to be null
		
		if(moduleName.isEmpty())
		{
			System.out.println("Warning: null/empty module action name/value/type in script.");
		}
		else
		{
			modActions.put(moduleName,newAction);
		}
	}
}
