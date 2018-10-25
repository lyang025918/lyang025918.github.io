// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopDocs.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			ScoreDoc, TopFieldDocs, Sort, FieldDoc, 
//			FieldComparator, SortField

public class TopDocs
{
	private static class MergeSortQueue extends PriorityQueue
	{

		final ScoreDoc shardHits[][];
		final FieldComparator comparators[];
		final int reverseMul[];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopDocs.desiredAssertionStatus();

		public boolean lessThan(ShardRef first, ShardRef second)
		{
			if (!$assertionsDisabled && first == second)
				throw new AssertionError();
			FieldDoc firstFD = (FieldDoc)shardHits[first.shardIndex][first.hitIndex];
			FieldDoc secondFD = (FieldDoc)shardHits[second.shardIndex][second.hitIndex];
			for (int compIDX = 0; compIDX < comparators.length; compIDX++)
			{
				FieldComparator comp = comparators[compIDX];
				int cmp = reverseMul[compIDX] * comp.compareValues(firstFD.fields[compIDX], secondFD.fields[compIDX]);
				if (cmp != 0)
					return cmp < 0;
			}

			if (first.shardIndex < second.shardIndex)
				return true;
			if (first.shardIndex > second.shardIndex)
				return false;
			if (!$assertionsDisabled && first.hitIndex == second.hitIndex)
				throw new AssertionError();
			else
				return first.hitIndex < second.hitIndex;
		}

		public volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((ShardRef)x0, (ShardRef)x1);
		}


		public MergeSortQueue(Sort sort, TopDocs shardHits[])
			throws IOException
		{
			super(shardHits.length);
			this.shardHits = new ScoreDoc[shardHits.length][];
label0:
			for (int shardIDX = 0; shardIDX < shardHits.length; shardIDX++)
			{
				ScoreDoc shard[] = shardHits[shardIDX].scoreDocs;
				if (shard == null)
					continue;
				this.shardHits[shardIDX] = shard;
				int hitIDX = 0;
				do
				{
					if (hitIDX >= shard.length)
						continue label0;
					ScoreDoc sd = shard[hitIDX];
					if (!(sd instanceof FieldDoc))
						throw new IllegalArgumentException((new StringBuilder()).append("shard ").append(shardIDX).append(" was not sorted by the provided Sort (expected FieldDoc but got ScoreDoc)").toString());
					FieldDoc fd = (FieldDoc)sd;
					if (fd.fields == null)
						throw new IllegalArgumentException((new StringBuilder()).append("shard ").append(shardIDX).append(" did not set sort field values (FieldDoc.fields is null); you must pass fillFields=true to IndexSearcher.search on each shard").toString());
					hitIDX++;
				} while (true);
			}

			SortField sortFields[] = sort.getSort();
			comparators = new FieldComparator[sortFields.length];
			reverseMul = new int[sortFields.length];
			for (int compIDX = 0; compIDX < sortFields.length; compIDX++)
			{
				SortField sortField = sortFields[compIDX];
				comparators[compIDX] = sortField.getComparator(1, compIDX);
				reverseMul[compIDX] = sortField.getReverse() ? -1 : 1;
			}

		}
	}

	private static class ScoreMergeSortQueue extends PriorityQueue
	{

		final ScoreDoc shardHits[][];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopDocs.desiredAssertionStatus();

		public boolean lessThan(ShardRef first, ShardRef second)
		{
			if (!$assertionsDisabled && first == second)
				throw new AssertionError();
			float firstScore = shardHits[first.shardIndex][first.hitIndex].score;
			float secondScore = shardHits[second.shardIndex][second.hitIndex].score;
			if (firstScore < secondScore)
				return false;
			if (firstScore > secondScore)
				return true;
			if (first.shardIndex < second.shardIndex)
				return true;
			if (first.shardIndex > second.shardIndex)
				return false;
			if (!$assertionsDisabled && first.hitIndex == second.hitIndex)
				throw new AssertionError();
			else
				return first.hitIndex < second.hitIndex;
		}

		public volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((ShardRef)x0, (ShardRef)x1);
		}


		public ScoreMergeSortQueue(TopDocs shardHits[])
		{
			super(shardHits.length);
			this.shardHits = new ScoreDoc[shardHits.length][];
			for (int shardIDX = 0; shardIDX < shardHits.length; shardIDX++)
				this.shardHits[shardIDX] = shardHits[shardIDX].scoreDocs;

		}
	}

	private static class ShardRef
	{

		final int shardIndex;
		int hitIndex;

		public String toString()
		{
			return (new StringBuilder()).append("ShardRef(shardIndex=").append(shardIndex).append(" hitIndex=").append(hitIndex).append(")").toString();
		}

		public ShardRef(int shardIndex)
		{
			this.shardIndex = shardIndex;
		}
	}


	public int totalHits;
	public ScoreDoc scoreDocs[];
	private float maxScore;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/TopDocs.desiredAssertionStatus();

	public float getMaxScore()
	{
		return maxScore;
	}

	public void setMaxScore(float maxScore)
	{
		this.maxScore = maxScore;
	}

	TopDocs(int totalHits, ScoreDoc scoreDocs[])
	{
		this(totalHits, scoreDocs, (0.0F / 0.0F));
	}

	public TopDocs(int totalHits, ScoreDoc scoreDocs[], float maxScore)
	{
		this.totalHits = totalHits;
		this.scoreDocs = scoreDocs;
		this.maxScore = maxScore;
	}

	public static TopDocs merge(Sort sort, int topN, TopDocs shardHits[])
		throws IOException
	{
		PriorityQueue queue;
		if (sort == null)
			queue = new ScoreMergeSortQueue(shardHits);
		else
			queue = new MergeSortQueue(sort, shardHits);
		int totalHitCount = 0;
		int availHitCount = 0;
		float maxScore = 1.401298E-045F;
		for (int shardIDX = 0; shardIDX < shardHits.length; shardIDX++)
		{
			TopDocs shard = shardHits[shardIDX];
			totalHitCount += shard.totalHits;
			if (shard.scoreDocs != null && shard.scoreDocs.length > 0)
			{
				availHitCount += shard.scoreDocs.length;
				queue.add(new ShardRef(shardIDX));
				maxScore = Math.max(maxScore, shard.getMaxScore());
			}
		}

		if (availHitCount == 0)
			maxScore = (0.0F / 0.0F);
		ScoreDoc hits[] = new ScoreDoc[Math.min(topN, availHitCount)];
		int hitUpto = 0;
		do
		{
			if (hitUpto >= hits.length)
				break;
			if (!$assertionsDisabled && queue.size() <= 0)
				throw new AssertionError();
			ShardRef ref = (ShardRef)queue.pop();
			ScoreDoc hit = shardHits[ref.shardIndex].scoreDocs[ref.hitIndex++];
			hit.shardIndex = ref.shardIndex;
			hits[hitUpto] = hit;
			hitUpto++;
			if (ref.hitIndex < shardHits[ref.shardIndex].scoreDocs.length)
				queue.add(ref);
		} while (true);
		if (sort == null)
			return new TopDocs(totalHitCount, hits, maxScore);
		else
			return new TopFieldDocs(totalHitCount, hits, sort.getSort(), maxScore);
	}

}
