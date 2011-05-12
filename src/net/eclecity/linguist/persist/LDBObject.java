// LDBObject.java

package net.eclecity.linguist.persist;

/******************************************************************************
	A base class for the persistence library.
	This class contains useful static methods.
*/
public abstract class LDBObject implements java.io.Serializable
{
	/***************************************************************************
		Print a string to the console.
		@param s the string to print.
	*/
	public static void print(String s)
	{
		System.out.print(s);
	}
	
	/***************************************************************************
		Print a string followed by a newline to the console.
		@param s the string to print.
	*/
	public static void println(String s)
	{
		System.out.println(s);
	}
	
	/***************************************************************************
		Print a newline to the console.
	*/
	public static void println()
	{
		System.out.println();
	}
	
	/***************************************************************************
		Wait for a given number of milliseconds.
		@param n the number of milliseconds to wait.
	*/
	public static void wait(int n)
	{
		try { Thread.sleep(n); }
		catch (InterruptedException ignored) {}
	}
}
