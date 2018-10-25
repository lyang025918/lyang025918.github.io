// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CachingWrapperFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.FixedBitSet;

// Referenced classes of package org.apache.lucene.search:
//			Filter, DocIdSet, DocIdSetIterator, BitsFilteredDocIdSet

public class CachingWrapperFilter extends Filter
{

	private final Filter filter;
	private final Map cache;
	private final boolean recacheDeletes;
	int hitCount;
	int missCount;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/CachingWrapperFilter.desiredAssertionStatus();

	public CachingWrapperFilter(Filter filter)
	{
		this(filter, false);
	}

	public CachingWrapperFilter(Filter filter, boolean recacheDeletes)
	{
		cache = Collections.synchronizedMap(new WeakHashMap());
		this.filter = filter;
		this.recacheDeletes = recacheDeletes;
	}

	protected DocIdSet docIdSetToCache(DocIdSet docIdSet, AtomicReader reader)
		throws IOException
	{
		if (docIdSet == null)
			return DocIdSet.EMPTY_DOCIDSET;
		if (docIdSet.isCacheable())
			return docIdSet;
		DocIdSetIterator it = docIdSet.iterator();
		if (it == null)
		{
			return DocIdSet.EMPTY_DOCIDSET;
		} else
		{
			FixedBitSet bits = new FixedBitSet(reader.maxDoc());
			bits.or(it);
			return bits;
		}
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		AtomicReader reader = context.reader();
		Bits liveDocs = reader.getLiveDocs();
		boolean doCacheAcceptDocs = recacheDeletes && acceptDocs == liveDocs;
		Object key;
		Bits cacheAcceptDocs;
		if (doCacheAcceptDocs)
		{
			if (!$assertionsDisabled && acceptDocs != liveDocs)
				throw new AssertionError();
			key = reader.getCombinedCoreAndDeletesKey();
			cacheAcceptDocs = acceptDocs;
		} else
		{
			key = reader.getCoreCacheKey();
			cacheAcceptDocs = null;
		}
		DocIdSet docIdSet = (DocIdSet)cache.get(key);
		if (docIdSet != null)
		{
			hitCount++;
		} else
		{
			missCount++;
			docIdSet = docIdSetToCache(filter.getDocIdSet(context, cacheAcceptDocs), reader);
			cache.put(key, docIdSet);
		}
		if (doCacheAcceptDocs)
			return docIdSet;
		else
			return BitsFilteredDocIdSet.wrap(docIdSet, acceptDocs);
	}

	public String toString()
	{
		return (new StringBuilder()).append("CachingWrapperFilter(").append(filter).append(",recacheDeletes=").append(recacheDeletes).append(")").toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof CachingWrapperFilter))
		{
			return false;
		} else
		{
			CachingWrapperFilter other = (CachingWrapperFilter)o;
			return filter.equals(other.filter) && recacheDeletes == other.recacheDeletes;
		}
	}

	public int hashCode()
	{
		return (filter.hashCode() ^ 0x1117bf25) + (recacheDeletes ? 0 : 1);
	}

}
