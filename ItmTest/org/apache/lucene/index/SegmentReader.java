// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentReader.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.codecs.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.CloseableThreadLocal;

// Referenced classes of package org.apache.lucene.index:
//			AtomicReader, SegmentCoreReaders, SegmentInfoPerCommit, SegmentInfo, 
//			FieldInfos, StoredFieldVisitor, Fields, DocValues

public final class SegmentReader extends AtomicReader
{
	public static interface CoreClosedListener
	{

		public abstract void onClose(SegmentReader segmentreader);
	}


	private final SegmentInfoPerCommit si;
	private final Bits liveDocs;
	private final int numDocs;
	final SegmentCoreReaders core;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentReader.desiredAssertionStatus();

	public SegmentReader(SegmentInfoPerCommit si, int termInfosIndexDivisor, IOContext context)
		throws IOException
	{
		boolean success;
		this.si = si;
		core = new SegmentCoreReaders(this, si.info.dir, si, context, termInfosIndexDivisor);
		success = false;
		if (si.hasDeletions())
		{
			liveDocs = si.info.getCodec().liveDocsFormat().readLiveDocs(directory(), si, new IOContext(IOContext.READ, true));
		} else
		{
			if (!$assertionsDisabled && si.getDelCount() != 0)
				throw new AssertionError();
			liveDocs = null;
		}
		numDocs = si.info.getDocCount() - si.getDelCount();
		success = true;
		if (!success)
			core.decRef();
		break MISSING_BLOCK_LABEL_154;
		Exception exception;
		exception;
		if (!success)
			core.decRef();
		throw exception;
	}

	SegmentReader(SegmentInfoPerCommit si, SegmentCoreReaders core, IOContext context)
		throws IOException
	{
		this(si, core, si.info.getCodec().liveDocsFormat().readLiveDocs(si.info.dir, si, context), si.info.getDocCount() - si.getDelCount());
	}

	SegmentReader(SegmentInfoPerCommit si, SegmentCoreReaders core, Bits liveDocs, int numDocs)
	{
		this.si = si;
		this.core = core;
		core.incRef();
		if (!$assertionsDisabled && liveDocs == null)
		{
			throw new AssertionError();
		} else
		{
			this.liveDocs = liveDocs;
			this.numDocs = numDocs;
			return;
		}
	}

	public Bits getLiveDocs()
	{
		ensureOpen();
		return liveDocs;
	}

	protected void doClose()
		throws IOException
	{
		core.decRef();
	}

	public boolean hasDeletions()
	{
		return liveDocs != null;
	}

	public FieldInfos getFieldInfos()
	{
		ensureOpen();
		return core.fieldInfos;
	}

	public StoredFieldsReader getFieldsReader()
	{
		ensureOpen();
		return (StoredFieldsReader)core.fieldsReaderLocal.get();
	}

	public void document(int docID, StoredFieldVisitor visitor)
		throws IOException
	{
		if (docID < 0 || docID >= maxDoc())
		{
			throw new IllegalArgumentException((new StringBuilder()).append("docID must be >= 0 and < maxDoc=").append(maxDoc()).append(" (got docID=").append(docID).append(")").toString());
		} else
		{
			getFieldsReader().visitDocument(docID, visitor);
			return;
		}
	}

	public Fields fields()
	{
		ensureOpen();
		return core.fields;
	}

	public int numDocs()
	{
		return numDocs;
	}

	public int maxDoc()
	{
		return si.info.getDocCount();
	}

	public TermVectorsReader getTermVectorsReader()
	{
		ensureOpen();
		return (TermVectorsReader)core.termVectorsLocal.get();
	}

	public Fields getTermVectors(int docID)
		throws IOException
	{
		TermVectorsReader termVectorsReader = getTermVectorsReader();
		if (termVectorsReader == null)
			return null;
		else
			return termVectorsReader.get(docID);
	}

	public String toString()
	{
		return si.toString(si.info.dir, si.info.getDocCount() - numDocs - si.getDelCount());
	}

	public String getSegmentName()
	{
		return si.info.name;
	}

	SegmentInfoPerCommit getSegmentInfo()
	{
		return si;
	}

	public Directory directory()
	{
		return si.info.dir;
	}

	public Object getCoreCacheKey()
	{
		return core;
	}

	public Object getCombinedCoreAndDeletesKey()
	{
		return this;
	}

	public int getTermInfosIndexDivisor()
	{
		return core.termsIndexDivisor;
	}

	public DocValues docValues(String field)
		throws IOException
	{
		ensureOpen();
		PerDocProducer perDoc = core.perDocProducer;
		if (perDoc == null)
			return null;
		else
			return perDoc.docValues(field);
	}

	public DocValues normValues(String field)
		throws IOException
	{
		ensureOpen();
		PerDocProducer perDoc = core.norms;
		if (perDoc == null)
			return null;
		else
			return perDoc.docValues(field);
	}

	public void addCoreClosedListener(CoreClosedListener listener)
	{
		ensureOpen();
		core.addCoreClosedListener(listener);
	}

	public void removeCoreClosedListener(CoreClosedListener listener)
	{
		ensureOpen();
		core.removeCoreClosedListener(listener);
	}

}
