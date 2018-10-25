// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReadersAndLiveDocs.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.LiveDocsFormat;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.MutableBits;

// Referenced classes of package org.apache.lucene.index:
//			SegmentReader, SegmentInfoPerCommit, SegmentInfo, IndexWriter, 
//			LiveIndexWriterConfig

class ReadersAndLiveDocs
{

	public final SegmentInfoPerCommit info;
	private final AtomicInteger refCount = new AtomicInteger(1);
	private final IndexWriter writer;
	private SegmentReader reader;
	private SegmentReader mergeReader;
	private Bits liveDocs;
	private int pendingDeleteCount;
	private boolean shared;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ReadersAndLiveDocs.desiredAssertionStatus();

	public ReadersAndLiveDocs(IndexWriter writer, SegmentInfoPerCommit info)
	{
		this.info = info;
		this.writer = writer;
		shared = true;
	}

	public void incRef()
	{
		int rc = refCount.incrementAndGet();
		if (!$assertionsDisabled && rc <= 1)
			throw new AssertionError();
		else
			return;
	}

	public void decRef()
	{
		int rc = refCount.decrementAndGet();
		if (!$assertionsDisabled && rc < 0)
			throw new AssertionError();
		else
			return;
	}

	public int refCount()
	{
		int rc = refCount.get();
		if (!$assertionsDisabled && rc < 0)
			throw new AssertionError();
		else
			return rc;
	}

	public synchronized int getPendingDeleteCount()
	{
		return pendingDeleteCount;
	}

	public synchronized boolean verifyDocCounts()
	{
		int count;
		if (liveDocs != null)
		{
			count = 0;
			for (int docID = 0; docID < info.info.getDocCount(); docID++)
				if (liveDocs.get(docID))
					count++;

		} else
		{
			count = info.info.getDocCount();
		}
		if (!$assertionsDisabled && info.info.getDocCount() - info.getDelCount() - pendingDeleteCount != count)
			throw new AssertionError((new StringBuilder()).append("info.docCount=").append(info.info.getDocCount()).append(" info.getDelCount()=").append(info.getDelCount()).append(" pendingDeleteCount=").append(pendingDeleteCount).append(" count=").append(count).toString());
		else
			return true;
	}

	public synchronized SegmentReader getReader(IOContext context)
		throws IOException
	{
		if (reader == null)
		{
			reader = new SegmentReader(info, writer.getConfig().getReaderTermsIndexDivisor(), context);
			if (liveDocs == null)
				liveDocs = reader.getLiveDocs();
		}
		reader.incRef();
		return reader;
	}

	public synchronized SegmentReader getMergeReader(IOContext context)
		throws IOException
	{
		if (mergeReader == null)
			if (reader != null)
			{
				reader.incRef();
				mergeReader = reader;
			} else
			{
				mergeReader = new SegmentReader(info, -1, context);
				if (liveDocs == null)
					liveDocs = mergeReader.getLiveDocs();
			}
		mergeReader.incRef();
		return mergeReader;
	}

	public synchronized void release(SegmentReader sr)
		throws IOException
	{
		if (!$assertionsDisabled && info != sr.getSegmentInfo())
		{
			throw new AssertionError();
		} else
		{
			sr.decRef();
			return;
		}
	}

	public synchronized boolean delete(int docID)
	{
		if (!$assertionsDisabled && liveDocs == null)
			throw new AssertionError();
		if (!$assertionsDisabled && !Thread.holdsLock(writer))
			throw new AssertionError();
		if (!$assertionsDisabled && (docID < 0 || docID >= liveDocs.length()))
			throw new AssertionError((new StringBuilder()).append("out of bounds: docid=").append(docID).append(" liveDocsLength=").append(liveDocs.length()).append(" seg=").append(info.info.name).append(" docCount=").append(info.info.getDocCount()).toString());
		if (!$assertionsDisabled && shared)
			throw new AssertionError();
		boolean didDelete = liveDocs.get(docID);
		if (didDelete)
		{
			((MutableBits)liveDocs).clear(docID);
			pendingDeleteCount++;
		}
		return didDelete;
	}

	public synchronized void dropReaders()
		throws IOException
	{
		if (reader != null)
		{
			reader.decRef();
			reader = null;
		}
		if (mergeReader != null)
		{
			mergeReader.decRef();
			mergeReader = null;
		}
		decRef();
	}

	public synchronized SegmentReader getReadOnlyClone(IOContext context)
		throws IOException
	{
		if (reader == null)
		{
			getReader(context).decRef();
			if (!$assertionsDisabled && reader == null)
				throw new AssertionError();
		}
		shared = true;
		if (liveDocs != null)
			return new SegmentReader(reader.getSegmentInfo(), reader.core, liveDocs, info.info.getDocCount() - info.getDelCount() - pendingDeleteCount);
		if (!$assertionsDisabled && reader.getLiveDocs() != liveDocs)
		{
			throw new AssertionError();
		} else
		{
			reader.incRef();
			return reader;
		}
	}

	public synchronized void initWritableLiveDocs()
		throws IOException
	{
		if (!$assertionsDisabled && !Thread.holdsLock(writer))
			throw new AssertionError();
		if (!$assertionsDisabled && info.info.getDocCount() <= 0)
			throw new AssertionError();
		if (shared)
		{
			LiveDocsFormat liveDocsFormat = info.info.getCodec().liveDocsFormat();
			if (liveDocs == null)
				liveDocs = liveDocsFormat.newLiveDocs(info.info.getDocCount());
			else
				liveDocs = liveDocsFormat.newLiveDocs(liveDocs);
			shared = false;
		} else
		if (!$assertionsDisabled && liveDocs == null)
			throw new AssertionError();
	}

	public synchronized Bits getLiveDocs()
	{
		if (!$assertionsDisabled && !Thread.holdsLock(writer))
			throw new AssertionError();
		else
			return liveDocs;
	}

	public synchronized Bits getReadOnlyLiveDocs()
	{
		if (!$assertionsDisabled && !Thread.holdsLock(writer))
		{
			throw new AssertionError();
		} else
		{
			shared = true;
			return liveDocs;
		}
	}

	public synchronized void dropChanges()
	{
		pendingDeleteCount = 0;
	}

	public synchronized boolean writeLiveDocs(Directory dir)
		throws IOException
	{
		if (pendingDeleteCount != 0)
		{
			if (!$assertionsDisabled && liveDocs.length() != info.info.getDocCount())
			{
				throw new AssertionError();
			} else
			{
				info.info.getCodec().liveDocsFormat().writeLiveDocs((MutableBits)liveDocs, dir, info, pendingDeleteCount, IOContext.DEFAULT);
				info.advanceDelGen();
				info.setDelCount(info.getDelCount() + pendingDeleteCount);
				pendingDeleteCount = 0;
				return true;
			}
		} else
		{
			return false;
		}
	}

	public String toString()
	{
		return (new StringBuilder()).append("ReadersAndLiveDocs(seg=").append(info).append(" pendingDeleteCount=").append(pendingDeleteCount).append(" shared=").append(shared).append(")").toString();
	}

}
