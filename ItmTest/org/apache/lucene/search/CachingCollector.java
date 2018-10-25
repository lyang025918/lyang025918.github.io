// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CachingCollector.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;

// Referenced classes of package org.apache.lucene.search:
//			Collector, Scorer

public abstract class CachingCollector extends Collector
{
	private static final class NoScoreCachingCollector extends CachingCollector
	{

		public void collect(int doc)
			throws IOException
		{
			if (curDocs == null)
			{
				other.collect(doc);
				return;
			}
			if (upto == curDocs.length)
			{
				base += upto;
				int nextLength = 8 * curDocs.length;
				if (nextLength > 0x80000)
					nextLength = 0x80000;
				if (base + nextLength > maxDocsToCache)
				{
					nextLength = maxDocsToCache - base;
					if (nextLength <= 0)
					{
						curDocs = null;
						cachedSegs.clear();
						cachedDocs.clear();
						other.collect(doc);
						return;
					}
				}
				curDocs = new int[nextLength];
				cachedDocs.add(curDocs);
				upto = 0;
			}
			curDocs[upto] = doc;
			upto++;
			other.collect(doc);
		}

		public void replay(Collector other)
			throws IOException
		{
			replayInit(other);
			int curUpto = 0;
			int curbase = 0;
			int chunkUpto = 0;
			curDocs = CachingCollector.EMPTY_INT_ARRAY;
			for (Iterator i$ = cachedSegs.iterator(); i$.hasNext();)
			{
				SegStart seg = (SegStart)i$.next();
				other.setNextReader(seg.readerContext);
				while (curbase + curUpto < seg.end) 
				{
					if (curUpto == curDocs.length)
					{
						curbase += curDocs.length;
						curDocs = (int[])cachedDocs.get(chunkUpto);
						chunkUpto++;
						curUpto = 0;
					}
					other.collect(curDocs[curUpto++]);
				}
			}

		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			other.setScorer(scorer);
		}

		public String toString()
		{
			if (isCached())
				return (new StringBuilder()).append("CachingCollector (").append(base + upto).append(" docs cached)").toString();
			else
				return "CachingCollector (cache was cleared)";
		}

		NoScoreCachingCollector(Collector other, double maxRAMMB)
		{
			super(other, maxRAMMB, false, null);
		}

		NoScoreCachingCollector(Collector other, int maxDocsToCache)
		{
			super(other, maxDocsToCache, null);
		}
	}

	private static final class ScoreCachingCollector extends CachingCollector
	{

		private final CachedScorer cachedScorer;
		private final List cachedScores;
		private Scorer scorer;
		private float curScores[];

		public void collect(int doc)
			throws IOException
		{
			if (curDocs == null)
			{
				cachedScorer.score = scorer.score();
				cachedScorer.doc = doc;
				other.collect(doc);
				return;
			}
			if (upto == curDocs.length)
			{
				base += upto;
				int nextLength = 8 * curDocs.length;
				if (nextLength > 0x80000)
					nextLength = 0x80000;
				if (base + nextLength > maxDocsToCache)
				{
					nextLength = maxDocsToCache - base;
					if (nextLength <= 0)
					{
						curDocs = null;
						curScores = null;
						cachedSegs.clear();
						cachedDocs.clear();
						cachedScores.clear();
						cachedScorer.score = scorer.score();
						cachedScorer.doc = doc;
						other.collect(doc);
						return;
					}
				}
				curDocs = new int[nextLength];
				cachedDocs.add(curDocs);
				curScores = new float[nextLength];
				cachedScores.add(curScores);
				upto = 0;
			}
			curDocs[upto] = doc;
			cachedScorer.score = curScores[upto] = scorer.score();
			upto++;
			cachedScorer.doc = doc;
			other.collect(doc);
		}

		public void replay(Collector other)
			throws IOException
		{
			replayInit(other);
			int curUpto = 0;
			int curBase = 0;
			int chunkUpto = 0;
			curDocs = CachingCollector.EMPTY_INT_ARRAY;
			for (Iterator i$ = cachedSegs.iterator(); i$.hasNext();)
			{
				SegStart seg = (SegStart)i$.next();
				other.setNextReader(seg.readerContext);
				other.setScorer(cachedScorer);
				while (curBase + curUpto < seg.end) 
				{
					if (curUpto == curDocs.length)
					{
						curBase += curDocs.length;
						curDocs = (int[])cachedDocs.get(chunkUpto);
						curScores = (float[])cachedScores.get(chunkUpto);
						chunkUpto++;
						curUpto = 0;
					}
					cachedScorer.score = curScores[curUpto];
					cachedScorer.doc = curDocs[curUpto];
					other.collect(curDocs[curUpto++]);
				}
			}

		}

		public void setScorer(Scorer scorer)
			throws IOException
		{
			this.scorer = scorer;
			other.setScorer(cachedScorer);
		}

		public String toString()
		{
			if (isCached())
				return (new StringBuilder()).append("CachingCollector (").append(base + upto).append(" docs & scores cached)").toString();
			else
				return "CachingCollector (cache was cleared)";
		}

		ScoreCachingCollector(Collector other, double maxRAMMB)
		{
			super(other, maxRAMMB, true, null);
			cachedScorer = new CachedScorer();
			cachedScores = new ArrayList();
			curScores = new float[128];
			cachedScores.add(curScores);
		}

		ScoreCachingCollector(Collector other, int maxDocsToCache)
		{
			super(other, maxDocsToCache, null);
			cachedScorer = new CachedScorer();
			cachedScores = new ArrayList();
			curScores = new float[128];
			cachedScores.add(curScores);
		}
	}

	private static final class CachedScorer extends Scorer
	{

		int doc;
		float score;

		public final float score()
		{
			return score;
		}

		public final int advance(int target)
		{
			throw new UnsupportedOperationException();
		}

		public final int docID()
		{
			return doc;
		}

		public final float freq()
		{
			throw new UnsupportedOperationException();
		}

		public final int nextDoc()
		{
			throw new UnsupportedOperationException();
		}

		private CachedScorer()
		{
			super(null);
		}

	}

	private static class SegStart
	{

		public final AtomicReaderContext readerContext;
		public final int end;

		public SegStart(AtomicReaderContext readerContext, int end)
		{
			this.readerContext = readerContext;
			this.end = end;
		}
	}


	private static final int MAX_ARRAY_SIZE = 0x80000;
	private static final int INITIAL_ARRAY_SIZE = 128;
	private static final int EMPTY_INT_ARRAY[] = new int[0];
	protected final Collector other;
	protected final int maxDocsToCache;
	protected final List cachedSegs;
	protected final List cachedDocs;
	private AtomicReaderContext lastReaderContext;
	protected int curDocs[];
	protected int upto;
	protected int base;
	protected int lastDocBase;

	public static CachingCollector create(boolean acceptDocsOutOfOrder, boolean cacheScores, double maxRAMMB)
	{
		Collector other = new Collector(acceptDocsOutOfOrder) {

			final boolean val$acceptDocsOutOfOrder;

			public boolean acceptsDocsOutOfOrder()
			{
				return acceptDocsOutOfOrder;
			}

			public void setScorer(Scorer scorer1)
			{
			}

			public void collect(int i)
			{
			}

			public void setNextReader(AtomicReaderContext atomicreadercontext)
			{
			}

			
			{
				acceptDocsOutOfOrder = flag;
				super();
			}
		};
		return create(other, cacheScores, maxRAMMB);
	}

	public static CachingCollector create(Collector other, boolean cacheScores, double maxRAMMB)
	{
		return ((CachingCollector) (cacheScores ? new ScoreCachingCollector(other, maxRAMMB) : new NoScoreCachingCollector(other, maxRAMMB)));
	}

	public static CachingCollector create(Collector other, boolean cacheScores, int maxDocsToCache)
	{
		return ((CachingCollector) (cacheScores ? new ScoreCachingCollector(other, maxDocsToCache) : new NoScoreCachingCollector(other, maxDocsToCache)));
	}

	private CachingCollector(Collector other, double maxRAMMB, boolean cacheScores)
	{
		cachedSegs = new ArrayList();
		this.other = other;
		cachedDocs = new ArrayList();
		curDocs = new int[128];
		cachedDocs.add(curDocs);
		int bytesPerDoc = 4;
		if (cacheScores)
			bytesPerDoc += 4;
		maxDocsToCache = (int)((maxRAMMB * 1024D * 1024D) / (double)bytesPerDoc);
	}

	private CachingCollector(Collector other, int maxDocsToCache)
	{
		cachedSegs = new ArrayList();
		this.other = other;
		cachedDocs = new ArrayList();
		curDocs = new int[128];
		cachedDocs.add(curDocs);
		this.maxDocsToCache = maxDocsToCache;
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return other.acceptsDocsOutOfOrder();
	}

	public boolean isCached()
	{
		return curDocs != null;
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		other.setNextReader(context);
		if (lastReaderContext != null)
			cachedSegs.add(new SegStart(lastReaderContext, base + upto));
		lastReaderContext = context;
	}

	void replayInit(Collector other)
	{
		if (!isCached())
			throw new IllegalStateException("cannot replay: cache was cleared because too much RAM was required");
		if (!other.acceptsDocsOutOfOrder() && this.other.acceptsDocsOutOfOrder())
			throw new IllegalArgumentException("cannot replay: given collector does not support out-of-order collection, while the wrapped collector does. Therefore cached documents may be out-of-order.");
		if (lastReaderContext != null)
		{
			cachedSegs.add(new SegStart(lastReaderContext, base + upto));
			lastReaderContext = null;
		}
	}

	public abstract void replay(Collector collector)
		throws IOException;

	CachingCollector(Collector x0, double x1, boolean x2, 1 x3)
	{
		this(x0, x1, x2);
	}



}
