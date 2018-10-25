// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentInfos.java

package org.apache.lucene.index;

import java.io.*;
import java.util.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.codecs.lucene3x.Lucene3xCodec;
import org.apache.lucene.codecs.lucene3x.Lucene3xSegmentInfoReader;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfoPerCommit, CorruptIndexException, SegmentInfo, IndexFileNames, 
//			MergePolicy, IndexFormatTooNewException, IndexNotFoundException, IndexCommit

public final class SegmentInfos
	implements Cloneable, Iterable
{
	public static abstract class FindSegmentsFile
	{

		final Directory directory;

		public Object run()
			throws IOException
		{
			return run(null);
		}

		public Object run(IndexCommit commit)
			throws IOException
		{
			String segmentFileName;
			long lastGen;
			long gen;
			int genLookaheadCount;
			IOException exc;
			int retryCount;
			boolean useFirstMethod;
			if (commit != null)
				if (directory != commit.getDirectory())
					throw new IOException("the specified commit does not match the specified Directory");
				else
					return doBody(commit.getSegmentsFileName());
			segmentFileName = null;
			lastGen = -1L;
			gen = 0L;
			genLookaheadCount = 0;
			exc = null;
			retryCount = 0;
			useFirstMethod = true;
_L2:
			String files[];
			long genA;
			long genB;
			IndexInput genInput;
			if (!useFirstMethod)
				break MISSING_BLOCK_LABEL_440;
			files = null;
			genA = -1L;
			files = directory.listAll();
			if (files != null)
				genA = SegmentInfos.getLastCommitGeneration(files);
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("directory listing genA=").append(genA).toString());
			genB = -1L;
			genInput = null;
			try
			{
				genInput = directory.openInput("segments.gen", IOContext.READONCE);
			}
			catch (FileNotFoundException e)
			{
				if (SegmentInfos.infoStream != null)
					SegmentInfos.message((new StringBuilder()).append("segments.gen open: FileNotFoundException ").append(e).toString());
			}
			catch (IOException e)
			{
				if (SegmentInfos.infoStream != null)
					SegmentInfos.message((new StringBuilder()).append("segments.gen open: IOException ").append(e).toString());
			}
			if (genInput == null)
				break MISSING_BLOCK_LABEL_350;
			int version = genInput.readInt();
			if (version == -2)
			{
				long gen0 = genInput.readLong();
				long gen1 = genInput.readLong();
				if (SegmentInfos.infoStream != null)
					SegmentInfos.message((new StringBuilder()).append("fallback check: ").append(gen0).append("; ").append(gen1).toString());
				if (gen0 == gen1)
					genB = gen0;
			} else
			{
				throw new IndexFormatTooNewException(genInput, version, -2, -2);
			}
			genInput.close();
			break MISSING_BLOCK_LABEL_350;
			IOException err2;
			err2;
			if (err2 instanceof CorruptIndexException)
				throw err2;
			genInput.close();
			break MISSING_BLOCK_LABEL_350;
			Exception exception;
			exception;
			genInput.close();
			throw exception;
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("segments.gen check: genB=").append(genB).toString());
			gen = Math.max(genA, genB);
			if (gen == -1L)
				throw new IndexNotFoundException((new StringBuilder()).append("no segments* file found in ").append(directory).append(": files: ").append(Arrays.toString(files)).toString());
			if (useFirstMethod && lastGen == gen && retryCount >= 2)
				useFirstMethod = false;
			if (!useFirstMethod)
			{
				if (genLookaheadCount < SegmentInfos.defaultGenLookaheadCount)
				{
					gen++;
					genLookaheadCount++;
					if (SegmentInfos.infoStream != null)
						SegmentInfos.message((new StringBuilder()).append("look ahead increment gen to ").append(gen).toString());
				} else
				{
					throw exc;
				}
			} else
			if (lastGen == gen)
				retryCount++;
			else
				retryCount = 0;
			lastGen = gen;
			segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", gen);
			Object v;
			v = doBody(segmentFileName);
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("success on ").append(segmentFileName).toString());
			return v;
			IOException err;
			err;
			String prevSegmentFileName;
			if (exc == null)
				exc = err;
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("primary Exception on '").append(segmentFileName).append("': ").append(err).append("'; will retry: retryCount=").append(retryCount).append("; gen = ").append(gen).toString());
			if (gen <= 1L || !useFirstMethod || retryCount != 1)
				continue; /* Loop/switch isn't completed */
			prevSegmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", gen - 1L);
			boolean prevExists = directory.fileExists(prevSegmentFileName);
			if (!prevExists)
				continue; /* Loop/switch isn't completed */
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("fallback to prior segment file '").append(prevSegmentFileName).append("'").toString());
			Object v;
			v = doBody(prevSegmentFileName);
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("success on fallback ").append(prevSegmentFileName).toString());
			return v;
			IOException err2;
			err2;
			if (SegmentInfos.infoStream != null)
				SegmentInfos.message((new StringBuilder()).append("secondary Exception on '").append(prevSegmentFileName).append("': ").append(err2).append("'; will retry").toString());
			if (true) goto _L2; else goto _L1
_L1:
		}

		protected abstract Object doBody(String s)
			throws IOException;

		public FindSegmentsFile(Directory directory)
		{
			this.directory = directory;
		}
	}


	public static final int VERSION_40 = 0;
	public static final int FORMAT_SEGMENTS_GEN_CURRENT = -2;
	public int counter;
	public long version;
	private long generation;
	private long lastGeneration;
	public Map userData;
	private List segments;
	private static PrintStream infoStream = null;
	ChecksumIndexOutput pendingSegnOutput;
	private static final String SEGMENT_INFO_UPGRADE_CODEC = "SegmentInfo3xUpgrade";
	private static final int SEGMENT_INFO_UPGRADE_VERSION = 0;
	private static int defaultGenLookaheadCount = 10;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentInfos.desiredAssertionStatus();

	public SegmentInfos()
	{
		userData = Collections.emptyMap();
		segments = new ArrayList();
	}

	public SegmentInfoPerCommit info(int i)
	{
		return (SegmentInfoPerCommit)segments.get(i);
	}

	public static long getLastCommitGeneration(String files[])
	{
		if (files == null)
			return -1L;
		long max = -1L;
		String arr$[] = files;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String file = arr$[i$];
			if (!file.startsWith("segments") || file.equals("segments.gen"))
				continue;
			long gen = generationFromSegmentsFileName(file);
			if (gen > max)
				max = gen;
		}

		return max;
	}

	public static long getLastCommitGeneration(Directory directory)
		throws IOException
	{
		return getLastCommitGeneration(directory.listAll());
		NoSuchDirectoryException nsde;
		nsde;
		return -1L;
	}

	public static String getLastCommitSegmentsFileName(String files[])
	{
		return IndexFileNames.fileNameFromGeneration("segments", "", getLastCommitGeneration(files));
	}

	public static String getLastCommitSegmentsFileName(Directory directory)
		throws IOException
	{
		return IndexFileNames.fileNameFromGeneration("segments", "", getLastCommitGeneration(directory));
	}

	public String getSegmentsFileName()
	{
		return IndexFileNames.fileNameFromGeneration("segments", "", lastGeneration);
	}

	public static long generationFromSegmentsFileName(String fileName)
	{
		if (fileName.equals("segments"))
			return 0L;
		if (fileName.startsWith("segments"))
			return Long.parseLong(fileName.substring(1 + "segments".length()), 36);
		else
			throw new IllegalArgumentException((new StringBuilder()).append("fileName \"").append(fileName).append("\" is not a segments file").toString());
	}

	public String getNextSegmentFileName()
	{
		long nextGeneration;
		if (generation == -1L)
			nextGeneration = 1L;
		else
			nextGeneration = generation + 1L;
		return IndexFileNames.fileNameFromGeneration("segments", "", nextGeneration);
	}

	public final void read(Directory directory, String segmentFileName)
		throws IOException
	{
		boolean success;
		ChecksumIndexInput input;
		success = false;
		clear();
		generation = generationFromSegmentsFileName(segmentFileName);
		lastGeneration = generation;
		input = new ChecksumIndexInput(directory.openInput(segmentFileName, IOContext.READ));
		int format = input.readInt();
		if (format == 0x3fd76c17)
		{
			CodecUtil.checkHeaderNoMagic(input, "segments", 0, 0);
			version = input.readLong();
			counter = input.readInt();
			int numSegments = input.readInt();
			if (numSegments < 0)
				throw new CorruptIndexException((new StringBuilder()).append("invalid segment count: ").append(numSegments).append(" (resource: ").append(input).append(")").toString());
			for (int seg = 0; seg < numSegments; seg++)
			{
				String segName = input.readString();
				Codec codec = Codec.forName(input.readString());
				SegmentInfo info = codec.segmentInfoFormat().getSegmentInfoReader().read(directory, segName, IOContext.READ);
				info.setCodec(codec);
				long delGen = input.readLong();
				int delCount = input.readInt();
				if (delCount < 0 || delCount > info.getDocCount())
					throw new CorruptIndexException((new StringBuilder()).append("invalid deletion count: ").append(delCount).append(" (resource: ").append(input).append(")").toString());
				add(new SegmentInfoPerCommit(info, delCount, delGen));
			}

			userData = input.readStringStringMap();
		} else
		{
			Lucene3xSegmentInfoReader.readLegacyInfos(this, directory, input, format);
			Codec codec = Codec.forName("Lucene3x");
			SegmentInfoPerCommit info;
			for (Iterator i$ = iterator(); i$.hasNext(); info.info.setCodec(codec))
				info = (SegmentInfoPerCommit)i$.next();

		}
		long checksumNow = input.getChecksum();
		long checksumThen = input.readLong();
		if (checksumNow != checksumThen)
			throw new CorruptIndexException((new StringBuilder()).append("checksum mismatch in segments file (resource: ").append(input).append(")").toString());
		success = true;
		if (!success)
		{
			clear();
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		} else
		{
			input.close();
		}
		break MISSING_BLOCK_LABEL_474;
		Exception exception;
		exception;
		if (!success)
		{
			clear();
			IOUtils.closeWhileHandlingException(new Closeable[] {
				input
			});
		} else
		{
			input.close();
		}
		throw exception;
	}

	public final void read(Directory directory)
		throws IOException
	{
		generation = lastGeneration = -1L;
		(new FindSegmentsFile(directory) {

			final SegmentInfos this$0;

			protected Object doBody(String segmentFileName)
				throws IOException
			{
				read(directory, segmentFileName);
				return null;
			}

			
			{
				this$0 = SegmentInfos.this;
				super(x0);
			}
		}).run();
	}

	private void write(Directory directory)
		throws IOException
	{
		String segmentsFileName;
		ChecksumIndexOutput segnOutput;
		boolean success;
		Set upgradedSIFiles;
		segmentsFileName = getNextSegmentFileName();
		if (generation == -1L)
			generation = 1L;
		else
			generation++;
		segnOutput = null;
		success = false;
		upgradedSIFiles = new HashSet();
		Iterator i$;
		segnOutput = new ChecksumIndexOutput(directory.createOutput(segmentsFileName, IOContext.DEFAULT));
		CodecUtil.writeHeader(segnOutput, "segments", 0);
		segnOutput.writeLong(this.version);
		segnOutput.writeInt(counter);
		segnOutput.writeInt(size());
		i$ = iterator();
_L2:
		String markerFileName;
		IndexOutput out;
		if (!i$.hasNext())
			break; /* Loop/switch isn't completed */
		SegmentInfoPerCommit siPerCommit = (SegmentInfoPerCommit)i$.next();
		SegmentInfo si = siPerCommit.info;
		segnOutput.writeString(si.name);
		segnOutput.writeString(si.getCodec().getName());
		segnOutput.writeLong(siPerCommit.getDelGen());
		segnOutput.writeInt(siPerCommit.getDelCount());
		if (!$assertionsDisabled && si.dir != directory)
			throw new AssertionError();
		if (!$assertionsDisabled && siPerCommit.getDelCount() > si.getDocCount())
			throw new AssertionError();
		String version = si.getVersion();
		if (version != null && StringHelper.getVersionComparator().compare(version, "4.0") >= 0 || segmentWasUpgraded(directory, si))
			continue; /* Loop/switch isn't completed */
		markerFileName = IndexFileNames.segmentFileName(si.name, "upgraded", "si");
		si.addFile(markerFileName);
		String segmentFileName = write3xInfo(directory, si, IOContext.DEFAULT);
		upgradedSIFiles.add(segmentFileName);
		directory.sync(Collections.singletonList(segmentFileName));
		si.addFile(markerFileName);
		out = directory.createOutput(markerFileName, IOContext.DEFAULT);
		CodecUtil.writeHeader(out, "SegmentInfo3xUpgrade", 0);
		Exception exception;
		out.close();
		upgradedSIFiles.add(markerFileName);
		directory.sync(Collections.singletonList(markerFileName));
		continue; /* Loop/switch isn't completed */
		exception;
		out.close();
		throw exception;
		if (true) goto _L2; else goto _L1
_L1:
		segnOutput.writeStringStringMap(userData);
		pendingSegnOutput = segnOutput;
		success = true;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				segnOutput
			});
			for (i$ = upgradedSIFiles.iterator(); i$.hasNext();)
			{
				String fileName = (String)i$.next();
				try
				{
					directory.deleteFile(fileName);
				}
				catch (Throwable t) { }
			}

			try
			{
				directory.deleteFile(segmentsFileName);
			}
			catch (Throwable t) { }
		}
		break MISSING_BLOCK_LABEL_538;
		Exception exception1;
		exception1;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				segnOutput
			});
			for (Iterator i$ = upgradedSIFiles.iterator(); i$.hasNext();)
			{
				String fileName = (String)i$.next();
				try
				{
					directory.deleteFile(fileName);
				}
				catch (Throwable t) { }
			}

			try
			{
				directory.deleteFile(segmentsFileName);
			}
			catch (Throwable t) { }
		}
		throw exception1;
	}

	private static boolean segmentWasUpgraded(Directory directory, SegmentInfo si)
	{
label0:
		{
			String markerFileName = IndexFileNames.segmentFileName(si.name, "upgraded", "si");
			IndexInput in = null;
			boolean flag;
			try
			{
				in = directory.openInput(markerFileName, IOContext.READONCE);
				if (CodecUtil.checkHeader(in, "SegmentInfo3xUpgrade", 0, 0) != 0)
					break label0;
				flag = true;
			}
			catch (IOException ioe)
			{
				if (in != null)
					IOUtils.closeWhileHandlingException(new Closeable[] {
						in
					});
				break MISSING_BLOCK_LABEL_113;
			}
			finally
			{
				if (in != null)
					IOUtils.closeWhileHandlingException(new Closeable[] {
						in
					});
				throw exception;
			}
			if (in != null)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					in
				});
			return flag;
		}
		if (in != null)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				in
			});
		break MISSING_BLOCK_LABEL_113;
		return false;
	}

	/**
	 * @deprecated Method write3xInfo is deprecated
	 */

	public static String write3xInfo(Directory dir, SegmentInfo si, IOContext context)
		throws IOException
	{
		String fileName;
		boolean success;
		IndexOutput output;
		fileName = IndexFileNames.segmentFileName(si.name, "", "si");
		si.addFile(fileName);
		success = false;
		output = dir.createOutput(fileName, context);
		if (!$assertionsDisabled && !(si.getCodec() instanceof Lucene3xCodec))
			throw new AssertionError("broken test, trying to mix preflex with other codecs");
		CodecUtil.writeHeader(output, "Lucene3xSegmentInfo", 0);
		output.writeString(si.getVersion());
		output.writeInt(si.getDocCount());
		output.writeStringStringMap(si.attributes());
		output.writeByte((byte)(si.getUseCompoundFile() ? 1 : -1));
		output.writeStringStringMap(si.getDiagnostics());
		output.writeStringSet(si.files());
		output.close();
		success = true;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
			try
			{
				si.dir.deleteFile(fileName);
			}
			catch (Throwable t) { }
		}
		break MISSING_BLOCK_LABEL_201;
		Exception exception;
		exception;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				output
			});
			try
			{
				si.dir.deleteFile(fileName);
			}
			catch (Throwable t) { }
		}
		throw exception;
		return fileName;
	}

	public SegmentInfos clone()
	{
		SegmentInfos sis;
		sis = (SegmentInfos)super.clone();
		sis.segments = new ArrayList(size());
		SegmentInfoPerCommit info;
		for (Iterator i$ = iterator(); i$.hasNext(); sis.add(info.clone()))
		{
			info = (SegmentInfoPerCommit)i$.next();
			if (!$assertionsDisabled && info.info.getCodec() == null)
				throw new AssertionError();
		}

		sis.userData = new HashMap(userData);
		return sis;
		CloneNotSupportedException e;
		e;
		throw new RuntimeException("should not happen", e);
	}

	public long getVersion()
	{
		return version;
	}

	public long getGeneration()
	{
		return generation;
	}

	public long getLastGeneration()
	{
		return lastGeneration;
	}

	public static void setInfoStream(PrintStream infoStream)
	{
		infoStream = infoStream;
	}

	public static void setDefaultGenLookaheadCount(int count)
	{
		defaultGenLookaheadCount = count;
	}

	public static int getDefaultGenLookahedCount()
	{
		return defaultGenLookaheadCount;
	}

	public static PrintStream getInfoStream()
	{
		return infoStream;
	}

	private static void message(String message)
	{
		infoStream.println((new StringBuilder()).append("SIS [").append(Thread.currentThread().getName()).append("]: ").append(message).toString());
	}

	void updateGeneration(SegmentInfos other)
	{
		lastGeneration = other.lastGeneration;
		generation = other.generation;
	}

	final void rollbackCommit(Directory dir)
	{
		if (pendingSegnOutput != null)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				pendingSegnOutput
			});
			pendingSegnOutput = null;
			String segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
			IOUtils.deleteFilesIgnoringExceptions(dir, new String[] {
				segmentFileName
			});
		}
	}

	final void prepareCommit(Directory dir)
		throws IOException
	{
		if (pendingSegnOutput != null)
		{
			throw new IllegalStateException("prepareCommit was already called");
		} else
		{
			write(dir);
			return;
		}
	}

	public Collection files(Directory dir, boolean includeSegmentsFile)
		throws IOException
	{
		HashSet files = new HashSet();
		if (includeSegmentsFile)
		{
			String segmentFileName = getSegmentsFileName();
			if (segmentFileName != null)
				files.add(segmentFileName);
		}
		int size = size();
		for (int i = 0; i < size; i++)
		{
			SegmentInfoPerCommit info = info(i);
			if (!$assertionsDisabled && info.info.dir != dir)
				throw new AssertionError();
			if (info.info.dir == dir)
				files.addAll(info.files());
		}

		return files;
	}

	final void finishCommit(Directory dir)
		throws IOException
	{
		boolean success;
		if (pendingSegnOutput == null)
			throw new IllegalStateException("prepareCommit was not called");
		success = false;
		pendingSegnOutput.finishCommit();
		success = true;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				pendingSegnOutput
			});
			rollbackCommit(dir);
			break MISSING_BLOCK_LABEL_264;
		}
		success = false;
		pendingSegnOutput.close();
		success = true;
		if (!success)
		{
			String segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
			IOUtils.deleteFilesIgnoringExceptions(dir, new String[] {
				segmentFileName
			});
		}
		pendingSegnOutput = null;
		break MISSING_BLOCK_LABEL_264;
		Throwable t;
		t;
		if (!success)
		{
			String segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
			IOUtils.deleteFilesIgnoringExceptions(dir, new String[] {
				segmentFileName
			});
		}
		pendingSegnOutput = null;
		throw t;
		Exception exception;
		exception;
		if (!success)
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				pendingSegnOutput
			});
			rollbackCommit(dir);
			break MISSING_BLOCK_LABEL_261;
		}
		success = false;
		pendingSegnOutput.close();
		success = true;
		if (!success)
		{
			String segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
			IOUtils.deleteFilesIgnoringExceptions(dir, new String[] {
				segmentFileName
			});
		}
		pendingSegnOutput = null;
		break MISSING_BLOCK_LABEL_261;
		Exception exception1;
		exception1;
		if (!success)
		{
			String segmentFileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
			IOUtils.deleteFilesIgnoringExceptions(dir, new String[] {
				segmentFileName
			});
		}
		pendingSegnOutput = null;
		throw exception1;
		throw exception;
		String fileName;
		fileName = IndexFileNames.fileNameFromGeneration("segments", "", generation);
		success = false;
		dir.sync(Collections.singleton(fileName));
		success = true;
		if (!success)
			try
			{
				dir.deleteFile(fileName);
			}
			// Misplaced declaration of an exception variable
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_324;
		Exception exception2;
		exception2;
		if (!success)
			try
			{
				dir.deleteFile(fileName);
			}
			catch (Throwable t) { }
		throw exception2;
		lastGeneration = generation;
		IndexOutput genOutput = dir.createOutput("segments.gen", IOContext.READONCE);
		genOutput.writeInt(-2);
		genOutput.writeLong(generation);
		genOutput.writeLong(generation);
		genOutput.close();
		dir.sync(Collections.singleton("segments.gen"));
		break MISSING_BLOCK_LABEL_434;
		Exception exception3;
		exception3;
		genOutput.close();
		dir.sync(Collections.singleton("segments.gen"));
		throw exception3;
		genOutput;
		try
		{
			dir.deleteFile("segments.gen");
		}
		catch (Throwable t2) { }
		if (genOutput instanceof ThreadInterruptedException)
			throw (ThreadInterruptedException)genOutput;
	}

	final void commit(Directory dir)
		throws IOException
	{
		prepareCommit(dir);
		finishCommit(dir);
	}

	public String toString(Directory directory)
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(getSegmentsFileName()).append(": ");
		int count = size();
		for (int i = 0; i < count; i++)
		{
			if (i > 0)
				buffer.append(' ');
			SegmentInfoPerCommit info = info(i);
			buffer.append(info.toString(directory, 0));
		}

		return buffer.toString();
	}

	public Map getUserData()
	{
		return userData;
	}

	void setUserData(Map data)
	{
		if (data == null)
			userData = Collections.emptyMap();
		else
			userData = data;
	}

	void replace(SegmentInfos other)
	{
		rollbackSegmentInfos(other.asList());
		lastGeneration = other.lastGeneration;
	}

	public int totalDocCount()
	{
		int count = 0;
		for (Iterator i$ = iterator(); i$.hasNext();)
		{
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)i$.next();
			count += info.info.getDocCount();
		}

		return count;
	}

	public void changed()
	{
		version++;
	}

	void applyMergeChanges(MergePolicy.OneMerge merge, boolean dropSegment)
	{
		Set mergedAway = new HashSet(merge.segments);
		boolean inserted = false;
		int newSegIdx = 0;
		int segIdx = 0;
		for (int cnt = segments.size(); segIdx < cnt; segIdx++)
		{
			if (!$assertionsDisabled && segIdx < newSegIdx)
				throw new AssertionError();
			SegmentInfoPerCommit info = (SegmentInfoPerCommit)segments.get(segIdx);
			if (mergedAway.contains(info))
			{
				if (!inserted && !dropSegment)
				{
					segments.set(segIdx, merge.info);
					inserted = true;
					newSegIdx++;
				}
			} else
			{
				segments.set(newSegIdx, info);
				newSegIdx++;
			}
		}

		segments.subList(newSegIdx, segments.size()).clear();
		if (!inserted && !dropSegment)
			segments.add(0, merge.info);
	}

	List createBackupSegmentInfos()
	{
		List list = new ArrayList(size());
		SegmentInfoPerCommit info;
		for (Iterator i$ = iterator(); i$.hasNext(); list.add(info.clone()))
		{
			info = (SegmentInfoPerCommit)i$.next();
			if (!$assertionsDisabled && info.info.getCodec() == null)
				throw new AssertionError();
		}

		return list;
	}

	void rollbackSegmentInfos(List infos)
	{
		clear();
		addAll(infos);
	}

	public Iterator iterator()
	{
		return asList().iterator();
	}

	public List asList()
	{
		return Collections.unmodifiableList(segments);
	}

	public int size()
	{
		return segments.size();
	}

	public void add(SegmentInfoPerCommit si)
	{
		segments.add(si);
	}

	public void addAll(Iterable sis)
	{
		SegmentInfoPerCommit si;
		for (Iterator i$ = sis.iterator(); i$.hasNext(); add(si))
			si = (SegmentInfoPerCommit)i$.next();

	}

	public void clear()
	{
		segments.clear();
	}

	public void remove(SegmentInfoPerCommit si)
	{
		segments.remove(si);
	}

	void remove(int index)
	{
		segments.remove(index);
	}

	boolean contains(SegmentInfoPerCommit si)
	{
		return segments.contains(si);
	}

	int indexOf(SegmentInfoPerCommit si)
	{
		return segments.indexOf(si);
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}




}
