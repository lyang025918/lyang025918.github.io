// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoMergeScheduler.java

package org.apache.lucene.index;


// Referenced classes of package org.apache.lucene.index:
//			MergeScheduler, IndexWriter

public final class NoMergeScheduler extends MergeScheduler
{

	public static final MergeScheduler INSTANCE = new NoMergeScheduler();

	private NoMergeScheduler()
	{
	}

	public void close()
	{
	}

	public void merge(IndexWriter indexwriter)
	{
	}

}
