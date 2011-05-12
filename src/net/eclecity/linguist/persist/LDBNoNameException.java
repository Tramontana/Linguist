// LDBNoNameException.java

package net.eclecity.linguist.persist;

/******************************************************************************
	An exception thrown when a request is made to a field that has no name.
*/
public class LDBNoNameException extends LDBException
{
	/***************************************************************************
		Construct the exception.
	*/
	public LDBNoNameException()
	{
		super("Field has no name.");
	}
}
