//	BasicCModuleRunning.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.condition;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/******************************************************************************
	Test if a module is running.
*/
public class BasicCModuleRunning extends LCCondition
{
	private LHModule module=null;		// the module to test
	private LVValue name=null;			// the name of the module to test
	private LRProgram program;
	private boolean sense;				// true if returning true

	public BasicCModuleRunning(LHModule module,boolean sense)
	{
		this.module=module;
		this.sense=sense;
	}

	public BasicCModuleRunning(LRProgram program,LVValue name,boolean sense)
	{
		this.program=program;
		this.name=name;
		this.sense=sense;
	}

	public boolean test() throws LRException
	{
		boolean test=false;
		LRProgram p=null;
		if (module!=null) p=module.getModule();
		else if (name!=null) p=program.getModule(name.getStringValue());
		else return !sense;
		if (p!=null) test=p.isRunning();
		return sense?test:!test;
	}
}
