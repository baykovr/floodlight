package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.HashMap;

import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

// TODO : Error reporting

public class FrescoCallGraph 
{
	// Order in which modules are executed
	protected ArrayList<String> modCallOrder;
	
	// Table of variables, name and values
	// which the modules will update/reference
	protected HashMap<String,String> valueTable;
	
	// Map variable name (from valueTable) to moduleAttribute
	// for example, a valueTable entry a is the attribute input of module B
	protected HashMap<String,FrescoModuleAttribute> modNameAttributeMap;
	
	public FrescoCallGraph()
	{
		modCallOrder      = new ArrayList<String>();
		valueTable        = new HashMap<String,String>();
		modNameAttributeMap = new HashMap<String,FrescoModuleAttribute>();
	}
	
	public void add_var(String variable)
	{
		if(!valueTable.containsKey(variable))
		{
			valueTable.put(variable,null);
		}
	}
	
	public void update_var(String variable,String value)
	{
		if(valueTable.containsKey(variable))
		{
			valueTable.put(variable,value);
		}
	}
	
	public void addModuleAttributeMap(String moduleName,String modAttrName,String modAttrValue)
	{
		if( moduleName.isEmpty()  ||modAttrName.isEmpty() || modAttrValue.isEmpty())
		{
			System.out.println("Warning: null/empty attribute name/value in script.");
			//TODO : Additional logging, print variables...
		}
		else
		{
			modNameAttributeMap.put(moduleName,new FrescoModuleAttribute(modAttrName,modAttrValue));
		}
	}
}
