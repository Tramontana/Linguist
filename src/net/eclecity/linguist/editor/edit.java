// edit.java

package net.eclecity.linguist.editor;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import net.eclecity.linguist.handler.LHSystemOut;
import net.eclecity.linguist.main.LLCompiler;
import net.eclecity.linguist.main.LLMain;


/******************************************************************************
	A simple script editor, mainly for Zaurus.
*/
public class edit extends JFrame implements LHSystemOut
{
	private Properties properties;
	private CardLayout cardLayout=new CardLayout();
	private JPanel mainPanel=new JPanel(cardLayout);
	private Vector cards=new Vector();
	private Vector names=new Vector();
	private int currentCard;
	private JRadioButtonMenuItem[] windows= new JRadioButtonMenuItem[10];

	private Action actionNewScript;
	private Action actionOpenScript;
	private Action actionCloseScript;
	private Action actionSaveScript;
	private Action actionExit;
	private Action actionCut;
	private Action actionCopy;
	private Action actionPaste;
	private Action actionCompile;
	private Action actionCompileRun;
	private Action actionSystemCommand;
	private Action actionAbout;

	/***************************************************************************
		Constructor.
	*/
	public edit()
	{
		super("Script Editor");
		setResizable(false);
		File file=new File("editor.props");
		if (file.exists())
		{
			try
			{
				ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
				properties=(Properties)in.readObject();
				in.close();
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
			catch (ClassNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
		}
		else properties=new Properties();
		setBounds(0,0,240,280);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				System.exit(0);
			}
		});
		doActions();
		doMenus();
		getContentPane().add(mainPanel);
		for (int n=0; n<10; n++)
		{
			String name=(n==0)?"console":""+n;
			names.addElement(name);
			LEditPane editPane=new LEditPane(this);
			cards.addElement(editPane);
			mainPanel.add(name,editPane.getScroller());
		}
		validate();
		setVisible(true);

		cardLayout.show(mainPanel,"1");
		currentCard=1;
	}

	/***************************************************************************
		Create the actions.
	*/
	private void doActions()
	{
		actionNewScript=new AbstractAction("New script")
		{
			public void actionPerformed(ActionEvent evt)
			{
				((LEditPane)cards.elementAt(currentCard)).newScript();
			}
		};
		actionOpenScript=new AbstractAction("Open script...")
		{
			public void actionPerformed(ActionEvent evt)
			{
				((LEditPane)cards.elementAt(currentCard)).openScript();
			}
		};
		actionCloseScript=new AbstractAction("Close script")
		{
			public void actionPerformed(ActionEvent evt)
			{
				((LEditPane)cards.elementAt(currentCard)).closeScript();
			}
		};
		actionSaveScript=new AbstractAction("Save script")
		{
			public void actionPerformed(ActionEvent evt)
			{
				((LEditPane)cards.elementAt(currentCard)).saveScript();
			}
		};
		actionExit=new AbstractAction("Exit")
		{
			public void actionPerformed(ActionEvent evt)
			{
				doExit();
			}
		};
		actionCut=new AbstractAction("Cut")
		{
			public void actionPerformed(ActionEvent evt)
			{
			}
		};
		actionCopy=new AbstractAction("Copy")
		{
			public void actionPerformed(ActionEvent evt)
			{
			}
		};
		actionPaste=new AbstractAction("Paste")
		{
			public void actionPerformed(ActionEvent evt)
			{
			}
		};
		actionCompile=new AbstractAction("Compile script")
		{
			public void actionPerformed(ActionEvent evt)
			{
				final LEditPane editPane=(LEditPane)cards.elementAt(currentCard);
				currentCard=0;
				cardLayout.show(mainPanel,"console");
				windows[0].setSelected(true);
				editPane.saveScript();
				new Thread(new Runnable()
				{
					public void run()
					{
						new LLMain(new String[]{editPane.getFileName(),"-o"},
							false,edit.this,LLCompiler.compiledExtension);
					}
				}).start();
			}
		};
		actionCompileRun=new AbstractAction("Compile and Run")
		{
			public void actionPerformed(ActionEvent evt)
			{
				final LEditPane editPane=(LEditPane)cards.elementAt(currentCard);
				currentCard=0;
				cardLayout.show(mainPanel,"console");
				windows[0].setSelected(true);
				editPane.saveScript();
				new Thread(new Runnable()
				{
					public void run()
					{
						new LLMain(new String[]{editPane.getFileName(),"-r"},
							false,edit.this,LLCompiler.compiledExtension);
					}
				}).start();
			}
		};
		actionSystemCommand=new AbstractAction("System Command")
		{
			public void actionPerformed(ActionEvent evt)
			{
				final LEditPane editPane=(LEditPane)cards.elementAt(currentCard);
				currentCard=0;
				cardLayout.show(mainPanel,"console");
				windows[0].setSelected(true);
				new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							LEditPane console=(LEditPane)cards.elementAt(0);
							Process command=Runtime.getRuntime().exec(editPane.getCurrentLine());
							BufferedReader br=new BufferedReader(new InputStreamReader(command.getInputStream()));
							String s;
							while ((s=br.readLine())!=null)
							{
								s="\n"+s;
								int currentPos=console.getCaretPosition();
								console.insert(s,currentPos);
								console.setCaretPosition(currentPos+s.length());
							}
							br.close();
						}
						catch (IOException e) { e.printStackTrace(); }
					}
				}).start();
			}
		};
		actionAbout=new AbstractAction("About")
		{
			public void actionPerformed(ActionEvent evt)
			{
				JOptionPane.showMessageDialog(edit.this,"Linguist Script Editor.\n"
					+"Revision 0.5\n\n�2002\nTechModern Limited");
			}
		};
	}

	/***************************************************************************
		Create the menus.
	*/
	private void doMenus()
	{
		JMenuBar menuBar=new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu=new JMenu("File");
		menuBar.add(menu);
		menu.add(actionNewScript);
		menu.add(actionOpenScript);
		menu.add(actionCloseScript);
		menu.addSeparator();
		menu.add(actionSaveScript);
		menu.addSeparator();
		menu.add(actionExit);

		menu=new JMenu("Edit");
		menuBar.add(menu);
		menu.add(actionCut);
		menu.add(actionCopy);
		menu.add(actionPaste);

		menu=new JMenu("Window");
		menuBar.add(menu);
		ButtonGroup bg=new ButtonGroup();
		for (int n=0; n<10; n++)
		{
			windows[n]=new JRadioButtonMenuItem((n==0)?"console":""+n);
			if (n==1) windows[n].setSelected(true);
			bg.add(windows[n]);
			menu.add(windows[n]);
			windows[n].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent evt)
				{
					String name=evt.getActionCommand();
					cardLayout.show(mainPanel,name);
					currentCard=names.indexOf(name);
				}
			});
		}

		menu=new JMenu("Command");
		menuBar.add(menu);
		menu.add(actionCompile);
		menu.add(actionCompileRun);
		menu.addSeparator();
		menu.add(actionSystemCommand);

		menu=new JMenu("Help");
		menuBar.add(menu);
		menu.add(actionAbout);
	}

	/***************************************************************************
		Put a value into the properties file.
	*/
	public void putParam(String key,String value)
	{
		properties.setProperty(key,value);
	}

	/***************************************************************************
		Get a parameter from the properties file.
	*/
	public String getParam(String key)
	{
		return properties.getProperty(key);
	}

	/***************************************************************************
		Get a parameter from the properties file.
	*/
	public String getParam(String key,String defval)
	{
		return properties.getProperty(key,defval);
	}

	/***************************************************************************
		Exit.
	*/
	private void doExit()
	{
//		if (!editor.closeScript()) return;
		try
		{
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("editor.props"));
			out.writeObject(properties);
			out.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		dispose();
		System.exit(0);
	}

	/***************************************************************************
		Implement the LHSystemOut interface.
	*/
	public void systemOut(String message)
	{
//		System.out.println(message);
		((LEditPane)cards.elementAt(0)).append(message+"\n");
	}

	/***************************************************************************
		Main program.
	*/
	public static void main(String[] args)
	{
		new edit();
	}
}
