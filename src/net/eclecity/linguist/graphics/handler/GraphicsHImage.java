// GraphicsHImage.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import net.eclecity.linguist.handler.LHConstant;


/******************************************************************************
	A constant image.
*/
public class GraphicsHImage extends LHConstant
{
	int width;
	int height;

	public GraphicsHImage() {}

	public GraphicsHImage(int line,String name,String source,int left,int top,
		int width,int height,boolean extract,int pass)
	{
		super(line,name,null);
		if (pass==2)
		{
			if (new File(source).exists())
			{
				if (extract)
				{
					ImageIcon icon=new ImageIcon(source);
					if (width==-1)
					{
						width=icon.getIconWidth()-left;
						height=icon.getIconHeight()-top;
					}
					this.width=width;
					this.height=height;
					int[] pixels=new int[width*height];
					try
					{
						new PixelGrabber(icon.getImage(),left,top,width,height,
							pixels,0,width).grabPixels();
						super.setValue(pixels);
					}
					catch (InterruptedException ignored) {}
				}
				else
				{
					try
					{
						InputStream in=new FileInputStream(source);
						int n=in.available();
						byte[] data=new byte[n];
						in.read(data,0,n);
						super.setValue(data);
					}
					catch (IOException e) {}
				}
			}
			else super.setValue(null);
		}
	}

	public ImageIcon getIcon()
	{
		ImageIcon icon;
		Object data=getValue();
		if (data instanceof byte[]) icon=new ImageIcon((byte[])data);
		else /* if (data instanceof int[]) */
		{
			icon=new ImageIcon(new Frame().getToolkit().createImage(
				new MemoryImageSource(width,height,ColorModel.getRGBdefault(),
				(int[])data,0,width)));
		}
		width=icon.getIconWidth();
		height=icon.getIconHeight();
		return icon;
	}

	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public Dimension getSize() { return new Dimension(width,height); }

	public int execute() { return pc+1; }
}

