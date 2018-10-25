// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositiveScoresOnlyCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Collector, ScoreCachingWrappingScorer, Scorer

public class PositiveScoresOnlyCollector extends Collector
{

	private final Collector c;
	private Scorer scorer;

	public PositiveScoresOnlyCollector(Collector c)
	{
		this.c = c;
	}

	public void collect(int doc)
		throws IOException
	{
		if (scorer.score() > 0.0F)
			c.collect(doc);
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		c.setNextReader(context);
	}

	public void setScorer(Scorer scorer)
		throws IOException
	{
		this.scorer = new ScoreCachingWrappingScorer(scorer);
		c.setScorer(this.scorer);
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return c.acceptsDocsOutOfOrder();
	}
}
