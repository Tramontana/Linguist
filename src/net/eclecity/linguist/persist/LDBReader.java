// LDBReader.java

package net.eclecity.linguist.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/***************************************************************************
	The XML reader.
*/
public class LDBReader extends LDBObject implements ContentHandler, ErrorHandler
{
	private final String vendorParserClass="org.apache.xerces.parsers.SAXParser";
	private LDBClient client;
	private String tableName;
	private Properties table;

	public LDBReader(LDBClient client,String xmlURI) throws IOException,SAXException,LDBException
	{
		System.out.println("XML Reader: source is "+xmlURI);
		this.client=client;

		XMLReader reader=XMLReaderFactory.createXMLReader(vendorParserClass);

		// Register content handler
		reader.setContentHandler(this);

		// Register error handler
		reader.setErrorHandler(this);

		// Parse
		client.clear();
		InputSource source=new InputSource(new FileInputStream(xmlURI));
		reader.parse(source);
		client.commit();
	}

	public void setDocumentLocator(Locator locator) {}

	public void startDocument()
	{
	}

	public void endDocument()
	{
		try { client.commit(); }
		catch (LDBException e) {}
	}

	public void processingInstruction(String target, String data) {}

	public void startPrefixMapping(String prefix, String uri) {}

	public void endPrefixMapping(String prefix) {}

	// When we see a <record> tag create a new table.
	// When we see a <field> tag add the field to the current record.
	public void startElement(String namespaceURI, String localName,
		String qName, Attributes atts)
	{
		if (localName.equals("record"))
		{
			tableName=atts.getValue(0);
			table=new Properties();
		}
		else if (localName.equals("field"))
			table.put(atts.getValue(0),atts.getValue(1));
	}

	// When we see a </record> tag create a new record.
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("record"))
		{
			try
			{
				// We need the client to help us here.
				client.getPersistent().insert(tableName,table);
			}
			catch (Exception e) { e.printStackTrace(); }
		}
	}

	public void characters(char[] ch, int start, int length) {}

	public void ignorableWhitespace(char[] ch, int start, int length) {}

	public void skippedEntity(String name) {}

 	public void warning(SAXParseException exception)
		throws SAXException {

     System.out.println("**Parsing Warning**\n" +
                        "  Line:    " +
                           exception.getLineNumber() + "\n" +
                        "  URI:     " +
                           exception.getSystemId() + "\n" +
                        "  Message: " +
                           exception.getMessage());
     throw new SAXException("Warning encountered");
 }

 public void error(SAXParseException exception)
     throws SAXException {

     System.out.println("**Parsing Error**\n" +
                        "  Line:    " +
                           exception.getLineNumber() + "\n" +
                        "  URI:     " +
                           exception.getSystemId() + "\n" +
                        "  Message: " +
                           exception.getMessage());
     throw new SAXException("Error encountered");
 }

 public void fatalError(SAXParseException exception)
     throws SAXException {

     System.out.println("**Parsing Fatal Error**\n" +
                        "  Line:    " +
                           exception.getLineNumber() + "\n" +
                        "  URI:     " +
                           exception.getSystemId() + "\n" +
                        "  Message: " +
                           exception.getMessage());
     throw new SAXException("Fatal Error encountered");
 }

	class Element
	{
		String name;
		String value;

		Element(String name,String value)
		{
			System.out.println("New element: "+name+","+value);
			this.name=name;
			this.value=value;
		}
	}
}
