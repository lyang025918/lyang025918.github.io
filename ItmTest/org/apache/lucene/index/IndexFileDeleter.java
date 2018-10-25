// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexFileDeleter.java

package org.apache.lucene.index;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NoSuchDirectoryException;
import org.apache.lucene.util.CollectionUtil;
import org.apache.lucene.util.InfoStream;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfos, CorruptIndexException, IndexDeletionPolicy, IndexWriter, 
//			IndexFileNames, IndexCommit

final class IndexFileDeleter
{
	private static final class CommitPoint extends IndexCommit
	{

		Collection files;
		String segmentsFileName;
		boolean deleted;
		Directory directory;
		Collection commitsToDelete;
		long generation;
		final Map userData;
		private final int segmentCount;

		public String toString()
		{
			return (new StringBuilder()).append("IndexFileDeleter.CommitPoint(").append(segmentsFileName).append(")").toString();
		}

		public int getSegmentCount()
		{
			return segmentCount;
		}

		public String getSegmentsFileName()
		{
			return segmentsFileName;
		}

		public Collection getFileNames()
		{
			return files;
		}

		public Directory getDirectory()
		{
			return directory;
		}

		public long getGeneration()
		{
			return generation;
		}

		public Map getUserData()
		{
			return userData;
		}

		public void delete()
		{
			if (!deleted)
			{
				deleted = true;
				commitsToDelete.add(this);
			}
		}

		public boolean isDeleted()
		{
			return deleted;
		}

		public CommitPoint(Collection commitsToDelete, Directory directory, SegmentInfos segmentInfos)
			throws IOException
		{
			this.directory = directory;
			this.commitsToDelete = commitsToDelete;
			userData = segmentInfos.getUserData();
			segmentsFileName = segmentInfos.getSegmentsFileName();
			generation = segmentInfos.getGeneration();
			files = Collections.unmodifiableCollection(segmentInfos.files(directory, true));
			segmentCount = segmentInfos.size();
		}
	}

	private static final class RefCount
	{

		final String fileName;
		boolean initDone;
		int count;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexFileDeleter.desiredAssertionStatus();

		public int IncRef()
		{
			if (!initDone)
				initDone = true;
			else
			if (!$assertionsDisabled && count <= 0)
				throw new AssertionError((new StringBuilder()).append(Thread.currentThread().getName()).append(": RefCount is 0 pre-increment for file \"").append(fileName).append("\"").toString());
			return ++count;
		}

		public int DecRef()
		{
			if (!$assertionsDisabled && count <= 0)
				throw new AssertionError((new StringBuilder()).append(Thread.currentThread().getName()).append(": RefCount is 0 pre-decrement for file \"").append(fileName).append("\"").toString());
			else
				return --count;
		}


		RefCount(String fileName)
		{
			this.fileName = fileName;
		}
	}


	private List deletable;
	private Map refCounts;
	private List commits;
	private List lastFiles;
	private List commitsToDelete;
	private final InfoStream infoStream;
	private Directory directory;
	private IndexDeletionPolicy policy;
	final boolean startingCommitDeleted;
	private SegmentInfos lastSegmentInfos;
	public static boolean VERBOSE_REF_COUNTS = false;
	private final IndexWriter writer;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/IndexFileDeleter.desiredAssertionStatus();

	private boolean locked()
	{
		return writer == null || Thread.holdsLock(writer);
	}

	public IndexFileDeleter(Directory directory, IndexDeletionPolicy policy, SegmentInfos segmentInfos, InfoStream infoStream, IndexWriter writer)
		throws IOException
	{
		refCounts = new HashMap();
		commits = new ArrayList();
		lastFiles = new ArrayList();
		commitsToDelete = new ArrayList();
		this.infoStream = infoStream;
		this.writer = writer;
		String currentSegmentsFile = segmentInfos.getSegmentsFileName();
		if (infoStream.isEnabled("IFD"))
			infoStream.message("IFD", (new StringBuilder()).append("init: current segments file is \"").append(currentSegmentsFile).append("\"; deletionPolicy=").append(policy).toString());
		this.policy = policy;
		this.directory = directory;
		long currentGen = segmentInfos.getGeneration();
		CommitPoint currentCommitPoint = null;
		String files[] = null;
		try
		{
			files = directory.listAll();
		}
		catch (NoSuchDirectoryException e)
		{
			files = new String[0];
		}
		if (currentSegmentsFile != null)
		{
			Matcher m = IndexFileNames.CODEC_FILE_PATTERN.matcher("");
			String arr$[] = files;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String fileName = arr$[i$];
				m.reset(fileName);
				if (fileName.endsWith("write.lock") || fileName.equals("segments.gen") || !m.matches() && !fileName.startsWith("segments"))
					continue;
				getRefCount(fileName);
				if (!fileName.startsWith("segments"))
					continue;
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("init: load commit \"").append(fileName).append("\"").toString());
				SegmentInfos sis = new SegmentInfos();
				try
				{
					sis.read(directory, fileName);
				}
				catch (FileNotFoundException e)
				{
					if (infoStream.isEnabled("IFD"))
						infoStream.message("IFD", (new StringBuilder()).append("init: hit FileNotFoundException when loading commit \"").append(fileName).append("\"; skipping this commit point").toString());
					sis = null;
				}
				catch (IOException e)
				{
					if (SegmentInfos.generationFromSegmentsFileName(fileName) <= currentGen && directory.fileLength(fileName) > 0L)
						throw e;
					sis = null;
				}
				if (sis == null)
					continue;
				CommitPoint commitPoint = new CommitPoint(commitsToDelete, directory, sis);
				if (sis.getGeneration() == segmentInfos.getGeneration())
					currentCommitPoint = commitPoint;
				commits.add(commitPoint);
				incRef(sis, true);
				if (lastSegmentInfos == null || sis.getGeneration() > lastSegmentInfos.getGeneration())
					lastSegmentInfos = sis;
			}

		}
		if (currentCommitPoint == null && currentSegmentsFile != null)
		{
			SegmentInfos sis = new SegmentInfos();
			try
			{
				sis.read(directory, currentSegmentsFile);
			}
			catch (IOException e)
			{
				throw new CorruptIndexException("failed to locate current segments_N file");
			}
			if (infoStream.isEnabled("IFD"))
				infoStream.message("IFD", (new StringBuilder()).append("forced open of current segments file ").append(segmentInfos.getSegmentsFileName()).toString());
			currentCommitPoint = new CommitPoint(commitsToDelete, directory, sis);
			commits.add(currentCommitPoint);
			incRef(sis, true);
		}
		CollectionUtil.mergeSort(commits);
		Iterator i$ = refCounts.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
			RefCount rc = (RefCount)entry.getValue();
			String fileName = (String)entry.getKey();
			if (0 == rc.count)
			{
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("init: removing unreferenced file \"").append(fileName).append("\"").toString());
				deleteFile(fileName);
			}
		} while (true);
		if (currentSegmentsFile != null)
			policy.onInit(commits);
		checkpoint(segmentInfos, false);
		startingCommitDeleted = currentCommitPoint != null ? currentCommitPoint.isDeleted() : false;
		deleteCommits();
	}

	public SegmentInfos getLastSegmentInfos()
	{
		return lastSegmentInfos;
	}

	private void deleteCommits()
		throws IOException
	{
		int size = commitsToDelete.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
			{
				CommitPoint commit = (CommitPoint)commitsToDelete.get(i);
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("deleteCommits: now decRef commit \"").append(commit.getSegmentsFileName()).append("\"").toString());
				String file;
				for (Iterator i$ = commit.files.iterator(); i$.hasNext(); decRef(file))
					file = (String)i$.next();

			}

			commitsToDelete.clear();
			size = commits.size();
			int readFrom = 0;
			int writeTo = 0;
			for (; readFrom < size; readFrom++)
			{
				CommitPoint commit = (CommitPoint)commits.get(readFrom);
				if (commit.deleted)
					continue;
				if (writeTo != readFrom)
					commits.set(writeTo, commits.get(readFrom));
				writeTo++;
			}

			for (; size > writeTo; size--)
				commits.remove(size - 1);

		}
	}

	public void refresh(String segmentName)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String files[] = directory.listAll();
		String segmentPrefix1;
		String segmentPrefix2;
		if (segmentName != null)
		{
			segmentPrefix1 = (new StringBuilder()).append(segmentName).append(".").toString();
			segmentPrefix2 = (new StringBuilder()).append(segmentName).append("_").toString();
		} else
		{
			segmentPrefix1 = null;
			segmentPrefix2 = null;
		}
		Matcher m = IndexFileNames.CODEC_FILE_PATTERN.matcher("");
		for (int i = 0; i < files.length; i++)
		{
			String fileName = files[i];
			m.reset(fileName);
			if (segmentName != null && !fileName.startsWith(segmentPrefix1) && !fileName.startsWith(segmentPrefix2) || fileName.endsWith("write.lock") || refCounts.containsKey(fileName) || fileName.equals("segments.gen") || !m.matches() && !fileName.startsWith("segments"))
				continue;
			if (infoStream.isEnabled("IFD"))
				infoStream.message("IFD", (new StringBuilder()).append("refresh [prefix=").append(segmentName).append("]: removing newly created unreferenced file \"").append(fileName).append("\"").toString());
			deleteFile(fileName);
		}

	}

	public void refresh()
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
		{
			throw new AssertionError();
		} else
		{
			deletable = null;
			refresh(null);
			return;
		}
	}

	public void close()
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		int size = lastFiles.size();
		if (size > 0)
		{
			for (int i = 0; i < size; i++)
				decRef((Collection)lastFiles.get(i));

			lastFiles.clear();
		}
		deletePendingFiles();
	}

	void revisitPolicy()
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		if (infoStream.isEnabled("IFD"))
			infoStream.message("IFD", "now revisitPolicy");
		if (commits.size() > 0)
		{
			policy.onCommit(commits);
			deleteCommits();
		}
	}

	public void deletePendingFiles()
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		if (deletable != null)
		{
			List oldDeletable = deletable;
			deletable = null;
			int size = oldDeletable.size();
			for (int i = 0; i < size; i++)
			{
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("delete pending file ").append((String)oldDeletable.get(i)).toString());
				deleteFile((String)oldDeletable.get(i));
			}

		}
	}

	public void checkpoint(SegmentInfos segmentInfos, boolean isCommit)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		if (!$assertionsDisabled && !Thread.holdsLock(writer))
			throw new AssertionError();
		if (infoStream.isEnabled("IFD"))
			infoStream.message("IFD", (new StringBuilder()).append("now checkpoint \"").append(writer.segString(writer.toLiveInfos(segmentInfos))).append("\" [").append(segmentInfos.size()).append(" segments ").append("; isCommit = ").append(isCommit).append("]").toString());
		deletePendingFiles();
		incRef(segmentInfos, isCommit);
		if (isCommit)
		{
			commits.add(new CommitPoint(commitsToDelete, directory, segmentInfos));
			policy.onCommit(commits);
			deleteCommits();
		} else
		{
			Collection lastFile;
			for (Iterator i$ = lastFiles.iterator(); i$.hasNext(); decRef(lastFile))
				lastFile = (Collection)i$.next();

			lastFiles.clear();
			lastFiles.add(segmentInfos.files(directory, false));
		}
	}

	void incRef(SegmentInfos segmentInfos, boolean isCommit)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String fileName;
		for (Iterator i$ = segmentInfos.files(directory, isCommit).iterator(); i$.hasNext(); incRef(fileName))
			fileName = (String)i$.next();

	}

	void incRef(Collection files)
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String file;
		for (Iterator i$ = files.iterator(); i$.hasNext(); incRef(file))
			file = (String)i$.next();

	}

	void incRef(String fileName)
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		RefCount rc = getRefCount(fileName);
		if (infoStream.isEnabled("IFD") && VERBOSE_REF_COUNTS)
			infoStream.message("IFD", (new StringBuilder()).append("  IncRef \"").append(fileName).append("\": pre-incr count is ").append(rc.count).toString());
		rc.IncRef();
	}

	void decRef(Collection files)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String file;
		for (Iterator i$ = files.iterator(); i$.hasNext(); decRef(file))
			file = (String)i$.next();

	}

	void decRef(String fileName)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		RefCount rc = getRefCount(fileName);
		if (infoStream.isEnabled("IFD") && VERBOSE_REF_COUNTS)
			infoStream.message("IFD", (new StringBuilder()).append("  DecRef \"").append(fileName).append("\": pre-decr count is ").append(rc.count).toString());
		if (0 == rc.DecRef())
		{
			deleteFile(fileName);
			refCounts.remove(fileName);
		}
	}

	void decRef(SegmentInfos segmentInfos)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String file;
		for (Iterator i$ = segmentInfos.files(directory, false).iterator(); i$.hasNext(); decRef(file))
			file = (String)i$.next();

	}

	public boolean exists(String fileName)
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		if (!refCounts.containsKey(fileName))
			return false;
		else
			return getRefCount(fileName).count > 0;
	}

	private RefCount getRefCount(String fileName)
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		RefCount rc;
		if (!refCounts.containsKey(fileName))
		{
			rc = new RefCount(fileName);
			refCounts.put(fileName, rc);
		} else
		{
			rc = (RefCount)refCounts.get(fileName);
		}
		return rc;
	}

	void deleteFiles(List files)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		String file;
		for (Iterator i$ = files.iterator(); i$.hasNext(); deleteFile(file))
			file = (String)i$.next();

	}

	void deleteNewFiles(Collection files)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		Iterator i$ = files.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			String fileName = (String)i$.next();
			if (!refCounts.containsKey(fileName) || ((RefCount)refCounts.get(fileName)).count == 0)
			{
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("delete new file \"").append(fileName).append("\"").toString());
				deleteFile(fileName);
			}
		} while (true);
	}

	void deleteFile(String fileName)
		throws IOException
	{
		if (!$assertionsDisabled && !locked())
			throw new AssertionError();
		try
		{
			if (infoStream.isEnabled("IFD"))
				infoStream.message("IFD", (new StringBuilder()).append("delete \"").append(fileName).append("\"").toString());
			directory.deleteFile(fileName);
		}
		catch (IOException e)
		{
			if (directory.fileExists(fileName))
			{
				if (infoStream.isEnabled("IFD"))
					infoStream.message("IFD", (new StringBuilder()).append("unable to remove file \"").append(fileName).append("\": ").append(e.toString()).append("; Will re-try later.").toString());
				if (deletable == null)
					deletable = new ArrayList();
				deletable.add(fileName);
			}
		}
	}

}
