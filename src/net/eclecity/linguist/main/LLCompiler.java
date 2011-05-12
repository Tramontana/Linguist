// LLCompiler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.handler.LHFlag;
import net.eclecity.linguist.handler.LHGoto;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHLabel;
import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVPair;
import net.eclecity.linguist.value.LVString;
import net.eclecity.linguist.value.LVStringConstant;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * This class is the main program for the compiler.
 */
public class LLCompiler extends LLObject
{
	public static String compiledExtension = ".lrun";

	private String name;
	private String[] packages = null;
	private boolean atEOF;
	private String param;
	private String oldToken;
	private boolean gotToken;
	private String currentLine;
	private int scriptPos;
	private int tokenStart;
	private int currentLino;
	private int scriptLine;
	private int currentLength;
	private LRProgram program;
	private int programCounter;
	private Hashtable symbols;
	private Hashtable userData;
	private Vector warnings;
	private boolean warningIssued;

	private static Hashtable modules = new Hashtable(11);
	private static Hashtable modulesToCompile = null;

	public LLScript[] script;
	public int pass;
	public String token;
	public int maxVar;
	public int scriptLines;
	public LHHandler symbolHandler;
	public boolean inConstant = false;

	public LLCompiler()
	{
		symbols = new Hashtable();
		userData = new Hashtable();
	}

	public String getScriptName()
	{
		return name;
	}

	public String[] getPackages()
	{
		return packages;
	}

	public int getCurrentLino()
	{
		return currentLino; /* script[currentLino].line; */
	}

	public int getCurrentScriptLine()
	{
		return script[currentLino].line;
	}

	public String getRelativeLine(int n)
	{
		return script[currentLino + n].text;
	}

	public LRProgram getProgram()
	{
		return program;
	}

	public int getProgramCounter()
	{
		return programCounter;
	}

	public String getParam()
	{
		return param;
	}

	public void putData(String name, Object data)
	{
		if (pass == 2) userData.put(name, data);
	}

	public Object getData(String name)
	{
		return (pass == 2) ? userData.get(name) : null;
	}

	/****************************************************************************
	 * Compile the script.
	 * 
	 * @param name the name of the script.
	 * @param script the String array containing the lines of the script.
	 * @param param the parameter supplied as -a (if any).
	 * @return The compiled program.
	 */
	public LRProgram compile(String name, LLScript[] script, String param,
			String compiledExt) throws LLException
	{
		this.name = name;
		this.script = script;
		this.param = param;
		compiledExtension = compiledExt;
		scriptLines = script.length;
		errorLine = -1;
		systemOut("");
		doPass(1);
		doPass(2);
		systemOut(LLMessages.compilationComplete(name));
		return program;
	}

	/****************************************************************************
	 * Do a single pass. The compiler performs two passes; one to locate
	 * variables and the other to generate the output file for the runtime
	 * module.
	 * 
	 * @param the pass (1 or 2).
	 */
	private void doPass(int pass) throws LLException
	{
		this.pass = pass;
		systemOut("Pass " + pass + " of " + name);
		program = new LRProgram();
		programCounter = 0;
		currentLine = script[0].text;
		currentLength = currentLine.length();
		scriptLine = 0;
		scriptPos = 0;
		atEOF = false;
		gotToken = false;
		addCommand(new LHGoto(0, 1));
		try
		{
			while (true)
				doKeyword();
		}
		catch (LLException e)
		{
			errorLine = currentLino;
			Vector message = e.getMessage(this);
			if (message.size() > 0)
			{
				int n;
				for (n = 0; n < message.size(); n++)
					systemOut((String) message.elementAt(n));
				if (warnings.size() > 0)
				{
					systemOut(LLText.warnings());
					for (n = 0; n < warnings.size(); n++)
						systemOut((String) warnings.elementAt(n));
				}
				program = null;
				throw e;
			}
		}
	}

	private static int errorLine = -1;

	public int getErrorLine()
	{
		return errorLine;
	}

	/****************************************************************************
	 * Process a single keyword.
	 */
	public void doKeyword() throws LLException
	{
		int n;
		warnings = new Vector(); // reset the warnings
		inConstant = false;
		getNextToken();
		currentLino = scriptLine;
		if (atEOF) return;
		// If the instruction is 'package' we add the named package.
		if (token.equals(LLText.packageString()))
		{
			getNextToken();
			if (packages == null)
			{
				packages = new String[1];
				packages[0] = token;
			}
			else
			{
				for (n = 0; n < packages.length; n++)
				{
					if (packages[n].equals(token)) return; // already there
				}
				String[] p = new String[packages.length + 1];
				for (n = 0; n < packages.length; n++)
					p[n] = packages[n];
				p[n] = token;
				packages = p;
			}
		}
		// If the instruction is 'compile' we add the named script to the
		// list of files to be compiled.
		else if (token.equals(LLText.compileString()))
		{
			getNextToken();
			if (modulesToCompile == null) modulesToCompile = new Hashtable(11);
			modulesToCompile.put(token, token);
		}
		// If the instruction is 'begin' we do a begin...end block.
		else if (token.equals(LLText.beginString()))
		{
			while (true)
			{
				if (atEOF) return;
				getNextToken();
				if (token.equals(LLText.endString())) break;
				unGetToken();
				doKeyword();
			}
		}
		// If the instruction ends with a colon it's a label.
		else
		{
			if (token.endsWith(":"))
			{
				String name = token.substring(0, token.length() - 1);
				if (pass == 1)
				{
					if (isSymbol(name)) throw new LLException(LLMessages
							.nameAlreadyUsed(name));
				}
				LHLabel label = new LHLabel(getCurrentLino(), programCounter);
				if (pass == 1) symbols.put(name, label);
				addCommand(label);
				return;
			}
			// Otherwise it's a regular command.
			for (n = 0; n < token.length(); n++)
			{
				if (!Character.isJavaIdentifierPart(token.charAt(n))) throw new LLException(
						LLMessages.badCharacter(token));
			}
			String name = token.substring(0, 1).toUpperCase() + token.substring(1);
			if (packages != null)
			{
				for (n = 0; n < packages.length; n++)
				{
					resetMark();
					int m = mark();
					Class c = null;
					try
					{
						String className = "net.eclecity.linguist." + packages[n] + ".keyword."
								+ packages[n].substring(0, 1).toUpperCase()
								+ packages[n].substring(1).toLowerCase() + "K" + name;
						c = Class.forName(className);
						if (c != null)
						{
							LHHandler h = handleKeyword(c);
							if (h != null)
							{
								addCommand(h);
								return;
							}
						}
					}
					catch (ClassNotFoundException e)
					{}
					rewind(m);
				}
			}
			throw new LLException(LLMessages.unhandledKeyword(token));
		}
	}

	/****************************************************************************
	 * Find a handler for a given class.
	 * 
	 * @param c the class to handle.
	 * @return The handler, or null.
	 */
	public LHHandler handleKeyword(Class c) throws LLException
	{
		try
		{
			warningIssued = false;
			LKHandler h = (LKHandler) c.newInstance();
			LHHandler hh = h.compile(this);
			if (hh == null) warning(h, LLMessages.notHandled(token));
			return hh;
		}
		catch (InstantiationException e)
		{
			classError(c);
		}
		catch (IllegalAccessException e)
		{
			classError(c);
		}
		return null;
	}

	public void classError(Class c) throws LLException
	{
		throw new LLException(LLMessages.cantInstantiate(c.getName()));
	}

	/****************************************************************************
	 * Get a numeric or string value. Try each of the packages until one returns
	 * an LVValue.
	 */
	public LVValue getValue() throws LLException
	{
		return getValue(true);
	}

	public LVValue getValue(boolean allowMultipartString) throws LLException
	{
		numeric = true;
		LVValue value = getSingleValue();
		if (value == null) return null;
		boolean type = numeric;
		if (allowMultipartString)
		{
			getNextToken();
			if (token.equals(LLText.catString()))
			{
				type = false; // must be a string
				Vector v = new Vector();
				v.addElement(value);
				while (true)
				{
					getNextToken();
					LVValue item = getSingleValue();
					if (item == null) return null;
					v.addElement(item);
					getNextToken();
					if (!token.equals(LLText.catString())) break;
				}
				value = new LVString(v);
			}
			unGetToken();
		}
		if (value != null) value.setNumeric(type);
		return value;
	}

	private boolean numeric;

	private LVValue getSingleValue() throws LLException
	{
		for (int n = 0; n < packages.length; n++)
		{
			int m = mark(); // remember where we are
			Class c = null;
			try
			{
				String className = "net.eclecity.linguist." + packages[n] + "."
						+ packages[n].substring(0, 1).toUpperCase()
						+ packages[n].substring(1).toLowerCase() + "LGetValue";
				c = Class.forName(className);
				if (c != null)
				{
					try
					{
						LLGetValue gv = (LLGetValue) c.newInstance();
						LVValue value = gv.getValue(this);
						numeric = gv.isNumeric();
						if (value != null) return value;
					}
					catch (InstantiationException e)
					{
						classError(c);
					}
					catch (IllegalAccessException e)
					{
						classError(c);
					}
				}
			}
			catch (ClassNotFoundException e)
			{}
			rewind(m); // back up to the start of the value again
		}
		throw new LLException(LLMessages.unhandledValue(token));
	}

	/****************************************************************************
	 * Get a pair of values. Try each of the packages until one returns an
	 * LVPair.
	 */
	public LVPair getPair() throws LLException
	{
		for (int n = 0; n < packages.length; n++)
		{
			int m = mark(); // remember where we are
			Class c = null;
			try
			{
				String className = "net.eclecity.linguist." + packages[n] + "."
						+ packages[n].substring(0, 1).toUpperCase()
						+ packages[n].substring(1).toLowerCase() + "LGetPair";
				c = Class.forName(className);
				if (c != null)
				{
					try
					{
						LLGetPair gp = (LLGetPair) c.newInstance();
						LVPair pair = gp.getPair(this);
						if (pair != null) return pair;
					}
					catch (InstantiationException e)
					{
						classError(c);
					}
					catch (IllegalAccessException e)
					{
						classError(c);
					}
				}
			}
			catch (ClassNotFoundException e)
			{}
			rewind(m); // back up to the start of the value again
		}
		throw new LLException(LLMessages.unhandledPair(token));
	}

	/****************************************************************************
	 * Get a condition. Try each of the packages until one returns an
	 * LCCondition.
	 */
	public LCCondition getCondition() throws LLException
	{
		for (int n = 0; n < packages.length; n++)
		{
			int m = mark(); // remember where we are
			Class c = null;
			try
			{
				String className = "net.eclecity.linguist." + packages[n] + "."
						+ packages[n].substring(0, 1).toUpperCase()
						+ packages[n].substring(1).toLowerCase() + "LGetCondition";
				c = Class.forName(className);
				if (c != null)
				{
					try
					{
						LLGetCondition gc = (LLGetCondition) c.newInstance();
						LCCondition condition = gc.getCondition(this);
						if (condition != null) return condition;
					}
					catch (InstantiationException e)
					{
						classError(c);
					}
					catch (IllegalAccessException e)
					{
						classError(c);
					}
				}
			}
			catch (ClassNotFoundException e)
			{}
			rewind(m); // back up to the start of the value again
		}
		throw new LLException(LLMessages.unhandledCondition(token));
	}

	/****************************************************************************
	 * Save a compile warning.
	 * 
	 * @param message the warning message.
	 */
	public void warning(Object source, String message)
	{
		if (!warningIssued)
		{
			String s = source.getClass().getName();
			warnings.addElement(s + ": " + message);
			warningIssued = true;
		}
	}

	/****************************************************************************
	 * Put a symbol in the table.
	 * 
	 * @param name the key.
	 * @param value the value of the symbol.
	 */
	public void putSymbol(String name, Object value) throws LLException
	{
		if (pass == 1 && symbols.get(name) != null) throw new LLException(
				LLMessages.nameAlreadyUsed(name));
		if (pass == 2) symbols.remove(name);
		symbols.put(name, value);
	}

	/****************************************************************************
	 * Add a new handler to the program.
	 * 
	 * @param h the handler to add.
	 */
	public void addCommand(LHHandler h)
	{
		if (h != null)
		{
			if (h instanceof LHFlag)
			{}
			else
			{
				if (pass == 2) program.addElement(h);
				programCounter++;
			}
		}
	}

	/****************************************************************************
	 * Replace a command in the program.
	 * 
	 * @param h the command to put.
	 * @param n the index.
	 */
	public void setCommandAt(LHHandler h, int n)
	{
		if (pass == 2) program.setElementAt(h, n);
	}

	/****************************************************************************
	 * Skip the token named.
	 * 
	 * @param s the token to skip.
	 */
	public void skip(String s) throws LLException
	{
		getNextToken();
		if (token.equals(s)) getNextToken();
	}

	/****************************************************************************
	 * Get the next token from the script.
	 */
	public void getNextToken() throws LLException
	{
		if (gotToken)
		{
			token = oldToken;
			gotToken = false;
			return;
		}
		if (atEOF) throw new LLException("");
		token = new String();

		//	first skip whitespace
		while (true)
		{
			if (scriptPos == currentLength)
			{
				if (++scriptLine == scriptLines)
				{
					atEOF = true;
					return;
				}
				currentLine = script[scriptLine].text;
				currentLength = currentLine.length();
				scriptPos = 0;
			}
			if (currentLength > 0)
			{
				if (!Character.isWhitespace(currentLine.charAt(scriptPos))) break;
				scriptPos++;
			}
		}

		// now copy characters until whitespace
		tokenStart = scriptPos; // mark the start of the token
		char c;
		while (scriptPos < currentLength)
		{
			if (Character.isWhitespace(c = currentLine.charAt(scriptPos))) break;
			token += c;
			if (token.equals("!"))
			{
				if (++scriptLine == scriptLines)
				{
					pass = 0;
					atEOF = true;
					return;
				}
				currentLine = script[scriptLine].text;
				currentLength = currentLine.length();
				scriptPos = 0;
				getNextToken();
				break;
			}
			scriptPos++;
		}
	}

	/****************************************************************************
	 * Unget a token. Set a flag to say 'already got a token'.
	 */
	public void unGetToken()
	{
		if (token.length() > 0)
		{
			oldToken = token;
			gotToken = true;
		}
	}

	public void unGetToken(String s)
	{
		token = s;
		unGetToken();
	}

	/****************************************************************************
	 * Get a quoted string from the script. This replaces the existing token.
	 */
	public String getString()
	{
		char quote = token.charAt(0);
		scriptPos = tokenStart + 1;
		token = new String();
		char c;
		while ((c = currentLine.charAt(scriptPos)) != quote)
		{
			token += c;
			scriptPos++;
			if (scriptPos == currentLength) break;
		}
		if (scriptPos < currentLength) scriptPos++;
		return token;
	}

	private Vector markStack = new Vector();

	/****************************************************************************
	 * Mark the current position in the script.
	 */
	public int mark()
	{
		int n = markStack.size();
		markStack.addElement(new LLMark(scriptLine, scriptPos, token));
		return n;
	}

	public void resetMark()
	{
		markStack = new Vector();
		gotToken = false;
	}

	/****************************************************************************
	 * Return to the marked position in the script.
	 */
	public void rewind(int n)
	{
		LLMark env = (LLMark) markStack.elementAt(n);
		scriptLine = env.line;
		scriptPos = env.pos;
		currentLine = script[scriptLine].text;
		currentLength = currentLine.length();
		token = env.token;
		gotToken = false;
	}

	/****************************************************************************
	 * Find the symbol given by the current token.
	 * 
	 * @return The symbol handler, or null.
	 */
	public LHHandler findSymbol()
	{
		symbolHandler = findSymbol(token);
		return symbolHandler;
	}

	/****************************************************************************
	 * Find the symbol given by the parameter.
	 * 
	 * @param name the symbol to search for.
	 * @return null if the symbol does not exist.
	 */
	public LHHandler findSymbol(String name)
	{
		if (symbols.containsKey(name)) return (LHHandler) symbols.get(name);
		return null;
	}

	/****************************************************************************
	 * Check if the current token is in the symbol table.
	 */
	public boolean isSymbol()
	{
		return ((symbolHandler = findSymbol(token)) != null);
	}

	public boolean isSymbol(String s)
	{
		return ((symbolHandler = findSymbol(s)) != null);
	}

	/****************************************************************************
	 * Report if the current token represents a constant.
	 * 
	 * @return True if a constant.
	 */
	public boolean isConstant()
	{
		int offset = 0;
		if (token.charAt(0) == '-') offset = 1;
		if (Character.isDigit(token.charAt(offset))) return true;
		String s = token.substring(offset);
		for (int n = 0; n < s.length(); n++)
		{
			if (!Character.isJavaIdentifierPart(s.charAt(n)))
			{
				s = s.substring(0, n);
				break;
			}
		}
		LHHandler handler = findSymbol(s);
		if (handler != null)
		{
			if (handler instanceof LHConstant)
			{
				if (((LHConstant) handler).isNumeric()) return true;
			}
		}
		return false;
	}

	/****************************************************************************
	 * Check if the current token is a program label.
	 */
	protected void checkProgramLabel() throws LLException
	{
		if ((symbolHandler = findSymbol(token)) == null) if (pass == 2) throw new LLException(
				LLMessages.unknownVariable(token));
		if (!(symbolHandler instanceof LHLabel)) if (pass == 2) throw new LLException(
				LLMessages.notLabel(token));
	}

	protected int getLabelValue()
	{
		return (symbolHandler != null) ? symbolHandler.getPC() : 0;
	}

	/****************************************************************************
	 * Throw a compile error - "Don't understand token".
	 */
	public void dontUnderstandToken() throws LLException
	{
		throw new LLException(LLMessages.dontUnderstand(token));
	}

	/****************************************************************************
	 * Throw a compile error - "Inappropriate type".
	 */
	public void inappropriateType() throws LLException
	{
		throw new LLException(LLMessages.inappropriateType(token));
	}

	/****************************************************************************
	 * Throw a compile error - "Constant expected".
	 */
	public void constantExpected() throws LLException
	{
		throw new LLException(LLMessages.constantExpected(token));
	}

	/****************************************************************************
	 * Add a module to be compiled. Only if its name can be evaluated at compile
	 * time.
	 */
	public void addModuleToCompile(LVValue name)
	{
		if (name instanceof LVStringConstant)
		{
			try
			{
				String s = name.getStringValue();
				// ignore URLs
				if (s.indexOf("://") < 0) modules.put(s, s);
			}
			catch (LRException ignored)
			{}
		}
	}

	/****************************************************************************
	 * Compile the extra modules. If the modulesToCompile table is non-null, only
	 * the files named in it will be compiled. Otherwise, compile all files that
	 * were encountered during compilation.
	 */
	public void compileExtraModules(String path) throws LLException
	{
		Enumeration enumeration;
		if (modulesToCompile != null)
		{
			enumeration = modulesToCompile.keys();
			while (enumeration.hasMoreElements())
				compileModule(path + (String) enumeration.nextElement());
		}
		else while (!modules.isEmpty())
		{
			enumeration = modules.keys();
			while (enumeration.hasMoreElements())
			{
				String name = (String) enumeration.nextElement();
				compileModule(path + name);
				modules.remove(name);
			}
		}
	}

	/****************************************************************************
	 * Compile a script to binary.
	 */
	private void compileModule(String name) throws LLException
	{
		if (pass == 1) return;
		name += this.name.substring(this.name.indexOf("."));
		// Look for the script.
		LLScript[] script = LLMain.getFile(name, true);
		// Now compile it.
		try
		{
			LRProgram program = null;
			program = new LLCompiler().compile(name, script, param,
					compiledExtension);
			name = name.substring(0, name.indexOf("."));
			try
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(bos);
				out.writeObject(script);
				out.writeObject(packages);
				out.writeObject(program);
				out.flush();
				byte[] b = bos.toByteArray();
				out.close();
				FileOutputStream fos = new FileOutputStream(name
						+ compiledExtension);
				ZipOutputStream zos = new ZipOutputStream(fos);
				ZipEntry ze = new ZipEntry("script");
				ze.setTime(new Date().getTime());
				ze.setMethod(ZipEntry.DEFLATED);
				zos.putNextEntry(ze);
				zos.write(b, 0, b.length);
				zos.closeEntry();
				zos.close();
				System.out.println(LLMessages
						.outputWritten(name, compiledExtension));
			}
			catch (IOException e2)
			{
				System.out.println(e2);
			}
		}
		catch (LLException e)
		{
			throw new LLException(LLMessages.aborted());
		}
	}

	////////////////////////////////////////////////////////////////////////////
	//	The constant expression evaluator

	private static LLCompiler evCompiler;
	private static String evToken;
	private static String item;
	private static int tokenPtr;

	public static boolean isNumeric(String s)
	{
		if (Character.isDigit(s.charAt(0))) return true;
		if (s.length() > 1) { return (s.charAt(0) == '-' && Character.isDigit(s
				.charAt(1))); }
		return false;
	}

	private static void getItem()
	{
		item = new String();
		int n = 0;
		while (tokenPtr < evToken.length())
		{
			char c = evToken.charAt(tokenPtr);
			switch (c)
			{
				case '-':
					if (n > 0) return;
					item += c;
					tokenPtr++;
					break;
				case '+':
				case '*':
				case '/':
				case 0:
					return;
				default:
					item += c;
					tokenPtr++;
					break;
			}
			n++;
		}
	}

	private static long nextItem() throws LLException
	{
		long value = 0;
		getItem();
		if (isNumeric(item)) value = Long.valueOf(item).longValue();
		else
		{
			int offset = 0;
			if (item.charAt(0) == '-') offset = 1;
			String s = item.substring(offset);
			if (!evCompiler.symbols.containsKey(s)) throw new LLException(
					LLMessages.undefinedSymbol(item));
			LHHandler symbol = (LHHandler) evCompiler.symbols.get(s);
			if (!(symbol instanceof LHConstant)) throw new LLException(LLMessages
					.constantExpected(item));
			LHConstant c = (LHConstant) symbol;
			if (!c.isNumeric()) throw new LLException(LLMessages
					.constantNotNumeric(item));
			value = ((Long) ((LHConstant) symbol).getValue()).longValue();
			if (offset != 0) value = -value;
		}
		return value;
	}

	private static long multiplyDivide() throws LLException
	{
		long value;
		value = nextItem();
		while (tokenPtr < evToken.length())
		{
			switch (evToken.charAt(tokenPtr))
			{
				case '*':
					tokenPtr++;
					value *= nextItem();
					break;
				case '/':
					tokenPtr++;
					value /= nextItem();
					break;
				default:
					return value;
			}
		}
		return value;
	}

	private static long eval(String s) throws LLException
	{
		evToken = new String(s);
		long value = multiplyDivide();
		while (tokenPtr < evToken.length())
		{
			switch (evToken.charAt(tokenPtr))
			{
				case '+':
					tokenPtr++;
					value += multiplyDivide();
					break;
				case '-':
					tokenPtr++;
					value -= multiplyDivide();
					break;
				default:
					return value;
			}
		}
		return value;
	}

	public long evaluate(String s) throws LLException
	{
		evCompiler = this;
		tokenPtr = 0;
		try
		{
			return eval(s);
		}
		catch (NumberFormatException e)
		{
			throw new LLException(LLMessages.constantNotNumeric(token));
		}
	}

	/****************************************************************************
	 * Handle system output.
	 */
	private static LHSystemOut systemOutput;

	public static void setSystemOut(LHSystemOut so)
	{
		systemOutput = so;
	}

	public static void systemOut(String message)
	{
		systemOutput.systemOut(message);
	}

	/****************************************************************************
	 * A class for keeping track of the position in a script.
	 */
	class LLMark
	{
		int line;
		int pos;
		String token;

		LLMark(int line, int pos, String token)
		{
			this.line = line;
			this.pos = pos;
			this.token = token;
		}
	}

	/****************************************************************************
	 * A class for maintaining information about an item in a collection.
	 */
	class LLClass
	{
		Class theClass;
		String type;
		String name;
		int elements;

		LLClass(Class c, String t, String n, int e)
		{
			theClass = c;
			type = t;
			name = n;
			elements = e;
		}
	}
}

