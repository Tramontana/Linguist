// ServletHCookie.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import javax.servlet.http.Cookie;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHStringValue;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A cookie.
*/
public class ServletHCookie extends LHStringValue
{
	private ServletRBackground background=null;

	public ServletHCookie() {}

	public Object newElement(Object extra) { return new ServletHCookieData(); }
	
	/***************************************************************************
		Get a named cookie from the request.
		If it doesn't exist, create a new one.
	*/
	public void get(LVValue value) throws LRException
	{
		if (background==null) background=(ServletRBackground)getBackground("servlet");
		String name=value.getStringValue();
		ServletHCookieData myData=(ServletHCookieData)getData();
		myData.cookie=null;
		ServletRServletParams params=
			(ServletRServletParams)getQueueData(new ServletRServletParams().getClass());
		if (params!=null)
		{
			if (params.request!=null)
			{
				// look for the cookie having this name
				Cookie[] cookies=params.request.getCookies();
				for (int n=0; n<cookies.length; n++)
				{
					if (cookies[n].getName().equals(name))
					{
						myData.cookie=cookies[n];
						break;
					}
				}
			}
		}
	}
	
	/***************************************************************************
		Create a new cookie.
	*/
	public void create(LVValue name) throws LRException
	{
		ServletHCookieData myData=(ServletHCookieData)getData();
		myData.cookie=new Cookie(name.getStringValue(),"empty");
		myData.cookie.setMaxAge(-1);
	}
	
	/***************************************************************************
		Set the expiry time of the cookie.
	*/
	public void setExpiry(LVValue value) throws LRException
	{
		ServletHCookieData myData=(ServletHCookieData)getData();
		myData.cookie.setMaxAge(value.getIntegerValue());
	}
	
	/***************************************************************************
		Return the cookie.
	*/
	public Cookie getCookie() throws LRException
	{
		ServletHCookieData myData=(ServletHCookieData)getData();
		return myData.cookie;
	}
	
	/***************************************************************************
		Return the value from the cookie.
	*/
	public String getStringValue() throws LRException
	{
		return getValue((ServletHCookieData)getData());
	}
	public String getStringValue(int n) throws LRException
	{
		return getValue((ServletHCookieData)getData(n));
	}
	private String getValue(ServletHCookieData myData)
	{
		if (myData.cookie==null) return "";
		return myData.cookie.getValue();
	}

	/***************************************************************************
		Set the value of the cookie.
	*/
	public void setValue(String value) throws LRException
	{
		ServletHCookieData myData=(ServletHCookieData)getData();
		if (myData.cookie!=null) myData.cookie.setValue(value);
	}

	/***************************************************************************
		The data for a cookie.
	*/
	class ServletHCookieData extends LHData
	{
		Cookie cookie=null;
		
		ServletHCookieData() {}
	}
}


