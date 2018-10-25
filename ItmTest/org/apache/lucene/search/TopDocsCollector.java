// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopDocsCollector.java

package org.apache.lucene.search;

import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			Collector, ScoreDoc, TopDocs

public abstract class TopDocsCollector extends Collector
{

	protected static final TopDocs EMPTY_TOPDOCS = new TopDocs(0, new ScoreDoc[0], (0.0F / 0.0F));
	protected PriorityQueue pq;
	protected int totalHits;

	protected TopDocsCollector(PriorityQueue pq)
	{
		this.pq = pq;
	}

	protected void populateResults(ScoreDoc results[], int howMany)
	{
		for (int i = howMany - 1; i >= 0; i--)
			results[i] = (ScoreDoc)pq.pop();

	}

	protected TopDocs newTopDocs(ScoreDoc results[], int start)
	{
		return results != null ? new TopDocs(totalHits, results) : EMPTY_TOPDOCS;
	}

	public int getTotalHits()
	{
		return totalHits;
	}

	protected int topDocsSize()
	{
		return totalHits >= pq.size() ? pq.size() : totalHits;
	}

	public TopDocs topDocs()
	{
		return topDocs(0, topDocsSize());
	}

	public TopDocs topDocs(int start)
	{
		return topDocs(start, topDocsSize());
	}

	public TopDocs topDocs(int start, int howMany)
	{
		int size = topDocsSize();
		if (start < 0 || start >= size || howMany <= 0)
			return newTopDocs(null, start);
		howMany = Math.min(size - start, howMany);
		ScoreDoc results[] = new ScoreDoc[howMany];
		for (int i = pq.size() - start - howMany; i > 0; i--)
			pq.pop();

		populateResults(results, howMany);
		return newTopDocs(results, start);
	}

}
