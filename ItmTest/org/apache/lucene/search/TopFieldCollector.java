// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopFieldCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			TopDocsCollector, FieldValueHitQueue, FieldDoc, TopFieldDocs, 
//			ScoreDoc, Sort, TopDocs, FieldComparator, 
//			Scorer

public abstract class TopFieldCollector extends TopDocsCollector
{
	private static final class PagingFieldCollector extends TopFieldCollector
	{

		Scorer scorer;
		int collectedHits;
		final FieldComparator comparators[];
		final int reverseMul[];
		final FieldValueHitQueue queue;
		final boolean trackDocScores;
		final boolean trackMaxScore;
		final FieldDoc after;
		int afterDoc;

		void updateBottom(int doc, float score)
		{
			bottom.doc = docBase + doc;
			bottom.score = score;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			boolean sameValues = true;
			int compIDX = 0;
			do
			{
				if (compIDX >= comparators.length)
					break;
				FieldComparator comp = comparators[compIDX];
				int cmp = reverseMul[compIDX] * comp.compareDocToValue(doc, after.fields[compIDX]);
				if (cmp < 0)
					return;
				if (cmp > 0)
				{
					sameValues = false;
					break;
				}
				compIDX++;
			} while (true);
			if (sameValues && doc <= afterDoc)
				return;
			collectedHits++;
			float score = (0.0F / 0.0F);
			if (trackMaxScore)
			{
				score = scorer.score();
				if (score > maxScore)
					maxScore = score;
			}
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
					{
						if (doc + docBase > bottom.doc)
							return;
						break;
					}
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				if (trackDocScores && !trackMaxScore)
					score = scorer.score();
				updateBottom(doc, score);
				for (i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = collectedHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				if (trackDocScores && !trackMaxScore)
					score = scorer.score();
				bottom = (FieldValueHitQueue.Entry)pq.add(new FieldValueHitQueue.Entry(slot, docBase + doc, score));
				queueFull = collectedHits == numHits;
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public void setScorer(Scorer scorer)
		{
			this.scorer = scorer;
			for (int i = 0; i < comparators.length; i++)
				comparators[i].setScorer(scorer);

		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public void setNextReader(AtomicReaderContext context)
			throws IOException
		{
			docBase = context.docBase;
			afterDoc = after.doc - docBase;
			for (int i = 0; i < comparators.length; i++)
				queue.setComparator(i, comparators[i].setNextReader(context));

		}

		public PagingFieldCollector(FieldValueHitQueue queue, FieldDoc after, int numHits, boolean fillFields, boolean trackDocScores, boolean trackMaxScore)
		{
			super(queue, numHits, fillFields, null);
			this.queue = queue;
			this.trackDocScores = trackDocScores;
			this.trackMaxScore = trackMaxScore;
			this.after = after;
			comparators = queue.getComparators();
			reverseMul = queue.getReverseMul();
			maxScore = (-1.0F / 0.0F);
		}
	}

	private static final class OutOfOrderMultiComparatorScoringNoMaxScoreCollector extends MultiComparatorScoringNoMaxScoreCollector
	{

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
					{
						if (doc + docBase > bottom.doc)
							return;
						break;
					}
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				float score = scorer.score();
				updateBottom(doc, score);
				for (int i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				float score = scorer.score();
				add(slot, doc, score);
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			super.setScorer(scorer);
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderMultiComparatorScoringNoMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class MultiComparatorScoringNoMaxScoreCollector extends MultiComparatorNonScoringCollector
	{

		Scorer scorer;

		final void updateBottom(int doc, float score)
		{
			bottom.doc = docBase + doc;
			bottom.score = score;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
						return;
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				float score = scorer.score();
				updateBottom(doc, score);
				for (int i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				float score = scorer.score();
				add(slot, doc, score);
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			super.setScorer(scorer);
		}

		public MultiComparatorScoringNoMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static final class OutOfOrderMultiComparatorScoringMaxScoreCollector extends MultiComparatorScoringMaxScoreCollector
	{

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (score > maxScore)
				maxScore = score;
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
					{
						if (doc + docBase > bottom.doc)
							return;
						break;
					}
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				updateBottom(doc, score);
				for (i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				add(slot, doc, score);
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderMultiComparatorScoringMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class MultiComparatorScoringMaxScoreCollector extends MultiComparatorNonScoringCollector
	{

		Scorer scorer;

		final void updateBottom(int doc, float score)
		{
			bottom.doc = docBase + doc;
			bottom.score = score;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (score > maxScore)
				maxScore = score;
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
						return;
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				updateBottom(doc, score);
				for (i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				add(slot, doc, score);
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			super.setScorer(scorer);
		}

		public MultiComparatorScoringMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
			maxScore = (-1.0F / 0.0F);
		}
	}

	private static class OutOfOrderMultiComparatorNonScoringCollector extends MultiComparatorNonScoringCollector
	{

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
					{
						if (doc + docBase > bottom.doc)
							return;
						break;
					}
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				updateBottom(doc);
				for (i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				add(slot, doc, (0.0F / 0.0F));
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderMultiComparatorNonScoringCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class MultiComparatorNonScoringCollector extends TopFieldCollector
	{

		final FieldComparator comparators[];
		final int reverseMul[];
		final FieldValueHitQueue queue;

		final void updateBottom(int doc)
		{
			bottom.doc = docBase + doc;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int i = 0;
				do
				{
					int c = reverseMul[i] * comparators[i].compareBottom(doc);
					if (c < 0)
						return;
					if (c > 0)
						break;
					if (i == comparators.length - 1)
						return;
					i++;
				} while (true);
				for (i = 0; i < comparators.length; i++)
					comparators[i].copy(bottom.slot, doc);

				updateBottom(doc);
				for (i = 0; i < comparators.length; i++)
					comparators[i].setBottom(bottom.slot);

			} else
			{
				int slot = totalHits - 1;
				for (int i = 0; i < comparators.length; i++)
					comparators[i].copy(slot, doc);

				add(slot, doc, (0.0F / 0.0F));
				if (queueFull)
				{
					for (int i = 0; i < comparators.length; i++)
						comparators[i].setBottom(bottom.slot);

				}
			}
		}

		public void setNextReader(AtomicReaderContext context)
			throws IOException
		{
			docBase = context.docBase;
			for (int i = 0; i < comparators.length; i++)
				queue.setComparator(i, comparators[i].setNextReader(context));

		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			for (int i = 0; i < comparators.length; i++)
				comparators[i].setScorer(scorer);

		}

		public MultiComparatorNonScoringCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields, null);
			this.queue = queue;
			comparators = queue.getComparators();
			reverseMul = queue.getReverseMul();
		}
	}

	private static class OutOfOrderOneComparatorScoringMaxScoreCollector extends OneComparatorScoringMaxScoreCollector
	{

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (score > maxScore)
				maxScore = score;
			totalHits++;
			if (queueFull)
			{
				int cmp = reverseMul * comparator.compareBottom(doc);
				if (cmp < 0 || cmp == 0 && doc + docBase > bottom.doc)
					return;
				comparator.copy(bottom.slot, doc);
				updateBottom(doc, score);
				comparator.setBottom(bottom.slot);
			} else
			{
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, score);
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderOneComparatorScoringMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class OneComparatorScoringMaxScoreCollector extends OneComparatorNonScoringCollector
	{

		Scorer scorer;

		final void updateBottom(int doc, float score)
		{
			bottom.doc = docBase + doc;
			bottom.score = score;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (score > maxScore)
				maxScore = score;
			totalHits++;
			if (queueFull)
			{
				if (reverseMul * comparator.compareBottom(doc) <= 0)
					return;
				comparator.copy(bottom.slot, doc);
				updateBottom(doc, score);
				comparator.setBottom(bottom.slot);
			} else
			{
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, score);
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			super.setScorer(scorer);
		}

		public OneComparatorScoringMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
			maxScore = (-1.0F / 0.0F);
		}
	}

	private static class OutOfOrderOneComparatorScoringNoMaxScoreCollector extends OneComparatorScoringNoMaxScoreCollector
	{

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int cmp = reverseMul * comparator.compareBottom(doc);
				if (cmp < 0 || cmp == 0 && doc + docBase > bottom.doc)
					return;
				float score = scorer.score();
				comparator.copy(bottom.slot, doc);
				updateBottom(doc, score);
				comparator.setBottom(bottom.slot);
			} else
			{
				float score = scorer.score();
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, score);
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderOneComparatorScoringNoMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class OneComparatorScoringNoMaxScoreCollector extends OneComparatorNonScoringCollector
	{

		Scorer scorer;

		final void updateBottom(int doc, float score)
		{
			bottom.doc = docBase + doc;
			bottom.score = score;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				if (reverseMul * comparator.compareBottom(doc) <= 0)
					return;
				float score = scorer.score();
				comparator.copy(bottom.slot, doc);
				updateBottom(doc, score);
				comparator.setBottom(bottom.slot);
			} else
			{
				float score = scorer.score();
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, score);
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			comparator.setScorer(scorer);
		}

		public OneComparatorScoringNoMaxScoreCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class OutOfOrderOneComparatorNonScoringCollector extends OneComparatorNonScoringCollector
	{

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				int cmp = reverseMul * comparator.compareBottom(doc);
				if (cmp < 0 || cmp == 0 && doc + docBase > bottom.doc)
					return;
				comparator.copy(bottom.slot, doc);
				updateBottom(doc);
				comparator.setBottom(bottom.slot);
			} else
			{
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, (0.0F / 0.0F));
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public OutOfOrderOneComparatorNonScoringCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields);
		}
	}

	private static class OneComparatorNonScoringCollector extends TopFieldCollector
	{

		FieldComparator comparator;
		final int reverseMul;
		final FieldValueHitQueue queue;

		final void updateBottom(int doc)
		{
			bottom.doc = docBase + doc;
			bottom = (FieldValueHitQueue.Entry)pq.updateTop();
		}

		public void collect(int doc)
			throws IOException
		{
			totalHits++;
			if (queueFull)
			{
				if (reverseMul * comparator.compareBottom(doc) <= 0)
					return;
				comparator.copy(bottom.slot, doc);
				updateBottom(doc);
				comparator.setBottom(bottom.slot);
			} else
			{
				int slot = totalHits - 1;
				comparator.copy(slot, doc);
				add(slot, doc, (0.0F / 0.0F));
				if (queueFull)
					comparator.setBottom(bottom.slot);
			}
		}

		public void setNextReader(AtomicReaderContext context)
			throws IOException
		{
			docBase = context.docBase;
			queue.setComparator(0, comparator.setNextReader(context));
			comparator = queue.firstComparator;
		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			comparator.setScorer(scorer);
		}

		public OneComparatorNonScoringCollector(FieldValueHitQueue queue, int numHits, boolean fillFields)
		{
			super(queue, numHits, fillFields, null);
			this.queue = queue;
			comparator = queue.getComparators()[0];
			reverseMul = queue.getReverseMul()[0];
		}
	}


	private static final ScoreDoc EMPTY_SCOREDOCS[] = new ScoreDoc[0];
	private final boolean fillFields;
	float maxScore;
	final int numHits;
	FieldValueHitQueue.Entry bottom;
	boolean queueFull;
	int docBase;

	private TopFieldCollector(PriorityQueue pq, int numHits, boolean fillFields)
	{
		super(pq);
		maxScore = (0.0F / 0.0F);
		bottom = null;
		this.numHits = numHits;
		this.fillFields = fillFields;
	}

	public static TopFieldCollector create(Sort sort, int numHits, boolean fillFields, boolean trackDocScores, boolean trackMaxScore, boolean docsScoredInOrder)
		throws IOException
	{
		return create(sort, numHits, null, fillFields, trackDocScores, trackMaxScore, docsScoredInOrder);
	}

	public static TopFieldCollector create(Sort sort, int numHits, FieldDoc after, boolean fillFields, boolean trackDocScores, boolean trackMaxScore, boolean docsScoredInOrder)
		throws IOException
	{
		if (sort.fields.length == 0)
			throw new IllegalArgumentException("Sort must contain at least one field");
		if (numHits <= 0)
			throw new IllegalArgumentException("numHits must be > 0; please use TotalHitCountCollector if you just need the total hit count");
		FieldValueHitQueue queue = FieldValueHitQueue.create(sort.fields, numHits);
		if (after == null)
		{
			if (queue.getComparators().length == 1)
			{
				if (docsScoredInOrder)
				{
					if (trackMaxScore)
						return new OneComparatorScoringMaxScoreCollector(queue, numHits, fillFields);
					if (trackDocScores)
						return new OneComparatorScoringNoMaxScoreCollector(queue, numHits, fillFields);
					else
						return new OneComparatorNonScoringCollector(queue, numHits, fillFields);
				}
				if (trackMaxScore)
					return new OutOfOrderOneComparatorScoringMaxScoreCollector(queue, numHits, fillFields);
				if (trackDocScores)
					return new OutOfOrderOneComparatorScoringNoMaxScoreCollector(queue, numHits, fillFields);
				else
					return new OutOfOrderOneComparatorNonScoringCollector(queue, numHits, fillFields);
			}
			if (docsScoredInOrder)
			{
				if (trackMaxScore)
					return new MultiComparatorScoringMaxScoreCollector(queue, numHits, fillFields);
				if (trackDocScores)
					return new MultiComparatorScoringNoMaxScoreCollector(queue, numHits, fillFields);
				else
					return new MultiComparatorNonScoringCollector(queue, numHits, fillFields);
			}
			if (trackMaxScore)
				return new OutOfOrderMultiComparatorScoringMaxScoreCollector(queue, numHits, fillFields);
			if (trackDocScores)
				return new OutOfOrderMultiComparatorScoringNoMaxScoreCollector(queue, numHits, fillFields);
			else
				return new OutOfOrderMultiComparatorNonScoringCollector(queue, numHits, fillFields);
		}
		if (after.fields == null)
			throw new IllegalArgumentException("after.fields wasn't set; you must pass fillFields=true for the previous search");
		if (after.fields.length != sort.getSort().length)
			throw new IllegalArgumentException((new StringBuilder()).append("after.fields has ").append(after.fields.length).append(" values but sort has ").append(sort.getSort().length).toString());
		else
			return new PagingFieldCollector(queue, after, numHits, fillFields, trackDocScores, trackMaxScore);
	}

	final void add(int slot, int doc, float score)
	{
		bottom = (FieldValueHitQueue.Entry)pq.add(new FieldValueHitQueue.Entry(slot, docBase + doc, score));
		queueFull = totalHits == numHits;
	}

	protected void populateResults(ScoreDoc results[], int howMany)
	{
		if (fillFields)
		{
			FieldValueHitQueue queue = (FieldValueHitQueue)pq;
			for (int i = howMany - 1; i >= 0; i--)
				results[i] = queue.fillFields((FieldValueHitQueue.Entry)queue.pop());

		} else
		{
			for (int i = howMany - 1; i >= 0; i--)
			{
				FieldValueHitQueue.Entry entry = (FieldValueHitQueue.Entry)pq.pop();
				results[i] = new FieldDoc(entry.doc, entry.score);
			}

		}
	}

	protected TopDocs newTopDocs(ScoreDoc results[], int start)
	{
		if (results == null)
		{
			results = EMPTY_SCOREDOCS;
			maxScore = (0.0F / 0.0F);
		}
		return new TopFieldDocs(totalHits, results, ((FieldValueHitQueue)pq).getFields(), maxScore);
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return false;
	}


}
