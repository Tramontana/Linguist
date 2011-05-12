// BasicHBuffer.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.FileReader;
import java.io.IOException;

import net.eclecity.linguist.handler.LHStringValue;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LULoadString;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A String variable.
	<pre>
	[1.001 GT]  16/12/02  Add the ability to read from a file.
	</pre>
*/
public class BasicHBuffer extends LHStringValue
{
	public Object newElement(Object extra) { return new String(""); }

	public void load(LVValue filename) throws LRException
	{
		setData(new LULoadString().load(filename.getStringValue()));
	}

	/***************************************************************************
		Set the value of the buffer.
	*/
	public void setValue(String value) throws LRException
	{
		setData(value);
	}

	/***************************************************************************
		Return the value from the buffer.
	*/
	public String getStringValue() throws LRException
	{
		String value=(String)getData();
		return (value==null)?"":value;
	}
	public String getStringValue(int n) throws LRException
	{
		String value=(String)getData(n);
		return (value==null)?"":value;
	}

	/***************************************************************************
		Clear all elements.
	*/
	public void clearAll()
	{
		try
		{
			for (int n=0; n<elements; n++) setData(n,"");
		}
		catch (LRException e) {}
	}

	/***************************************************************************
		Duplicate the buffer.
	*/
	public void duplicate(LHVariableHandler handler)
	{
		Object[] data=handler.getDataArray();
		String[] newData=new String[data.length];
		for (int n=0; n<data.length; n++) newData[n]=new String((String)data[n]);
		setDataArray(newData);
	}

	/***************************************************************************
		Read the buffer from a named file.
	*/
	public void readFrom(LVValue fileName) throws LRException
	{
		try
		{
			StringBuffer sb=new StringBuffer();
			FileReader fr=new FileReader(fileName.getStringValue());
			char[] buf=new char[1000];
			int count;
			while ((count=fr.read(buf,0,buf.length))>0) sb.append(buf,0,count);
			fr.close();
			setData(sb.toString());
		}
		catch (IOException e) { throw new LRException(e); }
	}

	/***************************************************************************
		Compare one string with another.  Used by the sorter.
	*/
	public int compare(Object o1,Object o2)
	{
		return ((String)o1).compareTo((String)o2);
	}
}

