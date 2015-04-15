package net.floodlightcontroller.fresco.modules;

public final class FrescoModuleAttribute {
	public String moduleName;
	public String attrName;
	public String attrValue;
	
	public FrescoModuleAttribute(String newModuleName, String newAttrName, String newAttrValue)
	{
		moduleName = newModuleName;
		attrName   = newAttrName;
		attrValue  = newAttrValue;
	}
	public String toString()
	{
		return "[ATTR] ["+moduleName+"] "+attrName+" : "+attrValue;
	}
}
