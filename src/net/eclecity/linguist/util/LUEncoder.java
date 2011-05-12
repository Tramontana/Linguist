// LUEncoder.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.support.Base64;


/******************************************************************************
	A utility class that does URL encoding and decoding.
	<pre>
	[1.001 GT]  04/12/02  New class.
	</pre>
*/
public class LUEncoder
{
	/***************************************************************************
		Encode.
	*/
	static public String encode(String text) throws LRException
	{
		return encode(text,URL);
	}

	/***************************************************************************
		Encode.
	*/
	static public String encode(String text,String method) throws LRException
	{
		try
		{
			if (method==null || method.equals(URL)) return URLEncoder.encode(text,"UTF-8");
			else if (method.equals(BASE64)) return Base64.encodeString(text);
			else throw new LRException(LUErrors.badEncodingMethod(method));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new LRException(e);
		}
	}

	/***************************************************************************
		Decode
	*/
	static public String decode(String text) throws LRException
	{
		return decode(text,URL);
   }

	/***************************************************************************
		Decode
	*/
	static public String decode(String text,String method) throws LRException
	{
		try
		{
			if (method==null || method.equals(URL)) return URLDecoder.decode(text,"UTF-8");
			else if (method.equals(BASE64)) return Base64.decodeToString(text);
			else throw new LRException(LUErrors.badEncodingMethod(method));
		}
		catch (UnsupportedEncodingException e)
		{
			throw new LRException(e);
		}
   }

	/***************************************************************************
		Check if the method is valid
	*/
	static public boolean isValidMethod(String text)
	{
		if (text.equals(URL)) return true;
		if (text.equals(BASE64)) return true;
		return false;
   }

	private static final String URL="url";
	private static final String BASE64="base64";
}
