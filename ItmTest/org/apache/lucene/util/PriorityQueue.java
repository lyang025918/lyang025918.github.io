// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PriorityQueue.java

package org.apache.lucene.util;


public abstract class PriorityQueue
{

	private int size;
	private final int maxSize;
	private final Object heap[];

	public PriorityQueue(int maxSize)
	{
		this(maxSize, true);
	}

	public PriorityQueue(int maxSize, boolean prepopulate)
	{
		size = 0;
		int heapSize;
		if (0 == maxSize)
			heapSize = 2;
		else
		if (maxSize == 0x7fffffff)
			heapSize = 0x7fffffff;
		else
			heapSize = maxSize + 1;
		heap = (Object[])new Object[heapSize];
		this.maxSize = maxSize;
		if (prepopulate)
		{
			Object sentinel = getSentinelObject();
			if (sentinel != null)
			{
				heap[1] = sentinel;
				for (int i = 2; i < heap.length; i++)
					heap[i] = getSentinelObject();

				size = maxSize;
			}
		}
	}

	protected abstract boolean lessThan(Object obj, Object obj1);

	protected Object getSentinelObject()
	{
		return null;
	}

	public final Object add(Object element)
	{
		size++;
		heap[size] = element;
		upHeap();
		return heap[1];
	}

	public Object insertWithOverflow(Object element)
	{
		if (size < maxSize)
		{
			add(element);
			return null;
		}
		if (size > 0 && !lessThan(element, heap[1]))
		{
			Object ret = heap[1];
			heap[1] = element;
			updateTop();
			return ret;
		} else
		{
			return element;
		}
	}

	public final Object top()
	{
		return heap[1];
	}

	public final Object pop()
	{
		if (size > 0)
		{
			Object result = heap[1];
			heap[1] = heap[size];
			heap[size] = null;
			size--;
			downHeap();
			return result;
		} else
		{
			return null;
		}
	}

	public final Object updateTop()
	{
		downHeap();
		return heap[1];
	}

	public final int size()
	{
		return size;
	}

	public final void clear()
	{
		for (int i = 0; i <= size; i++)
			heap[i] = null;

		size = 0;
	}

	private final void upHeap()
	{
		int i = size;
		Object node = heap[i];
		for (int j = i >>> 1; j > 0 && lessThan(node, heap[j]); j >>>= 1)
		{
			heap[i] = heap[j];
			i = j;
		}

		heap[i] = node;
	}

	private final void downHeap()
	{
		int i = 1;
		Object node = heap[i];
		int j = i << 1;
		int k = j + 1;
		if (k <= size && lessThan(heap[k], heap[j]))
			j = k;
		do
		{
			if (j > size || !lessThan(heap[j], node))
				break;
			heap[i] = heap[j];
			i = j;
			j = i << 1;
			k = j + 1;
			if (k <= size && lessThan(heap[k], heap[j]))
				j = k;
		} while (true);
		heap[i] = node;
	}

	protected final Object[] getHeapArray()
	{
		return (Object[])heap;
	}
}
