// FixedLayout.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.support;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Hashtable;

/******************************************************************************
	FixedLayout is a layout manager that should be used (in accordance with
	Sun's advice) instead of setting the layout of a container to null.<p>
	The code for this class is taken from "Graphic Java - Mastering the AWT
	(2nd edition) from SunSoft.

	@author Graham Trott of TechModern Limited.
	<br>Created 27/07/00

	@version Java Version 1.2<p>
	Class Version -><pre>
	[1.0 GT]   27/07/00  Created.
	</pre/
*/
public class FixedLayout implements LayoutManager
{
	public void addLayoutComponent(String s,Component comp)
	{
	}

	public void removeLayoutComponent(Component comp)
	{
	}

	public Dimension preferredLayoutSize(Container target)
	{
		Insets insets=target.getInsets();
		Dimension dim=new Dimension(0,0);
		int ncomponents=target.getComponentCount();
		Component comp;
		Dimension d;
		Rectangle size=new Rectangle(0,0);
		Rectangle compSize;

		for (int n=0; n<ncomponents; n++)
		{
			comp=target.getComponent(n);
			if (comp.isVisible())
			{
				d=comp.getSize();
				compSize=new Rectangle(comp.getLocation());
				compSize.setSize(d.width,d.height);
				size=size.union(compSize);
			}
		}
		dim.width+=size.width+insets.right;
		dim.height+=size.height+insets.bottom;
		return dim;
	}

	public Dimension minimumLayoutSize(Container target)
	{
		Insets insets=target.getInsets();
		Dimension dim=new Dimension(0,0);
		int ncomponents=target.getComponentCount();
		Component comp;
		Dimension d;
		Rectangle size=new Rectangle(0,0);
		Rectangle compSize;

		for (int n=0; n<ncomponents; n++)
		{
			comp=target.getComponent(n);
			if (comp.isVisible())
			{
				d=comp.getMinimumSize();
				compSize=new Rectangle(comp.getLocation());
				compSize.setSize(d.width,d.height);
				size=size.union(compSize);
			}
		}
		dim.width+=size.width+insets.right;
		dim.height+=size.height+insets.bottom;
		return dim;
	}

	public void layoutContainer(Container target)
	{
		int ncomponents=target.getComponentCount();
		Component comp;
		Dimension sz,ps;
		Point loc;

		for (int n=0; n<ncomponents; n++)
		{
			comp=target.getComponent(n);
			if (comp.isVisible())
			{
				sz=comp.getSize();
				ps=comp.getPreferredSize();
				loc=getComponentLocation(comp);
				if (sz.width<ps.width || sz.height<ps.height) sz=ps;
				comp.setBounds(loc.x,loc.y,sz.width,sz.height);
			}
		}
	}

	private Point getComponentLocation(Component comp)
	{
		Point loc=comp.getLocation();

		if (!hash.containsKey(comp)) addComponentToHashtable(comp);
		else
		{
			Point oldLocation=(Point)hash.get(comp);
			if (oldLocation.x!=loc.x || oldLocation.y!=loc.y) addComponentToHashtable(comp);
		}
		return comp.getLocation();
	}

	private void addComponentToHashtable(Component comp)
	{
		Insets insets=comp.getParent().getInsets();
		Point loc=comp.getLocation();

		comp.setLocation(loc.x+insets.left,loc.y+insets.top);
		hash.put(comp,comp.getLocation());
	}

	private Hashtable hash=new Hashtable();
}
