// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiDocsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Arrays;

// Referenced classes of package org.apache.lucene.index:
//			DocsEnum, ReaderSlice, MultiTermsEnum

public final class MultiDocsEnum extends DocsEnum
{
	public static final class EnumWithSlice
	{

		public DocsEnum docsEnum;
		public ReaderSlice slice;

		public String toString()
		{
			return (new StringBuilder()).append(slice.toString()).append(":").append(docsEnum).toString();
		}

		EnumWithSlice()
		{
		}
	}


	private final MultiTermsEnum parent;
	final DocsEnum subDocsEnum[];
	private EnumWithSlice subs[];
	int numSubs;
	int upto;
	DocsEnum current;
	int currentBase;
	int doc;

	public MultiDocsEnum(MultiTermsEnum parent, int subReaderCount)
	{
		doc = -1;
		this.parent = parent;
		subDocsEnum = new DocsEnum[subReaderCount];
	}

	MultiDocsEnum reset(EnumWithSlice subs[], int numSubs)
	{
		this.numSubs = numSubs;
		this.subs = new EnumWithSlice[subs.length];
		for (int i = 0; i < subs.length; i++)
		{
			this.subs[i] = new EnumWithSlice();
			this.subs[i].docsEnum = subs[i].docsEnum;
			this.subs[i].slice = subs[i].slice;
		}

		upto = -1;
		doc = -1;
		current = null;
		return this;
	}

	public boolean canReuse(MultiTermsEnum parent)
	{
		return this.parent == parent;
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
			current = subs[upto].docsEnum;
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
				current = subs[upto].docsEnum;
				currentBase = subs[upto].slice.start;
			}
			int doc = current.nextDoc();
			if (doc != 0x7fffffff)
				return this.doc = currentBase + doc;
			current = null;
		} while (true);
	}

	public String toString()
	{
		return (new StringBuilder()).append("MultiDocsEnum(").append(Arrays.toString(getSubs())).append(")").toString();
	}
}
