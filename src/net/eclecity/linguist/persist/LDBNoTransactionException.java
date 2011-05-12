// LDBNoTransactionException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when an action is requested
	without a transaction having been created.
*/
public class LDBNoTransactionException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBNoTransactionException()
	{
		super("No transaction defined.");
	}
}
