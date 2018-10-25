// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseCompositeReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.index:
//			CompositeReader, IndexReader, ReaderUtil, Fields, 
//			StoredFieldVisitor, Term

public abstract class BaseCompositeReader extends CompositeReader
{

	private final IndexReader subReaders[];
	private final int starts[];
	private final int maxDoc;
	private final int numDocs;
	private final boolean hasDeletions;
	private final List subReadersList;

	protected BaseCompositeReader(IndexReader subReaders[])
	{
		this.subReaders = subReaders;
		subReadersList = Collections.unmodifiableList(Arrays.asList(subReaders));
		starts = new int[subReaders.length + 1];
		int maxDoc = 0;
		int numDocs = 0;
		boolean hasDeletions = false;
		for (int i = 0; i < subReaders.length; i++)
		{
			starts[i] = maxDoc;
			IndexReader r = subReaders[i];
			maxDoc += r.maxDoc();
			if (maxDoc < 0)
				throw new IllegalArgumentException("Too many documents, composite IndexReaders cannot exceed 2147483647");
			numDocs += r.numDocs();
			if (r.hasDeletions())
				hasDeletions = true;
			r.registerParentReader(this);
		}

		starts[subReaders.length] = maxDoc;
		this.maxDoc = maxDoc;
		this.numDocs = numDocs;
		this.hasDeletions = hasDeletions;
	}

	public final Fields getTermVectors(int docID)
		throws IOException
	{
		ensureOpen();
		int i = readerIndex(docID);
		return subReaders[i].getTermVectors(docID - starts[i]);
	}

	public final int numDocs()
	{
		return numDocs;
	}

	public final int maxDoc()
	{
		return maxDoc;
	}

	public final void document(int docID, StoredFieldVisitor visitor)
		throws IOException
	{
		ensureOpen();
		int i = readerIndex(docID);
		subReaders[i].document(docID - starts[i], visitor);
	}

	public final boolean hasDeletions()
	{
		return hasDeletions;
	}

	public final int docFreq(Term term)
		throws IOException
	{
		ensureOpen();
		int total = 0;
		for (int i = 0; i < subReaders.length; i++)
			total += subReaders[i].docFreq(term);

		return total;
	}

	public final long totalTermFreq(Term term)
		throws IOException
	{
		ensureOpen();
		long total = 0L;
		for (int i = 0; i < subReaders.length; i++)
		{
			long sub = subReaders[i].totalTermFreq(term);
			if (sub == -1L)
				return -1L;
			total += sub;
		}

		return total;
	}

	protected final int readerIndex(int docID)
	{
		if (docID < 0 || docID >= maxDoc)
			throw new IllegalArgumentException((new StringBuilder()).append("docID must be >= 0 and < maxDoc=").append(maxDoc).append(" (got docID=").append(docID).append(")").toString());
		else
			return ReaderUtil.subIndex(docID, starts);
	}

	protected final int readerBase(int readerIndex)
	{
		if (readerIndex < 0 || readerIndex >= subReaders.length)
			throw new IllegalArgumentException("readerIndex must be >= 0 and < getSequentialSubReaders().size()");
		else
			return starts[readerIndex];
	}

	protected final List getSequentialSubReaders()
	{
		return subReadersList;
	}
}
