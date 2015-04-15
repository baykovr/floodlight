package net.floodlightcontroller.fresco.modules;

public class FrescoModuleAction {
	
	public enum FR_ActionType{
		call,
		eval
	}
	
	public String moduleName;
	public FR_ActionType actionType;
	public String actionValue;
	
	public FrescoModuleAction(String newModuleName, FR_ActionType newActionType)
	{
		moduleName = newModuleName;
		actionType = newActionType;
	}
	public FrescoModuleAction(String newModuleName, FR_ActionType newActionType, String newActionValue)
	{
		moduleName  = newModuleName;
		actionType  = newActionType;
		actionValue = newActionValue;
	}
	
	public String toString()
	{
		String actionString = "[ACTION] ["+moduleName+"] "+actionType;
		if(!actionValue.isEmpty())
		{
			actionString+=" "+actionValue;
		}
		return actionString;
	}
}
