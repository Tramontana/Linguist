// LTEncrypt.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.eclecity.linguist.support.Base64;
import net.eclecity.linguist.util.LUEncrypt;


/******************************************************************************
	An encryption tool.
	<pre>
	[1.001 GT]  11/01/02  New class.
	</pre>
*/
public class LTEncrypt
{
	private static JTextField text;
	private static JTextField key;
	private static JTextArea encoded;

	/***************************************************************************
		Main program displays a dialog that takes a string for encryption
		and a key, and generates the encrypted result.
	*/
	public static void main(String[] args)
	{
		JDialog dialog=new JDialog((Frame)null,"Encryptor");
		Container contentPane=dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel topPanel=new JPanel(new BorderLayout());
		contentPane.add(topPanel,BorderLayout.NORTH);

		JPanel panel=new JPanel(new GridLayout(2,1));
		topPanel.add(panel,BorderLayout.WEST);
		panel.add(new JLabel("Text:"));
		panel.add(new JLabel("Key:"));

		panel=new JPanel(new GridLayout(2,1));
		topPanel.add(panel,BorderLayout.CENTER);
		text=new JTextField(40);
		panel.add(text);
		key=new JTextField();
		panel.add(key);

		encoded=new JTextArea(10,40);
		contentPane.add(new JScrollPane(encoded),BorderLayout.CENTER);
		encoded.setLineWrap(true);
		encoded.setBorder(new EmptyBorder(10,10,10,10));

		panel=new JPanel(new GridLayout(1,5));
		contentPane.add(panel,BorderLayout.SOUTH);
		panel.add(new JLabel(""));
		JButton button=new JButton("Decode");
		panel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				decode();
			}
		});
		button=new JButton("Encrypt");
		panel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					encoded.setText(LUEncrypt.encrypt(text.getText(),key.getText()));
				}
				catch (Exception e) {}
			}
		});
		button=new JButton("OK");
		panel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		panel.add(new JLabel(""));

		dialog.pack();
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent event)
			{
				System.exit(0);
			}
		});
		dialog.setVisible(true);
	}

	/***************************************************************************
		Decode a base64 string.
	*/
	private static void decode()
	{
		final JDialog dialog=new JDialog((Frame)null,"Base64 Decoder",true);
		dialog.getContentPane().setLayout(new BorderLayout());
		JPanel panel=new JPanel(new BorderLayout());
		dialog.getContentPane().add(panel,BorderLayout.CENTER);
		JPanel p=new JPanel(new GridLayout(2,1));
		panel.add(p,BorderLayout.WEST);
		p.add(new JLabel("Base 64:"));
		p.add(new JLabel("Decoded:"));
		p=new JPanel(new GridLayout(2,1));
		panel.add(p,BorderLayout.CENTER);
		final JTextField base64=new JTextField(20);
		final JTextField decoded=new JTextField(20);
		decoded.setEditable(false);
		p.add(base64);
		p.add(decoded);
		panel=new JPanel(new GridLayout(1,4));
		dialog.getContentPane().add(panel,BorderLayout.SOUTH);
		panel.add(new JLabel(""));
		JButton button=new JButton("Decode");
		panel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				decoded.setText(Base64.decodeToString(base64.getText()));
			}
		});
		button=new JButton("OK");
		panel.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				dialog.dispose();
			}
		});
		panel.add(new JLabel(""));
		dialog.pack();
		dialog.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent event)
			{
				System.exit(0);
			}
		});
		dialog.setVisible(true);
	}
}
