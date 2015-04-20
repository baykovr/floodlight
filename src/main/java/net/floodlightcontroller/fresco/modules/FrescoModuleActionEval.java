package net.floodlightcontroller.fresco.modules;

import java.util.HashMap;

public class FrescoModuleActionEval extends AbstractFrescoModuleAction {
	// from Script spec
	//	eval : var_left operator var_right : actionIfTrue , actionIfFalse
	
	public String varLeftKey, varRightKey;
	public String varLeft, operator, varRight, actionIfTrue, actionIfFalse;
	
	private HashMap<String,String> valueTable = new HashMap<String,String>();
	
	public FrescoModuleActionEval(
			String newVarLeftKey,
			String newOperator,
			String newVarRightKey,
			String newActionIfTrue,
			String newActionIfFalse)
	{
		varLeftKey = newVarLeftKey;
		operator = newOperator;
		varRightKey = newVarRightKey;
		
		actionIfTrue = newActionIfTrue;
		actionIfFalse = newActionIfFalse;
	}
	public void setValueTable(HashMap<String,String> newValueTable)
	{
		valueTable = newValueTable;
	}
	
	public String evaluate()
	{
		// Look up values in valueTable and evaluate
		varLeft       = valueTable.get(varLeftKey);
		varRight      = valueTable.get(varRightKey);
		
		if(varLeft != null && varRight != null)
		{
			System.out.println("[DBG-ActionEval]");
			System.out.println("\t[DBG] Key "+varLeft+" "+operator+" "+varRight);
			
			switch(operator)
			{
				case "==":
				{
					if(varLeft.equals(varRight))
					{						
						return actionIfTrue;
					}
					else
					{
						return actionIfFalse;
					}
				}
				case "!=":
				{
					if(!varLeft.equals(varRight))
					{
						return actionIfTrue;
					}
					else
					{
						return actionIfFalse;
					}
				}
				default:
				{
					System.out.println("[ERROR] Unhandled operator "+operator);
					return null;
	 			}
			}
		}
		else
		{
			System.out.println("[ERROR-ActionEval] No entires for one or more keys");
			System.out.println("\t[DBG] Key "+varLeftKey+" value "+varLeft);
			System.out.println("\t[DBG] Key "+varRightKey+" value "+varRight);
			return null;
		}
	}
	
}
