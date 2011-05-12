// BasicHOffer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;

/******************************************************************************
	Offer a hashtable to the network.
	<pre>
	[1.001 GT]  01/04/03  New class.
	</pre>
*/
public class BasicHOffer extends LHHandler
{
	private BasicHHashtable table;

	public BasicHOffer(int line,BasicHHashtable table)
	{
		super(line);
		this.table=table;
	}

	public int execute()
	{
		if (table!=null) table.offer();
		return pc+1;
	}
}

