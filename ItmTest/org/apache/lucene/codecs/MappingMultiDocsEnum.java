// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MappingMultiDocsEnum.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.*;

public final class MappingMultiDocsEnum extends DocsEnum
{

	private org.apache.lucene.index.MultiDocsEnum.EnumWithSlice subs[];
	int numSubs;
	int upto;
	org.apache.lucene.index.MergeState.DocMap currentMap;
	DocsEnum current;
	int currentBase;
	int doc;
	private MergeState mergeState;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/MappingMultiDocsEnum.desiredAssertionStatus();

	public MappingMultiDocsEnum()
	{
		doc = -1;
	}

	MappingMultiDocsEnum reset(MultiDocsEnum docsEnum)
	{
		numSubs = docsEnum.getNumSubs();
		subs = docsEnum.getSubs();
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

	public org.apache.lucene.index.MultiDocsEnum.EnumWithSlice[] getSubs()
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
				current = subs[upto].docsEnum;
				currentBase = mergeState.docBase[reader];
				currentMap = mergeState.docMaps[reader];
				if (!$assertionsDisabled && currentMap.maxDoc() != subs[upto].slice.length)
					throw new AssertionError((new StringBuilder()).append("readerIndex=").append(reader).append(" subs.len=").append(subs.length).append(" len1=").append(currentMap.maxDoc()).append(" vs ").append(subs[upto].slice.length).toString());
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

}
