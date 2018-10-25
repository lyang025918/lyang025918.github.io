// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoMergePolicy.java

package org.apache.lucene.index;

import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			MergePolicy, SegmentInfos, SegmentInfoPerCommit, IndexWriter

public final class NoMergePolicy extends MergePolicy
{

	public static final MergePolicy NO_COMPOUND_FILES = new NoMergePolicy(false);
	public static final MergePolicy COMPOUND_FILES = new NoMergePolicy(true);
	private final boolean useCompoundFile;

	private NoMergePolicy(boolean useCompoundFile)
	{
		this.useCompoundFile = useCompoundFile;
	}

	public void close()
	{
	}

	public MergePolicy.MergeSpecification findMerges(SegmentInfos segmentInfos)
	{
		return null;
	}

	public MergePolicy.MergeSpecification findForcedMerges(SegmentInfos segmentInfos, int maxSegmentCount, Map segmentsToMerge)
	{
		return null;
	}

	public MergePolicy.MergeSpecification findForcedDeletesMerges(SegmentInfos segmentInfos)
	{
		return null;
	}

	public boolean useCompoundFile(SegmentInfos segments, SegmentInfoPerCommit newSegment)
	{
		return useCompoundFile;
	}

	public void setIndexWriter(IndexWriter indexwriter)
	{
	}

	public String toString()
	{
		return "NoMergePolicy";
	}

}
