// LDBField.java

package net.eclecity.linguist.persist;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************************
	A database field.  This class represents the field in such a way
	as to preserve independence of the type of database used to store
	the record containing the field.
*/
public class LDBField extends LDBObject
{
	private LDBFieldSpec fieldSpec;
	private Object value;
	private boolean changed;

	/***************************************************************************
		Default constructor.
	*/
	public LDBField()
	{
		super();
	}

	/***************************************************************************
		Construct this field.
		@param fieldSpec the specification for the field
		@param value the value object for the field
	*/
	public LDBField(LDBFieldSpec fieldSpec,Object value)
	{
		this.fieldSpec=fieldSpec;
		this.value=value;
	}

	/***************************************************************************
		Set the field specifier for this field.
		@param fieldSpec the specification for the field
	*/
	public void setFieldSpec(LDBFieldSpec fieldSpec) { this.fieldSpec=fieldSpec; }

	/***************************************************************************
		Duplicate this field.
	*/
	public LDBField duplicate()
	{
		LDBField field=new LDBField(fieldSpec,value);
		field.setChanged(changed);
		return field;
	}

	/***************************************************************************
		Set the appropriate field value as read from the database.
		If an error occurs, substitute a suitable default.
		@param rs the ResultSet
	*/
	public void setFieldValue(ResultSet rs) throws LDBException
	{
		LDBFieldSpec fieldSpec=getFieldSpec();
		String name=fieldSpec.getName();
		switch (fieldSpec.getType())
		{
		case LDBFieldSpec.BYTE:
			try { setValue(new Byte(rs.getByte(name))); }
			catch (SQLException e) { setValue(new Byte((byte)-1)); }
			break;
		case LDBFieldSpec.INT:
			try { setValue(new Integer(rs.getInt(name))); }
			catch (SQLException e) { setValue(new Integer(-1)); }
			break;
		case LDBFieldSpec.LONG:
			try { setValue(new Long(rs.getLong(name))); }
			catch (SQLException e) { setValue(new Long(-1)); }
			break;
		case LDBFieldSpec.CHAR:
			try { setValue(rs.getString(name)); }
			catch (SQLException e) { setValue("<Bad field: '"+name+"'>"); }
			break;
		case LDBFieldSpec.BINARY:
			/*
				// This next bit doesn't work on JDK1.3 for all data types
				try { setValue(rs.getObject(name)); }
				catch (SQLException e) { setValue("<Bad field: '"+name+"'>"); }
				// So do it this way instead.
			*/
			Object o=null;
			try { o=rs.getObject(name); }
			catch (SQLException se) { throw new LDBBadFieldValueException(o.toString(),name); }
			if (o instanceof byte[])		// don't try to store byte[] in the database!
			{
				// Deserialize the read data to the original Object
				try
				{
					ByteArrayInputStream bis=new ByteArrayInputStream((byte[])o);
					ObjectInputStream ois=new ObjectInputStream(bis);
					setValue(ois.readObject());
					ois.close();
				}
				catch (ClassNotFoundException e) { setValue(null); }
				catch (IOException e) { setValue(null); }
			}
			else setValue(o);
			break;
		}
		setChanged(false);
	}

	/***************************************************************************
		Set the value object for this field.
		@param value the value object for the field
	*/
	public void setValue(Object value)
	{
		this.value=value;
		changed=true;
	}
	/***************************************************************************
		Return the value object for this field.
	*/
	public Object getValue() { return value; }

	/***************************************************************************
		Mark the field as being changed.
	*/
	public void setChanged() { changed=true; }
	/***************************************************************************
		Mark the field's changed status according to the parameter.
		@param state the new status.
	*/
	public void setChanged(boolean state) { changed=state; }
	/***************************************************************************
		Return true if the field is changed, false if not.
	*/
	public boolean isChanged() { return changed; }

	/***************************************************************************
		Return the field specification for this field.
	*/
	public LDBFieldSpec getFieldSpec() { return fieldSpec; }
	/***************************************************************************
		Return the name of this field.
	*/
	public String getName() { return fieldSpec.getName(); }

	/***************************************************************************
		Return the value of this field as a string.
		The usual use is to create an SQL command to assign a value
		to a field.
		@param quote true if a CHAR field should be returned quoted
	*/
	public String getStringValue(boolean quote) throws LDBException
	{
		String s;
		switch (fieldSpec.getType())
		{
			case LDBFieldSpec.BYTE:
				if (value==null) return "0";
				return value.toString();
			case LDBFieldSpec.INT:
				if (value==null) return "0";
				return value.toString();
			case LDBFieldSpec.LONG:
				if (value==null) return "0";
				return value.toString();
			case LDBFieldSpec.CHAR:
				if (value==null) return "";
				s=(String)value;
				if (quote) s="'"+s+"'";
				return s;
			case LDBFieldSpec.BINARY:
				if (value instanceof String)
				{
					s=(String)value;
					if (quote) s="'"+s+"'";
					return s;
				}
		}
		throw new LDBNoFieldValueException(fieldSpec.getName());
	}
}
