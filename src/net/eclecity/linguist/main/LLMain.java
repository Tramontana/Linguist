//	LLMain.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Date;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.runtime.LRProgram;


/******************************************************************************
	The main program.
	Compiles the script and writes the result to a file.
	<pre>
	[1.001 GT]  08/01/01  Existing class.
	</pre>
*/
public class LLMain
{
	private LRProgram program=null;

	public LLMain(String[] args,boolean exitEnable,
		LHSystemOut systemOutput,String compiledExtension)
	{
		LLScript[] script=null;
		String name="";
		String param="";
		boolean output=false;
		boolean run=false;
		if (!compiledExtension.startsWith(".")) compiledExtension="."+compiledExtension;
		if (args[0].endsWith(compiledExtension))
		{
			param="";
			if (args.length>1) param=args[1];
			runProgram(args[0],param,exitEnable);
		}
		else
		{
			for (int n=0; n<args.length; n++)
			{
				if (args[n].charAt(0)=='-')
				{
					switch (Character.toLowerCase(args[n].charAt(1)))
					{
					case 'a':				// specify a command-line parameter
						param=args[n].substring(args[n].charAt(2)=='='?3:2);
						break;
					case 'o':				// create object but don't run
						output=true;
						break;
					case 'r':				// compile and run
						run=true;
						break;
					case '?':				// get help with syntax
						systemOutput.systemOut(LLText.syntax());
						return;
					}
				}
				else
				{
					name=args[n];
					int index=name.lastIndexOf("/");
					if (index>0) path=name.substring(0,index+1);
				}
			}
			script=getFile(name,true);
		}
		if (script==null) return;
		LLCompiler compiler=new LLCompiler();
		LLCompiler.setSystemOut(systemOutput);
		systemOutput.systemOut("Linguist compiler V"+LLVersion.version);
		systemOutput.systemOut("Copyleft 1998-2005 by Eclecity.\n"
			+"See "+LLVersion.home+" for more information.");
		try
		{
			program=compiler.compile(name,script,param,compiledExtension);
			String[] packages=compiler.getPackages();
			name=name.substring(0,name.lastIndexOf("."));
			if (output)
			{
				try
				{
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					ObjectOutputStream out=new ObjectOutputStream(bos);
					out.writeObject(script);
					out.writeObject(packages);
					out.writeObject(program);
					out.flush();
					byte[] b=bos.toByteArray();
					out.close();
					FileOutputStream fos=new FileOutputStream(name+compiledExtension);
      			ZipOutputStream zos=new ZipOutputStream(fos);
					ZipEntry ze=new ZipEntry("script");
					ze.setTime(new Date().getTime());
					ze.setMethod(ZipEntry.DEFLATED);
					zos.putNextEntry(ze);
					zos.write(b,0,b.length);
					zos.closeEntry();
					zos.close();
					systemOutput.systemOut(LLMessages.outputWritten(name,compiledExtension));
				}
				catch (IOException e) { System.out.println(e); }
			}
			compiler.compileExtraModules(path);
			if (run) program.init(null,name,script,packages,param,exitEnable,0,systemOutput);
			else if (exitEnable) System.exit(0);
		}
		catch (LLException e) { if (exitEnable) System.exit(0); }
		errorLine=compiler.getErrorLine();
	}

	private int errorLine=-1;

	/***************************************************************************
		Return the line number at which an error was detected.
		@return The line number.
	*/
	public int getErrorLine() { return errorLine; }

	/***************************************************************************
		Return the program that we created.
		@return The program.
	*/
	public LRProgram getProgram() { return program; }

	/***************************************************************************
		Read the contents of a text file.
		This method opens the file and returns its contents as a String array,
		one line per element.
		@param name the name of the file.
		@param include if true, permit the method to call itself recursively for included files.
		@return A String array comprising the lines of the file.
	*/
	public static LLScript[] getFile(String name,boolean include)
	{
		File file=new File(name);
		try
		{
			LLScript[] ss;
			int line=0;
			Vector<LLScript> v=new Vector<LLScript>();
			FileInputStream in=new FileInputStream(file);
			String s="";
			int c;
			int n;
			while ((c=in.read())>-1)
			{
				if (c=='\n')
				{
					// Check if this is an 'include' instruction.
					// If so, load the file (recursively).
					if (include && s.trim().startsWith(LLText.includeString()))
					{
						n=LLText.includeString().length()+1;
						while (s.charAt(n)==' ') n++;
						String parent=file.getParent();
						if (parent!=null) parent=parent+"/";
						else parent="";
						ss=getFile(parent+s.substring(n),include);
						for (n=0; n<ss.length; n++) v.addElement(ss[n]);
						line++;
					}
					else v.addElement(new LLScript(line++,s));
					s="";
				}
				else if (c!='\r') s+=new Character((char)c).toString();
			}
			if (s.length()>0) v.addElement(new LLScript(line,s));
			in.close();
			ss=new LLScript[v.size()];
			for (n=0; n<v.size(); n++) ss[n]=v.elementAt(n);
			return ss;
		}
//		catch (FileNotFoundException e) { throw e; }
		catch (IOException e) { System.out.println(e); }
		return null;
	}
	private static String path="";

	/***************************************************************************
		Run a precompiled script.
		@param name the name of the file.
		@param param an arbitrary String parameter.
	*/
	private void runProgram(String name,String param,boolean exitEnable)
	{
		try
		{
			ClassLoader cl=this.getClass().getClassLoader();
			InputStream resource=cl.getResourceAsStream(name);
			ZipInputStream zis;
			if (resource!=null) zis=new ZipInputStream(resource);
			else zis=new ZipInputStream(new FileInputStream(name));
			zis.getNextEntry();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			byte[] b=new byte[1024];
			while (true)
			{
				int n=zis.read(b,0,b.length);
				if (n<0) break;
				if (n>0) bos.write(b,0,n);
			}
			zis.close();
			ByteArrayInputStream bis=new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in=new ObjectInputStream(bis);
			try
			{
				LLScript[] script=(LLScript[])in.readObject();
				String[] packages=(String[])in.readObject();
				program=(LRProgram)in.readObject();
				in.close();
				System.out.println("Linguist runtime V"+LLVersion.version);
				System.out.println("Copyleft 1998-2003 by Linguist Software.\n"
					+"See "+LLVersion.home+" for more information.");
				System.out.println("Compiled script '"+name+"' loaded.");
				program.init(null,name.substring(0,name.length()-5),script,packages,param,exitEnable,0,null);
			}
			catch (ClassNotFoundException e) { System.out.println(e); }
			catch (OptionalDataException e) { System.out.println(e); }
			bos.close();
		}
		catch (IOException e) { System.out.println(e); }
	}
}
