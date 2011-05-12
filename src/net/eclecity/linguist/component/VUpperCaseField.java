// VUpperCaseField.java

package net.eclecity.linguist.component;

/******************************************************************************
	VUpperCaseField - Extends VTextField to force all uppercase text

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	22/03/02  Created.
	</pre>
*/
public class VUpperCaseField extends VTextField
{
   /***************************************************************************
   	Standard constructor.
   	@param width the width of the field.
   */
	public VUpperCaseField(int width)
	{
   	super(width);
	}

   /***************************************************************************
   	Check a character that is about to be inserted into the field.
   	@param c the character to check
   	@return the character converted to uppercase.
   */
	protected Character checkCharacter(char c)
	{
		return new Character(Character.toUpperCase(c));
	}
}
