// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiTermsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsEnum, MultiDocsEnum, MultiBits, BitsSlice, 
//			MultiDocsAndPositionsEnum, DocsEnum, DocsAndPositionsEnum, ReaderSlice

public final class MultiTermsEnum extends TermsEnum
{
	private static final class TermMergeQueue extends PriorityQueue
	{

		Comparator termComp;

		protected boolean lessThan(TermsEnumWithSlice termsA, TermsEnumWithSlice termsB)
		{
			int cmp = termComp.compare(termsA.current, termsB.current);
			if (cmp != 0)
				return cmp < 0;
			else
				return termsA.subSlice.start < termsB.subSlice.start;
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((TermsEnumWithSlice)x0, (TermsEnumWithSlice)x1);
		}

		TermMergeQueue(int size)
		{
			super(size);
		}
	}

	private static final class TermsEnumWithSlice
	{

		private final ReaderSlice subSlice;
		private TermsEnum terms;
		public BytesRef current;
		final int index;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiTermsEnum.desiredAssertionStatus();

		public void reset(TermsEnum terms, BytesRef term)
		{
			this.terms = terms;
			current = term;
		}

		public String toString()
		{
			return (new StringBuilder()).append(subSlice.toString()).append(":").append(terms).toString();
		}




		public TermsEnumWithSlice(int index, ReaderSlice subSlice)
		{
			this.subSlice = subSlice;
			this.index = index;
			if (!$assertionsDisabled && subSlice.length < 0)
				throw new AssertionError((new StringBuilder()).append("length=").append(subSlice.length).toString());
			else
				return;
		}
	}

	static class TermsEnumIndex
	{

		public static final TermsEnumIndex EMPTY_ARRAY[] = new TermsEnumIndex[0];
		final int subIndex;
		final TermsEnum termsEnum;


		public TermsEnumIndex(TermsEnum termsEnum, int subIndex)
		{
			this.termsEnum = termsEnum;
			this.subIndex = subIndex;
		}
	}


	private final TermMergeQueue queue;
	private final TermsEnumWithSlice subs[];
	private final TermsEnumWithSlice currentSubs[];
	private final TermsEnumWithSlice top[];
	private final MultiDocsEnum.EnumWithSlice subDocs[];
	private final MultiDocsAndPositionsEnum.EnumWithSlice subDocsAndPositions[];
	private BytesRef lastSeek;
	private boolean lastSeekExact;
	private final BytesRef lastSeekScratch = new BytesRef();
	private int numTop;
	private int numSubs;
	private BytesRef current;
	private Comparator termComp;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiTermsEnum.desiredAssertionStatus();

	public int getMatchCount()
	{
		return numTop;
	}

	public TermsEnumWithSlice[] getMatchArray()
	{
		return top;
	}

	public MultiTermsEnum(ReaderSlice slices[])
	{
		queue = new TermMergeQueue(slices.length);
		top = new TermsEnumWithSlice[slices.length];
		subs = new TermsEnumWithSlice[slices.length];
		subDocs = new MultiDocsEnum.EnumWithSlice[slices.length];
		subDocsAndPositions = new MultiDocsAndPositionsEnum.EnumWithSlice[slices.length];
		for (int i = 0; i < slices.length; i++)
		{
			subs[i] = new TermsEnumWithSlice(i, slices[i]);
			subDocs[i] = new MultiDocsEnum.EnumWithSlice();
			subDocs[i].slice = slices[i];
			subDocsAndPositions[i] = new MultiDocsAndPositionsEnum.EnumWithSlice();
			subDocsAndPositions[i].slice = slices[i];
		}

		currentSubs = new TermsEnumWithSlice[slices.length];
	}

	public BytesRef term()
	{
		return current;
	}

	public Comparator getComparator()
	{
		return termComp;
	}

	public TermsEnum reset(TermsEnumIndex termsEnumsIndex[])
		throws IOException
	{
		if (!$assertionsDisabled && termsEnumsIndex.length > top.length)
			throw new AssertionError();
		numSubs = 0;
		numTop = 0;
		termComp = null;
		queue.clear();
		for (int i = 0; i < termsEnumsIndex.length; i++)
		{
			TermsEnumIndex termsEnumIndex = termsEnumsIndex[i];
			if (!$assertionsDisabled && termsEnumIndex == null)
				throw new AssertionError();
			if (termComp == null)
			{
				queue.termComp = termComp = termsEnumIndex.termsEnum.getComparator();
			} else
			{
				Comparator subTermComp = termsEnumIndex.termsEnum.getComparator();
				if (subTermComp != null && !subTermComp.equals(termComp))
					throw new IllegalStateException((new StringBuilder()).append("sub-readers have different BytesRef.Comparators: ").append(subTermComp).append(" vs ").append(termComp).append("; cannot merge").toString());
			}
			BytesRef term = termsEnumIndex.termsEnum.next();
			if (term != null)
			{
				TermsEnumWithSlice entry = subs[termsEnumIndex.subIndex];
				entry.reset(termsEnumIndex.termsEnum, term);
				queue.add(entry);
				currentSubs[numSubs++] = entry;
			}
		}

		if (queue.size() == 0)
			return TermsEnum.EMPTY;
		else
			return this;
	}

	public boolean seekExact(BytesRef term, boolean useCache)
		throws IOException
	{
		queue.clear();
		numTop = 0;
		boolean seekOpt = false;
		if (lastSeek != null && termComp.compare(lastSeek, term) <= 0)
			seekOpt = true;
		lastSeek = null;
		lastSeekExact = true;
		for (int i = 0; i < numSubs; i++)
		{
			boolean status;
			if (seekOpt)
			{
				BytesRef curTerm = currentSubs[i].current;
				if (curTerm != null)
				{
					int cmp = termComp.compare(term, curTerm);
					if (cmp == 0)
						status = true;
					else
					if (cmp < 0)
						status = false;
					else
						status = currentSubs[i].terms.seekExact(term, useCache);
				} else
				{
					status = false;
				}
			} else
			{
				status = currentSubs[i].terms.seekExact(term, useCache);
			}
			if (!status)
				continue;
			top[numTop++] = currentSubs[i];
			current = currentSubs[i].current = currentSubs[i].terms.term();
			if (!$assertionsDisabled && !term.equals(currentSubs[i].current))
				throw new AssertionError();
		}

		return numTop > 0;
	}

	public TermsEnum.SeekStatus seekCeil(BytesRef term, boolean useCache)
		throws IOException
	{
		queue.clear();
		numTop = 0;
		lastSeekExact = false;
		boolean seekOpt = false;
		if (lastSeek != null && termComp.compare(lastSeek, term) <= 0)
			seekOpt = true;
		lastSeekScratch.copyBytes(term);
		lastSeek = lastSeekScratch;
		for (int i = 0; i < numSubs; i++)
		{
			TermsEnum.SeekStatus status;
			if (seekOpt)
			{
				BytesRef curTerm = currentSubs[i].current;
				if (curTerm != null)
				{
					int cmp = termComp.compare(term, curTerm);
					if (cmp == 0)
						status = TermsEnum.SeekStatus.FOUND;
					else
					if (cmp < 0)
						status = TermsEnum.SeekStatus.NOT_FOUND;
					else
						status = currentSubs[i].terms.seekCeil(term, useCache);
				} else
				{
					status = TermsEnum.SeekStatus.END;
				}
			} else
			{
				status = currentSubs[i].terms.seekCeil(term, useCache);
			}
			if (status == TermsEnum.SeekStatus.FOUND)
			{
				top[numTop++] = currentSubs[i];
				current = currentSubs[i].current = currentSubs[i].terms.term();
				continue;
			}
			if (status == TermsEnum.SeekStatus.NOT_FOUND)
			{
				currentSubs[i].current = currentSubs[i].terms.term();
				if (!$assertionsDisabled && currentSubs[i].current == null)
					throw new AssertionError();
				queue.add(currentSubs[i]);
			} else
			{
				currentSubs[i].current = null;
			}
		}

		if (numTop > 0)
			return TermsEnum.SeekStatus.FOUND;
		if (queue.size() > 0)
		{
			pullTop();
			return TermsEnum.SeekStatus.NOT_FOUND;
		} else
		{
			return TermsEnum.SeekStatus.END;
		}
	}

	public void seekExact(long ord)
	{
		throw new UnsupportedOperationException();
	}

	public long ord()
	{
		throw new UnsupportedOperationException();
	}

	private void pullTop()
	{
		if (!$assertionsDisabled && numTop != 0)
			throw new AssertionError();
		do
			top[numTop++] = (TermsEnumWithSlice)queue.pop();
		while (queue.size() != 0 && ((TermsEnumWithSlice)queue.top()).current.bytesEquals(top[0].current));
		current = top[0].current;
	}

	private void pushTop()
		throws IOException
	{
		for (int i = 0; i < numTop; i++)
		{
			top[i].current = top[i].terms.next();
			if (top[i].current != null)
				queue.add(top[i]);
		}

		numTop = 0;
	}

	public BytesRef next()
		throws IOException
	{
		if (lastSeekExact)
		{
			TermsEnum.SeekStatus status = seekCeil(current);
			if (!$assertionsDisabled && status != TermsEnum.SeekStatus.FOUND)
				throw new AssertionError();
			lastSeekExact = false;
		}
		lastSeek = null;
		pushTop();
		if (queue.size() > 0)
			pullTop();
		else
			current = null;
		return current;
	}

	public int docFreq()
		throws IOException
	{
		int sum = 0;
		for (int i = 0; i < numTop; i++)
			sum += top[i].terms.docFreq();

		return sum;
	}

	public long totalTermFreq()
		throws IOException
	{
		long sum = 0L;
		for (int i = 0; i < numTop; i++)
		{
			long v = top[i].terms.totalTermFreq();
			if (v == -1L)
				return v;
			sum += v;
		}

		return sum;
	}

	public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
		throws IOException
	{
		MultiDocsEnum docsEnum;
		if (reuse != null && (reuse instanceof MultiDocsEnum))
		{
			docsEnum = (MultiDocsEnum)reuse;
			if (!docsEnum.canReuse(this))
				docsEnum = new MultiDocsEnum(this, subs.length);
		} else
		{
			docsEnum = new MultiDocsEnum(this, subs.length);
		}
		MultiBits multiLiveDocs;
		if (liveDocs instanceof MultiBits)
			multiLiveDocs = (MultiBits)liveDocs;
		else
			multiLiveDocs = null;
		int upto = 0;
		for (int i = 0; i < numTop; i++)
		{
			TermsEnumWithSlice entry = top[i];
			Bits b;
			if (multiLiveDocs != null)
			{
				MultiBits.SubResult sub = multiLiveDocs.getMatchingSub(entry.subSlice);
				if (sub.matches)
					b = sub.result;
				else
					b = new BitsSlice(liveDocs, entry.subSlice);
			} else
			if (liveDocs != null)
				b = new BitsSlice(liveDocs, entry.subSlice);
			else
				b = null;
			if (!$assertionsDisabled && entry.index >= docsEnum.subDocsEnum.length)
				throw new AssertionError((new StringBuilder()).append(entry.index).append(" vs ").append(docsEnum.subDocsEnum.length).append("; ").append(subs.length).toString());
			DocsEnum subDocsEnum = entry.terms.docs(b, docsEnum.subDocsEnum[entry.index], flags);
			if (subDocsEnum != null)
			{
				docsEnum.subDocsEnum[entry.index] = subDocsEnum;
				subDocs[upto].docsEnum = subDocsEnum;
				subDocs[upto].slice = entry.subSlice;
				upto++;
				continue;
			}
			if (!$assertionsDisabled)
				throw new AssertionError("One of our subs cannot provide a docsenum");
		}

		if (upto == 0)
			return null;
		else
			return docsEnum.reset(subDocs, upto);
	}

	public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
		throws IOException
	{
		MultiDocsAndPositionsEnum docsAndPositionsEnum;
		if (reuse != null && (reuse instanceof MultiDocsAndPositionsEnum))
		{
			docsAndPositionsEnum = (MultiDocsAndPositionsEnum)reuse;
			if (!docsAndPositionsEnum.canReuse(this))
				docsAndPositionsEnum = new MultiDocsAndPositionsEnum(this, subs.length);
		} else
		{
			docsAndPositionsEnum = new MultiDocsAndPositionsEnum(this, subs.length);
		}
		MultiBits multiLiveDocs;
		if (liveDocs instanceof MultiBits)
			multiLiveDocs = (MultiBits)liveDocs;
		else
			multiLiveDocs = null;
		int upto = 0;
		for (int i = 0; i < numTop; i++)
		{
			TermsEnumWithSlice entry = top[i];
			Bits b;
			if (multiLiveDocs != null)
			{
				MultiBits.SubResult sub = multiLiveDocs.getMatchingSub(top[i].subSlice);
				if (sub.matches)
					b = sub.result;
				else
					b = new BitsSlice(liveDocs, top[i].subSlice);
			} else
			if (liveDocs != null)
				b = new BitsSlice(liveDocs, top[i].subSlice);
			else
				b = null;
			if (!$assertionsDisabled && entry.index >= docsAndPositionsEnum.subDocsAndPositionsEnum.length)
				throw new AssertionError((new StringBuilder()).append(entry.index).append(" vs ").append(docsAndPositionsEnum.subDocsAndPositionsEnum.length).append("; ").append(subs.length).toString());
			DocsAndPositionsEnum subPostings = entry.terms.docsAndPositions(b, docsAndPositionsEnum.subDocsAndPositionsEnum[entry.index], flags);
			if (subPostings != null)
			{
				docsAndPositionsEnum.subDocsAndPositionsEnum[entry.index] = subPostings;
				subDocsAndPositions[upto].docsAndPositionsEnum = subPostings;
				subDocsAndPositions[upto].slice = entry.subSlice;
				upto++;
				continue;
			}
			if (entry.terms.docs(b, null, 0) != null)
				return null;
		}

		if (upto == 0)
			return null;
		else
			return docsAndPositionsEnum.reset(subDocsAndPositions, upto);
	}

	public String toString()
	{
		return (new StringBuilder()).append("MultiTermsEnum(").append(Arrays.toString(subs)).append(")").toString();
	}

}
