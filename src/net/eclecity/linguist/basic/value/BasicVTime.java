// BasicVTime.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUUtil;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * Get the current time or convert a time value.
 * <p>
 * Returns the current timestamp divided by the given scale factor, or the
 * timestamp corresponding to (today) hh:mm:ss.
 * <p>
 * 
 * <pre>
 * 
 *  [1.001 GT]  15/07/00  Pre-existing.
 *  
 * </pre>
 */
public class BasicVTime extends LVValue
{
	private LVValue value = null; // the value to convert
	private long divisor = 1;

	/****************************************************************************
	 * Create a BasicVTime representing the timestamp in milliseconds.
	 */
	public BasicVTime()
	{}

	/****************************************************************************
	 * Create a BasicVTime representing the timestamp divided by the given scale
	 * factor.
	 */
	public BasicVTime(long divisor)
	{
		this.divisor = divisor;
	}

	/****************************************************************************
	 * Create a BasicVTime representing the given String, assumed to be in the
	 * format "hh:mm:ss". Today is assumed.
	 */
	public BasicVTime(LVValue value)
	{
		this.value = value;
	}

	/****************************************************************************
	 * Return the time value. If a string had been given, convert it to today's
	 * date and the time it contains. If not, return the currrent time divided by
	 * the given scale factor (1 by default).
	 */
	public long getNumericValue() throws LRException
	{
		if (value != null)
		{
			if (value.isNumeric()) return value.getNumericValue();
			// else parse to a timestamp
			// format is assumed to be (today) hh:mm:ss
			String s = value.getStringValue();
			int hour = LUUtil.getInt(s.substring(0, 2), new GregorianCalendar()
					.get(Calendar.HOUR));
			int minute = LUUtil.getInt(s.substring(3, 5), new GregorianCalendar()
					.get(Calendar.MINUTE));
			int second = LUUtil.getInt(s.substring(6), new GregorianCalendar()
					.get(Calendar.SECOND));
			Calendar calendar = new GregorianCalendar();
			calendar.set(Calendar.HOUR, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			return calendar.getTime().getTime();
		}
		return new Date().getTime() / divisor;
	}

	/****************************************************************************
	 * Return the time value as a String. If the value was numeric, convert it to
	 * a standard date format; if a String, return it unchanged. If it was null,
	 * return the current date and time in standard format.
	 */
	public String getStringValue() throws LRException
	{
		if (value != null)
		{
			if (value.isNumeric()) // convert to a string
			{
				DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
				return df.format(new Date(value.getNumericValue()));
			}
			// else simply return as is
			return value.getStringValue();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss:SSS");
		return sdf.format(new Date());
	}
}