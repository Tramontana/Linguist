//	BasicKDummy.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.keyword;

import net.eclecity.linguist.basic.handler.BasicHDummy;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;

/******************************************************************************
	dummy
*/
public class BasicKDummy extends LKHandler
{
	public LHHandler handleKeyword()
	{
      return new BasicHDummy(line);
	}
}

