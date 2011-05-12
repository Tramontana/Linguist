//	BasicVDatime.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUUtil;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Get a date and time as a timestamp.<p>
	Converts from string to long or vice versa.
	<p><pre>
	[1.001 GT]  15/07/00  Pre-existing.
	</pre>
*/
public class BasicVDatime extends LVValue
{
	private LVValue value=null;		// the value to convert
	private LVValue format=null;

	/***************************************************************************
		Create a BasicVDatime using the current timestamp.
	*/
	public BasicVDatime()
	{
	}

	/***************************************************************************
		Create a BasicVDatime from the value given.
	*/
	public BasicVDatime(LVValue value)
	{
		this.value=value;
	}

	/***************************************************************************
		Create a BasicVDatime from the value given with a specfied format.
	*/
	public BasicVDatime(LVValue value,LVValue format)
	{
		this.value=value;
		this.format=format;
	}

	/***************************************************************************
		If the value is null, return the current timestamp.
		Otherwise, assume the value to be in the form "dd mm yyyy hh mm ss"
		(any delimiter is OK but only one character between items),
		convert it to a timestamp and return that.
	*/
	public long getNumericValue() throws LRException
	{
		if (value!=null)
		{
			int date=1;
			int month=1;
			int year=0;
			int hour=0;
			int minute=0;
			int second=0;
			// parse to a timestamp
			// format is assumed to be dd mm yyyy hh mm ss
			String[] ss=value.getStringValue().split(" ");
			try
			{
				date=LUUtil.getInt(ss[0]);
				month=LUUtil.getInt(ss[1]);
				year=LUUtil.getInt(ss[2]);
				hour=LUUtil.getInt(ss[3]);
				minute=LUUtil.getInt(ss[4]);
				second=LUUtil.getInt(ss[5]);
			}
			catch (ArrayIndexOutOfBoundsException e) {}
			Calendar calendar=new GregorianCalendar();
			calendar.set(year,month-1,date,hour,minute,second);
			return calendar.getTime().getTime();
		}
		return new Date().getTime();
	}

	/***************************************************************************
		If the value is null, return the current timestamp.
		If it's a number, convert it to a string using a standard format.
		If it's a string, simply return it as it is.
	*/
	public String getStringValue() throws LRException
	{
		if (value==null) value=new LVConstant(getNumericValue());
		if (value.isNumeric())		// convert to a string
		{
			String fmt="dd/MM/yyyy HH:mm";
			if (format!=null) fmt=format.getStringValue();
			return new SimpleDateFormat(fmt).format(new Date(value.getNumericValue()));
		}
		// else simply return as is
		return value.getStringValue();
	}
}
