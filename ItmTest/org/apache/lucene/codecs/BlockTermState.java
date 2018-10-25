// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BlockTermState.java

package org.apache.lucene.codecs;

import org.apache.lucene.index.OrdTermState;
import org.apache.lucene.index.TermState;

public class BlockTermState extends OrdTermState
{

	public int docFreq;
	public long totalTermFreq;
	public int termBlockOrd;
	public long blockFilePointer;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/BlockTermState.desiredAssertionStatus();

	protected BlockTermState()
	{
	}

	public void copyFrom(TermState _other)
	{
		if (!$assertionsDisabled && !(_other instanceof BlockTermState))
		{
			throw new AssertionError((new StringBuilder()).append("can not copy from ").append(_other.getClass().getName()).toString());
		} else
		{
			BlockTermState other = (BlockTermState)_other;
			super.copyFrom(_other);
			docFreq = other.docFreq;
			totalTermFreq = other.totalTermFreq;
			termBlockOrd = other.termBlockOrd;
			blockFilePointer = other.blockFilePointer;
			return;
		}
	}

	public String toString()
	{
		return (new StringBuilder()).append("docFreq=").append(docFreq).append(" totalTermFreq=").append(totalTermFreq).append(" termBlockOrd=").append(termBlockOrd).append(" blockFP=").append(blockFilePointer).toString();
	}

}
