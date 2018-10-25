// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearcherManager.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.search:
//			ReferenceManager, SearcherFactory, IndexSearcher

public final class SearcherManager extends ReferenceManager
{

	private final SearcherFactory searcherFactory;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/SearcherManager.desiredAssertionStatus();

	public SearcherManager(IndexWriter writer, boolean applyAllDeletes, SearcherFactory searcherFactory)
		throws IOException
	{
		if (searcherFactory == null)
			searcherFactory = new SearcherFactory();
		this.searcherFactory = searcherFactory;
		current = getSearcher(searcherFactory, DirectoryReader.open(writer, applyAllDeletes));
	}

	public SearcherManager(Directory dir, SearcherFactory searcherFactory)
		throws IOException
	{
		if (searcherFactory == null)
			searcherFactory = new SearcherFactory();
		this.searcherFactory = searcherFactory;
		current = getSearcher(searcherFactory, DirectoryReader.open(dir));
	}

	protected void decRef(IndexSearcher reference)
		throws IOException
	{
		reference.getIndexReader().decRef();
	}

	protected IndexSearcher refreshIfNeeded(IndexSearcher referenceToRefresh)
		throws IOException
	{
		IndexReader r = referenceToRefresh.getIndexReader();
		if (!$assertionsDisabled && !(r instanceof DirectoryReader))
			throw new AssertionError((new StringBuilder()).append("searcher's IndexReader should be a DirectoryReader, but got ").append(r).toString());
		IndexReader newReader = DirectoryReader.openIfChanged((DirectoryReader)r);
		if (newReader == null)
			return null;
		else
			return getSearcher(searcherFactory, newReader);
	}

	protected boolean tryIncRef(IndexSearcher reference)
	{
		return reference.getIndexReader().tryIncRef();
	}

	public boolean isSearcherCurrent()
		throws IOException
	{
		IndexSearcher searcher = (IndexSearcher)acquire();
		boolean flag;
		IndexReader r = searcher.getIndexReader();
		if (!$assertionsDisabled && !(r instanceof DirectoryReader))
			throw new AssertionError((new StringBuilder()).append("searcher's IndexReader should be a DirectoryReader, but got ").append(r).toString());
		flag = ((DirectoryReader)r).isCurrent();
		release(searcher);
		return flag;
		Exception exception;
		exception;
		release(searcher);
		throw exception;
	}

	static IndexSearcher getSearcher(SearcherFactory searcherFactory, IndexReader reader)
		throws IOException
	{
		boolean success = false;
		IndexSearcher searcher;
		searcher = searcherFactory.newSearcher(reader);
		if (searcher.getIndexReader() != reader)
			throw new IllegalStateException((new StringBuilder()).append("SearcherFactory must wrap exactly the provided reader (got ").append(searcher.getIndexReader()).append(" but expected ").append(reader).append(")").toString());
		success = true;
		if (!success)
			reader.decRef();
		break MISSING_BLOCK_LABEL_86;
		Exception exception;
		exception;
		if (!success)
			reader.decRef();
		throw exception;
		return searcher;
	}

	protected volatile boolean tryIncRef(Object x0)
	{
		return tryIncRef((IndexSearcher)x0);
	}

	protected volatile Object refreshIfNeeded(Object x0)
		throws IOException
	{
		return refreshIfNeeded((IndexSearcher)x0);
	}

	protected volatile void decRef(Object x0)
		throws IOException
	{
		decRef((IndexSearcher)x0);
	}

}
