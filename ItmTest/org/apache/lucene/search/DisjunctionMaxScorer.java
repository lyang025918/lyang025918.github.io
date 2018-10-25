// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DisjunctionMaxScorer.java

package org.apache.lucene.search;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.search:
//			DisjunctionScorer, Scorer, Weight

class DisjunctionMaxScorer extends DisjunctionScorer
{

	private final float tieBreakerMultiplier;
	private int doc;
	private float scoreSum;
	private float scoreMax;

	public DisjunctionMaxScorer(Weight weight, float tieBreakerMultiplier, Scorer subScorers[], int numScorers)
	{
		super(weight, subScorers, numScorers);
		doc = -1;
		this.tieBreakerMultiplier = tieBreakerMultiplier;
	}

	public int nextDoc()
		throws IOException
	{
		if (numScorers == 0)
			return doc = 0x7fffffff;
		while (subScorers[0].docID() == doc) 
			if (subScorers[0].nextDoc() != 0x7fffffff)
			{
				heapAdjust(0);
			} else
			{
				heapRemoveRoot();
				if (numScorers == 0)
					return doc = 0x7fffffff;
			}
		return doc = subScorers[0].docID();
	}

	public int docID()
	{
		return doc;
	}

	public float score()
		throws IOException
	{
		int doc = subScorers[0].docID();
		scoreSum = scoreMax = subScorers[0].score();
		int size = numScorers;
		scoreAll(1, size, doc);
		scoreAll(2, size, doc);
		return scoreMax + (scoreSum - scoreMax) * tieBreakerMultiplier;
	}

	private void scoreAll(int root, int size, int doc)
		throws IOException
	{
		if (root < size && subScorers[root].docID() == doc)
		{
			float sub = subScorers[root].score();
			scoreSum += sub;
			scoreMax = Math.max(scoreMax, sub);
			scoreAll((root << 1) + 1, size, doc);
			scoreAll((root << 1) + 2, size, doc);
		}
	}

	public float freq()
		throws IOException
	{
		int doc = subScorers[0].docID();
		int size = numScorers;
		return (float)(1 + freq(1, size, doc) + freq(2, size, doc));
	}

	private int freq(int root, int size, int doc)
		throws IOException
	{
		int freq = 0;
		if (root < size && subScorers[root].docID() == doc)
		{
			freq = ++freq + freq((root << 1) + 1, size, doc);
			freq += freq((root << 1) + 2, size, doc);
		}
		return freq;
	}

	public int advance(int target)
		throws IOException
	{
		if (numScorers == 0)
			return doc = 0x7fffffff;
		while (subScorers[0].docID() < target) 
			if (subScorers[0].advance(target) != 0x7fffffff)
			{
				heapAdjust(0);
			} else
			{
				heapRemoveRoot();
				if (numScorers == 0)
					return doc = 0x7fffffff;
			}
		return doc = subScorers[0].docID();
	}
}
