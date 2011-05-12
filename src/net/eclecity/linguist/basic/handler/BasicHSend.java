// BasicHSend.java

//=============================================================================
//	Linguist Script Compiler, Debugger and Runtime
//	Copyleft (C) 1999 Graham Trott
//
//	Part of Linguist; see LS.java for a full copyleft notice.
//=============================================================================

package net.eclecity.linguist.basic.handler;

import java.io.File;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.eclecity.linguist.handler.LHHandler;
import net.eclecity.linguist.handler.LHModule;
import net.eclecity.linguist.runtime.LRException;
import net.eclecity.linguist.runtime.LRProgram;
import net.eclecity.linguist.value.LVValue;

/*******************************************************************************
 * Send a message to a module.
 */
public class BasicHSend extends LHHandler
{
	private LVValue message = null; // the message
	private LHModule module; // the module to send it to
	private BasicHDispatcher dispatcher; // the dispatcher to send it to
	private LVValue name; // or the name of a module
	private LVValue from = null;
	private LVValue to = null;
	private LVValue subject = null;
	private LVValue smtpHost = null;
	private String packageName; // or the name of a package
	private boolean parent = false;

	/****************************************************************************
	 * Send a message to the parent of this module.
	 */
	public BasicHSend(int line, LVValue message)
	{
		super(line);
		this.message = message;
		this.module = null;
		name = null;
		packageName = null;
		parent = true;
	}

	/****************************************************************************
	 * Send a message to a module variable.
	 */
	public BasicHSend(int line, LVValue message, LHModule module)
	{
		super(line);
		this.message = message;
		this.module = module;
		name = null;
		packageName = null;
	}

	/****************************************************************************
	 * Send a message to a module identified by its name.
	 */
	public BasicHSend(int line, LVValue message, LVValue name)
	{
		super(line);
		this.message = message;
		this.name = name;
		module = null;
		packageName = null;
	}

	/****************************************************************************
	 * Send a message to a dispatcher.
	 */
	public BasicHSend(int line, LVValue message, BasicHDispatcher dispatcher)
	{
		super(line);
		this.message = message;
		this.dispatcher = dispatcher;
	}

	/****************************************************************************
	 * Send a message to the background of a package.
	 */
	public BasicHSend(int line, LVValue message, String packageName)
	{
		super(line);
		this.message = message;
		this.packageName = packageName;
		name = null;
		module = null;
	}

	/****************************************************************************
	 * Send an email message.
	 */
	public BasicHSend(int line, LVValue message, LVValue from, LVValue to,
			LVValue subject, LVValue smtpHost)
	{
		super(line);
		this.message = message;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.smtpHost = smtpHost;
	}

	/****************************************************************************
	 * (Runtime) Do it now.
	 */
	public int execute() throws LRException
	{
		LRProgram p;
		// if the parent is set, send it back up
		if (parent)
		{
			getParent().onMessage(program, message.getStringValue());
		}
		// if a module is defined, send it there
		else if (module != null)
		{
			p = module.getModule();
			if (p != null) p.onMessage(program, message.getStringValue());
			// else throw new LRError(LRError.moduleNotAssigned(module.getName()));
		}
		// if the module is named, send it there
		else if (name != null)
		{
			p = getModule(name.getStringValue());
			if (p != null) p.onMessage(program, message.getStringValue());
			// else throw new
			// LRError(LRError.moduleNotFound(name.getStringValue()));
		}
		// if the dispatcher is named, send it there
		else if (dispatcher != null) dispatcher.send(message);
		// if the package is named, send it to the background of that package
		else if (packageName != null)
		{
			getBackground(packageName)
					.onMessage(program, message.getStringValue());
		}
		// send an email message
		else if (message != null)
		{
			/*
			 * try { SmtpClient smtp; if (smtpHost != null) smtp = new
			 * SmtpClient(smtpHost.getStringValue()); else smtp = new SmtpClient();
			 * smtp.from(from.getStringValue()); smtp.to(to.getStringValue());
			 * PrintStream msg = smtp.startMessage(); msg.println("To: " +
			 * to.getStringValue()); msg.println("Subject: " +
			 * subject.getStringValue()); msg.println();
			 * msg.println(message.getStringValue()); msg.println();
			 * smtp.closeServer(); } catch (IOException e) { e.printStackTrace(); }
			 */
			try
			{
				sendMail(smtpHost.getStringValue(), from.getStringValue(), to
						.getStringValue(), subject.getStringValue(), message
						.getStringValue());
			}
			catch (AddressException e)
			{}
			catch (MessagingException e)
			{}
		}
		return pc + 1;
	}

	protected Message prepareHeader(String smtp_host, String from, String to,
			String subject) throws AddressException, MessagingException
	{
		Properties props = new Properties();
		props.put("smtp.host", smtp_host);
		Session session = Session.getDefaultInstance(props, null);

		Message msg = new MimeMessage(session);

		InternetAddress addr = new InternetAddress(to);
		msg.addRecipients(Message.RecipientType.TO,
				new InternetAddress[] { addr });

		InternetAddress from_addr = new InternetAddress(from);
		msg.setFrom(from_addr);

		msg.setSubject(subject);

		return msg;
	}

	public void sendMail(String smtp_host, String from, String to,
			String subject, String message) throws AddressException,
			MessagingException
	{
		Message msg = prepareHeader(smtp_host, from, to, subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}

	public void sendWithAttachments(String smtp_host, String from, String to,
			String subject, String message, Vector attach)
			throws AddressException, MessagingException
	{
		Message msg = prepareHeader(smtp_host, from, to, subject);

		MimeMultipart mp = new MimeMultipart();

		MimeBodyPart text = new MimeBodyPart();
		text.setDisposition(Part.INLINE);
		text.setContent(message, "text/plain");
		mp.addBodyPart(text);

		for (int i = 0; i < attach.size(); i++)
		{
			MimeBodyPart file_part = new MimeBodyPart();
			File file = (File) attach.elementAt(i);
			FileDataSource fds = new FileDataSource(file);
			DataHandler dh = new DataHandler(fds);
			file_part.setFileName(file.getName());
			file_part.setDisposition(Part.ATTACHMENT);
			file_part.setDescription("Attached file: " + file.getName());
			file_part.setDataHandler(dh);
			mp.addBodyPart(file_part);
		}

		msg.setContent(mp);
		Transport.send(msg);
	}
}

