// LFileChooser.java

package net.eclecity.linguist.editor;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/******************************************************************************
	The file chooser.
*/
public class LFileChooser extends JDialog
{
	private String value;

	/***************************************************************************
		Constructor.
	*/
	public LFileChooser(Frame frame,String title)
	{
		super(frame,title,true);
		Container contentPane=getContentPane();
		setBounds(0,0,240,280);
		setResizable(false);
		File file=new File(".");
		String[] list=file.list();
		Vector<String> files=new Vector<String>();
		int n;
		for (n=0; n<list.length; n++)
		{
			File f=new File(list[n]);
			if (!f.isDirectory()) files.addElement(list[n]);
		}
		JPanel panel=new JPanel(new GridLayout(files.size(),1));
		JScrollPane scroller=new JScrollPane(panel);
		scroller.getVerticalScrollBar().setUnitIncrement(20);
		contentPane.add(scroller);
		for (n=0; n<files.size(); n++)
		{
			JButton label=new JButton(files.elementAt(n));
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent evt)
				{
					value=((JButton)evt.getSource()).getText();
					dispose();
				}
			});
			panel.add(label);
		}
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				dispose();
			}
		});
		setVisible(true);
	}
	
	/***************************************************************************
		Get the response value.
	*/
	public String getValue()
	{
		return value;
	}
}
