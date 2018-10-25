// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentInfoPerCommit.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.LiveDocsFormat;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfo

public class SegmentInfoPerCommit
{

	public final SegmentInfo info;
	private int delCount;
	private long delGen;
	private volatile long sizeInBytes;
	private long bufferedDeletesGen;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentInfoPerCommit.desiredAssertionStatus();

	public SegmentInfoPerCommit(SegmentInfo info, int delCount, long delGen)
	{
		sizeInBytes = -1L;
		this.info = info;
		this.delCount = delCount;
		this.delGen = delGen;
	}

	void advanceDelGen()
	{
		if (delGen == -1L)
			delGen = 1L;
		else
			delGen++;
		sizeInBytes = -1L;
	}

	public long sizeInBytes()
		throws IOException
	{
		if (sizeInBytes == -1L)
		{
			long sum = 0L;
			for (Iterator i$ = files().iterator(); i$.hasNext();)
			{
				String fileName = (String)i$.next();
				sum += info.dir.fileLength(fileName);
			}

			sizeInBytes = sum;
		}
		return sizeInBytes;
	}

	public Collection files()
		throws IOException
	{
		Collection files = new HashSet(info.files());
		info.getCodec().liveDocsFormat().files(this, files);
		return files;
	}

	long getBufferedDeletesGen()
	{
		return bufferedDeletesGen;
	}

	void setBufferedDeletesGen(long v)
	{
		bufferedDeletesGen = v;
		sizeInBytes = -1L;
	}

	void clearDelGen()
	{
		delGen = -1L;
		sizeInBytes = -1L;
	}

	public void setDelGen(long delGen)
	{
		this.delGen = delGen;
		sizeInBytes = -1L;
	}

	public boolean hasDeletions()
	{
		return delGen != -1L;
	}

	public long getNextDelGen()
	{
		if (delGen == -1L)
			return 1L;
		else
			return delGen + 1L;
	}

	public long getDelGen()
	{
		return delGen;
	}

	public int getDelCount()
	{
		return delCount;
	}

	void setDelCount(int delCount)
	{
		this.delCount = delCount;
		if (!$assertionsDisabled && delCount > info.getDocCount())
			throw new AssertionError();
		else
			return;
	}

	public String toString(Directory dir, int pendingDelCount)
	{
		return info.toString(dir, delCount + pendingDelCount);
	}

	public String toString()
	{
		String s = info.toString(info.dir, delCount);
		if (delGen != -1L)
			s = (new StringBuilder()).append(s).append(":delGen=").append(delGen).toString();
		return s;
	}

	public SegmentInfoPerCommit clone()
	{
		return new SegmentInfoPerCommit(info, delCount, delGen);
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
