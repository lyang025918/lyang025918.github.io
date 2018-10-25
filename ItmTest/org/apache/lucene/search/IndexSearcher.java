// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexSearcher.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.search:
//			FilteredQuery, FieldDoc, HitQueue, TopDocs, 
//			ScoreDoc, TopFieldDocs, TermStatistics, CollectionStatistics, 
//			Query, Weight, Sort, TopFieldCollector, 
//			Collector, TopScoreDocCollector, Scorer, Filter, 
//			Explanation

public class IndexSearcher
{
	public static class LeafSlice
	{

		final AtomicReaderContext leaves[];

		public transient LeafSlice(AtomicReaderContext leaves[])
		{
			this.leaves = leaves;
		}
	}

	private static final class ExecutionHelper
		implements Iterator, Iterable
	{

		private final CompletionService service;
		private int numTasks;

		public boolean hasNext()
		{
			return numTasks > 0;
		}

		public void submit(Callable task)
		{
			service.submit(task);
			numTasks++;
		}

		public Object next()
		{
			Exception exception;
			if (!hasNext())
				throw new NoSuchElementException();
			Object obj;
			try
			{
				obj = service.take().get();
			}
			catch (InterruptedException e)
			{
				throw new ThreadInterruptedException(e);
			}
			catch (ExecutionException e)
			{
				throw new RuntimeException(e);
			}
			finally
			{
				numTasks--;
			}
			numTasks--;
			return obj;
			throw exception;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public Iterator iterator()
		{
			return this;
		}

		ExecutionHelper(Executor executor)
		{
			service = new ExecutorCompletionService(executor);
		}
	}

	private static final class SearcherCallableWithSort
		implements Callable
	{
		private final class FakeScorer extends Scorer
		{

			float score;
			int doc;
			final SearcherCallableWithSort this$0;

			public int advance(int target)
			{
				throw new UnsupportedOperationException();
			}

			public int docID()
			{
				return doc;
			}

			public float freq()
			{
				throw new UnsupportedOperationException();
			}

			public int nextDoc()
			{
				throw new UnsupportedOperationException();
			}

			public float score()
			{
				return score;
			}

			public FakeScorer()
			{
				this$0 = SearcherCallableWithSort.this;
				super(null);
			}
		}


		private final Lock lock;
		private final IndexSearcher searcher;
		private final Weight weight;
		private final int nDocs;
		private final TopFieldCollector hq;
		private final Sort sort;
		private final LeafSlice slice;
		private final FieldDoc after;
		private final boolean doDocScores;
		private final boolean doMaxScore;
		private final FakeScorer fakeScorer = new FakeScorer();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/IndexSearcher.desiredAssertionStatus();

		public TopFieldDocs call()
			throws IOException
		{
			TopFieldDocs docs;
			if (!$assertionsDisabled && slice.leaves.length != 1)
				throw new AssertionError();
			docs = searcher.search(Arrays.asList(slice.leaves), weight, after, nDocs, sort, true, doDocScores, doMaxScore);
			lock.lock();
			AtomicReaderContext ctx = slice.leaves[0];
			int base = ctx.docBase;
			hq.setNextReader(ctx);
			hq.setScorer(fakeScorer);
			ScoreDoc arr$[] = docs.scoreDocs;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				ScoreDoc scoreDoc = arr$[i$];
				fakeScorer.doc = scoreDoc.doc - base;
				fakeScorer.score = scoreDoc.score;
				hq.collect(scoreDoc.doc - base);
			}

			if (doMaxScore && docs.getMaxScore() > hq.maxScore)
				hq.maxScore = docs.getMaxScore();
			lock.unlock();
			break MISSING_BLOCK_LABEL_245;
			Exception exception;
			exception;
			lock.unlock();
			throw exception;
			return docs;
		}

		public volatile Object call()
			throws Exception
		{
			return call();
		}


		public SearcherCallableWithSort(Lock lock, IndexSearcher searcher, LeafSlice slice, Weight weight, FieldDoc after, int nDocs, TopFieldCollector hq, 
				Sort sort, boolean doDocScores, boolean doMaxScore)
		{
			this.lock = lock;
			this.searcher = searcher;
			this.weight = weight;
			this.nDocs = nDocs;
			this.hq = hq;
			this.sort = sort;
			this.slice = slice;
			this.after = after;
			this.doDocScores = doDocScores;
			this.doMaxScore = doMaxScore;
		}
	}

	private static final class SearcherCallableNoSort
		implements Callable
	{

		private final Lock lock;
		private final IndexSearcher searcher;
		private final Weight weight;
		private final ScoreDoc after;
		private final int nDocs;
		private final HitQueue hq;
		private final LeafSlice slice;

		public TopDocs call()
			throws IOException
		{
			TopDocs docs;
			ScoreDoc scoreDocs[];
			docs = searcher.search(Arrays.asList(slice.leaves), weight, after, nDocs);
			scoreDocs = docs.scoreDocs;
			lock.lock();
			int j = 0;
			do
			{
				if (j >= scoreDocs.length)
					break;
				ScoreDoc scoreDoc = scoreDocs[j];
				if (scoreDoc == hq.insertWithOverflow(scoreDoc))
					break;
				j++;
			} while (true);
			lock.unlock();
			break MISSING_BLOCK_LABEL_106;
			Exception exception;
			exception;
			lock.unlock();
			throw exception;
			return docs;
		}

		public volatile Object call()
			throws Exception
		{
			return call();
		}

		public SearcherCallableNoSort(Lock lock, IndexSearcher searcher, LeafSlice slice, Weight weight, ScoreDoc after, int nDocs, HitQueue hq)
		{
			this.lock = lock;
			this.searcher = searcher;
			this.weight = weight;
			this.after = after;
			this.nDocs = nDocs;
			this.hq = hq;
			this.slice = slice;
		}
	}


	final IndexReader reader;
	protected final IndexReaderContext readerContext;
	protected final List leafContexts;
	protected final LeafSlice leafSlices[];
	private final ExecutorService executor;
	private static final Similarity defaultSimilarity = new DefaultSimilarity();
	private Similarity similarity;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/IndexSearcher.desiredAssertionStatus();

	public static Similarity getDefaultSimilarity()
	{
		return defaultSimilarity;
	}

	public IndexSearcher(IndexReader r)
	{
		this(r, null);
	}

	public IndexSearcher(IndexReader r, ExecutorService executor)
	{
		this(r.getContext(), executor);
	}

	public IndexSearcher(IndexReaderContext context, ExecutorService executor)
	{
		similarity = defaultSimilarity;
		if (!$assertionsDisabled && !context.isTopLevel)
		{
			throw new AssertionError((new StringBuilder()).append("IndexSearcher's ReaderContext must be topLevel for reader").append(context.reader()).toString());
		} else
		{
			reader = context.reader();
			this.executor = executor;
			readerContext = context;
			leafContexts = context.leaves();
			leafSlices = executor != null ? slices(leafContexts) : null;
			return;
		}
	}

	public IndexSearcher(IndexReaderContext context)
	{
		this(context, null);
	}

	protected LeafSlice[] slices(List leaves)
	{
		LeafSlice slices[] = new LeafSlice[leaves.size()];
		for (int i = 0; i < slices.length; i++)
			slices[i] = new LeafSlice(new AtomicReaderContext[] {
				(AtomicReaderContext)leaves.get(i)
			});

		return slices;
	}

	public IndexReader getIndexReader()
	{
		return reader;
	}

	public Document doc(int docID)
		throws IOException
	{
		return reader.document(docID);
	}

	public void doc(int docID, StoredFieldVisitor fieldVisitor)
		throws IOException
	{
		reader.document(docID, fieldVisitor);
	}

	public final Document document(int docID, Set fieldsToLoad)
		throws IOException
	{
		return reader.document(docID, fieldsToLoad);
	}

	public void setSimilarity(Similarity similarity)
	{
		this.similarity = similarity;
	}

	public Similarity getSimilarity()
	{
		return similarity;
	}

	protected Query wrapFilter(Query query, Filter filter)
	{
		return ((Query) (filter != null ? new FilteredQuery(query, filter) : query));
	}

	public TopDocs searchAfter(ScoreDoc after, Query query, int n)
		throws IOException
	{
		return search(createNormalizedWeight(query), after, n);
	}

	public TopDocs searchAfter(ScoreDoc after, Query query, Filter filter, int n)
		throws IOException
	{
		return search(createNormalizedWeight(wrapFilter(query, filter)), after, n);
	}

	public TopDocs search(Query query, int n)
		throws IOException
	{
		return search(query, ((Filter) (null)), n);
	}

	public TopDocs search(Query query, Filter filter, int n)
		throws IOException
	{
		return search(createNormalizedWeight(wrapFilter(query, filter)), ((ScoreDoc) (null)), n);
	}

	public void search(Query query, Filter filter, Collector results)
		throws IOException
	{
		search(leafContexts, createNormalizedWeight(wrapFilter(query, filter)), results);
	}

	public void search(Query query, Collector results)
		throws IOException
	{
		search(leafContexts, createNormalizedWeight(query), results);
	}

	public TopFieldDocs search(Query query, Filter filter, int n, Sort sort)
		throws IOException
	{
		return search(createNormalizedWeight(wrapFilter(query, filter)), n, sort, false, false);
	}

	public TopFieldDocs search(Query query, Filter filter, int n, Sort sort, boolean doDocScores, boolean doMaxScore)
		throws IOException
	{
		return search(createNormalizedWeight(wrapFilter(query, filter)), n, sort, doDocScores, doMaxScore);
	}

	public TopDocs searchAfter(ScoreDoc after, Query query, Filter filter, int n, Sort sort)
		throws IOException
	{
		if (after != null && !(after instanceof FieldDoc))
			throw new IllegalArgumentException((new StringBuilder()).append("after must be a FieldDoc; got ").append(after).toString());
		else
			return search(createNormalizedWeight(wrapFilter(query, filter)), (FieldDoc)after, n, sort, true, false, false);
	}

	public TopFieldDocs search(Query query, int n, Sort sort)
		throws IOException
	{
		return search(createNormalizedWeight(query), n, sort, false, false);
	}

	public TopDocs searchAfter(ScoreDoc after, Query query, int n, Sort sort)
		throws IOException
	{
		if (after != null && !(after instanceof FieldDoc))
			throw new IllegalArgumentException((new StringBuilder()).append("after must be a FieldDoc; got ").append(after).toString());
		else
			return search(createNormalizedWeight(query), (FieldDoc)after, n, sort, true, false, false);
	}

	public TopDocs searchAfter(ScoreDoc after, Query query, Filter filter, int n, Sort sort, boolean doDocScores, boolean doMaxScore)
		throws IOException
	{
		if (after != null && !(after instanceof FieldDoc))
			throw new IllegalArgumentException((new StringBuilder()).append("after must be a FieldDoc; got ").append(after).toString());
		else
			return search(createNormalizedWeight(wrapFilter(query, filter)), (FieldDoc)after, n, sort, true, doDocScores, doMaxScore);
	}

	protected TopDocs search(Weight weight, ScoreDoc after, int nDocs)
		throws IOException
	{
		if (executor == null)
			return search(leafContexts, weight, after, nDocs);
		HitQueue hq = new HitQueue(nDocs, false);
		Lock lock = new ReentrantLock();
		ExecutionHelper runner = new ExecutionHelper(executor);
		for (int i = 0; i < leafSlices.length; i++)
			runner.submit(new SearcherCallableNoSort(lock, this, leafSlices[i], weight, after, nDocs, hq));

		int totalHits = 0;
		float maxScore = (-1.0F / 0.0F);
		Iterator i$ = runner.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TopDocs topDocs = (TopDocs)i$.next();
			if (topDocs.totalHits != 0)
			{
				totalHits += topDocs.totalHits;
				maxScore = Math.max(maxScore, topDocs.getMaxScore());
			}
		} while (true);
		ScoreDoc scoreDocs[] = new ScoreDoc[hq.size()];
		for (int i = hq.size() - 1; i >= 0; i--)
			scoreDocs[i] = (ScoreDoc)hq.pop();

		return new TopDocs(totalHits, scoreDocs, maxScore);
	}

	protected TopDocs search(List leaves, Weight weight, ScoreDoc after, int nDocs)
		throws IOException
	{
		int limit = reader.maxDoc();
		if (limit == 0)
			limit = 1;
		nDocs = Math.min(nDocs, limit);
		TopScoreDocCollector collector = TopScoreDocCollector.create(nDocs, after, !weight.scoresDocsOutOfOrder());
		search(leaves, weight, ((Collector) (collector)));
		return collector.topDocs();
	}

	protected TopFieldDocs search(Weight weight, int nDocs, Sort sort, boolean doDocScores, boolean doMaxScore)
		throws IOException
	{
		return search(weight, null, nDocs, sort, true, doDocScores, doMaxScore);
	}

	protected TopFieldDocs search(Weight weight, FieldDoc after, int nDocs, Sort sort, boolean fillFields, boolean doDocScores, boolean doMaxScore)
		throws IOException
	{
		if (sort == null)
			throw new NullPointerException();
		if (executor == null)
			return search(leafContexts, weight, after, nDocs, sort, fillFields, doDocScores, doMaxScore);
		TopFieldCollector topCollector = TopFieldCollector.create(sort, nDocs, after, fillFields, doDocScores, doMaxScore, false);
		Lock lock = new ReentrantLock();
		ExecutionHelper runner = new ExecutionHelper(executor);
		for (int i = 0; i < leafSlices.length; i++)
			runner.submit(new SearcherCallableWithSort(lock, this, leafSlices[i], weight, after, nDocs, topCollector, sort, doDocScores, doMaxScore));

		int totalHits = 0;
		float maxScore = (-1.0F / 0.0F);
		Iterator i$ = runner.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			TopFieldDocs topFieldDocs = (TopFieldDocs)i$.next();
			if (topFieldDocs.totalHits != 0)
			{
				totalHits += topFieldDocs.totalHits;
				maxScore = Math.max(maxScore, topFieldDocs.getMaxScore());
			}
		} while (true);
		TopFieldDocs topDocs = (TopFieldDocs)topCollector.topDocs();
		return new TopFieldDocs(totalHits, topDocs.scoreDocs, topDocs.fields, topDocs.getMaxScore());
	}

	protected TopFieldDocs search(List leaves, Weight weight, FieldDoc after, int nDocs, Sort sort, boolean fillFields, boolean doDocScores, 
			boolean doMaxScore)
		throws IOException
	{
		int limit = reader.maxDoc();
		if (limit == 0)
			limit = 1;
		nDocs = Math.min(nDocs, limit);
		TopFieldCollector collector = TopFieldCollector.create(sort, nDocs, after, fillFields, doDocScores, doMaxScore, !weight.scoresDocsOutOfOrder());
		search(leaves, weight, ((Collector) (collector)));
		return (TopFieldDocs)collector.topDocs();
	}

	protected void search(List leaves, Weight weight, Collector collector)
		throws IOException
	{
		Iterator i$ = leaves.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReaderContext ctx = (AtomicReaderContext)i$.next();
			collector.setNextReader(ctx);
			Scorer scorer = weight.scorer(ctx, !collector.acceptsDocsOutOfOrder(), true, ctx.reader().getLiveDocs());
			if (scorer != null)
				scorer.score(collector);
		} while (true);
	}

	public Query rewrite(Query original)
		throws IOException
	{
		Query query = original;
		for (Query rewrittenQuery = query.rewrite(reader); rewrittenQuery != query; rewrittenQuery = query.rewrite(reader))
			query = rewrittenQuery;

		return query;
	}

	public Explanation explain(Query query, int doc)
		throws IOException
	{
		return explain(createNormalizedWeight(query), doc);
	}

	protected Explanation explain(Weight weight, int doc)
		throws IOException
	{
		int n = ReaderUtil.subIndex(doc, leafContexts);
		AtomicReaderContext ctx = (AtomicReaderContext)leafContexts.get(n);
		int deBasedDoc = doc - ctx.docBase;
		return weight.explain(ctx, deBasedDoc);
	}

	public Weight createNormalizedWeight(Query query)
		throws IOException
	{
		query = rewrite(query);
		Weight weight = query.createWeight(this);
		float v = weight.getValueForNormalization();
		float norm = getSimilarity().queryNorm(v);
		if (Float.isInfinite(norm) || Float.isNaN(norm))
			norm = 1.0F;
		weight.normalize(norm, 1.0F);
		return weight;
	}

	public IndexReaderContext getTopReaderContext()
	{
		return readerContext;
	}

	public String toString()
	{
		return (new StringBuilder()).append("IndexSearcher(").append(reader).append("; executor=").append(executor).append(")").toString();
	}

	public TermStatistics termStatistics(Term term, TermContext context)
		throws IOException
	{
		return new TermStatistics(term.bytes(), context.docFreq(), context.totalTermFreq());
	}

	public CollectionStatistics collectionStatistics(String field)
		throws IOException
	{
		if (!$assertionsDisabled && field == null)
			throw new AssertionError();
		Terms terms = MultiFields.getTerms(reader, field);
		int docCount;
		long sumTotalTermFreq;
		long sumDocFreq;
		if (terms == null)
		{
			docCount = 0;
			sumTotalTermFreq = 0L;
			sumDocFreq = 0L;
		} else
		{
			docCount = terms.getDocCount();
			sumTotalTermFreq = terms.getSumTotalTermFreq();
			sumDocFreq = terms.getSumDocFreq();
		}
		return new CollectionStatistics(field, reader.maxDoc(), docCount, sumTotalTermFreq, sumDocFreq);
	}

}
