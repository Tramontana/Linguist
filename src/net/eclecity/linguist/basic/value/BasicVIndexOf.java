//	BasicVIndexOf.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Extract an index.
	<pre>
	[1.000 GT] 25/09/01  Get the index of an item in a vector.
	</pre>
*/
public class BasicVIndexOf extends LVValue
{
	private BasicHVector vector=null;
	private LHStringHolder stringHolder=null;
	private LHVariableHandler handler=null;
	private LVValue value=null;

	/***************************************************************************
		Extract the index of a variable.
	*/
	public BasicVIndexOf(LHVariableHandler handler)
	{
		this.handler=handler;
	}

	/***************************************************************************
		Extract the index of a variable's value in a vector.
	*/
	public BasicVIndexOf(BasicHVector vector,LHStringHolder stringHolder)
	{
		this.vector=vector;
		this.stringHolder=stringHolder;
	}

	/***************************************************************************
		Extract the index of a value in a vector.
	*/
	public BasicVIndexOf(BasicHVector vector,LVValue value)
	{
		this.vector=vector;
		this.value=value;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public long getNumericValue() throws LRException
	{
		if (vector!=null)
		{
			if (stringHolder!=null) return vector.indexOf(stringHolder.getStringValue());
			return vector.indexOf(value.getStringValue());
		}
		return handler.getTheIndex();
	}

	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
