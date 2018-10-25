// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexReader.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DocumentStoredFieldVisitor;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.index:
//			CompositeReader, AtomicReader, Fields, DirectoryReader, 
//			IndexReaderContext, IndexWriter, IndexCommit, Terms, 
//			StoredFieldVisitor, Term

public abstract class IndexReader
	implements Closeable
{
	public static interface ReaderClosedListener
	{

		public abstract void onClose(IndexReader indexreader);
	}


	private boolean closed;
	private boolean closedByChild;
	private final AtomicInteger refCount = new AtomicInteger(1);
	private final Set readerClosedListeners = Collections.synchronizedSet(new LinkedHashSet());
	private final Set parentReaders = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));

	IndexReader()
	{
		closed = false;
		closedByChild = false;
		if (!(this instanceof CompositeReader) && !(this instanceof AtomicReader))
			throw new Error("IndexReader should never be directly extended, subclass AtomicReader or CompositeReader instead.");
		else
			return;
	}

	public final void addReaderClosedListener(ReaderClosedListener listener)
	{
		ensureOpen();
		readerClosedListeners.add(listener);
	}

	public final void removeReaderClosedListener(ReaderClosedListener listener)
	{
		ensureOpen();
		readerClosedListeners.remove(listener);
	}

	public final void registerParentReader(IndexReader reader)
	{
		ensureOpen();
		parentReaders.add(reader);
	}

	private void notifyReaderClosedListeners()
	{
		synchronized (readerClosedListeners)
		{
			ReaderClosedListener listener;
			for (Iterator i$ = readerClosedListeners.iterator(); i$.hasNext(); listener.onClose(this))
				listener = (ReaderClosedListener)i$.next();

		}
	}

	private void reportCloseToParentReaders()
	{
		synchronized (parentReaders)
		{
			IndexReader parent;
			for (Iterator i$ = parentReaders.iterator(); i$.hasNext(); parent.reportCloseToParentReaders())
			{
				parent = (IndexReader)i$.next();
				parent.closedByChild = true;
				parent.refCount.addAndGet(0);
			}

		}
	}

	public final int getRefCount()
	{
		return refCount.get();
	}

	public final void incRef()
	{
		ensureOpen();
		refCount.incrementAndGet();
	}

	public final boolean tryIncRef()
	{
		int count;
		while ((count = refCount.get()) > 0) 
			if (refCount.compareAndSet(count, count + 1))
				return true;
		return false;
	}

	public final void decRef()
		throws IOException
	{
		int rc;
		boolean success;
		if (refCount.get() <= 0)
			throw new AlreadyClosedException("this IndexReader is closed");
		rc = refCount.decrementAndGet();
		if (rc != 0)
			break MISSING_BLOCK_LABEL_81;
		success = false;
		doClose();
		success = true;
		if (!success)
			refCount.incrementAndGet();
		break MISSING_BLOCK_LABEL_70;
		Exception exception;
		exception;
		if (!success)
			refCount.incrementAndGet();
		throw exception;
		reportCloseToParentReaders();
		notifyReaderClosedListeners();
		break MISSING_BLOCK_LABEL_117;
		if (rc < 0)
			throw new IllegalStateException((new StringBuilder()).append("too many decRef calls: refCount is ").append(rc).append(" after decrement").toString());
	}

	protected final void ensureOpen()
		throws AlreadyClosedException
	{
		if (refCount.get() <= 0)
			throw new AlreadyClosedException("this IndexReader is closed");
		if (closedByChild)
			throw new AlreadyClosedException("this IndexReader cannot be used anymore as one of its child readers was closed");
		else
			return;
	}

	public final boolean equals(Object obj)
	{
		return this == obj;
	}

	public final int hashCode()
	{
		return System.identityHashCode(this);
	}

	/**
	 * @deprecated Method open is deprecated
	 */

	public static DirectoryReader open(Directory directory)
		throws IOException
	{
		return DirectoryReader.open(directory);
	}

	/**
	 * @deprecated Method open is deprecated
	 */

	public static DirectoryReader open(Directory directory, int termInfosIndexDivisor)
		throws IOException
	{
		return DirectoryReader.open(directory, termInfosIndexDivisor);
	}

	/**
	 * @deprecated Method open is deprecated
	 */

	public static DirectoryReader open(IndexWriter writer, boolean applyAllDeletes)
		throws IOException
	{
		return DirectoryReader.open(writer, applyAllDeletes);
	}

	/**
	 * @deprecated Method open is deprecated
	 */

	public static DirectoryReader open(IndexCommit commit)
		throws IOException
	{
		return DirectoryReader.open(commit);
	}

	/**
	 * @deprecated Method open is deprecated
	 */

	public static DirectoryReader open(IndexCommit commit, int termInfosIndexDivisor)
		throws IOException
	{
		return DirectoryReader.open(commit, termInfosIndexDivisor);
	}

	public abstract Fields getTermVectors(int i)
		throws IOException;

	public final Terms getTermVector(int docID, String field)
		throws IOException
	{
		Fields vectors = getTermVectors(docID);
		if (vectors == null)
			return null;
		else
			return vectors.terms(field);
	}

	public abstract int numDocs();

	public abstract int maxDoc();

	public final int numDeletedDocs()
	{
		return maxDoc() - numDocs();
	}

	public abstract void document(int i, StoredFieldVisitor storedfieldvisitor)
		throws IOException;

	public final Document document(int docID)
		throws IOException
	{
		DocumentStoredFieldVisitor visitor = new DocumentStoredFieldVisitor();
		document(docID, ((StoredFieldVisitor) (visitor)));
		return visitor.getDocument();
	}

	public final Document document(int docID, Set fieldsToLoad)
		throws IOException
	{
		DocumentStoredFieldVisitor visitor = new DocumentStoredFieldVisitor(fieldsToLoad);
		document(docID, ((StoredFieldVisitor) (visitor)));
		return visitor.getDocument();
	}

	public abstract boolean hasDeletions();

	public final synchronized void close()
		throws IOException
	{
		if (!closed)
		{
			decRef();
			closed = true;
		}
	}

	protected abstract void doClose()
		throws IOException;

	public abstract IndexReaderContext getContext();

	public final List leaves()
	{
		return getContext().leaves();
	}

	public Object getCoreCacheKey()
	{
		return this;
	}

	public Object getCombinedCoreAndDeletesKey()
	{
		return this;
	}

	public abstract int docFreq(Term term)
		throws IOException;

	public abstract long totalTermFreq(Term term)
		throws IOException;
}
