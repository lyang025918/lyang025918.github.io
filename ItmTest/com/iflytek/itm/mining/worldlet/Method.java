// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Method.java

package com.iflytek.itm.mining.worldlet;

import java.util.*;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.graph.AbstractBaseGraph;

// Referenced classes of package com.iflytek.itm.mining.worldlet:
//			ITMEdge, ITMNode, Network

public class Method
{

	public Method()
	{
	}

	public void fetchNetFeature(Network network)
	{
		edgeDegrees(network, false);
		edgeBetweennessX(network, false);
	}

	public int buildNet(Network network, List docs)
	{
		return network.buildNet(docs);
	}

	public void removeStopWordNode(Network network)
	{
		network.removeStopWordNode();
	}

	public void remove0Degree(Network network)
	{
		network.remove0Degree();
	}

	public void nodeEdgeRank(Network network)
	{
		network.nodeEdgeRank();
	}

	public AbstractBaseGraph filterWords(Network network, int word_top_n)
	{
		return network.filterWords(word_top_n);
	}

	private void edgeDegreeCover(Network network, boolean idf)
	{
		AbstractBaseGraph graph = network.getOriginalGraph();
		NeighborIndex neighborIndex = new NeighborIndex(graph);
		ITMNode node1 = null;
		ITMNode node2 = null;
		Set node1s = null;
		Set node2s = null;
		int node1size = 0;
		int node2size = 0;
		int unionSize = 0;
		double result = 0.0D;
		ITMEdge edge;
		for (Iterator iterator = graph.edgeSet().iterator(); iterator.hasNext(); edge.appendEdgeAttri("cover", Double.valueOf(result)))
		{
			edge = (ITMEdge)iterator.next();
			node1 = (ITMNode)graph.getEdgeSource(edge);
			node2 = (ITMNode)graph.getEdgeTarget(edge);
			node1s = neighborIndex.neighborsOf(node1);
			node2s = neighborIndex.neighborsOf(node2);
			node1size = node1s.size();
			node2size = node2s.size();
			node1s.retainAll(node2s);
			unionSize = node1s.size();
			result = (double)unionSize / (double)((node1size + node2size) - unionSize);
		}

	}

	private void edgeBetweennessX(Network network, boolean idf)
	{
		AbstractBaseGraph graph = network.getOriginalGraph();
		List edgeList = null;
		Set gnodes = graph.vertexSet();
		int sum = gnodes.size();
		ITMNode nodes[] = new ITMNode[sum];
		gnodes.toArray(nodes);
		double perresult = 1.0D / (double)sum;
		BellmanFordShortestPath Bfsp = null;
		for (int i = 0; i < sum - 1; i++)
		{
			Bfsp = new BellmanFordShortestPath(graph, nodes[i], 1);
			for (int j = i + 1; j < sum; j++)
			{
				edgeList = Bfsp.getPathEdgeList(nodes[j]);
				if (null == edgeList || edgeList.isEmpty())
					continue;
				ITMEdge e;
				for (Iterator iterator = edgeList.iterator(); iterator.hasNext(); e.appendEdgeAttri("betweennessx", Double.valueOf(perresult)))
					e = (ITMEdge)iterator.next();

			}

		}

	}

	private void edgeDegrees(Network network, boolean idf)
	{
		ITMNode node = null;
		double edgeDegree = 0.0D;
		AbstractBaseGraph graph = network.getOriginalGraph();
		ITMEdge edge;
		for (Iterator iterator = graph.edgeSet().iterator(); iterator.hasNext(); edge.appendEdgeAttri("degree", Double.valueOf(edgeDegree)))
		{
			edge = (ITMEdge)iterator.next();
			node = (ITMNode)graph.getEdgeSource(edge);
			edgeDegree = graph.degreeOf(node);
			node = (ITMNode)graph.getEdgeTarget(edge);
			edgeDegree += graph.degreeOf(node);
		}

	}

	public static void main(String args1[])
	{
	}

	public static int fetchResult(StringBuffer buffer, AbstractBaseGraph graphs[])
	{
		int ret = 0;
		ret = Network.graph2xml(buffer, graphs);
		return ret;
	}

	public static int fetchResult(StringBuffer buffer, AbstractBaseGraph graph)
	{
		int ret = 0;
		ret = Network.graph2xml(buffer, graph);
		return ret;
	}
}
