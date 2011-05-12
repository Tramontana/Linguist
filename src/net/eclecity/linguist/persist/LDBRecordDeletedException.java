// LDBRecordDeletedException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made for a record
	that has been deleted.
*/
public class LDBRecordDeletedException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBRecordDeletedException()
	{
		super("Record has been deleted.");
	}
}
