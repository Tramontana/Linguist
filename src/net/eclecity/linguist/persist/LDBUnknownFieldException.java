// LDBUnknownFieldException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a reference is made to
	an unknown field.
*/
public class LDBUnknownFieldException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBUnknownFieldException()
	{
		super("This field is not part of the record.");
	}
}
