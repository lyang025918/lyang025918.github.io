// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Collector, Scorer

public class MultiCollector extends Collector
{

	private final Collector collectors[];

	public static transient Collector wrap(Collector collectors[])
	{
		int n = 0;
		Collector arr$[] = collectors;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			if (c != null)
				n++;
		}

		if (n == 0)
			throw new IllegalArgumentException("At least 1 collector must not be null");
		Collector arr$[];
		int len$;
		if (n == 1)
		{
			Collector col = null;
			arr$ = collectors;
			len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				Collector c = arr$[i$];
				if (c != null)
				{
					col = c;
					break;
				}
				i$++;
			} while (true);
			return col;
		}
		if (n == collectors.length)
			return new MultiCollector(collectors);
		Collector colls[] = new Collector[n];
		n = 0;
		arr$ = collectors;
		len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			if (c != null)
				colls[n++] = c;
		}

		return new MultiCollector(colls);
	}

	private transient MultiCollector(Collector collectors[])
	{
		this.collectors = collectors;
	}

	public boolean acceptsDocsOutOfOrder()
	{
		Collector arr$[] = collectors;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			if (!c.acceptsDocsOutOfOrder())
				return false;
		}

		return true;
	}

	public void collect(int doc)
		throws IOException
	{
		Collector arr$[] = collectors;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			c.collect(doc);
		}

	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		Collector arr$[] = collectors;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			c.setNextReader(context);
		}

	}

	public void setScorer(Scorer s)
		throws IOException
	{
		Collector arr$[] = collectors;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Collector c = arr$[i$];
			c.setScorer(s);
		}

	}
}
