// LRProgram.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.runtime;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.handler.LHVariableHandler;
import net.eclecity.linguist.main.LLScript;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * The program and its execution methods.
 */
public class LRProgram extends Vector implements Runnable, Serializable
{
  private static Hashtable<String, LRProgram> moduleListByName = new Hashtable<String, LRProgram>();

  private String pathName = ""; // the path to the program module
  private String scriptName; // the name of the program
  private String[] packages; // the packages used by this language
  private LLScript[] script; // the script text file
  private Object param; // an arbitrary parameter
  private LRProgram parent; // my parent program, or null
  private boolean running; // stop when this is false
  private boolean executing; // true if a thread is running
  private Object queueData; // data passed to the queue
  private LRReg reg = null; // the registration object
  private Vector<LRProgram> scripts; // all the extra known threads
  private boolean exitEnable; // if true, call System.exit() at end
  private boolean trace; // if true, show each line of execution
  private int nextPC = 0;
  private LHSystemOut systemOut; // where system output goes, if not null
  private Thread myThread;
  private Hashtable<String, LRBackground> backgrounds; // the backgrounds used
  // by this script
  private Hashtable<Integer, Object> objectData; // user data associated with
                                                // this program.
  private LVValue returnValue; // if execute() needs to return a value
  private Object backgroundData; // data for one or more of the backgrounds
  private transient long timestamp; // the time this module was launched

  private static Hashtable<String, LRBackground> allBackgrounds = new Hashtable<String, LRBackground>();

  /*****************************************************************************
   * Constructor.
   */
  public LRProgram()
  {
  }

  /*****************************************************************************
   * Initialize this program.
   */
  public void init(String name, String[] packages)
  {
    this.init(null, name, null, packages, null, false, 0, null);
  }

  /*****************************************************************************
   * Initialize this program.
   */
  public void init(LRProgram parent, String name, LLScript[] script,
      String[] packages, Object param, boolean exitEnable, int nextPC,
      LHSystemOut systemOut)
  {
    timestamp = System.currentTimeMillis();
    this.parent = parent;
    scriptName = name;
    int n = name.lastIndexOf("/");
    if (n > 0) pathName = name.substring(0, n + 1);
    this.packages = packages;
    this.script = script;
    this.param = param;
    this.exitEnable = exitEnable;
    this.nextPC = nextPC;
    this.systemOut = systemOut;
    reg = new LRReg(this);
    scripts = new Vector<LRProgram>();
    moduleListByName.put(name, this);
    setEnvironment();
    for (n = 0; n < size(); n++)
      getHandler(n).setEnvironment(this, n);
    if (parent == null)
    {
      backgrounds = new Hashtable<String, LRBackground>();
      if (!startBackgrounds()) return;
    }
    else backgrounds = parent.getBackgrounds();
    objectData = new Hashtable<Integer, Object>();
    queue = new Vector<LRQueue>();
    queueData = null;
    (myThread = new Thread(this, name)).start();
  }

  public void setParam(Object param)
  {
    this.param = param;
  }

  public Object getParam()
  {
    return param;
  }

  public Hashtable<Integer, Object> getObjectData()
  {
    return objectData;
  }

  public long getTimestamp()
  {
    return timestamp;
  }

  /*****************************************************************************
   * Set the environment. This means going through each of the handlers in our
   * table and setting its index. This is done after compilation, prior to
   * running the program.
   */
  private void setEnvironment()
  {
    for (int n = 0; n < size(); n++)
      getHandler(n).setEnvironment(this, n);
  }

  /*****************************************************************************
   * Run this LRProgram. First wait for all the backgrounds to finish
   * initializing. Then launch the script. After that the thread has done its
   * job and will stop.
   */
  public void run()
  {
    while (!myThread.isInterrupted())
    {
      // wait for the backgrounds to be ready
      boolean flag = true;
      Enumeration en = backgrounds.elements();
      while (en.hasMoreElements())
      {
        if (!((LRBackground)en.nextElement()).isReady())
        {
          flag = false;
          break;
        }
      }
      if (flag)
      {
        addQueue(0); // start the script
        myThread = null;
        break;
      }
      try
      {
        Thread.sleep(10);
      }
      catch (InterruptedException e)
      {
      }
    }
    myThread = null;
  }

  /*****************************************************************************
   * Stop this program.
   */
  public void stop()
  {
    running = false;
  }

  /*****************************************************************************
   * Specify a parameter for one or more of the backgrounds. It's up to the
   * backgrounds to decide if the parameter is for them.
   */
  public void setBackgroundData(Object data)
  {
    backgroundData = data;
  }

  /*****************************************************************************
   * Start the background for each of the packages.
   */
  private boolean startBackgrounds()
  {
    String className = "";
    try
    {
      for (int n = 0; n < packages.length; n++)
      {
        Class c = null;
        try
        {
          String moduleName = packages[n].substring(0, 1).toUpperCase()
              + packages[n].substring(1).toLowerCase() + "RBackground";
          className = "net.eclecity.linguist." + packages[n] + ".runtime."
              + moduleName;
          c = Class.forName(className);
          if (c != null)
          {
            try
            {
              LRBackground background = allBackgrounds.get(moduleName);
              // If this background is not already running, start it.
              if (background == null)
              {
                System.out.println(scriptName + ": Starting " + moduleName);
                background = (LRBackground)c.newInstance();
                allBackgrounds.put(moduleName, background);
                background.init(this, moduleName, backgroundData);
              }
              // Add it to the local table
              backgrounds.put(packages[n] + "." + scriptName, background);
            }
            catch (InstantiationException e)
            {
              classError(c);
            }
            catch (IllegalAccessException e)
            {
              classError(c);
            }
          }
        }
        catch (ClassNotFoundException e)
        {
        }
      }
    }
    catch (LRException e)
    {
      System.out.println(LRException.cantStartBackground(className) + ": "
          + e.getMessage());
      exit(1);
      return false;
    }
    return true;
  }

  /*****************************************************************************
   * Finish all the backgrounds.
   */
  private void finishBackgrounds()
  {
    if (backgrounds != null)
    {
      for (Enumeration e = backgrounds.elements(); e.hasMoreElements();)
      {
        LRBackground background = (LRBackground)e.nextElement();
        String name = background.getName();
        if (allBackgrounds.containsKey(name))
        {
          background.finish();
          allBackgrounds.remove(name);
        }
      }
      backgrounds = null;
    }
  }

  /*****************************************************************************
   * Return the table of backgrounds.
   */
  public Hashtable<String, LRBackground> getBackgrounds()
  {
    return backgrounds;
  }

  /*****************************************************************************
   * Return a background from the table. First find our ultimate parent, who
   * owns the background.
   */
  public LRBackground getBackground(String packageName) throws LRException
  {
    try
    {
      LRProgram p = this;
      while (p.parent != null)
        p = p.getParent();
      return backgrounds.get(packageName + "." + p.getScriptName());
    }
    catch (NullPointerException e)
    {
      throw new LRException(LRException.backgroundNotStarted(packageName));
    }
  }

  private void classError(Class c) throws LRException
  {
    throw new LRException(LRException.cantInstantiate(c.getName()));
  }

  public boolean isExecuting()
  {
    return executing;
  }

  public void register(String name, int number)
  {
    reg.register(name, number);
  }

  public void setReturnValue(LVValue value)
  {
    this.returnValue = value;
  }

  private Vector<LRQueue> queue; // the execution queue

  public void addQueue(int pc)
  {
    addQueue(pc, null);
  }

  public void addQueue(int pc, Object data)
  {
    queue.addElement(new LRQueue(pc, data));
    if (!executing)
    {
      executing = true; // this is MOST important!
      new ExecThread(this);
    }
  }

  /*****************************************************************************
   * Turn the tracer on or off.
   */
  public void setTrace(boolean trace)
  {
    this.trace = trace;
  }

  /*****************************************************************************
   * The main execution entry point.
   */
  public void execute(Thread thread)
  {
    running = executing = true;
    while (running && !queue.isEmpty())
    {
      LRQueue q = queue.firstElement();
      queue.removeElementAt(0);
      execute(q);
    }
    executing = false;
    if (!running) terminate();
    thread.interrupt();
  }

  /*****************************************************************************
   * Execute from a given address.
   */
  public LVValue execute(int where)
  {
    return execute(where, null);
  }

  public LVValue execute(int where, Object data)
  {
    return execute(new LRQueue(where, data));
  }

  public LVValue execute(LRQueue q)
  {
    //		System.out.println("Execute in "+getScriptName());
    returnValue = null;
    int pc = 0;
    try
    {
      pc = q.getPC();
      Object qd = q.getData();
      while (running && pc < size())
      {
        //				System.out.println(getScriptName()+" "+pc+"
        // "+getHandler(pc).getClass().getName());
        if (trace)
        {
          int line = getHandler(pc).getLine();
          System.out.println(getScriptName() + "', line "
              + (script[line].line + 1) + ":   " + script[line].text);
        }
        queueData = qd;
        pc = getHandler(pc).execute();
        if (pc == 0) break;
      }
    }
    catch (LRException e)
    {
      int line = getHandler(pc).getLine();
      System.out.println(LRException.error() + scriptName + "', line "
          + (script[line].line + 1) + ":\n" + script[line].text + "\n"
          + e.getMessage() + "\n");
      exit(1);
    }
    catch (Exception e)
    {
      try
      {
        int line = getHandler(pc).getLine();
        System.out.println(LRException.error() + scriptName + "', line "
            + (script[line].line + 1) + ":\n" + script[line].text + "\n");
      }
      catch (Exception ignored)
      {
      }
      e.printStackTrace();
      exit(1);
    }
    return returnValue;
  }

  /*****************************************************************************
   * Terminate this LRProgram.
   */
  public void terminate()
  {
    if (scripts != null) for (int n = 0; n < scripts.size(); n++)
      (scripts.elementAt(n)).stop();
    cancelTimers();
    finishBackgrounds();
    if (reg != null) reg.stop();
    exit(0);
  }

  /*****************************************************************************
   * Exit. Where we go depends on how we were called. In some cases it might be
   * back to the operating system, or we may simply stop and let someone else
   * take over.
   */
  private void exit(int code)
  {
    if (exitHandler != 0)
    {
      int n = exitHandler;
      exitHandler = 0; // prevent another exit
      exitListener.execute(new LRQueue(n, null));
    }
    for (int n = 0; n < size(); n++)
    {
      LHHandler h = (LHHandler)elementAt(n);
      if (h instanceof LHVariableHandler)
      {
        LHVariableHandler lh = (LHVariableHandler)h;
        lh.disposeAll();
      }
    }
    nullEverything();
    running = false;
    if (parent != null)
      parent.removeScript(this);
    else if (exitEnable) System.exit(code);
  }

  private void nullEverything()
  {
    pathName = null;
    scriptName = null;
    packages = null;
    script = null;
    param = null;
    queueData = null;
    reg = null;
    scripts = null;
    systemOut = null;
    myThread = null;
    backgrounds = null;
    setSize(0);
  }

  private LRProgram exitListener = null;
  private int exitHandler = 0;

  public void setExitHandler(LRProgram ls, int pc)
  {
    exitListener = ls;
    exitHandler = pc;
  }

  /*****************************************************************************
   * Output to the Vector that holds console output.
   */
  public LHSystemOut getSystemOut()
  {
    return systemOut;
  }

  public void systemOut(String s)
  {
    systemOut.systemOut(s);
  }

  private Stack<Object> userStack = new Stack<Object>();

  public void push(Object v)
  {
    userStack.addElement(v);
  }

  public Object pop()
  {
    return userStack.pop();
  }

  private Stack<Integer> programStack = new Stack<Integer>();

  public void pushPC(int pc)
  {
    programStack.addElement(new Integer(pc));
  }

  public int popPC() throws LRException
  {
    try
    {
      return (programStack.pop()).intValue();
    }
    catch (EmptyStackException e)
    {
      throw new LRException(LRException.stackUnderflow());
    }
  }

  private Set<LRTimer> timers = new HashSet<LRTimer>();

  public void addTimer(int count, int where)
  {
    addTimer(count, where, false);
  }

  public void addTimer(int count, int where, boolean continuous)
  {
    timers.add(new LRTimer(this, count, where, continuous));
  }

  public void removeTimer(LRTimer timer)
  {
    timers.remove(timer);
  }

  public void cancelTimer(LVValue id)
  {
  }

  public void cancelTimers()
  {
    for (LRTimer timer : timers)
      if (timer != null) timer.stop();
    timers = new HashSet<LRTimer>();
  }

  /*****************************************************************************
   * Get the queue data. If a particular class is requested, go back through the
   * hierarchy.
   */
  public Object getQueueData()
  {
    return queueData;
  }

  public Object getQueueData(Class c)
  {
    Object qd = queueData;
    if (qd == null) return null;
    if (qd.getClass().equals(c)) return qd;
    if (qd instanceof LRMessage) return ((LRMessage)qd).caller.getQueueData(c);
    return null;
  }

  /*****************************************************************************
   * Handle a message to this program module.
   */
  private int messageHandler = 0;

  public void onMessage(LRProgram caller, String message)
  {
    if (messageHandler != 0)
      execute(new LRQueue(messageHandler, new LRMessage(message, caller)));
  }

  public void setMessageHandler(int where)
  {
    messageHandler = where;
  }

  private int externalEventHandler = 0;
  private Object externalEventReturnValue = null;

  /*****************************************************************************
   * Handle an event sent from outside Linguist.
   */
  public Object handleExternalEvent(Object event)
  {
    return handleExternalEvent(event, false);
  }

  public Object handleExternalEvent(Object event, boolean newThread)
  {
    if (externalEventHandler != 0)
    {
      if (newThread)
        addQueue(externalEventHandler, event);
      else
      {
        execute(new LRQueue(externalEventHandler, event));
        if (!running) terminate();
        return externalEventReturnValue;
      }
    }
    return null;
  }

  public void setExternalEventHandler(int where)
  {
    externalEventHandler = where;
  }

  public void setExternalEventReturnValue(Object value)
  {
    externalEventReturnValue = value;
  }

  /*****************************************************************************
   * Look after data for whoever needs it, on a per-module basis.
   */
  private Map<String, Object> userData = new HashMap<String, Object>();

  public void putUserData(String key, Object data)
  {
    userData.put(key, data);
  }

  public Object getUserData(String key)
  {
    return userData.get(key);
  }

  /*****************************************************************************
   * Run a new script.
   */
  public void newScript(LRProgram p, String name, LLScript[] script,
      String[] packages, int nextPC)
  {
    p.init(this, name, script, packages, param, exitEnable, nextPC, systemOut);
    scripts.addElement(p);
  }

  /*****************************************************************************
   * Run a new script.
   */
  public void releaseParent()
  {
    // Allow my parent to continue.
    if (nextPC != 0)
    {
      parent.addQueue(nextPC);
      nextPC = 0;
    }
  }

  /*****************************************************************************
   * Remove a script.
   */
  protected void removeScript(LRProgram p)
  {
    if (scripts != null && p != null) scripts.removeElement(p);
  }

  /*****************************************************************************
   * Get a variable from the current script.
   */
  public LHVariableHandler getVariable(String name)
  {
    for (int n = 0; n < size(); n++)
    {
      LHHandler h = (LHHandler)elementAt(n);
      if (h instanceof LHVariableHandler)
      {
        if (((LHVariableHandler)h).getName().equals(name))
          return (LHVariableHandler)h;
      }
    }
    return null; // not found
  }

  public LHHandler getHandler(int pc)
  {
    return (LHHandler)elementAt(pc);
  }

  public String getLine(int n)
  {
    return script[n].text;
  }

  public String getPathName()
  {
    return pathName;
  }

  public String getScriptName()
  {
    return scriptName;
  }

  public LRProgram getParent()
  {
    return parent;
  }

  public LRProgram getModule(String name)
  {
    return moduleListByName.get(name);
  }

  public boolean getExitEnable()
  {
    return exitEnable;
  }

  public boolean isRunning()
  {
    return running;
  }

  /*****************************************************************************
   * The class that starts a new execution thread.
   */
  final class ExecThread implements Runnable, Serializable
  {
    private LRProgram program;
    private Thread myThread;

    ExecThread()
    {
    }

    ExecThread(LRProgram p)
    {
      program = p;
      (myThread = new Thread(this, "Execute")).start();
    }

    public void run()
    {
      program.execute(myThread);
    }
  }

  /*****************************************************************************
   * A timer.
   */
  final class LRTimer implements Runnable, Serializable
  {
    private LRProgram program;
    private int count;
    private int where;
    private boolean continuous;
    private Thread myThread;

    LRTimer()
    {
    }

    LRTimer(LRProgram program, int count, int where, boolean continuous)
    {
      this.program = program;
      this.count = count;
      this.where = where;
      this.continuous = continuous;
      (myThread = new Thread(this, "Timer")).start();
    }

    public synchronized void run()
    {
      try
      {
        wait(count);
        program.removeTimer(this);
        if (continuous) new LRTimer(program, count, where, true);
        program.addQueue(where);
      }
      catch (InterruptedException e)
      {
      }
    }

    public void stop()
    {
      myThread.interrupt();
      program.removeTimer(this);
    }

    boolean isContinuous()
    {
      return continuous;
    }
  }

  /*****************************************************************************
   * This is a helper class that handles registration. Registration enables
   * Linguist to be freely distributed; the catch is that if a valid
   * registration number is not presented via this class, the program will only
   * run for ten minutes. This is long enough for most development work but will
   * prevent commercial distribution.
   * 
   * This class is final and has no useful methods. And yes, I was born in 1948.
   */
  final class LRReg implements Runnable
  {
    private String registeredName = "";
    private int registeredNo = 0;
    private boolean registered = false;
    private LRProgram program;
    private Thread myThread = null;

    LRReg()
    {
    }

    LRReg(LRProgram program)
    {
      this.program = program;
      registered = false;
      try
      {
        LRReg2 reg2 = new LRReg2();
        register(reg2.regName, reg2.regNo);
      }
      catch (NoClassDefFoundError e)
      {
      }
      if (!registered)
        (myThread = new Thread(this, program.getScriptName() + "Reg")).start();
    }

    public void run()
    {
      try
      {
        Thread.sleep(10 * 60 * 1000);
      }
      catch (InterruptedException e)
      {
      }
      if (!registered)
      {
        System.out.println(LRException.timeout(program.getScriptName()));
        program.stop();
      }
    }

    public void stop()
    {
      if (myThread != null)
      {
        myThread.interrupt();
        myThread = null;
      }
    }

    /***************************************************************************
     * Register this module.
     */
    boolean register(String name, int number)
    {
      int sum = 0;
      for (int n = 0; n < name.length(); n++)
      {
        sum += (name.charAt(n) * (n + 1));
        if (sum > 999999) sum -= 999999;
      }
      sum *= 1948;
      sum += 8491;
      registered = (sum == number);
      if (registered)
      {
        registeredName = name;
        registeredNo = number;
      }
      return registered;
    }

    public String getRegistrationName()
    {
      return registeredName;
    }

    public int getRegistrationNumber()
    {
      return registeredNo;
    }
  }

  /*****************************************************************************
   * If this class file is present and correct we don't need to register.
   */
  class LRReg2
  {
    public String regName = "Linguist";
    public int regNo = 7753739;

    LRReg2()
    {
    }
  }
}
