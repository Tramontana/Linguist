// LS.java

/*******************************************************************************
 * Linguist Script Compiler, Debugger and Runtime Copyleft (C) 1999 Graham Trott
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but without
 * any warranty; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have recieved a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 * 
 * ----------------------------------------------------------------
 * 
 * This program is offered free in the hope that others will be encouraged to
 * make improvements. The author would greatly appreciate being kept informed of
 * such improvements and would like to be provided with copies of such. Please
 * direct all correspondence to:
 * 
 * Graham Trott gt@pobox.com
 * http://www.eclecity.net
 * snail: 6 Orchard Way, Wymondhamn Norfolk NR18 0NX, England
 ******************************************************************************/

package net.eclecity.linguist;

import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLMain;

/*******************************************************************************
 * The Linguist application.
 */
public class LS implements LHSystemOut
{
	public static void main(String[] args)
	{
		new LS(args);
	}

	/****************************************************************************
	 * Default constructor.
	 */
	public LS()
	{
		this(new String[0]);
	}

	/****************************************************************************
	 * Constructor. If the argument list is empty the program goes to its help
	 * system.
	 * @param the argument list.
	 */
	public LS(String[] args)
	{
		if (args.length == 0) new LHelp("");
		else if (args[0].equals("help"))
		{
			if (args.length==1) new LHelp("");
			else new LHelp(args[1]);
		}
		else new LLMain(args, true, this, LLCompiler.compiledExtension);
	}

	/****************************************************************************
	 * Implementation of LHSystemOut.
	 * This would allow logging etc. instead of simply writing to the console.
	 */
	public void systemOut(String message)
	{
		System.out.println(message);
	}
}