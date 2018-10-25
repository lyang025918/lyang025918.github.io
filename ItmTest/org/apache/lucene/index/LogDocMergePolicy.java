// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogDocMergePolicy.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			LogMergePolicy, SegmentInfoPerCommit

public class LogDocMergePolicy extends LogMergePolicy
{

	public static final int DEFAULT_MIN_MERGE_DOCS = 1000;

	public LogDocMergePolicy()
	{
		minMergeSize = 1000L;
		maxMergeSize = 0x7fffffffffffffffL;
		maxMergeSizeForForcedMerge = 0x7fffffffffffffffL;
	}

	protected long size(SegmentInfoPerCommit info)
		throws IOException
	{
		return sizeDocs(info);
	}

	public void setMinMergeDocs(int minMergeDocs)
	{
		minMergeSize = minMergeDocs;
	}

	public int getMinMergeDocs()
	{
		return (int)minMergeSize;
	}
}
