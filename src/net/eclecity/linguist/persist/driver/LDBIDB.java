// LDBIDB.java

package net.eclecity.linguist.persist.driver;

import net.eclecity.linguist.persist.LDBException;
import net.eclecity.linguist.persist.LDBSQL;

/******************************************************************************
	A driver for an 'InstantDB' SQL database.
*/
public class LDBIDB extends LDBSQL
{
	/***************************************************************************
		Create an SQL driver object for IDB.
		This constructor must be present as the class is instantiated by name.
	*/
	public LDBIDB() {}
	
	/***************************************************************************
		Connect to the database.
		@param name the root name of the properties file
	*/
	public void connect(String name) throws LDBException
	{
		connect("org.enhydra.instantdb.jdbc.idbDriver","jdbc:idb:"+name+".prp");
	}
}
