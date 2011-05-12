// LDBNoRecordsException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a reference is made to
	an unknown field type.
*/
public class LDBNoRecordsException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBNoRecordsException()
	{
		super("No more records available.");
	}
}
