// LHelp.java

package net.eclecity.linguist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/*******************************************************************************
 * Linguist Help. This is the Linguist Help system. All help files are HTML and
 * are normally read from the Linguist JAR file.
 */
public class LHelp implements Runnable
{
  private String keyword;
  private JFrame frame;
  private Properties properties;
  private int left, top, width, height;

  private static final String HOME = System.getProperty("user.home")
      + "/.linguist";
  private static final String PROPS = HOME + "/lhelp.props";

  /*****************************************************************************
   * Constructor.
   * 
   * @param keyword the keyword to look up. If this is empty show the main index
   *        page.
   */
  public LHelp(String keyword)
  {
    this.keyword = keyword;
    new File(HOME).mkdirs();
    File file = new File(PROPS);
    if (file.exists())
    {
      try
      {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        properties = (Properties)in.readObject();
        in.close();
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else properties = new Properties();
    left = getIntParam("left", 100);
    top = getIntParam("top", 100);
    width = getIntParam("width", 500);
    height = getIntParam("height", 300);
    new Thread(this).start();
  }

  /*****************************************************************************
   * Run the help screen in its own thread. If we were called with a keyword,
   * look in all the help files to see how many occurrences of it there are.
   */
  public void run()
  {
    Vector<String> v = new Vector<String>();
    if (keyword.length() > 0) getResources(v, "Reference.html", keyword);
    int size = v.size();
    // @TODO: Implement context-sensitive help.
    switch (size)
    {
      case 0:
        browse("Reference.html");
        break;
      case 1:
        browse(v.elementAt(0) + "/" + keyword + ".html");
        break;
      default:
        List list = new List();
        list.setBackground(new Color(255, 251, 240));
        for (int n = 0; n < size; n++)
        {
          list.add(v.elementAt(n) + "." + keyword);
        }
        frame = new JFrame("Uses of '" + keyword + "'");
        JLabel label = new JLabel("Select which package you are interested in",
            JLabel.CENTER);
        label.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(list, BorderLayout.CENTER);
        frame.getContentPane().add(label, BorderLayout.SOUTH);
        frame.pack();
        Dimension screenSize = frame.getToolkit().getScreenSize();
        Dimension windowSize = frame.getSize();
        frame.setLocation((screenSize.width - windowSize.width) / 2,
            (screenSize.height - windowSize.height) / 2);
        frame.setVisible(true);
        list.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent evt)
          {
            if (evt.getStateChange() != ItemEvent.SELECTED) return;
            List target = (List)evt.getItemSelectable();
            String item = target.getSelectedItem();
            int n = item.indexOf(".");
            item = item.substring(0, n) + "/" + item.substring(n + 1) + ".html";
            browse(item);
          }

        });
        try
        {
          Thread.sleep(20000);
          disposeWindow();
        }
        catch (InterruptedException e)
        {
          disposeWindow();
        }
        break;
    }
  }

  /*****************************************************************************
   * Get all the resources of a given name.
   */
  private void getResources(Vector<String> v, String path, String name)
  {
    System.out.println("getResources: " + path);
    URL url = getHTML(path);
    StringBuffer sb = new StringBuffer();
    String s;
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(
          url.openStream()));
      while ((s = br.readLine()) != null)
        sb.append(s);
    }
    catch (IOException e)
    {
      System.out.println("URL not found for " + path);
    }
    s = sb.toString();
    int index = 0;
    while ((index = s.toUpperCase().indexOf("A HREF=\"", index) + 8) > 7)
    {
      int index2 = s.indexOf("\"", index);
      String link = s.substring(index, index2);
      int slash = link.lastIndexOf("/");
      int dot = link.indexOf(".");
      if (dot > 0)
      {
        String test = link.substring(slash + 1, dot);
        if (test.equals(name.substring(name.lastIndexOf("/") + 1)))
        {
          link = path.substring(0, path.lastIndexOf("/"));
          if (!v.contains(link)) v.add(link);
        }
        if (slash >= 0) getResources(v, link, name);
      }
      index = index2;
    }
  }

  /*****************************************************************************
   * Dispose of the Help window.
   */
  private void disposeWindow()
  {
    if (frame != null) frame.dispose();
  }

  /*****************************************************************************
   * Show a help file in the built-in browser.
   */
  private void browse(String item)
  {
    disposeWindow();
    final JFrame frame = new JFrame("Linguist Help");
    frame.getContentPane().add(new Help(item));
    frame.setBounds(left, top, width, height);
    frame.setVisible(true);
    frame.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent evt)
      {
        putParam("left", "" + frame.getX());
        putParam("top", "" + frame.getY());
        putParam("width", "" + frame.getWidth());
        putParam("height", "" + frame.getHeight());
        try
        {
          ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
              PROPS));
          out.writeObject(properties);
          out.close();
        }
        catch (FileNotFoundException e)
        {
          e.printStackTrace();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        frame.dispose();
        System.exit(0);
      }
    });
  }

  /*****************************************************************************
   * Put a parameter into the config file.
   */
  public void putParam(String key, String value)
  {
    properties.setProperty(key, value);
  }

  /*****************************************************************************
   * Get an integer parameter from the properties file.
   */
  public int getIntParam(String s, int def)
  {
    try
    {
      return Integer.parseInt(properties.getProperty(s));
    }
    catch (Exception e)
    {
    }
    return def;
  }

  /*****************************************************************************
   * Main program (if needed).
   */
  public static void main(String[] args)
  {
    if (args.length < 1)
      new LHelp("");
    else new LHelp(args[0]);
  }

  /*****************************************************************************
   * Get a help file. First look in the resources, then in the local filing
   * system. (This is to permit development without having to keep resource JARs
   * up to date. In final deployment the images will always come from the
   * resource JAR.)
   * 
   * @param fileName the name of the file.
   * @return the URL of the file.
   */
  private URL getHTML(String fileName)
  {
    URL url = LHelp.class.getResource("/help/" + fileName);
    if (url == null)
      try
      {
        url = new URL("file://" + System.getProperty("user.dir") + "/help/"
            + fileName);
      }
      catch (MalformedURLException e)
      {
        e.printStackTrace();
        return null;
      }
    return url;
  }

  /*****************************************************************************
   * An HTML Help panel
   */
  class Help extends JScrollPane
  {
    private JEditorPane area;
    private String previous;

    public Help(String name)
    {
      super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
      area = new JEditorPane();
      setViewportView(area);
      area.setEditable(false);
      area.addHyperlinkListener(new HyperlinkListener()
      {
        public void hyperlinkUpdate(HyperlinkEvent event)
        {
          if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
          {
            String target = event.getDescription();
            String prev = previous;
            int index = prev.lastIndexOf("/");
            if (index >= 0)
              prev = prev.substring(0, index);
            else prev = "";
            while (target.startsWith("../"))
            {
              index = prev.lastIndexOf("/");
              if (index >= 0)
                prev = prev.substring(0, index);
              else prev = "";
              target = target.substring(3);
            }
            if (prev.length() > 0 && !prev.endsWith("/")) prev += "/";
            setContent(prev + target);
          }
        }
      });
      setContent(name);
    }

    /***************************************************************************
     * Set the browser to the contents of a given help file.
     * 
     * @param name the name of the help file.
     */
    private void setContent(String name)
    {
      previous = name;
      URL url = getHTML(name);
      System.out.println(url.toString());
      setPage(url);
    }

    /***************************************************************************
     * Set the browser to the contents of a given URL.
     * 
     * @param url the URL of the page.
     */
    private void setPage(URL url)
    {
      try
      {
        area.setPage(url);
      }
      catch (Exception e)
      {
        area.setContentType("text/plain");
        area.setText("Error loading page.\n" + e);
      }
    }
  }
}