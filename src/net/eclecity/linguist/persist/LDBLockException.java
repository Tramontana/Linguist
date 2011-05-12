// LDBLockException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when an attempt is made to modify a record
	locked to another client.
*/
public class LDBLockException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBLockException()
	{
		super("Record is locked to another client.");
	}
}
