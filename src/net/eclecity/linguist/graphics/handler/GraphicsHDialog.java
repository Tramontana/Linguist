// GraphicsHDialog.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A dialog variable.
	<pre>
	[1.001 GT]  04/04/02  New class.
	</pre>
*/
public class GraphicsHDialog extends LHVariableHandler
{
	public Object newElement(Object extra) { return new Data(); }

	/***************************************************************************
		Create a dialog.
	*/
	public void create(int type,GraphicsHWindow window,LVValue title,LVValue text,
		Vector optionVector) throws LRException
	{
		Frame parent=(window==null)?null:window.getFrame();
		final Object[] options=optionVector.toArray();
		Data myData=(Data)getData();
		switch (type)
		{
			case CONFIRM:
				myData.choice=JOptionPane.showOptionDialog(
					parent,
					new JLabel(text.getStringValue()),
					title.getStringValue(),
					0,
					JOptionPane.PLAIN_MESSAGE,
					(Icon)null,
					options,
					options[0]);
				break;
			case INPUT:
				JTextField userInput=new JTextField(20);
				Object[] items=new Object[2];
				items[0]=new JLabel(text.getStringValue());
				items[1]=userInput;
				myData.choice=JOptionPane.showOptionDialog(
					parent,
					items,
					title.getStringValue(),
					0,
					JOptionPane.PLAIN_MESSAGE,
					(Icon)null,
					options,
					options[0]);
				myData.value=userInput.getText();
				break;
			case INFO:
				final JDialog dialog=new JDialog(parent,title.getStringValue(),true);
				dialog.getContentPane().add(new JLabel(text.getStringValue()));
				dialog.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent evt)
					{
						dialog.dispose();
					}
				});
				dialog.pack();
				if (parent!=null) dialog.setLocation(parent.getX()+(parent.getWidth()-dialog.getWidth())/2,
					parent.getY()+(parent.getHeight()-dialog.getHeight())/2);
				new Thread(new Runnable()
				{
					public void run()
					{
						try { Thread.sleep(((LVValue)options[0]).getNumericValue()); }
						catch (InterruptedException e) {}
						catch (LRException e) {}
						dialog.dispose();
					}
				}).start();
				dialog.setVisible(true);
				break;
		}
	}

	/***************************************************************************
		Get a numeric value.
	*/
	public long getNumericValue(int code) throws LRException
	{
		Data myData=(Data)getData();
		switch (code)
		{
			case CHOICE:
				return myData.choice;
			case VALUE:
				try
				{
					return Long.parseLong(myData.value);
				}
				catch (NumberFormatException e) {}
		}
		return 0;
	}

	/***************************************************************************
		Get a string value.
	*/
	public String getStringValue(int code) throws LRException
	{
		Data myData=(Data)getData();
		switch (code)
		{
			case CHOICE:
				return ""+myData.choice;
			case VALUE:
				return myData.value;
		}
		return "";
	}

	/***************************************************************************
		Define the different dialog types.
	*/
	public static final int
		CONFIRM=0,
		INPUT=1,
		INFO=2;

	public static final int
		CHOICE=0,
		VALUE=1;

	/***************************************************************************
		A private class that holds data about a dialog.
	*/
	class Data extends LHData
	{
		int choice;
		String value;

		Data() {}
	}
}

