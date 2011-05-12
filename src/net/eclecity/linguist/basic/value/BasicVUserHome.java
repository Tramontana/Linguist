// BasicVUserHome.java

// =============================================================================
// Linguist Script Compiler, Debugger and Runtime
// Copyleft (C) 1999 Graham Trott
//
// Part of Linguist; see LS.java for a full copyleft notice.
// =============================================================================

package net.eclecity.linguist.basic.value;

import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * Get the name of the user home directory.
 */
public class BasicVUserHome extends LVValue
{
  public BasicVUserHome()
  {
  }

  public long getNumericValue()
  {
    return 0;
  }

  public String getStringValue()
  {
    String s = System.getProperty("user.home");
    if (!s.endsWith(System.getProperty("file.separator")))
      s += "/";
    return s;
  }
}
