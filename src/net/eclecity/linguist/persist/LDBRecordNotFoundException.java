// LDBRecordNotFoundException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made for a record
	that is not present in the database.
*/
public class LDBRecordNotFoundException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBRecordNotFoundException()
	{
		super("Record not found.");
	}
}
