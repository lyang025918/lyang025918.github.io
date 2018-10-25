// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Scorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

// Referenced classes of package org.apache.lucene.search:
//			DocIdSetIterator, Collector, Weight

public abstract class Scorer extends DocIdSetIterator
{
	public static class ChildScorer
	{

		public final Scorer child;
		public final String relationship;

		public ChildScorer(Scorer child, String relationship)
		{
			this.child = child;
			this.relationship = relationship;
		}
	}


	protected final Weight weight;

	protected Scorer(Weight weight)
	{
		this.weight = weight;
	}

	public void score(Collector collector)
		throws IOException
	{
		collector.setScorer(this);
		int doc;
		while ((doc = nextDoc()) != 0x7fffffff) 
			collector.collect(doc);
	}

	public boolean score(Collector collector, int max, int firstDocID)
		throws IOException
	{
		collector.setScorer(this);
		int doc;
		for (doc = firstDocID; doc < max; doc = nextDoc())
			collector.collect(doc);

		return doc != 0x7fffffff;
	}

	public abstract float score()
		throws IOException;

	public abstract float freq()
		throws IOException;

	public Weight getWeight()
	{
		return weight;
	}

	public Collection getChildren()
	{
		return Collections.emptyList();
	}
}
