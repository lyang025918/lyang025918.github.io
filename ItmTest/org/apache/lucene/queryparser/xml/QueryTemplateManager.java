// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryTemplateManager.java

package org.apache.lucene.queryparser.xml;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			DOMUtils

public class QueryTemplateManager
{

	static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	static final TransformerFactory tFactory = TransformerFactory.newInstance();
	HashMap compiledTemplatesCache;
	Templates defaultCompiledTemplates;

	public QueryTemplateManager()
	{
		compiledTemplatesCache = new HashMap();
		defaultCompiledTemplates = null;
	}

	public QueryTemplateManager(InputStream xslIs)
		throws TransformerConfigurationException, ParserConfigurationException, SAXException, IOException
	{
		compiledTemplatesCache = new HashMap();
		defaultCompiledTemplates = null;
		addDefaultQueryTemplate(xslIs);
	}

	public void addDefaultQueryTemplate(InputStream xslIs)
		throws TransformerConfigurationException, ParserConfigurationException, SAXException, IOException
	{
		defaultCompiledTemplates = getTemplates(xslIs);
	}

	public void addQueryTemplate(String name, InputStream xslIs)
		throws TransformerConfigurationException, ParserConfigurationException, SAXException, IOException
	{
		compiledTemplatesCache.put(name, getTemplates(xslIs));
	}

	public String getQueryAsXmlString(Properties formProperties, String queryTemplateName)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		Templates ts = (Templates)compiledTemplatesCache.get(queryTemplateName);
		return getQueryAsXmlString(formProperties, ts);
	}

	public Document getQueryAsDOM(Properties formProperties, String queryTemplateName)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		Templates ts = (Templates)compiledTemplatesCache.get(queryTemplateName);
		return getQueryAsDOM(formProperties, ts);
	}

	public String getQueryAsXmlString(Properties formProperties)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		return getQueryAsXmlString(formProperties, defaultCompiledTemplates);
	}

	public Document getQueryAsDOM(Properties formProperties)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		return getQueryAsDOM(formProperties, defaultCompiledTemplates);
	}

	public static String getQueryAsXmlString(Properties formProperties, Templates template)
		throws ParserConfigurationException, TransformerException
	{
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformCriteria(formProperties, template, result);
		return writer.toString();
	}

	public static String getQueryAsXmlString(Properties formProperties, InputStream xslIs)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformCriteria(formProperties, xslIs, result);
		return writer.toString();
	}

	public static Document getQueryAsDOM(Properties formProperties, Templates template)
		throws ParserConfigurationException, TransformerException
	{
		DOMResult result = new DOMResult();
		transformCriteria(formProperties, template, result);
		return (Document)result.getNode();
	}

	public static Document getQueryAsDOM(Properties formProperties, InputStream xslIs)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		DOMResult result = new DOMResult();
		transformCriteria(formProperties, xslIs, result);
		return (Document)result.getNode();
	}

	public static void transformCriteria(Properties formProperties, InputStream xslIs, Result result)
		throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document xslDoc = builder.parse(xslIs);
		DOMSource ds = new DOMSource(xslDoc);
		Transformer transformer = null;
		synchronized (tFactory)
		{
			transformer = tFactory.newTransformer(ds);
		}
		transformCriteria(formProperties, transformer, result);
	}

	public static void transformCriteria(Properties formProperties, Templates template, Result result)
		throws ParserConfigurationException, TransformerException
	{
		transformCriteria(formProperties, template.newTransformer(), result);
	}

	public static void transformCriteria(Properties formProperties, Transformer transformer, Result result)
		throws ParserConfigurationException, TransformerException
	{
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("Document");
		doc.appendChild(root);
		Enumeration keysEnum = formProperties.keys();
		do
		{
			if (!keysEnum.hasMoreElements())
				break;
			String propName = (String)keysEnum.nextElement();
			String value = formProperties.getProperty(propName);
			if (value != null && value.length() > 0)
				DOMUtils.insertChild(root, propName, value);
		} while (true);
		DOMSource xml = new DOMSource(doc);
		transformer.transform(xml, result);
	}

	public static Templates getTemplates(InputStream xslIs)
		throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException
	{
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document xslDoc = builder.parse(xslIs);
		DOMSource ds = new DOMSource(xslDoc);
		return tFactory.newTemplates(ds);
	}

}
