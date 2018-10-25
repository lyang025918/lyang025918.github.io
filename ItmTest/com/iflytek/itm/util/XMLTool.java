// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XMLTool.java

package com.iflytek.itm.util;

import com.iflytek.itm.mining.worldlet.ITMEdge;
import com.iflytek.itm.mining.worldlet.ITMNode;
import java.io.*;
import java.util.*;
import org.dom4j.*;
import org.dom4j.io.*;
import org.jgrapht.graph.AbstractBaseGraph;

public class XMLTool
{

	private SAXReader saxReader;
	private Document document;
	private Element root;

	public XMLTool()
	{
		saxReader = null;
		document = null;
		root = null;
	}

	public int graph2Xml(AbstractBaseGraph graph[], StringBuffer xmlContent)
	{
		int ret = 0;
		List nodes = new ArrayList();
		List edges[] = new ArrayList[graph.length];
		ret = splitGraph(graph, nodes, edges);
		ret = generateXml(nodes, edges, xmlContent);
		return ret;
	}

	private int generateXml(List nodes, List edges[], StringBuffer xmlContent)
	{
		int ret = 0;
		ret = writeInit();
		if (0 == ret)
		{
			ret = createDocument("hot_view", nodes, edges);
			if (0 == ret)
				xmlContent.append(document.asXML());
		}
		return ret;
	}

	public int xml2Graph()
	{
		int ret = 0;
		return ret;
	}

	public int text2Xml()
	{
		int ret = 0;
		return ret;
	}

	private int splitGraph(AbstractBaseGraph graph[], List nodes, List edges[])
	{
		int ret = 0;
		int nodeid = 1;
		Map nodeIndex = new LinkedHashMap();
		ITMNode source = null;
		ITMNode target = null;
		for (int index = 0; index < graph.length; index++)
		{
			Set gnodes = graph[index].vertexSet();
			Set gedges = graph[index].edgeSet();
			Map entry;
			for (Iterator iterator = gnodes.iterator(); iterator.hasNext(); nodes.add(entry))
			{
				ITMNode node = (ITMNode)iterator.next();
				entry = new LinkedHashMap();
				entry.put("id", String.valueOf(nodeid));
				entry.put("value", node.vertics);
				entry.put("weight", String.valueOf(node.getNodeAttri("weight").intValue()));
				nodeIndex.put(node.vertics, String.valueOf(nodeid));
				nodeid++;
			}

			edges[index] = new ArrayList();
			int edgeid = 1;
			Map entry;
			for (Iterator iterator1 = gedges.iterator(); iterator1.hasNext(); edges[index].add(entry))
			{
				ITMEdge edge = (ITMEdge)iterator1.next();
				entry = new LinkedHashMap();
				source = (ITMNode)graph[index].getEdgeSource(edge);
				target = (ITMNode)graph[index].getEdgeTarget(edge);
				entry.put("id", String.valueOf(edgeid));
				entry.put("begin", nodeIndex.get(source.vertics));
				entry.put("end", nodeIndex.get(target.vertics));
				entry.put("weight", String.valueOf(edge.getEdgeAttri("weight").intValue()));
				edgeid++;
			}

			nodeIndex.clear();
		}

		nodeIndex = null;
		return ret;
	}

	private int writeInit()
	{
		int ret = 0;
		document = DocumentHelper.createDocument();
		return ret;
	}

	private int readInit()
	{
		int ret = 0;
		saxReader = new SAXReader();
		return ret;
	}

	private int createDocument(String miningType, List nodes, List edges[])
	{
		int ret = 0;
		addSubject(miningType, "", nodes, edges);
		return ret;
	}

	private int addSubject(String miningType, String reserved, List nodes, List edges[])
	{
		int ret = 0;
		root = document.addElement("result");
		root = root.addElement("subject");
		root.addAttribute("value", miningType);
		Element child = root.addElement("param");
		child.addAttribute("reserved", reserved);
		addItems("node", nodes);
		for (int i = 0; i < edges.length; i++)
			addItems("edge", edges[i]);

		return ret;
	}

	private int addItems(String value, List item)
	{
		int ret = 0;
		if (null == item || item.isEmpty())
			return ret;
		int count = item.size();
		Element subRoot = root.addElement("items");
		subRoot.addAttribute("value", value);
		subRoot.addAttribute("count", (new StringBuilder()).append(count).append("").toString());
		Map subItem = null;
		for (int index = 0; index < count; index++)
		{
			subItem = (Map)item.get(index);
			addItem(subRoot, subItem);
		}

		return ret;
	}

	private int addItem(Element pRoot, Map item)
	{
		int ret = 0;
		if (null == item || item.isEmpty())
			return ret;
		Iterator iterator = item.keySet().iterator();
		Element element = pRoot.addElement("item");
		String key;
		for (; iterator.hasNext(); element.addAttribute(key, (String)item.get(key)))
			key = (String)iterator.next();

		return ret;
	}

	private int write(String path, String encode)
	{
		int ret = 0;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encode);
		try
		{
			XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(path)), format);
			xmlWriter.write(document);
			xmlWriter.close();
		}
		catch (FileNotFoundException e)
		{
			ret = -1;
		}
		catch (IOException e)
		{
			ret = -2;
		}
		return ret;
	}

	public static void main(String args1[])
	{
	}
}
