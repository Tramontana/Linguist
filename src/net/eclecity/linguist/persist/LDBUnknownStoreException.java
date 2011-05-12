// LDBUnknownStoreException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to use
	an unknown type of persistent store.
*/
public class LDBUnknownStoreException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBUnknownStoreException()
	{
		super("Unknown type of persistent store.");
	}
}
