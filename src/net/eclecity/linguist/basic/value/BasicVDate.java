//	BasicVDate.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUUtil;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Convert a value to a date string.<p>
	Converts from string to long or vice versa.
	<p><pre>
	[1.001 GT]  15/07/00  Pre-existing.
	</pre>
*/
public class BasicVDate extends LVValue
{
	private LVValue value;				// the value to convert

	/***************************************************************************
		Create a BasicVDate from the value given.
	*/
	public BasicVDate(LVValue value)
	{
		this.value=value;
	}

	/***************************************************************************
		If the value is numeric, return it as is.
		Otherwise, assume it to be in the form dd/mm/yyyy, convert it
		to a timestamp and return that.
	*/
	public long getNumericValue() throws LRException
	{
		if (value.isNumeric()) return value.getNumericValue();
		// else parse to a timestamp
		// format is assumed to be dd/mm/yyyy
		String s=value.getStringValue();
		int date=LUUtil.getInt(s.substring(0,2),
			new GregorianCalendar().get(Calendar.DATE));
		int month=LUUtil.getInt(s.substring(3,5),
			new GregorianCalendar().get(Calendar.MONTH));
		int year=LUUtil.getInt(s.substring(6),
			new GregorianCalendar().get(Calendar.YEAR));
		return new GregorianCalendar(year,month,date)
			.getTime().getTime();
	}

	/***************************************************************************
		If the value is a string, return it as is.
		Otherwise, return it as a standard formatted date value.
	*/
	public String getStringValue() throws LRException
	{
		if (value.isNumeric())		// convert to a string
		{
			DateFormat df=DateFormat.getDateInstance(DateFormat.SHORT);
			return df.format(new Date(value.getNumericValue()));
		}
		// else simply return as is
		return value.getStringValue();
	}
}
