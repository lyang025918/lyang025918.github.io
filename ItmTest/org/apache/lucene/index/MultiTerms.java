// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiTerms.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.automaton.CompiledAutomaton;

// Referenced classes of package org.apache.lucene.index:
//			Terms, MultiTermsEnum, TermsEnum, ReaderSlice

public final class MultiTerms extends Terms
{

	private final Terms subs[];
	private final ReaderSlice subSlices[];
	private final Comparator termComp;
	private final boolean hasOffsets;
	private final boolean hasPositions;
	private final boolean hasPayloads;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiTerms.desiredAssertionStatus();

	public MultiTerms(Terms subs[], ReaderSlice subSlices[])
		throws IOException
	{
		this.subs = subs;
		this.subSlices = subSlices;
		Comparator _termComp = null;
		if (!$assertionsDisabled && subs.length <= 0)
			throw new AssertionError("inefficient: don't use MultiTerms over one sub");
		boolean _hasOffsets = true;
		boolean _hasPositions = true;
		boolean _hasPayloads = false;
		for (int i = 0; i < subs.length; i++)
		{
			if (_termComp == null)
			{
				_termComp = subs[i].getComparator();
			} else
			{
				Comparator subTermComp = subs[i].getComparator();
				if (subTermComp != null && !subTermComp.equals(_termComp))
					throw new IllegalStateException("sub-readers have different BytesRef.Comparators; cannot merge");
			}
			_hasOffsets &= subs[i].hasOffsets();
			_hasPositions &= subs[i].hasPositions();
			_hasPayloads |= subs[i].hasPayloads();
		}

		termComp = _termComp;
		hasOffsets = _hasOffsets;
		hasPositions = _hasPositions;
		hasPayloads = hasPositions && _hasPayloads;
	}

	public TermsEnum intersect(CompiledAutomaton compiled, BytesRef startTerm)
		throws IOException
	{
		List termsEnums = new ArrayList();
		for (int i = 0; i < subs.length; i++)
		{
			TermsEnum termsEnum = subs[i].intersect(compiled, startTerm);
			if (termsEnum != null)
				termsEnums.add(new MultiTermsEnum.TermsEnumIndex(termsEnum, i));
		}

		if (termsEnums.size() > 0)
			return (new MultiTermsEnum(subSlices)).reset((MultiTermsEnum.TermsEnumIndex[])termsEnums.toArray(MultiTermsEnum.TermsEnumIndex.EMPTY_ARRAY));
		else
			return TermsEnum.EMPTY;
	}

	public TermsEnum iterator(TermsEnum reuse)
		throws IOException
	{
		List termsEnums = new ArrayList();
		for (int i = 0; i < subs.length; i++)
		{
			TermsEnum termsEnum = subs[i].iterator(null);
			if (termsEnum != null)
				termsEnums.add(new MultiTermsEnum.TermsEnumIndex(termsEnum, i));
		}

		if (termsEnums.size() > 0)
			return (new MultiTermsEnum(subSlices)).reset((MultiTermsEnum.TermsEnumIndex[])termsEnums.toArray(MultiTermsEnum.TermsEnumIndex.EMPTY_ARRAY));
		else
			return TermsEnum.EMPTY;
	}

	public long size()
	{
		return -1L;
	}

	public long getSumTotalTermFreq()
		throws IOException
	{
		long sum = 0L;
		Terms arr$[] = subs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Terms terms = arr$[i$];
			long v = terms.getSumTotalTermFreq();
			if (v == -1L)
				return -1L;
			sum += v;
		}

		return sum;
	}

	public long getSumDocFreq()
		throws IOException
	{
		long sum = 0L;
		Terms arr$[] = subs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Terms terms = arr$[i$];
			long v = terms.getSumDocFreq();
			if (v == -1L)
				return -1L;
			sum += v;
		}

		return sum;
	}

	public int getDocCount()
		throws IOException
	{
		int sum = 0;
		Terms arr$[] = subs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Terms terms = arr$[i$];
			int v = terms.getDocCount();
			if (v == -1)
				return -1;
			sum += v;
		}

		return sum;
	}

	public Comparator getComparator()
	{
		return termComp;
	}

	public boolean hasOffsets()
	{
		return hasOffsets;
	}

	public boolean hasPositions()
	{
		return hasPositions;
	}

	public boolean hasPayloads()
	{
		return hasPayloads;
	}

}
