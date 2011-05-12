// BasicHHashtable.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.eclecity.linguist.basic.runtime.BasicRMessages;
import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHHashtable;
import net.eclecity.linguist.handler.LHStringHolder;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.support.Base64;
import net.eclecity.linguist.util.LUUtil;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A hashtable variable.
	<pre>
	[1.000 GT] 20/10/00  Pre-existing.
	</pre>
*/
public class BasicHHashtable extends LHVariableHandler implements LHHashtable
{
	public Object newElement(Object extra) { return new Data(); }

	/***************************************************************************
		Tell callers this type doesn't return a value.
	*/
	public boolean hasValue() { return false; }

	/***************************************************************************
		Put a value into the table.
		Everything goes in as a String.
	*/
	public void put(LVValue key,LVValue value) throws LRException
	{
		Data myData=(Data)getData();
		Hashtable<String,String> table=myData.table;
		table.put(key.getStringValue(),value.getStringValue());
	}

	/***************************************************************************
		Get a variable from the table.
	*/
	public void get(LVValue key,LHVariableHandler variable) throws LRException
	{
		Hashtable table=((Data)getData()).table;
		String value=(String)table.get(key.getStringValue());
		Object[] array=variable.getDataArray();
		int index=variable.getTheIndex();
		if (array[0].getClass()==String.class)
		{
			try
			{
				array[index]=new String(value);
			}
			catch (NullPointerException e) { array[index]=""; }
		}
		else if (array[0].getClass()==Long.class)
		{
			try
			{
				array[index]=new Long(LUUtil.getLong(value));
			}
			catch (NullPointerException e) { e.printStackTrace(); array[index]=new Long(0); }
		}
		else throw new LRException(BasicRMessages.incompatibleType(variable.getName()));
	}

	/***************************************************************************
		Put a value into the table.
		Used by internal functions of extension packages.
	*/
	public void put(String key,String value) throws LRException
	{
		Data myData=(Data)getData();
		myData.table.put(key,value);
	}

	/***************************************************************************
		Get a value from the table.
		Used by internal functions of extension packages.
	*/
	public String get(String key) throws LRException
	{
		Data myData=(Data)getData();
		return myData.table.get(key);
	}

	/***************************************************************************
		Clear the table.
	*/
	public void clear() throws LRException
	{
		((Data)getData()).table=new Hashtable<String,String>();
	}

	/***************************************************************************
		Remove an item from the table.
		If the parameter is null, remove all items.
	*/
	public void remove(LVValue key) throws LRException
	{
		Hashtable table=((Data)getData()).table;
		if (key==null) table=new Hashtable();
		else table.remove(key.getStringValue());
	}

	/***************************************************************************
		Encode the hashtable.
	*/
	public void encode(LHStringHolder string)
	{
		try
		{
			string.setValue(Base64.encodeObject(encode()));
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantEncodeHashtable()); }
	}

	/***************************************************************************
		Encode the hashtable.
	*/
	private byte[] encode()
	{
		try
		{
			Object data=getData();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream out=new ObjectOutputStream(bos);
			out.writeObject(data);
			out.flush();
			byte[] b=bos.toByteArray();
			out.close();
			bos=new ByteArrayOutputStream();
			ZipOutputStream zos=new ZipOutputStream(bos);
			ZipEntry ze=new ZipEntry("hashtable");
			ze.setTime(new Date().getTime());
			ze.setMethod(ZipEntry.DEFLATED);
			zos.putNextEntry(ze);
			zos.write(b,0,b.length);
			zos.closeEntry();
			zos.close();
			return bos.toByteArray();
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantEncodeHashtable()); }
		return null;
	}

	/***************************************************************************
		Decode the hashtable.
	*/
	public void decode(LHStringHolder string)
	{
		try
		{
			String s=string.getStringValue();
			if (s.length()!=0)
			{
				byte[] bb=(byte[])Base64.decodeToObject(s);
				if (bb==null) bb=new byte[0];
				decode(bb);
			}
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantDecodeHashtable()); }
	}

	/***************************************************************************
		Decode the hashtable.
	*/
	public void decode(byte[] bb)
	{
		try
		{
			ByteArrayInputStream bis=new ByteArrayInputStream(bb);
			ZipInputStream zis=new ZipInputStream(bis);
			zis.getNextEntry();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			byte[] b=new byte[1024];
			while (true)
			{
				int n=zis.read(b,0,b.length);
				if (n<0) break;
				if (n>0) bos.write(b,0,n);
			}
			zis.close();
			Object data=null;
			bis=new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in=new ObjectInputStream(bis);
			try
			{
				data=in.readObject();
				in.close();
			}
			catch (ClassNotFoundException e) {}
			catch (OptionalDataException e) {}
			bos.close();
			setData(data);
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantDecodeHashtable()); }
	}

	/***************************************************************************
		Save the table to a disk file.
	*/
	public void save(LVValue fileName)
	{
		try
		{
			FileOutputStream fos=new FileOutputStream(fileName.getStringValue());
			ObjectOutputStream out=new ObjectOutputStream(fos);
			out.writeObject(((Data)getData()).table);
			out.flush();
			out.close();
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantSaveHashtable()); }
	}

	/***************************************************************************
		Load the table from a disk file.
	*/
	@SuppressWarnings("unchecked")
	public void load(LVValue fileName)
	{
		try
		{
			FileInputStream fis=new FileInputStream(fileName.getStringValue());
			ObjectInputStream in=new ObjectInputStream(fis);
			((Data)getData()).table=(Hashtable)in.readObject();
			in.close();
		}
		catch (Throwable e) { System.out.println(BasicRMessages.cantLoadHashtable()); }
	}

	/***************************************************************************
		Offer the table to the network.
	*/
	public void offer()
	{
		final byte[] data=encode();
		try
		{
			final ServerSocket serverSocket=new ServerSocket(17349);
			Thread thread=new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						Socket socket=serverSocket.accept();
						socket.setSoTimeout(5000);
						socket.setTcpNoDelay(true);
						InputStream is=new ByteArrayInputStream(data);
						OutputStream os=socket.getOutputStream();
						byte[] buf=new byte[1000];
						int count;
						while ((count=is.read(buf))>0) os.write(buf,0,count);
						socket.close();
					}
					catch (SocketException se) {}
					catch (IOException ioe) {}
				}
			});
			thread.setDaemon(true);
			thread.start();
		}
		catch (IOException e) {}
	}

	/***************************************************************************
		Get the table from a server socket on the network.
	*/
	public void get(LVValue address) throws LRException
	{
		try
		{
			Socket socket=new Socket(address.getStringValue(),17349);
			InputStream is=socket.getInputStream();
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			byte[] buf=new byte[1000];
			int count;
			while ((count=is.read(buf))>0) os.write(buf,0,count);
			decode(os.toByteArray());
			socket.close();
		}
		catch (UnknownHostException e) {}
		catch (IOException e) {}
	}

	/***************************************************************************
		Report if the table is empty.
	*/
	public boolean isEmpty() throws LRException
	{
		return (((Data)getData()).table.size()==0);
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
		Reset the table.
	*/
	public void reset() throws LRException
	{
		Data myData=(Data)getData();
		myData.enumeration=myData.table.keys();
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
		Get the next key from the table.
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
		Implementation of LHHashtable.  Return the table.
	*/
	public Hashtable getTable() throws LRException
	{
		Data myData=(Data)getData();
		return myData.table;
	}

	/***************************************************************************
		A private class that holds data about a table.
	*/
	class Data extends LHData
	{
		Hashtable<String,String> table=new Hashtable<String,String>();

		transient Enumeration enumeration=null;

		Data() {}
	}
}
