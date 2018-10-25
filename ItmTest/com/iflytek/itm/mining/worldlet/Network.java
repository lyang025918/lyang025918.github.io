// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Network.java

package com.iflytek.itm.mining.worldlet;

import com.iflytek.itm.mining.ITMMiningResource;
import com.iflytek.itm.util.XMLTool;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.SimpleGraph;

// Referenced classes of package com.iflytek.itm.mining.worldlet:
//			ITMEdge, ITMNode

public class Network
{
	private class ITMComparator
		implements Comparator
	{

		private String rankAttri;
		final Network this$0;

		public String getRankAttri()
		{
			return rankAttri;
		}

		public int compare(Object o1, Object o2)
		{
			ITMEdge element1 = (ITMEdge)o1;
			ITMEdge element2 = (ITMEdge)o2;
			return element2.compare(element1, rankAttri);
		}

		public ITMComparator()
		{
			this$0 = Network.this;
			super();
			rankAttri = null;
		}

		public ITMComparator(String rankAttri)
		{
			this$0 = Network.this;
			super();
			this.rankAttri = null;
			this.rankAttri = rankAttri;
		}
	}


	private Map rankedEdges;
	private Map nameNodeMap;
	private AbstractBaseGraph originalGraph;
	private static final int MAP_DEFAULT_SIZE = 4096;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/worldlet/Network);

	public Map getRankedEdges()
	{
		return rankedEdges;
	}

	public Network()
	{
		rankedEdges = null;
		nameNodeMap = null;
		originalGraph = null;
		nameNodeMap = new HashMap(4096);
		rankedEdges = new LinkedHashMap(4096);
	}

	public AbstractBaseGraph getOriginalGraph()
	{
		return originalGraph;
	}

	public int buildNet(List docs)
	{
		int ret = 0;
		originalGraph = new SimpleGraph(com/iflytek/itm/mining/worldlet/ITMEdge);
		nameNodeMap.clear();
		rankedEdges.clear();
		ret = generateNet(docs);
		return ret;
	}

	private int generateNet(List content)
	{
		int ret = 0;
		String words[] = null;
		for (Iterator iterator = content.iterator(); iterator.hasNext(); addOneLine(words))
		{
			String line = (String)iterator.next();
			words = line.split("\\s+");
		}

		return ret;
	}

	private int addOneLine(String words[])
	{
		int ret = 0;
		boolean addNode = true;
		int size = words.length;
		addOneNode(words[0]);
		for (int distance = 1; distance < size && distance <= 2; distance++)
		{
			for (int j = 0; j < size - distance; j++)
			{
				if (addNode)
					addOneNode(words[j + distance]);
				if (!words[j].equals(words[j + distance]))
					addOneEdge(words[j], words[j + distance]);
			}

			addNode = false;
		}

		return ret;
	}

	private void addOneNode(String nodeName)
	{
		ITMNode node = (ITMNode)nameNodeMap.get(nodeName);
		if (null == node)
		{
			node = new ITMNode(nodeName);
			nameNodeMap.put(nodeName, node);
			originalGraph.addVertex(node);
		}
		node.appendNodeAttri("weight", Double.valueOf(1.0D));
	}

	private void addOneEdge(String sourceName, String targetName)
	{
		ITMNode source = (ITMNode)nameNodeMap.get(sourceName);
		ITMNode target = (ITMNode)nameNodeMap.get(targetName);
		ITMEdge edge;
		if (originalGraph.containsEdge(source, target))
			edge = (ITMEdge)originalGraph.getEdge(source, target);
		else
			edge = (ITMEdge)originalGraph.addEdge(source, target);
		edge.appendEdgeAttri("freq", Double.valueOf(1.0D));
	}

	public void remove0Degree()
	{
		Set set = new HashSet(originalGraph.vertexSet());
		Iterator iterator = set.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			ITMNode v = (ITMNode)iterator.next();
			int degree = originalGraph.degreeOf(v);
			if (degree == 0)
				originalGraph.removeVertex(v);
		} while (true);
	}

	private Map edgeRank(Set edges, ITMComparator comparator)
	{
		List edgeList = new ArrayList(edges);
		Collections.sort(edgeList, comparator);
		Map subRankedEdge = new LinkedHashMap();
		Iterator iterator = edgeList.iterator();
		ITMEdge edge = null;
		for (; iterator.hasNext(); subRankedEdge.put(edge, edge.getEdgeAttri(comparator.getRankAttri())))
			edge = (ITMEdge)iterator.next();

		return subRankedEdge;
	}

	private Map edgeRank(Map rankedEdges)
	{
		List total = new ArrayList(rankedEdges.entrySet());
		Collections.sort(total, new Comparator() {

			final Network this$0;

			public int compare(java.util.Map.Entry o1, java.util.Map.Entry o2)
			{
				return ((Integer)o2.getValue()).compareTo((Integer)o1.getValue());
			}

			public volatile int compare(Object obj, Object obj1)
			{
				return compare((java.util.Map.Entry)obj, (java.util.Map.Entry)obj1);
			}

			
			{
				this.this$0 = Network.this;
				super();
			}
		});
		rankedEdges.clear();
		java.util.Map.Entry ele = null;
		Iterator iterator1 = total.iterator();
		int i = 0;
		for (; iterator1.hasNext(); rankedEdges.put(ele.getKey(), ele.getValue()))
		{
			ele = (java.util.Map.Entry)iterator1.next();
			((ITMEdge)ele.getKey()).appendEdgeAttri("weight", Double.valueOf(((Integer)ele.getValue()).doubleValue()));
		}

		return rankedEdges;
	}

	public void nodeEdgeRank()
	{
		String attributes[] = {
			"degree", "freq", "betweennessx"
		};
		ITMEdge edgei = null;
		Map subRanked = null;
		Set edges = originalGraph.edgeSet();
		Iterator iterator = null;
		int order = 0;
		int size = 0;
		String as[] = attributes;
		int i = as.length;
		for (int j = 0; j < i; j++)
		{
			String attribute = as[j];
			subRanked = edgeRank(edges, new ITMComparator(attribute));
			order = 0;
			size = subRanked.size();
			for (iterator = subRanked.keySet().iterator(); iterator.hasNext();)
			{
				edgei = (ITMEdge)iterator.next();
				if (!rankedEdges.isEmpty() && null != rankedEdges.get(edgei))
					rankedEdges.put(edgei, Integer.valueOf((((Integer)rankedEdges.get(edgei)).intValue() + size) - order));
				else
					rankedEdges.put(edgei, Integer.valueOf(size - order));
				order++;
			}

		}

		edgeRank(rankedEdges);
	}

	private Map edgeRankByKey(Set edges, ITMComparator comparator)
	{
		List edgeList = new ArrayList(edges);
		Collections.sort(edgeList, comparator);
		Map subRankedEdge = new LinkedHashMap();
		Iterator iterator = edgeList.iterator();
		ITMEdge edge = null;
		int order = 0;
		for (; iterator.hasNext(); subRankedEdge.put(edge, Integer.valueOf(order++)))
			edge = (ITMEdge)iterator.next();

		return subRankedEdge;
	}

	public void removeStopWordNode()
	{
		Set nodes = new HashSet(originalGraph.vertexSet());
		Iterator iterator = nodes.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			ITMNode node = (ITMNode)iterator.next();
			if (ITMMiningResource.inst().isNoiseWord(node.vertics))
				originalGraph.removeVertex(node);
		} while (true);
	}

	public AbstractBaseGraph filterWords(int word_top_n)
	{
		AbstractBaseGraph graph = new SimpleGraph(com/iflytek/itm/mining/worldlet/ITMEdge);
		centerGraph(rankedEdges, word_top_n, graph);
		return graph;
	}

	private void centerGraph(Map edges, int word_top_n, AbstractBaseGraph retGraph)
	{
		Iterator iterator = edges.keySet().iterator();
		ITMEdge edge = null;
		ITMNode nodeSource = null;
		ITMNode nodeTarget = null;
		Map curNodeNum = new LinkedHashMap();
		while (retGraph.vertexSet().size() < word_top_n && iterator.hasNext()) 
		{
			edge = (ITMEdge)iterator.next();
			nodeSource = (ITMNode)originalGraph.getEdgeSource(edge);
			nodeTarget = (ITMNode)originalGraph.getEdgeTarget(edge);
			if (curNodeNum.isEmpty())
				addFirstEdge(retGraph, curNodeNum, nodeSource, nodeTarget, edge);
			else
			if (curNodeNum.containsKey(nodeSource) && ((Integer)curNodeNum.get(nodeSource)).intValue() > 3)
				curNodeNum.remove(nodeSource);
			else
			if (curNodeNum.containsKey(nodeTarget) && ((Integer)curNodeNum.get(nodeTarget)).intValue() > 3)
				curNodeNum.remove(nodeTarget);
			else
				addEdge(retGraph, curNodeNum, nodeSource, nodeTarget, edge);
		}
	}

	private void addFirstEdge(AbstractBaseGraph graph, Map curNodeNum, ITMNode nodeSource, ITMNode nodeTarget, ITMEdge edge)
	{
		graph.addVertex(nodeSource);
		graph.addVertex(nodeTarget);
		graph.addEdge(nodeSource, nodeTarget, edge);
		curNodeNum.put(nodeSource, Integer.valueOf(1));
		curNodeNum.put(nodeTarget, Integer.valueOf(1));
	}

	private void addEdge(AbstractBaseGraph graph, Map curNodeNum, ITMNode nodeSource, ITMNode nodeTarget, ITMEdge edge)
	{
		ITMNode toAddNode = null;
		ITMNode existedNode = null;
		if (curNodeNum.containsKey(nodeSource) && !graph.containsVertex(nodeTarget))
		{
			toAddNode = nodeTarget;
			existedNode = nodeSource;
		} else
		if (curNodeNum.containsKey(nodeTarget) && !graph.containsVertex(nodeSource))
		{
			toAddNode = nodeSource;
			existedNode = nodeTarget;
		}
		if (null != toAddNode && null != existedNode)
		{
			graph.addVertex(toAddNode);
			graph.addEdge(nodeSource, nodeTarget, edge);
			curNodeNum.put(existedNode, Integer.valueOf(((Integer)curNodeNum.get(existedNode)).intValue() + 1));
			curNodeNum.put(toAddNode, Integer.valueOf(1));
		}
	}

	public static int graph2xml(StringBuffer buffer, AbstractBaseGraph graph)
	{
		int ret = 0;
		XMLTool xmlTool = new XMLTool();
		AbstractBaseGraph graphs[] = new SimpleGraph[1];
		graphs[0] = graph;
		xmlTool.graph2Xml(graphs, buffer);
		return ret;
	}

	public static int graph2xml(StringBuffer buffer, AbstractBaseGraph graphs[])
	{
		int ret = 0;
		XMLTool xmlTool = new XMLTool();
		xmlTool.graph2Xml(graphs, buffer);
		return ret;
	}

	public static void graph2text(StringBuffer buffer, AbstractBaseGraph graph)
	{
		Set edges = graph.edgeSet();
		Set nodes = graph.vertexSet();
		ITMNode source = null;
		ITMNode target = null;
		Map nodeIndex = new LinkedHashMap();
		int edgeSize = edges.size();
		int nodeSize = nodes.size();
		int index = 0;
		buffer.append("node=").append(edgeSize).append(" ").append("arc=").append(nodeSize).append("\n");
		buffer.append("nodes\n");
		ITMNode node;
		for (Iterator iterator = nodes.iterator(); iterator.hasNext(); buffer.append("\ti=").append(index++).append(" word=").append(node.vertics).append("\n"))
		{
			node = (ITMNode)iterator.next();
			nodeIndex.put(node.vertics, Integer.valueOf(index));
		}

		index = 0;
		buffer.append("arcs\n");
		ITMEdge edge;
		for (Iterator iterator1 = edges.iterator(); iterator1.hasNext(); buffer.append("\tj=").append(index++).append(" begin=").append(nodeIndex.get(source.vertics)).append(" end=").append(nodeIndex.get(target.vertics)).append(" weight=").append(graph.getEdgeWeight(edge)).append("\n"))
		{
			edge = (ITMEdge)iterator1.next();
			source = (ITMNode)graph.getEdgeSource(edge);
			target = (ITMNode)graph.getEdgeTarget(edge);
		}

	}

	public static void text2graph(AbstractBaseGraph graph, StringBuffer buffer)
	{
		ITMNode node = null;
		ITMNode source = null;
		ITMNode target = null;
		ITMEdge edge = null;
		double weight = 0.0D;
		Map nodeIndex = new LinkedHashMap();
		Pattern pnode = Pattern.compile("\\ti=(\\d)\\sword=(.+)");
		Pattern pedge = Pattern.compile("\\tj=(\\d)\\sbegin=(\\d)\\send=(\\d)\\sweight=([0-9\\.]+)");
		Matcher m = null;
		int index = 0;
		boolean matchNode = false;
		String lines[] = buffer.toString().split("\\n");
		String as[] = lines;
		int i = as.length;
		for (int j = 0; j < i; j++)
		{
			String line = as[j];
			if (line.equals("nodes"))
			{
				matchNode = true;
				continue;
			}
			if (line.equals("arcs"))
			{
				matchNode = false;
				continue;
			}
			if (matchNode)
			{
				m = pnode.matcher(line);
				if (m.find())
				{
					index = Integer.valueOf(m.group(1)).intValue();
					node = new ITMNode(m.group(2));
					nodeIndex.put(Integer.valueOf(index), node);
					graph.addVertex(node);
				}
				continue;
			}
			m = pedge.matcher(line);
			if (m.find())
			{
				source = (ITMNode)nodeIndex.get(Integer.valueOf(m.group(2)));
				target = (ITMNode)nodeIndex.get(Integer.valueOf(m.group(3)));
				weight = Double.valueOf(m.group(4)).doubleValue();
				edge = (ITMEdge)graph.addEdge(source, target);
				graph.setEdgeWeight(edge, weight);
			}
		}

	}

	public static void main(String args[])
	{
		AbstractBaseGraph graph = new SimpleGraph(com/iflytek/itm/mining/worldlet/ITMEdge);
		ITMNode node1 = new ITMNode("你好");
		ITMNode node2 = new ITMNode("开通");
		ITMNode node3 = new ITMNode("短信");
		ITMNode node4 = new ITMNode("业务");
		ITMNode node5 = new ITMNode("功能");
		graph.addVertex(node1);
		graph.addVertex(node2);
		graph.addVertex(node3);
		graph.addVertex(node4);
		graph.addVertex(node5);
		graph.addEdge(node1, node2);
		graph.addEdge(node1, node3);
		graph.addEdge(node1, node5);
		graph.addEdge(node1, node4);
		graph.addEdge(node3, node4);
		graph.addEdge(node4, node5);
		StringBuffer buf = new StringBuffer();
		graph2text(buf, graph);
		AbstractBaseGraph graph1 = new SimpleGraph(com/iflytek/itm/mining/worldlet/ITMEdge);
		text2graph(graph1, buf);
	}

}
