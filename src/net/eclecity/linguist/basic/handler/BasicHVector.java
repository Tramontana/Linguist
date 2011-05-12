// BasicHVector.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import net.eclecity.linguist.basic.runtime.BasicRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.util.LUEncrypt;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A vector variable.
	<pre>
	[1.000 GT] 25/09/01  New class.
	</pre>
*/
public class BasicHVector extends LHVariableHandler
{
	public Object newElement(Object extra) { return new Data(this); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Add a value to the table.
	*/
	public void add(LVValue value) throws LRException
	{
		Data myData=(Data)getData();
		myData.table.addElement(value.getStringValue());
	}

	/***************************************************************************
		Remove an item from the table.
		If the parameter is null, remove all items.
	*/
	public void remove(LVValue value) throws LRException
	{
		Vector table=((Data)getData()).table;
		if (value==null) table=new Vector();
		else table.removeElement(value.getStringValue());
	}

	/***************************************************************************
		Encrypt the table.
	*/
	public void encrypt(LHStringHolder string,LVValue key) throws LRException
	{
		string.setValue(LUEncrypt.encrypt((Serializable)getData(),key.getStringValue()));
	}

	/***************************************************************************
		Decrypt the table.
	*/
	public void decrypt(LHStringHolder string,LVValue key) throws LRException
	{
		setData(LUEncrypt.decrypt(string.getStringValue(),key.getStringValue()));
	}

	/***************************************************************************
		Save the table to a disk file.
	*/
	public void save(LVValue fileName) throws LRException
	{
		try
		{
			FileOutputStream fos=new FileOutputStream(fileName.getStringValue());
			ObjectOutputStream out=new ObjectOutputStream(fos);
			out.writeObject(((Data)getData()).table);
			out.flush();
			out.close();
		}
		catch (IOException e) { e.printStackTrace(); }
	}

	/***************************************************************************
		Load the table from a disk file.
	*/
	public void load(LVValue fileName) throws LRException
	{
		try
		{
			FileInputStream fis=new FileInputStream(fileName.getStringValue());
			ObjectInputStream in=new ObjectInputStream(fis);
			((Data)getData()).table=(Vector)in.readObject();
			in.close();
		}
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}

	/***************************************************************************
		Report if the table is empty.
	*/
	public boolean isEmpty() throws LRException
	{
		return (((Data)getData()).table.size()==0);
	}

	/***************************************************************************
		Clear the table.
	*/
	public void clear() throws LRException
	{
		((Data)getData()).table=new Vector();
	}

	/***************************************************************************
		Reset the table.
	*/
	public void reset() throws LRException
	{
		Data myData=(Data)getData();
		myData.enumeration=myData.table.elements();
	}

	/***************************************************************************
		Report if the table enumeration has more data.
	*/
	public boolean more() throws LRException
	{
		Data myData=(Data)getData();
		return myData.enumeration.hasMoreElements();
	}

	/***************************************************************************
		Get the next data item from the table.
	*/
	public String getNextItem() throws LRException
	{
		Data myData=(Data)getData();
		try
		{
			return (String)myData.enumeration.nextElement();
		}
		catch (NoSuchElementException e) {}
		catch (ClassCastException e) {}
		throw new LRException(BasicRMessages.noDataAvailable(getName()));
	}

	/***************************************************************************
		Report if the table contains a particular item.
	*/
	public boolean contains(LVValue value,boolean sense) throws LRException
	{
		String s=value.getStringValue();
		Data myData=(Data)getData();
		if (sense) return myData.table.contains(s);
		return !myData.table.contains(s);
	}

	/***************************************************************************
		Get the index of a value in the table.
	*/
	public int indexOf(String value) throws LRException
	{
		Data myData=(Data)getData();
		return myData.table.indexOf(value);
	}

	/***************************************************************************
		A private class that holds data about a vector.
	*/
	class Data extends LHData
	{
		Vector table=new Vector();

		transient BasicHVector owner;
		transient Enumeration enumeration=null;

		Data(BasicHVector owner)
		{
			this.owner=owner;
		}
	}
}
