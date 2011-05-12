// LLine.java

package net.eclecity.linguist.textpane;

import java.util.Vector;

/******************************************************************************
	The LLine class.
*/
public class LLine extends Vector
{
	private int top=0;
	private int base=0;

	public LLine() {}
	
	public void addToken(LToken token) { addElement(token); }
	public LToken getToken(int n) { return (LToken)elementAt(n); }
	public int getTop() { return top; }
	public void setTop(int t) { top=t; }
	public int getBase() { return base; }
	
	public void setBase(int b)
	{
		base=b;
		for (int n=0; n<size(); n++) getToken(n).setBase(base);
	}
}
