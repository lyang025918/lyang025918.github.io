// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexCommit.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.apache.lucene.store.Directory;

public abstract class IndexCommit
	implements Comparable
{

	public abstract String getSegmentsFileName();

	public abstract Collection getFileNames()
		throws IOException;

	public abstract Directory getDirectory();

	public abstract void delete();

	public abstract boolean isDeleted();

	public abstract int getSegmentCount();

	protected IndexCommit()
	{
	}

	public boolean equals(Object other)
	{
		if (other instanceof IndexCommit)
		{
			IndexCommit otherCommit = (IndexCommit)other;
			return otherCommit.getDirectory() == getDirectory() && otherCommit.getGeneration() == getGeneration();
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return getDirectory().hashCode() + Long.valueOf(getGeneration()).hashCode();
	}

	public abstract long getGeneration();

	public abstract Map getUserData()
		throws IOException;

	public int compareTo(IndexCommit commit)
	{
		if (getDirectory() != commit.getDirectory())
			throw new UnsupportedOperationException("cannot compare IndexCommits from different Directory instances");
		long gen = getGeneration();
		long comgen = commit.getGeneration();
		if (gen < comgen)
			return -1;
		return gen <= comgen ? 0 : 1;
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((IndexCommit)x0);
	}
}
