// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentTermEnum.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.store.IndexInput;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			TermBuffer, TermInfo

/**
 * @deprecated Class SegmentTermEnum is deprecated
 */

final class SegmentTermEnum
	implements Cloneable, Closeable
{

	private IndexInput input;
	FieldInfos fieldInfos;
	long size;
	long position;
	public static final int FORMAT_VERSION_UTF8_LENGTH_IN_BYTES = -4;
	public static final int FORMAT_CURRENT = -4;
	public static final int FORMAT_MINIMUM = -4;
	private TermBuffer termBuffer;
	private TermBuffer prevBuffer;
	private TermBuffer scanBuffer;
	TermInfo termInfo;
	private int format;
	private boolean isIndex;
	long indexPointer;
	int indexInterval;
	int skipInterval;
	int newSuffixStart;
	int maxSkipLevels;
	private boolean first;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/SegmentTermEnum.desiredAssertionStatus();

	SegmentTermEnum(IndexInput i, FieldInfos fis, boolean isi)
		throws CorruptIndexException, IOException
	{
		position = -1L;
		termBuffer = new TermBuffer();
		prevBuffer = new TermBuffer();
		scanBuffer = new TermBuffer();
		termInfo = new TermInfo();
		isIndex = false;
		indexPointer = 0L;
		first = true;
		input = i;
		fieldInfos = fis;
		isIndex = isi;
		maxSkipLevels = 1;
		int firstInt = input.readInt();
		if (firstInt >= 0)
		{
			format = 0;
			size = firstInt;
			indexInterval = 128;
			skipInterval = 0x7fffffff;
		} else
		{
			format = firstInt;
			if (format > -4)
				throw new IndexFormatTooOldException(input, format, -4, -4);
			if (format < -4)
				throw new IndexFormatTooNewException(input, format, -4, -4);
			size = input.readLong();
			indexInterval = input.readInt();
			skipInterval = input.readInt();
			maxSkipLevels = input.readInt();
			if (!$assertionsDisabled && indexInterval <= 0)
				throw new AssertionError((new StringBuilder()).append("indexInterval=").append(indexInterval).append(" is negative; must be > 0").toString());
			if (!$assertionsDisabled && skipInterval <= 0)
				throw new AssertionError((new StringBuilder()).append("skipInterval=").append(skipInterval).append(" is negative; must be > 0").toString());
		}
	}

	protected SegmentTermEnum clone()
	{
		SegmentTermEnum clone = null;
		try
		{
			clone = (SegmentTermEnum)super.clone();
		}
		catch (CloneNotSupportedException e) { }
		clone.input = input.clone();
		clone.termInfo = new TermInfo(termInfo);
		clone.termBuffer = termBuffer.clone();
		clone.prevBuffer = prevBuffer.clone();
		clone.scanBuffer = new TermBuffer();
		return clone;
	}

	final void seek(long pointer, long p, Term t, TermInfo ti)
		throws IOException
	{
		input.seek(pointer);
		position = p;
		termBuffer.set(t);
		prevBuffer.reset();
		termInfo.set(ti);
		first = p == -1L;
	}

	public final boolean next()
		throws IOException
	{
		prevBuffer.set(termBuffer);
		if (position++ >= size - 1L)
		{
			termBuffer.reset();
			return false;
		}
		termBuffer.read(input, fieldInfos);
		newSuffixStart = termBuffer.newSuffixStart;
		termInfo.docFreq = input.readVInt();
		termInfo.freqPointer += input.readVLong();
		termInfo.proxPointer += input.readVLong();
		if (termInfo.docFreq >= skipInterval)
			termInfo.skipOffset = input.readVInt();
		if (isIndex)
			indexPointer += input.readVLong();
		return true;
	}

	final int scanTo(Term term)
		throws IOException
	{
		scanBuffer.set(term);
		int count = 0;
		if (first)
		{
			next();
			first = false;
			count++;
		}
		while (scanBuffer.compareTo(termBuffer) > 0 && next()) 
			count++;
		return count;
	}

	public final Term term()
	{
		return termBuffer.toTerm();
	}

	final Term prev()
	{
		return prevBuffer.toTerm();
	}

	final TermInfo termInfo()
	{
		return new TermInfo(termInfo);
	}

	final void termInfo(TermInfo ti)
	{
		ti.set(termInfo);
	}

	public final int docFreq()
	{
		return termInfo.docFreq;
	}

	final long freqPointer()
	{
		return termInfo.freqPointer;
	}

	final long proxPointer()
	{
		return termInfo.proxPointer;
	}

	public final void close()
		throws IOException
	{
		input.close();
	}

	protected volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
