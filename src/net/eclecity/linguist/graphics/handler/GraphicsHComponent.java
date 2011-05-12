// GraphicsHComponent.java

// =============================================================================
// Linguist Script Compiler, Debugger and Runtime
// Copyleft (C) 1999 Graham Trott
//
// Part of Linguist; see LS.java for a full copyleft notice.
// =============================================================================

package net.eclecity.linguist.graphics.handler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import net.eclecity.linguist.graphics.runtime.GraphicsRMessages;
import net.eclecity.linguist.graphics.value.GraphicsVColor;
import net.eclecity.linguist.graphics.value.GraphicsVLocation;
import net.eclecity.linguist.graphics.value.GraphicsVSize;
import net.eclecity.linguist.handler.LHCallback;
import net.eclecity.linguist.handler.LHEvent;
import net.eclecity.linguist.handler.LHEventSource;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * Component handler. This is the base class for other components.
 * 
 * <pre>
 *  [1.001 GT]  14/02/01  Pre-existing.
 * </pre>
 */
public abstract class GraphicsHComponent extends LHVariableHandler implements
		LHEventSource, ActionListener, TextListener, MouseListener,
		MouseMotionListener, WindowListener, ComponentListener, KeyListener
{
	private static Hashtable indices = new Hashtable();
	private String eventName = ""; // the name of the item that generated the
												// last event

	/****************************************************************************
	 * Default constructor.
	 */
	public GraphicsHComponent()
	{
	}

	/****************************************************************************
	 * Construct a new private data object.
	 */
	public Object newElement(Object extra)
	{
		return new GraphicsHComponentData();
	}

	/****************************************************************************
	 * Create a new component.
	 */
	public void create(GraphicsHComponent parent, Component component,
			boolean scrolling) throws LRException
	{
		create(parent, component, null, scrolling);
	}

	/**
	 * Create the component.
	 * 
	 * @param parent
	 * @param component
	 * @param contentPane
	 * @param scrolling
	 * @throws LRException
	 */
	@SuppressWarnings("unchecked")
	public void create(GraphicsHComponent parent, Component component,
			Container contentPane, boolean scrolling) throws LRException
	{
		GraphicsHComponentData myData = getComponentData();
		if (myData != null)
		{
			myData.parent = parent;
			myData.component = component;
			myData.contentPane = contentPane;
			indices.put(component, new Integer(getTheIndex()));
			component.setVisible(false);
			component.addComponentListener(this);
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
			component.addKeyListener(this);
			myData.cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			if (scrolling)
				myData.scroller = new JScrollPane(component);
		}
	}

	/****************************************************************************
	 * Dispose the component.
	 */
	public void dispose() throws LRException
	{
		GraphicsHComponentData myData = getComponentData();
		if (myData != null && myData.component != null)
		{
			removeIndex(myData.component);
			Container parent = myData.component.getParent();
			if (parent != null)
				parent.remove(myData.component);
			myData.component = null;
		}
		super.dispose();
	}

	/****************************************************************************
	 * Get the data object.
	 */
	public GraphicsHComponentData getComponentData() throws LRException
	{
		return (GraphicsHComponentData)getData();
	}

	public GraphicsHComponentData getComponentData(int index) throws LRException
	{
		return (GraphicsHComponentData)getData(index);
	}

	/****************************************************************************
	 * Get the extra data object.
	 */
	public Object getExtra() throws LRException
	{
		return getComponentData().extra;
	}

	public Object getExtra(int index) throws LRException
	{
		return getComponentData(index).extra;
	}

	/****************************************************************************
	 * Set the extra data object.
	 */
	public void setExtra(Object extra) throws LRException
	{
		getComponentData().extra = extra;
	}

	public void setExtra(Object extra, int index) throws LRException
	{
		getComponentData(index).extra = extra;
	}

	/****************************************************************************
	 * Show or hide the component.
	 */
	public void setVisible(boolean visible, LVValue left, LVValue top)
			throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
		{
			if (left != null && top != null)
				c.setLocation(left.getIntegerValue(), top.getIntegerValue());
			c.setVisible(visible);
		}
	}

	/****************************************************************************
	 * Show or hide the component.
	 */
	public void setVisible(boolean visible) throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
			c.setVisible(visible);
	}

	/****************************************************************************
	 * Hide all views of this component.
	 */
	public void hideAll() throws LRException
	{
		for (int n = 0; n < elements; n++)
		{
			Component c = getComponentData(n).component;
			if (c != null)
				c.setVisible(false);
		}
	}

	/****************************************************************************
	 * Focus the component.
	 */
	public void focus() throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
			((JComponent)c).requestFocusInWindow();
	}

	/****************************************************************************
	 * Report if the component exists (has been created).
	 */
	public boolean exists() throws LRException
	{
		return (getComponentData().component != null);
	}

	/****************************************************************************
	 * Report if the component is visible.
	 */
	public boolean isVisible() throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
			return c.isVisible();
		return false;
	}

	/****************************************************************************
	 * Enable or disable the component.
	 */
	public void setEnabled(boolean enable) throws LRException
	{
		setEnabled(enable, true);
	}

	public void setEnabled(boolean enable, boolean doComponent)
			throws LRException
	{
		if (doComponent)
			getComponent().setEnabled(enable);
		getComponentData().enable = enable;
	}

	/****************************************************************************
	 * Get the location of this component.
	 */
	public int getLeft() throws LRException
	{
		Component c = getComponentData().component;
		if (c == null)
			throw new LRException(GraphicsRMessages.nullComponent(getName()));
		return c.getLocation().x;
	}

	public int getTop() throws LRException
	{
		Component c = getComponentData().component;
		if (c == null)
			throw new LRException(GraphicsRMessages.nullComponent(getName()));
		return c.getLocation().y;
	}

	public Point getLocation() throws LRException
	{
		Component c = getComponentData().component;
		if (c == null)
			throw new LRException(GraphicsRMessages.nullComponent(getName()));
		return c.getLocation();
	}

	/****************************************************************************
	 * Move the component.
	 */
	public void moveTo(GraphicsVLocation location) throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
			c.setLocation(location.getLocation());
	}

	public void moveBy(GraphicsVSize size) throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
		{
			Point loc = c.getLocation();
			c.setLocation(loc.x + size.getWidth(), loc.y + size.getHeight());
		}
	}

	public void moveBy(LVValue value, int direction) throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
		{
			Point loc = c.getLocation();
			switch (direction)
			{
				case LEFT:
					c.setLocation(loc.x - value.getIntegerValue(), loc.y);
					break;
				case RIGHT:
					c.setLocation(loc.x + value.getIntegerValue(), loc.y);
					break;
				case UP:
					c.setLocation(loc.x, loc.y - value.getIntegerValue());
					break;
				case DOWN:
					c.setLocation(loc.x, loc.y + value.getIntegerValue());
					break;
			}
		}
	}

	/****************************************************************************
	 * Center the component.
	 */
	public void center(GraphicsVLocation location) throws LRException
	{
		Component c = getComponentData().component;
		if (c != null)
			c.setLocation(location.getLeft() - c.getSize().width / 2, location
					.getTop()
					- c.getSize().height / 2);
	}

	/****************************************************************************
	 * Set the size of the component.
	 */
	public void setSize(GraphicsVSize size) throws LRException
	{
		GraphicsHComponentData myData = getComponentData();
		if (myData.component instanceof JComponent)
		{
			JComponent c = (JComponent)myData.component;
			c.setSize(size.getSize());
			c.setPreferredSize(c.getSize());
		}
		else
		{
			Component c = myData.component;
			c.setSize(size.getSize());
		}
		getComponentData().sizeSpecified = true;
	}

	/****************************************************************************
	 * Set the width of the component.
	 */
	public void setWidth(LVValue width) throws LRException
	{
		Component c = getComponent();
		c.setSize(width.getIntegerValue(), c.getSize().height);
		getComponentData().sizeSpecified = true;
	}

	/****************************************************************************
	 * Set the height of the component.
	 */
	public void setHeight(LVValue height) throws LRException
	{
		Component c = getComponent();
		c.setSize(c.getSize().width, height.getIntegerValue());
		getComponentData().sizeSpecified = true;
	}

	/****************************************************************************
	 * Set the layout manager of the component.
	 */
	public void setLayout(int layout) throws LRException
	{
		Container container = getContentPane();
		switch (layout)
		{
			case NO_LAYOUT:
				container.setLayout(null);
				break;
			case FLOW_LAYOUT:
				container.setLayout(new FlowLayout());
				break;
			case BORDER_LAYOUT:
				container.setLayout(new BorderLayout());
				break;
		}
	}

	/****************************************************************************
	 * Add a component to this component.
	 */
	public void add(GraphicsHComponent component) throws LRException
	{
		add(component, null);
	}

	/****************************************************************************
	 * Add a component to this component.
	 */
	public void add(GraphicsHComponent component, Object constraints)
			throws LRException
	{
		component.getComponentData().parent = this;
		Container contentPane = getContentPane();
		if (contentPane == null)
			throw new LRException(GraphicsRMessages.notContainer(getName()));
		if (constraints == null)
		{
			contentPane.add(component.getVisibleComponent(), 0);
		}
		else
		{
			if (contentPane.getLayout() instanceof BorderLayout)
			{
				contentPane.add(component.getVisibleComponent(), constraints);
			}
		}
		contentPane.validate();
	}

	/****************************************************************************
	 * Get the component that's actually displayed. This might be a scroll pane
	 * containing the actual component, in which case this method will be
	 * overridden.
	 */
	public Component getVisibleComponent() throws LRException
	{
		GraphicsHComponentData myData = getComponentData();
		return (myData.scroller != null) ? myData.scroller : myData.component;
	}

	/****************************************************************************
	 * Clear the component. Override if necessary.
	 */
	// public void clear(LVValue red,LVValue green,LVValue blue) throws LRError
	// {}
	/****************************************************************************
	 * Set the cursor for this component.
	 */
	public void setCursor(GraphicsHCursor cursor) throws LRException
	{
		getComponentData().cursor = cursor.getCursor();
	}

	/****************************************************************************
	 * Set the opacity of this component.
	 */
	public void setOpaque(boolean opaque) throws LRException
	{
		Component component = getComponentData().component;
		if (component instanceof JComponent)
			((JComponent)component).setOpaque(opaque);
	}

	/****************************************************************************
	 * Set the foreground color of this component.
	 */
	public void setForeground(GraphicsVColor color) throws LRException
	{
		Component component = getComponentData().component;
		if (component instanceof JComponent)
			((JComponent)component).setForeground(color.getColor());
	}

	/****************************************************************************
	 * Set the background color of this component.
	 */
	public void setBackground(GraphicsVColor color) throws LRException
	{
		try
		{
			Component component = getComponentData().component;
			if (component instanceof JComponent)
				((JComponent)component).setOpaque(true);
			component.setBackground(color.getColor());
		}
		catch (NullPointerException e)
		{
		}
	}

	/****************************************************************************
	 * Set the background color of this component.
	 */
	public void setBackground(LVValue red, LVValue green, LVValue blue)
			throws LRException
	{
		try
		{
			Color color = new Color(red.getNumericValue(),
					green.getNumericValue(), blue.getNumericValue());
			Component component = getComponentData().component;
			if (component instanceof JComponent)
				((JComponent)component).setOpaque(true);
			component.setBackground(color);
		}
		catch (NullPointerException e)
		{
		}
	}

	/****************************************************************************
	 * Set the status of the component.
	 */
	public void setStatus(LVValue status) throws LRException
	{
		getComponentData().status = status.getIntegerValue();
	}

	/****************************************************************************
	 * Get the status of this component.
	 */
	public int getStatus() throws LRException
	{
		return getComponentData().status;
	}

	/****************************************************************************
	 * Set the description of the component.
	 */
	public void setDescription(LVValue text) throws LRException
	{
		getComponentData().description = text.getStringValue();
	}

	/****************************************************************************
	 * Get the description of this component.
	 */
	public String getDescription() throws LRException
	{
		return getComponentData().description;
	}

	/****************************************************************************
	 * Get the left position of this component.
	 */
	public int getLeft(boolean inset) throws LRException
	{
		Component component = getComponentData().component;
		if (component == null)
			return 0;
		if (inset)
			return ((Container)component).getInsets().left;
		return component.getLocation().x;
	}

	/****************************************************************************
	 * Get the right position of this component.
	 */
	public int getRight(boolean inset) throws LRException
	{
		Component component = getComponentData().component;
		if (component == null)
			return 0;
		if (inset)
			return ((Container)component).getInsets().right;
		return component.getLocation().x + component.getSize().width;
	}

	/****************************************************************************
	 * Get the top position of this component.
	 */
	public int getTop(boolean inset) throws LRException
	{
		Component component = getComponentData().component;
		if (component == null)
			return 0;
		if (inset)
			return ((Container)component).getInsets().top;
		return component.getLocation().y;
	}

	/****************************************************************************
	 * Get the bottom position of this component.
	 */
	public int getBottom(boolean inset) throws LRException
	{
		Component component = getComponentData().component;
		if (component == null)
			return 0;
		if (inset)
			return ((Container)component).getInsets().bottom;
		return component.getLocation().y + component.getSize().height;
	}

	/****************************************************************************
	 * Get the width of this component.
	 */
	public int getWidth()
	{
		Container c = (Container)getComponent();
		if (c == null)
			return 0;
		return c.getSize().width;
	}

	/****************************************************************************
	 * Get the height of this component.
	 */
	public int getHeight()
	{
		Container c = (Container)getComponent();
		if (c == null)
			return 0;
		return c.getSize().height;
	}

	/****************************************************************************
	 * Register a mouse event handler.
	 */
	public void onEvent(int eventType, int cb) throws LRException
	{
		switch (eventType)
		{
			case ACTION:
				onAction(cb);
				break;
			case MOUSE_ENTER:
				onMouseEnter(cb);
				break;
			case MOUSE_EXIT:
				onMouseExit(cb);
				break;
			case MOUSE_CLICK:
				onMouseClick(cb);
				break;
			case MOUSE_DOWN:
				onMouseDown(cb);
				break;
			case MOUSE_UP:
				onMouseUp(cb);
				break;
			case MOUSE_MOVE:
				onMouseMove(cb);
				break;
			case MOUSE_DRAG:
				onMouseDrag(cb);
				break;
			case KEY_PRESSED:
				onKeyPressed(cb);
				break;
			case KEY_RELEASED:
				onKeyReleased(cb);
				break;
			case KEY_TYPED:
				onKeyTyped(cb);
				break;
		}
	}

	/****************************************************************************
	 * Register handlers for mouse and other events.
	 */
	public void onAction(int cb) throws LRException
	{
		getComponentData().onActionCB = new LHCallback(program, cb);
	}

	public void onMouseEnter(int cb) throws LRException
	{
		getComponentData().onMouseEnterCB = new LHCallback(program, cb);
	}

	public void onMouseExit(int cb) throws LRException
	{
		getComponentData().onMouseExitCB = new LHCallback(program, cb);
	}

	public void onMouseClick(int cb) throws LRException
	{
		getComponentData().onMouseClickCB = new LHCallback(program, cb);
	}

	public void onMouseDown(int cb) throws LRException
	{
		getComponentData().onMouseDownCB = new LHCallback(program, cb);
	}

	public void onMouseUp(int cb) throws LRException
	{
		getComponentData().onMouseUpCB = new LHCallback(program, cb);
	}

	public void onMouseMove(int cb) throws LRException
	{
		getComponentData().onMouseMoveCB = new LHCallback(program, cb);
	}

	public void onMouseDrag(int cb) throws LRException
	{
		getComponentData().onMouseDragCB = new LHCallback(program, cb);
	}

	public void onWindowClose(int cb) throws LRException
	{
		getComponentData().onWindowCloseCB = new LHCallback(program, cb);
	}

	public void onWindowResize(int cb) throws LRException
	{
		getComponentData().onWindowResizeCB = new LHCallback(program, cb);
	}

	public void onWindowIconify(int cb) throws LRException
	{
		getComponentData().onWindowIconifyCB = new LHCallback(program, cb);
	}

	public void onWindowDeiconify(int cb) throws LRException
	{
		getComponentData().onWindowDeiconifyCB = new LHCallback(program, cb);
	}

	public void onKeyPressed(int cb) throws LRException
	{
		getComponentData().onKeyPressedCB = new LHCallback(program, cb);
	}

	public void onKeyReleased(int cb) throws LRException
	{
		getComponentData().onKeyReleasedCB = new LHCallback(program, cb);
	}

	public void onKeyTyped(int cb) throws LRException
	{
		getComponentData().onKeyTypedCB = new LHCallback(program, cb);
	}

	/****************************************************************************
	 * Remove this item.
	 */
	protected void removeIndex(Object item)
	{
		if (indices != null && item != null)
			indices.remove(item);
	}

	/****************************************************************************
	 * Find the component that generated an event.
	 */
	public int getIndex(EventObject evt)
	{
		Integer index = (Integer)indices.get(evt.getSource());
		return (index == null) ? -1 : index.intValue();
	}

	/****************************************************************************
	 * Assign a name for event handling.
	 */
	public void setEventName(LVValue name) throws LRException
	{
		getComponentData().eventName = name.getStringValue();
	}

	/****************************************************************************
	 * Retrieve the event name.
	 */
	public String getEventName()
	{
		return eventName;
	}

	/****************************************************************************
	 * Find which component in an array generated the last event.
	 */
	public void findEvent() throws LRException
	{
		Object data = program.getQueueData();
		if (data instanceof LHEvent)
			setTheIndex(((LHEvent)data).getIndex());
	}

	/****************************************************************************
	 * Process an action event on this component.
	 */
	public void actionPerformed(ActionEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onActionCB != null)
					addQueue(myData.onActionCB, new LHEvent(this, evt, index));
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a text event on this component.
	 */
	public void textValueChanged(TextEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onActionCB != null)
					addQueue(myData.onActionCB, new LHEvent(this, evt, index));
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse entered event on this component.
	 */
	public void mouseEntered(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.cursor != null)
					myData.component.setCursor(myData.cursor);
				if (myData.onMouseEnterCB != null)
					addQueue(myData.onMouseEnterCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseEntered(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}

	}

	/****************************************************************************
	 * Process a mouse exited event on this component.
	 */
	public void mouseExited(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseExitCB != null)
					addQueue(myData.onMouseExitCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseExited(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse clicked event on this component. If no callback is
	 * assigned, pass the event up.
	 */
	public void mouseClicked(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseClickCB != null)
					addQueue(myData.onMouseClickCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseClicked(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse pressed event on this component.
	 */
	public void mousePressed(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseDownCB != null)
					addQueue(myData.onMouseDownCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mousePressed(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse released event on this component.
	 */
	public void mouseReleased(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseUpCB != null)
					addQueue(myData.onMouseUpCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseReleased(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse moved event on this component.
	 */
	public void mouseMoved(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseMoveCB != null)
					addQueue(myData.onMouseMoveCB, new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseMoved(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Process a mouse dragged event on this component.
	 */
	public void mouseDragged(MouseEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			if (myData.enable)
			{
				eventName = myData.eventName;
				if (myData.onMouseDragCB != null)
					getProgram().execute(myData.onMouseDragCB.getCallback(),
							new LHEvent(this, evt, index));
				else if (myData.parent != null)
				{
					try
					{
						myData.parent.mouseDragged(evt);
					}
					catch (ArrayIndexOutOfBoundsException ignored)
					{
					}
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * WindowListener interface methods.
	 */
	public void windowActivated(WindowEvent evt)
	{
	}

	public void windowClosed(WindowEvent evt)
	{
	}

	public void windowClosing(WindowEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onWindowCloseCB != null)
				addQueue(myData.onWindowCloseCB, new LHEvent(this, evt, index));
		}
		catch (LRException er)
		{
		}
	}

	public void windowDeactivated(WindowEvent evt)
	{
	}

	public void windowDeiconified(WindowEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onWindowDeiconifyCB != null)
				addQueue(myData.onWindowDeiconifyCB, new LHEvent(this, evt, index));
		}
		catch (LRException er)
		{
		}
	}

	public void windowIconified(WindowEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onWindowIconifyCB != null)
				addQueue(myData.onWindowIconifyCB, new LHEvent(this, evt, index));
		}
		catch (LRException er)
		{
		}
	}

	public void windowOpened(WindowEvent evt)
	{
	}

	/****************************************************************************
	 * ComponentListener interface methods.
	 */
	public void componentHidden(ComponentEvent evt)
	{
	}

	public void componentMoved(ComponentEvent evt)
	{
	}

	public void componentResized(ComponentEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onWindowResizeCB != null)
				addQueue(myData.onWindowResizeCB, new LHEvent(this, evt, index));
		}
		catch (LRException er)
		{
		}
	}

	public void componentShown(ComponentEvent evt)
	{
	}

	/****************************************************************************
	 * KeyListener interface methods.
	 */
	public void keyPressed(KeyEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onKeyPressedCB != null)
				addQueue(myData.onKeyPressedCB, new LHEvent(this, evt, index));
			else if (myData.parent != null)
			{
				try
				{
					myData.parent.keyPressed(evt);
				}
				catch (ArrayIndexOutOfBoundsException ignored)
				{
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	public void keyReleased(KeyEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onKeyReleasedCB != null)
				addQueue(myData.onKeyReleasedCB, new LHEvent(this, evt, index));
			else if (myData.parent != null)
			{
				try
				{
					myData.parent.keyReleased(evt);
				}
				catch (ArrayIndexOutOfBoundsException ignored)
				{
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	public void keyTyped(KeyEvent evt)
	{
		int index = getIndex(evt);
		if (index < 0)
			return;
		try
		{
			GraphicsHComponentData myData = (GraphicsHComponentData)getData(index);
			if (myData == null)
				return;
			eventName = myData.eventName;
			if (myData.onKeyTypedCB != null)
				addQueue(myData.onKeyTypedCB, new LHEvent(this, evt, index));
			else if (myData.parent != null)
			{
				try
				{
					myData.parent.keyTyped(evt);
				}
				catch (ArrayIndexOutOfBoundsException ignored)
				{
				}
			}
		}
		catch (LRException e)
		{
		}
	}

	/****************************************************************************
	 * Return the underlying Component.
	 */
	public Component getComponent()
	{
		try
		{
			return getComponentData().component;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public Component getComponent(int n)
	{
		try
		{
			return ((GraphicsHComponentData)getData(n)).component;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/****************************************************************************
	 * Return the content pane.
	 */
	public Container getContentPane() throws LRException
	{
		try
		{
			return ((GraphicsHComponentData)getData()).contentPane;
		}
		catch (NullPointerException e)
		{
			throw new LRException(e);
		}
	}

	public Container getContentPane(int n) throws LRException
	{
		try
		{
			return ((GraphicsHComponentData)getData(n)).contentPane;
		}
		catch (NullPointerException e)
		{
			throw new LRException(e);
		}
	}

	/****************************************************************************
	 * Return the Frame.
	 */
	public Frame getFrame()
	{
		Component comp = getComponent();
		while (comp != null)
		{
			comp = comp.getParent();
			if (comp instanceof Frame)
				break;
		}
		return (Frame)comp;
	}

	public static final int NO_LAYOUT = 0;
	public static final int FLOW_LAYOUT = 1;
	public static final int BORDER_LAYOUT = 2;

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;

	public static final int MOUSE_ENTER = 0;
	public static final int MOUSE_EXIT = 1;
	public static final int MOUSE_CLICK = 2;
	public static final int MOUSE_DOWN = 3;
	public static final int MOUSE_UP = 4;
	public static final int MOUSE_MOVE = 5;
	public static final int MOUSE_DRAG = 6;
	public static final int ACTION = 7;

	public static final int KEY_PRESSED = 8;
	public static final int KEY_RELEASED = 9;
	public static final int KEY_TYPED = 10;

	public static final int ON_CLOSE = 1;
	public static final int ON_RESIZE = 2;
	public static final int ON_ICONIFY = 3;
	public static final int ON_DEICONIFY = 4;
}
