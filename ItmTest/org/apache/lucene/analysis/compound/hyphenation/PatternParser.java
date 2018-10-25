// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternParser.java

package org.apache.lucene.analysis.compound.hyphenation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

// Referenced classes of package org.apache.lucene.analysis.compound.hyphenation:
//			Hyphen, PatternConsumer

public class PatternParser extends DefaultHandler
{

	XMLReader parser;
	int currElement;
	PatternConsumer consumer;
	StringBuilder token;
	ArrayList exception;
	char hyphenChar;
	String errMsg;
	static final int ELEM_CLASSES = 1;
	static final int ELEM_EXCEPTIONS = 2;
	static final int ELEM_PATTERNS = 3;
	static final int ELEM_HYPHEN = 4;

	public PatternParser()
	{
		token = new StringBuilder();
		parser = createParser();
		parser.setContentHandler(this);
		parser.setErrorHandler(this);
		parser.setEntityResolver(this);
		hyphenChar = '-';
	}

	public PatternParser(PatternConsumer consumer)
	{
		this();
		this.consumer = consumer;
	}

	public void setConsumer(PatternConsumer consumer)
	{
		this.consumer = consumer;
	}

	public void parse(String filename)
		throws IOException
	{
		parse(new InputSource(filename));
	}

	public void parse(File file)
		throws IOException
	{
		InputSource src = new InputSource(file.toURL().toExternalForm());
		parse(src);
	}

	public void parse(InputSource source)
		throws IOException
	{
		try
		{
			parser.parse(source);
		}
		catch (SAXException e)
		{
			throw new IOException(e);
		}
	}

	static XMLReader createParser()
	{
		SAXParserFactory factory;
		factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newSAXParser().getXMLReader();
		Exception e;
		e;
		throw new RuntimeException((new StringBuilder()).append("Couldn't create XMLReader: ").append(e.getMessage()).toString());
	}

	protected String readToken(StringBuffer chars)
	{
		boolean space = false;
		int i;
		for (i = 0; i < chars.length() && Character.isWhitespace(chars.charAt(i)); i++)
			space = true;

		if (space)
		{
			for (int countr = i; countr < chars.length(); countr++)
				chars.setCharAt(countr - i, chars.charAt(countr));

			chars.setLength(chars.length() - i);
			if (token.length() > 0)
			{
				String word = token.toString();
				token.setLength(0);
				return word;
			}
		}
		space = false;
		i = 0;
		do
		{
			if (i >= chars.length())
				break;
			if (Character.isWhitespace(chars.charAt(i)))
			{
				space = true;
				break;
			}
			i++;
		} while (true);
		token.append(chars.toString().substring(0, i));
		for (int countr = i; countr < chars.length(); countr++)
			chars.setCharAt(countr - i, chars.charAt(countr));

		chars.setLength(chars.length() - i);
		if (space)
		{
			String word = token.toString();
			token.setLength(0);
			return word;
		} else
		{
			token.append(chars);
			return null;
		}
	}

	protected static String getPattern(String word)
	{
		StringBuilder pat = new StringBuilder();
		int len = word.length();
		for (int i = 0; i < len; i++)
			if (!Character.isDigit(word.charAt(i)))
				pat.append(word.charAt(i));

		return pat.toString();
	}

	protected ArrayList normalizeException(ArrayList ex)
	{
		ArrayList res = new ArrayList();
		for (int i = 0; i < ex.size(); i++)
		{
			Object item = ex.get(i);
			if (item instanceof String)
			{
				String str = (String)item;
				StringBuilder buf = new StringBuilder();
				for (int j = 0; j < str.length(); j++)
				{
					char c = str.charAt(j);
					if (c != hyphenChar)
					{
						buf.append(c);
					} else
					{
						res.add(buf.toString());
						buf.setLength(0);
						char h[] = new char[1];
						h[0] = hyphenChar;
						res.add(new Hyphen(new String(h), null, null));
					}
				}

				if (buf.length() > 0)
					res.add(buf.toString());
			} else
			{
				res.add(item);
			}
		}

		return res;
	}

	protected String getExceptionWord(ArrayList ex)
	{
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < ex.size(); i++)
		{
			Object item = ex.get(i);
			if (item instanceof String)
			{
				res.append((String)item);
				continue;
			}
			if (((Hyphen)item).noBreak != null)
				res.append(((Hyphen)item).noBreak);
		}

		return res.toString();
	}

	protected static String getInterletterValues(String pat)
	{
		StringBuilder il = new StringBuilder();
		String word = (new StringBuilder()).append(pat).append("a").toString();
		int len = word.length();
		for (int i = 0; i < len; i++)
		{
			char c = word.charAt(i);
			if (Character.isDigit(c))
			{
				il.append(c);
				i++;
			} else
			{
				il.append('0');
			}
		}

		return il.toString();
	}

	public InputSource resolveEntity(String publicId, String systemId)
	{
		if (systemId != null && systemId.matches("(?i).*\\bhyphenation.dtd\\b.*") || "hyphenation-info".equals(publicId))
			return new InputSource(getClass().getResource("hyphenation.dtd").toExternalForm());
		else
			return null;
	}

	public void startElement(String uri, String local, String raw, Attributes attrs)
	{
		if (local.equals("hyphen-char"))
		{
			String h = attrs.getValue("value");
			if (h != null && h.length() == 1)
				hyphenChar = h.charAt(0);
		} else
		if (local.equals("classes"))
			currElement = 1;
		else
		if (local.equals("patterns"))
			currElement = 3;
		else
		if (local.equals("exceptions"))
		{
			currElement = 2;
			exception = new ArrayList();
		} else
		if (local.equals("hyphen"))
		{
			if (token.length() > 0)
				exception.add(token.toString());
			exception.add(new Hyphen(attrs.getValue("pre"), attrs.getValue("no"), attrs.getValue("post")));
			currElement = 4;
		}
		token.setLength(0);
	}

	public void endElement(String uri, String local, String raw)
	{
		if (token.length() > 0)
		{
			String word = token.toString();
			switch (currElement)
			{
			case 1: // '\001'
				consumer.addClass(word);
				break;

			case 2: // '\002'
				exception.add(word);
				exception = normalizeException(exception);
				consumer.addException(getExceptionWord(exception), (ArrayList)exception.clone());
				break;

			case 3: // '\003'
				consumer.addPattern(getPattern(word), getInterletterValues(word));
				break;
			}
			if (currElement != 4)
				token.setLength(0);
		}
		if (currElement == 4)
			currElement = 2;
		else
			currElement = 0;
	}

	public void characters(char ch[], int start, int length)
	{
		StringBuffer chars = new StringBuffer(length);
		chars.append(ch, start, length);
		for (String word = readToken(chars); word != null; word = readToken(chars))
			switch (currElement)
			{
			case 1: // '\001'
				consumer.addClass(word);
				break;

			case 2: // '\002'
				exception.add(word);
				exception = normalizeException(exception);
				consumer.addException(getExceptionWord(exception), (ArrayList)exception.clone());
				exception.clear();
				break;

			case 3: // '\003'
				consumer.addPattern(getPattern(word), getInterletterValues(word));
				break;
			}

	}

	private String getLocationString(SAXParseException ex)
	{
		StringBuilder str = new StringBuilder();
		String systemId = ex.getSystemId();
		if (systemId != null)
		{
			int index = systemId.lastIndexOf('/');
			if (index != -1)
				systemId = systemId.substring(index + 1);
			str.append(systemId);
		}
		str.append(':');
		str.append(ex.getLineNumber());
		str.append(':');
		str.append(ex.getColumnNumber());
		return str.toString();
	}
}
