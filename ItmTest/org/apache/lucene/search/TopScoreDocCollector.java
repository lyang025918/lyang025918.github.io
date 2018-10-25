// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopScoreDocCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			TopDocsCollector, HitQueue, ScoreDoc, TopDocs, 
//			Scorer

public abstract class TopScoreDocCollector extends TopDocsCollector
{
	private static class OutOfOrderPagingScoreDocCollector extends TopScoreDocCollector
	{

		private final ScoreDoc after;
		private int afterDoc;
		private int collectedHits;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopScoreDocCollector.desiredAssertionStatus();

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (!$assertionsDisabled && Float.isNaN(score))
				throw new AssertionError();
			totalHits++;
			if (score > after.score || score == after.score && doc <= afterDoc)
				return;
			if (score < pqTop.score)
				return;
			doc += docBase;
			if (score == pqTop.score && doc > pqTop.doc)
			{
				return;
			} else
			{
				collectedHits++;
				pqTop.doc = doc;
				pqTop.score = score;
				pqTop = (ScoreDoc)pq.updateTop();
				return;
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public void setNextReader(AtomicReaderContext context)
		{
			setNextReader(context);
			afterDoc = after.doc - docBase;
		}

		protected int topDocsSize()
		{
			return collectedHits >= pq.size() ? pq.size() : collectedHits;
		}

		protected TopDocs newTopDocs(ScoreDoc results[], int start)
		{
			return results != null ? new TopDocs(totalHits, results) : new TopDocs(totalHits, new ScoreDoc[0], (0.0F / 0.0F));
		}


		private OutOfOrderPagingScoreDocCollector(ScoreDoc after, int numHits)
		{
			super(numHits, null);
			this.after = after;
		}

	}

	private static class OutOfOrderTopScoreDocCollector extends TopScoreDocCollector
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopScoreDocCollector.desiredAssertionStatus();

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (!$assertionsDisabled && Float.isNaN(score))
				throw new AssertionError();
			totalHits++;
			if (score < pqTop.score)
				return;
			doc += docBase;
			if (score == pqTop.score && doc > pqTop.doc)
			{
				return;
			} else
			{
				pqTop.doc = doc;
				pqTop.score = score;
				pqTop = (ScoreDoc)pq.updateTop();
				return;
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}


		private OutOfOrderTopScoreDocCollector(int numHits)
		{
			super(numHits, null);
		}

	}

	private static class InOrderPagingScoreDocCollector extends TopScoreDocCollector
	{

		private final ScoreDoc after;
		private int afterDoc;
		private int collectedHits;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopScoreDocCollector.desiredAssertionStatus();

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (!$assertionsDisabled && score == (-1.0F / 0.0F))
				throw new AssertionError();
			if (!$assertionsDisabled && Float.isNaN(score))
				throw new AssertionError();
			totalHits++;
			if (score > after.score || score == after.score && doc <= afterDoc)
				return;
			if (score <= pqTop.score)
			{
				return;
			} else
			{
				collectedHits++;
				pqTop.doc = doc + docBase;
				pqTop.score = score;
				pqTop = (ScoreDoc)pq.updateTop();
				return;
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return false;
		}

		public void setNextReader(AtomicReaderContext context)
		{
			setNextReader(context);
			afterDoc = after.doc - docBase;
		}

		protected int topDocsSize()
		{
			return collectedHits >= pq.size() ? pq.size() : collectedHits;
		}

		protected TopDocs newTopDocs(ScoreDoc results[], int start)
		{
			return results != null ? new TopDocs(totalHits, results) : new TopDocs(totalHits, new ScoreDoc[0], (0.0F / 0.0F));
		}


		private InOrderPagingScoreDocCollector(ScoreDoc after, int numHits)
		{
			super(numHits, null);
			this.after = after;
		}

	}

	private static class InOrderTopScoreDocCollector extends TopScoreDocCollector
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopScoreDocCollector.desiredAssertionStatus();

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (!$assertionsDisabled && score == (-1.0F / 0.0F))
				throw new AssertionError();
			if (!$assertionsDisabled && Float.isNaN(score))
				throw new AssertionError();
			if (Float.isNaN(score))
				score = 0.0F;
			totalHits++;
			if (score <= pqTop.score)
			{
				return;
			} else
			{
				pqTop.doc = doc + docBase;
				pqTop.score = score;
				pqTop = (ScoreDoc)pq.updateTop();
				return;
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return false;
		}


		private InOrderTopScoreDocCollector(int numHits)
		{
			super(numHits, null);
		}

	}


	ScoreDoc pqTop;
	int docBase;
	Scorer scorer;

	public static TopScoreDocCollector create(int numHits, boolean docsScoredInOrder)
	{
		return create(numHits, null, docsScoredInOrder);
	}

	public static TopScoreDocCollector create(int numHits, ScoreDoc after, boolean docsScoredInOrder)
	{
		if (numHits <= 0)
			throw new IllegalArgumentException("numHits must be > 0; please use TotalHitCountCollector if you just need the total hit count");
		if (docsScoredInOrder)
			return ((TopScoreDocCollector) (after != null ? new InOrderPagingScoreDocCollector(after, numHits) : new InOrderTopScoreDocCollector(numHits)));
		else
			return ((TopScoreDocCollector) (after != null ? new OutOfOrderPagingScoreDocCollector(after, numHits) : new OutOfOrderTopScoreDocCollector(numHits)));
	}

	private TopScoreDocCollector(int numHits)
	{
		super(new HitQueue(numHits, true));
		docBase = 0;
		pqTop = (ScoreDoc)pq.top();
	}

	protected TopDocs newTopDocs(ScoreDoc results[], int start)
	{
		if (results == null)
			return EMPTY_TOPDOCS;
		float maxScore = (0.0F / 0.0F);
		if (start == 0)
		{
			maxScore = results[0].score;
		} else
		{
			for (int i = pq.size(); i > 1; i--)
				pq.pop();

			maxScore = ((ScoreDoc)pq.pop()).score;
		}
		return new TopDocs(totalHits, results, maxScore);
	}

	public void setNextReader(AtomicReaderContext context)
	{
		docBase = context.docBase;
	}

	public void setScorer(Scorer scorer)
		throws IOException
	{
		this.scorer = scorer;
	}

}
