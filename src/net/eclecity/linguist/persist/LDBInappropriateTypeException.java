// LDBInappropriateTypeException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to a field that has no name.
*/
public class LDBInappropriateTypeException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBInappropriateTypeException(String name)
	{
		super("Inappropriate use of field '"+name+"'.");
	}
}
