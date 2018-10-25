// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WorldletResult.java

package com.iflytek.itm.mining.worldlet;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jgrapht.graph.AbstractBaseGraph;

// Referenced classes of package com.iflytek.itm.mining.worldlet:
//			Network, Method

public class WorldletResult
{

	private Network network;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/worldlet/WorldletResult);

	public WorldletResult()
	{
		network = new Network();
	}

	public Network getNetwork()
	{
		return network;
	}

	public AbstractBaseGraph doWorldlet(List docs, int indexs[], int numMember, int word_top_n)
	{
		Method method = new Method();
		List subDocs = new ArrayList();
		for (int i = 0; i < numMember; i++)
			subDocs.add(docs.get(indexs[i]));

		logger.info("doWorldlet | buildNetFromText enter");
		if (0 != buildNetFromText(method, subDocs))
		{
			return null;
		} else
		{
			logger.info("doWorldlet | buildNetFromText leave");
			removeStopWordNode(method);
			remove0Degree(method);
			logger.info("doWorldlet | fetchNetFeature enter");
			fetchNetFeature(method);
			logger.info("doWorldlet | fetchNetFeature leave");
			nodeEdgeRank(method);
			AbstractBaseGraph graph = filterWords(method, word_top_n);
			return graph;
		}
	}

	private int buildNetFromText(Method method, List docs)
	{
		return method.buildNet(network, docs);
	}

	private void removeStopWordNode(Method method)
	{
		method.removeStopWordNode(network);
	}

	private void remove0Degree(Method method)
	{
		method.remove0Degree(network);
	}

	private void fetchNetFeature(Method method)
	{
		method.fetchNetFeature(network);
	}

	private void nodeEdgeRank(Method method)
	{
		method.nodeEdgeRank(network);
	}

	public int fetchResult(StringBuffer buffer, AbstractBaseGraph graphs[])
	{
		int ret = 0;
		ret = Method.fetchResult(buffer, graphs);
		return ret;
	}

	public int fetchResult(StringBuffer buffer, AbstractBaseGraph graph)
	{
		int ret = 0;
		ret = Method.fetchResult(buffer, graph);
		return ret;
	}

	private AbstractBaseGraph filterWords(Method method, int word_top_n)
	{
		return method.filterWords(network, word_top_n);
	}

}
