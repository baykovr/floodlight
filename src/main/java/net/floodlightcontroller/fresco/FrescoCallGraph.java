package net.floodlightcontroller.fresco;

import java.util.ArrayList;
import java.util.HashMap;

import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

// TODO : Error reporting

public class FrescoCallGraph 
{
	protected HashMap<String,String> valueTable;
	protected ArrayList<String> modCallOrder;
	protected HashMap<String,FrescoModuleAttribute> modNameAttrMap;
	
	public void add_var(String variable)
	{
		if(!valueTable.containsKey(variable))
		{
			valueTable.put(variable,null);
		}
	}
	public void update_var_value(String variable,String value)
	{
		if(valueTable.containsKey(variable))
		{
			valueTable.put(variable,value);
		}
	}
	public void mapModNameAttr(String modName,String modAttrName,String modAttrValue)
	{
		modNameAttrMap.put(modName,new FrescoModuleAttribute(modAttrName,modAttrValue));
	}
}
