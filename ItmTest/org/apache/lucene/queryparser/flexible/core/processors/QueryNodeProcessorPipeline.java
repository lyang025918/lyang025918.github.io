// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeProcessorPipeline.java

package org.apache.lucene.queryparser.flexible.core.processors;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.processors:
//			QueryNodeProcessor

public class QueryNodeProcessorPipeline
	implements QueryNodeProcessor, List
{

	private LinkedList processors;
	private QueryConfigHandler queryConfig;

	public QueryNodeProcessorPipeline()
	{
		processors = new LinkedList();
	}

	public QueryNodeProcessorPipeline(QueryConfigHandler queryConfigHandler)
	{
		processors = new LinkedList();
		queryConfig = queryConfigHandler;
	}

	public QueryConfigHandler getQueryConfigHandler()
	{
		return queryConfig;
	}

	public QueryNode process(QueryNode queryTree)
		throws QueryNodeException
	{
		for (Iterator i$ = processors.iterator(); i$.hasNext();)
		{
			QueryNodeProcessor processor = (QueryNodeProcessor)i$.next();
			queryTree = processor.process(queryTree);
		}

		return queryTree;
	}

	public void setQueryConfigHandler(QueryConfigHandler queryConfigHandler)
	{
		queryConfig = queryConfigHandler;
		QueryNodeProcessor processor;
		for (Iterator i$ = processors.iterator(); i$.hasNext(); processor.setQueryConfigHandler(queryConfig))
			processor = (QueryNodeProcessor)i$.next();

	}

	public boolean add(QueryNodeProcessor processor)
	{
		boolean added = processors.add(processor);
		if (added)
			processor.setQueryConfigHandler(queryConfig);
		return added;
	}

	public void add(int index, QueryNodeProcessor processor)
	{
		processors.add(index, processor);
		processor.setQueryConfigHandler(queryConfig);
	}

	public boolean addAll(Collection c)
	{
		boolean anyAdded = processors.addAll(c);
		QueryNodeProcessor processor;
		for (Iterator i$ = c.iterator(); i$.hasNext(); processor.setQueryConfigHandler(queryConfig))
			processor = (QueryNodeProcessor)i$.next();

		return anyAdded;
	}

	public boolean addAll(int index, Collection c)
	{
		boolean anyAdded = processors.addAll(index, c);
		QueryNodeProcessor processor;
		for (Iterator i$ = c.iterator(); i$.hasNext(); processor.setQueryConfigHandler(queryConfig))
			processor = (QueryNodeProcessor)i$.next();

		return anyAdded;
	}

	public void clear()
	{
		processors.clear();
	}

	public boolean contains(Object o)
	{
		return processors.contains(o);
	}

	public boolean containsAll(Collection c)
	{
		return processors.containsAll(c);
	}

	public QueryNodeProcessor get(int index)
	{
		return (QueryNodeProcessor)processors.get(index);
	}

	public int indexOf(Object o)
	{
		return processors.indexOf(o);
	}

	public boolean isEmpty()
	{
		return processors.isEmpty();
	}

	public Iterator iterator()
	{
		return processors.iterator();
	}

	public int lastIndexOf(Object o)
	{
		return processors.lastIndexOf(o);
	}

	public ListIterator listIterator()
	{
		return processors.listIterator();
	}

	public ListIterator listIterator(int index)
	{
		return processors.listIterator(index);
	}

	public boolean remove(Object o)
	{
		return processors.remove(o);
	}

	public QueryNodeProcessor remove(int index)
	{
		return (QueryNodeProcessor)processors.remove(index);
	}

	public boolean removeAll(Collection c)
	{
		return processors.removeAll(c);
	}

	public boolean retainAll(Collection c)
	{
		return processors.retainAll(c);
	}

	public QueryNodeProcessor set(int index, QueryNodeProcessor processor)
	{
		QueryNodeProcessor oldProcessor = (QueryNodeProcessor)processors.set(index, processor);
		if (oldProcessor != processor)
			processor.setQueryConfigHandler(queryConfig);
		return oldProcessor;
	}

	public int size()
	{
		return processors.size();
	}

	public List subList(int fromIndex, int toIndex)
	{
		return processors.subList(fromIndex, toIndex);
	}

	public Object[] toArray(Object array[])
	{
		return processors.toArray(array);
	}

	public Object[] toArray()
	{
		return processors.toArray();
	}

	public volatile Object remove(int x0)
	{
		return remove(x0);
	}

	public volatile void add(int x0, Object x1)
	{
		add(x0, (QueryNodeProcessor)x1);
	}

	public volatile Object set(int x0, Object x1)
	{
		return set(x0, (QueryNodeProcessor)x1);
	}

	public volatile Object get(int x0)
	{
		return get(x0);
	}

	public volatile boolean add(Object x0)
	{
		return add((QueryNodeProcessor)x0);
	}
}
