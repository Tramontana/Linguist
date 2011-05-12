//	BasicVNetworkAddress.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get the network address of this machine.
*/
public class BasicVNetworkAddress extends LVValue
{
	public BasicVNetworkAddress() {	}

	public long getNumericValue()
	{
		return 0;
	}

	public String getStringValue()
	{
		InetAddress ia=null;
		try { ia=InetAddress.getLocalHost(); }
		catch (UnknownHostException e) { return ""; }
		return ia.getHostAddress();
	}
}
