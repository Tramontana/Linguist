//	CommsKSet.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.comms.keyword;

import javax.comm.SerialPort;

import net.eclecity.linguist.comms.handler.CommsHPort;
import net.eclecity.linguist.comms.handler.CommsHSet;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.keyword.LKHandler;
import net.eclecity.linguist.main.LLException;
import net.eclecity.linguist.main.LLMessages;
import net.eclecity.linguist.value.LVValue;


/******************************************************************************
	set {port} to {baudrate} {databits} {stopbits} n/e/o/m/s
	set {port} dtr/rts high/low/true/false
*/
public class CommsKSet extends LKHandler
{
	public LHHandler handleKeyword() throws LLException
	{
		getNextToken();
	   if (isSymbol())
	   {
	      LHHandler h=getHandler();
	      if (h instanceof CommsHPort)
	      {
				// set {port} to {baudrate} {databits} {stopbits} n/e/o/m/s
				getNextToken();
				if (tokenIs("to"))
				{
					LVValue baud=getNextValue();
					getNextToken();
					int dataBits;
					if (tokenIs("5")) dataBits=SerialPort.DATABITS_5;
					else if (tokenIs("6")) dataBits=SerialPort.DATABITS_6;
					else if (tokenIs("7")) dataBits=SerialPort.DATABITS_7;
					else if (tokenIs("8")) dataBits=SerialPort.DATABITS_8;
					else throw new LLException(LLMessages.unsupportedPortConfig());
					getNextToken();
					int stopBits;
					if (tokenIs("1")) stopBits=SerialPort.STOPBITS_1;
					else if (tokenIs("1.5")) stopBits=SerialPort.STOPBITS_1_5;
					else if (tokenIs("2")) stopBits=SerialPort.STOPBITS_2;
					else throw new LLException(LLMessages.unsupportedPortConfig());
					getNextToken();
					int parity;
					switch (getToken().charAt(0))
					{
						case 'n': parity=SerialPort.PARITY_NONE; break;
						case 'e': parity=SerialPort.PARITY_EVEN; break;
						case 'o': parity=SerialPort.PARITY_ODD; break;
						case 'm': parity=SerialPort.PARITY_MARK; break;
						case 's': parity=SerialPort.PARITY_SPACE; break;
						default: throw new LLException(LLMessages.unsupportedPortConfig());
					}
					return new CommsHSet(line,(CommsHPort)h,baud,dataBits,stopBits,parity);
				}
				if (tokenIs("dtr"))
				{
					getNextToken();
					boolean dtrTrue=false;
					if (tokenIs("high") || tokenIs("true")) dtrTrue=true;
					else if (tokenIs("low") || tokenIs("false")) dtrTrue=false;
					else dontUnderstandToken();
					return new CommsHSet(line,(CommsHPort)h,true,dtrTrue);
				}
				if (tokenIs("rts"))
				{
					getNextToken();
					boolean rtsTrue=false;
					if (tokenIs("high") || tokenIs("true")) rtsTrue=true;
					else if (tokenIs("low") || tokenIs("false")) rtsTrue=false;
					else dontUnderstandToken();
					return new CommsHSet(line,(CommsHPort)h,false,rtsTrue);
				}
				dontUnderstandToken();
	      }
	   }
		warning(this,LLMessages.variableExpected(getToken()));
      return null;
	}
}

