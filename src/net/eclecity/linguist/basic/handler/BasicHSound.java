// BasicHSound.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A sound variable.<p>
	This provides a means to play short sound clips.
	<p><pre>
	[1.001 GT]  13/07/00  Pre-existing.
	</pre>
*/
public class BasicHSound extends LHVariableHandler
{
	public Object newElement(Object extra) { return new BasicHAudioClip(); }
	
	/***************************************************************************
		Load an audio clip.
	*/
	public void load(LVValue path) throws LRException
	{
		((BasicHAudioClip)getData()).loadSound(path.getStringValue());
	}
	
	/***************************************************************************
		Play the sound.
	*/
	public void play(boolean looped) throws LRException
	{
		BasicHAudioClip clip=((BasicHAudioClip)getData());
		if (clip!=null) clip.playSound(looped);
	}
	
	/***************************************************************************
		Stop the sound.
	*/
	public void stop() throws LRException
	{
		BasicHAudioClip clip=((BasicHAudioClip)getData());
		if (clip!=null) clip.stopSound();
	}
}
	
/******************************************************************************
	An audio clip.
*/
class BasicHAudioClip implements Serializable
{
	private AudioClip clip=null;
	
	BasicHAudioClip() {}
	
	void loadSound(String name)
	{
		URL url=null;
		try
		{
			if (name.startsWith("http://")) url=new URL(name);
			else url=new URL("file","localhost",name);
			clip=Applet.newAudioClip(url);
		}
		catch (MalformedURLException e) {}
	}
	
	void playSound(boolean looped)
	{
		if (clip!=null)
		{
			if (looped) clip.loop();
			else clip.play();
		}
	}
	
	void stopSound()
	{
		if (clip!=null) clip.stop();
	}
}
