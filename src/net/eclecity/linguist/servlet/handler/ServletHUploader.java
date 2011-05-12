// ServletHUploader.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.servlet.handler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;

import net.eclecity.linguist.handler.LHData;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.servlet.runtime.ServletRBackground;
import net.eclecity.linguist.servlet.runtime.ServletRServletParams;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	A file uploader.
	<pre>
	[1.001 GT]  18/05/03  New class.
	</pre>
*/
public class ServletHUploader extends LHVariableHandler
{
	protected ServletRBackground background;

	public ServletHUploader() {}

	public Object newElement(Object extra) { return new Uploader(); }

	/***************************************************************************
		Set the full path name of the file to create.
	*/
	public void setPath(LVValue path) throws LRException
	{
		Uploader uploader=(Uploader)getData();
		uploader.setPath(path.getStringValue());
	}

	/***************************************************************************
		Add a file type.
	*/
	public void add(LVValue type) throws LRException
	{
		Uploader uploader=(Uploader)getData();
		uploader.addType(type.getStringValue());
	}

	/***************************************************************************
		Upload the file.
	*/
	public void upload() throws LRException
	{
		Uploader uploader=(Uploader)getData();
		try { uploader.upload(); }
		catch (IOException e) {}
	}

	/***************************************************************************
		Get a parameter.
	*/
	public String getParameter(LVValue name) throws LRException
	{
		Uploader uploader=(Uploader)getData();
		return uploader.getParameter(name.getStringValue());
	}

	/***************************************************************************
		A private class that does the upload.
	*/
	class Uploader extends LHData
	{
		ServletRequest request;
		int maxSize=10*1024*1024;		// 10MB default
		Hashtable parameters=new Hashtable();
		Vector types=new Vector();
		String path="uploaded.dat";

		Uploader() {}

		void setPath(String path)
		{
			this.path=path;
		}

		@SuppressWarnings("unchecked")
		void putParameter(String name,String value)
		{
			parameters.put(name,value.trim());
		}

		String getParameter(String name)
		{
			return (String)parameters.get(name);
		}

		@SuppressWarnings("unchecked")
		void addType(String type)
		{
			types.add(type.toLowerCase());
		}

		/************************************************************************
			Do the upload.
		*/
		void upload() throws IOException
		{
			ServletRServletParams params=
				(ServletRServletParams)program.getQueueData(new ServletRServletParams().getClass());
			request=params.request;
			int length=request.getContentLength();
			if (length>maxSize) return;
			String type=request.getContentType().toLowerCase();
			if (type==null || !type.startsWith("multipart/form-data")) return;
			// Get the boundary string
			int index=type.indexOf("boundary=");
			if (index<0) return;
			// Prefix with "--"  (don't know why)
			String boundary="--"+type.substring(index+9);		// skip the "boundary="
			MultipartStream in=new MultipartStream(request.getInputStream(),maxSize);
			// Read past the first boundary
			String line=in.readLine();
			if (line==null) return;
			if (!line.startsWith(boundary)) throw new IOException("Corrupt form data; no boundary");
			while (true)
			{
				line=in.readLine();
				if (line==null) break;
				String original=line;
				line=line.toLowerCase();
				// The content disposition should be "form-data".
				int start=line.indexOf("content-disposition: ");
				int end=line.indexOf(";");
				if (start==-1 || end==-1) throw new IOException("Bad content disposition.");
				String disposition=line.substring(start+21,end);
				if (!disposition.equals("form-data")) throw new IOException("Bad content disposition.");
				// Get the field name.
				start=line.indexOf("name=\"",end);
				end=line.indexOf("\"",start+7);
				if (start==-1 || end==-1) throw new IOException("Bad field name.");
				String fieldName=original.substring(start+6,end);
				// Get the filename.
				String fileName=null;
				start=line.indexOf("filename=\"",end);
				end=line.indexOf("\"",start+10);
				if (start>0 && end>0)
				{
					fileName=original.substring(start+10,end);
					putParameter("source",fileName);
				}
				// Move on to the content type
				line=in.readLine();
				if (line==null) return;
				String contentType=null;
				if (line.toLowerCase().startsWith("content-type"))
				{
					start=line.indexOf(" ");
					if (start<0) throw new IOException("Bad content type");
					contentType=line.substring(start+1);
				}
				if (contentType!=null)
				{
					line=in.readLine();
					if (line==null || line.length()>0) throw new IOException("Bad line after content type.");
				}
				else contentType="application/octet-stream";
				// Now read the content.
				if (fileName==null)
				{
					StringBuffer sb=new StringBuffer();
					while ((line=in.readLine())!=null)
					{
						if (line.startsWith(boundary)) break;
						sb.append(line+"\r\n");
					}
					String value=sb.toString();
					if (value!=null) putParameter(fieldName,value);
				}
				else
				{
					boolean valid=true;
					// Check if the type is allowed
					String fileType=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
					if (!types.contains(fileType))
					{
						println("Not an allowed file type: '"+fileType+"'.");
						valid=false;
					}
					// Do the file upload
					BufferedOutputStream bos=null;
					if (valid) bos=new BufferedOutputStream(new FileOutputStream(path),8*1024);
					byte[] bb=new byte[8*1024];
					int count;
					boolean flag=false;
					while ((count=in.readLine(bb))!=-1)
					{
						if (count>2 && bb[0]=='-' && bb[1]=='-')
						{
							line=new String(bb,0,count,"ISO-8859-1");
							if (line.startsWith(boundary)) break;
						}
						if (flag)
						{
							if (valid)
							{
								bos.write('\r');
								bos.write('\n');
							}
							flag=false;
						}
						if (count>=2 && bb[count-2]=='\r'
							&& bb[count-1]=='\n')
						{
							if (valid) bos.write(bb,0,count-2);
							flag=true;
						}
						else if (valid) bos.write(bb,0,count);
					}
					if (valid)
					{
						bos.flush();
						bos.close();
					}
				}
			}
		}
	}

	class MultipartStream
	{
		ServletInputStream in;
		int expected;
		int read=0;
		byte[] buf=new byte[8*1024];

		MultipartStream(ServletInputStream in,int expected)
		{
			this.in=in;
			this.expected=expected;
		}

		String readLine() throws IOException
		{
			StringBuffer sb=new StringBuffer();
			int length;
			do
			{
				length=readLine(buf);
				if (length>0) sb.append(new String(buf,0,length,"ISO-8859-1"));
			}
			while (length==buf.length);
			if (sb.length()==0) return null;
			sb.setLength(sb.length()-2);
			return sb.toString();
		}

		int readLine(byte[] b) throws IOException
		{
			if (read>=expected) return -1;
			int count=in.readLine(b,0,buf.length);
			if (count>0) read+=count;
			return count;
		}
	}
}


