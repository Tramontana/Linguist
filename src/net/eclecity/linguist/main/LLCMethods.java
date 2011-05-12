// LLCMethods.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.main;

import net.eclecity.linguist.condition.LCCondition;
import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVPair;
import net.eclecity.linguist.value.LVValue;

public class LLCMethods extends LLObject
{
	protected LLCompiler compiler;

	public LLCMethods() {}

	protected String getScriptName() { return compiler.getScriptName(); }
	protected String[] getPackages() { return compiler.getPackages(); }
	protected int getMaxVar() { return compiler.maxVar; }
	protected void bumpMaxVar() { compiler.maxVar++; }
	protected void skip(String s) throws LLException { compiler.skip(s); }
	protected void getNextToken() throws LLException { compiler.getNextToken(); }
	protected void unGetToken() { compiler.unGetToken(); }
	protected void unGetToken(String s) { compiler.unGetToken(s); }
	protected String getToken() { return compiler.token; }
	protected String getString() { return compiler.getString(); }
	protected boolean tokenIs(String s) { return compiler.token.equals(s); }
	protected int getCurrentLino() { return compiler.getCurrentLino(); }
	protected int getPass() { return compiler.pass; }
	protected void putSymbol(String s,Object o) throws LLException { compiler.putSymbol(s,o); }
	protected boolean isSymbol(){ return compiler.isSymbol(); }
	protected boolean isSymbol(String s) { return compiler.isSymbol(s); }
	protected LHHandler findSymbol() { return compiler.findSymbol(); }
	protected LHHandler findSymbol(String s) { return compiler.findSymbol(s); }
	protected LHHandler getHandler() { return compiler.symbolHandler; }
	protected boolean isConstant() { return compiler.isConstant(); }
	protected void checkProgramLabel() throws LLException { compiler.checkProgramLabel(); }
	protected int getLabelValue() { return compiler.getLabelValue(); }
	protected void dontUnderstandToken() throws LLException { compiler.dontUnderstandToken(); }
	protected void inappropriateType() throws LLException { compiler.inappropriateType(); }
	protected void constantExpected() throws LLException { compiler.constantExpected(); }
	protected void inConstant() { compiler.inConstant=true; }
	protected int getPC() { return compiler.getProgramCounter(); }
	protected void doKeyword() throws LLException { compiler.doKeyword(); }
	protected void addCommand(LHHandler h) { compiler.addCommand(h); }
	protected void setCommandAt(LHHandler h,int pc) { compiler.setCommandAt(h,pc); }
	protected long evaluate(String s) throws LLException { return compiler.evaluate(s); }
	protected long evaluate() throws LLException { return compiler.evaluate(compiler.token); }
	protected int evaluateInt() throws LLException { return (int)compiler.evaluate(compiler.token); }
	protected LVValue getValue() throws LLException { return compiler.getValue(); }
	protected LVValue getValue(boolean flag) throws LLException { return compiler.getValue(flag); }
	protected LVValue getNextValue() throws LLException
	{
		getNextToken();
		return compiler.getValue();
	}
	protected LVPair getPair() throws LLException { return compiler.getPair(); }
	protected LCCondition getCondition() throws LLException  { return compiler.getCondition(); }
	protected String getParam() { return compiler.getParam(); }
	protected LRProgram getProgram() { return compiler.getProgram(); }
	protected void putData(String name,Object data) { compiler.putData(name,data); }
	protected Object getData(String name) { return compiler.getData(name); }
	protected void warning(Object source,String message) { compiler.warning(source,message); }
}
