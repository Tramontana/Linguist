// BasicHGet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import net.eclecity.linguist.basic.runtime.BasicRBackground;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


public class BasicHGet extends LHHandler implements FilenameFilter
{
	private BasicHBuffer buffer;
	private BasicHHashtable hashtable;
	private LHModule module;
	private LHVariableHandler variable;
	private LVValue title;
	private LVValue left;
	private LVValue top;
	private LVValue key;
	private LVValue timestamp;

	/***************************************************************************
		Get a file name and put it in a buffer.
	*/
	public BasicHGet(int line,BasicHBuffer buffer,LVValue title,LVValue left,LVValue top)
	{
		super(line);
		this.buffer=buffer;
		this.title=title;
		this.left=left;
		this.top=top;
	}

	/***************************************************************************
		Get a module given its timestamp.
	*/
	public BasicHGet(int line,LHModule module,LVValue timestamp)
	{
		super(line);
		this.module=module;
		this.timestamp=timestamp;
	}

	/***************************************************************************
		Get a hashtable from the network.
	*/
	private LVValue address;

	public BasicHGet(int line,BasicHHashtable hashtable,LVValue address)
	{
		super(line);
		this.hashtable=hashtable;
		this.address=address;
	}

	/***************************************************************************
		Get a variable from a hashtable.
	*/
	public BasicHGet(int line,BasicHHashtable hashtable,LHVariableHandler variable,LVValue key)
	{
		super(line);
		this.hashtable=hashtable;
		this.variable=variable;
		this.key=key;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (buffer!=null)
		{
			Frame f=new Frame();
			f.setLocation(left.getIntegerValue(),top.getIntegerValue());
			BasicRBackground bg=(BasicRBackground)getBackground("basic");
			FileDialog fd=new FileDialog(f,title.getStringValue(),FileDialog.LOAD);
			buffer.setValue("");
			fd.setDirectory(bg.getDirectory());
			fd.setFilenameFilter(this);
			fd.setVisible(true);
			if (fd.getDirectory()!=null && fd.getFile()!=null)
			{
				bg.setDirectory(fd.getDirectory());
				buffer.setValue(fd.getDirectory()+fd.getFile());
			}
		}
		else if (hashtable!=null)
		{
			if (address!=null) hashtable.get(address);
			else hashtable.get(key,variable);
		}
		else if (module!=null) module.setModule(LHModule.getModule(timestamp.getNumericValue()));
		return pc+1;
	}

	public boolean accept(File dir,String name)
	{
		name=name.toLowerCase();
		return true;
	}
}

