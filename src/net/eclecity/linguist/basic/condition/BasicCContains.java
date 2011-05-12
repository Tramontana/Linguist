//	BasicCContains.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.basic.handler.BasicHHashtable;
import net.eclecity.linguist.basic.handler.BasicHVector;
import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Test if one string contains another or if a table contains a value.
	<pre>
	[1.000 GT] 25/09/01  Add test for vectors.
	</pre>
*/
public class BasicCContains extends LCCondition
{
	private BasicHHashtable hashtable;
	private BasicHVector vector;
	private LVValue value;			// the value to test
	private LVValue value2;			// the value to compare with
	private boolean sense;

	/***************************************************************************
		Test if one string contains another.
	*/
	public BasicCContains(LVValue value,LVValue value2,boolean sense)
	{
		this.value=value;
		this.value2=value2;
		this.sense=sense;
	}

	/***************************************************************************
		Test if a hashtable contains a value.
	*/
	public BasicCContains(BasicHHashtable hashtable,LVValue value,boolean sense)
	{
		this.hashtable=hashtable;
		this.value=value;
		this.sense=sense;
	}

	/***************************************************************************
		Test if a vector contains a value.
	*/
	public BasicCContains(BasicHVector vector,LVValue value,boolean sense)
	{
		this.vector=vector;
		this.value=value;
		this.sense=sense;
	}

	/***************************************************************************
		(Runtime)  Do the test now.
	*/
	public boolean test() throws LRException
	{
		if (hashtable!=null)
		{
			return hashtable.contains(value,sense);
		}
		else if (vector!=null)
		{
			return vector.contains(value,sense);
		}
		else
		{
			if (value.isNumeric()) return false;
			boolean result=(value.getStringValue().indexOf(value2.getStringValue())>=0);
			return sense?result:!result;
		}
	}
}
