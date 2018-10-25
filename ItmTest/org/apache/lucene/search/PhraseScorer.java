// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhraseScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.search.similarities.Similarity;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, PhrasePositions, Weight, PhraseQuery

abstract class PhraseScorer extends Scorer
{

	PhrasePositions min;
	PhrasePositions max;
	private float freq;
	final org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer;

	PhraseScorer(Weight weight, PhraseQuery.PostingsAndFreq postings[], org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer)
	{
		super(weight);
		this.docScorer = docScorer;
		if (postings.length > 0)
		{
			min = new PhrasePositions(postings[0].postings, postings[0].position, 0, postings[0].terms);
			max = min;
			max.doc = -1;
			for (int i = 1; i < postings.length; i++)
			{
				PhrasePositions pp = new PhrasePositions(postings[i].postings, postings[i].position, i, postings[i].terms);
				max.next = pp;
				max = pp;
				max.doc = -1;
			}

			max.next = min;
		}
	}

	public int docID()
	{
		return max.doc;
	}

	public int nextDoc()
		throws IOException
	{
		return advance(max.doc);
	}

	public float score()
		throws IOException
	{
		return docScorer.score(max.doc, freq);
	}

	private boolean advanceMin(int target)
		throws IOException
	{
		if (!min.skipTo(target))
		{
			max.doc = 0x7fffffff;
			return false;
		} else
		{
			min = min.next;
			max = max.next;
			return true;
		}
	}

	public int advance(int target)
		throws IOException
	{
		freq = 0.0F;
		if (!advanceMin(target))
			return 0x7fffffff;
		boolean restart = false;
		do
		{
			if (freq != 0.0F)
				break;
			while (min.doc < max.doc || restart) 
			{
				restart = false;
				if (!advanceMin(max.doc))
					return 0x7fffffff;
			}
			freq = phraseFreq();
			restart = true;
		} while (true);
		return max.doc;
	}

	public final float freq()
	{
		return freq;
	}

	abstract float phraseFreq()
		throws IOException;

	public String toString()
	{
		return (new StringBuilder()).append("scorer(").append(weight).append(")").toString();
	}
}
