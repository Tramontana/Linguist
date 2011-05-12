//	NetworkKUpload.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.keyword;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.network.handler.NetworkHUpload;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	upload {filename} to {ip address}
*/
public class NetworkKUpload extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		LVValue fileName=null;
		try { fileName=getNextValue(); }
		catch (LLException e) { return null; }
		skip("to");
	   return new NetworkHUpload(line,fileName,getValue());
	}
}

