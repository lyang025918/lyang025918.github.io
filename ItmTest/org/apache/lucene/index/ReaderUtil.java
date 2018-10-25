// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReaderUtil.java

package org.apache.lucene.index;

import java.util.List;

// Referenced classes of package org.apache.lucene.index:
//			AtomicReaderContext, IndexReaderContext

public final class ReaderUtil
{

	private ReaderUtil()
	{
	}

	public static IndexReaderContext getTopLevelContext(IndexReaderContext context)
	{
		for (; context.parent != null; context = context.parent);
		return context;
	}

	public static int subIndex(int n, int docStarts[])
	{
		int size = docStarts.length;
		int lo = 0;
		int hi;
		for (hi = size - 1; hi >= lo;)
		{
			int mid = lo + hi >>> 1;
			int midValue = docStarts[mid];
			if (n < midValue)
				hi = mid - 1;
			else
			if (n > midValue)
			{
				lo = mid + 1;
			} else
			{
				for (; mid + 1 < size && docStarts[mid + 1] == midValue; mid++);
				return mid;
			}
		}

		return hi;
	}

	public static int subIndex(int n, List leaves)
	{
		int size = leaves.size();
		int lo = 0;
		int hi;
		for (hi = size - 1; hi >= lo;)
		{
			int mid = lo + hi >>> 1;
			int midValue = ((AtomicReaderContext)leaves.get(mid)).docBase;
			if (n < midValue)
				hi = mid - 1;
			else
			if (n > midValue)
			{
				lo = mid + 1;
			} else
			{
				for (; mid + 1 < size && ((AtomicReaderContext)leaves.get(mid + 1)).docBase == midValue; mid++);
				return mid;
			}
		}

		return hi;
	}
}
