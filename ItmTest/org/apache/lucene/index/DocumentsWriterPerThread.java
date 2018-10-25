// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocumentsWriterPerThread.java

package org.apache.lucene.index;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.LiveDocsFormat;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			BufferedDeletes, SegmentInfo, SegmentWriteState, SegmentInfoPerCommit, 
//			PerDocWriteState, Term, FrozenBufferedDeletes, DocConsumer, 
//			DocumentsWriterDeleteQueue, DocumentsWriter, IndexWriter, LiveIndexWriterConfig, 
//			FieldInfos, DocumentsWriterFlushControl, IndexFileDeleter, TermVectorsConsumer, 
//			FreqProxTermsWriter, TermsHash, NormsConsumer, DocInverter, 
//			DocFieldProcessor

class DocumentsWriterPerThread
{
	static class FlushedSegment
	{

		final SegmentInfoPerCommit segmentInfo;
		final FieldInfos fieldInfos;
		final BufferedDeletes segmentDeletes;
		final MutableBits liveDocs;
		final int delCount;

		private FlushedSegment(SegmentInfoPerCommit segmentInfo, FieldInfos fieldInfos, BufferedDeletes segmentDeletes, MutableBits liveDocs, int delCount)
		{
			this.segmentInfo = segmentInfo;
			this.fieldInfos = fieldInfos;
			this.segmentDeletes = segmentDeletes;
			this.liveDocs = liveDocs;
			this.delCount = delCount;
		}

	}

	static class DocState
	{

		final DocumentsWriterPerThread docWriter;
		Analyzer analyzer;
		InfoStream infoStream;
		Similarity similarity;
		int docID;
		Iterable doc;
		String maxTermPrefix;

		public boolean testPoint(String name)
		{
			return docWriter.writer.testPoint(name);
		}

		public void clear()
		{
			doc = null;
			analyzer = null;
		}

		DocState(DocumentsWriterPerThread docWriter, InfoStream infoStream)
		{
			this.docWriter = docWriter;
			this.infoStream = infoStream;
		}
	}

	static abstract class IndexingChain
	{

		abstract DocConsumer getChain(DocumentsWriterPerThread documentswriterperthread);

		IndexingChain()
		{
		}
	}


	static final IndexingChain defaultIndexingChain = new IndexingChain() {

		DocConsumer getChain(DocumentsWriterPerThread documentsWriterPerThread)
		{
			TermsHashConsumer termVectorsWriter = new TermVectorsConsumer(documentsWriterPerThread);
			TermsHashConsumer freqProxWriter = new FreqProxTermsWriter();
			InvertedDocConsumer termsHash = new TermsHash(documentsWriterPerThread, freqProxWriter, true, new TermsHash(documentsWriterPerThread, termVectorsWriter, false, null));
			NormsConsumer normsWriter = new NormsConsumer(documentsWriterPerThread);
			DocInverter docInverter = new DocInverter(documentsWriterPerThread.docState, termsHash, normsWriter);
			return new DocFieldProcessor(documentsWriterPerThread, docInverter);
		}

	};
	private static final boolean INFO_VERBOSE = false;
	final DocumentsWriter parent;
	final Codec codec;
	final IndexWriter writer;
	final TrackingDirectoryWrapper directory;
	final Directory directoryOrig;
	final DocState docState;
	final DocConsumer consumer;
	final Counter bytesUsed;
	SegmentWriteState flushState;
	BufferedDeletes pendingDeletes;
	SegmentInfo segmentInfo;
	boolean aborting;
	boolean hasAborted;
	private FieldInfos.Builder fieldInfos;
	private final InfoStream infoStream;
	private int numDocsInRAM;
	private int flushedDocCount;
	DocumentsWriterDeleteQueue deleteQueue;
	DocumentsWriterDeleteQueue.DeleteSlice deleteSlice;
	private final NumberFormat nf;
	final org.apache.lucene.util.ByteBlockPool.Allocator byteBlockAllocator;
	static final int BYTE_BLOCK_NOT_MASK = -32768;
	static final int MAX_TERM_LENGTH_UTF8 = 32766;
	static final int INT_BLOCK_SHIFT = 13;
	static final int INT_BLOCK_SIZE = 8192;
	static final int INT_BLOCK_MASK = 8191;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DocumentsWriterPerThread.desiredAssertionStatus();

	void abort()
	{
		hasAborted = aborting = true;
		if (infoStream.isEnabled("DWPT"))
			infoStream.message("DWPT", "now abort");
		try
		{
			consumer.abort();
		}
		catch (Throwable t) { }
		pendingDeletes.clear();
		deleteSlice = deleteQueue.newSlice();
		doAfterFlush();
		aborting = false;
		if (infoStream.isEnabled("DWPT"))
			infoStream.message("DWPT", "done abort");
		break MISSING_BLOCK_LABEL_128;
		Exception exception;
		exception;
		aborting = false;
		if (infoStream.isEnabled("DWPT"))
			infoStream.message("DWPT", "done abort");
		throw exception;
	}

	public DocumentsWriterPerThread(Directory directory, DocumentsWriter parent, FieldInfos.Builder fieldInfos, IndexingChain indexingChain)
	{
		aborting = false;
		hasAborted = false;
		nf = NumberFormat.getInstance(Locale.ROOT);
		directoryOrig = directory;
		this.directory = new TrackingDirectoryWrapper(directory);
		this.parent = parent;
		this.fieldInfos = fieldInfos;
		writer = parent.indexWriter;
		infoStream = parent.infoStream;
		codec = parent.codec;
		docState = new DocState(this, infoStream);
		docState.similarity = parent.indexWriter.getConfig().getSimilarity();
		bytesUsed = Counter.newCounter();
		byteBlockAllocator = new org.apache.lucene.util.ByteBlockPool.DirectTrackingAllocator(bytesUsed);
		consumer = indexingChain.getChain(this);
		pendingDeletes = new BufferedDeletes();
		initialize();
	}

	public DocumentsWriterPerThread(DocumentsWriterPerThread other, FieldInfos.Builder fieldInfos)
	{
		this(other.directoryOrig, other.parent, fieldInfos, other.parent.chain);
	}

	void initialize()
	{
		deleteQueue = parent.deleteQueue;
		if (!$assertionsDisabled && numDocsInRAM != 0)
		{
			throw new AssertionError((new StringBuilder()).append("num docs ").append(numDocsInRAM).toString());
		} else
		{
			pendingDeletes.clear();
			deleteSlice = null;
			return;
		}
	}

	void setAborting()
	{
		aborting = true;
	}

	boolean checkAndResetHasAborted()
	{
		boolean retval = hasAborted;
		hasAborted = false;
		return retval;
	}

	public void updateDocument(Iterable doc, Analyzer analyzer, Term delTerm)
		throws IOException
	{
		boolean success;
		if (!$assertionsDisabled && !writer.testPoint("DocumentsWriterPerThread addDocument start"))
			throw new AssertionError();
		if (!$assertionsDisabled && deleteQueue == null)
			throw new AssertionError();
		docState.doc = doc;
		docState.analyzer = analyzer;
		docState.docID = numDocsInRAM;
		if (segmentInfo == null)
			initSegmentInfo();
		success = false;
		consumer.processDocument(fieldInfos);
		docState.clear();
		break MISSING_BLOCK_LABEL_121;
		Exception exception;
		exception;
		docState.clear();
		throw exception;
		success = true;
		if (!success)
			if (!aborting)
			{
				deleteDocID(docState.docID);
				numDocsInRAM++;
			} else
			{
				abort();
			}
		break MISSING_BLOCK_LABEL_212;
		Exception exception1;
		exception1;
		if (!success)
			if (!aborting)
			{
				deleteDocID(docState.docID);
				numDocsInRAM++;
			} else
			{
				abort();
			}
		throw exception1;
		success = false;
		consumer.finishDocument();
		success = true;
		if (!success)
			abort();
		break MISSING_BLOCK_LABEL_251;
		Exception exception2;
		exception2;
		if (!success)
			abort();
		throw exception2;
		finishDocument(delTerm);
		return;
	}

	private void initSegmentInfo()
	{
		String segment = writer.newSegmentName();
		segmentInfo = new SegmentInfo(directoryOrig, Constants.LUCENE_MAIN_VERSION, segment, -1, false, codec, null, null);
		if (!$assertionsDisabled && numDocsInRAM != 0)
			throw new AssertionError();
		else
			return;
	}

	public int updateDocuments(Iterable docs, Analyzer analyzer, Term delTerm)
		throws IOException
	{
		int docCount;
		if (!$assertionsDisabled && !writer.testPoint("DocumentsWriterPerThread addDocuments start"))
			throw new AssertionError();
		if (!$assertionsDisabled && deleteQueue == null)
			throw new AssertionError();
		docState.analyzer = analyzer;
		if (segmentInfo == null)
			initSegmentInfo();
		docCount = 0;
		Iterator i$ = docs.iterator();
_L3:
		if (!i$.hasNext()) goto _L2; else goto _L1
_L1:
		boolean success;
		Iterable doc = (Iterable)i$.next();
		docState.doc = doc;
		docState.docID = numDocsInRAM;
		docCount++;
		success = false;
		consumer.processDocument(fieldInfos);
		success = true;
		break MISSING_BLOCK_LABEL_139;
		exception;
		if (!success)
			if (!aborting)
			{
				int docID = docState.docID;
				for (int endDocID = docID - docCount; docID > endDocID; docID--)
					deleteDocID(docID);

				numDocsInRAM++;
			} else
			{
				abort();
			}
		throw exception;
		Exception exception;
		if (!success)
			if (!aborting)
			{
				int docID = docState.docID;
				for (int endDocID = docID - docCount; docID > endDocID; docID--)
					deleteDocID(docID);

				numDocsInRAM++;
			} else
			{
				abort();
			}
		success = false;
		consumer.finishDocument();
		success = true;
		if (!success)
			abort();
		continue; /* Loop/switch isn't completed */
		Exception exception1;
		exception1;
		if (!success)
			abort();
		throw exception1;
		finishDocument(null);
		  goto _L3
_L2:
		if (delTerm != null)
		{
			deleteQueue.add(delTerm, deleteSlice);
			if (!$assertionsDisabled && !deleteSlice.isTailItem(delTerm))
				throw new AssertionError("expected the delete term as the tail item");
			deleteSlice.apply(pendingDeletes, numDocsInRAM - docCount);
		}
		docState.clear();
		break MISSING_BLOCK_LABEL_405;
		Exception exception2;
		exception2;
		docState.clear();
		throw exception2;
		return docCount;
	}

	private void finishDocument(Term delTerm)
	{
		if (deleteSlice == null)
		{
			deleteSlice = deleteQueue.newSlice();
			if (delTerm != null)
			{
				deleteQueue.add(delTerm, deleteSlice);
				deleteSlice.reset();
			}
		} else
		if (delTerm != null)
		{
			deleteQueue.add(delTerm, deleteSlice);
			if (!$assertionsDisabled && !deleteSlice.isTailItem(delTerm))
				throw new AssertionError("expected the delete term as the tail item");
			deleteSlice.apply(pendingDeletes, numDocsInRAM);
		} else
		if (deleteQueue.updateSlice(deleteSlice))
			deleteSlice.apply(pendingDeletes, numDocsInRAM);
		numDocsInRAM++;
	}

	void deleteDocID(int docIDUpto)
	{
		pendingDeletes.addDocID(docIDUpto);
	}

	public int numDeleteTerms()
	{
		return pendingDeletes.numTermDeletes.get();
	}

	public int getNumDocsInRAM()
	{
		return numDocsInRAM;
	}

	private void doAfterFlush()
	{
		segmentInfo = null;
		consumer.doAfterFlush();
		directory.getCreatedFiles().clear();
		fieldInfos = new FieldInfos.Builder(fieldInfos.globalFieldNumbers);
		parent.subtractFlushedNumDocs(numDocsInRAM);
		numDocsInRAM = 0;
	}

	FrozenBufferedDeletes prepareFlush()
	{
		if (!$assertionsDisabled && numDocsInRAM <= 0)
			throw new AssertionError();
		FrozenBufferedDeletes globalDeletes = deleteQueue.freezeGlobalBuffer(deleteSlice);
		if (deleteSlice != null)
		{
			deleteSlice.apply(pendingDeletes, numDocsInRAM);
			if (!$assertionsDisabled && !deleteSlice.isEmpty())
				throw new AssertionError();
			deleteSlice = null;
		}
		return globalDeletes;
	}

	FlushedSegment flush()
		throws IOException
	{
		double startMBUsed;
		boolean success;
		if (!$assertionsDisabled && numDocsInRAM <= 0)
			throw new AssertionError();
		if (!$assertionsDisabled && deleteSlice != null)
			throw new AssertionError("all deletes must be applied in prepareFlush");
		segmentInfo.setDocCount(numDocsInRAM);
		flushState = new SegmentWriteState(infoStream, directory, segmentInfo, fieldInfos.finish(), writer.getConfig().getTermIndexInterval(), pendingDeletes, new IOContext(new FlushInfo(numDocsInRAM, bytesUsed())));
		startMBUsed = (double)parent.flushControl.netBytes() / 1024D / 1024D;
		if (pendingDeletes.docIDs.size() > 0)
		{
			flushState.liveDocs = codec.liveDocsFormat().newLiveDocs(numDocsInRAM);
			int delDocID;
			for (Iterator i$ = pendingDeletes.docIDs.iterator(); i$.hasNext(); flushState.liveDocs.clear(delDocID))
				delDocID = ((Integer)i$.next()).intValue();

			flushState.delCountOnFlush = pendingDeletes.docIDs.size();
			pendingDeletes.bytesUsed.addAndGet(-pendingDeletes.docIDs.size() * BufferedDeletes.BYTES_PER_DEL_DOCID);
			pendingDeletes.docIDs.clear();
		}
		if (aborting)
		{
			if (infoStream.isEnabled("DWPT"))
				infoStream.message("DWPT", "flush: skip because aborting is set");
			return null;
		}
		if (infoStream.isEnabled("DWPT"))
			infoStream.message("DWPT", (new StringBuilder()).append("flush postings as segment ").append(flushState.segmentInfo.name).append(" numDocs=").append(numDocsInRAM).toString());
		success = false;
		FlushedSegment flushedsegment;
		consumer.flush(flushState);
		pendingDeletes.terms.clear();
		segmentInfo.setFiles(new HashSet(directory.getCreatedFiles()));
		SegmentInfoPerCommit segmentInfoPerCommit = new SegmentInfoPerCommit(segmentInfo, 0, -1L);
		if (infoStream.isEnabled("DWPT"))
		{
			infoStream.message("DWPT", (new StringBuilder()).append("new segment has ").append(flushState.liveDocs != null ? flushState.segmentInfo.getDocCount() - flushState.delCountOnFlush : 0).append(" deleted docs").toString());
			infoStream.message("DWPT", (new StringBuilder()).append("new segment has ").append(flushState.fieldInfos.hasVectors() ? "vectors" : "no vectors").append("; ").append(flushState.fieldInfos.hasNorms() ? "norms" : "no norms").append("; ").append(flushState.fieldInfos.hasDocValues() ? "docValues" : "no docValues").append("; ").append(flushState.fieldInfos.hasProx() ? "prox" : "no prox").append("; ").append(flushState.fieldInfos.hasFreq() ? "freqs" : "no freqs").toString());
			infoStream.message("DWPT", (new StringBuilder()).append("flushedFiles=").append(segmentInfoPerCommit.files()).toString());
			infoStream.message("DWPT", (new StringBuilder()).append("flushed codec=").append(codec).toString());
		}
		flushedDocCount += flushState.segmentInfo.getDocCount();
		BufferedDeletes segmentDeletes;
		if (pendingDeletes.queries.isEmpty())
		{
			pendingDeletes.clear();
			segmentDeletes = null;
		} else
		{
			segmentDeletes = pendingDeletes;
			pendingDeletes = new BufferedDeletes();
		}
		if (infoStream.isEnabled("DWPT"))
		{
			double newSegmentSize = (double)segmentInfo.sizeInBytes() / 1024D / 1024D;
			infoStream.message("DWPT", (new StringBuilder()).append("flushed: segment=").append(segmentInfo.name).append(" ramUsed=").append(nf.format(startMBUsed)).append(" MB").append(" newFlushedSize(includes docstores)=").append(nf.format(newSegmentSize)).append(" MB").append(" docs/MB=").append(nf.format((double)flushedDocCount / newSegmentSize)).toString());
		}
		if (!$assertionsDisabled && segmentInfo == null)
			throw new AssertionError();
		FlushedSegment fs = new FlushedSegment(segmentInfoPerCommit, flushState.fieldInfos, segmentDeletes, flushState.liveDocs, flushState.delCountOnFlush);
		doAfterFlush();
		success = true;
		flushedsegment = fs;
		if (!success)
		{
			if (segmentInfo != null)
				synchronized (parent.indexWriter)
				{
					parent.indexWriter.deleter.refresh(segmentInfo.name);
				}
			abort();
		}
		return flushedsegment;
		Exception exception1;
		exception1;
		if (!success)
		{
			if (segmentInfo != null)
				synchronized (parent.indexWriter)
				{
					parent.indexWriter.deleter.refresh(segmentInfo.name);
				}
			abort();
		}
		throw exception1;
	}

	SegmentInfo getSegmentInfo()
	{
		return segmentInfo;
	}

	long bytesUsed()
	{
		return bytesUsed.get() + pendingDeletes.bytesUsed.get();
	}

	int[] getIntBlock()
	{
		int b[] = new int[8192];
		bytesUsed.addAndGet(32768L);
		return b;
	}

	void recycleIntBlocks(int blocks[][], int offset, int length)
	{
		bytesUsed.addAndGet(-(length * 32768));
	}

	PerDocWriteState newPerDocWriteState(String segmentSuffix)
	{
		if (!$assertionsDisabled && segmentInfo == null)
			throw new AssertionError();
		else
			return new PerDocWriteState(infoStream, directory, segmentInfo, bytesUsed, segmentSuffix, IOContext.DEFAULT);
	}

	public String toString()
	{
		return (new StringBuilder()).append("DocumentsWriterPerThread [pendingDeletes=").append(pendingDeletes).append(", segment=").append(segmentInfo == null ? "null" : segmentInfo.name).append(", aborting=").append(aborting).append(", numDocsInRAM=").append(numDocsInRAM).append(", deleteQueue=").append(deleteQueue).append("]").toString();
	}

}
