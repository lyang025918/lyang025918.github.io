// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexWriterConfig.java

package org.apache.lucene.index;

import java.io.PrintStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			LiveIndexWriterConfig, KeepOnlyLastCommitDeletionPolicy, ConcurrentMergeScheduler, LogByteSizeMergePolicy, 
//			ThreadAffinityDocumentsWriterThreadPool, IndexDeletionPolicy, MergeScheduler, MergePolicy, 
//			FlushPolicy, DocumentsWriterPerThreadPool, DocumentsWriterPerThread, IndexWriter, 
//			IndexCommit

public final class IndexWriterConfig extends LiveIndexWriterConfig
	implements Cloneable
{
	public static final class OpenMode extends Enum
	{

		public static final OpenMode CREATE;
		public static final OpenMode APPEND;
		public static final OpenMode CREATE_OR_APPEND;
		private static final OpenMode $VALUES[];

		public static OpenMode[] values()
		{
			return (OpenMode[])$VALUES.clone();
		}

		public static OpenMode valueOf(String name)
		{
			return (OpenMode)Enum.valueOf(org/apache/lucene/index/IndexWriterConfig$OpenMode, name);
		}

		static 
		{
			CREATE = new OpenMode("CREATE", 0);
			APPEND = new OpenMode("APPEND", 1);
			CREATE_OR_APPEND = new OpenMode("CREATE_OR_APPEND", 2);
			$VALUES = (new OpenMode[] {
				CREATE, APPEND, CREATE_OR_APPEND
			});
		}

		private OpenMode(String s, int i)
		{
			super(s, i);
		}
	}


	public static final int DEFAULT_TERM_INDEX_INTERVAL = 32;
	public static final int DISABLE_AUTO_FLUSH = -1;
	public static final int DEFAULT_MAX_BUFFERED_DELETE_TERMS = -1;
	public static final int DEFAULT_MAX_BUFFERED_DOCS = -1;
	public static final double DEFAULT_RAM_BUFFER_SIZE_MB = 16D;
	public static long WRITE_LOCK_TIMEOUT = 1000L;
	public static final boolean DEFAULT_READER_POOLING = false;
	public static final int DEFAULT_READER_TERMS_INDEX_DIVISOR = 1;
	public static final int DEFAULT_RAM_PER_THREAD_HARD_LIMIT_MB = 1945;
	public static final int DEFAULT_MAX_THREAD_STATES = 8;

	public static void setDefaultWriteLockTimeout(long writeLockTimeout)
	{
		WRITE_LOCK_TIMEOUT = writeLockTimeout;
	}

	public static long getDefaultWriteLockTimeout()
	{
		return WRITE_LOCK_TIMEOUT;
	}

	public IndexWriterConfig(Version matchVersion, Analyzer analyzer)
	{
		super(analyzer, matchVersion);
	}

	public IndexWriterConfig clone()
	{
		IndexWriterConfig clone;
		clone = (IndexWriterConfig)super.clone();
		clone.flushPolicy = flushPolicy.clone();
		clone.indexerThreadPool = indexerThreadPool.clone();
		clone.mergePolicy = mergePolicy.clone();
		return clone;
		CloneNotSupportedException e;
		e;
		throw new RuntimeException(e);
	}

	public IndexWriterConfig setOpenMode(OpenMode openMode)
	{
		this.openMode = openMode;
		return this;
	}

	public OpenMode getOpenMode()
	{
		return openMode;
	}

	public IndexWriterConfig setIndexDeletionPolicy(IndexDeletionPolicy delPolicy)
	{
		this.delPolicy = ((IndexDeletionPolicy) (delPolicy != null ? delPolicy : ((IndexDeletionPolicy) (new KeepOnlyLastCommitDeletionPolicy()))));
		return this;
	}

	public IndexDeletionPolicy getIndexDeletionPolicy()
	{
		return delPolicy;
	}

	public IndexWriterConfig setIndexCommit(IndexCommit commit)
	{
		this.commit = commit;
		return this;
	}

	public IndexCommit getIndexCommit()
	{
		return commit;
	}

	public IndexWriterConfig setSimilarity(Similarity similarity)
	{
		this.similarity = similarity != null ? similarity : IndexSearcher.getDefaultSimilarity();
		return this;
	}

	public Similarity getSimilarity()
	{
		return similarity;
	}

	public IndexWriterConfig setMergeScheduler(MergeScheduler mergeScheduler)
	{
		this.mergeScheduler = ((MergeScheduler) (mergeScheduler != null ? mergeScheduler : ((MergeScheduler) (new ConcurrentMergeScheduler()))));
		return this;
	}

	public MergeScheduler getMergeScheduler()
	{
		return mergeScheduler;
	}

	public IndexWriterConfig setWriteLockTimeout(long writeLockTimeout)
	{
		this.writeLockTimeout = writeLockTimeout;
		return this;
	}

	public long getWriteLockTimeout()
	{
		return writeLockTimeout;
	}

	public IndexWriterConfig setMergePolicy(MergePolicy mergePolicy)
	{
		this.mergePolicy = ((MergePolicy) (mergePolicy != null ? mergePolicy : ((MergePolicy) (new LogByteSizeMergePolicy()))));
		return this;
	}

	public IndexWriterConfig setCodec(Codec codec)
	{
		this.codec = codec;
		return this;
	}

	public Codec getCodec()
	{
		return codec;
	}

	public MergePolicy getMergePolicy()
	{
		return mergePolicy;
	}

	IndexWriterConfig setIndexerThreadPool(DocumentsWriterPerThreadPool threadPool)
	{
		if (threadPool == null)
		{
			throw new IllegalArgumentException("threadPool must not be null");
		} else
		{
			indexerThreadPool = threadPool;
			return this;
		}
	}

	DocumentsWriterPerThreadPool getIndexerThreadPool()
	{
		return indexerThreadPool;
	}

	public IndexWriterConfig setMaxThreadStates(int maxThreadStates)
	{
		indexerThreadPool = new ThreadAffinityDocumentsWriterThreadPool(maxThreadStates);
		return this;
	}

	public int getMaxThreadStates()
	{
		return ((ThreadAffinityDocumentsWriterThreadPool)indexerThreadPool).getMaxThreadStates();
		ClassCastException cce;
		cce;
		throw new IllegalStateException(cce);
	}

	public IndexWriterConfig setReaderPooling(boolean readerPooling)
	{
		this.readerPooling = readerPooling;
		return this;
	}

	public boolean getReaderPooling()
	{
		return readerPooling;
	}

	IndexWriterConfig setIndexingChain(DocumentsWriterPerThread.IndexingChain indexingChain)
	{
		this.indexingChain = indexingChain != null ? indexingChain : DocumentsWriterPerThread.defaultIndexingChain;
		return this;
	}

	DocumentsWriterPerThread.IndexingChain getIndexingChain()
	{
		return indexingChain;
	}

	IndexWriterConfig setFlushPolicy(FlushPolicy flushPolicy)
	{
		this.flushPolicy = flushPolicy;
		return this;
	}

	public IndexWriterConfig setRAMPerThreadHardLimitMB(int perThreadHardLimitMB)
	{
		if (perThreadHardLimitMB <= 0 || perThreadHardLimitMB >= 2048)
		{
			throw new IllegalArgumentException("PerThreadHardLimit must be greater than 0 and less than 2048MB");
		} else
		{
			this.perThreadHardLimitMB = perThreadHardLimitMB;
			return this;
		}
	}

	public int getRAMPerThreadHardLimitMB()
	{
		return perThreadHardLimitMB;
	}

	FlushPolicy getFlushPolicy()
	{
		return flushPolicy;
	}

	public InfoStream getInfoStream()
	{
		return infoStream;
	}

	public Analyzer getAnalyzer()
	{
		return super.getAnalyzer();
	}

	public int getMaxBufferedDeleteTerms()
	{
		return super.getMaxBufferedDeleteTerms();
	}

	public int getMaxBufferedDocs()
	{
		return super.getMaxBufferedDocs();
	}

	public IndexWriter.IndexReaderWarmer getMergedSegmentWarmer()
	{
		return super.getMergedSegmentWarmer();
	}

	public double getRAMBufferSizeMB()
	{
		return super.getRAMBufferSizeMB();
	}

	public int getReaderTermsIndexDivisor()
	{
		return super.getReaderTermsIndexDivisor();
	}

	public int getTermIndexInterval()
	{
		return super.getTermIndexInterval();
	}

	public IndexWriterConfig setInfoStream(InfoStream infoStream)
	{
		if (infoStream == null)
		{
			throw new IllegalArgumentException("Cannot set InfoStream implementation to null. To disable logging use InfoStream.NO_OUTPUT");
		} else
		{
			this.infoStream = infoStream;
			return this;
		}
	}

	public IndexWriterConfig setInfoStream(PrintStream printStream)
	{
		return setInfoStream(((InfoStream) (printStream != null ? ((InfoStream) (new PrintStreamInfoStream(printStream))) : InfoStream.NO_OUTPUT)));
	}

	public IndexWriterConfig setMaxBufferedDeleteTerms(int maxBufferedDeleteTerms)
	{
		return (IndexWriterConfig)super.setMaxBufferedDeleteTerms(maxBufferedDeleteTerms);
	}

	public IndexWriterConfig setMaxBufferedDocs(int maxBufferedDocs)
	{
		return (IndexWriterConfig)super.setMaxBufferedDocs(maxBufferedDocs);
	}

	public IndexWriterConfig setMergedSegmentWarmer(IndexWriter.IndexReaderWarmer mergeSegmentWarmer)
	{
		return (IndexWriterConfig)super.setMergedSegmentWarmer(mergeSegmentWarmer);
	}

	public IndexWriterConfig setRAMBufferSizeMB(double ramBufferSizeMB)
	{
		return (IndexWriterConfig)super.setRAMBufferSizeMB(ramBufferSizeMB);
	}

	public IndexWriterConfig setReaderTermsIndexDivisor(int divisor)
	{
		return (IndexWriterConfig)super.setReaderTermsIndexDivisor(divisor);
	}

	public IndexWriterConfig setTermIndexInterval(int interval)
	{
		return (IndexWriterConfig)super.setTermIndexInterval(interval);
	}

	public volatile LiveIndexWriterConfig setReaderTermsIndexDivisor(int x0)
	{
		return setReaderTermsIndexDivisor(x0);
	}

	public volatile LiveIndexWriterConfig setMergedSegmentWarmer(IndexWriter.IndexReaderWarmer x0)
	{
		return setMergedSegmentWarmer(x0);
	}

	public volatile LiveIndexWriterConfig setMaxBufferedDocs(int x0)
	{
		return setMaxBufferedDocs(x0);
	}

	public volatile LiveIndexWriterConfig setRAMBufferSizeMB(double x0)
	{
		return setRAMBufferSizeMB(x0);
	}

	public volatile LiveIndexWriterConfig setMaxBufferedDeleteTerms(int x0)
	{
		return setMaxBufferedDeleteTerms(x0);
	}

	public volatile LiveIndexWriterConfig setTermIndexInterval(int x0)
	{
		return setTermIndexInterval(x0);
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
