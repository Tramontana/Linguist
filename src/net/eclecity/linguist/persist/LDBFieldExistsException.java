// LDBFieldExistsException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to create a field
	that already exists.
*/
public class LDBFieldExistsException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBFieldExistsException()
	{
		super("Field already exists.");
	}
}
