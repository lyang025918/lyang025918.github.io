// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DOMUtils.java

package org.apache.lucene.queryparser.xml;

import java.io.Reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			ParserException

public class DOMUtils
{

	public DOMUtils()
	{
	}

	public static Element getChildByTagOrFail(Element e, String name)
		throws ParserException
	{
		Element kid = getChildByTagName(e, name);
		if (null == kid)
			throw new ParserException((new StringBuilder()).append(e.getTagName()).append(" missing \"").append(name).append("\" child element").toString());
		else
			return kid;
	}

	public static Element getFirstChildOrFail(Element e)
		throws ParserException
	{
		Element kid = getFirstChildElement(e);
		if (null == kid)
			throw new ParserException((new StringBuilder()).append(e.getTagName()).append(" does not contain a child element").toString());
		else
			return kid;
	}

	public static String getAttributeOrFail(Element e, String name)
		throws ParserException
	{
		String v = e.getAttribute(name);
		if (null == v)
			throw new ParserException((new StringBuilder()).append(e.getTagName()).append(" missing \"").append(name).append("\" attribute").toString());
		else
			return v;
	}

	public static String getAttributeWithInheritanceOrFail(Element e, String name)
		throws ParserException
	{
		String v = getAttributeWithInheritance(e, name);
		if (null == v)
			throw new ParserException((new StringBuilder()).append(e.getTagName()).append(" missing \"").append(name).append("\" attribute").toString());
		else
			return v;
	}

	public static String getNonBlankTextOrFail(Element e)
		throws ParserException
	{
		String v = getText(e);
		if (null != v)
			v = v.trim();
		if (null == v || 0 == v.length())
			throw new ParserException((new StringBuilder()).append(e.getTagName()).append(" has no text").toString());
		else
			return v;
	}

	public static Element getChildByTagName(Element e, String name)
	{
		for (Node kid = e.getFirstChild(); kid != null; kid = kid.getNextSibling())
			if (kid.getNodeType() == 1 && name.equals(kid.getNodeName()))
				return (Element)kid;

		return null;
	}

	public static String getAttributeWithInheritance(Element element, String attributeName)
	{
		String result = element.getAttribute(attributeName);
		if (result == null || "".equals(result))
		{
			Node n = element.getParentNode();
			if (n == element || n == null)
				return null;
			if (n instanceof Element)
			{
				Element parent = (Element)n;
				return getAttributeWithInheritance(parent, attributeName);
			} else
			{
				return null;
			}
		} else
		{
			return result;
		}
	}

	public static String getChildTextByTagName(Element e, String tagName)
	{
		Element child = getChildByTagName(e, tagName);
		return child == null ? null : getText(child);
	}

	public static Element insertChild(Element parent, String tagName, String text)
	{
		Element child = parent.getOwnerDocument().createElement(tagName);
		parent.appendChild(child);
		if (text != null)
			child.appendChild(child.getOwnerDocument().createTextNode(text));
		return child;
	}

	public static String getAttribute(Element element, String attributeName, String deflt)
	{
		String result = element.getAttribute(attributeName);
		return result != null && !"".equals(result) ? result : deflt;
	}

	public static float getAttribute(Element element, String attributeName, float deflt)
	{
		String result = element.getAttribute(attributeName);
		return result != null && !"".equals(result) ? Float.parseFloat(result) : deflt;
	}

	public static int getAttribute(Element element, String attributeName, int deflt)
	{
		String result = element.getAttribute(attributeName);
		return result != null && !"".equals(result) ? Integer.parseInt(result) : deflt;
	}

	public static boolean getAttribute(Element element, String attributeName, boolean deflt)
	{
		String result = element.getAttribute(attributeName);
		return result != null && !"".equals(result) ? Boolean.valueOf(result).booleanValue() : deflt;
	}

	public static String getText(Node e)
	{
		StringBuilder sb = new StringBuilder();
		getTextBuffer(e, sb);
		return sb.toString();
	}

	public static Element getFirstChildElement(Element element)
	{
		for (Node kid = element.getFirstChild(); kid != null; kid = kid.getNextSibling())
			if (kid.getNodeType() == 1)
				return (Element)kid;

		return null;
	}

	private static void getTextBuffer(Node e, StringBuilder sb)
	{
		for (Node kid = e.getFirstChild(); kid != null; kid = kid.getNextSibling())
			switch (kid.getNodeType())
			{
			case 3: // '\003'
				sb.append(kid.getNodeValue());
				break;

			case 1: // '\001'
				getTextBuffer(kid, sb);
				break;

			case 5: // '\005'
				getTextBuffer(kid, sb);
				break;
			}

	}

	public static Document loadXML(Reader is)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (Exception se)
		{
			throw new RuntimeException("Parser configuration error", se);
		}
		Document doc = null;
		try
		{
			doc = db.parse(new InputSource(is));
		}
		catch (Exception se)
		{
			throw new RuntimeException((new StringBuilder()).append("Error parsing file:").append(se).toString(), se);
		}
		return doc;
	}
}
