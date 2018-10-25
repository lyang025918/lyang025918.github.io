// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LiveIndexWriterConfig.java

package org.apache.lucene.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.InfoStream;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.index:
//			KeepOnlyLastCommitDeletionPolicy, ConcurrentMergeScheduler, TieredMergePolicy, FlushByRamOrCountsPolicy, 
//			ThreadAffinityDocumentsWriterThreadPool, IndexCommit, IndexWriterConfig, DocumentsWriterPerThread, 
//			IndexWriter, IndexDeletionPolicy, MergeScheduler, MergePolicy, 
//			DocumentsWriterPerThreadPool, FlushPolicy

public class LiveIndexWriterConfig
{

	private final Analyzer analyzer;
	private volatile int maxBufferedDocs;
	private volatile double ramBufferSizeMB;
	private volatile int maxBufferedDeleteTerms;
	private volatile int readerTermsIndexDivisor;
	private volatile IndexWriter.IndexReaderWarmer mergedSegmentWarmer;
	private volatile int termIndexInterval;
	protected volatile IndexDeletionPolicy delPolicy;
	protected volatile IndexCommit commit;
	protected volatile IndexWriterConfig.OpenMode openMode;
	protected volatile Similarity similarity;
	protected volatile MergeScheduler mergeScheduler;
	protected volatile long writeLockTimeout;
	protected volatile DocumentsWriterPerThread.IndexingChain indexingChain;
	protected volatile Codec codec;
	protected volatile InfoStream infoStream;
	protected volatile MergePolicy mergePolicy;
	protected volatile DocumentsWriterPerThreadPool indexerThreadPool;
	protected volatile boolean readerPooling;
	protected volatile FlushPolicy flushPolicy;
	protected volatile int perThreadHardLimitMB;
	protected final Version matchVersion;

	LiveIndexWriterConfig(Analyzer analyzer, Version matchVersion)
	{
		this.analyzer = analyzer;
		this.matchVersion = matchVersion;
		ramBufferSizeMB = 16D;
		maxBufferedDocs = -1;
		maxBufferedDeleteTerms = -1;
		readerTermsIndexDivisor = 1;
		mergedSegmentWarmer = null;
		termIndexInterval = 32;
		delPolicy = new KeepOnlyLastCommitDeletionPolicy();
		commit = null;
		openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND;
		similarity = IndexSearcher.getDefaultSimilarity();
		mergeScheduler = new ConcurrentMergeScheduler();
		writeLockTimeout = IndexWriterConfig.WRITE_LOCK_TIMEOUT;
		indexingChain = DocumentsWriterPerThread.defaultIndexingChain;
		codec = Codec.getDefault();
		infoStream = InfoStream.getDefault();
		mergePolicy = new TieredMergePolicy();
		flushPolicy = new FlushByRamOrCountsPolicy();
		readerPooling = false;
		indexerThreadPool = new ThreadAffinityDocumentsWriterThreadPool(8);
		perThreadHardLimitMB = 1945;
	}

	LiveIndexWriterConfig(IndexWriterConfig config)
	{
		maxBufferedDeleteTerms = config.getMaxBufferedDeleteTerms();
		maxBufferedDocs = config.getMaxBufferedDocs();
		mergedSegmentWarmer = config.getMergedSegmentWarmer();
		ramBufferSizeMB = config.getRAMBufferSizeMB();
		readerTermsIndexDivisor = config.getReaderTermsIndexDivisor();
		termIndexInterval = config.getTermIndexInterval();
		matchVersion = config.matchVersion;
		analyzer = config.getAnalyzer();
		delPolicy = config.getIndexDeletionPolicy();
		commit = config.getIndexCommit();
		openMode = config.getOpenMode();
		similarity = config.getSimilarity();
		mergeScheduler = config.getMergeScheduler();
		writeLockTimeout = config.getWriteLockTimeout();
		indexingChain = config.getIndexingChain();
		codec = config.getCodec();
		infoStream = config.getInfoStream();
		mergePolicy = config.getMergePolicy();
		indexerThreadPool = config.getIndexerThreadPool();
		readerPooling = config.getReaderPooling();
		flushPolicy = config.getFlushPolicy();
		perThreadHardLimitMB = config.getRAMPerThreadHardLimitMB();
	}

	public Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public LiveIndexWriterConfig setTermIndexInterval(int interval)
	{
		termIndexInterval = interval;
		return this;
	}

	public int getTermIndexInterval()
	{
		return termIndexInterval;
	}

	public LiveIndexWriterConfig setMaxBufferedDeleteTerms(int maxBufferedDeleteTerms)
	{
		if (maxBufferedDeleteTerms != -1 && maxBufferedDeleteTerms < 1)
		{
			throw new IllegalArgumentException("maxBufferedDeleteTerms must at least be 1 when enabled");
		} else
		{
			this.maxBufferedDeleteTerms = maxBufferedDeleteTerms;
			return this;
		}
	}

	public int getMaxBufferedDeleteTerms()
	{
		return maxBufferedDeleteTerms;
	}

	public LiveIndexWriterConfig setRAMBufferSizeMB(double ramBufferSizeMB)
	{
		if (ramBufferSizeMB != -1D && ramBufferSizeMB <= 0.0D)
			throw new IllegalArgumentException("ramBufferSize should be > 0.0 MB when enabled");
		if (ramBufferSizeMB == -1D && maxBufferedDocs == -1)
		{
			throw new IllegalArgumentException("at least one of ramBufferSize and maxBufferedDocs must be enabled");
		} else
		{
			this.ramBufferSizeMB = ramBufferSizeMB;
			return this;
		}
	}

	public double getRAMBufferSizeMB()
	{
		return ramBufferSizeMB;
	}

	public LiveIndexWriterConfig setMaxBufferedDocs(int maxBufferedDocs)
	{
		if (maxBufferedDocs != -1 && maxBufferedDocs < 2)
			throw new IllegalArgumentException("maxBufferedDocs must at least be 2 when enabled");
		if (maxBufferedDocs == -1 && ramBufferSizeMB == -1D)
		{
			throw new IllegalArgumentException("at least one of ramBufferSize and maxBufferedDocs must be enabled");
		} else
		{
			this.maxBufferedDocs = maxBufferedDocs;
			return this;
		}
	}

	public int getMaxBufferedDocs()
	{
		return maxBufferedDocs;
	}

	public LiveIndexWriterConfig setMergedSegmentWarmer(IndexWriter.IndexReaderWarmer mergeSegmentWarmer)
	{
		mergedSegmentWarmer = mergeSegmentWarmer;
		return this;
	}

	public IndexWriter.IndexReaderWarmer getMergedSegmentWarmer()
	{
		return mergedSegmentWarmer;
	}

	public LiveIndexWriterConfig setReaderTermsIndexDivisor(int divisor)
	{
		if (divisor <= 0 && divisor != -1)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("divisor must be >= 1, or -1 (got ").append(divisor).append(")").toString());
		} else
		{
			readerTermsIndexDivisor = divisor;
			return this;
		}
	}

	public int getReaderTermsIndexDivisor()
	{
		return readerTermsIndexDivisor;
	}

	public IndexWriterConfig.OpenMode getOpenMode()
	{
		return openMode;
	}

	public IndexDeletionPolicy getIndexDeletionPolicy()
	{
		return delPolicy;
	}

	public IndexCommit getIndexCommit()
	{
		return commit;
	}

	public Similarity getSimilarity()
	{
		return similarity;
	}

	public MergeScheduler getMergeScheduler()
	{
		return mergeScheduler;
	}

	public long getWriteLockTimeout()
	{
		return writeLockTimeout;
	}

	public Codec getCodec()
	{
		return codec;
	}

	public MergePolicy getMergePolicy()
	{
		return mergePolicy;
	}

	DocumentsWriterPerThreadPool getIndexerThreadPool()
	{
		return indexerThreadPool;
	}

	public int getMaxThreadStates()
	{
		return ((ThreadAffinityDocumentsWriterThreadPool)indexerThreadPool).getMaxThreadStates();
		ClassCastException cce;
		cce;
		throw new IllegalStateException(cce);
	}

	public boolean getReaderPooling()
	{
		return readerPooling;
	}

	DocumentsWriterPerThread.IndexingChain getIndexingChain()
	{
		return indexingChain;
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

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("matchVersion=").append(matchVersion).append("\n");
		sb.append("analyzer=").append(analyzer != null ? analyzer.getClass().getName() : "null").append("\n");
		sb.append("ramBufferSizeMB=").append(getRAMBufferSizeMB()).append("\n");
		sb.append("maxBufferedDocs=").append(getMaxBufferedDocs()).append("\n");
		sb.append("maxBufferedDeleteTerms=").append(getMaxBufferedDeleteTerms()).append("\n");
		sb.append("mergedSegmentWarmer=").append(getMergedSegmentWarmer()).append("\n");
		sb.append("readerTermsIndexDivisor=").append(getReaderTermsIndexDivisor()).append("\n");
		sb.append("termIndexInterval=").append(getTermIndexInterval()).append("\n");
		sb.append("delPolicy=").append(getIndexDeletionPolicy().getClass().getName()).append("\n");
		IndexCommit commit = getIndexCommit();
		sb.append("commit=").append(commit != null ? ((Object) (commit)) : "null").append("\n");
		sb.append("openMode=").append(getOpenMode()).append("\n");
		sb.append("similarity=").append(getSimilarity().getClass().getName()).append("\n");
		sb.append("mergeScheduler=").append(getMergeScheduler().getClass().getName()).append("\n");
		sb.append("default WRITE_LOCK_TIMEOUT=").append(IndexWriterConfig.WRITE_LOCK_TIMEOUT).append("\n");
		sb.append("writeLockTimeout=").append(getWriteLockTimeout()).append("\n");
		sb.append("codec=").append(getCodec()).append("\n");
		sb.append("infoStream=").append(getInfoStream().getClass().getName()).append("\n");
		sb.append("mergePolicy=").append(getMergePolicy()).append("\n");
		sb.append("indexerThreadPool=").append(getIndexerThreadPool()).append("\n");
		sb.append("readerPooling=").append(getReaderPooling()).append("\n");
		sb.append("perThreadHardLimitMB=").append(getRAMPerThreadHardLimitMB()).append("\n");
		return sb.toString();
	}
}
