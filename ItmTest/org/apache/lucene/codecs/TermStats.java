// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermStats.java

package org.apache.lucene.codecs;


public class TermStats
{

	public final int docFreq;
	public final long totalTermFreq;

	public TermStats(int docFreq, long totalTermFreq)
	{
		this.docFreq = docFreq;
		this.totalTermFreq = totalTermFreq;
	}
}
