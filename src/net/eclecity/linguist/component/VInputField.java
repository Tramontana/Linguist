// VInputField.java

package net.eclecity.linguist.component;

import java.util.Vector;

import javax.swing.JList;

/******************************************************************************
	VInputField - Extends VUpperCaseField to validate input choices

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	03/05/02  Created.
	</pre>
*/
public class VInputField extends VTextField
{
	private JList list;
	private Vector items;
	private boolean disable;

	public VInputField(JList list,Vector items)
	{
		super(3);
		this.list=list;
		this.items=items;
	}

   /***************************************************************************
   	Check a character that is about to be inserted into the field.
   	@param c the character to check
   	@return the checked char as an object, or null if the character was invalid.
  */
	protected Character checkCharacter(char c)
	{
		if (Character.isJavaIdentifierPart(c))
			return new Character(Character.toUpperCase(c));
		return null;
	}

   /***************************************************************************
   	Check a string that is about to be inserted into the field.
   	@param s the string to check
   	@return true if the string is valid.
   */
	protected boolean checkString(String s)
	{
		s=getText()+s;
		for (int n=0; n<items.size(); n++)
		{
			if (((String)items.get(n)).startsWith(s))
			{
				list.setSelectedIndex(n);
				list.ensureIndexIsVisible(n);
				return true;
			}
		}
		return false;
	}

	/***************************************************************************
		Do the actual validation.
		@param constraints as described in the class notes.
		@return true if the field is OK.
	*/
	protected boolean doTheValidation(Object constraints)
	{
		String s=getText();
		if (s.length()==0 || disable) return true;
		for (int n=0; n<items.size(); n++)
		{
			String name=(String)items.get(n);
			int index=name.indexOf(' ');
			if (index>0) name=name.substring(0,index);
			if (name.equals(s)) return true;
		}
   	return false;
	}

	/***************************************************************************
		Disable this field to prevent validation.
	*/
	public void setDisable(boolean disable) { this.disable=disable; }
}
