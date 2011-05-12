// NetworkHUpload.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.network.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.value.LVValue;


public class NetworkHUpload extends LHHandler
{
	private LVValue fileName;
	private LVValue address;

	/***************************************************************************
		Upload a file to a networker module.
	*/
	public NetworkHUpload(int line,LVValue fileName,LVValue address)
	{
		this.line=line;
		this.fileName=fileName;
		this.address=address;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute()
	{
		try
		{
			File file=new File(fileName.toString());
			if (file.exists())
			{
				FileInputStream fis=new FileInputStream(file);
				println("Begin upload of "+fileName.toString()+".");
				Socket socket=new Socket(address.toString(),17349);
				socket.setSoTimeout(2000);
				socket.setTcpNoDelay(true);
				OutputStream out=socket.getOutputStream();
				byte[] data=new byte[1000];
				int length;
				while ((length=fis.read(data))>0)
				{
					out.write(data,0,length);
//					try { Thread.sleep(1000); } catch (InterruptedException e) {}
				}
				fis.close();
				out.close();
				socket.close();
				println("Upload completed.");
			}
		}
		catch (IOException e) { println(e.getMessage()); }
		return pc+1;
	}
}

