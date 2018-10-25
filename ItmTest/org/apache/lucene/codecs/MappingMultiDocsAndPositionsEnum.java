// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MappingMultiDocsAndPositionsEnum.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;

public final class MappingMultiDocsAndPositionsEnum extends DocsAndPositionsEnum
{

	private org.apache.lucene.index.MultiDocsAndPositionsEnum.EnumWithSlice subs[];
	int numSubs;
	int upto;
	org.apache.lucene.index.MergeState.DocMap currentMap;
	DocsAndPositionsEnum current;
	int currentBase;
	int doc;
	private MergeState mergeState;

	public MappingMultiDocsAndPositionsEnum()
	{
		doc = -1;
	}

	MappingMultiDocsAndPositionsEnum reset(MultiDocsAndPositionsEnum postingsEnum)
	{
		numSubs = postingsEnum.getNumSubs();
		subs = postingsEnum.getSubs();
		upto = -1;
		current = null;
		return this;
	}

	public void setMergeState(MergeState mergeState)
	{
		this.mergeState = mergeState;
	}

	public int getNumSubs()
	{
		return numSubs;
	}

	public org.apache.lucene.index.MultiDocsAndPositionsEnum.EnumWithSlice[] getSubs()
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
	{
		throw new UnsupportedOperationException();
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
				int reader = subs[upto].slice.readerIndex;
				current = subs[upto].docsAndPositionsEnum;
				currentBase = mergeState.docBase[reader];
				currentMap = mergeState.docMaps[reader];
			}
			int doc = current.nextDoc();
			if (doc != 0x7fffffff)
			{
				doc = currentMap.get(doc);
				if (doc != -1)
					return this.doc = currentBase + doc;
			} else
			{
				current = null;
			}
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
}
