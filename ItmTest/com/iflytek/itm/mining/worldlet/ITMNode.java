// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMNode.java

package com.iflytek.itm.mining.worldlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ITMNode
	implements Serializable
{

	public String vertics;
	private Map nodeAttri;

	public ITMNode()
	{
		this("");
	}

	public ITMNode(String nodeName)
	{
		nodeAttri = null;
		vertics = nodeName;
		nodeAttri = new HashMap();
	}

	public Double getNodeAttri(String attriName)
	{
		return (Double)nodeAttri.get(attriName);
	}

	public void appendNodeAttri(String attriName, Double attriValue)
	{
		if (null != nodeAttri.get(attriName))
			nodeAttri.put(attriName, Double.valueOf(((Double)nodeAttri.get(attriName)).doubleValue() + attriValue.doubleValue()));
		else
			nodeAttri.put(attriName, attriValue);
	}

	int compareTo(ITMNode other)
	{
		return vertics.compareTo(other.vertics);
	}

	public String toString()
	{
		return vertics;
	}
}
