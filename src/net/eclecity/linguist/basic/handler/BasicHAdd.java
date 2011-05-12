// BasicHAdd.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHValueHolder;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Add something to something.
	<pre>
	[1.000 GT] 25/09/01  Add to a vector.
	</pre>
*/
public class BasicHAdd extends LHHandler
{
	private BasicHVector vector=null;
	private LVValue value;					// the value to add
	private LHValueHolder variable;		// the variable to add it to
	private LHValueHolder target;			// the variable to put it in

	/***************************************************************************
		Add a numeric value to a value holder [giving another value holder].
	*/
	public BasicHAdd(int line,LVValue value,LHValueHolder variable,LHValueHolder target)
	{
		super(line);
		this.value=value;
		this.variable=variable;
		this.target=target;
	}

	/***************************************************************************
		Add an item to a vector.
	*/
	public BasicHAdd(int line,LVValue value,BasicHVector vector)
	{
		super(line);
		this.value=value;
		this.vector=vector;
	}

	/***************************************************************************
		(Runtime)  Do it now.
	*/
	public int execute() throws LRException
	{
		if (vector!=null) vector.add(value);
		else target.setValue(variable.getNumericValue()+value.getNumericValue());
		return pc+1;
	}
}

