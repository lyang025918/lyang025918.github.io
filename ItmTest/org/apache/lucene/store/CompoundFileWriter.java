// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompoundFileWriter.java

package org.apache.lucene.store;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.index.IndexFileNames;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.store:
//			AlreadyClosedException, Directory, IndexOutput, IndexInput, 
//			IOContext

final class CompoundFileWriter
	implements Closeable
{
	private final class DirectCFSIndexOutput extends IndexOutput
	{

		private final IndexOutput delegate;
		private final long offset;
		private boolean closed;
		private FileEntry entry;
		private long writtenBytes;
		private final boolean isSeparate;
		static final boolean $assertionsDisabled = !org/apache/lucene/store/CompoundFileWriter.desiredAssertionStatus();
		final CompoundFileWriter this$0;

		public void flush()
			throws IOException
		{
			delegate.flush();
		}

		public void close()
			throws IOException
		{
			if (!closed)
			{
				closed = true;
				entry.length = writtenBytes;
				if (isSeparate)
				{
					delegate.close();
					pendingEntries.add(entry);
				} else
				{
					releaseOutputLock();
				}
				prunePendingEntries();
			}
		}

		public long getFilePointer()
		{
			return delegate.getFilePointer() - offset;
		}

		public void seek(long pos)
			throws IOException
		{
			if (!$assertionsDisabled && closed)
			{
				throw new AssertionError();
			} else
			{
				delegate.seek(offset + pos);
				return;
			}
		}

		public long length()
			throws IOException
		{
			if (!$assertionsDisabled && closed)
				throw new AssertionError();
			else
				return delegate.length() - offset;
		}

		public void writeByte(byte b)
			throws IOException
		{
			if (!$assertionsDisabled && closed)
			{
				throw new AssertionError();
			} else
			{
				writtenBytes++;
				delegate.writeByte(b);
				return;
			}
		}

		public void writeBytes(byte b[], int offset, int length)
			throws IOException
		{
			if (!$assertionsDisabled && closed)
			{
				throw new AssertionError();
			} else
			{
				writtenBytes += length;
				delegate.writeBytes(b, offset, length);
				return;
			}
		}


		DirectCFSIndexOutput(IndexOutput delegate, FileEntry entry, boolean isSeparate)
		{
			this$0 = CompoundFileWriter.this;
			super();
			this.delegate = delegate;
			this.entry = entry;
			entry.offset = offset = delegate.getFilePointer();
			this.isSeparate = isSeparate;
		}
	}

	private static final class FileEntry
	{

		String file;
		long length;
		long offset;
		Directory dir;

		private FileEntry()
		{
		}

	}


	static final int FORMAT_PRE_VERSION = 0;
	static final int FORMAT_NO_SEGMENT_PREFIX = -1;
	static final String DATA_CODEC = "CompoundFileWriterData";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;
	static final String ENTRY_CODEC = "CompoundFileWriterEntries";
	private final Directory directory;
	private final Map entries = new HashMap();
	private final Set seenIDs = new HashSet();
	private final Queue pendingEntries = new LinkedList();
	private boolean closed;
	private IndexOutput dataOut;
	private final AtomicBoolean outputTaken = new AtomicBoolean(false);
	final String entryTableName;
	final String dataFileName;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/CompoundFileWriter.desiredAssertionStatus();

	CompoundFileWriter(Directory dir, String name)
	{
		closed = false;
		if (dir == null)
			throw new NullPointerException("directory cannot be null");
		if (name == null)
		{
			throw new NullPointerException("name cannot be null");
		} else
		{
			directory = dir;
			entryTableName = IndexFileNames.segmentFileName(IndexFileNames.stripExtension(name), "", "cfe");
			dataFileName = name;
			return;
		}
	}

	private synchronized IndexOutput getOutput()
		throws IOException
	{
		boolean success;
		if (dataOut != null)
			break MISSING_BLOCK_LABEL_81;
		success = false;
		dataOut = directory.createOutput(dataFileName, IOContext.DEFAULT);
		CodecUtil.writeHeader(dataOut, "CompoundFileWriterData", 0);
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				dataOut
			});
		break MISSING_BLOCK_LABEL_81;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				dataOut
			});
		throw exception;
		return dataOut;
	}

	Directory getDirectory()
	{
		return directory;
	}

	String getName()
	{
		return dataFileName;
	}

	public void close()
		throws IOException
	{
		IOException priorException;
		IndexOutput entryTableOut;
		if (closed)
			return;
		priorException = null;
		entryTableOut = null;
		if (!pendingEntries.isEmpty() || outputTaken.get())
			throw new IllegalStateException("CFS has pending open files");
		closed = true;
		getOutput();
		if (!$assertionsDisabled && dataOut == null)
			throw new AssertionError();
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			dataOut
		});
		break MISSING_BLOCK_LABEL_134;
		IOException e;
		e;
		priorException = e;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			dataOut
		});
		break MISSING_BLOCK_LABEL_134;
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			dataOut
		});
		throw exception;
		entryTableOut = directory.createOutput(entryTableName, IOContext.DEFAULT);
		writeEntryTable(entries.values(), entryTableOut);
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			entryTableOut
		});
		break MISSING_BLOCK_LABEL_213;
		e;
		priorException = e;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			entryTableOut
		});
		break MISSING_BLOCK_LABEL_213;
		Exception exception1;
		exception1;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			entryTableOut
		});
		throw exception1;
	}

	private final void ensureOpen()
	{
		if (closed)
			throw new AlreadyClosedException("CFS Directory is already closed");
		else
			return;
	}

	private final long copyFileEntry(IndexOutput dataOut, FileEntry fileEntry)
		throws IOException
	{
		IndexInput is;
		boolean success;
		is = fileEntry.dir.openInput(fileEntry.file, IOContext.READONCE);
		success = false;
		long l;
		long startPtr = dataOut.getFilePointer();
		long length = fileEntry.length;
		dataOut.copyBytes(is, length);
		long endPtr = dataOut.getFilePointer();
		long diff = endPtr - startPtr;
		if (diff != length)
			throw new IOException((new StringBuilder()).append("Difference in the output file offsets ").append(diff).append(" does not match the original file length ").append(length).toString());
		fileEntry.offset = startPtr;
		success = true;
		l = length;
		if (success)
		{
			IOUtils.close(new Closeable[] {
				is
			});
			fileEntry.dir.deleteFile(fileEntry.file);
		} else
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				is
			});
		}
		return l;
		Exception exception;
		exception;
		if (success)
		{
			IOUtils.close(new Closeable[] {
				is
			});
			fileEntry.dir.deleteFile(fileEntry.file);
		} else
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				is
			});
		}
		throw exception;
	}

	protected void writeEntryTable(Collection entries, IndexOutput entryOut)
		throws IOException
	{
		CodecUtil.writeHeader(entryOut, "CompoundFileWriterEntries", 0);
		entryOut.writeVInt(entries.size());
		FileEntry fe;
		for (Iterator i$ = entries.iterator(); i$.hasNext(); entryOut.writeLong(fe.length))
		{
			fe = (FileEntry)i$.next();
			entryOut.writeString(IndexFileNames.stripSegmentName(fe.file));
			entryOut.writeLong(fe.offset);
		}

	}

	IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		boolean success;
		boolean outputLocked;
		ensureOpen();
		success = false;
		outputLocked = false;
		DirectCFSIndexOutput directcfsindexoutput;
		if (!$assertionsDisabled && name == null)
			throw new AssertionError("name must not be null");
		if (entries.containsKey(name))
			throw new IllegalArgumentException((new StringBuilder()).append("File ").append(name).append(" already exists").toString());
		FileEntry entry = new FileEntry();
		entry.file = name;
		entries.put(name, entry);
		String id = IndexFileNames.stripSegmentName(name);
		if (!$assertionsDisabled && seenIDs.contains(id))
			throw new AssertionError((new StringBuilder()).append("file=\"").append(name).append("\" maps to id=\"").append(id).append("\", which was already written").toString());
		seenIDs.add(id);
		DirectCFSIndexOutput out;
		if (outputLocked = outputTaken.compareAndSet(false, true))
		{
			out = new DirectCFSIndexOutput(getOutput(), entry, false);
		} else
		{
			entry.dir = directory;
			if (directory.fileExists(name))
				throw new IllegalArgumentException((new StringBuilder()).append("File ").append(name).append(" already exists").toString());
			out = new DirectCFSIndexOutput(directory.createOutput(name, context), entry, true);
		}
		success = true;
		directcfsindexoutput = out;
		if (!success)
		{
			entries.remove(name);
			if (outputLocked)
			{
				if (!$assertionsDisabled && !outputTaken.get())
					throw new AssertionError();
				releaseOutputLock();
			}
		}
		return directcfsindexoutput;
		Exception exception;
		exception;
		if (!success)
		{
			entries.remove(name);
			if (outputLocked)
			{
				if (!$assertionsDisabled && !outputTaken.get())
					throw new AssertionError();
				releaseOutputLock();
			}
		}
		throw exception;
	}

	final void releaseOutputLock()
	{
		outputTaken.compareAndSet(true, false);
	}

	private final void prunePendingEntries()
		throws IOException
	{
		if (!outputTaken.compareAndSet(false, true))
			break MISSING_BLOCK_LABEL_127;
		FileEntry entry;
		for (; !pendingEntries.isEmpty(); entries.put(entry.file, entry))
		{
			entry = (FileEntry)pendingEntries.poll();
			copyFileEntry(getOutput(), entry);
		}

		boolean compareAndSet = outputTaken.compareAndSet(true, false);
		if (!$assertionsDisabled && !compareAndSet)
			throw new AssertionError();
		break MISSING_BLOCK_LABEL_127;
		Exception exception;
		exception;
		boolean compareAndSet = outputTaken.compareAndSet(true, false);
		if (!$assertionsDisabled && !compareAndSet)
			throw new AssertionError();
		else
			throw exception;
	}

	long fileLength(String name)
		throws IOException
	{
		FileEntry fileEntry = (FileEntry)entries.get(name);
		if (fileEntry == null)
			throw new FileNotFoundException((new StringBuilder()).append(name).append(" does not exist").toString());
		else
			return fileEntry.length;
	}

	boolean fileExists(String name)
	{
		return entries.containsKey(name);
	}

	String[] listAll()
	{
		return (String[])entries.keySet().toArray(new String[0]);
	}



}
