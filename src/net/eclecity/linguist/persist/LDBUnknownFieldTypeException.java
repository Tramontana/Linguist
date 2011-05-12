// LDBUnknownFieldTypeException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a reference is made to
	an unknown field type.
*/
public class LDBUnknownFieldTypeException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBUnknownFieldTypeException()
	{
		super("Unknown field type.");
	}
}
