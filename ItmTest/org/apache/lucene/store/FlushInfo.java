// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FlushInfo.java

package org.apache.lucene.store;


public class FlushInfo
{

	public final int numDocs;
	public final long estimatedSegmentSize;

	public FlushInfo(int numDocs, long estimatedSegmentSize)
	{
		this.numDocs = numDocs;
		this.estimatedSegmentSize = estimatedSegmentSize;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (int)(estimatedSegmentSize ^ estimatedSegmentSize >>> 32);
		result = 31 * result + numDocs;
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
		FlushInfo other = (FlushInfo)obj;
		if (estimatedSegmentSize != other.estimatedSegmentSize)
			return false;
		return numDocs == other.numDocs;
	}

	public String toString()
	{
		return (new StringBuilder()).append("FlushInfo [numDocs=").append(numDocs).append(", estimatedSegmentSize=").append(estimatedSegmentSize).append("]").toString();
	}
}
