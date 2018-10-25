// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermInfosReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			SegmentTermEnum, TermInfosReaderIndex, TermInfo

/**
 * @deprecated Class TermInfosReader is deprecated
 */

final class TermInfosReader
	implements Closeable
{
	private static final class ThreadResources
	{

		SegmentTermEnum termEnum;

		private ThreadResources()
		{
		}

	}

	private static class CloneableTerm extends org.apache.lucene.util.DoubleBarrelLRUCache.CloneableKey
	{

		Term term;

		public boolean equals(Object other)
		{
			CloneableTerm t = (CloneableTerm)other;
			return term.equals(t.term);
		}

		public int hashCode()
		{
			return term.hashCode();
		}

		public CloneableTerm clone()
		{
			return new CloneableTerm(term);
		}

		public volatile org.apache.lucene.util.DoubleBarrelLRUCache.CloneableKey clone()
		{
			return clone();
		}

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}

		public CloneableTerm(Term t)
		{
			term = t;
		}
	}

	private static final class TermInfoAndOrd extends TermInfo
	{

		final long termOrd;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/TermInfosReader.desiredAssertionStatus();


		public TermInfoAndOrd(TermInfo ti, long termOrd)
		{
			super(ti);
			if (!$assertionsDisabled && termOrd < 0L)
			{
				throw new AssertionError();
			} else
			{
				this.termOrd = termOrd;
				return;
			}
		}
	}


	private final Directory directory;
	private final String segment;
	private final FieldInfos fieldInfos;
	private final CloseableThreadLocal threadResources;
	private final SegmentTermEnum origEnum;
	private final long size;
	private final TermInfosReaderIndex index;
	private final int indexLength;
	private final int totalIndexInterval;
	private static final int DEFAULT_CACHE_SIZE = 1024;
	private final DoubleBarrelLRUCache termsCache;
	private static final Comparator legacyComparator = BytesRef.getUTF8SortedAsUTF16Comparator();
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/TermInfosReader.desiredAssertionStatus();

	TermInfosReader(Directory dir, String seg, FieldInfos fis, IOContext context, int indexDivisor)
		throws CorruptIndexException, IOException
	{
		boolean success;
		threadResources = new CloseableThreadLocal();
		termsCache = new DoubleBarrelLRUCache(1024);
		success = false;
		if (indexDivisor < 1 && indexDivisor != -1)
			throw new IllegalArgumentException((new StringBuilder()).append("indexDivisor must be -1 (don't load terms index) or greater than 0: got ").append(indexDivisor).toString());
		String indexFileName;
		SegmentTermEnum indexEnum;
		directory = dir;
		segment = seg;
		fieldInfos = fis;
		origEnum = new SegmentTermEnum(directory.openInput(IndexFileNames.segmentFileName(segment, "", "tis"), context), fieldInfos, false);
		size = origEnum.size;
		if (indexDivisor == -1)
			break MISSING_BLOCK_LABEL_249;
		totalIndexInterval = origEnum.indexInterval * indexDivisor;
		indexFileName = IndexFileNames.segmentFileName(segment, "", "tii");
		indexEnum = new SegmentTermEnum(directory.openInput(indexFileName, context), fieldInfos, true);
		index = new TermInfosReaderIndex(indexEnum, indexDivisor, dir.fileLength(indexFileName), totalIndexInterval);
		indexLength = index.length();
		indexEnum.close();
		break MISSING_BLOCK_LABEL_264;
		Exception exception;
		exception;
		indexEnum.close();
		throw exception;
		totalIndexInterval = -1;
		index = null;
		indexLength = -1;
		success = true;
		if (!success)
			close();
		break MISSING_BLOCK_LABEL_293;
		Exception exception1;
		exception1;
		if (!success)
			close();
		throw exception1;
	}

	public int getSkipInterval()
	{
		return origEnum.skipInterval;
	}

	public int getMaxSkipLevels()
	{
		return origEnum.maxSkipLevels;
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			origEnum, threadResources
		});
	}

	long size()
	{
		return size;
	}

	private ThreadResources getThreadResources()
	{
		ThreadResources resources = (ThreadResources)threadResources.get();
		if (resources == null)
		{
			resources = new ThreadResources();
			resources.termEnum = terms();
			threadResources.set(resources);
		}
		return resources;
	}

	private final int compareAsUTF16(Term term1, Term term2)
	{
		if (term1.field().equals(term2.field()))
			return legacyComparator.compare(term1.bytes(), term2.bytes());
		else
			return term1.field().compareTo(term2.field());
	}

	TermInfo get(Term term)
		throws IOException
	{
		return get(term, false);
	}

	private TermInfo get(Term term, boolean mustSeekEnum)
		throws IOException
	{
		if (size == 0L)
			return null;
		ensureIndexIsRead();
		TermInfoAndOrd tiOrd = (TermInfoAndOrd)termsCache.get(new CloneableTerm(term));
		ThreadResources resources = getThreadResources();
		if (!mustSeekEnum && tiOrd != null)
			return tiOrd;
		else
			return seekEnum(resources.termEnum, term, tiOrd, true);
	}

	public void cacheCurrentTerm(SegmentTermEnum enumerator)
	{
		termsCache.put(new CloneableTerm(enumerator.term()), new TermInfoAndOrd(enumerator.termInfo, enumerator.position));
	}

	static Term deepCopyOf(Term other)
	{
		return new Term(other.field(), BytesRef.deepCopyOf(other.bytes()));
	}

	TermInfo seekEnum(SegmentTermEnum enumerator, Term term, boolean useCache)
		throws IOException
	{
		if (useCache)
			return seekEnum(enumerator, term, (TermInfoAndOrd)termsCache.get(new CloneableTerm(deepCopyOf(term))), useCache);
		else
			return seekEnum(enumerator, term, null, useCache);
	}

	TermInfo seekEnum(SegmentTermEnum enumerator, Term term, TermInfoAndOrd tiOrd, boolean useCache)
		throws IOException
	{
		if (size == 0L)
			return null;
		TermInfo ti;
		if (enumerator.term() != null && (enumerator.prev() != null && compareAsUTF16(term, enumerator.prev()) > 0 || compareAsUTF16(term, enumerator.term()) >= 0))
		{
			int enumOffset = (int)(enumerator.position / (long)totalIndexInterval) + 1;
			if (indexLength == enumOffset || index.compareTo(term, enumOffset) < 0)
			{
				int numScans = enumerator.scanTo(term);
				if (enumerator.term() != null && compareAsUTF16(term, enumerator.term()) == 0)
				{
					ti = enumerator.termInfo;
					if (numScans > 1)
						if (tiOrd == null)
						{
							if (useCache)
								termsCache.put(new CloneableTerm(deepCopyOf(term)), new TermInfoAndOrd(ti, enumerator.position));
						} else
						{
							if (!$assertionsDisabled && !sameTermInfo(ti, tiOrd, enumerator))
								throw new AssertionError();
							if (!$assertionsDisabled && (long)(int)enumerator.position != tiOrd.termOrd)
								throw new AssertionError();
						}
				} else
				{
					ti = null;
				}
				return ti;
			}
		}
		int indexPos;
		if (tiOrd != null)
			indexPos = (int)(tiOrd.termOrd / (long)totalIndexInterval);
		else
			indexPos = index.getIndexOffset(term);
		index.seekEnum(enumerator, indexPos);
		enumerator.scanTo(term);
		if (enumerator.term() != null && compareAsUTF16(term, enumerator.term()) == 0)
		{
			ti = enumerator.termInfo;
			if (tiOrd == null)
			{
				if (useCache)
					termsCache.put(new CloneableTerm(deepCopyOf(term)), new TermInfoAndOrd(ti, enumerator.position));
			} else
			{
				if (!$assertionsDisabled && !sameTermInfo(ti, tiOrd, enumerator))
					throw new AssertionError();
				if (!$assertionsDisabled && enumerator.position != tiOrd.termOrd)
					throw new AssertionError();
			}
		} else
		{
			ti = null;
		}
		return ti;
	}

	private boolean sameTermInfo(TermInfo ti1, TermInfo ti2, SegmentTermEnum enumerator)
	{
		if (ti1.docFreq != ti2.docFreq)
			return false;
		if (ti1.freqPointer != ti2.freqPointer)
			return false;
		if (ti1.proxPointer != ti2.proxPointer)
			return false;
		return ti1.docFreq < enumerator.skipInterval || ti1.skipOffset == ti2.skipOffset;
	}

	private void ensureIndexIsRead()
	{
		if (index == null)
			throw new IllegalStateException("terms index was not loaded when this reader was created");
		else
			return;
	}

	long getPosition(Term term)
		throws IOException
	{
		if (size == 0L)
			return -1L;
		ensureIndexIsRead();
		int indexOffset = index.getIndexOffset(term);
		SegmentTermEnum enumerator = getThreadResources().termEnum;
		index.seekEnum(enumerator, indexOffset);
		while (compareAsUTF16(term, enumerator.term()) > 0 && enumerator.next()) ;
		if (compareAsUTF16(term, enumerator.term()) == 0)
			return enumerator.position;
		else
			return -1L;
	}

	public SegmentTermEnum terms()
	{
		return origEnum.clone();
	}

	public SegmentTermEnum terms(Term term)
		throws IOException
	{
		get(term, true);
		return getThreadResources().termEnum.clone();
	}

}
