// LHHashtable.java

package net.eclecity.linguist.handler;

import java.util.Hashtable;

import net.eclecity.linguist.runtime.LRException;


/******************************************************************************
	An interface to label a variable as a hashtable.
	<pre>
	[1.001 GT]  12/11/02  New class.
	</pre>
*/
public interface LHHashtable
{
	public Hashtable getTable() throws LRException;
}
