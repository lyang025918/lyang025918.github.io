// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SerialMergeScheduler.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			MergeScheduler, IndexWriter, MergePolicy

public class SerialMergeScheduler extends MergeScheduler
{

	public SerialMergeScheduler()
	{
	}

	public synchronized void merge(IndexWriter writer)
		throws IOException
	{
		do
		{
			MergePolicy.OneMerge merge = writer.getNextMerge();
			if (merge != null)
				writer.merge(merge);
			else
				return;
		} while (true);
	}

	public void close()
	{
	}
}
