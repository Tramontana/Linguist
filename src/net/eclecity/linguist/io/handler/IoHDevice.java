// IoHDevice.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.io.handler;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	A device variable.
*/
public class IoHDevice extends LHVariableHandler
{
	static
	{
		try
		{
			Runtime.getRuntime().loadLibrary("net.eclecity.linguist/lib/IO");
			System.out.println("IO library loaded");
		}
		catch (Exception e) { System.out.println(e.toString()); }
		catch (UnsatisfiedLinkError e) { System.out.println(e.toString()); }
	}

	public IoHDevice() {}

	public Object newElement(Object extra) { return new IoHDeviceData(); }
	
	/***************************************************************************
		Open a device.
	*/
	public void open(LVValue name) throws LRException
	{
		IoHDeviceData myData=(IoHDeviceData)getData();
		String deviceName=name.getStringValue();
		myData.device=initDevice(deviceName);
		System.out.println("Address of "+deviceName+" is "+myData.device);
	}
	
	/***************************************************************************
		Set a port on a device.
	*/
	public void set(LVValue port,LVValue value) throws LRException
	{
		IoHDeviceData myData=(IoHDeviceData)getData();
		setPort(myData.device,port.getIntegerValue(),value.getIntegerValue());
	}
	
	/***************************************************************************
		Read a port on a device.
	*/
	public void read(LVValue port) throws LRException
	{
		IoHDeviceData myData=(IoHDeviceData)getData();
		getPort(myData.device,port.getIntegerValue());
	}
	
	private native int initDevice(String name);
	private native void setPort(int device,int address,int value);
	private native int getPort(int device,int address);

	/***************************************************************************
		A private class that holds data about a device
	*/
	class IoHDeviceData extends LHData
	{
		int device=0;
		
		IoHDeviceData() {}
	}
}

