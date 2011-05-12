// BasicHString.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import net.eclecity.linguist.basic.BasicLMessages;
import net.eclecity.linguist.handler.LHConstant;
import net.eclecity.linguist.main.LLException;

/*******************************************************************************
 * A String constant.
 */
public class BasicHString extends LHConstant
{
	public BasicHString(int line, String name, String source, boolean isFile,
			int pass) throws LLException
	{
		super(line, name, null, false);
		String data = "";
		if (pass == 2)
		{
			if (isFile)
			{
				File file = new File(source);
				if (file == null) { throw new LLException(BasicLMessages
						.fileNotFound(source)); }
				try
				{
					BufferedReader in = new BufferedReader(new FileReader(file));
					String s;
					while ((s = in.readLine()) != null)
					{
						data += s;
						data += '\n';
					}
					in.close();
				}
				catch (IOException e)
				{}
			}
			else data = source;
		}
		super.setValue(data);
	}

	public String getString()
	{
		return (String) getValue();
	}

	public int execute()
	{
		return pc + 1;
	}
}

