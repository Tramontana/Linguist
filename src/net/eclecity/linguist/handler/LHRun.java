// LHRun.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipInputStream;

import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLScript;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.util.LUHttpMessage;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	Run another script.
*/
public class LHRun extends LHHandler
{
	Vector exports;
	LVValue name;
	LHModule module;

	public LHRun(int line,LVValue name,Vector exports,LHModule module)
	{
		this.line=line;
		this.exports=exports;
		this.module=module;
		this.name=name;
	}

	public int execute() throws LRException
	{
		LRProgram p=null;
		String[] packages=null;
		LLScript[] script=null;
		String name=this.name.getStringValue();
		if (!name.endsWith(LLCompiler.compiledExtension))
			name+=LLCompiler.compiledExtension;
		try
		{
			ZipInputStream zis=null;
			InputStream resource=null;
			if (name.startsWith("http://"))
			{
				LUHttpMessage msg=null;
				try { msg=new LUHttpMessage(name); }
				catch (MalformedURLException e) { throw new LRException(LRException.badURL(name)); }
				resource=msg.sendPostMessage(new Properties());
			}
			else
			{
				name=program.getPathName()+name;
				ClassLoader cl=this.getClass().getClassLoader();
				resource=cl.getResourceAsStream(name);
			}
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
				script=(LLScript[])in.readObject();
				packages=(String[])in.readObject();
				p=(LRProgram)in.readObject();
				in.close();
			}
			catch (ClassNotFoundException e) { cantRunScript(e,name); }
			catch (OptionalDataException e) { cantRunScript(e,name); }
			bos.close();
		}
		catch (IOException e) { cantRunScript(e,name); }
		// Check all my exports against the imports in the named script
		Vector imports=new Vector();
		int n;
		for (n=0; n<p.size(); n++)
		{
			LHHandler h=(LHHandler)p.elementAt(n);
			if (h instanceof LHImport)
			{
				imports=((LHImport)h).getImports();
				break;
			}
		}
		if (imports.size()!=exports.size())
			throw new LRException(LRException.exportsDontMatchImports(program.getScriptName(),name));
		for (n=0; n<exports.size(); n++)
		{
			LHHandler importVar=(LHHandler)imports.elementAt(n);
			LHHandler exportVar=(LHHandler)exports.elementAt(n);
			if (!importVar.getClass().equals(exportVar.getClass()))
				throw new LRException(LRException.exportDoesNotMatchImport(
					((LHHandler)exports.elementAt(n)).getName(),
					((LHHandler)imports.elementAt(n)).getName(),
					name));
			if (importVar instanceof LHVariableHandler)
				((LHVariableHandler)importVar).importData((LHVariableHandler)exportVar);
			else if (importVar instanceof LHConstant)
				((LHConstant)importVar).setValue(((LHConstant)exportVar).getValue());
		}
		if (module!=null) module.setModule(p);
		program.newScript(p,this.name.getStringValue(),script,packages,pc+1);
		return 0;
	}

	private void cantRunScript(Exception e,String name) throws LRException
	{
//		e.printStackTrace();
		throw new LRException(LRException.cantRunScript(name));
	}
}

