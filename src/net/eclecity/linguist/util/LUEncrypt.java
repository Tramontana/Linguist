// LUEncrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.util;

import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.support.Base64;


/******************************************************************************
	A utility class that does encryption and decryption.
	<pre>
	[1.001 GT]  06/10/00  New class.
	[1.002 GT]  11/02/03  Update to use Java Cryptography.
	</pre>
*/
public class LUEncrypt
{
	private static final String ALGORITHM="DES";

	/***************************************************************************
		Encrypt an object to a String.
		@param item the item to encrypt
		@param key the secret key, in Base64 format
		@return an encrypted 7-bit string.
	*/
	static public String encrypt(Serializable item,String keyString) throws LRException
	{
		try
		{
			byte[] rawDesKey=Base64.decode(keyString);
			DESKeySpec keySpec=new DESKeySpec(rawDesKey);
			SecretKeyFactory desFactory=SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey key=desFactory.generateSecret(keySpec);
			Cipher c=Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE,key);
			SealedObject so=new SealedObject(item,c);
			return Base64.encodeObject(so);
		}
		catch (Exception e) { e.printStackTrace(); throw new LRException(LUErrors.cantEncrypt()); }
	}

	/***************************************************************************
		Decrypt an String to an Object.
		@param item the encrypted string
		@param key the secret key, in Base64 format
		@return the decrypted object
	*/
	static public Object decrypt(String encrypted,String keyString) throws LRException
	{
		try
		{
			byte[] rawDesKey=Base64.decode(keyString);
			DESKeySpec keySpec=new DESKeySpec(rawDesKey);
			SecretKeyFactory desFactory=SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey key=desFactory.generateSecret(keySpec);
			SealedObject so=(SealedObject)Base64.decodeToObject(encrypted);
			return so.getObject(key);
		}
		catch (Exception e) { throw new LRException(LUErrors.cantDecrypt()); }
   }
}
