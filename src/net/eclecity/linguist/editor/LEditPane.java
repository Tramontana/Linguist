// LEditPane.java

package net.eclecity.linguist.editor;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/******************************************************************************
	The main editor panel.
*/
public class LEditPane extends JTextArea
{
	private edit parent;
	private JScrollPane scroller;
	private boolean changed;
	private String fileName;
	private Vector script;

	/***************************************************************************
		Constructor.
	*/
	public LEditPane(edit parent)
	{
		this.parent=parent;
		scroller=new JScrollPane(this);
		setFont(new Font("Monospaced",Font.PLAIN,10));
		setTabSize(2);
	}
	
	/***************************************************************************
		Return the scroller.
	*/
	public JScrollPane getScroller() { return scroller; }

	/***************************************************************************
		Return the name of the file.
	*/
	public String getFileName()
	{
		return fileName;
	}

	/***************************************************************************
		Return the state of the "changed" flag.
	*/
	public boolean isChanged()
	{
		return changed;
	}

	/***************************************************************************
		New script.
	*/
	public void newScript()
	{
		setText("");
		fileName=null;
		changed=false;
	}

	/***************************************************************************
		Open a script.
	*/
	public void openScript()
	{
		LFileChooser chooser=new LFileChooser(parent,"Open a script");
		loadScript(chooser.getValue());
	}

	/***************************************************************************
		Load a script.
	*/
	private void loadScript(String name)
	{
		try
		{
			File file=new File(name);
			if (file.exists())
			{
				script=new Vector();
				BufferedReader br=new BufferedReader(new FileReader(file));
				while (true)
				{
					String s=br.readLine();
					if (s==null) break;
					script.addElement(s);
				}
				br.close();
				fileName=name;
				showScript();
				changed=false;
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(parent,"Unable to load file.");
		}
	}

	/***************************************************************************
		Save the current script.
	*/
	public void saveScript()
	{
		this.saveScript(fileName);
	}

	/***************************************************************************
		Save a script.
	*/
	public void saveScript(String name)
	{
		if (name==null) return;
		try
		{
			File file=new File(name+".tmp");
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			bw.write(getText());
			bw.close();
			File oldFile=new File(name);
			if (oldFile.exists())
			{
				oldFile.delete();
				file.renameTo(oldFile);
			}
			changed=false;
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(parent,"Unable to save file to\n"+name+".");
		}
	}

	/***************************************************************************
		Show the current script.
	*/
	public void showScript()
	{
		StringBuffer sb=new StringBuffer();
		Enumeration enumeration=script.elements();
		while (enumeration.hasMoreElements())
		{
			sb.append((String)enumeration.nextElement());
			sb.append("\n");
		}
		setText(sb.toString());
		scroller.getVerticalScrollBar().setValue(0);
	}

	/***************************************************************************
		Close the current script.
	*/
	public boolean closeScript()
	{
		if (changed)
		{
			switch (JOptionPane.showConfirmDialog(parent,
				"The text may have changed.\nDo you want to resave this file?",
				"Close",JOptionPane.YES_NO_CANCEL_OPTION))
			{
				case JOptionPane.YES_OPTION:
					saveScript(fileName);
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return false;
			}
		}
		script=new Vector();
		showScript();
		changed=false;
		return true;
	}

	/***************************************************************************
		Return the line containing the caret.
	*/
	public String getCurrentLine()
	{
		String s=getText();
		int caretPos=getCaretPosition();
		int start=caretPos;
		while (start>0)
		{
			if (s.charAt(start-1)=='\n') break;
			start--;
		}
		int end=caretPos;
		while (end<s.length())
		{
			if (s.charAt(end)=='\n') break;
			end++;
		}
		return s.substring(start,end);
	}
}
