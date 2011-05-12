// LDBNotifyListener.java

package net.eclecity.linguist.persist;

/******************************************************************************
	A listener that requires to be informed when changes
	are made to a record.
*/
public interface LDBNotifyListener
{
	/***************************************************************************
		Notify this listener of a change to the record.
		@param record the record doing the notification
		@param client the client that did the modification
	*/
	public void notify(LDBRecord record,LDBClient client) throws LDBException;
}
