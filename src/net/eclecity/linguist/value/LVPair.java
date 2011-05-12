//	LVPair.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;

import net.eclecity.linguist.runtime.LRException;

public class LVPair implements java.io.Serializable
{
	private LVValue first;
	private LVValue last;
	
	public LVPair()
	{
		first=new LVConstant(0);
		last=new LVConstant(0);
	}

	public LVPair(LVValue first,LVValue last)
	{
		this.first=first;
		this.last=last;
	}
	
	public LVPair(LVPair pair)
	{
		this.first=pair.getFirst();
		this.last=pair.getLast();
	}
	
	public LVValue getFirst() { return first; }
	public LVValue getLast() { return last; }
	
	public long getFirstValue() throws LRException { return first.getNumericValue(); }
	public long getLastValue() throws LRException { return last.getNumericValue(); }
}
