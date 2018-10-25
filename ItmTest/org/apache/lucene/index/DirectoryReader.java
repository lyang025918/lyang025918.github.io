// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DirectoryReader.java

package org.apache.lucene.index;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.Directory;

// Referenced classes of package org.apache.lucene.index:
//			BaseCompositeReader, SegmentInfos, StandardDirectoryReader, IndexWriter, 
//			IndexCommit, AtomicReader

public abstract class DirectoryReader extends BaseCompositeReader
{

	public static final int DEFAULT_TERMS_INDEX_DIVISOR = 1;
	protected final Directory directory;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/DirectoryReader.desiredAssertionStatus();

	public static DirectoryReader open(Directory directory)
		throws IOException
	{
		return StandardDirectoryReader.open(directory, null, 1);
	}

	public static DirectoryReader open(Directory directory, int termInfosIndexDivisor)
		throws IOException
	{
		return StandardDirectoryReader.open(directory, null, termInfosIndexDivisor);
	}

	public static DirectoryReader open(IndexWriter writer, boolean applyAllDeletes)
		throws IOException
	{
		return writer.getReader(applyAllDeletes);
	}

	public static DirectoryReader open(IndexCommit commit)
		throws IOException
	{
		return StandardDirectoryReader.open(commit.getDirectory(), commit, 1);
	}

	public static DirectoryReader open(IndexCommit commit, int termInfosIndexDivisor)
		throws IOException
	{
		return StandardDirectoryReader.open(commit.getDirectory(), commit, termInfosIndexDivisor);
	}

	public static DirectoryReader openIfChanged(DirectoryReader oldReader)
		throws IOException
	{
		DirectoryReader newReader = oldReader.doOpenIfChanged();
		if (!$assertionsDisabled && newReader == oldReader)
			throw new AssertionError();
		else
			return newReader;
	}

	public static DirectoryReader openIfChanged(DirectoryReader oldReader, IndexCommit commit)
		throws IOException
	{
		DirectoryReader newReader = oldReader.doOpenIfChanged(commit);
		if (!$assertionsDisabled && newReader == oldReader)
			throw new AssertionError();
		else
			return newReader;
	}

	public static DirectoryReader openIfChanged(DirectoryReader oldReader, IndexWriter writer, boolean applyAllDeletes)
		throws IOException
	{
		DirectoryReader newReader = oldReader.doOpenIfChanged(writer, applyAllDeletes);
		if (!$assertionsDisabled && newReader == oldReader)
			throw new AssertionError();
		else
			return newReader;
	}

	public static List listCommits(Directory dir)
		throws IOException
	{
		String files[] = dir.listAll();
		List commits = new ArrayList();
		SegmentInfos latest = new SegmentInfos();
		latest.read(dir);
		long currentGen = latest.getGeneration();
		commits.add(new StandardDirectoryReader.ReaderCommit(latest, dir));
		for (int i = 0; i < files.length; i++)
		{
			String fileName = files[i];
			if (!fileName.startsWith("segments") || fileName.equals("segments.gen") || SegmentInfos.generationFromSegmentsFileName(fileName) >= currentGen)
				continue;
			SegmentInfos sis = new SegmentInfos();
			try
			{
				sis.read(dir, fileName);
			}
			catch (FileNotFoundException fnfe)
			{
				sis = null;
			}
			if (sis != null)
				commits.add(new StandardDirectoryReader.ReaderCommit(sis, dir));
		}

		Collections.sort(commits);
		return commits;
	}

	public static boolean indexExists(Directory directory)
	{
		(new SegmentInfos()).read(directory);
		return true;
		IOException ioe;
		ioe;
		return false;
	}

	protected DirectoryReader(Directory directory, AtomicReader segmentReaders[])
	{
		super(segmentReaders);
		this.directory = directory;
	}

	public final Directory directory()
	{
		return directory;
	}

	protected abstract DirectoryReader doOpenIfChanged()
		throws IOException;

	protected abstract DirectoryReader doOpenIfChanged(IndexCommit indexcommit)
		throws IOException;

	protected abstract DirectoryReader doOpenIfChanged(IndexWriter indexwriter, boolean flag)
		throws IOException;

	public abstract long getVersion();

	public abstract boolean isCurrent()
		throws IOException;

	public abstract IndexCommit getIndexCommit()
		throws IOException;

}
