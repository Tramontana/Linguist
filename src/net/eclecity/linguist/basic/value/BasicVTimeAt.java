//	BasicVTimeAt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.util.Date;
import java.util.TimeZone;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get the current time at a timezone.
*/
public class BasicVTimeAt extends LVValue
{
	private LVValue zone;


	public BasicVTimeAt(LVValue zone)
	{
		this.zone=zone;
	}
	
	public long getNumericValue() throws LRException
	{
		TimeZone tz=TimeZone.getTimeZone(zone.getStringValue());
		return new Date().getTime()+tz.getRawOffset();
	}
	
	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
