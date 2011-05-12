// LRQueue.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.runtime;

/******************************************************************************
	Manage a queue of script addresses with optional attached data.
*/
public class LRQueue implements java.io.Serializable
{
	private int pc;				// the pc to go to
	private Object data;			// additional data if needed

	public LRQueue(int pc,Object data)
	{
		this.pc=pc;
		this.data=data;
	}

	public LRQueue(int pc)
	{
		this(pc,null);
	}

	public int getPC() { return pc; }
	public Object getData() { return data; }
}

