// LDBNoPersistentException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when an attempt is made to do transactions
	without first connecting to a database.
*/
public class LDBNoPersistentException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBNoPersistentException()
	{
		super("No persistent connection has been made.");
	}
}
