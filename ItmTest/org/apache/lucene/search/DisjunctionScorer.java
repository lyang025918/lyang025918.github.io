// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DisjunctionScorer.java

package org.apache.lucene.search;

import java.util.ArrayList;
import java.util.Collection;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Weight

abstract class DisjunctionScorer extends Scorer
{

	protected final Scorer subScorers[];
	protected int numScorers;

	protected DisjunctionScorer(Weight weight, Scorer subScorers[], int numScorers)
	{
		super(weight);
		this.subScorers = subScorers;
		this.numScorers = numScorers;
		heapify();
	}

	protected final void heapify()
	{
		for (int i = (numScorers >> 1) - 1; i >= 0; i--)
			heapAdjust(i);

	}

	protected final void heapAdjust(int root)
	{
		Scorer scorer = subScorers[root];
		int doc = scorer.docID();
		for (int i = root; i <= (numScorers >> 1) - 1;)
		{
			int lchild = (i << 1) + 1;
			Scorer lscorer = subScorers[lchild];
			int ldoc = lscorer.docID();
			int rdoc = 0x7fffffff;
			int rchild = (i << 1) + 2;
			Scorer rscorer = null;
			if (rchild < numScorers)
			{
				rscorer = subScorers[rchild];
				rdoc = rscorer.docID();
			}
			if (ldoc < doc)
			{
				if (rdoc < ldoc)
				{
					subScorers[i] = rscorer;
					subScorers[rchild] = scorer;
					i = rchild;
				} else
				{
					subScorers[i] = lscorer;
					subScorers[lchild] = scorer;
					i = lchild;
				}
			} else
			if (rdoc < doc)
			{
				subScorers[i] = rscorer;
				subScorers[rchild] = scorer;
				i = rchild;
			} else
			{
				return;
			}
		}

	}

	protected final void heapRemoveRoot()
	{
		if (numScorers == 1)
		{
			subScorers[0] = null;
			numScorers = 0;
		} else
		{
			subScorers[0] = subScorers[numScorers - 1];
			subScorers[numScorers - 1] = null;
			numScorers--;
			heapAdjust(0);
		}
	}

	public final Collection getChildren()
	{
		ArrayList children = new ArrayList(numScorers);
		for (int i = 0; i < numScorers; i++)
			children.add(new Scorer.ChildScorer(subScorers[i], "SHOULD"));

		return children;
	}
}
