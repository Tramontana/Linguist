// VFormItem.java

package net.eclecity.linguist.component;

import java.awt.Color;

/******************************************************************************
	VFormItem.
	Provides a standard means to validate a form or other GUI element.
	<p>Each implementing class manages a particular type of data.
	Many provide a means to block inappropriate input at the point
	of typing, and offer the means to listen for validation failure.
	Validation tends to occur when the component loses the focus;
	having registered validation listeners allows the component to
	notify its parent program that the data is invalid.

	@author Graham Trott of TechModern Ltd.
	@guardian Graham Trott of TechModern Ltd.
	@deployment All
	@version Java 1.2

	Class Version -><pre>
	[5.1 GT]   04/11/00  Complete overhaul.
	[5.0 GT]   07/08/00  Created.
	</pre>
*/
public interface VFormItem
{
	/** The color used for normal text. */
	public static final Color GOOD=Color.black;
	/** The color used when content validation fails. */
	public static final Color BAD=new Color(192,0,0);

	/***************************************************************************
		Check if the content has changed.
		@return true if the content has changed.
	*/
	public boolean isChanged();
	/***************************************************************************
		Validate the content of this form item.
		@return true if the content is OK.
	*/
	public boolean validateContent();
	/***************************************************************************
		Set the context to use for validation.
		@param context a parameter needed to set the context for validation.
	*/
	public void setValidationContext(Object context);
}
