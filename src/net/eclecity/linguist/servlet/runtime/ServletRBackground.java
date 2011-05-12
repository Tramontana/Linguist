// ServletRBackground.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.runtime;

import java.awt.Color;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import net.eclecity.linguist.runtime.LRBackground;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.servlet.handler.ServletHCookie;
import net.eclecity.linguist.servlet.handler.ServletHElement;
import net.eclecity.linguist.servlet.handler.ServletHElementData;
import net.eclecity.linguist.util.LULoadString;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Handle background actions for the servlet package.
*/
public class ServletRBackground extends LRBackground
{
	private String head="";
	private String title="Untitled";
	private Properties properties=new Properties();

	private Stack fonts=new Stack();
	private Stack colors=new Stack();
	private Stack sizes=new Stack();
	private Stack bolds=new Stack();
	private Stack italics=new Stack();

	private String bodySpec=null;
	private String textFont=null;
	private Color textColor=null;
	private int textSize=0;
	private boolean textMode=false;
	private boolean isBold=false;
	private boolean isItalic=false;

	public ServletRBackground() {}

	public void setTextMode() { textMode=true; }
	public boolean isTextMode() { return textMode; }
	public String getHead() { return head; }
	public void setTitle(String title) { this.title=title; }
	public String getTitle() { return title; }
	public Vector getElements() { return elements; }

	private Vector elements=new Vector();
	private boolean form=false;

	private HttpSession session=null;

	protected void initBackground(Object data) {}
 	public void onMessage(LRProgram p,String message) {}
	public void doFinishActions() {}

	/***************************************************************************
		Set the head of the document.
	*/
	public void setHead(String head,boolean isFile)
	{
		if (isFile)
		{
			head=new LULoadString().load(head);
			int index0=head.indexOf("<HEAD>")+6;
			int index1=head.indexOf("</HEAD>",index0);
			if (index0<6 || index1<0) head="";
			else head=head.substring(index0,index1);
		}
		this.head=head;
	}

	/***************************************************************************
		Get the current session.
	*/
	public void getSession()
	{
		ServletRServletParams params=(ServletRServletParams)program.getQueueData();
		if (params.request!=null) session=params.request.getSession(true);
	}

	/***************************************************************************
		Return true if this is a new session.
	*/
	public boolean isNewSession()
	{
		return session.isNew();
	}

	/***************************************************************************
		Get the session ID.
	*/
	public String getSessionID()
	{
		if (session==null) return "";
		String s=session.getId();
		if (s==null) s="";
		return s;
	}

	/***************************************************************************
		Put a value into the session.
	*/
	public void putSessionValue(LVValue value,LVValue data) throws LRException
	{
		if (session!=null) session.putValue(value.getStringValue(),data.getStringValue());
	}

	/***************************************************************************
		Get a value from the session.
	*/
	public String getSessionValue(LVValue value) throws LRException
	{
		if (session==null) return "";
		String s=(String)session.getValue(value.getStringValue());
		if (s==null) s="";
		return s;
	}

	/***************************************************************************
		Methods for dealing with the HTML document.
	*/
	public void clear()
	{
		elements=new Vector();
		fonts=new Stack();
		colors=new Stack();
		sizes=new Stack();
		bolds=new Stack();
		italics=new Stack();
		bodySpec=null;
		textMode=false;
		isBold=false;
		isItalic=false;
	}

	/***************************************************************************
		Add an item to the document.
	*/
	public void add(Object item) throws LRException
	{
		if (item instanceof ServletHCookie)
		{
			ServletRServletParams params=
				(ServletRServletParams)program.getQueueData(new ServletRServletParams().getClass());
			if (params!=null)
			{
				if (params.response!=null)
				{
					params.response.addCookie(((ServletHCookie)item).getCookie());
				}
			}
		}
		else elements.addElement(ServletHElement.getElement(item));
	}

	/***************************************************************************
		Set the body spec for the background.
	*/
	public void setBodySpec(String s) { bodySpec=s; }

	/***************************************************************************
		Deal with the <BODY> tag of the document.
	*/
	public String getBodySpec() { return bodySpec; }

	/***************************************************************************
		Methods for dealing with parameters used in test mode.
	*/
	public void putParameter(String key,String data)
	{
		properties.put(key,data);
	}
	public Properties getParameters()
	{
		return properties;
	}

	/***************************************************************************
		Tell the background a form is in use.
	*/
	public void setForm() { form=true; }

	/***************************************************************************
		Return true if a form is in use.
	*/
	public boolean isForm() { return form; }

	/***************************************************************************
		Support methods.
	*/
	public static String toHex(Color c)
	{
		return toHex(c.getRed())+toHex(c.getGreen())+toHex(c.getBlue());
	}

	public static String toHex(int r,int g,int b)
	{
		return toHex(r)+toHex(g)+toHex(b);
	}

	private static String toHex(int n)
	{
		String s=Integer.toHexString(n);
		if (s.length()<2) s="0"+s;
		return s;
	}

	/***************************************************************************
		Generate tags for color and style.
	*/
	public String getPrefixes(ServletHElementData myData)
	{
		String s="";
		boolean fFlag=(myData.font!=null && !myData.font.equals(textFont));
		boolean cFlag=(myData.color!=null && !myData.color.equals(textColor));
		boolean sFlag=(myData.size!=0 && myData.size!=textSize);
		if (fFlag | cFlag | sFlag)
		{
			s="<FONT";
			if (fFlag)
			{
				fonts.push(textFont);
				textFont=myData.font;
				s+=(" FACE=\""+textFont+"\"");
			}
			if (cFlag)
			{
				colors.push(textColor);
				textColor=myData.color;
				s+=(" COLOR=\"#"+toHex(textColor)+"\"");
			}
			if (sFlag)
			{
				sizes.push(new Integer(textSize));
				textSize=myData.size;
				s+=(" SIZE="+textSize+"\"");
			}
			s+=">";
		}
		if (myData.isBold && myData.isBold!=isBold)
		{
			bolds.push(new Boolean(isBold));
			isBold=myData.isBold;
			if (isBold) s+="<B>";
		}
		if (myData.isItalic && myData.isItalic!=isItalic)
		{
			italics.push(new Boolean(isItalic));
			isItalic=myData.isItalic;
			if (isItalic) s+="<I>";
		}
		return s;
	}

	public String getSuffixes(ServletHElementData myData)
	{
		String s="";
		if (myData.isItalic)
		{
			isItalic=((Boolean)italics.pop()).booleanValue();
			if (myData.isItalic && !isItalic) s+="</I>";
		}
		if (myData.isBold)
		{
			isBold=((Boolean)bolds.pop()).booleanValue();
			if (myData.isBold && !isBold) s+="</B>";
		}
		boolean fFlag=false;
		boolean cFlag=false;
		boolean sFlag=false;
		if (myData.font!=null)
		{
			textFont=(String)fonts.pop();
			if (!myData.font.equals(textFont)) fFlag=true;
		}
		if (myData.color!=null)
		{
			textColor=(Color)colors.pop();
			if (!myData.color.equals(textColor)) cFlag=true;
		}
		if (myData.size!=0)
		{
			textSize=((Integer)sizes.pop()).intValue();
			if (myData.size!=textSize) sFlag=true;
		}
		if (fFlag | cFlag | sFlag) s+="</FONT>";
		return s;
	}
}
