// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MergedIterator.java

package org.apache.lucene.index;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.lucene.util.PriorityQueue;

final class MergedIterator
	implements Iterator
{
	private static class TermMergeQueue extends PriorityQueue
	{

		protected boolean lessThan(SubIterator a, SubIterator b)
		{
			int cmp = a.current.compareTo(b.current);
			if (cmp != 0)
				return cmp < 0;
			else
				return a.index < b.index;
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((SubIterator)x0, (SubIterator)x1);
		}

		TermMergeQueue(int size)
		{
			super(size);
		}
	}

	private static class SubIterator
	{

		Iterator iterator;
		Comparable current;
		int index;

		private SubIterator()
		{
		}

	}


	private Comparable current;
	private final TermMergeQueue queue;
	private final SubIterator top[];
	private int numTop;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MergedIterator.desiredAssertionStatus();

	public transient MergedIterator(Iterator iterators[])
	{
		queue = new TermMergeQueue(iterators.length);
		top = new SubIterator[iterators.length];
		int index = 0;
		Iterator arr$[] = iterators;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Iterator iterator = arr$[i$];
			if (iterator.hasNext())
			{
				SubIterator sub = new SubIterator();
				sub.current = (Comparable)iterator.next();
				sub.iterator = iterator;
				sub.index = index++;
				queue.add(sub);
			}
		}

	}

	public boolean hasNext()
	{
		if (queue.size() > 0)
			return true;
		for (int i = 0; i < numTop; i++)
			if (top[i].iterator.hasNext())
				return true;

		return false;
	}

	public Comparable next()
	{
		pushTop();
		if (queue.size() > 0)
			pullTop();
		else
			current = null;
		if (current == null)
			throw new NoSuchElementException();
		else
			return current;
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private void pullTop()
	{
		if (!$assertionsDisabled && numTop != 0)
			throw new AssertionError();
		do
			top[numTop++] = (SubIterator)queue.pop();
		while (queue.size() != 0 && ((SubIterator)queue.top()).current.equals(top[0].current));
		current = top[0].current;
	}

	private void pushTop()
	{
		for (int i = 0; i < numTop; i++)
			if (top[i].iterator.hasNext())
			{
				top[i].current = (Comparable)top[i].iterator.next();
				queue.add(top[i]);
			} else
			{
				top[i].current = null;
			}

		numTop = 0;
	}

	public volatile Object next()
	{
		return next();
	}

}
