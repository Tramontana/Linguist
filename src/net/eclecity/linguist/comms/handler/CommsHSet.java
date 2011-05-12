// CommsHSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Set a port's attributes.
*/
public class CommsHSet extends LHHandler
{
	private CommsHPort port=null;
	private LVValue baudrate;
	private int databits;
	private int stopbits;
	private int parity;
	private boolean dtr;
	private boolean state;
	private boolean dtr_rts;

	public CommsHSet(int line,CommsHPort port,LVValue baudrate,int databits,int stopbits,int parity)
	{
		this.line=line;
		this.port=port;
		this.baudrate=baudrate;
		this.databits=databits;
		this.stopbits=stopbits;
		this.parity=parity;
	}

	public CommsHSet(int line,CommsHPort port,boolean dtr,boolean state)
	{
		this.line=line;
		this.port=port;
		this.dtr=dtr;
		this.state=state;
		dtr_rts=true;
	}

	public int execute() throws LRException
	{
		if (port!=null)
		{
			if (dtr_rts) port.set(dtr,state);
			else port.set(baudrate,databits,stopbits,parity);
		}
		return pc+1;
	}
}

