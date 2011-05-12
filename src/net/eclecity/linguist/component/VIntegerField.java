// VIntegerField.java

package net.eclecity.linguist.component;


/******************************************************************************
	VIntegerField - Extends VTextField to provide a standard data entry
	field for an integer.
	<p>For validation, the 'constraints' parameter can be one of the following:
	<br>An Integer representing the highest allowed value (minimum zero).
	<br>An int array where the first element is the minimum allowable value
	and the second is the maximum allowable value.

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	21/03/02  Improved validation: add Vector of allowable values.
	23/01/01  Improved validation.
	04/11/00  Complete overhaul.
	18/10/00  Change to extend VTextField.
	18/10/00  Created.
	</pre>
*/
public class VIntegerField extends VTextField
{
   /***************************************************************************
   	Standard constructor.
   	@param width the width of the field.
   */
	public VIntegerField(int width)
	{
   	super(width);
	}

	/***************************************************************************
		Get the numeric value in the field.
		@return the current value.
	*/
	public int getValue()
	{
		return Integer.parseInt(getText());
	}

	/***************************************************************************
		Do the actual validation.
		@param constraints as described in the class notes.
		@return true if the field is OK.
	*/
	protected boolean doTheValidation(Object constraints)
	{
		int value=toInt(0);
		if (constraints instanceof Integer)
		{
			if (value<0) return false;
			if (value>((Integer)constraints).intValue()) return false;
		}
		else if (constraints instanceof VRange)
		{
			VRange range=(VRange)constraints;
			if (value<range.lower || value>range.upper) return false;
		}
		else if (constraints instanceof int[])
		{
			int[] values=(int[])constraints;
			for (int n=0; n<values.length; n++) if (values[n]==value) return true;
			return false;
		}
   	return true;
	}

   /***************************************************************************
   	Check a character that is about to be inserted into the field.
   	@param c the character to check
   	@return the checked char as an object, or null if the character was invalid.
   */
	protected Character checkCharacter(char c)
	{
		if (Character.isDigit(c)) return new Character(c);
		return null;
	}
}
