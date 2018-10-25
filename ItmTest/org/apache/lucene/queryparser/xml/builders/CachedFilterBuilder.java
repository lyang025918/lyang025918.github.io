// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CachedFilterBuilder.java

package org.apache.lucene.queryparser.xml.builders;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.lucene.queryparser.xml.*;
import org.apache.lucene.search.*;
import org.w3c.dom.Element;

public class CachedFilterBuilder
	implements FilterBuilder
{
	static class LRUCache extends LinkedHashMap
	{

		protected int maxsize;

		protected boolean removeEldestEntry(java.util.Map.Entry eldest)
		{
			return size() > maxsize;
		}

		public LRUCache(int maxsize)
		{
			super((maxsize * 4) / 3 + 1, 0.75F, true);
			this.maxsize = maxsize;
		}
	}


	private final QueryBuilderFactory queryFactory;
	private final FilterBuilderFactory filterFactory;
	private LRUCache filterCache;
	private final int cacheSize;

	public CachedFilterBuilder(QueryBuilderFactory queryFactory, FilterBuilderFactory filterFactory, int cacheSize)
	{
		this.queryFactory = queryFactory;
		this.filterFactory = filterFactory;
		this.cacheSize = cacheSize;
	}

	public synchronized Filter getFilter(Element e)
		throws ParserException
	{
		Element childElement = DOMUtils.getFirstChildOrFail(e);
		if (filterCache == null)
			filterCache = new LRUCache(cacheSize);
		QueryBuilder qb = queryFactory.getQueryBuilder(childElement.getNodeName());
		Object cacheKey = null;
		Query q = null;
		Filter f = null;
		if (qb != null)
		{
			q = qb.getQuery(childElement);
			cacheKey = q;
		} else
		{
			f = filterFactory.getFilter(childElement);
			cacheKey = f;
		}
		Filter cachedFilter = (Filter)filterCache.get(cacheKey);
		if (cachedFilter != null)
			return cachedFilter;
		if (qb != null)
			cachedFilter = new QueryWrapperFilter(q);
		else
			cachedFilter = new CachingWrapperFilter(f);
		filterCache.put(cacheKey, cachedFilter);
		return cachedFilter;
	}
}
