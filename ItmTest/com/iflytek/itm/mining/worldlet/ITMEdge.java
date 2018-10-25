// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMEdge.java

package com.iflytek.itm.mining.worldlet;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jgrapht.graph.*;

// Referenced classes of package com.iflytek.itm.mining.worldlet:
//			ITMNode

public class ITMEdge extends DefaultEdge
{

	private Map edgeAttri;

	public ITMEdge()
	{
		edgeAttri = null;
		edgeAttri = new LinkedHashMap();
		edgeAttri.put("betweennessx", Double.valueOf(0.0D));
		edgeAttri.put("freq", Double.valueOf(0.0D));
		edgeAttri.put("degree", Double.valueOf(0.0D));
	}

	public void appendEdgeAttri(String attName, Double attValue)
	{
		if (null != edgeAttri.get(attName))
			edgeAttri.put(attName, Double.valueOf(((Double)edgeAttri.get(attName)).doubleValue() + attValue.doubleValue()));
		else
			edgeAttri.put(attName, attValue);
	}

	public Double getEdgeAttri(String attName)
	{
		return (Double)edgeAttri.get(attName);
	}

	public int compare(ITMEdge otherEdge, String rankAttri)
	{
		if (null == rankAttri || rankAttri.isEmpty())
			return super.toString().compareTo(otherEdge.toString());
		else
			return ((Double)edgeAttri.get(rankAttri)).compareTo(otherEdge.getEdgeAttri(rankAttri));
	}

	public static void main(String args[])
	{
		AbstractBaseGraph g = new SimpleGraph(com/iflytek/itm/mining/worldlet/ITMEdge);
		ITMNode node1 = new ITMNode();
		node1.vertics = "Äú";
		ITMNode node2 = new ITMNode();
		node2.vertics = "ºÃ";
		ITMNode node3 = new ITMNode();
		node3.vertics = "ÄãºÃ°¡";
		ITMNode node4 = new ITMNode();
		node4.vertics = "¿­ÀÖ";
		ITMNode node5 = new ITMNode();
		node5.vertics = "Äú";
		g.addVertex(node1);
		g.addVertex(node2);
		g.addVertex(node3);
		g.addVertex(node4);
		boolean b = g.containsVertex(node5);
		g.addEdge(node1, node2);
		b = g.containsEdge(node2, node1);
		g.addEdge(node2, node3);
		g.addEdge(node4, node2);
		g.addEdge(node1, node3);
		g.addEdge(node1, node4);
		g.addEdge(node3, node4);
		g.addEdge(node4, node3);
		g.removeVertex(node2);
		System.out.println("ok");
	}
}
