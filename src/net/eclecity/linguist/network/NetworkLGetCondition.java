// NetworkLGetCondition

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLGetCondition;
import net.eclecity.linguist.network.condition.NetworkCAddressActive;
import net.eclecity.linguist.network.condition.NetworkCHasData;
import net.eclecity.linguist.network.condition.NetworkCHasError;
import net.eclecity.linguist.network.condition.NetworkCHasServices;
import net.eclecity.linguist.network.condition.NetworkCNetError;
import net.eclecity.linguist.network.condition.NetworkCSenderIs;
import net.eclecity.linguist.network.handler.NetworkHClient;
import net.eclecity.linguist.network.handler.NetworkHMessage;
import net.eclecity.linguist.network.handler.NetworkHSocket;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	<pre>
	Generate	code for a condition:

	network error
	{socket} has data
	{socket} has error
	{client} has services
	{message} has error
	address {address} is [not] active
	the source/destination of {message} is {not} the sender
	<p>
	[1.001 GT]  17/02/01  Pre-existing.
	</pre>
*/
public class NetworkLGetCondition extends LLGetCondition
{
	public LCCondition getCondition(LLCompiler c) throws LLException
	{
		compiler=c;
		getNextToken();
		if (tokenIs("the")) getNextToken();
		if (tokenIs("network"))
		{
			getNextToken();
			if (tokenIs("error")) return new NetworkCNetError(getProgram());
			dontUnderstandToken();
		}
		if (tokenIs("source") || tokenIs("destination"))
		{
			boolean source=false;
			if (tokenIs("source")) source=true;
			skip("of");
			if (isSymbol())
			{
				if (getHandler() instanceof NetworkHMessage)
				{
					skip("is");
					boolean sense=true;
					if (tokenIs("not")) sense=false;
					else unGetToken();
					skip("the");
					if (tokenIs("sender"))
						return new NetworkCSenderIs((NetworkHMessage)getHandler(),source,sense);
				}
			}
			dontUnderstandToken();
		}
		if (tokenIs("address"))
		{
			LVValue address=getNextValue();
			skip("is");
			boolean sense=true;
			if (tokenIs("not"))
			{
				sense=false;
				getNextToken();
			}
			if (tokenIs("active"))
			{
				return new NetworkCAddressActive(address,sense);
			}
		}
		if (isSymbol())
		{
			if (getHandler() instanceof NetworkHSocket)
			{
				skip("has");
				if (tokenIs("data"))
				{
					return new NetworkCHasData((NetworkHSocket)getHandler());
				}
				if (tokenIs("error"))
				{
					return new NetworkCHasError((NetworkHSocket)getHandler());
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof NetworkHMessage)
			{
				getNextToken();
				if (tokenIs("has"))
				{
					getNextToken();
					if (tokenIs("error"))
					{
						return new NetworkCHasError((NetworkHMessage)getHandler());
					}
				}
				dontUnderstandToken();
			}
			if (getHandler() instanceof NetworkHClient)
			{
				skip("has");
				if (tokenIs("services"))
				{
					return new NetworkCHasServices((NetworkHClient)getHandler());
				}
				dontUnderstandToken();
			}
		}
		return null;
	}
}
