// VComboField.java

package net.eclecity.linguist.component;

import java.awt.LayoutManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JPanel;

/******************************************************************************
	VComboField - A GUI component that holds other fields as a single entity.
	It handles FocusEvents on behalf of its member fields so that validation
	can be done properly (as long as the member fields implement VFormItem).

	@author Graham Trott of TechModern Ltd.
	@guardian Graham Trott of TechModern Ltd.
	@deployment All
	@version Java 1.2

	Class Version -><pre>
	[5.1 GT]   04/11/00  Complete overhaul.
	[5.0 GT]   18/10/00  Created.
	</pre>
*/
public abstract class VComboField extends JPanel implements VFormItem, FocusListener
{
	private Vector focusListeners;

   /***************************************************************************
   	Constructor.
   */
	public VComboField(LayoutManager manager)
	{
   	super(manager);
   	setOpaque(false);
   	focusListeners=new Vector();
   }

	/***************************************************************************
		Add a FocusListener.
	*/
	public void addFocusListener(FocusListener listener)
	{
		if (focusListeners!=null & listener instanceof JDialog)
			focusListeners.addElement(listener);
	}

	/***************************************************************************
		The FocusListener interface, which passes on messages
		on behalf of the enclosed fields.
		The FocusEvent is retargetted at the combination field.
	*/
	public void focusGained(FocusEvent fe) {}
	public void focusLost(FocusEvent fe)
	{
		if (focusListeners==null || fe.isTemporary()) return;
		Enumeration enumeration=focusListeners.elements();
		while (enumeration.hasMoreElements())
			((FocusListener)enumeration.nextElement()).focusLost(new FocusEvent(this,fe.getID()));
	}

	/***************************************************************************
		Check if the content has changed.
		@return true if the content has changed.
	*/
	public boolean isChanged() { return false; }

	/***************************************************************************
		Set the validation context.
	*/
	public void setValidationContext(Object context) {}

	/***************************************************************************
		Validate the content of this form item.
		@return true by default.
	*/
	public boolean validateContent() { return true; }

	/***************************************************************************
		Add a listener to catch validation events.  Empty method.
		@param listener a listener.
	*/
	public void addValidationListener(ValidationListener listener) {}
}
