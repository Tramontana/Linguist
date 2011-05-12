// LHVariableHandler.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.handler;

import java.util.Arrays;

import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * A generic variable base class.
 * <p>
 * 
 * <pre>
 * 
 *  [1.002 GT]  12/02/01  Add hasValue().
 *  [1.001 GT]  27/07/00  Pre-existing.
 *  
 * </pre>
 */
public abstract class LHVariableHandler extends LHHandler
{
	private int theIndex; // the current index for this variable
	private Object[] data = null; // the variable's data

	protected String name; // the name of the variable, for debugging
	protected int elements; // the number of elements
	protected boolean alias; // true if this is an alias
	protected int displayStart = 0;

	public LHVariableHandler()
	{}

	public void init()
	{
		init(0, "", 0, 1);
	}

	public void init(int line, String name, int pc, int elements)
	{
		this.line = line;
		this.name = name;
		this.pc = pc;
		this.elements = elements;
		alias = false;
		newData();
	}

	public void newData()
	{
		newData(getTotalElements());
	}

	public void newData(int count)
	{
		data = new Object[count];
		for (int n = 0; n < count; n++)
		{
			data[n] = newElement(null);
		}
	}

	/****************************************************************************
	 * Dispose the current element. Override this to perform special actions like
	 * closing a window or stopping a media player.
	 */
	public void dispose() throws LRException
	{
		checkIndex(theIndex);
		data[(elements == 1) ? 0 : theIndex] = newElement(null);
	}

	/****************************************************************************
	 * Dispose all elements.
	 */
	public void disposeAll()
	{
		for (int n = 0; n < elements; n++)
		{
			try
			{
				setTheIndex(n);
				dispose();
			}
			catch (LRException ignored)
			{}
			data[n] = null;
		}
	}

	public void aliasTo(LHVariableHandler h)
	{
		data[0] = h;
		alias = true;
	}

	/****************************************************************************
	 * Import data from another variable of this type.
	 */
	public void importData(LHVariableHandler h)
	{
		elements = h.getElements();
		data = (h.getDataArray());
	}

	/****************************************************************************
	 * Get the current index. Throw an error if it's outside the range of this
	 * array.
	 */
	public int getTheIndex()
	{
		if (alias) return ((LHVariableHandler) data[0]).getTheIndex();
		return (elements == 1) ? 0 : theIndex;
	}

	/****************************************************************************
	 * Set the current index. If the requested index is outside the range for
	 * this variable, do nothing.
	 */
	public void setTheIndex(LVValue value) throws LRException
	{
		setTheIndex(value.getIntegerValue());
	}

	/****************************************************************************
	 * Set the current index. If the requested index is outside the range for
	 * this variable, do nothing.
	 */
	public void setTheIndex(int value) throws LRException
	{
		if (alias) ((LHVariableHandler) data[0]).setTheIndex(value);
		if (elements > 1)
		{
			if (value < 0 || value >= elements) throw new LRException(LRException
					.indexOutOfRange(name, value));
			theIndex = value;
		}
	}

	/****************************************************************************
	 * Increment the current index. If the resulting index is outside the range
	 * for this variable, do nothing.
	 */
	public void incrementTheIndex() throws LRException
	{
		setTheIndex(getTheIndex() + 1);
	}

	/****************************************************************************
	 * Decrement the current index. If the resulting index is less than zero, do
	 * nothing.
	 */
	public void decrementTheIndex() throws LRException
	{
		setTheIndex(getTheIndex() - 1);
	}

	/****************************************************************************
	 * Check that an index is within range.
	 */
	public synchronized void checkIndex(int index) throws LRException
	{
		if (elements > 1 && index >= elements) throw new LRException(LRException
				.indexOutOfRange(name, index));
	}

	/****************************************************************************
	 * Assign an object to this variable.
	 */
	public synchronized void setData(Object value) throws LRException
	{
		setData(theIndex, value);
	}

	public synchronized void setData(int index, Object value) throws LRException
	{
		if (alias)
		{
			if (data[0] instanceof LHVariableHandler) ((LHVariableHandler) data[0])
					.setData(value);
			else throw new LRException(LRException.cantAssignAlias(getName()));
		}
		else
		{
			checkIndex(index);
			data[index] = value;
		}
	}

	/****************************************************************************
	 * Retrieve data from this variable.
	 */
	public synchronized Object getData() throws LRException
	{
		return getData(theIndex);
	}

	public synchronized Object getData(int index) throws LRException
	{
		if (alias)
		{
			if (data[0] instanceof LHVariableHandler) return ((LHVariableHandler) data[0])
					.getData();
			throw new LRException(LRException.cantAccessAlias(getName()));
		}
		checkIndex(index);
		return data[index];
	}

	/****************************************************************************
	 * Tell a caller if this variable can return a value. True by default.
	 */
	public boolean hasValue()
	{
		return true;
	}

	/****************************************************************************
	 * Create a data item that's a duplicate of the given source. The default
	 * does nothing. Override to provide a 'real' copy.
	 */
	public void duplicate(LHVariableHandler value) throws LRException
	{
		if (value.getClass() != getClass()) throw new LRException(LRException
				.incompatibleObjects());
	}

	/****************************************************************************
	 * Push this variable onto the user stack. Duplicate it first to unlink it.
	 */
	public void push(LRProgram program) throws LRException
	{
		program.push(this.getDataArray());
		duplicate(this);
	}

	/****************************************************************************
	 * Sort the variable. This assumes it's an array. Every subclass should
	 * provide a suitable mechanism for sorting if it wants to. The default is to
	 * do nothing.
	 */
	public void sort()
	{
		Object[] data = getDataArray();
		ComparableData[] cData = new ComparableData[data.length];
		for (int n = 0; n < data.length; n++)
			cData[n] = new ComparableData(data[n]);
		Arrays.sort(cData);
		for (int n = 0; n < data.length; n++)
			data[n] = cData[n].data;
		//		setDataArray(data);
	}

	/****************************************************************************
	 * Every subclass should provide a suitable mechanism for comparing if it
	 * wants to. The default is to do nothing.
	 */
	protected int compare(Object o1, Object o2)
	{
		return 0;
	}

	/****************************************************************************
	 * Return the number of elements in this item.
	 */
	public int getElements()
	{
		if (alias)
		{
			if (data[0] instanceof LHVariableHandler) return ((LHVariableHandler) data[0])
					.getElements();
			return 0;
		}
		return elements;
	}

	/****************************************************************************
	 * Set the number of elements in this item.
	 */
	public void setElements(int n)
	{
		elements = n;
	}

	/****************************************************************************
	 * Return the number of elements in this item, accounting for enclosing
	 * collection(s).
	 */
	protected int getTotalElements()
	{
		int n = getElements();
		return n;
	}

	public String getName()
	{
		return name;
	}

	public boolean isArray()
	{
		return (elements > 1);
	}

	public boolean isAlias()
	{
		return alias;
	}

	public Object[] getDataArray()
	{
		return data;
	}

	public void setDataArray(Object[] o)
	{
		data = o;
	}

	protected Object newElement(Object extra)
	{
		return null;
	} // override

	public int execute()
	{
		return pc + 1;
	}

	/****************************************************************************
	 * An inner class to to data comparisons.
	 */
	class ComparableData implements Comparable
	{
		Object data;

		ComparableData(Object data)
		{
			this.data = data;
		}

		/*************************************************************************
		 * Implementation of the Comparable interface. This calls a method that is
		 * overridden by other handlers if they want to support sorting.
		 */
		public int compareTo(Object obj)
		{
			return compare(data, ((ComparableData) obj).data);
		}

		public String toString()
		{
			return data.toString();
		}
	}
}