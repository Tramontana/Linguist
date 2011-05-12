// GraphicsHC2DComponent.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/******************************************************************************
	A generic 2D component.
	<pre>
	[1.001 GT]  05/01/01  New class.
	</pre>
*/
public class GraphicsHC2DComponent extends JComponent
{
	private float alpha=1.0f;

	public GraphicsHC2DComponent() {}
	
	/***************************************************************************
		Set the alpha value of the component.
	*/
	public void setAlpha(int a)
	{
		if (a<0) a=0;
		if (a>100) a=100;
		alpha=a/100.0f;
		repaint();
	}

	/***************************************************************************
		Paint the component.
	*/
	public void paint(Graphics g)
	{
		Graphics2D g2=(Graphics2D)g;
		Composite c=AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		g2.setComposite(c);
	}
}
