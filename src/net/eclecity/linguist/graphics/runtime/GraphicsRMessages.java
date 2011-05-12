//	GraphicsRMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.runtime;

public class GraphicsRMessages
{
	public static String notComponent()
	{
		return "Item is not a component.";
	}

	public static String notContainer(String name)
	{
		return "Component '"+name+"' is not a container.";
	}

	public static String notJFrame(String name)
	{
		return "Component '"+name+"' is not a JFrame.";
	}

	public static String noParent(String name)
	{
		return "No parent given for '"+name+"'.";
	}

	public static String nullComponent(String name)
	{
		return "Component '"+name+"' has not been created.";
	}

	public static String doubleValue()
	{
		return "This is not a single value.";
	}

	public static String notRadioButton()
	{
		return "The component being added to the group is not a radio button.";
	}

	public static String cantSaveImage()
	{
		return "I can't save the label image.";
	}
}
