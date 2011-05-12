// ServletHUpload.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;

/******************************************************************************
	Upload a file.
*/
public class ServletHUpload extends LHHandler
{
	private ServletHUploader uploader;

	public ServletHUpload(int line,ServletHUploader uploader)
	{
		this.line=line;
		this.uploader=uploader;
	}

	/***************************************************************************
		(Runtime)   Do it now.
	*/
	public int execute() throws LRException
	{
		uploader.upload();
		return pc+1;
	}
}

