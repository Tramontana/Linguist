//	BasicVCalendar.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get a calendar value.<p>
	Returns the day, month, year etc, either at the current location,
	for a specific time value or for a given timezone.
	<p><pre>
	[1.001 GT]  15/07/00  Pre-existing.
	</pre>
*/
public class BasicVCalendar extends LVValue
{
	private LVValue timezone=null;
	private LVValue value=null;
	private int item;

	/***************************************************************************
		Create a BasicVCalendar, to compute the date/time component
		either of a time value or for a time zone.
		@param the time value or a standard zone string such as "Europe/London".
		@param the item identifier, for example Calendar.DATE or Calendar.MONTH.
		@flag true if value is a time value, false if it is a timezone string.
	*/
	public BasicVCalendar(LVValue value,int item,boolean flag)
	{
		if (flag) this.value=value;
		else this.timezone=value;
		this.item=item;
	}

	/***************************************************************************
		Create a BasicVCalendar, to compute the date/time component
		for the current time.
		@param the item identifier, for example Calendar.DATE or Calendar.MONTH.
	*/
	public BasicVCalendar(int item)
	{
		this.item=item;
	}

	/***************************************************************************
		Return the numeric value of the requested item.
		If the timezone is not null, compute a value based on the value given.
		Otherwise, if the value has been set extract the appropriate value from it.
		Finally, return the value for the current time.
	*/
	public long getNumericValue() throws LRException
	{
		if (timezone!=null)		// return the time at a timezone
			return new GregorianCalendar(
				TimeZone.getTimeZone(timezone.getStringValue())).get(item);
		if (value!=null)			// return the time of a value
		{
			Calendar calendar=new GregorianCalendar();
			calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
			calendar.setTime(new Date(value.getNumericValue()));
			return calendar.get(item);
		}
		return new GregorianCalendar().get(item);
	}

	/***************************************************************************
		Return the numeric value converted to a String.
		@return the string value of the date/time item.
	*/
	public String getStringValue() throws LRException
	{
		return String.valueOf(getNumericValue());
	}
}
