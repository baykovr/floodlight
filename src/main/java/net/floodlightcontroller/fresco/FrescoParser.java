package net.floodlightcontroller.fresco;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * FrescoParser : 
 * A simple parser for fresco script written in correct syntax,
 * works well on correctly written script.
 * TODO : Error Handling, and better syntax checking. 04.07.15
 * */
public class FrescoParser 
{
	private FrescoLogger log;
	
	private FrescoCallGraph callgraph;
	
	private Pattern mod_name_re       = Pattern.compile("_(.*)");
	private Pattern mod_line_re       = Pattern.compile("(.*):(.*)");
	private Pattern action_exp_re     = Pattern.compile("(.*):(.*):(.*)");
	
	// Ternary [lOperand] [operator] [rOperand] ? [val if true] : [val if false]
	// ternary_re (left section) ? (right section)
	// ternary_left_re (lOperand)(operators)(rOperand)

	private Pattern ternary_re       = Pattern.compile("(.*)\\?(.*)");	
	private Pattern ternary_left_re  = Pattern.compile("(.*)(==|>|<)(.*)");
	private Pattern ternary_right_re = Pattern.compile("(.*):(.*)");
	
	private Matcher matcher; 
	
	public FrescoParser(FrescoLogger log)
	{
		this.log = log;
		callgraph = new FrescoCallGraph();
	}
	public FrescoCallGraph parse(String filename)
	{
		readScriptFile(filename);
		
		// construct callgraph
		return callgraph;
	}
	private Scanner parse_module(Scanner fscanner)
	{
		String line;
		while(fscanner.hasNextLine())
		{
			line = fscanner.nextLine();
			if(line.length() > 0)
			{
				matcher = mod_line_re.matcher(line);
				if(matcher.find())
				{
					System.out.println("lv " +matcher.group(1).trim());
					System.out.println("rv " +matcher.group(2).trim());	
				}
				else if(line.trim().equals("action"))
				{
					System.out.println("Got action, groupc: "+matcher.groupCount());
					fscanner = parse_action(fscanner);
				}
				else if(line.trim().equals("end"))
				{
					break;
				}
				else
				{
					// Erro
					System.out.println("Module Unexpected line : "+line);
				}
			}
		}
		return fscanner;
	}
	private Scanner parse_action(Scanner fscanner)
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
					System.out.println("DEBUG: action type : "+action_type);
					switch(action_type)
					{
					case "call":
					{
						// call module named = matcher.group(2).trim();
						
						break;
					}
					case "eval":
					{
						matcher = ternary_re.matcher(line);
						if(matcher.find()) // valid ternary expression
						{
							String ternary_left  = matcher.group(1).trim(); 
							String ternary_right = matcher.group(2).trim();
							
							// Parse conditional
							matcher = ternary_left_re.matcher(ternary_left);
							if(matcher.find())
							{
								String loperand = matcher.group(1).trim();
								String operator = matcher.group(2).trim();
								String roperand = matcher.group(3).trim();
								
								System.out.println(); 
							}
							else
							{
								System.out.println("failed to parse ternary left");
							}
							
							// Parse eval logic
							matcher = ternary_right_re.matcher(ternary_right);
							if(matcher.find())
							{
								String eval_if_true  = matcher.group(1).trim();
								String eval_if_false = matcher.group(2).trim();
							}
							else
							{
								System.out.println("failed to parse ternary right");
							}
						}
						else
						{
							System.out.println("erro in action, no match for eval");
						}
						break;
					}
					default:
						System.out.println("erro in action, no such action type "+action_type);
						break;
					}
				}
				else if(line.trim().equals("end"))
				{
					break;
				}
				else
				{
					System.out.println("erro action unexpected line : "+line);
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
				   if(line.charAt(0) == '#')
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
					      module_name = matcher.group(1);
					      System.out.println("Got module name : "+module_name);
					      fscanner = parse_module(fscanner);
					   }
					   
//					   //Parse module name
//					   for(String mod : line.split("_"))
//					   {
//						   
//					   }
					   log.logInfo("PARSER","module declaration "+line); 
				   }
			   }
		   }
		   fscanner.close();
	   
		   //log.logInfo(content);
	   }
	   catch (Exception e) 
	   {
	       log.logErro("PARSER",filename+" : "+e);
	   }
	}
}
