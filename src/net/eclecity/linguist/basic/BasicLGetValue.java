// BasicLGetValue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic;

import java.util.Calendar;

import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHQueue;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.basic.value.BasicVAdded;
import net.eclecity.linguist.basic.value.BasicVAscii;
import net.eclecity.linguist.basic.value.BasicVBit;
import net.eclecity.linguist.basic.value.BasicVCalendar;
import net.eclecity.linguist.basic.value.BasicVCase;
import net.eclecity.linguist.basic.value.BasicVChars;
import net.eclecity.linguist.basic.value.BasicVDate;
import net.eclecity.linguist.basic.value.BasicVDatime;
import net.eclecity.linguist.basic.value.BasicVDay;
import net.eclecity.linguist.basic.value.BasicVDirectory;
import net.eclecity.linguist.basic.value.BasicVDivided;
import net.eclecity.linguist.basic.value.BasicVElementsIn;
import net.eclecity.linguist.basic.value.BasicVEncode;
import net.eclecity.linguist.basic.value.BasicVEventName;
import net.eclecity.linguist.basic.value.BasicVFilePath;
import net.eclecity.linguist.basic.value.BasicVFilesIn;
import net.eclecity.linguist.basic.value.BasicVFreeMemory;
import net.eclecity.linguist.basic.value.BasicVHalf;
import net.eclecity.linguist.basic.value.BasicVHex;
import net.eclecity.linguist.basic.value.BasicVIndexOf;
import net.eclecity.linguist.basic.value.BasicVLeft;
import net.eclecity.linguist.basic.value.BasicVLengthOf;
import net.eclecity.linguist.basic.value.BasicVLine;
import net.eclecity.linguist.basic.value.BasicVLinesIn;
import net.eclecity.linguist.basic.value.BasicVMessage;
import net.eclecity.linguist.basic.value.BasicVModifiedTime;
import net.eclecity.linguist.basic.value.BasicVModuleName;
import net.eclecity.linguist.basic.value.BasicVModulo;
import net.eclecity.linguist.basic.value.BasicVMultiplied;
import net.eclecity.linguist.basic.value.BasicVNetworkAddress;
import net.eclecity.linguist.basic.value.BasicVNetworkName;
import net.eclecity.linguist.basic.value.BasicVParam;
import net.eclecity.linguist.basic.value.BasicVParent;
import net.eclecity.linguist.basic.value.BasicVRandom;
import net.eclecity.linguist.basic.value.BasicVRight;
import net.eclecity.linguist.basic.value.BasicVSender;
import net.eclecity.linguist.basic.value.BasicVSquare;
import net.eclecity.linguist.basic.value.BasicVStringConstant;
import net.eclecity.linguist.basic.value.BasicVSystemName;
import net.eclecity.linguist.basic.value.BasicVSystemProperty;
import net.eclecity.linguist.basic.value.BasicVTaken;
import net.eclecity.linguist.basic.value.BasicVTime;
import net.eclecity.linguist.basic.value.BasicVTimeAt;
import net.eclecity.linguist.basic.value.BasicVTimerID;
import net.eclecity.linguist.basic.value.BasicVTimestamp;
import net.eclecity.linguist.basic.value.BasicVTotalMemory;
import net.eclecity.linguist.basic.value.BasicVUserHome;
import net.eclecity.linguist.basic.value.BasicVValueHolder;
import net.eclecity.linguist.basic.value.BasicVWord;
import net.eclecity.linguist.basic.value.BasicVWordsIn;
import net.eclecity.linguist.basic.value.BasicVXOR;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetValue;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.util.LUEncoder;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * Get a numeric or string value.
 * <p>
 * 
 * <pre>
 * 
 *  Numeric values:
 *  {constant}
 *  {variable}
 *  true
 *  false
 *  the elements in {array}
 *  the index of {variable}
 *  the index of {value} [in {vector}]
 *  random {expression}
 *  the value [of] {string}
 *  the length [of] {string}
 *  ascii {string}
 *  time &lt;string}
 *  the timestamp of {module}
 *  the words in {string}
 *  the lines in {string}
 *  the modify time of {file}
 *  the event type
 *  the free memory
 *  the total memory
 *  the day
 *  the year/month/date/hour/minute [at {timezone}]/[of {value}]
 *  the time/second/millisecond [at {timezone}]
 *  the timer id
 *  half {value}
 *  bit {n} of {value}
 *  short/long date {value}
 *  {value} added to {value}
 *  {value} taken from {value}
 *  {value} multiplied by {value}
 *  {value} divided by {value}
 *  {value} modulo {value}
 *  {value} xor {value}
 * 
 *  String values:
 *  {string literal}
 *  {buffer}
 *  {variable}
 *  {socket}
 *  {connection}
 *  {hashtable}
 *  return
 *  newline
 *  tab
 *  quote
 *  ascii {value}
 *  hex {value}
 *  char[acter] {n} [to {m}] of {string}
 *  word {n} of {string}
 *  line {n} of {string}
 *  left {n} [of] {string}
 *  right {n} [of] {string}
 *  next {hashtable}/{vector}
 *  system property {key}
 *  the arg
 *  the message
 *  the sender
 *  the parent
 *  the time
 *  the system name
 *  the module name
 *  the event name
 *  the directory
 *  the file path
 *  the network name/address
 *  the first/second/third/.../last char[acter]/word/line of {string}
 *  the files in [tree] {name}
 *  lowercase {value}
 *  uppercase {value}
 *  encode [url/base64] {value}
 *  decode [url/base64] {value}
 * 
 *  The item may optionally be followed by &quot;cat {item} ...&quot; and so on.
 *  This catenation only works at the outermost level, so
 * 
 *  line 5 of Buffer cat return
 * 
 *  extracts line 5 from Buffer and puts a return after it, as would
 *  be expected, rather than adding a return to Buffer and then extracting
 *  line 5 from the result.
 *  &lt;pre&gt;
 *  [1.001 GT]  15/07/00  Pre-existing.
 *  &lt;/pre&gt;
 * 
 */
public class BasicLGetValue extends LLGetValue
{
	public boolean isNumeric()
	{
		return numeric;
	}

	public boolean isString()
	{
		return !numeric;
	}

	/****************************************************************************
	 * Create a numeric or string value.
	 * 
	 * @param compiler the object that is compiling this script.
	 * @return a value, or null if the construct was not understood.
	 */
	public LVValue getValue(LLCompiler compiler) throws LLException
	{
		LVValue item = getItem(this.compiler = compiler);
		if (item == null) return null;
		if (item.isNumeric())
		{
			getNextToken();
			if (tokenIs("added"))
			{
				skip("to");
				return new BasicVAdded(item, getValue());
			}
			if (tokenIs("taken"))
			{
				skip("from");
				return new BasicVTaken(item, getValue());
			}
			if (tokenIs("multiplied"))
			{
				skip("by");
				return new BasicVMultiplied(item, getValue());
			}
			if (tokenIs("divided"))
			{
				skip("by");
				return new BasicVDivided(item, getValue());
			}
			if (tokenIs("modulo")) { return new BasicVModulo(item, getNextValue()); }
			if (tokenIs("xor")) { return new BasicVXOR(item, getNextValue()); }
			unGetToken();
		}
		return item;
	}

	private LVValue getItem(LLCompiler compiler) throws LLException
	{
		LHHandler handler;
		LVValue ordinal;
		if (tokenIs("true")) return new LVConstant(1);
		else if (tokenIs("false")) return new LVConstant(0);
		else if (tokenIs("empty"))
		{
			numeric = false;
			return new LVStringConstant("");
		}
		else if (tokenIs("tab"))
		{
			numeric = false;
			return new LVStringConstant("\t");
		}
		else if (tokenIs("quote"))
		{
			numeric = false;
			return new LVStringConstant("\"");
		}
		else if (tokenIs("newline"))
		{
			numeric = false;
			return new LVStringConstant("\n");
		}
		else if (tokenIs("return"))
		{
			numeric = false;
			return new LVStringConstant("\r");
		}
		else if (tokenIs("newline"))
		{
			numeric = false;
			return new LVStringConstant("\n");
		}
		else if (tokenIs("random"))
		{
			getNextToken();
			return new BasicVRandom(getValue());
		}
		else if (tokenIs("ascii"))
		{
			getNextToken();
			LVValue v = getValue();
			numeric = !v.isNumeric();
			return new BasicVAscii(v);
		}
		else if (tokenIs("hex"))
		{
			getNextToken();
			LVValue v = getValue();
			numeric = !v.isNumeric();
			return new BasicVHex(v);
		}
		else if (tokenIs("next"))
		{
			getNextToken();
			if (isSymbol())
			{
				if (getHandler() instanceof BasicHHashtable) { return new BasicVValueHolder(
						(BasicHHashtable) getHandler()); }
				if (getHandler() instanceof BasicHVector) { return new BasicVValueHolder(
						(BasicHVector) getHandler()); }
			}
			return null;
		}
		else if (tokenIs("system"))
		{
			getNextToken();
			if (tokenIs("property"))
			{
				numeric = false;
				return new BasicVSystemProperty(getNextValue());
			}
		}
		else if (tokenIs("the"))
		{
			getNextToken();
			if (tokenIs("timestamp"))
			{
				numeric = true;
				skip("of");
				if (isSymbol())
				{
					if (getHandler() instanceof LHModule) { return new BasicVTimestamp(
							(LHModule) getHandler()); }
				}
				return null;
			}
      else if (tokenIs("arg"))
			{
				numeric = false;
				return new BasicVParam(getProgram());
			}
      else if (tokenIs("system"))
			{
				getNextToken();
				if (tokenIs("name"))
				{
					numeric = false;
					return new BasicVSystemName();
				}
				return null;
			}
			if (tokenIs("module"))
			{
				getNextToken();
				if (tokenIs("name"))
				{
					numeric = false;
					return new BasicVModuleName(getProgram());
				}
				return null;
			}
      else if (tokenIs("event"))
			{
				getNextToken();
				if (tokenIs("name"))
				{
					numeric = false;
					return new BasicVEventName(getProgram());
				}
				return null;
			}
      else if (tokenIs("directory"))
			{
				return new BasicVDirectory();
			}
      else if (tokenIs("user"))
      {
        getNextToken();
        if (tokenIs("home"))
          return new BasicVUserHome();
        return null;
      }
			else if (tokenIs("elements"))
			{
				skip("in");
				if (isSymbol()) { return new BasicVElementsIn(
						(LHVariableHandler) getHandler()); }
				throw new LLException(LLMessages.unhandledVariable(getToken()));
			}
			else if (tokenIs("index"))
			{
				skip("of");
				if (isSymbol())
				{
					LHVariableHandler vh = (LHVariableHandler) getHandler();
					getNextToken();
					if (tokenIs("in"))
					{
						getNextToken();
						if (isSymbol())
						{
							if (getHandler() instanceof BasicHVector)
							{
								if (vh instanceof LHStringHolder) return new BasicVIndexOf(
										(BasicHVector) getHandler(), (LHStringHolder) vh);
							}
						}
						dontUnderstandToken();
					}
					else
					{
						unGetToken();
						return new BasicVIndexOf(vh);
					}
				}
				else
				{
					LVValue value = getValue();
					getNextToken();
					if (tokenIs("in"))
					{
						getNextToken();
						if (isSymbol())
						{
							if (getHandler() instanceof BasicHVector) { return new BasicVIndexOf(
									(BasicHVector) getHandler(), value); }
						}
					}
					dontUnderstandToken();
				}
				throw new LLException(LLMessages.unhandledVariable(getToken()));
			}
			else if (tokenIs("message"))
			{
				numeric = false;
				return new BasicVMessage(getProgram());
			}
			else if (tokenIs("sender"))
			{
				numeric = false;
				return new BasicVSender(getProgram());
			}
			else if (tokenIs("parent"))
			{
				numeric = false;
				return new BasicVParent(getProgram());
			}
			else if (tokenIs("file"))
			{
				numeric = false;
				getNextToken();
				if (tokenIs("path")) return new BasicVFilePath(getProgram());
				dontUnderstandToken();
			}
			else if (tokenIs("files"))
			{
				numeric = false;
				getNextToken();
				if (tokenIs("in"))
				{
					boolean tree = false;
					getNextToken();
					if (tokenIs("tree")) tree = true;
					else unGetToken();
					return new BasicVFilesIn(getNextValue(), tree);
				}
				dontUnderstandToken();
			}
			else if (tokenIs("modify"))
			{
				getNextToken();
				if (tokenIs("time"))
				{
					numeric = true;
					skip("of");
					if (isSymbol())
					{
						if (getHandler() instanceof BasicHFile) return new BasicVModifiedTime(
								(BasicHFile) getHandler());
					}
					return new BasicVModifiedTime(getValue());
				}
				dontUnderstandToken();
			}
			else if (tokenIs("network"))
			{
				numeric = false;
				getNextToken();
				if (tokenIs("name")) return new BasicVNetworkName(getProgram());
				else if (tokenIs("address")) return new BasicVNetworkAddress();
				dontUnderstandToken();
			}
			else if ((tokenIs("first") || tokenIs("second") || tokenIs("third")
					|| tokenIs("fourth") || tokenIs("fifth") || tokenIs("sixth")
					|| tokenIs("seventh") || tokenIs("eighth") || tokenIs("ninth")
					|| tokenIs("tenth") || tokenIs("last"))
					&& (ordinal = getOrdinal()) != null) return ordinal;
			// All these are numeric
			else if (tokenIs("square"))
			{
				skip("of");
				return new BasicVSquare(getValue());
			}
			else if (tokenIs("value"))
			{
				skip("of");
				return getValue();
			}
			else if (tokenIs("length"))
			{
				skip("of");
				return new BasicVLengthOf(getValue());
			}
			else if (tokenIs("words"))
			{
				skip("in");
				return new BasicVWordsIn(getValue());
			}
			else if (tokenIs("lines"))
			{
				skip("in");
				return new BasicVLinesIn(getValue());
			}
			else if (tokenIs("millisecond"))
			{
				getNextToken();
				if (tokenIs("at")) return new BasicVTimeAt(getNextValue());
				unGetToken();
				return new BasicVTime();
			}
			else if (tokenIs("free"))
			{
				getNextToken();
				if (tokenIs("memory")) return new BasicVFreeMemory();
				dontUnderstandToken();
			}
			else if (tokenIs("total"))
			{
				getNextToken();
				if (tokenIs("memory")) return new BasicVTotalMemory();
				dontUnderstandToken();
			}
			else if (tokenIs("day"))
			{
				return new BasicVDay();
			}
			else if (tokenIs("year"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.YEAR, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.YEAR, true);
				unGetToken();
				return new BasicVCalendar(Calendar.YEAR);
			}
			else if (tokenIs("month"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.MONTH, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.MONTH, true);
				unGetToken();
				return new BasicVCalendar(Calendar.MONTH);
			}
			else if (tokenIs("date"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.DATE, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.DATE, true);
				unGetToken();
				return new BasicVCalendar(Calendar.DATE);
			}
			else if (tokenIs("hour"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.HOUR_OF_DAY, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.HOUR_OF_DAY, true);
				unGetToken();
				return new BasicVCalendar(Calendar.HOUR_OF_DAY);
			}
			else if (tokenIs("minute"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.MINUTE, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.MINUTE, true);
				unGetToken();
				return new BasicVCalendar(Calendar.MINUTE);
			}
			else if (tokenIs("second"))
			{
				numeric = true;
				getNextToken();
				if (tokenIs("at")) return new BasicVCalendar(getNextValue(),
						Calendar.SECOND, false);
				if (tokenIs("of")) return new BasicVCalendar(getNextValue(),
						Calendar.SECOND, true);
				unGetToken();
				return new BasicVCalendar(Calendar.SECOND);
			}
			else if (tokenIs("timer"))
			{
				skip("id");
				return new BasicVTimerID(getProgram());
			}
		}
		else if (getToken().charAt(0) == '\"' || getToken().charAt(0) == '\'')
		{
			numeric = false;
			getString();
			return new LVStringConstant(getToken());
		}
		else if (tokenIs("character") || tokenIs("char"))
		{
			numeric = false;
			getNextToken();
			LVValue start = null;
			try
			{
				start = getValue();
			}
			catch (LLException e)
			{
				return null;
			}
			getNextToken();
			LVValue end = null;
			if (tokenIs("to"))
			{
				getNextToken();
				if (tokenIs("end")) end = new LVConstant(-1);
				else end = getValue();
			}
			else
			{
				end = new LVConstant(0);
				unGetToken();
			}
			skip("of");
			return new BasicVChars(getValue(false), start, end);
		}
		else if (tokenIs("word"))
		{
			numeric = false;
			getNextToken();
			LVValue value = null;
			try
			{
				value = getValue();
			}
			catch (LLException e)
			{
				return null;
			}
			skip("of");
			return new BasicVWord(getValue(false), value);
		}
		else if (tokenIs("line"))
		{
			numeric = false;
			getNextToken();
			LVValue value = null;
			try
			{
				value = getValue();
			}
			catch (LLException e)
			{
				return null;
			}
			skip("of");
			return new BasicVLine(getValue(false), value);
		}
		else if (tokenIs("left"))
		{
			numeric = false;
			getNextToken();
			LVValue value = null;
			try
			{
				value = getValue();
			}
			catch (LLException e)
			{
				return null;
			}
			skip("of");
			return new BasicVLeft(getValue(false), value);
		}
		else if (tokenIs("right"))
		{
			numeric = false;
			getNextToken();
			LVValue value = null;
			try
			{
				value = getValue();
			}
			catch (LLException e)
			{
				return null;
			}
			skip("of");
			return new BasicVRight(getValue(false), value);
		}
		else if (tokenIs("half"))
		{
			return new BasicVHalf(getNextValue());
		}
		else if (tokenIs("bit"))
		{
			LVValue bit = getNextValue();
			skip("of");
			return new BasicVBit(bit, getValue());
		}
		else if (tokenIs("time"))
		{
			LVValue value = getNextValue();
			numeric = !value.isNumeric();
			return new BasicVTime(value);
		}
		else if (tokenIs("date"))
		{
			LVValue value = getNextValue();
			numeric = !value.isNumeric();
			return new BasicVDate(value);
		}
		else if (tokenIs("datime"))
		{
			LVValue value = getNextValue();
			getNextToken();
			if (tokenIs("format"))
			{
				numeric = false;
				return new BasicVDatime(value, getNextValue());
			}
			unGetToken();
			numeric = !value.isNumeric();
			return new BasicVDatime(value);
		}
		else if (tokenIs("lowercase"))
		{
			numeric = false;
			return new BasicVCase(getNextValue(), false);
		}
		else if (tokenIs("uppercase"))
		{
			numeric = false;
			return new BasicVCase(getNextValue(), true);
		}
		else if (tokenIs("encode"))
		{
			numeric = false;
			String method = null;
			getNextToken();
			if (LUEncoder.isValidMethod(getToken())) method = getToken();
			else unGetToken();
			return new BasicVEncode(getNextValue(), method, true);
		}
		else if (tokenIs("decode"))
		{
			numeric = false;
			String method = null;
			getNextToken();
			if (LUEncoder.isValidMethod(getToken())) method = getToken();
			else unGetToken();
			return new BasicVEncode(getNextValue(), method, false);
		}
		else if (isConstant())
		{
			return new LVConstant(evaluate(getToken()));
		}
		else if ((handler = findSymbol()) != null)
		{
			if (handler instanceof LHConstant)
			{
				LHConstant c = (LHConstant) handler;
				numeric = c.isNumeric();
				if (!numeric) return new BasicVStringConstant(c);
			}
			if (handler instanceof LHValueHolder)
			{
				numeric = ((LHValueHolder) handler).isNumeric();
				return new BasicVValueHolder((LHValueHolder) handler);
			}
			if (handler instanceof BasicHQueue) { return new BasicVValueHolder(
					(BasicHQueue) handler); }
			warning(this, BasicLMessages.notArithmetic(getToken()));
		}
		return null;
	}

	private LVValue getOrdinal() throws LLException
	{
		unGetToken();
		int rewindPosition = compiler.mark();
		getNextToken();
		numeric = false;
		int n = 0;
		if (tokenIs("first")) n = 0;
		else if (tokenIs("second")) n = 1;
		else if (tokenIs("third")) n = 2;
		else if (tokenIs("fourth")) n = 3;
		else if (tokenIs("fifth")) n = 4;
		else if (tokenIs("sixth")) n = 5;
		else if (tokenIs("seventh")) n = 6;
		else if (tokenIs("eighth")) n = 7;
		else if (tokenIs("ninth")) n = 8;
		else if (tokenIs("tenth")) n = 9;
		else if (tokenIs("last")) n = -1;
		getNextToken();
		if (tokenIs("char") || tokenIs("character"))
		{
			skip("of");
			return new BasicVChars(getValue(), new LVConstant(n),
					new LVConstant(0));
		}
		else if (tokenIs("word"))
		{
			skip("of");
			return new BasicVWord(getValue(), new LVConstant(n));
		}
		else if (tokenIs("line"))
		{
			skip("of");
			return new BasicVLine(getValue(), new LVConstant(n));
		}
		compiler.rewind(rewindPosition);
		return null;
	}
}