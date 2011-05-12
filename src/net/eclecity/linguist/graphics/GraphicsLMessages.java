//	GraphicsLMessages.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics;

/******************************************************************************
	Error messages.
	<pre>
	[1.001 GT]  30/10/00  Pre-existing.
	</pre>
*/
public class GraphicsLMessages
{
	public static String windowExpected(String name)
	{
		return "'"+name+"' is not a window variable.";
	}

	public static String labelExpected(String name)
	{
		return "'"+name+"' is not a label variable.";
	}

	public static String buttonExpected(String name)
	{
		return "'"+name+"' is not a button variable.";
	}

	public static String labelOrButtonExpected(String name)
	{
		return "'"+name+" is not a label or a button variable.";
	}

	public static String popupMenuExpected(String name)
	{
		return "'"+name+" is not a popup menu variable.";
	}

	public static String cursorExpected(String name)
	{
		return "'"+name+"' is not a cursor variable.";
	}

	public static String colorExpected(String name)
	{
		return "'"+name+"' is not a color variable.";
	}

	public static String fontExpected(String name)
	{
		return "'"+name+"' is not a font variable.";
	}

	public static String textComponentExpected(String name)
	{
		return "'"+name+"' is not a text component.";
	}

	public static String componentExpected(String name)
	{
		return "'"+name+"' is not a component.";
	}

	public static String containerExpected(String name)
	{
		return "'"+name+"' is not a container.";
	}

	public static String borderExpected(String name)
	{
		return "'"+name+"' is not a border.";
	}

	public static String styleExpected(String name)
	{
		return "'"+name+"' is not a style.";
	}

	public static String menuExpected(String name)
	{
		return "'"+name+"' is not a menu.";
	}

	public static String imageExpected(String name)
	{
		return "'"+name+"' is not an image.";
	}

	public static String badImage(String name)
	{
		return "I can't load the image '"+name+"'.";
	}

	public static String missingRow()
	{
		return "Row data missing.";
	}

	public static String missingColumn()
	{
		return "Column data missing.";
	}

	public static String unknownBorderStyle(String name)
	{
		return "Unknown border style definition '"+name+"'.";
	}

	public static String borderStyleRequired()
	{
		return "Border style definition missing.";
	}

	public static String panelExpected(String name)
	{
		return "'"+name+"' is not a panel.";
	}

	public static String dialogExpected(String name)
	{
		return "'"+name+"' is not a dialog.";
	}
}
