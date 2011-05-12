// LDBFieldSpec.java

package net.eclecity.linguist.persist;

/******************************************************************************
	A field specifier.  This contains all the information needed
	to store the record containing the field, in whatever kind of
	database is required.
*/
public class LDBFieldSpec extends LDBObject
{
	private String name="default";
	private int type=INT;
	private int size=0;

	/***************************************************************************
		Construct a field specifier using a full specification.
		@param name the name (title) of the field
		@param type the field type (INT, CHAR ...)
		@param size the size of the field where relevant
	*/
	public LDBFieldSpec(String name,int type,int size)
	{
		this.name=name;
		this.type=type;
		this.size=size;
	}
	
	/***************************************************************************
		Construct a field specifier using partial data.
		The size value will be set to zero.
		@param name the name (title) of the field
		@param type the field type (INT, CHAR ...)
	*/
	public LDBFieldSpec(String name,int type)
	{
		this(name,type,0);
	}
	
	/** Set the name of the field. */
	public void setName(String name) { this.name=name; }
	/** Set the type of the field. */
	public void setType(int type) { this.type=type; }
	/** Set the size of the field. */
	public void setSize(int size) { this.size=size; }
	
	/** Return the name of the field. */
	public String getName() { return name; }
	/** Return the type of the field. */
	public int getType() { return type; }
	/** Return the size of the field. */
	public int getSize() { return size; }
	
	/** This field contains an integer value. */
	public static final int INT=0;
	/** This field contains a long integer value. */
	public static final int LONG=1;
	/** This field contains a character string. */
	public static final int CHAR=2;
	/** This field contains a byte value. */
	public static final int BYTE=3;
	/** This field contains a binary value of arbitrary size. */
	public static final int BINARY=4;
}
