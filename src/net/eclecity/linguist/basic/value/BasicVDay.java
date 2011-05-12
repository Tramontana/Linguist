//	BasicVDay.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get the current day number (0=Monday).
	<p><pre>
	[1.001 GT]  15/07/00  Pre-existing.
	</pre>
*/
public class BasicVDay extends LVValue
{
	/***************************************************************************
		Create a BasicVDay.
	*/
	public BasicVDay()
	{
	}
	
	/***************************************************************************
		Return the day of the week, where Monday is zero.
	*/
	public long getNumericValue()
	{
		int day=new GregorianCalendar().get(Calendar.DAY_OF_WEEK)-2;
		if (day<0) day+=7;
		return day;
	}
	
	/***************************************************************************
		Return the day number as a String.
	*/
	public String getStringValue()
	{
		return String.valueOf(getNumericValue());
	}
}
