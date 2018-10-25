// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConjunctionScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.ArrayUtil;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Weight

class ConjunctionScorer extends Scorer
{

	private final Scorer scorers[];
	private int lastDoc;

	public ConjunctionScorer(Weight weight, Collection scorers)
		throws IOException
	{
		this(weight, (Scorer[])scorers.toArray(new Scorer[scorers.size()]));
	}

	public transient ConjunctionScorer(Weight weight, Scorer scorers[])
		throws IOException
	{
		super(weight);
		lastDoc = -1;
		this.scorers = scorers;
		for (int i = 0; i < scorers.length; i++)
			if (scorers[i].nextDoc() == 0x7fffffff)
			{
				lastDoc = 0x7fffffff;
				return;
			}

		ArrayUtil.mergeSort(scorers, new Comparator() {

			final ConjunctionScorer this$0;

			public int compare(Scorer o1, Scorer o2)
			{
				return o1.docID() - o2.docID();
			}

			public volatile int compare(Object x0, Object x1)
			{
				return compare((Scorer)x0, (Scorer)x1);
			}

			
			{
				this$0 = ConjunctionScorer.this;
				super();
			}
		});
		if (doNext() == 0x7fffffff)
		{
			lastDoc = 0x7fffffff;
			return;
		}
		int end = scorers.length - 1;
		int max = end >> 1;
		for (int i = 0; i < max; i++)
		{
			Scorer tmp = scorers[i];
			int idx = end - i - 1;
			scorers[i] = scorers[idx];
			scorers[idx] = tmp;
		}

	}

	private int doNext()
		throws IOException
	{
		int first = 0;
		int doc;
		Scorer firstScorer;
		for (doc = scorers[scorers.length - 1].docID(); (firstScorer = scorers[first]).docID() < doc;)
		{
			doc = firstScorer.advance(doc);
			first = first != scorers.length - 1 ? first + 1 : 0;
		}

		return doc;
	}

	public int advance(int target)
		throws IOException
	{
		if (lastDoc == 0x7fffffff)
			return lastDoc;
		if (scorers[scorers.length - 1].docID() < target)
			scorers[scorers.length - 1].advance(target);
		return lastDoc = doNext();
	}

	public int docID()
	{
		return lastDoc;
	}

	public int nextDoc()
		throws IOException
	{
		if (lastDoc == 0x7fffffff)
			return lastDoc;
		if (lastDoc == -1)
		{
			return lastDoc = scorers[scorers.length - 1].docID();
		} else
		{
			scorers[scorers.length - 1].nextDoc();
			return lastDoc = doNext();
		}
	}

	public float score()
		throws IOException
	{
		float sum = 0.0F;
		for (int i = 0; i < scorers.length; i++)
			sum += scorers[i].score();

		return sum;
	}

	public float freq()
		throws IOException
	{
		return (float)scorers.length;
	}

	public Collection getChildren()
	{
		ArrayList children = new ArrayList(scorers.length);
		Scorer arr$[] = scorers;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Scorer scorer = arr$[i$];
			children.add(new Scorer.ChildScorer(scorer, "MUST"));
		}

		return children;
	}
}
