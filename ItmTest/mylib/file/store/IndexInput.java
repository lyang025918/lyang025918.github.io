// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexInput.java

package mylib.file.store;

import java.io.Closeable;
import java.io.IOException;

// Referenced classes of package mylib.file.store:
//			DataInput, BufferedIndexInput, IndexOutput

public abstract class IndexInput extends DataInput
	implements Cloneable, Closeable
{

	private final String resourceDescription;
	static final boolean $assertionsDisabled = !mylib/file/store/IndexInput.desiredAssertionStatus();

	/**
	 * @deprecated Method skipChars is deprecated
	 */

	public void skipChars(int length)
		throws IOException
	{
		for (int i = 0; i < length; i++)
		{
			byte b = readByte();
			if ((b & 0x80) == 0)
				continue;
			if ((b & 0xe0) != 224)
			{
				readByte();
			} else
			{
				readByte();
				readByte();
			}
		}

	}

	/**
	 * @deprecated Method IndexInput is deprecated
	 */

	protected IndexInput()
	{
		this("anonymous IndexInput");
	}

	protected IndexInput(String resourceDescription)
	{
		if (resourceDescription == null)
		{
			throw new IllegalArgumentException("resourceDescription must not be null");
		} else
		{
			this.resourceDescription = resourceDescription;
			return;
		}
	}

	public abstract void close()
		throws IOException;

	public abstract long getFilePointer();

	public abstract void seek(long l)
		throws IOException;

	public abstract long length();

	public void copyBytes(IndexOutput out, long numBytes)
		throws IOException
	{
		if (!$assertionsDisabled && numBytes < 0L)
			throw new AssertionError((new StringBuilder()).append("numBytes=").append(numBytes).toString());
		byte copyBuf[] = new byte[1024];
		int toCopy;
		for (; numBytes > 0L; numBytes -= toCopy)
		{
			toCopy = (int)(numBytes <= (long)copyBuf.length ? numBytes : copyBuf.length);
			readBytes(copyBuf, 0, toCopy);
			out.writeBytes(copyBuf, 0, toCopy);
		}

	}

	public String toString()
	{
		return resourceDescription;
	}

}
