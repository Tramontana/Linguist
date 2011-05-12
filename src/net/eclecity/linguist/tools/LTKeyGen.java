//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.tools;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.eclecity.linguist.support.Base64;


/******************************************************************************
	Secret key generator.
	<pre>
	[1.001 GT]  11/01/02  New class.
	</pre>
*/
public class LTKeyGen
{
	public static void main(String[] args)
	{
		try
		{
			KeyGenerator desGen=KeyGenerator.getInstance("DES");
			SecretKey desKey=desGen.generateKey();
			SecretKeyFactory desFactory=SecretKeyFactory.getInstance("DES");
			DESKeySpec desSpec=(DESKeySpec)desFactory.getKeySpec(desKey,DESKeySpec.class);
			byte[] rawDesKey=desSpec.getKey();
			System.out.println(Base64.encodeBytes(rawDesKey));

		}
		catch (Exception e) { e.printStackTrace(); }
	}
}
