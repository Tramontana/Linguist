// GraphicsHCanvas.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.ImageIcon;

import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Canvas handler.
	<pre>
	[1.001 GT]  05/101/01  New class.
	</pre>
*/
public class GraphicsHCanvas extends GraphicsH2DComponent
{
	public GraphicsHCanvas() {}

	public void create(GraphicsHComponent container,GraphicsVLocation location,GraphicsVSize size) throws LRException
	{
		GraphicsHCCanvas canvas=new GraphicsHCCanvas();
		if (location!=null) canvas.setLocation(location.getLocation());
		if (size!=null) getComponentData().sizeSpecified=true;
		else size=new GraphicsVSize(50,20);
		canvas.setSize(size.getWidth(),size.getHeight());
		canvas.setBorder(null);
		create(container,canvas,false);
		container.getContentPane().add(canvas,0);
	}
	
	public void setIcon(GraphicsHImage image) throws LRException
	{
		setIcon(image.getIcon());
	}
	
	public void setIcon(LVValue name) throws LRException
	{
		setIcon(new ImageIcon(name.getStringValue()));
	}
	
	public void setIcon(ImageIcon icon) throws LRException
	{
		if (getComponentData().sizeSpecified) return;
		GraphicsHCCanvas canvas=(GraphicsHCCanvas)getComponent();
		canvas.setIcon(icon);
		if (canvas==null) throw new LRException(LRException.notCreated(getName()));
		try { canvas.validate(); } catch (Exception ignored) {}		// it's needed - don't know why
		int iconWidth=0;
		int iconHeight=0;
		if (icon!=null)
		{
			iconWidth=icon.getIconWidth();
			iconHeight=icon.getIconHeight();
		}
		Insets insets=canvas.getInsets();
		canvas.setSize(insets.left+insets.right+iconWidth,insets.top+insets.bottom+iconHeight);
	}

	/***************************************************************************
		The actual component.
	*/
	class GraphicsHCCanvas extends GraphicsHC2DComponent
	{
		ImageIcon icon;

		GraphicsHCCanvas()
		{
		}
		
		public void setIcon(ImageIcon icon)
		{
			this.icon=icon;
			validate();
		}
		
		public void paint(Graphics g)
		{
			Graphics2D g2=(Graphics2D)g;
			super.paint(g2);
			g2.drawImage(icon.getImage(),0,0,this);
		}
	}
}
