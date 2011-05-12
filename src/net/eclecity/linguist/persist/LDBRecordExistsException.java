// LDBRecordExistsException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when an attempt is made to insert a record
	that already exists in the database.
*/
public class LDBRecordExistsException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBRecordExistsException()
	{
		super("Record already exists.");
	}
}
