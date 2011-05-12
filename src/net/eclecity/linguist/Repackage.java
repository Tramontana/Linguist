// Repackage.java

// Repackager for Linguist classes

package net.eclecity.linguist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

class Repackage
{
	String oldPrefix;
	String newPrefix;

	public static void main(String[] args)
	{
		if (args.length<2)
		{
			System.out.println("Syntax: net.eclecity.linguist.Repackage <oldpackage> <newpackage>");
			System.exit(0);
		}
		File f=new File("src/net/eclecity/linguist/"+args[0]);
		if (f.isDirectory()) new Repackage(args[0],args[1]);
		else System.out.println("Package '"+args[0]+"' does not exist.");
	}

	/***************************************************************************
		Repackage everything at and below this point
	*/
	Repackage(String oldPackage,String newPackage)
	{
		oldPrefix=oldPackage.substring(0,1).toUpperCase()+oldPackage.substring(1).toLowerCase();
		newPrefix=newPackage.substring(0,1).toUpperCase()+newPackage.substring(1).toLowerCase();
		rename(oldPackage,newPackage);
		doJava(new File("src/net/eclecity/linguist/"+newPackage),oldPrefix,newPrefix);
		doJava(new File("src/net/eclecity/linguist/"+newPackage,"condition"),oldPackage,newPackage);
		doJava(new File("src/net/eclecity/linguist/"+newPackage,"handler"),oldPackage,newPackage);
		doJava(new File("src/net/eclecity/linguist/"+newPackage,"keyword"),oldPackage,newPackage);
		doJava(new File("src/net/eclecity/linguist/"+newPackage,"runtime"),oldPackage,newPackage);
		doJava(new File("src/net/eclecity/linguist/"+newPackage,"value"),oldPackage,newPackage);
		doHTML(oldPackage,newPackage);

	}
	
	/***************************************************************************
		Rename all files
	*/
	void rename(String oldPackage,String newPackage)
	{
		// First rename the package folder
		File oldFile=new File("src/net/eclecity/linguist/"+oldPackage);
		File newFile=new File("src/net/eclecity/linguist/"+newPackage);
		System.out.println("Rename package "+oldPackage+" to "+newPackage);
		if (oldFile.exists()) oldFile.renameTo(newFile);
		rename(newFile,oldPrefix,newPrefix);
	}
	
	/***************************************************************************
		Rename a directory recursively
	*/
	void rename(File dir,String oldPrefix,String newPrefix)
	{
		String[] ls=dir.list();
		if (ls!=null)
		{
			for (int n=0; n<ls.length; n++)
			{
				File file=new File(dir,ls[n]);
				if (file.isDirectory()) rename(new File(dir,ls[n]),oldPrefix,newPrefix);
				else
				{
					if (ls[n].startsWith(oldPrefix))
					{
						String newName=newPrefix+ls[n].substring(oldPrefix.length());
//						System.out.println("Rename "+ls[n]+" to "+newName);
						file.renameTo(new File(dir,newName));
					}
				}
			}
		}
	}

	/***************************************************************************
		Edit all the java files
	*/
	void doJava(File file,String oldPackage,String newPackage)
	{
		String[] ls=file.list(new FilenameFilter()
		{
			public boolean accept(File dir,String name)
			{
				return name.endsWith(".java");
			}
		});
		if (ls!=null)
		{
			for (int n=0; n<ls.length; n++)
			{
				try
				{
					File theFile=new File(file,ls[n]);
					File tempFile=new File(file,"temp.java");
					BufferedReader in=new BufferedReader(new FileReader(theFile));
					BufferedWriter out=new BufferedWriter(new FileWriter(tempFile));
					String s;
					while ((s=in.readLine())!=null)
					{
						String item;
						int index=0;
						while (true)
						{
							item="net.eclecity.linguist."+oldPackage;
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+"net.eclecity.linguist."+newPackage+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'C';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'C'+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'H';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'H'+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'K';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'K'+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'L';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'L'+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'R';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'R'+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item=oldPrefix+'V';
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+newPrefix+'V'+s.substring(index+=item.length());
						}
//						System.out.println(s);
						out.write(s);
						out.newLine();
					}
					out.close();
					in.close();
					theFile.delete();
					tempFile.renameTo(theFile);
				}
				catch (IOException e) {}
			}
		}
	}

	/***************************************************************************
		Edit all the HTML files
	*/
	void doHTML(String oldPackage,String newPackage)
	{
		File file=new File("doc/"+oldPackage);
		file.renameTo(new File("doc/"+newPackage));
		String[] ls=file.list(new FilenameFilter()
		{
			public boolean accept(File dir,String name)
			{
				return name.endsWith(".html");
			}
		});
		if (ls!=null)
		{
			for (int n=0; n<ls.length; n++)
			{
				try
				{
					File theFile=new File(file,ls[n]);
					File tempFile=new File(file,"temp.java");
					BufferedReader in=new BufferedReader(new FileReader(theFile));
					BufferedWriter out=new BufferedWriter(new FileWriter(tempFile));
					String s;
					while ((s=in.readLine())!=null)
					{
						String item;
						int index=0;
						while (true)
						{
							item="net.eclecity.linguist."+oldPackage+".handler."+oldPrefix;
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+"net.eclecity.linguist."+newPackage+".handler."+newPrefix
								+s.substring(index+=item.length());
						}
						index=0;
						while (true)
						{
							item="net.eclecity.linguist."+oldPackage+".keyword."+oldPrefix;
							index=s.indexOf(item,index);
							if (index<0) break;
							s=s.substring(0,index)+"net.eclecity.linguist."+newPackage+".keyword."+newPrefix
								+s.substring(index+=item.length());
						}
//						System.out.println(s);
						out.write(s);
						out.newLine();
					}
					out.close();
					in.close();
					theFile.delete();
					tempFile.renameTo(theFile);
				}
				catch (IOException e) {}
			}
		}
	}
}
