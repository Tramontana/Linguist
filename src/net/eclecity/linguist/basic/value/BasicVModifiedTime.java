//	BasicVModifiedTime.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.io.File;

import net.eclecity.linguist.basic.handler.BasicHFile;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get the last modified time of a file.
*/
public class BasicVModifiedTime extends LVValue
{
	private BasicHFile file;
	private LVValue name;

	/***************************************************************************
		Get the last modified time of a file.
	*/
	public BasicVModifiedTime(BasicHFile file)
	{
		this.file=file;
	}

	/***************************************************************************
		Get the last modified time of a file.
	*/
	public BasicVModifiedTime(LVValue name)
	{
		this.name=name;
	}

	/***************************************************************************
		(Runtime)  Get the numeric value.
	*/
	public long getNumericValue() throws LRException
	{
		if (file!=null) return file.getModifyTime();
		else if (name!=null) return new File(name.getStringValue()).lastModified();
		return 0;
	}

	/***************************************************************************
		(Runtime)  Get the string value.
	*/
	public String getStringValue() throws LRException
	{
		return ""+getNumericValue();
	}
}
