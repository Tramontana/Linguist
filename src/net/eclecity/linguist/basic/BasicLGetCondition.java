// BasicLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic;

import net.eclecity.linguist.basic.condition.BasicCContains;
import net.eclecity.linguist.basic.condition.BasicCEmpty;
import net.eclecity.linguist.basic.condition.BasicCEndOfFile;
import net.eclecity.linguist.basic.condition.BasicCEndsWith;
import net.eclecity.linguist.basic.condition.BasicCEqual;
import net.eclecity.linguist.basic.condition.BasicCFile;
import net.eclecity.linguist.basic.condition.BasicCFileOpen;
import net.eclecity.linguist.basic.condition.BasicCGreater;
import net.eclecity.linguist.basic.condition.BasicCLess;
import net.eclecity.linguist.basic.condition.BasicCModuleRunning;
import net.eclecity.linguist.basic.condition.BasicCMore;
import net.eclecity.linguist.basic.condition.BasicCNegative;
import net.eclecity.linguist.basic.condition.BasicCNotEqual;
import net.eclecity.linguist.basic.condition.BasicCNotGreater;
import net.eclecity.linguist.basic.condition.BasicCNotLess;
import net.eclecity.linguist.basic.condition.BasicCPositive;
import net.eclecity.linguist.basic.condition.BasicCPrompt;
import net.eclecity.linguist.basic.condition.BasicCStartsWith;
import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHQueue;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Generate	code for a condition:
	<pre>
	{value} is [not] {value}
	{value} is [not] greater [than] {value}
	{value} is [not] less [than] {value}
	{value} is positive/negative
	{value} starts/ends with {value}
	{value} contains {value}
	{value} does not contain {value}
	[not] at end of {file}
	file {name} exists
	file {name} does not exist
	file {name} is a directory
	{file} is [not] open
	{socket} has data
	{module}/module {name} is [not] running
	{hashtable}/{queue} is [not] empty
	more {hashtable}
	{hashtable} has more data
	{hashtable} contains/does not contain {value}
	more {vector}
	{vector} has more data
	{vector} contains {value}
	prompt {string}
	<p>
	[1.001 GT]  12/10/00  Pre-existing.
	[1.002 GT]  16/04/03  Add "{file} is a directory".
	[1.003 GT]  11/05/03  Add "{hashtable} contains {value}".
	[1.004 GT]  22/06/03  Add "{value} does not contain {value}".
	</pre>
*/
public class BasicLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		boolean sense=true;
		getNextToken();
		if	(tokenIs("file"))
		{
			getNextToken();
			LVValue fileName=getValue();
			getNextToken();
			if (tokenIs("exists")) return new BasicCFile(fileName,BasicCFile.FILE_EXISTS,false);
			else if (tokenIs("does"))
			{
				getNextToken();
				if (tokenIs("not"))
				{
					getNextToken();
					if (tokenIs("exist")) return new BasicCFile(fileName,BasicCFile.FILE_EXISTS,true);
				}
			}
			if (tokenIs("is"))
			{
				skip("a");
				if (tokenIs("directory"))
					return new BasicCFile(fileName,BasicCFile.FILE_IS_DIRECTORY,false);
			}
			return null;
		}
		if	(tokenIs("module"))
		{
			LVValue name=getNextValue();
			sense=true;
			getNextToken();
			if (tokenIs("is"))
			{
				getNextToken();
				if (tokenIs("not"))
				{
					sense=false;
					getNextToken();
				}
				if (tokenIs("running")) return new BasicCModuleRunning(getProgram(),name,sense);
			}
			dontUnderstandToken();
		}
		if	(tokenIs("not"))
		{
			sense=false;
			getNextToken();
		}
		if (tokenIs("at"))
		{
			getNextToken();
			if (tokenIs("end"))
			{
				getNextToken();
				if (tokenIs("of"))
				{
					getNextToken();
					if (isSymbol())
					{
						if (getHandler() instanceof BasicHFile)
						{
							return new BasicCEndOfFile((BasicHFile)getHandler(),sense);
						}
					}
				}
			}
			return null;
		}
		if (tokenIs("prompt")) return new BasicCPrompt(getNextValue());
		if (tokenIs("more"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof BasicHHashtable)
					return new BasicCMore((BasicHHashtable)getHandler());
				if (getHandler() instanceof BasicHVector)
					return new BasicCMore((BasicHVector)getHandler());
			}
			return null;
		}
		if (isSymbol())
		{
			if (getHandler() instanceof BasicHFile)
			{
				skip("is");
				if (tokenIs("not"))
				{
					sense=false;
					getNextToken();
				}
				if (tokenIs("open"))
				{
					return new BasicCFileOpen((BasicHFile)getHandler(),sense);
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof LHModule)
			{
				skip("is");
				if (tokenIs("not"))
				{
					sense=false;
					getNextToken();
				}
				if (tokenIs("running"))
				{
					return new BasicCModuleRunning((LHModule)getHandler(),sense);
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof BasicHVector)
			{
				getNextToken();
				if (tokenIs("contains"))
				{
					return new BasicCContains((BasicHVector)getHandler(),getNextValue(),true);
				}
				if (tokenIs("does"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						getNextToken();
						if (tokenIs("contain"))
							return new BasicCContains((BasicHVector)getHandler(),getNextValue(),false);
					}
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof BasicHHashtable)
			{
				getNextToken();
				if (tokenIs("is"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						sense=false;
						getNextToken();
					}
					if (tokenIs("empty"))
						return new BasicCEmpty((BasicHHashtable)getHandler(),sense);
				}
				if (tokenIs("has"))
				{
					skip("more");
					if (tokenIs("data"))
					{
						return new BasicCMore((BasicHHashtable)getHandler());
					}
				}
				if (tokenIs("contains"))
				{
					return new BasicCContains((BasicHHashtable)getHandler(),getNextValue(),true);
				}
				if (tokenIs("does"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						getNextToken();
						if (tokenIs("contain"))
							return new BasicCContains((BasicHHashtable)getHandler(),getNextValue(),false);
					}
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof BasicHQueue)
			{
				getNextToken();
				if (tokenIs("is"))
				{
					getNextToken();
					if (tokenIs("not"))
					{
						sense=false;
						getNextToken();
					}
					if (tokenIs("empty"))
						return new BasicCEmpty((BasicHQueue)getHandler(),sense);
				}
				dontUnderstandToken();
			}
			if (!(getHandler() instanceof LHConstant))
			{
				if (!((LHVariableHandler)getHandler()).hasValue())
				{
					warning(this,LLMessages.noValue(getToken()));
					return null;
				}
			}
		}
		LVValue value=null;
		try { value=getValue(false); } catch (LLException e) { return null; }
		getNextToken();
		if (tokenIs("is"))
		{
			if	(!sense)	new LLException(BasicLMessages.syntaxError());
			getNextToken();
			if	(tokenIs("not"))
			{
				sense=false;
				getNextToken();
			}
			if (tokenIs("positive"))
			{
				if (sense) return new BasicCPositive(value);
				return new BasicCNegative(value);
			}
			else if (tokenIs("negative"))
			{
				if (sense) return new BasicCNegative(value);
				return new BasicCPositive(value);
			}
			else
			{
				if	(tokenIs("greater"))
				{
					skip("than");
					LVValue value2=getValue(false);
					if (sense) return new BasicCGreater(value,value2);
					return new BasicCNotGreater(value,value2);
				}
				else if (tokenIs("less"))
				{
					skip("than");
					LVValue value2=getValue(false);
					if (sense) return new BasicCLess(value,value2);
					return new BasicCNotLess(value,value2);
				}
				else
				{
					LVValue value2=getValue(false);
					if (value2==null) return null;
					if (sense) return new BasicCEqual(value,value2);
					return new BasicCNotEqual(value,value2);
				}
			}
		}
		else if (tokenIs("starts"))
		{
			skip("with");
			return new BasicCStartsWith(value,getValue());
		}
		else if (tokenIs("ends"))
		{
			skip("with");
			return new BasicCEndsWith(value,getValue());
		}
		else if (tokenIs("contains"))
		{
			return new BasicCContains(value,getNextValue(),true);
		}
		else if (tokenIs("does"))
		{
			skip("not");
			if (tokenIs("contain"))
			{
				return new BasicCContains(value,getNextValue(),false);
			}
		}
		else				/*	implied !=0	*/
		{
			unGetToken();
			if (sense) return new BasicCNotEqual(value,new LVConstant(0));
			return new BasicCEqual(value,new LVConstant(0));
		}
		return null;
	}
}
