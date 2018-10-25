// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardDirectoryReader.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.index:
//			DirectoryReader, SegmentReader, AtomicReader, SegmentInfos, 
//			IndexWriter, SegmentInfoPerCommit, ReadersAndLiveDocs, SegmentInfo, 
//			LiveIndexWriterConfig, IndexCommit

final class StandardDirectoryReader extends DirectoryReader
{
	static final class ReaderCommit extends IndexCommit
	{

		private String segmentsFileName;
		Collection files;
		Directory dir;
		long generation;
		final Map userData;
		private final int segmentCount;

		public String toString()
		{
			return (new StringBuilder()).append("DirectoryReader.ReaderCommit(").append(segmentsFileName).append(")").toString();
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
			return dir;
		}

		public long getGeneration()
		{
			return generation;
		}

		public boolean isDeleted()
		{
			return false;
		}

		public Map getUserData()
		{
			return userData;
		}

		public void delete()
		{
			throw new UnsupportedOperationException("This IndexCommit does not support deletions");
		}

		ReaderCommit(SegmentInfos infos, Directory dir)
			throws IOException
		{
			segmentsFileName = infos.getSegmentsFileName();
			this.dir = dir;
			userData = infos.getUserData();
			files = Collections.unmodifiableCollection(infos.files(dir, true));
			generation = infos.getGeneration();
			segmentCount = infos.size();
		}
	}


	private final IndexWriter writer;
	private final SegmentInfos segmentInfos;
	private final int termInfosIndexDivisor;
	private final boolean applyAllDeletes;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/StandardDirectoryReader.desiredAssertionStatus();

	StandardDirectoryReader(Directory directory, AtomicReader readers[], IndexWriter writer, SegmentInfos sis, int termInfosIndexDivisor, boolean applyAllDeletes)
	{
		super(directory, readers);
		this.writer = writer;
		segmentInfos = sis;
		this.termInfosIndexDivisor = termInfosIndexDivisor;
		this.applyAllDeletes = applyAllDeletes;
	}

	static DirectoryReader open(Directory directory, IndexCommit commit, int termInfosIndexDivisor)
		throws IOException
	{
		return (DirectoryReader)(new SegmentInfos.FindSegmentsFile(directory, termInfosIndexDivisor) {

			final int val$termInfosIndexDivisor;

			protected Object doBody(String segmentFileName)
				throws IOException
			{
				SegmentInfos sis;
				SegmentReader readers[];
				int i;
				sis = new SegmentInfos();
				sis.read(directory, segmentFileName);
				readers = new SegmentReader[sis.size()];
				i = sis.size() - 1;
_L3:
				if (i < 0) goto _L2; else goto _L1
_L1:
				IOException prior;
				boolean success;
				prior = null;
				success = false;
				readers[i] = new SegmentReader(sis.info(i), termInfosIndexDivisor, IOContext.READ);
				success = true;
				if (!success)
					IOUtils.closeWhileHandlingException(prior, readers);
				continue; /* Loop/switch isn't completed */
				IOException ex;
				ex;
				prior = ex;
				if (!success)
					IOUtils.closeWhileHandlingException(prior, readers);
				continue; /* Loop/switch isn't completed */
				Exception exception;
				exception;
				if (!success)
					IOUtils.closeWhileHandlingException(prior, readers);
				throw exception;
				i--;
				  goto _L3
_L2:
				return new StandardDirectoryReader(directory, readers, null, sis, termInfosIndexDivisor, false);
			}

			
			{
				termInfosIndexDivisor = i;
				super(x0);
			}
		}).run(commit);
	}

	static DirectoryReader open(IndexWriter writer, SegmentInfos infos, boolean applyAllDeletes)
		throws IOException
	{
		int numSegments;
		List readers;
		Directory dir;
		SegmentInfos segmentInfos;
		int infosUpto;
		int i;
		numSegments = infos.size();
		readers = new ArrayList();
		dir = writer.getDirectory();
		segmentInfos = infos.clone();
		infosUpto = 0;
		i = 0;
_L3:
		if (i >= numSegments) goto _L2; else goto _L1
_L1:
		IOException prior;
		boolean success;
		prior = null;
		success = false;
		ReadersAndLiveDocs rld;
		SegmentInfoPerCommit info = infos.info(i);
		if (!$assertionsDisabled && info.info.dir != dir)
			throw new AssertionError();
		rld = writer.readerPool.get(info, true);
		SegmentReader reader = rld.getReadOnlyClone(IOContext.READ);
		if (reader.numDocs() > 0 || writer.getKeepFullyDeletedSegments())
		{
			readers.add(reader);
			infosUpto++;
		} else
		{
			reader.close();
			segmentInfos.remove(infosUpto);
		}
		writer.readerPool.release(rld);
		break MISSING_BLOCK_LABEL_170;
		Exception exception;
		exception;
		writer.readerPool.release(rld);
		throw exception;
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(prior, readers);
		continue; /* Loop/switch isn't completed */
		IOException ex;
		ex;
		prior = ex;
		if (!success)
			IOUtils.closeWhileHandlingException(prior, readers);
		continue; /* Loop/switch isn't completed */
		Exception exception1;
		exception1;
		if (!success)
			IOUtils.closeWhileHandlingException(prior, readers);
		throw exception1;
		i++;
		  goto _L3
_L2:
		return new StandardDirectoryReader(dir, (AtomicReader[])readers.toArray(new SegmentReader[readers.size()]), writer, segmentInfos, writer.getConfig().getReaderTermsIndexDivisor(), applyAllDeletes);
	}

	private static DirectoryReader open(Directory directory, IndexWriter writer, SegmentInfos infos, List oldReaders, int termInfosIndexDivisor)
		throws IOException
	{
		Map segmentReaders;
		SegmentReader newReaders[];
		boolean readerShared[];
		int i;
		segmentReaders = new HashMap();
		if (oldReaders != null)
		{
			int i = 0;
			for (int c = oldReaders.size(); i < c; i++)
			{
				SegmentReader sr = (SegmentReader)oldReaders.get(i);
				segmentReaders.put(sr.getSegmentName(), Integer.valueOf(i));
			}

		}
		newReaders = new SegmentReader[infos.size()];
		readerShared = new boolean[infos.size()];
		i = infos.size() - 1;
_L3:
		if (i < 0) goto _L2; else goto _L1
_L1:
		boolean success;
		Throwable prior;
		Integer oldReaderIndex = (Integer)segmentReaders.get(infos.info(i).info.name);
		if (oldReaderIndex == null)
			newReaders[i] = null;
		else
			newReaders[i] = (SegmentReader)oldReaders.get(oldReaderIndex.intValue());
		success = false;
		prior = null;
		if (newReaders[i] == null || infos.info(i).info.getUseCompoundFile() != newReaders[i].getSegmentInfo().info.getUseCompoundFile())
		{
			SegmentReader newReader = new SegmentReader(infos.info(i), termInfosIndexDivisor, IOContext.READ);
			readerShared[i] = false;
			newReaders[i] = newReader;
		} else
		if (newReaders[i].getSegmentInfo().getDelGen() == infos.info(i).getDelGen())
		{
			readerShared[i] = true;
			newReaders[i].incRef();
		} else
		{
			readerShared[i] = false;
			if (!$assertionsDisabled && infos.info(i).info.dir != newReaders[i].getSegmentInfo().info.dir)
				throw new AssertionError();
			if (!$assertionsDisabled && !infos.info(i).hasDeletions())
				throw new AssertionError();
			newReaders[i] = new SegmentReader(infos.info(i), newReaders[i].core, IOContext.READ);
		}
		success = true;
		if (!success)
			for (i++; i < infos.size(); i++)
			{
				if (newReaders[i] == null)
					continue;
				try
				{
					if (!readerShared[i])
						newReaders[i].close();
					else
						newReaders[i].decRef();
					continue;
				}
				catch (Throwable t)
				{
					if (prior == null)
						prior = t;
				}
			}

		if (prior == null)
			continue; /* Loop/switch isn't completed */
		if (prior instanceof IOException)
			throw (IOException)prior;
		if (prior instanceof RuntimeException)
			throw (RuntimeException)prior;
		if (prior instanceof Error)
			throw (Error)prior;
		else
			throw new RuntimeException(prior);
		Throwable ex;
		ex;
		prior = ex;
		if (!success)
			for (i++; i < infos.size(); i++)
			{
				if (newReaders[i] == null)
					continue;
				try
				{
					if (!readerShared[i])
						newReaders[i].close();
					else
						newReaders[i].decRef();
					continue;
				}
				catch (Throwable t)
				{
					if (prior == null)
						prior = t;
				}
			}

		if (prior == null)
			continue; /* Loop/switch isn't completed */
		if (prior instanceof IOException)
			throw (IOException)prior;
		if (prior instanceof RuntimeException)
			throw (RuntimeException)prior;
		if (prior instanceof Error)
			throw (Error)prior;
		else
			throw new RuntimeException(prior);
		Exception exception;
		exception;
		if (!success)
			for (i++; i < infos.size(); i++)
			{
				if (newReaders[i] == null)
					continue;
				try
				{
					if (!readerShared[i])
						newReaders[i].close();
					else
						newReaders[i].decRef();
					continue;
				}
				catch (Throwable t)
				{
					if (prior == null)
						prior = t;
				}
			}

		if (prior != null)
		{
			if (prior instanceof IOException)
				throw (IOException)prior;
			if (prior instanceof RuntimeException)
				throw (RuntimeException)prior;
			if (prior instanceof Error)
				throw (Error)prior;
			else
				throw new RuntimeException(prior);
		} else
		{
			throw exception;
		}
		i--;
		  goto _L3
_L2:
		return new StandardDirectoryReader(directory, newReaders, writer, infos, termInfosIndexDivisor, false);
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getSimpleName());
		buffer.append('(');
		String segmentsFile = segmentInfos.getSegmentsFileName();
		if (segmentsFile != null)
			buffer.append(segmentsFile).append(":").append(segmentInfos.getVersion());
		if (writer != null)
			buffer.append(":nrt");
		AtomicReader r;
		for (Iterator i$ = getSequentialSubReaders().iterator(); i$.hasNext(); buffer.append(r))
		{
			r = (AtomicReader)i$.next();
			buffer.append(' ');
		}

		buffer.append(')');
		return buffer.toString();
	}

	protected DirectoryReader doOpenIfChanged()
		throws IOException
	{
		return doOpenIfChanged(null);
	}

	protected DirectoryReader doOpenIfChanged(IndexCommit commit)
		throws IOException
	{
		ensureOpen();
		if (writer != null)
			return doOpenFromWriter(commit);
		else
			return doOpenNoWriter(commit);
	}

	protected DirectoryReader doOpenIfChanged(IndexWriter writer, boolean applyAllDeletes)
		throws IOException
	{
		ensureOpen();
		if (writer == this.writer && applyAllDeletes == this.applyAllDeletes)
			return doOpenFromWriter(null);
		else
			return writer.getReader(applyAllDeletes);
	}

	private DirectoryReader doOpenFromWriter(IndexCommit commit)
		throws IOException
	{
		if (commit != null)
			throw new IllegalArgumentException("a reader obtained from IndexWriter.getReader() cannot currently accept a commit");
		if (writer.nrtIsCurrent(segmentInfos))
			return null;
		DirectoryReader reader = writer.getReader(applyAllDeletes);
		if (reader.getVersion() == segmentInfos.getVersion())
		{
			reader.decRef();
			return null;
		} else
		{
			return reader;
		}
	}

	private DirectoryReader doOpenNoWriter(IndexCommit commit)
		throws IOException
	{
		if (commit == null)
		{
			if (isCurrent())
				return null;
		} else
		{
			if (directory != commit.getDirectory())
				throw new IOException("the specified commit does not match the specified Directory");
			if (segmentInfos != null && commit.getSegmentsFileName().equals(segmentInfos.getSegmentsFileName()))
				return null;
		}
		return (DirectoryReader)(new SegmentInfos.FindSegmentsFile(directory) {

			final StandardDirectoryReader this$0;

			protected Object doBody(String segmentFileName)
				throws IOException
			{
				SegmentInfos infos = new SegmentInfos();
				infos.read(directory, segmentFileName);
				return doOpenIfChanged(infos, null);
			}

			
			{
				this$0 = StandardDirectoryReader.this;
				super(x0);
			}
		}).run(commit);
	}

	DirectoryReader doOpenIfChanged(SegmentInfos infos, IndexWriter writer)
		throws IOException
	{
		return open(directory, writer, infos, getSequentialSubReaders(), termInfosIndexDivisor);
	}

	public long getVersion()
	{
		ensureOpen();
		return segmentInfos.getVersion();
	}

	public boolean isCurrent()
		throws IOException
	{
		ensureOpen();
		if (writer == null || writer.isClosed())
		{
			SegmentInfos sis = new SegmentInfos();
			sis.read(directory);
			return sis.getVersion() == segmentInfos.getVersion();
		} else
		{
			return writer.nrtIsCurrent(segmentInfos);
		}
	}

	protected void doClose()
		throws IOException
	{
		Throwable firstExc = null;
		Iterator i$ = getSequentialSubReaders().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			AtomicReader r = (AtomicReader)i$.next();
			try
			{
				r.decRef();
			}
			catch (Throwable t)
			{
				if (firstExc == null)
					firstExc = t;
			}
		} while (true);
		if (writer != null)
			writer.deletePendingFiles();
		if (firstExc != null)
		{
			if (firstExc instanceof IOException)
				throw (IOException)firstExc;
			if (firstExc instanceof RuntimeException)
				throw (RuntimeException)firstExc;
			if (firstExc instanceof Error)
				throw (Error)firstExc;
			else
				throw new RuntimeException(firstExc);
		} else
		{
			return;
		}
	}

	public IndexCommit getIndexCommit()
		throws IOException
	{
		ensureOpen();
		return new ReaderCommit(segmentInfos, directory);
	}

}
