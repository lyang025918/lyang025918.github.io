// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MergeInfo.java

package org.apache.lucene.store;


public class MergeInfo
{

	public final int totalDocCount;
	public final long estimatedMergeBytes;
	public final boolean isExternal;
	public final int mergeMaxNumSegments;

	public MergeInfo(int totalDocCount, long estimatedMergeBytes, boolean isExternal, int mergeMaxNumSegments)
	{
		this.totalDocCount = totalDocCount;
		this.estimatedMergeBytes = estimatedMergeBytes;
		this.isExternal = isExternal;
		this.mergeMaxNumSegments = mergeMaxNumSegments;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (int)(estimatedMergeBytes ^ estimatedMergeBytes >>> 32);
		result = 31 * result + (isExternal ? 1231 : '\u04D5');
		result = 31 * result + mergeMaxNumSegments;
		result = 31 * result + totalDocCount;
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MergeInfo other = (MergeInfo)obj;
		if (estimatedMergeBytes != other.estimatedMergeBytes)
			return false;
		if (isExternal != other.isExternal)
			return false;
		if (mergeMaxNumSegments != other.mergeMaxNumSegments)
			return false;
		return totalDocCount == other.totalDocCount;
	}

	public String toString()
	{
		return (new StringBuilder()).append("MergeInfo [totalDocCount=").append(totalDocCount).append(", estimatedMergeBytes=").append(estimatedMergeBytes).append(", isExternal=").append(isExternal).append(", mergeMaxNumSegments=").append(mergeMaxNumSegments).append("]").toString();
	}
}
