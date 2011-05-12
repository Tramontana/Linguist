//	ServletKUpload.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.servlet.handler.ServletHUpload;
import net.eclecity.linguist.servlet.handler.ServletHUploader;

/******************************************************************************
	upload {uploader}
*/
public class ServletKUpload extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	   if (isSymbol())
	   {
	   	if (getHandler() instanceof ServletHUploader)
	   	{
		   	return new ServletHUpload(line,(ServletHUploader)getHandler());
	   	}
	   }
	   return null;
	}
}

