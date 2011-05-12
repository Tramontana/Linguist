// LDBBadFieldValueException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to create a field
	that already exists.
*/
public class LDBBadFieldValueException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBBadFieldValueException(String value,String fieldName)
	{
		super("Bad value '"+value+"' in field '"+fieldName+"'.");
	}
}
