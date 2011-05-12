//	LVObject.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.value;


/******************************************************************************
	Hold an arbitrary object.
	<pre>
	[1.001 GT]  04/10/00  New class.
	</pre>
*/
public class LVObject extends LVValue
{
	Object value;

	public LVObject()
	{
		this(new Object());
	}

	public LVObject(Object value)
	{
		this.value=value;
		setNumeric(false);
	}

	public Object getBinaryValue()
	{
		return value;
	}
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
