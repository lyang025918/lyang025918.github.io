// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScoreCachingWrappingScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Collector

public class ScoreCachingWrappingScorer extends Scorer
{

	private final Scorer scorer;
	private int curDoc;
	private float curScore;

	public ScoreCachingWrappingScorer(Scorer scorer)
	{
		super(scorer.weight);
		curDoc = -1;
		this.scorer = scorer;
	}

	public boolean score(Collector collector, int max, int firstDocID)
		throws IOException
	{
		return scorer.score(collector, max, firstDocID);
	}

	public float score()
		throws IOException
	{
		int doc = scorer.docID();
		if (doc != curDoc)
		{
			curScore = scorer.score();
			curDoc = doc;
		}
		return curScore;
	}

	public float freq()
		throws IOException
	{
		return scorer.freq();
	}

	public int docID()
	{
		return scorer.docID();
	}

	public int nextDoc()
		throws IOException
	{
		return scorer.nextDoc();
	}

	public void score(Collector collector)
		throws IOException
	{
		scorer.score(collector);
	}

	public int advance(int target)
		throws IOException
	{
		return scorer.advance(target);
	}

	public Collection getChildren()
	{
		return Collections.singleton(new Scorer.ChildScorer(scorer, "CACHED"));
	}
}
