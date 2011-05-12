//	NetworkCAddressActive.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.networker.Networker;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Test if an address is active.
*/
public class NetworkCAddressActive extends LCCondition
{
	private LVValue address;
	private boolean sense;

	public NetworkCAddressActive(LVValue address,boolean sense)
	{
		this.address=address;
		this.sense=sense;
	}

	public boolean test()
	{
		try
		{
			boolean retval=Networker.getInstance().isActive(address.getStringValue());
			return sense?retval:!retval;
		}
		catch (Exception e) {}
		return false;
	}
}
