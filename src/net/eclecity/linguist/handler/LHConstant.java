// LHConstant.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;


/******************************************************************************
	A generic constant.
	These don't go into the compiled program.
*/
public abstract class LHConstant extends LHHandler
{
	private String name;						// the name of the constant
	private Object value;					// the value
	private boolean numeric;				// true if numeric type
	
	public LHConstant() {}
	
	public LHConstant(int line,String name,Object value)
	{
		this(line,name,value,false);
	}

	public LHConstant(int line,String name,Object value,boolean numeric)
	{
		this.line=line;
		this.name=name;
		this.value=value;
		this.numeric=numeric;
	}

	public void setValue(Object data) { value=data; }
	public Object getValue() { return value; }
	public String getName() { return name; }
	public boolean isNumeric() { return numeric; }

	public int execute() { return pc+1; }
}

