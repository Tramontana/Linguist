//	MediaVLocation.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.value;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Hold a location.
*/
public class MediaVLocation extends LVValue
{
	LVValue left;
	LVValue top;
	
	public MediaVLocation () {}

	public MediaVLocation(LVValue left,LVValue top)
	{
		this.left=left;
		this.top=top;
	}

	public MediaVLocation(int left,int top)
	{
		this.left=new LVConstant(left);
		this.top=new LVConstant(top);
	}

	public int getLeft() throws LRException
	{
		return left.getIntegerValue();
	}
	
	public int getTop() throws LRException
	{
		return top.getIntegerValue();
	}
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
