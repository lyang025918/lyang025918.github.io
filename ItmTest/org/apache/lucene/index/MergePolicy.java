// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MergePolicy.java

package org.apache.lucene.index;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MergeInfo;
import org.apache.lucene.util.SetOnce;

// Referenced classes of package org.apache.lucene.index:
//			IndexWriter, SegmentInfos, SegmentInfoPerCommit, SegmentInfo

public abstract class MergePolicy
	implements Closeable, Cloneable
{
	public static class MergeAbortedException extends IOException
	{

		public MergeAbortedException()
		{
			super("merge is aborted");
		}

		public MergeAbortedException(String message)
		{
			super(message);
		}
	}

	public static class MergeException extends RuntimeException
	{

		private Directory dir;

		public Directory getDirectory()
		{
			return dir;
		}

		public MergeException(String message, Directory dir)
		{
			super(message);
			this.dir = dir;
		}

		public MergeException(Throwable exc, Directory dir)
		{
			super(exc);
			this.dir = dir;
		}
	}

	public static class MergeSpecification
	{

		public final List merges = new ArrayList();

		public void add(OneMerge merge)
		{
			merges.add(merge);
		}

		public String segString(Directory dir)
		{
			StringBuilder b = new StringBuilder();
			b.append("MergeSpec:\n");
			int count = merges.size();
			for (int i = 0; i < count; i++)
				b.append("  ").append(1 + i).append(": ").append(((OneMerge)merges.get(i)).segString(dir));

			return b.toString();
		}

		public MergeSpecification()
		{
		}
	}

	public static class OneMerge
	{

		SegmentInfoPerCommit info;
		boolean registerDone;
		long mergeGen;
		boolean isExternal;
		int maxNumSegments;
		public long estimatedMergeBytes;
		List readers;
		public final List segments;
		public final int totalDocCount;
		boolean aborted;
		Throwable error;
		boolean paused;

		synchronized void setException(Throwable error)
		{
			this.error = error;
		}

		synchronized Throwable getException()
		{
			return error;
		}

		synchronized void abort()
		{
			aborted = true;
			notifyAll();
		}

		synchronized boolean isAborted()
		{
			return aborted;
		}

		public synchronized void checkAborted(Directory dir)
			throws MergeAbortedException
		{
			if (aborted)
				throw new MergeAbortedException((new StringBuilder()).append("merge is aborted: ").append(segString(dir)).toString());
			while (paused) 
			{
				try
				{
					wait(1000L);
				}
				catch (InterruptedException ie)
				{
					throw new RuntimeException(ie);
				}
				if (aborted)
					throw new MergeAbortedException((new StringBuilder()).append("merge is aborted: ").append(segString(dir)).toString());
			}
		}

		public synchronized void setPause(boolean paused)
		{
			this.paused = paused;
			if (!paused)
				notifyAll();
		}

		public synchronized boolean getPause()
		{
			return paused;
		}

		public String segString(Directory dir)
		{
			StringBuilder b = new StringBuilder();
			int numSegments = segments.size();
			for (int i = 0; i < numSegments; i++)
			{
				if (i > 0)
					b.append(' ');
				b.append(((SegmentInfoPerCommit)segments.get(i)).toString(dir, 0));
			}

			if (info != null)
				b.append(" into ").append(info.info.name);
			if (maxNumSegments != -1)
				b.append((new StringBuilder()).append(" [maxNumSegments=").append(maxNumSegments).append("]").toString());
			if (aborted)
				b.append(" [ABORTED]");
			return b.toString();
		}

		public long totalBytesSize()
			throws IOException
		{
			long total = 0L;
			for (Iterator i$ = segments.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
				total += info.info.sizeInBytes();
			}

			return total;
		}

		public int totalNumDocs()
			throws IOException
		{
			int total = 0;
			for (Iterator i$ = segments.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
				total += info.info.getDocCount();
			}

			return total;
		}

		public MergeInfo getMergeInfo()
		{
			return new MergeInfo(totalDocCount, estimatedMergeBytes, isExternal, maxNumSegments);
		}

		public OneMerge(List segments)
		{
			maxNumSegments = -1;
			if (0 == segments.size())
				throw new RuntimeException("segments must include at least one segment");
			this.segments = new ArrayList(segments);
			int count = 0;
			for (Iterator i$ = segments.iterator(); i$.hasNext();)
			{
				SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
				count += info.info.getDocCount();
			}

			totalDocCount = count;
		}
	}


	protected SetOnce writer;

	public MergePolicy clone()
	{
		MergePolicy clone;
		try
		{
			clone = (MergePolicy)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
		clone.writer = new SetOnce();
		return clone;
	}

	public MergePolicy()
	{
		writer = new SetOnce();
	}

	public void setIndexWriter(IndexWriter writer)
	{
		this.writer.set(writer);
	}

	public abstract MergeSpecification findMerges(SegmentInfos segmentinfos)
		throws IOException;

	public abstract MergeSpecification findForcedMerges(SegmentInfos segmentinfos, int i, Map map)
		throws IOException;

	public abstract MergeSpecification findForcedDeletesMerges(SegmentInfos segmentinfos)
		throws IOException;

	public abstract void close();

	public abstract boolean useCompoundFile(SegmentInfos segmentinfos, SegmentInfoPerCommit segmentinfopercommit)
		throws IOException;

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
