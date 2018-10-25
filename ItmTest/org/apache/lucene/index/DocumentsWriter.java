// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriter.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.InfoStream;

// Referenced classes of package org.apache.lucene.index:
//			DocumentsWriterDeleteQueue, DocumentsWriterFlushQueue, DocumentsWriterFlushControl, FrozenBufferedDeletes, 
//			LiveIndexWriterConfig, IndexWriter, BufferedDeletesStream, DocumentsWriterPerThread, 
//			Term, SegmentInfoPerCommit, BufferedDeletes, DocumentsWriterPerThreadPool, 
//			FlushPolicy, FieldInfos

final class DocumentsWriter
{

	Directory directory;
	private volatile boolean closed;
	final InfoStream infoStream;
	Similarity similarity;
	List newFiles;
	final IndexWriter indexWriter;
	private AtomicInteger numDocsInRAM;
	volatile DocumentsWriterDeleteQueue deleteQueue;
	private final DocumentsWriterFlushQueue ticketQueue = new DocumentsWriterFlushQueue();
	private volatile boolean pendingChangesInCurrentFullFlush;
	private Collection abortedFiles;
	final DocumentsWriterPerThread.IndexingChain chain;
	final DocumentsWriterPerThreadPool perThreadPool;
	final FlushPolicy flushPolicy;
	final DocumentsWriterFlushControl flushControl;
	final Codec codec;
	private volatile DocumentsWriterDeleteQueue currentFullFlushDelQueue;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriter.desiredAssertionStatus();

	DocumentsWriter(Codec codec, LiveIndexWriterConfig config, Directory directory, IndexWriter writer, FieldInfos.FieldNumbers globalFieldNumbers, BufferedDeletesStream bufferedDeletesStream)
	{
		numDocsInRAM = new AtomicInteger(0);
		deleteQueue = new DocumentsWriterDeleteQueue();
		currentFullFlushDelQueue = null;
		this.codec = codec;
		this.directory = directory;
		indexWriter = writer;
		infoStream = config.getInfoStream();
		similarity = config.getSimilarity();
		perThreadPool = config.getIndexerThreadPool();
		chain = config.getIndexingChain();
		perThreadPool.initialize(this, globalFieldNumbers, config);
		flushPolicy = config.getFlushPolicy();
		if (!$assertionsDisabled && flushPolicy == null)
		{
			throw new AssertionError();
		} else
		{
			flushPolicy.init(this);
			flushControl = new DocumentsWriterFlushControl(this, config);
			return;
		}
	}

	synchronized transient void deleteQueries(Query queries[])
		throws IOException
	{
		deleteQueue.addDelete(queries);
		flushControl.doOnDelete();
		if (flushControl.doApplyAllDeletes())
			applyAllDeletes(deleteQueue);
	}

	synchronized transient void deleteTerms(Term terms[])
		throws IOException
	{
		DocumentsWriterDeleteQueue deleteQueue = this.deleteQueue;
		deleteQueue.addDelete(terms);
		flushControl.doOnDelete();
		if (flushControl.doApplyAllDeletes())
			applyAllDeletes(deleteQueue);
	}

	DocumentsWriterDeleteQueue currentDeleteSession()
	{
		return deleteQueue;
	}

	private void applyAllDeletes(DocumentsWriterDeleteQueue deleteQueue)
		throws IOException
	{
		if (deleteQueue != null && !flushControl.isFullFlush())
			ticketQueue.addDeletesAndPurge(this, deleteQueue);
		indexWriter.applyAllDeletes();
		indexWriter.flushCount.incrementAndGet();
	}

	int getNumDocs()
	{
		return numDocsInRAM.get();
	}

	Collection abortedFiles()
	{
		return abortedFiles;
	}

	private void ensureOpen()
		throws AlreadyClosedException
	{
		if (closed)
			throw new AlreadyClosedException("this IndexWriter is closed");
		else
			return;
	}

	synchronized void abort()
	{
		boolean success = false;
		int limit;
		int i;
		deleteQueue.clear();
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", "abort");
		limit = perThreadPool.getActiveThreadState();
		i = 0;
_L2:
		DocumentsWriterPerThreadPool.ThreadState perThread;
		if (i >= limit)
			break; /* Loop/switch isn't completed */
		perThread = perThreadPool.getThreadState(i);
		perThread.lock();
		if (!perThread.isActive())
			break MISSING_BLOCK_LABEL_125;
		perThread.dwpt.abort();
		perThread.dwpt.checkAndResetHasAborted();
		flushControl.doOnAbort(perThread);
		break MISSING_BLOCK_LABEL_146;
		Exception exception;
		exception;
		perThread.dwpt.checkAndResetHasAborted();
		flushControl.doOnAbort(perThread);
		throw exception;
		if (!$assertionsDisabled && !closed)
			throw new AssertionError();
		Exception exception1;
		perThread.unlock();
		i++;
		continue; /* Loop/switch isn't completed */
		exception1;
		perThread.unlock();
		throw exception1;
		if (true) goto _L2; else goto _L1
_L1:
		flushControl.abortPendingFlushes();
		flushControl.waitForFlush();
		success = true;
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append("done abort; abortedFiles=").append(abortedFiles).append(" success=").append(success).toString());
		break MISSING_BLOCK_LABEL_298;
		Exception exception2;
		exception2;
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append("done abort; abortedFiles=").append(abortedFiles).append(" success=").append(success).toString());
		throw exception2;
	}

	boolean anyChanges()
	{
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append("anyChanges? numDocsInRam=").append(numDocsInRAM.get()).append(" deletes=").append(anyDeletions()).append(" hasTickets:").append(ticketQueue.hasTickets()).append(" pendingChangesInFullFlush: ").append(pendingChangesInCurrentFullFlush).toString());
		return numDocsInRAM.get() != 0 || anyDeletions() || ticketQueue.hasTickets() || pendingChangesInCurrentFullFlush;
	}

	public int getBufferedDeleteTermsSize()
	{
		return deleteQueue.getBufferedDeleteTermsSize();
	}

	public int getNumBufferedDeleteTerms()
	{
		return deleteQueue.numGlobalTermDeletes();
	}

	public boolean anyDeletions()
	{
		return deleteQueue.anyChanges();
	}

	void close()
	{
		closed = true;
		flushControl.setClosed();
	}

	private boolean preUpdate()
		throws IOException
	{
		ensureOpen();
		boolean maybeMerge = false;
		if (flushControl.anyStalledThreads() || flushControl.numQueuedFlushes() > 0)
		{
			if (infoStream.isEnabled("DW"))
				infoStream.message("DW", "DocumentsWriter has queued dwpt; will hijack this thread to flush pending segment(s)");
			do
			{
				DocumentsWriterPerThread flushingDWPT;
				while ((flushingDWPT = flushControl.nextPendingFlush()) != null) 
					maybeMerge |= doFlush(flushingDWPT);
				if (infoStream.isEnabled("DW") && flushControl.anyStalledThreads())
					infoStream.message("DW", "WARNING DocumentsWriter has stalled threads; waiting");
				flushControl.waitIfStalled();
			} while (flushControl.numQueuedFlushes() != 0);
			if (infoStream.isEnabled("DW"))
				infoStream.message("DW", "continue indexing after helping out flushing DocumentsWriter is healthy");
		}
		return maybeMerge;
	}

	private boolean postUpdate(DocumentsWriterPerThread flushingDWPT, boolean maybeMerge)
		throws IOException
	{
		if (flushControl.doApplyAllDeletes())
			applyAllDeletes(deleteQueue);
		if (flushingDWPT != null)
		{
			maybeMerge |= doFlush(flushingDWPT);
		} else
		{
			DocumentsWriterPerThread nextPendingFlush = flushControl.nextPendingFlush();
			if (nextPendingFlush != null)
				maybeMerge |= doFlush(nextPendingFlush);
		}
		return maybeMerge;
	}

	boolean updateDocuments(Iterable docs, Analyzer analyzer, Term delTerm)
		throws IOException
	{
		boolean maybeMerge;
		DocumentsWriterPerThreadPool.ThreadState perThread;
		maybeMerge = preUpdate();
		perThread = flushControl.obtainAndLock();
		DocumentsWriterPerThread dwpt;
		if (!perThread.isActive())
		{
			ensureOpen();
			if (!$assertionsDisabled)
				throw new AssertionError("perThread is not active but we are still open");
		}
		dwpt = perThread.dwpt;
		int docCount = dwpt.updateDocuments(docs, analyzer, delTerm);
		numDocsInRAM.addAndGet(docCount);
		if (dwpt.checkAndResetHasAborted())
			flushControl.doOnAbort(perThread);
		break MISSING_BLOCK_LABEL_112;
		Exception exception;
		exception;
		if (dwpt.checkAndResetHasAborted())
			flushControl.doOnAbort(perThread);
		throw exception;
		DocumentsWriterPerThread flushingDWPT;
		boolean isUpdate = delTerm != null;
		flushingDWPT = flushControl.doAfterDocument(perThread, isUpdate);
		perThread.unlock();
		break MISSING_BLOCK_LABEL_154;
		Exception exception1;
		exception1;
		perThread.unlock();
		throw exception1;
		return postUpdate(flushingDWPT, maybeMerge);
	}

	boolean updateDocument(Iterable doc, Analyzer analyzer, Term delTerm)
		throws IOException
	{
		boolean maybeMerge;
		DocumentsWriterPerThreadPool.ThreadState perThread;
		maybeMerge = preUpdate();
		perThread = flushControl.obtainAndLock();
		DocumentsWriterPerThread dwpt;
		if (!perThread.isActive())
		{
			ensureOpen();
			throw new IllegalStateException("perThread is not active but we are still open");
		}
		dwpt = perThread.dwpt;
		dwpt.updateDocument(doc, analyzer, delTerm);
		numDocsInRAM.incrementAndGet();
		if (dwpt.checkAndResetHasAborted())
			flushControl.doOnAbort(perThread);
		break MISSING_BLOCK_LABEL_102;
		Exception exception;
		exception;
		if (dwpt.checkAndResetHasAborted())
			flushControl.doOnAbort(perThread);
		throw exception;
		DocumentsWriterPerThread flushingDWPT;
		boolean isUpdate = delTerm != null;
		flushingDWPT = flushControl.doAfterDocument(perThread, isUpdate);
		perThread.unlock();
		break MISSING_BLOCK_LABEL_144;
		Exception exception1;
		exception1;
		perThread.unlock();
		throw exception1;
		return postUpdate(flushingDWPT, maybeMerge);
	}

	private boolean doFlush(DocumentsWriterPerThread flushingDWPT)
		throws IOException
	{
		boolean maybeMerge = false;
_L2:
		boolean success;
		DocumentsWriterFlushQueue.SegmentFlushTicket ticket;
		if (flushingDWPT == null)
			break; /* Loop/switch isn't completed */
		maybeMerge = true;
		success = false;
		ticket = null;
		if (!$assertionsDisabled && currentFullFlushDelQueue != null && flushingDWPT.deleteQueue != currentFullFlushDelQueue)
			throw new AssertionError((new StringBuilder()).append("expected: ").append(currentFullFlushDelQueue).append("but was: ").append(flushingDWPT.deleteQueue).append(" ").append(flushControl.isFullFlush()).toString());
		ticket = ticketQueue.addFlushTicket(flushingDWPT);
		DocumentsWriterPerThread.FlushedSegment newSegment = flushingDWPT.flush();
		ticketQueue.addSegment(ticket, newSegment);
		success = true;
		if (!success && ticket != null)
			ticketQueue.markTicketFailed(ticket);
		break MISSING_BLOCK_LABEL_167;
		Exception exception;
		exception;
		if (!success && ticket != null)
			ticketQueue.markTicketFailed(ticket);
		throw exception;
		if (ticketQueue.getTicketCount() >= perThreadPool.getActiveThreadState())
			ticketQueue.forcePurge(this);
		else
			ticketQueue.tryPurge(this);
		flushControl.doAfterFlush(flushingDWPT);
		flushingDWPT.checkAndResetHasAborted();
		indexWriter.flushCount.incrementAndGet();
		indexWriter.doAfterFlush();
		break MISSING_BLOCK_LABEL_273;
		Exception exception1;
		exception1;
		flushControl.doAfterFlush(flushingDWPT);
		flushingDWPT.checkAndResetHasAborted();
		indexWriter.flushCount.incrementAndGet();
		indexWriter.doAfterFlush();
		throw exception1;
		flushingDWPT = flushControl.nextPendingFlush();
		if (true) goto _L2; else goto _L1
_L1:
		double ramBufferSizeMB = indexWriter.getConfig().getRAMBufferSizeMB();
		if (ramBufferSizeMB != -1D && (double)flushControl.getDeleteBytesUsed() > (1048576D * ramBufferSizeMB) / 2D)
		{
			if (infoStream.isEnabled("DW"))
				infoStream.message("DW", (new StringBuilder()).append("force apply deletes bytesUsed=").append(flushControl.getDeleteBytesUsed()).append(" vs ramBuffer=").append(1048576D * ramBufferSizeMB).toString());
			applyAllDeletes(deleteQueue);
		}
		return maybeMerge;
	}

	void finishFlush(DocumentsWriterPerThread.FlushedSegment newSegment, FrozenBufferedDeletes bufferedDeletes)
		throws IOException
	{
		if (newSegment == null)
		{
			if (!$assertionsDisabled && bufferedDeletes == null)
				throw new AssertionError();
			if (bufferedDeletes != null && bufferedDeletes.any())
			{
				indexWriter.publishFrozenDeletes(bufferedDeletes);
				if (infoStream.isEnabled("DW"))
					infoStream.message("DW", (new StringBuilder()).append("flush: push buffered deletes: ").append(bufferedDeletes).toString());
			}
		} else
		{
			publishFlushedSegment(newSegment, bufferedDeletes);
		}
	}

	final void subtractFlushedNumDocs(int numFlushed)
	{
		for (int oldValue = numDocsInRAM.get(); !numDocsInRAM.compareAndSet(oldValue, oldValue - numFlushed); oldValue = numDocsInRAM.get());
	}

	private void publishFlushedSegment(DocumentsWriterPerThread.FlushedSegment newSegment, FrozenBufferedDeletes globalPacket)
		throws IOException
	{
		if (!$assertionsDisabled && newSegment == null)
			throw new AssertionError();
		if (!$assertionsDisabled && newSegment.segmentInfo == null)
			throw new AssertionError();
		SegmentInfoPerCommit segInfo = indexWriter.prepareFlushedSegment(newSegment);
		BufferedDeletes deletes = newSegment.segmentDeletes;
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append("publishFlushedSegment seg-private deletes=").append(deletes).toString());
		FrozenBufferedDeletes packet = null;
		if (deletes != null && deletes.any())
		{
			packet = new FrozenBufferedDeletes(deletes, true);
			if (infoStream.isEnabled("DW"))
				infoStream.message("DW", (new StringBuilder()).append("flush: push buffered seg private deletes: ").append(packet).toString());
		}
		indexWriter.publishFlushedSegment(segInfo, packet, globalPacket);
	}

	private synchronized boolean setFlushingDeleteQueue(DocumentsWriterDeleteQueue session)
	{
		currentFullFlushDelQueue = session;
		return true;
	}

	final boolean flushAllThreads()
		throws IOException
	{
		DocumentsWriterDeleteQueue flushingDeleteQueue;
		boolean anythingFlushed;
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append(Thread.currentThread().getName()).append(" startFullFlush").toString());
		synchronized (this)
		{
			pendingChangesInCurrentFullFlush = anyChanges();
			flushingDeleteQueue = deleteQueue;
			flushControl.markForFullFlush();
			if (!$assertionsDisabled && !setFlushingDeleteQueue(flushingDeleteQueue))
				throw new AssertionError();
		}
		if (!$assertionsDisabled && currentFullFlushDelQueue == null)
			throw new AssertionError();
		if (!$assertionsDisabled && currentFullFlushDelQueue == deleteQueue)
			throw new AssertionError();
		anythingFlushed = false;
		DocumentsWriterPerThread flushingDWPT;
		while ((flushingDWPT = flushControl.nextPendingFlush()) != null) 
			anythingFlushed |= doFlush(flushingDWPT);
		flushControl.waitForFlush();
		if (!anythingFlushed && flushingDeleteQueue.anyChanges())
		{
			if (infoStream.isEnabled("DW"))
				infoStream.message("DW", (new StringBuilder()).append(Thread.currentThread().getName()).append(": flush naked frozen global deletes").toString());
			ticketQueue.addDeletesAndPurge(this, flushingDeleteQueue);
		} else
		{
			ticketQueue.forcePurge(this);
		}
		if (!$assertionsDisabled && (flushingDeleteQueue.anyChanges() || ticketQueue.hasTickets()))
			throw new AssertionError();
		Exception exception1;
		if (!$assertionsDisabled && flushingDeleteQueue != currentFullFlushDelQueue)
			throw new AssertionError();
		else
			return anythingFlushed;
		exception1;
		if (!$assertionsDisabled && flushingDeleteQueue != currentFullFlushDelQueue)
			throw new AssertionError();
		else
			throw exception1;
	}

	final void finishFullFlush(boolean success)
	{
		if (infoStream.isEnabled("DW"))
			infoStream.message("DW", (new StringBuilder()).append(Thread.currentThread().getName()).append(" finishFullFlush success=").append(success).toString());
		if (!$assertionsDisabled && !setFlushingDeleteQueue(null))
			throw new AssertionError();
		if (success)
			flushControl.finishFullFlush();
		else
			flushControl.abortFullFlushes();
		pendingChangesInCurrentFullFlush = false;
		break MISSING_BLOCK_LABEL_108;
		Exception exception;
		exception;
		pendingChangesInCurrentFullFlush = false;
		throw exception;
	}

}
