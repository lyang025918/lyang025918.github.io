// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DisjunctionSumScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.List;

// Referenced classes of package org.apache.lucene.search:
//			DisjunctionScorer, Scorer, Weight

class DisjunctionSumScorer extends DisjunctionScorer
{

	private final int minimumNrMatchers;
	private int doc;
	protected int nrMatchers;
	private double score;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/DisjunctionSumScorer.desiredAssertionStatus();

	public DisjunctionSumScorer(Weight weight, List subScorers, int minimumNrMatchers)
		throws IOException
	{
		super(weight, (Scorer[])subScorers.toArray(new Scorer[subScorers.size()]), subScorers.size());
		doc = -1;
		nrMatchers = -1;
		score = (0.0D / 0.0D);
		if (minimumNrMatchers <= 0)
			throw new IllegalArgumentException("Minimum nr of matchers must be positive");
		if (numScorers <= 1)
		{
			throw new IllegalArgumentException("There must be at least 2 subScorers");
		} else
		{
			this.minimumNrMatchers = minimumNrMatchers;
			return;
		}
	}

	public DisjunctionSumScorer(Weight weight, List subScorers)
		throws IOException
	{
		this(weight, subScorers, 1);
	}

	public int nextDoc()
		throws IOException
	{
		if (!$assertionsDisabled && doc == 0x7fffffff)
			throw new AssertionError();
		do
		{
			while (subScorers[0].docID() == doc) 
				if (subScorers[0].nextDoc() != 0x7fffffff)
				{
					heapAdjust(0);
				} else
				{
					heapRemoveRoot();
					if (numScorers < minimumNrMatchers)
						return doc = 0x7fffffff;
				}
			afterNext();
		} while (nrMatchers < minimumNrMatchers);
		return doc;
	}

	private void afterNext()
		throws IOException
	{
		Scorer sub = subScorers[0];
		doc = sub.docID();
		if (doc == 0x7fffffff)
		{
			nrMatchers = 0x7fffffff;
		} else
		{
			score = sub.score();
			nrMatchers = 1;
			countMatches(1);
			countMatches(2);
		}
	}

	private void countMatches(int root)
		throws IOException
	{
		if (root < numScorers && subScorers[root].docID() == doc)
		{
			nrMatchers++;
			score += subScorers[root].score();
			countMatches((root << 1) + 1);
			countMatches((root << 1) + 2);
		}
	}

	public float score()
		throws IOException
	{
		return (float)score;
	}

	public int docID()
	{
		return doc;
	}

	public float freq()
		throws IOException
	{
		return (float)nrMatchers;
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
		afterNext();
		if (nrMatchers >= minimumNrMatchers)
			return doc;
		else
			return nextDoc();
	}

}
