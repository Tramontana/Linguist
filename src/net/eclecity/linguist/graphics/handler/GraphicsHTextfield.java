// GraphicsHTextfield.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Text field handler.
*/
public class GraphicsHTextfield extends GraphicsHTextControl
{
	public GraphicsHTextfield() {}

	public void create(GraphicsHComponent container,LVValue text,GraphicsVLocation location,
		GraphicsVSize size,boolean editable) throws LRException
	{
		VTextField textField=new VTextField(text.getStringValue());
		create(container,textField,location,size,editable);
	}
	
	/***************************************************************************
		Set the mode of this text field.
	*/
	public void setMode(int mode)
	{
		((VTextField)getComponent()).setMode(mode);
	}
	
	/***************************************************************************
		Set the number of columns of this text field.
	*/
	public void setColumns(LVValue columns) throws LRException
	{
		((VTextField)getComponent()).setNColumns(columns.getIntegerValue());
	}
	
	public static final int
		TEXT=0,
		INTEGER=1;
	
	/***************************************************************************
		This inner class enables us to validate input as it's typed into a text field.
	*/
	class VTextField extends JTextField
	{
		private int mode;
		private int columns;

		VTextField(String text)
		{
			super();
			mode=TEXT;
			columns=25;
			this.setColumns(columns);
			setText(text);
		}
		
	   /***************************************************************************
	   	Set the mode of this field.
	   	This determines how typed characters will be interpreted.
	   */
		void setMode(int mode)
		{
			this.mode=mode;
		}

	   /***************************************************************************
	   	Set the number of columns of this field.
	   */
		void setNColumns(int columns)
		{
			this.columns=columns;
			super.setColumns(columns);
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
					if (getLength()>=columns) return;
					char[] chars=str.toCharArray();
					for (int n=0; n<chars.length; n++)
					{
						Character c=checkCharacter(chars[n]);
						if (c==null) return;
						chars[n]=c.charValue();
					}
					super.insertString(offs,new String(chars),a);
				}
			};
		}
	
	   /***************************************************************************
	   	Check a character that is about to be inserted into the field. 
	   	@param c the character to check
	   	@return the checked char as an object, or null if the character was invalid.
	   */
		protected Character checkCharacter(char c)
		{
			switch (mode)
			{
				case TEXT:
					return new Character(c);
				case INTEGER:
					if (Character.isDigit(c)) return new Character(c);
					break;
			}
			return null;
		}
	}
}
