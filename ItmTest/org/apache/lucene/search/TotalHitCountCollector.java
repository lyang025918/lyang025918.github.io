// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TotalHitCountCollector.java

package org.apache.lucene.search;

import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Collector, Scorer

public class TotalHitCountCollector extends Collector
{

	private int totalHits;

	public TotalHitCountCollector()
	{
	}

	public int getTotalHits()
	{
		return totalHits;
	}

	public void setScorer(Scorer scorer1)
	{
	}

	public void collect(int doc)
	{
		totalHits++;
	}

	public void setNextReader(AtomicReaderContext atomicreadercontext)
	{
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return true;
	}
}
