// GraphicsHLabel.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.eclecity.linguist.graphics.runtime.GraphicsRMessages;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/******************************************************************************
	Label handler.
	<pre>
	[1.001 GT]  19/10/00  Pre-existing.
	</pre>
*/
public class GraphicsHLabel extends GraphicsHSwingComponent implements GraphicsHTextComponent
{
	public GraphicsHLabel() {}

	public void create(GraphicsHComponent container,LVValue text,GraphicsVLocation location,GraphicsVSize size) throws LRException
	{
		JLabel label=new GraphicsHCLabel(text.getStringValue());
		create(container,label,location,size);
		if (container!=null) container.add(this);
	}

	public void create(GraphicsHComponent container,JComponent component,GraphicsVLocation location,GraphicsVSize size) throws LRException
	{
		if (location!=null) component.setLocation(location.getLocation());
		if (size!=null)
		{
			component.setSize(size.getWidth(),size.getHeight());
			getComponentData().sizeSpecified=true;
		}
		component.setBorder(null);
		create(container,component,false);
	}

	public void setText(LVValue text) throws LRException
	{
		if (text!=null)
		{
			JLabel label=(JLabel)getComponent();
			if (label!=null)
			{
				label.setText(text.getStringValue());
				setSize();
			}
		}
	}

	public String getText()
	{
		JLabel label=(JLabel)getComponent();
		if (label!=null) return label.getText();
		return "";
	}

	public void setIcon(GraphicsHImage image,boolean scaled) throws LRException
	{
		setIcon(image.getIcon(),scaled);
	}

	public void setIcon(LVValue name,boolean scaled) throws LRException
	{
		setIcon(new ImageIcon(name.getStringValue()),scaled);
	}

	private void setIcon(ImageIcon icon,boolean scaled) throws LRException
	{
		JLabel label=(JLabel)getComponent();
		if (label==null) throw new LRException(GraphicsRMessages.nullComponent(getName()));
		if (scaled)
		{
			int w=icon.getIconWidth();
			int h=icon.getIconHeight();
			int width=label.getWidth();
			int height=label.getHeight();
			// Sort out picture orientation
			double imageRatio=(double)w/(double)h;
			double iconRatio=(double)width/(double)height;
			if (imageRatio>iconRatio) height=(int)(width/imageRatio);
			else width=(int)(height*imageRatio);
			Image image=label.createImage(width,height);
			Graphics g=image.getGraphics();
			g.drawImage(icon.getImage(),0,0,width,height,label);
			g.dispose();
			label.setIcon(new ImageIcon(image));
		}
		else label.setIcon(icon);
		setSize();
	}

	/***************************************************************************
		Write the icon of this image as a JPEG.
	*/
	public void saveIcon(LVValue name) throws LRException
	{
		try
		{
			JLabel label=(JLabel)getComponent();
			ImageIcon icon=(ImageIcon)label.getIcon();
			BufferedImage bufferedImage=new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
			Graphics2D g2=bufferedImage.createGraphics();
			g2.drawImage(icon.getImage(),null,null);
			g2.dispose();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			JPEGEncodeParam jep=JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
			jep.setQuality(0.4f,false);
			JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(bos,jep);
			encoder.encode(bufferedImage);
			byte[] bb=bos.toByteArray();
			String s=name.getStringValue();
			File file=new File(s.substring(0,s.lastIndexOf('/')));
			file.mkdirs();
			FileOutputStream fos=new FileOutputStream(s);
			fos.write(bb);
			fos.close();
			bos.close();
		}
		catch (Exception e) { throw new LRException(GraphicsRMessages.cantSaveImage()); }
	}

	public void setHorizontalTextAlignment(int alignment)
	{
		((JLabel)getComponent()).setHorizontalAlignment(alignment);
	}

	public void setVerticalTextAlignment(int alignment)
	{
		((JLabel)getComponent()).setVerticalAlignment(alignment);
	}

	private void setSize() throws LRException
	{
		JLabel label=(JLabel)getComponent();
		setSize(label,label.getText(),label.getIcon(),
			label.getVerticalTextPosition(),label.getHorizontalTextPosition());
	}

	protected void setSize(JComponent component,String text,Icon icon,
		int vAlignment,int hAlignment) throws LRException
	{
		if (getComponentData().sizeSpecified) return;
		if (component==null) throw new LRException(LRException.notCreated(getName()));
		try { component.validate(); } catch (Exception ignored) {}		// it's needed - don't know why
		Insets insets=component.getInsets();
		int width=insets.left+insets.right;
		int height=insets.top+insets.bottom;
		Graphics g=component.getGraphics();
		int textWidth=0;
		int textHeight=0;
		if (g!=null)
		{
			FontMetrics fm=g.getFontMetrics(g.getFont());
			textWidth=fm.stringWidth(text);
			textHeight=fm.getHeight();
			g.dispose();
		}
		int iconWidth=0;
		int iconHeight=0;
		if (icon!=null)
		{
			iconWidth=icon.getIconWidth();
			iconHeight=icon.getIconHeight();
		}
		switch (vAlignment)
		{
		case SwingConstants.TOP:
			switch (hAlignment)
			{
			case SwingConstants.LEFT:
			case SwingConstants.RIGHT:
				width+=(textWidth+iconWidth);
				height+=Math.max(textHeight,iconHeight);
				break;
			case SwingConstants.CENTER:
				width+=Math.max(textWidth,iconWidth);
				height+=(textHeight+iconHeight);
				break;
			}
			break;
		case SwingConstants.CENTER:
			switch (hAlignment)
			{
			case SwingConstants.LEFT:
			case SwingConstants.RIGHT:
			case SwingConstants.LEADING:
			case SwingConstants.TRAILING:
				width+=(textWidth+iconWidth);
				height+=Math.max(textHeight,iconHeight);
				break;
			case SwingConstants.CENTER:
				width+=Math.max(textWidth,iconWidth);
				height+=Math.max(textHeight,iconHeight);
				break;
			}
			break;
		case SwingConstants.BOTTOM:
			switch (hAlignment)
			{
			case SwingConstants.LEFT:
			case SwingConstants.RIGHT:
				width+=(textWidth+iconWidth);
				height+=Math.max(textHeight,iconHeight);
				break;
			case SwingConstants.CENTER:
				width+=Math.max(textWidth,iconWidth);
				height+=(textHeight+iconHeight);
				break;
			}
			break;
		}
		component.setSize(width,height);
	}
}
