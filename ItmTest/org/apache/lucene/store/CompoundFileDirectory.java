// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompoundFileDirectory.java

package org.apache.lucene.store;

import java.io.*;
import java.util.*;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.store:
//			Directory, CompoundFileWriter, IOContext, IndexInput, 
//			BufferedIndexInput, IndexOutput, Lock

public final class CompoundFileDirectory extends Directory
{
	public static final class FileEntry
	{

		long offset;
		long length;

		public FileEntry()
		{
		}
	}


	private final Directory directory;
	private final String fileName;
	protected final int readBufferSize;
	private final Map entries;
	private final boolean openForWrite;
	private static final Map SENTINEL = Collections.emptyMap();
	private final CompoundFileWriter writer;
	private final Directory.IndexInputSlicer handle;
	private static final byte CODEC_MAGIC_BYTE1 = 63;
	private static final byte CODEC_MAGIC_BYTE2 = -41;
	private static final byte CODEC_MAGIC_BYTE3 = 108;
	private static final byte CODEC_MAGIC_BYTE4 = 23;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/CompoundFileDirectory.desiredAssertionStatus();

	public CompoundFileDirectory(Directory directory, String fileName, IOContext context, boolean openForWrite)
		throws IOException
	{
		boolean success;
		this.directory = directory;
		this.fileName = fileName;
		readBufferSize = BufferedIndexInput.bufferSize(context);
		isOpen = false;
		this.openForWrite = openForWrite;
		if (openForWrite)
			break MISSING_BLOCK_LABEL_126;
		success = false;
		handle = directory.createSlicer(fileName, context);
		entries = readEntries(handle, directory, fileName);
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				handle
			});
		break MISSING_BLOCK_LABEL_113;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				handle
			});
		throw exception;
		isOpen = true;
		writer = null;
		break MISSING_BLOCK_LABEL_196;
		if (!$assertionsDisabled && (directory instanceof CompoundFileDirectory))
			throw new AssertionError((new StringBuilder()).append("compound file inside of compound file: ").append(fileName).toString());
		entries = SENTINEL;
		isOpen = true;
		writer = new CompoundFileWriter(directory, fileName);
		handle = null;
	}

	private static final Map readEntries(Directory.IndexInputSlicer handle, Directory dir, String name)
		throws IOException
	{
		IOException priorE;
		IndexInput stream;
		IndexInput entriesStream;
		priorE = null;
		stream = null;
		entriesStream = null;
		Map map;
		stream = handle.openFullSlice();
		int firstInt = stream.readVInt();
		Map mapping;
		if (firstInt == 63)
		{
			byte secondByte = stream.readByte();
			byte thirdByte = stream.readByte();
			byte fourthByte = stream.readByte();
			if (secondByte != -41 || thirdByte != 108 || fourthByte != 23)
				throw new CorruptIndexException((new StringBuilder()).append("Illegal/impossible header for CFS file: ").append(secondByte).append(",").append(thirdByte).append(",").append(fourthByte).toString());
			CodecUtil.checkHeaderNoMagic(stream, "CompoundFileWriterData", 0, 0);
			String entriesFileName = IndexFileNames.segmentFileName(IndexFileNames.stripExtension(name), "", "cfe");
			entriesStream = dir.openInput(entriesFileName, IOContext.READONCE);
			CodecUtil.checkHeader(entriesStream, "CompoundFileWriterEntries", 0, 0);
			int numEntries = entriesStream.readVInt();
			mapping = new HashMap(numEntries);
			for (int i = 0; i < numEntries; i++)
			{
				FileEntry fileEntry = new FileEntry();
				String id = entriesStream.readString();
				FileEntry previous = (FileEntry)mapping.put(id, fileEntry);
				if (previous != null)
					throw new CorruptIndexException((new StringBuilder()).append("Duplicate cfs entry id=").append(id).append(" in CFS: ").append(entriesStream).toString());
				fileEntry.offset = entriesStream.readLong();
				fileEntry.length = entriesStream.readLong();
			}

		} else
		{
			mapping = readLegacyEntries(stream, firstInt);
		}
		map = mapping;
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			stream, entriesStream
		});
		return map;
		IOException ioe;
		ioe;
		priorE = ioe;
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			stream, entriesStream
		});
		break MISSING_BLOCK_LABEL_377;
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			stream, entriesStream
		});
		throw exception;
		throw new AssertionError("impossible to get here");
	}

	private static Map readLegacyEntries(IndexInput stream, int firstInt)
		throws CorruptIndexException, IOException
	{
		Map entries = new HashMap();
		int count;
		boolean stripSegmentName;
		if (firstInt < 0)
		{
			if (firstInt < -1)
				throw new CorruptIndexException((new StringBuilder()).append("Incompatible format version: ").append(firstInt).append(" expected >= ").append(-1).append(" (resource: ").append(stream).append(")").toString());
			count = stream.readVInt();
			stripSegmentName = false;
		} else
		{
			count = firstInt;
			stripSegmentName = true;
		}
		long streamLength = stream.length();
		FileEntry entry = null;
		for (int i = 0; i < count; i++)
		{
			long offset = stream.readLong();
			if (offset < 0L || offset > streamLength)
				throw new CorruptIndexException((new StringBuilder()).append("Invalid CFS entry offset: ").append(offset).append(" (resource: ").append(stream).append(")").toString());
			String id = stream.readString();
			if (stripSegmentName)
				id = IndexFileNames.stripSegmentName(id);
			if (entry != null)
				entry.length = offset - entry.offset;
			entry = new FileEntry();
			entry.offset = offset;
			FileEntry previous = (FileEntry)entries.put(id, entry);
			if (previous != null)
				throw new CorruptIndexException((new StringBuilder()).append("Duplicate cfs entry id=").append(id).append(" in CFS: ").append(stream).toString());
		}

		if (entry != null)
			entry.length = streamLength - entry.offset;
		return entries;
	}

	public Directory getDirectory()
	{
		return directory;
	}

	public String getName()
	{
		return fileName;
	}

	public synchronized void close()
		throws IOException
	{
		if (!isOpen)
			return;
		isOpen = false;
		if (writer != null)
		{
			if (!$assertionsDisabled && !openForWrite)
				throw new AssertionError();
			writer.close();
		} else
		{
			IOUtils.close(new Closeable[] {
				handle
			});
		}
	}

	public synchronized IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		if (!$assertionsDisabled && openForWrite)
			throw new AssertionError();
		String id = IndexFileNames.stripSegmentName(name);
		FileEntry entry = (FileEntry)entries.get(id);
		if (entry == null)
			throw new FileNotFoundException((new StringBuilder()).append("No sub-file with id ").append(id).append(" found (fileName=").append(name).append(" files: ").append(entries.keySet()).append(")").toString());
		else
			return handle.openSlice(name, entry.offset, entry.length);
	}

	public String[] listAll()
	{
		ensureOpen();
		String res[];
		if (writer != null)
		{
			res = writer.listAll();
		} else
		{
			res = (String[])entries.keySet().toArray(new String[entries.size()]);
			String seg = IndexFileNames.parseSegmentName(fileName);
			for (int i = 0; i < res.length; i++)
				res[i] = (new StringBuilder()).append(seg).append(res[i]).toString();

		}
		return res;
	}

	public boolean fileExists(String name)
	{
		ensureOpen();
		if (writer != null)
			return writer.fileExists(name);
		else
			return entries.containsKey(IndexFileNames.stripSegmentName(name));
	}

	public void deleteFile(String name)
	{
		throw new UnsupportedOperationException();
	}

	public void renameFile(String from, String to)
	{
		throw new UnsupportedOperationException();
	}

	public long fileLength(String name)
		throws IOException
	{
		ensureOpen();
		if (writer != null)
			return writer.fileLength(name);
		FileEntry e = (FileEntry)entries.get(IndexFileNames.stripSegmentName(name));
		if (e == null)
			throw new FileNotFoundException(name);
		else
			return e.length;
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		return writer.createOutput(name, context);
	}

	public void sync(Collection names)
	{
		throw new UnsupportedOperationException();
	}

	public Lock makeLock(String name)
	{
		throw new UnsupportedOperationException();
	}

	public Directory.IndexInputSlicer createSlicer(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		if (!$assertionsDisabled && openForWrite)
			throw new AssertionError();
		String id = IndexFileNames.stripSegmentName(name);
		final FileEntry entry = (FileEntry)entries.get(id);
		if (entry == null)
			throw new FileNotFoundException((new StringBuilder()).append("No sub-file with id ").append(id).append(" found (fileName=").append(name).append(" files: ").append(entries.keySet()).append(")").toString());
		else
			return new Directory.IndexInputSlicer() {

				final FileEntry val$entry;
				final CompoundFileDirectory this$0;

				public void close()
				{
				}

				public IndexInput openSlice(String sliceDescription, long offset, long length)
					throws IOException
				{
					return handle.openSlice(sliceDescription, entry.offset + offset, length);
				}

				public IndexInput openFullSlice()
					throws IOException
				{
					return openSlice("full-slice", 0L, entry.length);
				}

			
			{
				this$0 = CompoundFileDirectory.this;
				entry = fileentry;
				super(CompoundFileDirectory.this);
			}
			};
	}

	public String toString()
	{
		return (new StringBuilder()).append("CompoundFileDirectory(file=\"").append(fileName).append("\" in dir=").append(directory).append(")").toString();
	}


}
