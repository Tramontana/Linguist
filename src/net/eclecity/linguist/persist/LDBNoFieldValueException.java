// LDBNoFieldValueException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to get a value
	from a field whose type is unknown.
*/
public class LDBNoFieldValueException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBNoFieldValueException(String name)
	{
		super("Can't get a value for field '"+name+"'.");
	}
}
