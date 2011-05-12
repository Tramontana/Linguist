//	NetworkVProperty.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.value;

import net.eclecity.linguist.network.handler.NetworkHRules;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Get information from a message.
*/
public class NetworkVProperty extends LVValue
{
	private NetworkHRules rules;
	private LVValue key;

	public NetworkVProperty(NetworkHRules rules,LVValue key)
	{
		this.rules=rules;
		this.key=key;
	}

	public long getNumericValue() throws LRException
	{
		return longValue();
	}

	public String getStringValue() throws LRException
	{
		return rules.getProperty(key);
	}
}
