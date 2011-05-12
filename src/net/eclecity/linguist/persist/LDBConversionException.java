// LDBConversionException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when the system fails to convert a field value.
*/
public class LDBConversionException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBConversionException(String fieldName)
	{
		super("Conversion error in field '"+fieldName+"'.");
	}
}
