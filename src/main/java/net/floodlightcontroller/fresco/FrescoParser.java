package net.floodlightcontroller.fresco;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.floodlightcontroller.fresco.modules.FrescoModuleActionCall;
import net.floodlightcontroller.fresco.modules.FrescoModuleActionEval;
import net.floodlightcontroller.fresco.modules.FrescoModuleAttribute;

/*
 * FrescoParser : 
 * A simple parser for FRESCO script written in correct syntax,
 * works well on correctly written script.
 * TODO : Error Handling, and better syntax checking. 04.07.15
 * */
public class FrescoParser 
{
	private FrescoLogger fLogger;
	
	private FrescoGlobalTable globalTable;
	
	private Pattern mod_name_re       = Pattern.compile("_(.*)");
	private Pattern mod_line_re       = Pattern.compile("(.*):(.*)");
	
	private Pattern action_exp_re     = Pattern.compile("(.*):(.*)");
	
	// Ternary [lOperand] [operator] [rOperand] ? [val if true] : [val if false]
	// ternary_re (left section) ? (right section)
	// ternary_left_re (lOperand)(operators)(rOperand)

	private Pattern ternary_re       = Pattern.compile("(.*)\\?(.*)");	
	private Pattern ternary_left_re  = Pattern.compile("(.*)(==|>|<)(.*)");
	private Pattern ternary_left_inner_re = Pattern.compile("(.*):(.*)");
	
	private Pattern ternary_right_re = Pattern.compile("(.*),(.*)");
	
	private Matcher matcher; 
	
	public FrescoParser(FrescoLogger log)
	{
		this.fLogger = log;
		globalTable = new FrescoGlobalTable();
	}
	public FrescoGlobalTable parse(String filename)
	{
		// Construct CallGraph
		readScriptFile(filename);
		
		return globalTable;
	}
	private void ParserDBG(String message)
	{
		if(FrescoCoreSettings.Parser_dbg)
		{
			fLogger.logInfo("PARSER-DBG", message);
		}
	}
	
	private Scanner parse_module(String moduleName,Scanner fscanner)
	{
		String line;
		
		globalTable.modCallOrder.add(moduleName);
		
		while(fscanner.hasNextLine())
		{
			line = fscanner.nextLine();
			if(line.length() > 0)
			{
				matcher = mod_line_re.matcher(line);
				if(matcher.find())
				{
					String attrName  = matcher.group(1).trim();
					String attrValue = matcher.group(2).trim();
					
					ParserDBG(" : Adding "+
							moduleName+" "+attrName+" "+attrValue);
					
					globalTable.addModuleAttribute(moduleName, attrName, attrValue);
					
				}
				else if(line.trim().equals("action"))
				{
					ParserDBG(" : parse_action : gc "+matcher.groupCount());
					fscanner = parse_action(moduleName,fscanner);
				}
				else if(line.trim().equals("end"))
				{
					break;
				}
				else
				{
					// Erro
					ParserDBG("Module Unexpected line : "+line);
				}
			}
		}
		return fscanner;
	}
	private Scanner parse_action(String moduleName,Scanner fscanner)
	{
		String line;
		while(fscanner.hasNextLine())
		{
			line = fscanner.nextLine();
			if(line.length()>0)
			{
				matcher = action_exp_re.matcher(line);
				
				if (matcher.find())
				{
					String action_type = matcher.group(1).trim();
					
//					ParserDBG(("DBG ActionMatch : 0 : "+matcher.group(0).trim());
//					ParserDBG(("DBG ActionMatch : 1 : "+matcher.group(1).trim());
//					ParserDBG(("DBG ActionMatch : 2 : "+matcher.group(2).trim());

					switch(action_type)
					{
					case "call":
					{
						String callValue = matcher.group(2).trim();
						globalTable.addModuleAction(moduleName,new FrescoModuleActionCall(callValue));
						
						break;
					}
					case "eval":
					{
						matcher = ternary_re.matcher(line);
						if(matcher.find()) // valid ternary expression
						{
							String leftVarKey    = null;
							String rightVarKey   = null;
							
							String operator      = null;
							String actionIfTrue  = null;
							String actionIfFalse = null;
	
							String ternary_left  = matcher.group(1).trim(); 
							String ternary_right = matcher.group(2).trim();
							
							// Parse conditional
							matcher = ternary_left_re.matcher(ternary_left);
							if(matcher.find())
							{
								operator    = matcher.group(2).trim();
								rightVarKey = matcher.group(3).trim();
								
								matcher = ternary_left_inner_re.matcher(matcher.group(1).trim());
								
								if(matcher.find())
								{
									leftVarKey = matcher.group(2).trim();
								}
								else
								{
									ParserDBG("failed to parse ternary left inner");
								}
								ParserDBG("[ACTION LEFT VALUES] "+leftVarKey+" "+operator+" "+rightVarKey);
							}
							else
							{
								ParserDBG("failed to parse ternary left");
							}
							
							// Parse eval logic
							matcher = ternary_right_re.matcher(ternary_right);
							if(matcher.find())
							{
								actionIfTrue  = matcher.group(1).trim();
								actionIfFalse = matcher.group(2).trim();
							}
							else
							{
								ParserDBG("failed to parse ternary right");
							}
							// TODO Null check
							
							globalTable.addModuleAction(moduleName, 
									new FrescoModuleActionEval(
											leftVarKey,operator,rightVarKey,
											actionIfTrue,
											actionIfFalse));
							
						}
						else
						{
							ParserDBG("erro in action, no match for eval");
						}
						break;
					}
					default:
						ParserDBG("erro in action, no such action type "+action_type);
						break;
					}
				}
				else if(line.trim().equals("end"))
				{
					break;
				}
				else
				{
					ParserDBG("ERROR action unexpected line : "+line);
				}
			}
		}
		return fscanner;
	}
	
	private void readScriptFile(String filename)
	{
	   File file = new File(filename); 
	   try 
	   {
		   String line;
		   Scanner fscanner = new Scanner(file);
		   while(fscanner.hasNextLine())
		   {
			   line = fscanner.nextLine();
			   if(line.length()>0)
			   {
				   // Comment Line
				   if(line.trim().charAt(0) == '#')
				   {
					   //log.logInfo("Comment line "+line);
				   }
				   // Module Definition
				   else if(line.startsWith("module_"))
				   {
					   String module_name = null;
					   
					   matcher = mod_name_re.matcher(line);
					   
					   if (matcher.find())
					   {
					      module_name = matcher.group(1).trim();
					      ParserDBG(" : module_name : "+module_name);
					      fscanner = parse_module(module_name,fscanner);
					   }
					   
//					   //Parse module name
//					   for(String mod : line.split("_"))
//					   {
//						   
//					   }
					   fLogger.logInfo("PARSER","module declaration "+line); 
				   }
			   }
		   }
		   fscanner.close();
	   
		   //log.logInfo(content);
	   }
	   catch (Exception e) 
	   {
	       fLogger.logErro("PARSER",filename+" : "+e);
	   }
	}
}
