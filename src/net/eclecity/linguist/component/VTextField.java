// VTextField.java

package net.eclecity.linguist.component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/******************************************************************************
	VTextField - Extends JTextField to provide a standard data entry
	field.  Also implements VFormItem to provide
	a means to validate the content of the field.  If this does not pass,
	the text is shown in a different colour, to draw attention to the error.

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	[5.3 GT]   29/01/01  Initialize 'valid' to true on creation.
	[5.2 GT]   23/01/01  Improved validation.
	[5.1 GT]   04/11/00  Complete overhaul.
	[5.0 GT]   28/09/00  Created.
	</pre>
*/
public class VTextField extends JTextField
	implements VFormItem, KeyListener, FocusListener
{
	protected boolean valid;
	protected boolean changed;

	private Vector validationListeners;
	private Object context;
	private int width;

   /***************************************************************************
   	Default constructor.  Use a width of 10 characters.
   */
	public VTextField()
	{
   	this(10);
	}
   /***************************************************************************
   	Standard constructor.
   	@width the width of the field
   */
	public VTextField(int width)
	{
   	super(width);
   	this.width=width;
   	changed=false;
   	valid=true;
   	addKeyListener(this);
   	addFocusListener(this);
	}

   /***************************************************************************
   	Create the default model to use with this component.
   	This method embodies an inner class that manages insertions into
   	the text component, obeying local rules.
   	@return the model.
   */
	protected Document createDefaultModel()
	{
		return new PlainDocument()
		{
			/*********************************************************************
				Insert a string into this document, obeying local rules.
			*/
			public void insertString(int offs,String str,AttributeSet a)
				throws BadLocationException
			{
				if (str==null) return;
				if (getLength()==width) return;
				char[] chars=str.toCharArray();
				for (int n=0; n<chars.length; n++)
				{
					Character c=checkCharacter(chars[n]);
					if (c==null) return;
					chars[n]=c.charValue();
				}
				str=new String(chars);
				if (!checkString(str)) return;
				super.insertString(offs,str,a);
			}
		};
	}

   /***************************************************************************
   	Check a character that is about to be inserted into the field.
   	You can override this method to do special things like converting
   	to upper or lower case.
   	@param c the character to check
   	@return the checked char as an object, or null if the character was invalid.
   */
	protected Character checkCharacter(char c)
	{
		return new Character(c);
	}

   /***************************************************************************
   	Check the string that is about to be inserted into the field.
   	You can override this method, which is called after checkCharacter()
   	Yhas validated the characters individually.
   	@param s the string to check.
   	@return true if the string was valid.
   */
	protected boolean checkString(String s)
	{
		return true;
	}

   /***************************************************************************
   	Tell the caller if the contents have changed.
   	@return true if the content has changed.
   */
	public boolean isChanged() { return changed; }

   /***************************************************************************
   	Manage the focus.
   	@return true if the component can accept the focus.
   */
	public boolean isFocusable() { return isEditable(); }

   /***************************************************************************
   	Convert the content to an integer.
   	@param defValue the default if an error occurs.
   	@return the converted value.
   */
	public int toInt(int defValue)
	{
		try { return Integer.parseInt(getText()); }
		catch (NumberFormatException e) { return defValue; }
	}

   /***************************************************************************
   	Add a validation listener.
   */
	public void addValidationListener(ValidationListener listener)
	{
   	if (validationListeners==null) validationListeners=new Vector();
   	validationListeners.addElement(listener);
	}

	/***************************************************************************
		Set the context to use for validation.
		@param context a parameter needed to set the context for validation.
	*/
	public void setValidationContext(Object context) { this.context=context; }

   /***************************************************************************
   	Validate the field.
   	@return true if the content is OK.
   */
	public boolean validateContent()
	{
   	return validateContent(context);
	}

   /***************************************************************************
   	Validate the field.
   	@param context an object that governs how validation is to be done.
   	In many cases, making it non-null (any non-null object)
   	indicates that the field may be left blank.
   	@return true if the content is OK.
   */
	public boolean validateContent(Object context)
	{
		if (!changed) return valid;		// no need to do it again
		changed=false;
		valid=doTheValidation(context);
   	if (!valid)
   	{
   		setForeground(BAD);
   		validationFailed();
   	}
   	return valid;
	}

	/***************************************************************************
		Internal method to do the actual validation, overridden by derived classes.
		@param mayBeBlank if not null, the field may be blank.
		@return true if the field is OK.
	*/
	protected boolean doTheValidation(Object mayBeBlank)
	{
		return true;
	}

   /***************************************************************************
   	Report a validation failure.
   */
   protected void validationFailed()
   {
   	setForeground(BAD);
   	if (validationListeners!=null)
   	{
   		Enumeration enumeration=validationListeners.elements();
   		while (enumeration.hasMoreElements())
   		{
   			((ValidationListener)enumeration.nextElement()).validationFailed(this);
   		}
   	}
   }

	/***************************************************************************
		The KeyListener interface.
		When any key is pressed, restore the default (GOOD) text colour.
	*/
	public void keyPressed(KeyEvent ke)
	{
		if (!valid)
		{
			setForeground(GOOD);
			valid=true;
		}
		changed=true;
	}
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}

	/***************************************************************************
		The FocusListener interface.
		When the focus is lost, if the contents have changed do a validation.
	*/
	public void focusGained(FocusEvent fe) {}
	public void focusLost(FocusEvent fe)
	{
		if (changed)
		{
			validateContent();
			if (!valid) ((JTextField)fe.getSource()).requestFocus();
		}
	}
}
