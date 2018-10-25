// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermInfo.java

package org.apache.lucene.codecs.lucene3x;


/**
 * @deprecated Class TermInfo is deprecated
 */

class TermInfo
{

	public int docFreq;
	public long freqPointer;
	public long proxPointer;
	public int skipOffset;

	public TermInfo()
	{
		docFreq = 0;
		freqPointer = 0L;
		proxPointer = 0L;
	}

	public TermInfo(int df, long fp, long pp)
	{
		docFreq = 0;
		freqPointer = 0L;
		proxPointer = 0L;
		docFreq = df;
		freqPointer = fp;
		proxPointer = pp;
	}

	public TermInfo(TermInfo ti)
	{
		docFreq = 0;
		freqPointer = 0L;
		proxPointer = 0L;
		docFreq = ti.docFreq;
		freqPointer = ti.freqPointer;
		proxPointer = ti.proxPointer;
		skipOffset = ti.skipOffset;
	}

	public final void set(int docFreq, long freqPointer, long proxPointer, int skipOffset)
	{
		this.docFreq = docFreq;
		this.freqPointer = freqPointer;
		this.proxPointer = proxPointer;
		this.skipOffset = skipOffset;
	}

	public final void set(TermInfo ti)
	{
		docFreq = ti.docFreq;
		freqPointer = ti.freqPointer;
		proxPointer = ti.proxPointer;
		skipOffset = ti.skipOffset;
	}
}
