// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Collector, BooleanQuery, Weight

final class BooleanScorer extends Scorer
{
	static final class SubScorer
	{

		public Scorer scorer;
		public boolean prohibited;
		public Collector collector;
		public SubScorer next;

		public SubScorer(Scorer scorer, boolean required, boolean prohibited, Collector collector, SubScorer next)
		{
			if (required)
			{
				throw new IllegalArgumentException("this scorer cannot handle required=true");
			} else
			{
				this.scorer = scorer;
				this.prohibited = prohibited;
				this.collector = collector;
				this.next = next;
				return;
			}
		}
	}

	static final class BucketTable
	{

		public static final int SIZE = 2048;
		public static final int MASK = 2047;
		final Bucket buckets[] = new Bucket[2048];
		Bucket first;

		public Collector newCollector(int mask)
		{
			return new BooleanScorerCollector(mask, this);
		}

		public int size()
		{
			return 2048;
		}

		public BucketTable()
		{
			first = null;
			for (int idx = 0; idx < 2048; idx++)
				buckets[idx] = new Bucket();

		}
	}

	static final class Bucket
	{

		int doc;
		double score;
		int bits;
		int coord;
		Bucket next;

		Bucket()
		{
			doc = -1;
		}
	}

	private static final class BucketScorer extends Scorer
	{

		double score;
		int doc;
		int freq;

		public int advance(int target)
		{
			return 0x7fffffff;
		}

		public int docID()
		{
			return doc;
		}

		public float freq()
		{
			return (float)freq;
		}

		public int nextDoc()
		{
			return 0x7fffffff;
		}

		public float score()
		{
			return (float)score;
		}

		public BucketScorer(Weight weight)
		{
			super(weight);
			doc = 0x7fffffff;
		}
	}

	private static final class BooleanScorerCollector extends Collector
	{

		private BucketTable bucketTable;
		private int mask;
		private Scorer scorer;

		public void collect(int doc)
			throws IOException
		{
			BucketTable table = bucketTable;
			int i = doc & 0x7ff;
			Bucket bucket = table.buckets[i];
			if (bucket.doc != doc)
			{
				bucket.doc = doc;
				bucket.score = scorer.score();
				bucket.bits = mask;
				bucket.coord = 1;
				bucket.next = table.first;
				table.first = bucket;
			} else
			{
				bucket.score += scorer.score();
				bucket.bits |= mask;
				bucket.coord++;
			}
		}

		public void setNextReader(AtomicReaderContext atomicreadercontext)
		{
		}

		public void setScorer(Scorer scorer)
		{
			this.scorer = scorer;
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public BooleanScorerCollector(int mask, BucketTable bucketTable)
		{
			this.mask = mask;
			this.bucketTable = bucketTable;
		}
	}


	private SubScorer scorers;
	private BucketTable bucketTable;
	private final float coordFactors[];
	private final int minNrShouldMatch;
	private int end;
	private Bucket current;
	private static final int PROHIBITED_MASK = 1;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/BooleanScorer.desiredAssertionStatus();

	BooleanScorer(BooleanQuery.BooleanWeight weight, boolean disableCoord, int minNrShouldMatch, List optionalScorers, List prohibitedScorers, int maxCoord)
		throws IOException
	{
		super(weight);
		scorers = null;
		bucketTable = new BucketTable();
		this.minNrShouldMatch = minNrShouldMatch;
		if (optionalScorers != null && optionalScorers.size() > 0)
		{
			Iterator i$ = optionalScorers.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Scorer scorer = (Scorer)i$.next();
				if (scorer.nextDoc() != 0x7fffffff)
					scorers = new SubScorer(scorer, false, false, bucketTable.newCollector(0), scorers);
			} while (true);
		}
		if (prohibitedScorers != null && prohibitedScorers.size() > 0)
		{
			Iterator i$ = prohibitedScorers.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				Scorer scorer = (Scorer)i$.next();
				if (scorer.nextDoc() != 0x7fffffff)
					scorers = new SubScorer(scorer, false, true, bucketTable.newCollector(1), scorers);
			} while (true);
		}
		coordFactors = new float[optionalScorers.size() + 1];
		for (int i = 0; i < coordFactors.length; i++)
			coordFactors[i] = disableCoord ? 1.0F : weight.coord(i, maxCoord);

	}

	public boolean score(Collector collector, int max, int firstDocID)
		throws IOException
	{
		if (!$assertionsDisabled && firstDocID != -1)
			throw new AssertionError();
		BucketScorer bs = new BucketScorer(weight);
		collector.setScorer(bs);
		boolean more;
		do
		{
			bucketTable.first = null;
			do
			{
				if (current == null)
					break;
				if ((current.bits & 1) == 0)
				{
					if (current.doc >= max)
					{
						Bucket tmp = current;
						current = current.next;
						tmp.next = bucketTable.first;
						bucketTable.first = tmp;
						continue;
					}
					if (current.coord >= minNrShouldMatch)
					{
						bs.score = current.score * (double)coordFactors[current.coord];
						bs.doc = current.doc;
						bs.freq = current.coord;
						collector.collect(current.doc);
					}
				}
				current = current.next;
			} while (true);
			if (bucketTable.first != null)
			{
				current = bucketTable.first;
				bucketTable.first = current.next;
				return true;
			}
			more = false;
			end += 2048;
			for (SubScorer sub = scorers; sub != null; sub = sub.next)
			{
				int subScorerDocID = sub.scorer.docID();
				if (subScorerDocID != 0x7fffffff)
					more |= sub.scorer.score(sub.collector, end, subScorerDocID);
			}

			current = bucketTable.first;
		} while (current != null || more);
		return false;
	}

	public int advance(int target)
	{
		throw new UnsupportedOperationException();
	}

	public int docID()
	{
		throw new UnsupportedOperationException();
	}

	public int nextDoc()
	{
		throw new UnsupportedOperationException();
	}

	public float score()
	{
		throw new UnsupportedOperationException();
	}

	public float freq()
		throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public void score(Collector collector)
		throws IOException
	{
		score(collector, 0x7fffffff, -1);
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("boolean(");
		for (SubScorer sub = scorers; sub != null; sub = sub.next)
		{
			buffer.append(sub.scorer.toString());
			buffer.append(" ");
		}

		buffer.append(")");
		return buffer.toString();
	}

	public Collection getChildren()
	{
		throw new UnsupportedOperationException();
	}

}
