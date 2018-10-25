// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogByteSizeMergePolicy.java

package org.apache.lucene.index;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.index:
//			LogMergePolicy, SegmentInfoPerCommit

public class LogByteSizeMergePolicy extends LogMergePolicy
{

	public static final double DEFAULT_MIN_MERGE_MB = 1.6000000000000001D;
	public static final double DEFAULT_MAX_MERGE_MB = 2048D;
	public static final double DEFAULT_MAX_MERGE_MB_FOR_FORCED_MERGE = 9.2233720368547758E+018D;

	public LogByteSizeMergePolicy()
	{
		minMergeSize = 0x199999L;
		maxMergeSize = 0x80000000L;
		maxMergeSizeForForcedMerge = 0x7fffffffffffffffL;
	}

	protected long size(SegmentInfoPerCommit info)
		throws IOException
	{
		return sizeBytes(info);
	}

	public void setMaxMergeMB(double mb)
	{
		maxMergeSize = (long)(mb * 1024D * 1024D);
	}

	public double getMaxMergeMB()
	{
		return (double)maxMergeSize / 1024D / 1024D;
	}

	public void setMaxMergeMBForForcedMerge(double mb)
	{
		maxMergeSizeForForcedMerge = (long)(mb * 1024D * 1024D);
	}

	public double getMaxMergeMBForForcedMerge()
	{
		return (double)maxMergeSizeForForcedMerge / 1024D / 1024D;
	}

	public void setMinMergeMB(double mb)
	{
		minMergeSize = (long)(mb * 1024D * 1024D);
	}

	public double getMinMergeMB()
	{
		return (double)minMergeSize / 1024D / 1024D;
	}
}
