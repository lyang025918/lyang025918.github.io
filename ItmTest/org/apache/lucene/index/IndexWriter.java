// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexWriter.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.codecs.*;
import org.apache.lucene.codecs.lucene3x.Lucene3xCodec;
import org.apache.lucene.codecs.lucene3x.Lucene3xSegmentInfoFormat;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			LiveIndexWriterConfig, BufferedDeletesStream, SegmentInfos, DocumentsWriter, 
//			IndexFileDeleter, SegmentInfoPerCommit, FieldInfo, Term, 
//			AtomicReader, AtomicReaderContext, SegmentReader, SegmentInfo, 
//			SegmentMerger, TwoPhaseCommit, DirectoryReader, ReadersAndLiveDocs, 
//			IndexWriterConfig, IndexCommit, FieldInfos, FrozenBufferedDeletes, 
//			MergeState, StandardDirectoryReader, MergePolicy, IndexFileNames, 
//			MergeScheduler, DocumentsWriterPerThreadPool, IndexReader, ReaderUtil, 
//			DocumentsWriterFlushControl, DocumentsWriterPerThread

public class IndexWriter
	implements Closeable, TwoPhaseCommit
{
	public static abstract class IndexReaderWarmer
	{

		public abstract void warm(AtomicReader atomicreader)
			throws IOException;

		protected IndexReaderWarmer()
		{
		}
	}

	class ReaderPool
	{

		private final Map readerMap = new HashMap();
		static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexWriter.desiredAssertionStatus();
		final IndexWriter this$0;

		public synchronized boolean infoIsLive(SegmentInfoPerCommit info)
		{
			int idx = segmentInfos.indexOf(info);
			if (!$assertionsDisabled && idx == -1)
				throw new AssertionError((new StringBuilder()).append("info=").append(info).append(" isn't live").toString());
			if (!$assertionsDisabled && segmentInfos.info(idx) != info)
				throw new AssertionError((new StringBuilder()).append("info=").append(info).append(" doesn't match live info in segmentInfos").toString());
			else
				return true;
		}

		public synchronized void drop(SegmentInfoPerCommit info)
			throws IOException
		{
			ReadersAndLiveDocs rld = (ReadersAndLiveDocs)readerMap.get(info);
			if (rld != null)
			{
				if (!$assertionsDisabled && info != rld.info)
					throw new AssertionError();
				readerMap.remove(info);
				rld.dropReaders();
			}
		}

		public synchronized void release(ReadersAndLiveDocs rld)
			throws IOException
		{
			rld.decRef();
			if (!$assertionsDisabled && rld.refCount() < 1)
				throw new AssertionError();
			if (!poolReaders && rld.refCount() == 1)
			{
				if (rld.writeLiveDocs(directory))
				{
					if (!$assertionsDisabled && !infoIsLive(rld.info))
						throw new AssertionError();
					deleter.checkpoint(segmentInfos, false);
				}
				rld.dropReaders();
				readerMap.remove(rld.info);
			}
		}

		synchronized void dropAll(boolean doSave)
			throws IOException
		{
			ReadersAndLiveDocs rld;
			for (Iterator it = readerMap.entrySet().iterator(); it.hasNext(); rld.dropReaders())
			{
				rld = (ReadersAndLiveDocs)((java.util.Map.Entry)it.next()).getValue();
				if (doSave && rld.writeLiveDocs(directory))
				{
					if (!$assertionsDisabled && !infoIsLive(rld.info))
						throw new AssertionError();
					deleter.checkpoint(segmentInfos, false);
				}
				it.remove();
			}

			if (!$assertionsDisabled && readerMap.size() != 0)
				throw new AssertionError();
			else
				return;
		}

		public synchronized void commit(SegmentInfos infos)
			throws IOException
		{
			Iterator i$ = infos.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
				ReadersAndLiveDocs rld = (ReadersAndLiveDocs)readerMap.get(info);
				if (rld != null)
				{
					if (!$assertionsDisabled && rld.info != info)
						throw new AssertionError();
					if (rld.writeLiveDocs(directory))
					{
						if (!$assertionsDisabled && !infoIsLive(info))
							throw new AssertionError();
						deleter.checkpoint(segmentInfos, false);
					}
				}
			} while (true);
		}

		public synchronized ReadersAndLiveDocs get(SegmentInfoPerCommit info, boolean create)
		{
			if (!$assertionsDisabled && info.info.dir != directory)
				throw new AssertionError((new StringBuilder()).append("info.dir=").append(info.info.dir).append(" vs ").append(directory).toString());
			ReadersAndLiveDocs rld = (ReadersAndLiveDocs)readerMap.get(info);
			if (rld == null)
			{
				if (!create)
					return null;
				rld = new ReadersAndLiveDocs(IndexWriter.this, info);
				readerMap.put(info, rld);
			} else
			if (!$assertionsDisabled && rld.info != info)
				throw new AssertionError((new StringBuilder()).append("rld.info=").append(rld.info).append(" info=").append(info).append(" isLive?=").append(infoIsLive(rld.info)).append(" vs ").append(infoIsLive(info)).toString());
			if (create)
				rld.incRef();
			return rld;
		}


		ReaderPool()
		{
			this$0 = IndexWriter.this;
			super();
		}
	}


	public static final String WRITE_LOCK_NAME = "write.lock";
	public static final int MAX_TERM_LENGTH = 32766;
	private volatile boolean hitOOM;
	private final Directory directory;
	private final Analyzer analyzer;
	private volatile long changeCount;
	private long lastCommitChangeCount;
	private List rollbackSegments;
	volatile SegmentInfos pendingCommit;
	volatile long pendingCommitChangeCount;
	private Collection filesToCommit;
	final SegmentInfos segmentInfos;
	final FieldInfos.FieldNumbers globalFieldNumberMap;
	private DocumentsWriter docWriter;
	final IndexFileDeleter deleter;
	private Map segmentsToMerge;
	private int mergeMaxNumSegments;
	private Lock writeLock;
	private volatile boolean closed;
	private volatile boolean closing;
	private HashSet mergingSegments;
	private MergePolicy mergePolicy;
	private final MergeScheduler mergeScheduler;
	private LinkedList pendingMerges;
	private Set runningMerges;
	private List mergeExceptions;
	private long mergeGen;
	private boolean stopMerges;
	final AtomicInteger flushCount;
	final AtomicInteger flushDeletesCount;
	final ReaderPool readerPool;
	final BufferedDeletesStream bufferedDeletesStream;
	private volatile boolean poolReaders;
	private final LiveIndexWriterConfig config;
	final Codec codec;
	final InfoStream infoStream;
	private final Object commitLock;
	private final Object fullFlushLock;
	private boolean keepFullyDeletedSegments;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexWriter.desiredAssertionStatus();

	DirectoryReader getReader()
		throws IOException
	{
		return getReader(true);
	}

	DirectoryReader getReader(boolean applyAllDeletes)
		throws IOException
	{
		long tStart;
		ensureOpen();
		tStart = System.currentTimeMillis();
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "flush at getReader");
		poolReaders = true;
		doBeforeFlush();
		boolean anySegmentFlushed = false;
		Object obj = fullFlushLock;
		JVM INSTR monitorenter ;
		boolean success = false;
		DirectoryReader r;
		anySegmentFlushed = docWriter.flushAllThreads();
		if (!anySegmentFlushed)
			flushCount.incrementAndGet();
		success = true;
		synchronized (this)
		{
			maybeApplyDeletes(applyAllDeletes);
			r = StandardDirectoryReader.open(this, segmentInfos, applyAllDeletes);
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("return reader version=").append(r.getVersion()).append(" reader=").append(r).toString());
		}
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during NRT reader");
		docWriter.finishFullFlush(success);
		doAfterFlush();
		break MISSING_BLOCK_LABEL_318;
		OutOfMemoryError oom;
		oom;
		DirectoryReader directoryreader;
		handleOOM(oom, "getReader");
		directoryreader = null;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during NRT reader");
		docWriter.finishFullFlush(success);
		doAfterFlush();
		return directoryreader;
		Exception exception1;
		exception1;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during NRT reader");
		docWriter.finishFullFlush(success);
		doAfterFlush();
		throw exception1;
		obj;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception2;
		exception2;
		throw exception2;
_L1:
		if (anySegmentFlushed)
			maybeMerge();
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("getReader took ").append(System.currentTimeMillis() - tStart).append(" msec").toString());
		return r;
	}

	public int numDeletedDocs(SegmentInfoPerCommit info)
	{
		ensureOpen(false);
		int delCount = info.getDelCount();
		ReadersAndLiveDocs rld = readerPool.get(info, false);
		if (rld != null)
			delCount += rld.getPendingDeleteCount();
		return delCount;
	}

	protected final void ensureOpen(boolean includePendingClose)
		throws AlreadyClosedException
	{
		if (closed || includePendingClose && closing)
			throw new AlreadyClosedException("this IndexWriter is closed");
		else
			return;
	}

	protected final void ensureOpen()
		throws AlreadyClosedException
	{
		ensureOpen(true);
	}

	public IndexWriter(Directory d, IndexWriterConfig conf)
		throws IOException
	{
		boolean success;
		segmentsToMerge = new HashMap();
		mergingSegments = new HashSet();
		pendingMerges = new LinkedList();
		runningMerges = new HashSet();
		mergeExceptions = new ArrayList();
		flushCount = new AtomicInteger();
		flushDeletesCount = new AtomicInteger();
		readerPool = new ReaderPool();
		commitLock = new Object();
		fullFlushLock = new Object();
		config = new LiveIndexWriterConfig(conf.clone());
		directory = d;
		analyzer = config.getAnalyzer();
		infoStream = config.getInfoStream();
		mergePolicy = config.getMergePolicy();
		mergePolicy.setIndexWriter(this);
		mergeScheduler = config.getMergeScheduler();
		codec = config.getCodec();
		bufferedDeletesStream = new BufferedDeletesStream(infoStream);
		poolReaders = config.getReaderPooling();
		writeLock = directory.makeLock("write.lock");
		if (!writeLock.obtain(config.getWriteLockTimeout()))
			throw new LockObtainFailedException((new StringBuilder()).append("Index locked for write: ").append(writeLock).toString());
		success = false;
		IndexWriterConfig.OpenMode mode = config.getOpenMode();
		boolean create;
		if (mode == IndexWriterConfig.OpenMode.CREATE)
			create = true;
		else
		if (mode == IndexWriterConfig.OpenMode.APPEND)
			create = false;
		else
			create = !DirectoryReader.indexExists(directory);
		segmentInfos = new SegmentInfos();
		if (create)
		{
			try
			{
				segmentInfos.read(directory);
				segmentInfos.clear();
			}
			catch (IOException e) { }
			changeCount++;
			segmentInfos.changed();
		} else
		{
			segmentInfos.read(directory);
			IndexCommit commit = config.getIndexCommit();
			if (commit != null)
			{
				if (commit.getDirectory() != directory)
					throw new IllegalArgumentException("IndexCommit's directory doesn't match my directory");
				SegmentInfos oldInfos = new SegmentInfos();
				oldInfos.read(directory, commit.getSegmentsFileName());
				segmentInfos.replace(oldInfos);
				changeCount++;
				segmentInfos.changed();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", (new StringBuilder()).append("init: loaded commit \"").append(commit.getSegmentsFileName()).append("\"").toString());
			}
		}
		rollbackSegments = segmentInfos.createBackupSegmentInfos();
		globalFieldNumberMap = getFieldNumberMap();
		docWriter = new DocumentsWriter(codec, config, directory, this, globalFieldNumberMap, bufferedDeletesStream);
		synchronized (this)
		{
			deleter = new IndexFileDeleter(directory, config.getIndexDeletionPolicy(), segmentInfos, infoStream, this);
		}
		if (deleter.startingCommitDeleted)
		{
			changeCount++;
			segmentInfos.changed();
		}
		if (infoStream.isEnabled("IW"))
		{
			infoStream.message("IW", (new StringBuilder()).append("init: create=").append(create).toString());
			messageState();
		}
		success = true;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "init: hit exception on init; releasing write lock");
			try
			{
				writeLock.release();
			}
			catch (Throwable t) { }
			writeLock = null;
		}
		break MISSING_BLOCK_LABEL_815;
		Exception exception1;
		exception1;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "init: hit exception on init; releasing write lock");
			try
			{
				writeLock.release();
			}
			catch (Throwable t) { }
			writeLock = null;
		}
		throw exception1;
	}

	private FieldInfos getFieldInfos(SegmentInfo info)
		throws IOException
	{
		Directory cfsDir = null;
		FieldInfos fieldinfos;
		if (info.getUseCompoundFile())
			cfsDir = new CompoundFileDirectory(info.dir, IndexFileNames.segmentFileName(info.name, "", "cfs"), IOContext.READONCE, false);
		else
			cfsDir = info.dir;
		fieldinfos = info.getCodec().fieldInfosFormat().getFieldInfosReader().read(cfsDir, info.name, IOContext.READONCE);
		if (info.getUseCompoundFile() && cfsDir != null)
			cfsDir.close();
		return fieldinfos;
		Exception exception;
		exception;
		if (info.getUseCompoundFile() && cfsDir != null)
			cfsDir.close();
		throw exception;
	}

	private FieldInfos.FieldNumbers getFieldNumberMap()
		throws IOException
	{
		FieldInfos.FieldNumbers map = new FieldInfos.FieldNumbers();
		SegmentInfoPerCommit biggest = null;
		Iterator i$ = segmentInfos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			if (biggest == null || info.info.getDocCount() - info.getDelCount() > biggest.info.getDocCount() - biggest.getDelCount())
				biggest = info;
		} while (true);
		if (biggest != null)
		{
			FieldInfo fi;
			for (i$ = getFieldInfos(biggest.info).iterator(); i$.hasNext(); map.addOrGet(fi.name, fi.number))
				fi = (FieldInfo)i$.next();

		}
		return map;
	}

	public LiveIndexWriterConfig getConfig()
	{
		ensureOpen(false);
		return config;
	}

	private void messageState()
	{
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("\ndir=").append(directory).append("\n").append("index=").append(segString()).append("\n").append("version=").append(Constants.LUCENE_VERSION).append("\n").append(config.toString()).toString());
	}

	public void close()
		throws IOException
	{
		close(true);
	}

	public void close(boolean waitForMerges)
		throws IOException
	{
		synchronized (commitLock)
		{
			if (shouldClose())
				if (hitOOM)
					rollbackInternal();
				else
					closeInternal(waitForMerges, true);
		}
	}

	private synchronized boolean shouldClose()
	{
		while (!closed) 
		{
			if (!closing)
			{
				closing = true;
				return true;
			}
			doWait();
		}
		return false;
	}

	private void closeInternal(boolean waitForMerges, boolean doFlush)
		throws IOException
	{
		boolean interrupted = false;
		if (pendingCommit != null)
			throw new IllegalStateException("cannot close: prepareCommit was already called with no corresponding call to commit");
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("now flush at close waitForMerges=").append(waitForMerges).toString());
		docWriter.close();
		if (doFlush)
			flush(waitForMerges, true);
		else
			docWriter.abort();
		interrupted = Thread.interrupted();
		if (waitForMerges)
			try
			{
				mergeScheduler.merge(this);
			}
			catch (ThreadInterruptedException tie)
			{
				interrupted = true;
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "interrupted while waiting for final merges");
			}
		synchronized (this)
		{
			do
				try
				{
					finishMerges(waitForMerges && !interrupted);
					break;
				}
				catch (ThreadInterruptedException tie)
				{
					interrupted = true;
					if (infoStream.isEnabled("IW"))
						infoStream.message("IW", "interrupted while waiting for merges to finish");
				}
			while (true);
			stopMerges = true;
		}
		IOUtils.closeWhileHandlingException(new Closeable[] {
			mergePolicy, mergeScheduler
		});
		break MISSING_BLOCK_LABEL_434;
		Exception exception1;
		exception1;
		IOUtils.closeWhileHandlingException(new Closeable[] {
			mergePolicy, mergeScheduler
		});
		throw exception1;
		Exception exception2;
		exception2;
		interrupted = Thread.interrupted();
		if (waitForMerges)
			try
			{
				mergeScheduler.merge(this);
			}
			catch (ThreadInterruptedException tie)
			{
				interrupted = true;
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "interrupted while waiting for final merges");
			}
		synchronized (this)
		{
			do
				try
				{
					finishMerges(waitForMerges && !interrupted);
					break;
				}
				catch (ThreadInterruptedException tie)
				{
					interrupted = true;
					if (infoStream.isEnabled("IW"))
						infoStream.message("IW", "interrupted while waiting for merges to finish");
				}
			while (true);
			stopMerges = true;
		}
		IOUtils.closeWhileHandlingException(new Closeable[] {
			mergePolicy, mergeScheduler
		});
		break MISSING_BLOCK_LABEL_431;
		Exception exception4;
		exception4;
		IOUtils.closeWhileHandlingException(new Closeable[] {
			mergePolicy, mergeScheduler
		});
		throw exception4;
		throw exception2;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "now call final commit()");
		if (doFlush)
			commitInternal(null);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("at close: ").append(segString()).toString());
		DocumentsWriter oldWriter = docWriter;
		synchronized (this)
		{
			readerPool.dropAll(true);
			docWriter = null;
			deleter.close();
		}
		if (writeLock != null)
		{
			writeLock.release();
			writeLock = null;
		}
		synchronized (this)
		{
			closed = true;
		}
		if (!$assertionsDisabled && oldWriter.perThreadPool.numDeactivatedThreadStates() != oldWriter.perThreadPool.getMaxThreadStates())
			throw new AssertionError();
		synchronized (this)
		{
			closing = false;
			notifyAll();
			if (!closed && infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception while closing");
		}
		if (interrupted)
			Thread.currentThread().interrupt();
		break MISSING_BLOCK_LABEL_855;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "closeInternal");
		synchronized (this)
		{
			closing = false;
			notifyAll();
			if (!closed && infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception while closing");
		}
		if (interrupted)
			Thread.currentThread().interrupt();
		break MISSING_BLOCK_LABEL_855;
		Exception exception9;
		exception9;
		synchronized (this)
		{
			closing = false;
			notifyAll();
			if (!closed && infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception while closing");
		}
		if (interrupted)
			Thread.currentThread().interrupt();
		throw exception9;
	}

	public Directory getDirectory()
	{
		return directory;
	}

	public Analyzer getAnalyzer()
	{
		ensureOpen();
		return analyzer;
	}

	public synchronized int maxDoc()
	{
		ensureOpen();
		int count;
		if (docWriter != null)
			count = docWriter.getNumDocs();
		else
			count = 0;
		count += segmentInfos.totalDocCount();
		return count;
	}

	public synchronized int numDocs()
	{
		ensureOpen();
		int count;
		if (docWriter != null)
			count = docWriter.getNumDocs();
		else
			count = 0;
		for (Iterator i$ = segmentInfos.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			count += info.info.getDocCount() - numDeletedDocs(info);
		}

		return count;
	}

	public synchronized boolean hasDeletions()
	{
		ensureOpen();
		if (bufferedDeletesStream.any())
			return true;
		if (docWriter.anyDeletions())
			return true;
		for (Iterator i$ = segmentInfos.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			if (info.hasDeletions())
				return true;
		}

		return false;
	}

	public void addDocument(Iterable doc)
		throws IOException
	{
		addDocument(doc, analyzer);
	}

	public void addDocument(Iterable doc, Analyzer analyzer)
		throws IOException
	{
		updateDocument(null, doc, analyzer);
	}

	public void addDocuments(Iterable docs)
		throws IOException
	{
		addDocuments(docs, analyzer);
	}

	public void addDocuments(Iterable docs, Analyzer analyzer)
		throws IOException
	{
		updateDocuments(null, docs, analyzer);
	}

	public void updateDocuments(Term delTerm, Iterable docs)
		throws IOException
	{
		updateDocuments(delTerm, docs, analyzer);
	}

	public void updateDocuments(Term delTerm, Iterable docs, Analyzer analyzer)
		throws IOException
	{
		ensureOpen();
		boolean success;
		boolean anySegmentFlushed;
		success = false;
		anySegmentFlushed = false;
		anySegmentFlushed = docWriter.updateDocuments(docs, analyzer, delTerm);
		success = true;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception updating document");
		break MISSING_BLOCK_LABEL_89;
		Exception exception;
		exception;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception updating document");
		throw exception;
		if (anySegmentFlushed)
			maybeMerge();
		break MISSING_BLOCK_LABEL_111;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "updateDocuments");
	}

	public void deleteDocuments(Term term)
		throws IOException
	{
		ensureOpen();
		try
		{
			docWriter.deleteTerms(new Term[] {
				term
			});
		}
		catch (OutOfMemoryError oom)
		{
			handleOOM(oom, "deleteDocuments(Term)");
		}
	}

	public synchronized boolean tryDeleteDocument(IndexReader readerIn, int docID)
		throws IOException
	{
		ReadersAndLiveDocs rld;
		AtomicReader reader;
		if (readerIn instanceof AtomicReader)
		{
			reader = (AtomicReader)readerIn;
		} else
		{
			List leaves = readerIn.leaves();
			int subIndex = ReaderUtil.subIndex(docID, leaves);
			reader = ((AtomicReaderContext)leaves.get(subIndex)).reader();
			docID -= ((AtomicReaderContext)leaves.get(subIndex)).docBase;
			if (!$assertionsDisabled && docID < 0)
				throw new AssertionError();
			if (!$assertionsDisabled && docID >= reader.maxDoc())
				throw new AssertionError();
		}
		if (!(reader instanceof SegmentReader))
			throw new IllegalArgumentException("the reader must be a SegmentReader or composite reader containing only SegmentReaders");
		SegmentInfoPerCommit info = ((SegmentReader)reader).getSegmentInfo();
		if (segmentInfos.indexOf(info) == -1)
			break MISSING_BLOCK_LABEL_269;
		rld = readerPool.get(info, false);
		if (rld == null)
			break MISSING_BLOCK_LABEL_269;
		BufferedDeletesStream buffereddeletesstream = bufferedDeletesStream;
		JVM INSTR monitorenter ;
		rld.initWritableLiveDocs();
		if (rld.delete(docID))
		{
			int fullDelCount = rld.info.getDelCount() + rld.getPendingDeleteCount();
			if (fullDelCount == rld.info.info.getDocCount() && !mergingSegments.contains(rld.info))
			{
				segmentInfos.remove(rld.info);
				readerPool.drop(rld.info);
				checkpoint();
			}
		}
		return true;
		Exception exception;
		exception;
		throw exception;
		return false;
	}

	public transient void deleteDocuments(Term terms[])
		throws IOException
	{
		ensureOpen();
		try
		{
			docWriter.deleteTerms(terms);
		}
		catch (OutOfMemoryError oom)
		{
			handleOOM(oom, "deleteDocuments(Term..)");
		}
	}

	public void deleteDocuments(Query query)
		throws IOException
	{
		ensureOpen();
		try
		{
			docWriter.deleteQueries(new Query[] {
				query
			});
		}
		catch (OutOfMemoryError oom)
		{
			handleOOM(oom, "deleteDocuments(Query)");
		}
	}

	public transient void deleteDocuments(Query queries[])
		throws IOException
	{
		ensureOpen();
		try
		{
			docWriter.deleteQueries(queries);
		}
		catch (OutOfMemoryError oom)
		{
			handleOOM(oom, "deleteDocuments(Query..)");
		}
	}

	public void updateDocument(Term term, Iterable doc)
		throws IOException
	{
		ensureOpen();
		updateDocument(term, doc, getAnalyzer());
	}

	public void updateDocument(Term term, Iterable doc, Analyzer analyzer)
		throws IOException
	{
		ensureOpen();
		boolean success;
		boolean anySegmentFlushed;
		success = false;
		anySegmentFlushed = false;
		anySegmentFlushed = docWriter.updateDocument(doc, analyzer, term);
		success = true;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception updating document");
		break MISSING_BLOCK_LABEL_89;
		Exception exception;
		exception;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception updating document");
		throw exception;
		if (anySegmentFlushed)
			maybeMerge();
		break MISSING_BLOCK_LABEL_111;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "updateDocument");
	}

	final synchronized int getSegmentCount()
	{
		return segmentInfos.size();
	}

	final synchronized int getNumBufferedDocuments()
	{
		return docWriter.getNumDocs();
	}

	final synchronized Collection getIndexFileNames()
		throws IOException
	{
		return segmentInfos.files(directory, true);
	}

	final synchronized int getDocCount(int i)
	{
		if (i >= 0 && i < segmentInfos.size())
			return segmentInfos.info(i).info.getDocCount();
		else
			return -1;
	}

	final int getFlushCount()
	{
		return flushCount.get();
	}

	final int getFlushDeletesCount()
	{
		return flushDeletesCount.get();
	}

	final String newSegmentName()
	{
		SegmentInfos segmentinfos = segmentInfos;
		JVM INSTR monitorenter ;
		changeCount++;
		segmentInfos.changed();
		return (new StringBuilder()).append("_").append(Integer.toString(segmentInfos.counter++, 36)).toString();
		Exception exception;
		exception;
		throw exception;
	}

	public void forceMerge(int maxNumSegments)
		throws IOException
	{
		forceMerge(maxNumSegments, true);
	}

	public void forceMerge(int maxNumSegments, boolean doWait)
		throws IOException
	{
		ensureOpen();
		if (maxNumSegments < 1)
			throw new IllegalArgumentException((new StringBuilder()).append("maxNumSegments must be >= 1; got ").append(maxNumSegments).toString());
		if (infoStream.isEnabled("IW"))
		{
			infoStream.message("IW", (new StringBuilder()).append("forceMerge: index now ").append(segString()).toString());
			infoStream.message("IW", "now flush at forceMerge");
		}
		flush(true, true);
		synchronized (this)
		{
			resetMergeExceptions();
			segmentsToMerge.clear();
			SegmentInfoPerCommit info;
			for (Iterator i$ = segmentInfos.iterator(); i$.hasNext(); segmentsToMerge.put(info, Boolean.TRUE))
				info = (SegmentInfoPerCommit)i$.next();

			mergeMaxNumSegments = maxNumSegments;
			MergePolicy.OneMerge merge;
			for (Iterator i$ = pendingMerges.iterator(); i$.hasNext(); segmentsToMerge.put(merge.info, Boolean.TRUE))
			{
				merge = (MergePolicy.OneMerge)i$.next();
				merge.maxNumSegments = maxNumSegments;
			}

			MergePolicy.OneMerge merge;
			for (Iterator i$ = runningMerges.iterator(); i$.hasNext(); segmentsToMerge.put(merge.info, Boolean.TRUE))
			{
				merge = (MergePolicy.OneMerge)i$.next();
				merge.maxNumSegments = maxNumSegments;
			}

		}
		maybeMerge(maxNumSegments);
		if (doWait)
		{
			synchronized (this)
			{
				do
				{
					if (hitOOM)
						throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot complete forceMerge");
					if (mergeExceptions.size() > 0)
					{
						int size = mergeExceptions.size();
						for (int i = 0; i < size; i++)
						{
							MergePolicy.OneMerge merge = (MergePolicy.OneMerge)mergeExceptions.get(i);
							if (merge.maxNumSegments != -1)
							{
								IOException err = new IOException((new StringBuilder()).append("background merge hit exception: ").append(merge.segString(directory)).toString());
								Throwable t = merge.getException();
								if (t != null)
									err.initCause(t);
								throw err;
							}
						}

					}
					if (!maxNumSegmentsMergesPending())
						break;
					doWait();
				} while (true);
			}
			ensureOpen();
		}
	}

	private synchronized boolean maxNumSegmentsMergesPending()
	{
		for (Iterator i$ = pendingMerges.iterator(); i$.hasNext();)
		{
			MergePolicy.OneMerge merge = (MergePolicy.OneMerge)i$.next();
			if (merge.maxNumSegments != -1)
				return true;
		}

		for (Iterator i$ = runningMerges.iterator(); i$.hasNext();)
		{
			MergePolicy.OneMerge merge = (MergePolicy.OneMerge)i$.next();
			if (merge.maxNumSegments != -1)
				return true;
		}

		return false;
	}

	public void forceMergeDeletes(boolean doWait)
		throws IOException
	{
		ensureOpen();
		flush(true, true);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("forceMergeDeletes: index now ").append(segString()).toString());
		MergePolicy.MergeSpecification spec;
		synchronized (this)
		{
			spec = mergePolicy.findForcedDeletesMerges(segmentInfos);
			if (spec != null)
			{
				int numMerges = spec.merges.size();
				for (int i = 0; i < numMerges; i++)
					registerMerge((MergePolicy.OneMerge)spec.merges.get(i));

			}
		}
		mergeScheduler.merge(this);
		if (spec != null && doWait)
		{
			int numMerges = spec.merges.size();
			synchronized (this)
			{
				boolean running = true;
				do
				{
					if (!running)
						break;
					if (hitOOM)
						throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot complete forceMergeDeletes");
					running = false;
					for (int i = 0; i < numMerges; i++)
					{
						MergePolicy.OneMerge merge = (MergePolicy.OneMerge)spec.merges.get(i);
						if (pendingMerges.contains(merge) || runningMerges.contains(merge))
							running = true;
						Throwable t = merge.getException();
						if (t != null)
						{
							IOException ioe = new IOException((new StringBuilder()).append("background merge hit exception: ").append(merge.segString(directory)).toString());
							ioe.initCause(t);
							throw ioe;
						}
					}

					if (running)
						doWait();
				} while (true);
			}
		}
	}

	public void forceMergeDeletes()
		throws IOException
	{
		forceMergeDeletes(true);
	}

	public final void maybeMerge()
		throws IOException
	{
		maybeMerge(-1);
	}

	private final void maybeMerge(int maxNumSegments)
		throws IOException
	{
		ensureOpen(false);
		updatePendingMerges(maxNumSegments);
		mergeScheduler.merge(this);
	}

	private synchronized void updatePendingMerges(int maxNumSegments)
		throws IOException
	{
		if (!$assertionsDisabled && maxNumSegments != -1 && maxNumSegments <= 0)
			throw new AssertionError();
		if (stopMerges)
			return;
		if (hitOOM)
			return;
		MergePolicy.MergeSpecification spec;
		if (maxNumSegments != -1)
		{
			spec = mergePolicy.findForcedMerges(segmentInfos, maxNumSegments, Collections.unmodifiableMap(segmentsToMerge));
			if (spec != null)
			{
				int numMerges = spec.merges.size();
				for (int i = 0; i < numMerges; i++)
				{
					MergePolicy.OneMerge merge = (MergePolicy.OneMerge)spec.merges.get(i);
					merge.maxNumSegments = maxNumSegments;
				}

			}
		} else
		{
			spec = mergePolicy.findMerges(segmentInfos);
		}
		if (spec != null)
		{
			int numMerges = spec.merges.size();
			for (int i = 0; i < numMerges; i++)
				registerMerge((MergePolicy.OneMerge)spec.merges.get(i));

		}
	}

	public synchronized Collection getMergingSegments()
	{
		return mergingSegments;
	}

	public synchronized MergePolicy.OneMerge getNextMerge()
	{
		if (pendingMerges.size() == 0)
		{
			return null;
		} else
		{
			MergePolicy.OneMerge merge = (MergePolicy.OneMerge)pendingMerges.removeFirst();
			runningMerges.add(merge);
			return merge;
		}
	}

	public void rollback()
		throws IOException
	{
		ensureOpen();
		synchronized (commitLock)
		{
			if (shouldClose())
				rollbackInternal();
		}
	}

	private void rollbackInternal()
		throws IOException
	{
		boolean success;
		success = false;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "rollback");
		synchronized (this)
		{
			finishMerges(false);
			stopMerges = true;
		}
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "rollback: done finish merges");
		mergePolicy.close();
		mergeScheduler.close();
		bufferedDeletesStream.clear();
		docWriter.close();
		docWriter.abort();
		synchronized (this)
		{
			if (pendingCommit != null)
			{
				pendingCommit.rollbackCommit(directory);
				deleter.decRef(pendingCommit);
				pendingCommit = null;
				notifyAll();
			}
			readerPool.dropAll(false);
			segmentInfos.rollbackSegmentInfos(rollbackSegments);
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("rollback: infos=").append(segString(segmentInfos)).toString());
			if (!$assertionsDisabled && !testPoint("rollback before checkpoint"))
				throw new AssertionError();
			deleter.checkpoint(segmentInfos, false);
			deleter.refresh();
			lastCommitChangeCount = changeCount;
		}
		success = true;
		synchronized (this)
		{
			if (!success)
			{
				closing = false;
				notifyAll();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception during rollback");
			}
		}
		break MISSING_BLOCK_LABEL_465;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "rollbackInternal");
		synchronized (this)
		{
			if (!success)
			{
				closing = false;
				notifyAll();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception during rollback");
			}
		}
		break MISSING_BLOCK_LABEL_465;
		Exception exception4;
		exception4;
		synchronized (this)
		{
			if (!success)
			{
				closing = false;
				notifyAll();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception during rollback");
			}
		}
		throw exception4;
		closeInternal(false, false);
		return;
	}

	public synchronized void deleteAll()
		throws IOException
	{
		boolean success;
		ensureOpen();
		success = false;
		finishMerges(false);
		docWriter.abort();
		segmentInfos.clear();
		deleter.checkpoint(segmentInfos, false);
		deleter.refresh();
		readerPool.dropAll(false);
		changeCount++;
		segmentInfos.changed();
		success = true;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during deleteAll");
		break MISSING_BLOCK_LABEL_173;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "deleteAll");
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during deleteAll");
		break MISSING_BLOCK_LABEL_173;
		Exception exception;
		exception;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during deleteAll");
		throw exception;
	}

	private synchronized void finishMerges(boolean waitForMerges)
	{
		if (!waitForMerges)
		{
			stopMerges = true;
			MergePolicy.OneMerge merge;
			for (Iterator i$ = pendingMerges.iterator(); i$.hasNext(); mergeFinish(merge))
			{
				merge = (MergePolicy.OneMerge)i$.next();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", (new StringBuilder()).append("now abort pending merge ").append(segString(merge.segments)).toString());
				merge.abort();
			}

			pendingMerges.clear();
			MergePolicy.OneMerge merge;
			for (Iterator i$ = runningMerges.iterator(); i$.hasNext(); merge.abort())
			{
				merge = (MergePolicy.OneMerge)i$.next();
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", (new StringBuilder()).append("now abort running merge ").append(segString(merge.segments)).toString());
			}

			for (; runningMerges.size() > 0; doWait())
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", (new StringBuilder()).append("now wait for ").append(runningMerges.size()).append(" running merge to abort").toString());

			stopMerges = false;
			notifyAll();
			if (!$assertionsDisabled && 0 != mergingSegments.size())
				throw new AssertionError();
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "all running merges have aborted");
		} else
		{
			waitForMerges();
		}
	}

	public synchronized void waitForMerges()
	{
		ensureOpen(false);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "waitForMerges");
		for (; pendingMerges.size() > 0 || runningMerges.size() > 0; doWait());
		if (!$assertionsDisabled && 0 != mergingSegments.size())
			throw new AssertionError();
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "waitForMerges done");
	}

	synchronized void checkpoint()
		throws IOException
	{
		changeCount++;
		segmentInfos.changed();
		deleter.checkpoint(segmentInfos, false);
	}

	SegmentInfoPerCommit prepareFlushedSegment(DocumentsWriterPerThread.FlushedSegment flushedSegment)
		throws IOException
	{
		SegmentInfoPerCommit newSegment;
		IOContext context;
		boolean success;
		if (!$assertionsDisabled && flushedSegment == null)
			throw new AssertionError();
		newSegment = flushedSegment.segmentInfo;
		setDiagnostics(newSegment.info, "flush");
		context = new IOContext(new FlushInfo(newSegment.info.getDocCount(), newSegment.info.sizeInBytes()));
		success = false;
		if (useCompoundFile(newSegment))
		{
			Collection oldFiles = createCompoundFile(infoStream, directory, MergeState.CheckAbort.NONE, newSegment.info, context);
			newSegment.info.setUseCompoundFile(true);
			synchronized (this)
			{
				deleter.deleteNewFiles(oldFiles);
			}
		}
		this.codec.segmentInfoFormat().getSegmentInfoWriter().write(directory, newSegment.info, flushedSegment.fieldInfos, context);
		if (flushedSegment.liveDocs != null)
		{
			int delCount = flushedSegment.delCount;
			if (!$assertionsDisabled && delCount <= 0)
				throw new AssertionError();
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("flush: write ").append(delCount).append(" deletes gen=").append(flushedSegment.segmentInfo.getDelGen()).toString());
			SegmentInfoPerCommit info = flushedSegment.segmentInfo;
			Codec codec = info.info.getCodec();
			codec.liveDocsFormat().writeLiveDocs(flushedSegment.liveDocs, directory, info, delCount, context);
			newSegment.setDelCount(delCount);
			newSegment.advanceDelGen();
		}
		success = true;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("hit exception reating compound file for newly flushed segment ").append(newSegment.info.name).toString());
			synchronized (this)
			{
				deleter.refresh(newSegment.info.name);
			}
		}
		break MISSING_BLOCK_LABEL_474;
		Exception exception2;
		exception2;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("hit exception reating compound file for newly flushed segment ").append(newSegment.info.name).toString());
			synchronized (this)
			{
				deleter.refresh(newSegment.info.name);
			}
		}
		throw exception2;
		return newSegment;
	}

	synchronized void publishFrozenDeletes(FrozenBufferedDeletes packet)
	{
		if (!$assertionsDisabled && (packet == null || !packet.any()))
			throw new AssertionError();
		synchronized (bufferedDeletesStream)
		{
			bufferedDeletesStream.push(packet);
		}
	}

	synchronized void publishFlushedSegment(SegmentInfoPerCommit newSegment, FrozenBufferedDeletes packet, FrozenBufferedDeletes globalPacket)
		throws IOException
	{
		synchronized (bufferedDeletesStream)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "publishFlushedSegment");
			if (globalPacket != null && globalPacket.any())
				bufferedDeletesStream.push(globalPacket);
			long nextGen;
			if (packet != null && packet.any())
				nextGen = bufferedDeletesStream.push(packet);
			else
				nextGen = bufferedDeletesStream.getNextGen();
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("publish sets newSegment delGen=").append(nextGen).append(" seg=").append(segString(newSegment)).toString());
			newSegment.setBufferedDeletesGen(nextGen);
			segmentInfos.add(newSegment);
			checkpoint();
		}
	}

	synchronized boolean useCompoundFile(SegmentInfoPerCommit segmentInfo)
		throws IOException
	{
		return mergePolicy.useCompoundFile(segmentInfos, segmentInfo);
	}

	private synchronized void resetMergeExceptions()
	{
		mergeExceptions = new ArrayList();
		mergeGen++;
	}

	private transient void noDupDirs(Directory dirs[])
	{
		HashSet dups = new HashSet();
		for (int i = 0; i < dirs.length; i++)
		{
			if (dups.contains(dirs[i]))
				throw new IllegalArgumentException((new StringBuilder()).append("Directory ").append(dirs[i]).append(" appears more than once").toString());
			if (dirs[i] == directory)
				throw new IllegalArgumentException("Cannot add directory to itself");
			dups.add(dirs[i]);
		}

	}

	public transient void addIndexes(Directory dirs[])
		throws IOException
	{
		ensureOpen();
		noDupDirs(dirs);
		List infos;
		boolean success;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "flush at addIndexes(Directory...)");
		flush(false, true);
		infos = new ArrayList();
		success = false;
		Directory arr$[] = dirs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Directory dir = arr$[i$];
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("addIndexes: process directory ").append(dir).toString());
			SegmentInfos sis = new SegmentInfos();
			sis.read(dir);
			Set dsFilesCopied = new HashSet();
			Map dsNames = new HashMap();
			Set copiedFiles = new HashSet();
			SegmentInfoPerCommit info;
			String newSegName;
			IOContext context;
			for (Iterator i$ = sis.iterator(); i$.hasNext(); infos.add(copySegmentAsIs(info, newSegName, dsNames, dsFilesCopied, context, copiedFiles)))
			{
				info = (SegmentInfoPerCommit)i$.next();
				if (!$assertionsDisabled && infos.contains(info))
					throw new AssertionError((new StringBuilder()).append("dup info dir=").append(info.info.dir).append(" name=").append(info.info.name).toString());
				newSegName = newSegmentName();
				String dsName = Lucene3xSegmentInfoFormat.getDocStoreSegment(info.info);
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", (new StringBuilder()).append("addIndexes: process segment origName=").append(info.info.name).append(" newName=").append(newSegName).append(" info=").append(info).toString());
				context = new IOContext(new MergeInfo(info.info.getDocCount(), info.info.sizeInBytes(), true, -1));
			}

		}

		success = true;
		if (!success)
		{
			for (Iterator i$ = infos.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit sipc = (SegmentInfoPerCommit)i$.next();
				Iterator i$ = sipc.files().iterator();
				while (i$.hasNext()) 
				{
					String file = (String)i$.next();
					try
					{
						directory.deleteFile(file);
					}
					catch (Throwable t) { }
				}
			}

		}
		break MISSING_BLOCK_LABEL_595;
		Exception exception;
		exception;
		if (!success)
		{
			for (Iterator i$ = infos.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit sipc = (SegmentInfoPerCommit)i$.next();
				Iterator i$ = sipc.files().iterator();
				while (i$.hasNext()) 
				{
					String file = (String)i$.next();
					try
					{
						directory.deleteFile(file);
					}
					catch (Throwable t) { }
				}
			}

		}
		throw exception;
		IndexWriter indexwriter = this;
		JVM INSTR monitorenter ;
		success = false;
		ensureOpen();
		success = true;
		if (!success)
		{
			for (Iterator i$ = infos.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit sipc = (SegmentInfoPerCommit)i$.next();
				Iterator i$ = sipc.files().iterator();
				while (i$.hasNext()) 
				{
					String file = (String)i$.next();
					try
					{
						directory.deleteFile(file);
					}
					catch (Throwable t) { }
				}
			}

		}
		break MISSING_BLOCK_LABEL_792;
		Exception exception1;
		exception1;
		if (!success)
		{
			for (Iterator i$ = infos.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit sipc = (SegmentInfoPerCommit)i$.next();
				Iterator i$ = sipc.files().iterator();
				while (i$.hasNext()) 
				{
					String file = (String)i$.next();
					try
					{
						directory.deleteFile(file);
					}
					catch (Throwable t) { }
				}
			}

		}
		throw exception1;
		segmentInfos.addAll(infos);
		checkpoint();
		break MISSING_BLOCK_LABEL_830;
		Exception exception2;
		exception2;
		throw exception2;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "addIndexes(Directory...)");
	}

	public transient void addIndexes(IndexReader readers[])
		throws IOException
	{
		int numDocs;
		ensureOpen();
		numDocs = 0;
		IOContext context;
		TrackingDirectoryWrapper trackingDir;
		SegmentInfo info;
		SegmentMerger merger;
		boolean success;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "flush at addIndexes(IndexReader...)");
		flush(false, true);
		String mergedName = newSegmentName();
		IndexReader arr$[] = readers;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			IndexReader indexReader = arr$[i$];
			numDocs += indexReader.numDocs();
		}

		context = new IOContext(new MergeInfo(numDocs, -1L, true, -1));
		trackingDir = new TrackingDirectoryWrapper(directory);
		info = new SegmentInfo(directory, Constants.LUCENE_MAIN_VERSION, mergedName, -1, false, codec, null, null);
		merger = new SegmentMerger(info, infoStream, trackingDir, config.getTermIndexInterval(), MergeState.CheckAbort.NONE, globalFieldNumberMap, context);
		IndexReader arr$[] = readers;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			IndexReader reader = arr$[i$];
			merger.add(reader);
		}

		success = false;
		MergeState mergeState;
		mergeState = merger.merge();
		success = true;
		if (!success)
			synchronized (this)
			{
				deleter.refresh(info.name);
			}
		break MISSING_BLOCK_LABEL_304;
		Exception exception1;
		exception1;
		if (!success)
			synchronized (this)
			{
				deleter.refresh(info.name);
			}
		throw exception1;
		SegmentInfoPerCommit infoPerCommit;
label0:
		{
			infoPerCommit = new SegmentInfoPerCommit(info, 0, -1L);
			info.setFiles(new HashSet(trackingDir.getCreatedFiles()));
			trackingDir.getCreatedFiles().clear();
			setDiagnostics(info, "addIndexes(IndexReader...)");
			synchronized (this)
			{
				if (!stopMerges)
					break label0;
				deleter.deleteNewFiles(infoPerCommit.files());
			}
			return;
		}
		boolean useCompoundFile;
		ensureOpen();
		useCompoundFile = mergePolicy.useCompoundFile(segmentInfos, infoPerCommit);
		indexwriter1;
		JVM INSTR monitorexit ;
		  goto _L1
		exception3;
		throw exception3;
_L1:
		Collection filesToDelete;
		if (!useCompoundFile)
			break MISSING_BLOCK_LABEL_516;
		filesToDelete = infoPerCommit.files();
		createCompoundFile(infoStream, directory, MergeState.CheckAbort.NONE, info, context);
		synchronized (this)
		{
			deleter.deleteNewFiles(filesToDelete);
		}
		break MISSING_BLOCK_LABEL_510;
		Exception exception5;
		exception5;
		synchronized (this)
		{
			deleter.deleteNewFiles(filesToDelete);
		}
		throw exception5;
		info.setUseCompoundFile(true);
		success = false;
		codec.segmentInfoFormat().getSegmentInfoWriter().write(trackingDir, info, mergeState.fieldInfos, context);
		success = true;
		if (!success)
			synchronized (this)
			{
				deleter.refresh(info.name);
			}
		break MISSING_BLOCK_LABEL_626;
		Exception exception8;
		exception8;
		if (!success)
			synchronized (this)
			{
				deleter.refresh(info.name);
			}
		throw exception8;
label1:
		{
			info.addFiles(trackingDir.getCreatedFiles());
			synchronized (this)
			{
				if (!stopMerges)
					break label1;
				deleter.deleteNewFiles(info.files());
			}
			return;
		}
		ensureOpen();
		segmentInfos.add(infoPerCommit);
		checkpoint();
		indexwriter3;
		JVM INSTR monitorexit ;
		break MISSING_BLOCK_LABEL_707;
		exception10;
		throw exception10;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "addIndexes(IndexReader...)");
	}

	private SegmentInfoPerCommit copySegmentAsIs(SegmentInfoPerCommit info, String segName, Map dsNames, Set dsFilesCopied, IOContext context, Set copiedFiles)
		throws IOException
	{
		String newDsName;
		Set docStoreFiles3xOnly;
		SegmentInfo newInfo;
		SegmentInfoPerCommit newInfoPerCommit;
		Collection siFiles;
		boolean success;
		String dsName = Lucene3xSegmentInfoFormat.getDocStoreSegment(info.info);
		if (!$assertionsDisabled && dsName == null)
			throw new AssertionError();
		if (dsNames.containsKey(dsName))
		{
			newDsName = (String)dsNames.get(dsName);
		} else
		{
			dsNames.put(dsName, segName);
			newDsName = segName;
		}
		FieldInfos fis = getFieldInfos(info.info);
		docStoreFiles3xOnly = Lucene3xCodec.getDocStoreFiles(info.info);
		Map attributes;
		if (info.info.attributes() == null)
			attributes = new HashMap();
		else
			attributes = new HashMap(info.info.attributes());
		if (docStoreFiles3xOnly != null)
			attributes.put(Lucene3xSegmentInfoFormat.DS_NAME_KEY, newDsName);
		newInfo = new SegmentInfo(directory, info.info.getVersion(), segName, info.info.getDocCount(), info.info.getUseCompoundFile(), info.info.getCodec(), info.info.getDiagnostics(), attributes);
		newInfoPerCommit = new SegmentInfoPerCommit(newInfo, info.getDelCount(), info.getDelGen());
		Set segFiles = new HashSet();
		String newFileName;
		for (Iterator i$ = info.files().iterator(); i$.hasNext(); segFiles.add(newFileName))
		{
			String file = (String)i$.next();
			if (docStoreFiles3xOnly != null && docStoreFiles3xOnly.contains(file))
				newFileName = (new StringBuilder()).append(newDsName).append(IndexFileNames.stripSegmentName(file)).toString();
			else
				newFileName = (new StringBuilder()).append(segName).append(IndexFileNames.stripSegmentName(file)).toString();
		}

		newInfo.setFiles(segFiles);
		TrackingDirectoryWrapper trackingDir = new TrackingDirectoryWrapper(directory);
		try
		{
			newInfo.getCodec().segmentInfoFormat().getSegmentInfoWriter().write(trackingDir, newInfo, fis, context);
		}
		catch (UnsupportedOperationException uoe) { }
		siFiles = trackingDir.getCreatedFiles();
		success = false;
		Iterator i$ = info.files().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			String file = (String)i$.next();
			String newFileName;
			if (docStoreFiles3xOnly != null && docStoreFiles3xOnly.contains(file))
			{
				newFileName = (new StringBuilder()).append(newDsName).append(IndexFileNames.stripSegmentName(file)).toString();
				if (dsFilesCopied.contains(newFileName))
					continue;
				dsFilesCopied.add(newFileName);
			} else
			{
				newFileName = (new StringBuilder()).append(segName).append(IndexFileNames.stripSegmentName(file)).toString();
			}
			if (!siFiles.contains(newFileName))
			{
				if (!$assertionsDisabled && directory.fileExists(newFileName))
					throw new AssertionError((new StringBuilder()).append("file \"").append(newFileName).append("\" already exists; siFiles=").append(siFiles).toString());
				if (!$assertionsDisabled && copiedFiles.contains(file))
					throw new AssertionError((new StringBuilder()).append("file \"").append(file).append("\" is being copied more than once").toString());
				copiedFiles.add(file);
				info.info.dir.copy(directory, file, newFileName, context);
			}
		} while (true);
		success = true;
		if (!success)
		{
			for (Iterator i$ = newInfo.files().iterator(); i$.hasNext();)
			{
				String file = (String)i$.next();
				try
				{
					directory.deleteFile(file);
				}
				catch (Throwable t) { }
			}

		}
		break MISSING_BLOCK_LABEL_803;
		Exception exception;
		exception;
		if (!success)
		{
			for (Iterator i$ = newInfo.files().iterator(); i$.hasNext();)
			{
				String file = (String)i$.next();
				try
				{
					directory.deleteFile(file);
				}
				catch (Throwable t) { }
			}

		}
		throw exception;
		return newInfoPerCommit;
	}

	protected void doAfterFlush()
		throws IOException
	{
	}

	protected void doBeforeFlush()
		throws IOException
	{
	}

	public final void prepareCommit()
		throws IOException
	{
		ensureOpen();
		prepareCommit(null);
	}

	public final void prepareCommit(Map commitUserData)
		throws IOException
	{
		ensureOpen(false);
		Object obj = commitLock;
		JVM INSTR monitorenter ;
		SegmentInfos toCommit;
		if (infoStream.isEnabled("IW"))
		{
			infoStream.message("IW", "prepareCommit: flush");
			infoStream.message("IW", (new StringBuilder()).append("  index before flush ").append(segString()).toString());
		}
		if (hitOOM)
			throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot commit");
		if (pendingCommit != null)
			throw new IllegalStateException("prepareCommit was already called with no corresponding call to commit");
		doBeforeFlush();
		if (!$assertionsDisabled && !testPoint("startDoFlush"))
			throw new AssertionError();
		toCommit = null;
		boolean anySegmentsFlushed = false;
		Object obj1 = fullFlushLock;
		JVM INSTR monitorenter ;
		boolean flushSuccess;
		boolean success;
		flushSuccess = false;
		success = false;
		anySegmentsFlushed = docWriter.flushAllThreads();
		if (!anySegmentsFlushed)
			flushCount.incrementAndGet();
		flushSuccess = true;
		synchronized (this)
		{
			maybeApplyDeletes(true);
			readerPool.commit(segmentInfos);
			toCommit = segmentInfos.clone();
			pendingCommitChangeCount = changeCount;
			filesToCommit = toCommit.files(directory, false);
			deleter.incRef(filesToCommit);
		}
		success = true;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during prepareCommit");
		docWriter.finishFullFlush(flushSuccess);
		doAfterFlush();
		break MISSING_BLOCK_LABEL_346;
		Exception exception1;
		exception1;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during prepareCommit");
		docWriter.finishFullFlush(flushSuccess);
		doAfterFlush();
		throw exception1;
		Exception exception2;
		exception2;
		obj1;
		JVM INSTR monitorexit ;
		throw exception2;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "prepareCommit");
		boolean success = false;
		if (anySegmentsFlushed)
			maybeMerge();
		success = true;
		if (!success)
			synchronized (this)
			{
				deleter.decRef(filesToCommit);
				filesToCommit = null;
			}
		break MISSING_BLOCK_LABEL_477;
		Exception exception4;
		exception4;
		if (!success)
			synchronized (this)
			{
				deleter.decRef(filesToCommit);
				filesToCommit = null;
			}
		throw exception4;
		startCommit(toCommit, commitUserData);
		break MISSING_BLOCK_LABEL_495;
		Exception exception6;
		exception6;
		throw exception6;
	}

	public final void commit()
		throws IOException
	{
		commit(null);
	}

	public final void commit(Map commitUserData)
		throws IOException
	{
		ensureOpen();
		commitInternal(commitUserData);
	}

	private final void commitInternal(Map commitUserData)
		throws IOException
	{
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "commit: start");
		synchronized (commitLock)
		{
			ensureOpen(false);
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "commit: enter lock");
			if (pendingCommit == null)
			{
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "commit: now prepare");
				prepareCommit(commitUserData);
			} else
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "commit: already prepared");
			finishCommit();
		}
	}

	private final synchronized void finishCommit()
		throws IOException
	{
		if (pendingCommit == null)
			break MISSING_BLOCK_LABEL_209;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "commit: pendingCommit != null");
		pendingCommit.finishCommit(directory);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("commit: wrote segments file \"").append(pendingCommit.getSegmentsFileName()).append("\"").toString());
		lastCommitChangeCount = pendingCommitChangeCount;
		segmentInfos.updateGeneration(pendingCommit);
		segmentInfos.setUserData(pendingCommit.getUserData());
		rollbackSegments = pendingCommit.createBackupSegmentInfos();
		deleter.checkpoint(pendingCommit, true);
		deleter.decRef(filesToCommit);
		filesToCommit = null;
		pendingCommit = null;
		notifyAll();
		break MISSING_BLOCK_LABEL_233;
		Exception exception;
		exception;
		deleter.decRef(filesToCommit);
		filesToCommit = null;
		pendingCommit = null;
		notifyAll();
		throw exception;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "commit: pendingCommit == null; skip");
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", "commit: done");
		return;
	}

	protected final void flush(boolean triggerMerge, boolean applyAllDeletes)
		throws IOException
	{
		ensureOpen(false);
		if (doFlush(applyAllDeletes) && triggerMerge)
			maybeMerge();
	}

	private boolean doFlush(boolean applyAllDeletes)
		throws IOException
	{
		boolean success;
		if (hitOOM)
			throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot flush");
		doBeforeFlush();
		if (!$assertionsDisabled && !testPoint("startDoFlush"))
			throw new AssertionError();
		success = false;
		if (infoStream.isEnabled("IW"))
		{
			infoStream.message("IW", (new StringBuilder()).append("  start flush: applyAllDeletes=").append(applyAllDeletes).toString());
			infoStream.message("IW", (new StringBuilder()).append("  index before flush ").append(segString()).toString());
		}
		Object obj = fullFlushLock;
		JVM INSTR monitorenter ;
		boolean flushSuccess = false;
		boolean anySegmentFlushed;
		anySegmentFlushed = docWriter.flushAllThreads();
		flushSuccess = true;
		docWriter.finishFullFlush(flushSuccess);
		break MISSING_BLOCK_LABEL_169;
		Exception exception;
		exception;
		docWriter.finishFullFlush(flushSuccess);
		throw exception;
		Exception exception1;
		exception1;
		obj;
		JVM INSTR monitorexit ;
		throw exception1;
		OutOfMemoryError oom;
		oom;
		boolean flag;
		handleOOM(oom, "doFlush");
		flag = false;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during flush");
		return flag;
		Exception exception3;
		exception3;
		if (!success && infoStream.isEnabled("IW"))
			infoStream.message("IW", "hit exception during flush");
		throw exception3;
	}

	final synchronized void maybeApplyDeletes(boolean applyAllDeletes)
		throws IOException
	{
		if (applyAllDeletes)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "apply all deletes during flush");
			applyAllDeletes();
		} else
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("don't apply deletes now delTermCount=").append(bufferedDeletesStream.numTerms()).append(" bytesUsed=").append(bufferedDeletesStream.bytesUsed()).toString());
	}

	final synchronized void applyAllDeletes()
		throws IOException
	{
		flushDeletesCount.incrementAndGet();
		BufferedDeletesStream.ApplyDeletesResult result = bufferedDeletesStream.applyDeletes(readerPool, segmentInfos.asList());
		if (result.anyDeletes)
			checkpoint();
		if (!keepFullyDeletedSegments && result.allDeleted != null)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("drop 100% deleted segments: ").append(segString(result.allDeleted)).toString());
			Iterator i$ = result.allDeleted.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
				if (!mergingSegments.contains(info))
				{
					segmentInfos.remove(info);
					readerPool.drop(info);
				}
			} while (true);
			checkpoint();
		}
		bufferedDeletesStream.prune(segmentInfos);
	}

	public final long ramSizeInBytes()
	{
		ensureOpen();
		return docWriter.flushControl.netBytes() + bufferedDeletesStream.bytesUsed();
	}

	DocumentsWriter getDocsWriter()
	{
		boolean test = false;
		if (!$assertionsDisabled && !(test = true))
			throw new AssertionError();
		else
			return test ? docWriter : null;
	}

	public final synchronized int numRamDocs()
	{
		ensureOpen();
		return docWriter.getNumDocs();
	}

	private synchronized void ensureValidMerge(MergePolicy.OneMerge merge)
	{
		for (Iterator i$ = merge.segments.iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			if (!segmentInfos.contains(info))
				throw new MergePolicy.MergeException((new StringBuilder()).append("MergePolicy selected a segment (").append(info.info.name).append(") that is not in the current index ").append(segString()).toString(), directory);
		}

	}

	private synchronized ReadersAndLiveDocs commitMergedDeletes(MergePolicy.OneMerge merge)
		throws IOException
	{
		if (!$assertionsDisabled && !testPoint("startCommitMergeDeletes"))
			throw new AssertionError();
		List sourceSegments = merge.segments;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("commitMergeDeletes ").append(segString(merge.segments)).toString());
		int docUpto = 0;
		long minGen = 0x7fffffffffffffffL;
		ReadersAndLiveDocs mergedDeletes = null;
label0:
		for (int i = 0; i < sourceSegments.size(); i++)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)sourceSegments.get(i);
			minGen = Math.min(info.getBufferedDeletesGen(), minGen);
			int docCount = info.info.getDocCount();
			Bits prevLiveDocs = ((SegmentReader)merge.readers.get(i)).getLiveDocs();
			ReadersAndLiveDocs rld = readerPool.get(info, false);
			if (!$assertionsDisabled && rld == null)
				throw new AssertionError((new StringBuilder()).append("seg=").append(info.info.name).toString());
			Bits currentLiveDocs = rld.getLiveDocs();
			if (prevLiveDocs != null)
			{
				if (!$assertionsDisabled && currentLiveDocs == null)
					throw new AssertionError();
				if (!$assertionsDisabled && prevLiveDocs.length() != docCount)
					throw new AssertionError();
				if (!$assertionsDisabled && currentLiveDocs.length() != docCount)
					throw new AssertionError();
				if (currentLiveDocs != prevLiveDocs)
				{
					int j = 0;
					do
					{
						if (j >= docCount)
							continue label0;
						if (!prevLiveDocs.get(j))
						{
							if (!$assertionsDisabled && currentLiveDocs.get(j))
								throw new AssertionError();
						} else
						{
							if (!currentLiveDocs.get(j))
							{
								if (mergedDeletes == null)
								{
									mergedDeletes = readerPool.get(merge.info, true);
									mergedDeletes.initWritableLiveDocs();
								}
								mergedDeletes.delete(docUpto);
							}
							docUpto++;
						}
						j++;
					} while (true);
				}
				docUpto += info.info.getDocCount() - info.getDelCount() - rld.getPendingDeleteCount();
				continue;
			}
			if (currentLiveDocs != null)
			{
				if (!$assertionsDisabled && currentLiveDocs.length() != docCount)
					throw new AssertionError();
				int j = 0;
				do
				{
					if (j >= docCount)
						continue label0;
					if (!currentLiveDocs.get(j))
					{
						if (mergedDeletes == null)
						{
							mergedDeletes = readerPool.get(merge.info, true);
							mergedDeletes.initWritableLiveDocs();
						}
						mergedDeletes.delete(docUpto);
					}
					docUpto++;
					j++;
				} while (true);
			}
			docUpto += info.info.getDocCount();
		}

		if (!$assertionsDisabled && docUpto != merge.info.info.getDocCount())
			throw new AssertionError();
		if (infoStream.isEnabled("IW"))
			if (mergedDeletes == null)
				infoStream.message("IW", "no new deletes since merge started");
			else
				infoStream.message("IW", (new StringBuilder()).append(mergedDeletes.getPendingDeleteCount()).append(" new deletes since merge started").toString());
		if (!$assertionsDisabled && mergedDeletes != null && minGen <= merge.info.getBufferedDeletesGen())
		{
			throw new AssertionError();
		} else
		{
			merge.info.setBufferedDeletesGen(minGen);
			return mergedDeletes;
		}
	}

	private synchronized boolean commitMerge(MergePolicy.OneMerge merge)
		throws IOException
	{
		boolean dropSegment;
		boolean success;
		if (!$assertionsDisabled && !testPoint("startCommitMerge"))
			throw new AssertionError();
		if (hitOOM)
			throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot complete merge");
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("commitMerge: ").append(segString(merge.segments)).append(" index=").append(segString()).toString());
		if (!$assertionsDisabled && !merge.registerDone)
			throw new AssertionError();
		if (merge.isAborted())
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "commitMerge: skip: it was aborted");
			deleter.deleteNewFiles(merge.info.files());
			return false;
		}
		ReadersAndLiveDocs mergedDeletes = merge.info.info.getDocCount() != 0 ? commitMergedDeletes(merge) : null;
		if (!$assertionsDisabled && mergedDeletes != null && mergedDeletes.getPendingDeleteCount() == 0)
			throw new AssertionError();
		if (!$assertionsDisabled && segmentInfos.contains(merge.info))
			throw new AssertionError();
		boolean allDeleted = merge.segments.size() == 0 || merge.info.info.getDocCount() == 0 || mergedDeletes != null && mergedDeletes.getPendingDeleteCount() == merge.info.info.getDocCount();
		if (infoStream.isEnabled("IW") && allDeleted)
			infoStream.message("IW", (new StringBuilder()).append("merged segment ").append(merge.info).append(" is 100% deleted").append(keepFullyDeletedSegments ? "" : "; skipping insert").toString());
		dropSegment = allDeleted && !keepFullyDeletedSegments;
		if (!$assertionsDisabled && merge.segments.size() <= 0 && !dropSegment)
			throw new AssertionError();
		if (!$assertionsDisabled && merge.info.info.getDocCount() == 0 && !keepFullyDeletedSegments && !dropSegment)
			throw new AssertionError();
		segmentInfos.applyMergeChanges(merge, dropSegment);
		if (mergedDeletes != null)
		{
			if (dropSegment)
				mergedDeletes.dropChanges();
			readerPool.release(mergedDeletes);
			if (dropSegment)
				readerPool.drop(mergedDeletes.info);
		}
		if (dropSegment)
		{
			if (!$assertionsDisabled && segmentInfos.contains(merge.info))
				throw new AssertionError();
			deleter.deleteNewFiles(merge.info.files());
		}
		success = false;
		closeMergeReaders(merge, false);
		success = true;
		if (success)
			checkpoint();
		else
			try
			{
				checkpoint();
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_615;
		Exception exception;
		exception;
		if (success)
			checkpoint();
		else
			try
			{
				checkpoint();
			}
			catch (Throwable t) { }
		throw exception;
		deleter.deletePendingFiles();
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("after commitMerge: ").append(segString()).toString());
		if (merge.maxNumSegments != -1 && !dropSegment && !segmentsToMerge.containsKey(merge.info))
			segmentsToMerge.put(merge.info, Boolean.FALSE);
		return true;
	}

	private final void handleMergeException(Throwable t, MergePolicy.OneMerge merge)
		throws IOException
	{
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("handleMergeException: merge=").append(segString(merge.segments)).append(" exc=").append(t).toString());
		merge.setException(t);
		addMergeException(merge);
		if (t instanceof MergePolicy.MergeAbortedException)
			if (merge.isExternal)
				throw (MergePolicy.MergeAbortedException)t;
			else
				return;
		if (t instanceof IOException)
			throw (IOException)t;
		if (t instanceof RuntimeException)
			throw (RuntimeException)t;
		if (t instanceof Error)
			throw (Error)t;
		else
			throw new RuntimeException(t);
	}

	public void merge(MergePolicy.OneMerge merge)
		throws IOException
	{
		boolean success;
		long t0;
		success = false;
		t0 = System.currentTimeMillis();
		try
		{
			mergeInit(merge);
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("now merge\n  merge=").append(segString(merge.segments)).append("\n  index=").append(segString()).toString());
			mergeMiddle(merge);
			mergeSuccess(merge);
			success = true;
		}
		catch (Throwable t)
		{
			handleMergeException(t, merge);
		}
		synchronized (this)
		{
			mergeFinish(merge);
			if (!success)
			{
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception during merge");
				if (merge.info != null && !segmentInfos.contains(merge.info))
					deleter.refresh(merge.info.info.name);
			}
			if (success && !merge.isAborted() && (merge.maxNumSegments != -1 || !closed && !closing))
				updatePendingMerges(merge.maxNumSegments);
		}
		break MISSING_BLOCK_LABEL_381;
		Exception exception1;
		exception1;
		synchronized (this)
		{
			mergeFinish(merge);
			if (!success)
			{
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception during merge");
				if (merge.info != null && !segmentInfos.contains(merge.info))
					deleter.refresh(merge.info.info.name);
			}
			if (success && !merge.isAborted() && (merge.maxNumSegments != -1 || !closed && !closing))
				updatePendingMerges(merge.maxNumSegments);
		}
		throw exception1;
		OutOfMemoryError oom;
		oom;
		handleOOM(oom, "merge");
		if (merge.info != null && !merge.isAborted() && infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("merge time ").append(System.currentTimeMillis() - t0).append(" msec for ").append(merge.info.info.getDocCount()).append(" docs").toString());
		return;
	}

	void mergeSuccess(MergePolicy.OneMerge onemerge)
	{
	}

	final synchronized boolean registerMerge(MergePolicy.OneMerge merge)
		throws IOException
	{
		if (merge.registerDone)
			return true;
		if (!$assertionsDisabled && merge.segments.size() <= 0)
			throw new AssertionError();
		if (stopMerges)
		{
			merge.abort();
			throw new MergePolicy.MergeAbortedException((new StringBuilder()).append("merge is aborted: ").append(segString(merge.segments)).toString());
		}
		boolean isExternal = false;
		Iterator i$ = merge.segments.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			if (mergingSegments.contains(info))
				return false;
			if (!segmentInfos.contains(info))
				return false;
			if (info.info.dir != directory)
				isExternal = true;
			if (segmentsToMerge.containsKey(info))
				merge.maxNumSegments = mergeMaxNumSegments;
		} while (true);
		ensureValidMerge(merge);
		pendingMerges.add(merge);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("add merge to pendingMerges: ").append(segString(merge.segments)).append(" [total ").append(pendingMerges.size()).append(" pending]").toString());
		merge.mergeGen = mergeGen;
		merge.isExternal = isExternal;
		if (infoStream.isEnabled("IW"))
		{
			StringBuilder builder = new StringBuilder("registerMerge merging= [");
			SegmentInfoPerCommit info;
			for (Iterator i$ = mergingSegments.iterator(); i$.hasNext(); builder.append(info.info.name).append(", "))
				info = (SegmentInfoPerCommit)i$.next();

			builder.append("]");
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", builder.toString());
		}
		SegmentInfoPerCommit info;
		for (builder = merge.segments.iterator(); builder.hasNext(); mergingSegments.add(info))
		{
			info = (SegmentInfoPerCommit)builder.next();
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("registerMerge info=").append(segString(info)).toString());
		}

		merge.registerDone = true;
		return true;
	}

	final synchronized void mergeInit(MergePolicy.OneMerge merge)
		throws IOException
	{
		boolean success = false;
		_mergeInit(merge);
		success = true;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception in mergeInit");
			mergeFinish(merge);
		}
		break MISSING_BLOCK_LABEL_81;
		Exception exception;
		exception;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception in mergeInit");
			mergeFinish(merge);
		}
		throw exception;
	}

	private synchronized void _mergeInit(MergePolicy.OneMerge merge)
		throws IOException
	{
		if (!$assertionsDisabled && !testPoint("startMergeInit"))
			throw new AssertionError();
		if (!$assertionsDisabled && !merge.registerDone)
			throw new AssertionError();
		if (!$assertionsDisabled && merge.maxNumSegments != -1 && merge.maxNumSegments <= 0)
			throw new AssertionError();
		if (hitOOM)
			throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot merge");
		if (merge.info != null)
			return;
		if (merge.isAborted())
			return;
		BufferedDeletesStream.ApplyDeletesResult result = bufferedDeletesStream.applyDeletes(readerPool, merge.segments);
		if (result.anyDeletes)
			checkpoint();
		if (!keepFullyDeletedSegments && result.allDeleted != null)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", (new StringBuilder()).append("drop 100% deleted segments: ").append(result.allDeleted).toString());
			SegmentInfoPerCommit info;
			for (Iterator i$ = result.allDeleted.iterator(); i$.hasNext(); readerPool.drop(info))
			{
				info = (SegmentInfoPerCommit)i$.next();
				segmentInfos.remove(info);
				if (merge.segments.contains(info))
				{
					mergingSegments.remove(info);
					merge.segments.remove(info);
				}
			}

			checkpoint();
		}
		String mergeSegmentName = newSegmentName();
		SegmentInfo si = new SegmentInfo(directory, Constants.LUCENE_MAIN_VERSION, mergeSegmentName, -1, false, codec, null, null);
		merge.info = new SegmentInfoPerCommit(si, 0, -1L);
		bufferedDeletesStream.prune(segmentInfos);
		Map details = new HashMap();
		details.put("mergeMaxNumSegments", (new StringBuilder()).append("").append(merge.maxNumSegments).toString());
		details.put("mergeFactor", Integer.toString(merge.segments.size()));
		setDiagnostics(si, "merge", details);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("merge seg=").append(merge.info.info.name).append(" ").append(segString(merge.segments)).toString());
		if (!$assertionsDisabled && merge.estimatedMergeBytes != 0L)
			throw new AssertionError();
		Iterator i$ = merge.segments.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			if (info.info.getDocCount() > 0)
			{
				int delCount = numDeletedDocs(info);
				if (!$assertionsDisabled && delCount > info.info.getDocCount())
					throw new AssertionError();
				double delRatio = (double)delCount / (double)info.info.getDocCount();
				merge.estimatedMergeBytes += (double)info.info.sizeInBytes() * (1.0D - delRatio);
			}
		} while (true);
	}

	static void setDiagnostics(SegmentInfo info, String source)
	{
		setDiagnostics(info, source, null);
	}

	private static void setDiagnostics(SegmentInfo info, String source, Map details)
	{
		Map diagnostics = new HashMap();
		diagnostics.put("source", source);
		diagnostics.put("lucene.version", Constants.LUCENE_VERSION);
		diagnostics.put("os", Constants.OS_NAME);
		diagnostics.put("os.arch", Constants.OS_ARCH);
		diagnostics.put("os.version", Constants.OS_VERSION);
		diagnostics.put("java.version", Constants.JAVA_VERSION);
		diagnostics.put("java.vendor", Constants.JAVA_VENDOR);
		if (details != null)
			diagnostics.putAll(details);
		info.setDiagnostics(diagnostics);
	}

	final synchronized void mergeFinish(MergePolicy.OneMerge merge)
	{
		notifyAll();
		if (merge.registerDone)
		{
			List sourceSegments = merge.segments;
			SegmentInfoPerCommit info;
			for (Iterator i$ = sourceSegments.iterator(); i$.hasNext(); mergingSegments.remove(info))
				info = (SegmentInfoPerCommit)i$.next();

			merge.registerDone = false;
		}
		runningMerges.remove(merge);
	}

	private final synchronized void closeMergeReaders(MergePolicy.OneMerge merge, boolean suppressExceptions)
		throws IOException
	{
		int numSegments = merge.readers.size();
		Throwable th = null;
		boolean drop = !suppressExceptions;
		for (int i = 0; i < numSegments; i++)
		{
			SegmentReader sr = (SegmentReader)merge.readers.get(i);
			if (sr == null)
				continue;
			try
			{
				ReadersAndLiveDocs rld = readerPool.get(sr.getSegmentInfo(), false);
				if (!$assertionsDisabled && rld == null)
					throw new AssertionError();
				if (drop)
					rld.dropChanges();
				rld.release(sr);
				readerPool.release(rld);
				if (drop)
					readerPool.drop(rld.info);
			}
			catch (Throwable t)
			{
				if (th == null)
					th = t;
			}
			merge.readers.set(i, null);
		}

		if (!suppressExceptions && th != null)
		{
			if (th instanceof IOException)
				throw (IOException)th;
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	private int mergeMiddle(MergePolicy.OneMerge merge)
		throws IOException
	{
		String mergedName;
		List sourceSegments;
		IOContext context;
		MergeState.CheckAbort checkAbort;
		TrackingDirectoryWrapper dirWrapper;
		SegmentMerger merger;
		boolean success;
		merge.checkAborted(directory);
		mergedName = merge.info.info.name;
		sourceSegments = merge.segments;
		context = new IOContext(merge.getMergeInfo());
		checkAbort = new MergeState.CheckAbort(merge, directory);
		dirWrapper = new TrackingDirectoryWrapper(directory);
		merger = new SegmentMerger(merge.info.info, infoStream, dirWrapper, config.getTermIndexInterval(), checkAbort, globalFieldNumberMap, context);
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("merging ").append(segString(merge.segments)).toString());
		merge.readers = new ArrayList();
		success = false;
		int segUpto = 0;
_L5:
		if (segUpto >= sourceSegments.size()) goto _L2; else goto _L1
_L1:
		SegmentInfoPerCommit info;
		ReadersAndLiveDocs rld;
		SegmentReader reader;
		Bits liveDocs;
		int delCount;
		info = (SegmentInfoPerCommit)sourceSegments.get(segUpto);
		rld = readerPool.get(info, true);
		reader = rld.getMergeReader(context);
		if (!$assertionsDisabled && reader == null)
			throw new AssertionError();
		synchronized (this)
		{
			liveDocs = rld.getReadOnlyLiveDocs();
			delCount = rld.getPendingDeleteCount() + info.getDelCount();
			if (!$assertionsDisabled && !rld.verifyDocCounts())
				throw new AssertionError();
			if (infoStream.isEnabled("IW"))
				if (rld.getPendingDeleteCount() != 0)
					infoStream.message("IW", (new StringBuilder()).append("seg=").append(segString(info)).append(" delCount=").append(info.getDelCount()).append(" pendingDelCount=").append(rld.getPendingDeleteCount()).toString());
				else
				if (info.getDelCount() != 0)
					infoStream.message("IW", (new StringBuilder()).append("seg=").append(segString(info)).append(" delCount=").append(info.getDelCount()).toString());
				else
					infoStream.message("IW", (new StringBuilder()).append("seg=").append(segString(info)).append(" no deletes").toString());
		}
		if (reader.numDeletedDocs() == delCount) goto _L4; else goto _L3
_L3:
		SegmentReader newReader;
		boolean released;
		if (!$assertionsDisabled && delCount <= reader.numDeletedDocs())
			throw new AssertionError();
		newReader = new SegmentReader(info, reader.core, liveDocs, info.info.getDocCount() - delCount);
		released = false;
		rld.release(reader);
		released = true;
		break MISSING_BLOCK_LABEL_551;
		exception1;
		if (!released)
			newReader.decRef();
		throw exception1;
		Exception exception1;
		if (!released)
			newReader.decRef();
		reader = newReader;
_L4:
		merge.readers.add(reader);
		if (!$assertionsDisabled && delCount > info.info.getDocCount())
			throw new AssertionError((new StringBuilder()).append("delCount=").append(delCount).append(" info.docCount=").append(info.info.getDocCount()).append(" rld.pendingDeleteCount=").append(rld.getPendingDeleteCount()).append(" info.getDelCount()=").append(info.getDelCount()).toString());
		if (delCount < info.info.getDocCount())
			merger.add(reader);
		segUpto++;
		  goto _L5
_L2:
		boolean success3;
		merge.checkAborted(directory);
		success3 = false;
		MergeState mergeState;
		mergeState = merger.merge();
		success3 = true;
		if (!success3)
			synchronized (this)
			{
				deleter.refresh(merge.info.info.name);
			}
		break MISSING_BLOCK_LABEL_825;
		Exception exception3;
		exception3;
		if (!success3)
			synchronized (this)
			{
				deleter.refresh(merge.info.info.name);
			}
		throw exception3;
		Collection filesToRemove;
		if (!$assertionsDisabled && mergeState.segmentInfo != merge.info.info)
			throw new AssertionError();
		merge.info.info.setFiles(new HashSet(dirWrapper.getCreatedFiles()));
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("merge codec=").append(codec).append(" docCount=").append(merge.info.info.getDocCount()).append("; merged segment has ").append(mergeState.fieldInfos.hasVectors() ? "vectors" : "no vectors").append("; ").append(mergeState.fieldInfos.hasNorms() ? "norms" : "no norms").append("; ").append(mergeState.fieldInfos.hasDocValues() ? "docValues" : "no docValues").append("; ").append(mergeState.fieldInfos.hasProx() ? "prox" : "no prox").append("; ").append(mergeState.fieldInfos.hasProx() ? "freqs" : "no freqs").toString());
		boolean useCompoundFile;
		synchronized (this)
		{
			useCompoundFile = mergePolicy.useCompoundFile(segmentInfos, merge.info);
		}
		if (!useCompoundFile)
			break MISSING_BLOCK_LABEL_1724;
		success = false;
		filesToRemove = merge.info.files();
		filesToRemove = createCompoundFile(infoStream, directory, checkAbort, merge.info.info, context);
		success = true;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception creating compound file during merge");
			synchronized (this)
			{
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfs"));
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfe"));
				deleter.deleteNewFiles(merge.info.files());
			}
		}
		break MISSING_BLOCK_LABEL_1597;
		IOException ioe;
		ioe;
		synchronized (this)
		{
			if (!merge.isAborted())
				handleMergeException(ioe, merge);
		}
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception creating compound file during merge");
			synchronized (this)
			{
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfs"));
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfe"));
				deleter.deleteNewFiles(merge.info.files());
			}
		}
		break MISSING_BLOCK_LABEL_1597;
		Throwable t;
		t;
		handleMergeException(t, merge);
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception creating compound file during merge");
			synchronized (this)
			{
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfs"));
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfe"));
				deleter.deleteNewFiles(merge.info.files());
			}
		}
		break MISSING_BLOCK_LABEL_1597;
		Exception exception10;
		exception10;
		if (!success)
		{
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "hit exception creating compound file during merge");
			synchronized (this)
			{
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfs"));
				deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfe"));
				deleter.deleteNewFiles(merge.info.files());
			}
		}
		throw exception10;
		success = false;
		int i;
		synchronized (this)
		{
			deleter.deleteNewFiles(filesToRemove);
			if (!merge.isAborted())
				break MISSING_BLOCK_LABEL_1696;
			if (infoStream.isEnabled("IW"))
				infoStream.message("IW", "abort merge after building CFS");
			deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfs"));
			deleter.deleteFile(IndexFileNames.segmentFileName(mergedName, "", "cfe"));
			i = 0;
		}
		if (!success)
			closeMergeReaders(merge, true);
		return i;
		indexwriter5;
		JVM INSTR monitorexit ;
		merge.info.info.setUseCompoundFile(true);
		break MISSING_BLOCK_LABEL_1727;
		success = false;
		boolean success2 = false;
		codec.segmentInfoFormat().getSegmentInfoWriter().write(directory, merge.info.info, mergeState.fieldInfos, context);
		success2 = true;
		if (!success2)
			synchronized (this)
			{
				deleter.deleteNewFiles(merge.info.files());
			}
		break MISSING_BLOCK_LABEL_1848;
		Exception exception14;
		exception14;
		if (!success2)
			synchronized (this)
			{
				deleter.deleteNewFiles(merge.info.files());
			}
		throw exception14;
		IndexReaderWarmer mergedSegmentWarmer;
		ReadersAndLiveDocs rld;
		SegmentReader sr;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", String.format(Locale.ROOT, "merged segment size=%.3f MB vs estimate=%.3f MB", new Object[] {
				Double.valueOf((double)merge.info.info.sizeInBytes() / 1024D / 1024D), Double.valueOf((double)(merge.estimatedMergeBytes / 1024L) / 1024D)
			}));
		mergedSegmentWarmer = config.getMergedSegmentWarmer();
		if (!poolReaders || mergedSegmentWarmer == null)
			break MISSING_BLOCK_LABEL_2056;
		rld = readerPool.get(merge.info, true);
		sr = rld.getReader(IOContext.READ);
		mergedSegmentWarmer.warm(sr);
		synchronized (this)
		{
			rld.release(sr);
			readerPool.release(rld);
		}
		break MISSING_BLOCK_LABEL_2056;
		Exception exception17;
		exception17;
		synchronized (this)
		{
			rld.release(sr);
			readerPool.release(rld);
		}
		throw exception17;
		int j;
		if (commitMerge(merge))
			break MISSING_BLOCK_LABEL_2081;
		j = 0;
		if (!success)
			closeMergeReaders(merge, true);
		return j;
		success = true;
		if (!success)
			closeMergeReaders(merge, true);
		break MISSING_BLOCK_LABEL_2114;
		Exception exception19;
		exception19;
		if (!success)
			closeMergeReaders(merge, true);
		throw exception19;
		return merge.info.info.getDocCount();
	}

	synchronized void addMergeException(MergePolicy.OneMerge merge)
	{
		if (!$assertionsDisabled && merge.getException() == null)
			throw new AssertionError();
		if (!mergeExceptions.contains(merge) && mergeGen == merge.mergeGen)
			mergeExceptions.add(merge);
	}

	final int getBufferedDeleteTermsSize()
	{
		return docWriter.getBufferedDeleteTermsSize();
	}

	final int getNumBufferedDeleteTerms()
	{
		return docWriter.getNumBufferedDeleteTerms();
	}

	synchronized SegmentInfoPerCommit newestSegment()
	{
		return segmentInfos.size() <= 0 ? null : segmentInfos.info(segmentInfos.size() - 1);
	}

	public synchronized String segString()
	{
		return segString(((Iterable) (segmentInfos)));
	}

	public synchronized String segString(Iterable infos)
	{
		StringBuilder buffer = new StringBuilder();
		SegmentInfoPerCommit info;
		for (Iterator i$ = infos.iterator(); i$.hasNext(); buffer.append(segString(info)))
		{
			info = (SegmentInfoPerCommit)i$.next();
			if (buffer.length() > 0)
				buffer.append(' ');
		}

		return buffer.toString();
	}

	public synchronized String segString(SegmentInfoPerCommit info)
	{
		return info.toString(info.info.dir, numDeletedDocs(info) - info.getDelCount());
	}

	private synchronized void doWait()
	{
		try
		{
			wait(1000L);
		}
		catch (InterruptedException ie)
		{
			throw new ThreadInterruptedException(ie);
		}
	}

	void keepFullyDeletedSegments()
	{
		keepFullyDeletedSegments = true;
	}

	boolean getKeepFullyDeletedSegments()
	{
		return keepFullyDeletedSegments;
	}

	private boolean filesExist(SegmentInfos toSync)
		throws IOException
	{
		Collection files = toSync.files(directory, false);
		for (Iterator i$ = files.iterator(); i$.hasNext();)
		{
			String fileName = (String)i$.next();
			if (!$assertionsDisabled && !directory.fileExists(fileName))
				throw new AssertionError((new StringBuilder()).append("file ").append(fileName).append(" does not exist").toString());
			if (!$assertionsDisabled && !deleter.exists(fileName))
				throw new AssertionError((new StringBuilder()).append("IndexFileDeleter doesn't know about file ").append(fileName).toString());
		}

		return true;
	}

	synchronized SegmentInfos toLiveInfos(SegmentInfos sis)
	{
		SegmentInfos newSIS = new SegmentInfos();
		Map liveSIS = new HashMap();
		SegmentInfoPerCommit info;
		for (Iterator i$ = segmentInfos.iterator(); i$.hasNext(); liveSIS.put(info, info))
			info = (SegmentInfoPerCommit)i$.next();

		SegmentInfoPerCommit info;
		for (Iterator i$ = sis.iterator(); i$.hasNext(); newSIS.add(info))
		{
			info = (SegmentInfoPerCommit)i$.next();
			SegmentInfoPerCommit liveInfo = (SegmentInfoPerCommit)liveSIS.get(info);
			if (liveInfo != null)
				info = liveInfo;
		}

		return newSIS;
	}

	private void startCommit(SegmentInfos toSync, Map commitUserData)
		throws IOException
	{
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("startCommit index=").append(segString(toLiveInfos(toSync))).append(" changeCount=").append(changeCount).toString());
		if (!$assertionsDisabled && !filesExist(toSync))
			throw new AssertionError();
		if (commitUserData != null)
			toSync.setUserData(commitUserData);
		indexwriter;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		if (!$assertionsDisabled && !testPoint("midStartCommit"))
			throw new AssertionError();
		pendingCommitSet = false;
		if (!$assertionsDisabled && !testPoint("midStartCommit2"))
			throw new AssertionError();
		synchronized (this)
		{
			if (!$assertionsDisabled && pendingCommit != null)
				throw new AssertionError();
			if (!$assertionsDisabled && segmentInfos.getGeneration() != toSync.getGeneration())
				throw new AssertionError();
			toSync.prepareCommit(directory);
			pendingCommitSet = true;
			pendingCommit = toSync;
		}
		success = false;
		filesToSync = toSync.files(directory, false);
		directory.sync(filesToSync);
		success = true;
		if (!success)
		{
			pendingCommitSet = false;
			pendingCommit = null;
			toSync.rollbackCommit(directory);
		}
		break MISSING_BLOCK_LABEL_521;
		exception2;
		if (!success)
		{
			pendingCommitSet = false;
			pendingCommit = null;
			toSync.rollbackCommit(directory);
		}
		throw exception2;
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("done all syncs: ").append(filesToSync).toString());
		if (!$assertionsDisabled && !testPoint("midStartCommitSuccess"))
			throw new AssertionError();
		synchronized (this)
		{
			segmentInfos.updateGeneration(toSync);
			if (!pendingCommitSet)
			{
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception committing segments file");
				deleter.decRef(filesToCommit);
				filesToCommit = null;
			}
		}
		break MISSING_BLOCK_LABEL_749;
		exception4;
		synchronized (this)
		{
			segmentInfos.updateGeneration(toSync);
			if (!pendingCommitSet)
			{
				if (infoStream.isEnabled("IW"))
					infoStream.message("IW", "hit exception committing segments file");
				deleter.decRef(filesToCommit);
				filesToCommit = null;
			}
		}
		throw exception4;
		{
			if (!$assertionsDisabled && !testPoint("startStartCommit"))
				throw new AssertionError();
			if (!$assertionsDisabled && pendingCommit != null)
				throw new AssertionError();
			if (hitOOM)
				throw new IllegalStateException("this writer hit an OutOfMemoryError; cannot commit");
			boolean pendingCommitSet;
			boolean success;
			Collection filesToSync;
			Exception exception2;
			Exception exception4;
			try
			{
label0:
				{
					if (infoStream.isEnabled("IW"))
						infoStream.message("IW", "startCommit(): start");
					synchronized (this)
					{
						if (!$assertionsDisabled && lastCommitChangeCount > changeCount)
							throw new AssertionError((new StringBuilder()).append("lastCommitChangeCount=").append(lastCommitChangeCount).append(" changeCount=").append(changeCount).toString());
						if (pendingCommitChangeCount != lastCommitChangeCount)
							break label0;
						if (infoStream.isEnabled("IW"))
							infoStream.message("IW", "  skip startCommit(): no changes pending");
						deleter.decRef(filesToCommit);
						filesToCommit = null;
					}
					return;
				}
			}
			catch (OutOfMemoryError oom)
			{
				handleOOM(oom, "startCommit");
			}
		}
		if (!$assertionsDisabled && !testPoint("finishStartCommit"))
			throw new AssertionError();
		else
			return;
	}

	public static boolean isLocked(Directory directory)
		throws IOException
	{
		return directory.makeLock("write.lock").isLocked();
	}

	public static void unlock(Directory directory)
		throws IOException
	{
		directory.makeLock("write.lock").release();
	}

	private void handleOOM(OutOfMemoryError oom, String location)
	{
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("hit OutOfMemoryError inside ").append(location).toString());
		hitOOM = true;
		throw oom;
	}

	boolean testPoint(String name)
	{
		return true;
	}

	synchronized boolean nrtIsCurrent(SegmentInfos infos)
	{
		ensureOpen();
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("nrtIsCurrent: infoVersion matches: ").append(infos.version == segmentInfos.version).append(" DW changes: ").append(docWriter.anyChanges()).append(" BD changes: ").append(bufferedDeletesStream.any()).toString());
		return infos.version == segmentInfos.version && !docWriter.anyChanges() && !bufferedDeletesStream.any();
	}

	synchronized boolean isClosed()
	{
		return closed;
	}

	public synchronized void deleteUnusedFiles()
		throws IOException
	{
		ensureOpen(false);
		deleter.deletePendingFiles();
		deleter.revisitPolicy();
	}

	synchronized void deletePendingFiles()
		throws IOException
	{
		deleter.deletePendingFiles();
	}

	static final Collection createCompoundFile(InfoStream infoStream, Directory directory, MergeState.CheckAbort checkAbort, SegmentInfo info, IOContext context)
		throws IOException
	{
		String fileName;
		Collection files;
		CompoundFileDirectory cfsDir;
		IOException prior;
		fileName = IndexFileNames.segmentFileName(info.name, "", "cfs");
		if (infoStream.isEnabled("IW"))
			infoStream.message("IW", (new StringBuilder()).append("create compound file ").append(fileName).toString());
		if (!$assertionsDisabled && Lucene3xSegmentInfoFormat.getDocStoreOffset(info) != -1)
			throw new AssertionError();
		files = info.files();
		cfsDir = new CompoundFileDirectory(directory, fileName, context, true);
		prior = null;
		String file;
		for (Iterator i$ = files.iterator(); i$.hasNext(); checkAbort.work(directory.fileLength(file)))
		{
			file = (String)i$.next();
			directory.copy(cfsDir, file, file, context);
		}

		boolean success = false;
		IOUtils.closeWhileHandlingException(prior, new Closeable[] {
			cfsDir
		});
		success = true;
		if (!success)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		break MISSING_BLOCK_LABEL_475;
		Exception exception;
		exception;
		if (!success)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		throw exception;
		IOException ex;
		ex;
		prior = ex;
		ex = 0;
		IOUtils.closeWhileHandlingException(prior, new Closeable[] {
			cfsDir
		});
		ex = 1;
		if (!ex)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		break MISSING_BLOCK_LABEL_475;
		Exception exception1;
		exception1;
		if (!ex)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		throw exception1;
		Exception exception2;
		exception2;
		boolean success = false;
		IOUtils.closeWhileHandlingException(prior, new Closeable[] {
			cfsDir
		});
		success = true;
		if (!success)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		break MISSING_BLOCK_LABEL_472;
		Exception exception3;
		exception3;
		if (!success)
		{
			try
			{
				directory.deleteFile(fileName);
			}
			catch (Throwable t) { }
			try
			{
				directory.deleteFile(IndexFileNames.segmentFileName(info.name, "", "cfe"));
			}
			catch (Throwable t) { }
		}
		throw exception3;
		throw exception2;
		Set siFiles = new HashSet();
		siFiles.add(fileName);
		siFiles.add(IndexFileNames.segmentFileName(info.name, "", "cfe"));
		info.setFiles(siFiles);
		return files;
	}



}
