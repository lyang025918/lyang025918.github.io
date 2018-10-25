// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiDocsAndPositionsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			DocsAndPositionsEnum, ReaderSlice, MultiTermsEnum

public final class MultiDocsAndPositionsEnum extends DocsAndPositionsEnum
{
	public static final class EnumWithSlice
	{

		public DocsAndPositionsEnum docsAndPositionsEnum;
		public ReaderSlice slice;

		public String toString()
		{
			return (new StringBuilder()).append(slice.toString()).append(":").append(docsAndPositionsEnum).toString();
		}

		EnumWithSlice()
		{
		}
	}


	private final MultiTermsEnum parent;
	final DocsAndPositionsEnum subDocsAndPositionsEnum[];
	private EnumWithSlice subs[];
	int numSubs;
	int upto;
	DocsAndPositionsEnum current;
	int currentBase;
	int doc;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiDocsAndPositionsEnum.desiredAssertionStatus();

	public MultiDocsAndPositionsEnum(MultiTermsEnum parent, int subReaderCount)
	{
		doc = -1;
		this.parent = parent;
		subDocsAndPositionsEnum = new DocsAndPositionsEnum[subReaderCount];
	}

	public boolean canReuse(MultiTermsEnum parent)
	{
		return this.parent == parent;
	}

	public MultiDocsAndPositionsEnum reset(EnumWithSlice subs[], int numSubs)
	{
		this.numSubs = numSubs;
		this.subs = new EnumWithSlice[subs.length];
		for (int i = 0; i < subs.length; i++)
		{
			this.subs[i] = new EnumWithSlice();
			this.subs[i].docsAndPositionsEnum = subs[i].docsAndPositionsEnum;
			this.subs[i].slice = subs[i].slice;
		}

		upto = -1;
		doc = -1;
		current = null;
		return this;
	}

	public int getNumSubs()
	{
		return numSubs;
	}

	public EnumWithSlice[] getSubs()
	{
		return subs;
	}

	public int freq()
		throws IOException
	{
		if (!$assertionsDisabled && current == null)
			throw new AssertionError();
		else
			return current.freq();
	}

	public int docID()
	{
		return doc;
	}

	public int advance(int target)
		throws IOException
	{
		do
		{
			while (current != null) 
			{
				int doc = current.advance(target - currentBase);
				if (doc == 0x7fffffff)
					current = null;
				else
					return this.doc = doc + currentBase;
			}
			if (upto == numSubs - 1)
				return this.doc = 0x7fffffff;
			upto++;
			current = subs[upto].docsAndPositionsEnum;
			currentBase = subs[upto].slice.start;
		} while (true);
	}

	public int nextDoc()
		throws IOException
	{
		do
		{
			if (current == null)
			{
				if (upto == numSubs - 1)
					return this.doc = 0x7fffffff;
				upto++;
				current = subs[upto].docsAndPositionsEnum;
				currentBase = subs[upto].slice.start;
			}
			int doc = current.nextDoc();
			if (doc != 0x7fffffff)
				return this.doc = currentBase + doc;
			current = null;
		} while (true);
	}

	public int nextPosition()
		throws IOException
	{
		return current.nextPosition();
	}

	public int startOffset()
		throws IOException
	{
		return current.startOffset();
	}

	public int endOffset()
		throws IOException
	{
		return current.endOffset();
	}

	public BytesRef getPayload()
		throws IOException
	{
		return current.getPayload();
	}

	public String toString()
	{
		return (new StringBuilder()).append("MultiDocsAndPositionsEnum(").append(Arrays.toString(getSubs())).append(")").toString();
	}

}
