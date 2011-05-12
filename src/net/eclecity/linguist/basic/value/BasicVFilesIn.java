// BasicVFilesIn.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.io.File;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/*******************************************************************************
 * Get a list of the files in a directory.
 */
public class BasicVFilesIn extends LVValue
{
	private LVValue value;
	private boolean tree;
	private StringBuffer sb;

	public BasicVFilesIn(LVValue value, boolean tree)
	{
		this.value = value;
		this.tree = tree;
	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue() throws LRException
	{
		sb = new StringBuffer();
		File file = new File(value.getStringValue());
		if (tree) listFiles(file);
		else
		{
			String[] list = file.list();
			if (list != null)
			{
				for (int n = 0; n < list.length; n++)
					sb.append(list[n] + "\n");
			}
		}
		return sb.toString();
	}

	/****************************************************************************
	 * List a directory recursively.
	 */
	private void listFiles(File file)
	{
		System.out.println("List files in " + file.getAbsolutePath());
		File[] files = file.listFiles();
		if (files == null) return;
		for (int n = 0; n < files.length; n++)
		{
			if (files[n].isDirectory()) listFiles(files[n]);
			else sb.append(files[n].getAbsolutePath() + "\n");
		}
	}
}