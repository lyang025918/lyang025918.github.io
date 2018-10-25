// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MMapDirectory.java

package org.apache.lucene.store;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.*;
import org.apache.lucene.util.Constants;

// Referenced classes of package org.apache.lucene.store:
//			FSDirectory, LockFactory, IOContext, Directory, 
//			IndexInput, ByteBufferIndexInput

public class MMapDirectory extends FSDirectory
{
	private final class MMapIndexInput extends ByteBufferIndexInput
	{

		final MMapDirectory this$0;

		protected void freeBuffer(ByteBuffer buffer)
			throws IOException
		{
			cleanMapping(buffer);
		}

		MMapIndexInput(String resourceDescription, RandomAccessFile raf)
			throws IOException
		{
			this$0 = MMapDirectory.this;
			super(resourceDescription, map(raf, 0L, raf.length()), raf.length(), chunkSizePower);
		}
	}


	private boolean useUnmapHack;
	public static final int DEFAULT_MAX_BUFF;
	final int chunkSizePower;
	public static final boolean UNMAP_SUPPORTED;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/MMapDirectory.desiredAssertionStatus();

	public MMapDirectory(File path, LockFactory lockFactory)
		throws IOException
	{
		this(path, lockFactory, DEFAULT_MAX_BUFF);
	}

	public MMapDirectory(File path)
		throws IOException
	{
		this(path, null);
	}

	public MMapDirectory(File path, LockFactory lockFactory, int maxChunkSize)
		throws IOException
	{
		super(path, lockFactory);
		useUnmapHack = UNMAP_SUPPORTED;
		if (maxChunkSize <= 0)
			throw new IllegalArgumentException("Maximum chunk size for mmap must be >0");
		chunkSizePower = 31 - Integer.numberOfLeadingZeros(maxChunkSize);
		if (!$assertionsDisabled && (chunkSizePower < 0 || chunkSizePower > 30))
			throw new AssertionError();
		else
			return;
	}

	public void setUseUnmap(boolean useUnmapHack)
	{
		if (useUnmapHack && !UNMAP_SUPPORTED)
		{
			throw new IllegalArgumentException("Unmap hack not supported on this platform!");
		} else
		{
			this.useUnmapHack = useUnmapHack;
			return;
		}
	}

	public boolean getUseUnmap()
	{
		return useUnmapHack;
	}

	final void cleanMapping(final ByteBuffer buffer)
		throws IOException
	{
		if (useUnmapHack)
			try
			{
				AccessController.doPrivileged(new PrivilegedExceptionAction() {

					final ByteBuffer val$buffer;
					final MMapDirectory this$0;

					public Object run()
						throws Exception
					{
						Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
						getCleanerMethod.setAccessible(true);
						Object cleaner = getCleanerMethod.invoke(buffer, new Object[0]);
						if (cleaner != null)
							cleaner.getClass().getMethod("clean", new Class[0]).invoke(cleaner, new Object[0]);
						return null;
					}

			
			{
				this$0 = MMapDirectory.this;
				buffer = bytebuffer;
				super();
			}
				});
			}
			catch (PrivilegedActionException e)
			{
				IOException ioe = new IOException("unable to unmap the mapped buffer");
				ioe.initCause(e.getCause());
				throw ioe;
			}
	}

	public final int getMaxChunkSize()
	{
		return 1 << chunkSizePower;
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		File f;
		RandomAccessFile raf;
		ensureOpen();
		f = new File(getDirectory(), name);
		raf = new RandomAccessFile(f, "r");
		MMapIndexInput mmapindexinput = new MMapIndexInput((new StringBuilder()).append("MMapIndexInput(path=\"").append(f).append("\")").toString(), raf);
		raf.close();
		return mmapindexinput;
		Exception exception;
		exception;
		raf.close();
		throw exception;
	}

	public Directory.IndexInputSlicer createSlicer(String name, IOContext context)
		throws IOException
	{
		final MMapIndexInput full = (MMapIndexInput)openInput(name, context);
		return new Directory.IndexInputSlicer() {

			final MMapIndexInput val$full;
			final MMapDirectory this$0;

			public IndexInput openSlice(String sliceDescription, long offset, long length)
				throws IOException
			{
				ensureOpen();
				return full.slice(sliceDescription, offset, length);
			}

			public IndexInput openFullSlice()
				throws IOException
			{
				ensureOpen();
				return full.clone();
			}

			public void close()
				throws IOException
			{
				full.close();
			}

			
			{
				this$0 = MMapDirectory.this;
				full = mmapindexinput;
				super(MMapDirectory.this);
			}
		};
	}

	ByteBuffer[] map(RandomAccessFile raf, long offset, long length)
		throws IOException
	{
		if (length >>> chunkSizePower >= 0x7fffffffL)
			throw new IllegalArgumentException((new StringBuilder()).append("RandomAccessFile too big for chunk size: ").append(raf.toString()).toString());
		long chunkSize = 1L << chunkSizePower;
		int nrBuffers = (int)(length >>> chunkSizePower) + 1;
		ByteBuffer buffers[] = new ByteBuffer[nrBuffers];
		long bufferStart = 0L;
		FileChannel rafc = raf.getChannel();
		for (int bufNr = 0; bufNr < nrBuffers; bufNr++)
		{
			int bufSize = (int)(length <= bufferStart + chunkSize ? length - bufferStart : chunkSize);
			buffers[bufNr] = rafc.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, offset + bufferStart, bufSize);
			bufferStart += bufSize;
		}

		return buffers;
	}

	static 
	{
		DEFAULT_MAX_BUFF = Constants.JRE_IS_64BIT ? 0x40000000 : 0x10000000;
		boolean v;
		try
		{
			Class.forName("sun.misc.Cleaner");
			Class.forName("java.nio.DirectByteBuffer").getMethod("cleaner", new Class[0]);
			v = true;
		}
		catch (Exception e)
		{
			v = false;
		}
		UNMAP_SUPPORTED = v;
	}
}
