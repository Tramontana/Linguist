//	MediaVSize.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.media.value;

import java.awt.Dimension;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVConstant;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Hold a size.
*/
public class MediaVSize extends LVValue
{
	LVValue width;
	LVValue height;
	
	public MediaVSize() {}

	public MediaVSize(LVValue width,LVValue height)
	{
		this.width=width;
		this.height=height;
	}

	public MediaVSize(int width,int height)
	{
		this.width=new LVConstant(width);
		this.height=new LVConstant(height);
	}

	public int getWidth() throws LRException
	{
		return width.getIntegerValue();
	}
	
	public int getHeight() throws LRException
	{
		return height.getIntegerValue();
	}
	
	public Dimension getDimension() throws LRException
	{
		return new Dimension(width.getIntegerValue(),height.getIntegerValue());
	}
	
	public long getNumericValue() { return 0; }
	public String getStringValue() { return ""; }
}
