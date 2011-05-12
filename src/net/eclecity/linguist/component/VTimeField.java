// VTimeField.java

package net.eclecity.linguist.component;

import java.awt.BorderLayout;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTextField;

/******************************************************************************
	VTimeField - A GUI component that holds two drop-down number fields
	with a colon between them.

	@author Graham Trott of TechModern Ltd.
	@version Java 1.2

	Class Version -><pre>
	05/04/02  Created.
	</pre>
*/
public class VTimeField extends VComboField
{
	private VIntegerField leftField;
	private VIntegerField rightField;

   /***************************************************************************
   	Default constructor.
   */
	public VTimeField()
	{
   	this(null,null);
   }

   /***************************************************************************
   	Constructor.
   */
	public VTimeField(Object left,Object right)
	{
   	super(new BorderLayout());
   	leftField=new VIntegerField(2);
   	rightField=new VIntegerField(2);
//   	leftField.setBorder(new CompoundBorder(leftField.getBorder(),new EmptyBorder(0,8,0,4)));
//   	rightField.setBorder(new CompoundBorder(rightField.getBorder(),new EmptyBorder(0,4,0,8)));
   	leftField.setHorizontalAlignment(JTextField.CENTER);
   	rightField.setHorizontalAlignment(JTextField.CENTER);
   	add(leftField,BorderLayout.WEST);
   	add(new JLabel(" : ",JLabel.CENTER),BorderLayout.CENTER);
   	add(rightField,BorderLayout.EAST);
   	leftField.addFocusListener(this);
   	rightField.addFocusListener(this);
   	leftField.setValidationContext(left);
   	rightField.setValidationContext(right);
   }

   /***************************************************************************
   	Set the time to two integer values.  No checking is done.
   	@param left the value to use for the left field.
   	@param right the value to use for the right field.
   */
	public void setTime(int left,int right)
	{
   	leftField.setText(((left<10)?"0":"")+left);
   	rightField.setText(((right<10)?"0":"")+right);
	}

   /***************************************************************************
   	Set the time to a given calendar value.
   	@param date the date to use.
   	@param hoursMode if true use hours:minutes, else use minutes:seconds.
   */
   public void setTime(Calendar date,boolean hoursMode)
   {
   	if (hoursMode) setTime(date.get(Calendar.HOUR_OF_DAY),date.get(Calendar.MINUTE));
   	else setTime(date.get(Calendar.MINUTE),date.get(Calendar.SECOND));
	}

   /***************************************************************************
   	Set the component to be editable or not.
   	@param editable true if the field may be edited.
   */
   public void setEditable(boolean editable)
   {
   	leftField.setEditable(editable);
   	rightField.setEditable(editable);
	}

   /***************************************************************************
   	Get the value of the left field.
   	@return the left field.
   */
	public int getLeftField()
	{
		return leftField.toInt(0);
	}

   /***************************************************************************
   	Get the value of the right field.
   	@return the right field.
   */
	public int getRightField()
	{
		return rightField.toInt(0);
	}

   /***************************************************************************
   	Validate this component.
   */
	public boolean validateContent()
	{
		if (!leftField.validateContent()) return false;
		return rightField.validateContent();
	}

   /***************************************************************************
   	Add a validation listener. This sets up both of the text fields
   	as listeners.
   	@param listener the class interested in receiving validation events.
   */
	public void addValidationListener(ValidationListener listener)
	{
   	leftField.addValidationListener(listener);
   	rightField.addValidationListener(listener);
	}
}
