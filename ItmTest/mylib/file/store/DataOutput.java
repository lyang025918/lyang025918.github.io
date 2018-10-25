// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DataOutput.java

package mylib.file.store;

import java.io.IOException;
import java.util.*;

// Referenced classes of package mylib.file.store:
//			BytesRef, UnicodeUtil, DataInput

public abstract class DataOutput
{

	private static int COPY_BUFFER_SIZE = 16384;
	private byte copyBuffer[];
	static final boolean $assertionsDisabled = !mylib/file/store/DataOutput.desiredAssertionStatus();

	public DataOutput()
	{
	}

	public abstract void writeByte(byte byte0)
		throws IOException;

	public void writeBytes(byte b[], int length)
		throws IOException
	{
		writeBytes(b, 0, length);
	}

	public abstract void writeBytes(byte abyte0[], int i, int j)
		throws IOException;

	public void writeInt(int i)
		throws IOException
	{
		writeByte((byte)(i >> 24));
		writeByte((byte)(i >> 16));
		writeByte((byte)(i >> 8));
		writeByte((byte)i);
	}

	public void writeShort(short i)
		throws IOException
	{
		writeByte((byte)(i >> 8));
		writeByte((byte)i);
	}

	public final void writeVInt(int i)
		throws IOException
	{
		for (; (i & 0xffffff80) != 0; i >>>= 7)
			writeByte((byte)(i & 0x7f | 0x80));

		writeByte((byte)i);
	}

	public void writeLong(long i)
		throws IOException
	{
		writeInt((int)(i >> 32));
		writeInt((int)i);
	}

	public final void writeVLong(long i)
		throws IOException
	{
		if (!$assertionsDisabled && i < 0L)
			throw new AssertionError();
		for (; (i & -128L) != 0L; i >>>= 7)
			writeByte((byte)(int)(i & 127L | 128L));

		writeByte((byte)(int)i);
	}

	public void writeString(String s)
		throws IOException
	{
		BytesRef utf8Result = new BytesRef(10);
		UnicodeUtil.UTF16toUTF8(s, 0, s.length(), utf8Result);
		writeVInt(utf8Result.length);
		writeBytes(utf8Result.bytes, 0, utf8Result.length);
	}

	public void copyBytes(DataInput input, long numBytes)
		throws IOException
	{
		if (!$assertionsDisabled && numBytes < 0L)
			throw new AssertionError((new StringBuilder()).append("numBytes=").append(numBytes).toString());
		long left = numBytes;
		if (copyBuffer == null)
			copyBuffer = new byte[COPY_BUFFER_SIZE];
		int toCopy;
		for (; left > 0L; left -= toCopy)
		{
			if (left > (long)COPY_BUFFER_SIZE)
				toCopy = COPY_BUFFER_SIZE;
			else
				toCopy = (int)left;
			input.readBytes(copyBuffer, 0, toCopy);
			writeBytes(copyBuffer, 0, toCopy);
		}

	}

	/**
	 * @deprecated Method writeChars is deprecated
	 */

	public void writeChars(String s, int start, int length)
		throws IOException
	{
		int end = start + length;
		for (int i = start; i < end; i++)
		{
			int code = s.charAt(i);
			if (code >= 1 && code <= 127)
			{
				writeByte((byte)code);
				continue;
			}
			if (code >= 128 && code <= 2047 || code == 0)
			{
				writeByte((byte)(0xc0 | code >> 6));
				writeByte((byte)(0x80 | code & 0x3f));
			} else
			{
				writeByte((byte)(0xe0 | code >>> 12));
				writeByte((byte)(0x80 | code >> 6 & 0x3f));
				writeByte((byte)(0x80 | code & 0x3f));
			}
		}

	}

	/**
	 * @deprecated Method writeChars is deprecated
	 */

	public void writeChars(char s[], int start, int length)
		throws IOException
	{
		int end = start + length;
		for (int i = start; i < end; i++)
		{
			int code = s[i];
			if (code >= 1 && code <= 127)
			{
				writeByte((byte)code);
				continue;
			}
			if (code >= 128 && code <= 2047 || code == 0)
			{
				writeByte((byte)(0xc0 | code >> 6));
				writeByte((byte)(0x80 | code & 0x3f));
			} else
			{
				writeByte((byte)(0xe0 | code >>> 12));
				writeByte((byte)(0x80 | code >> 6 & 0x3f));
				writeByte((byte)(0x80 | code & 0x3f));
			}
		}

	}

	public void writeStringStringMap(Map map)
		throws IOException
	{
		if (map == null)
		{
			writeInt(0);
		} else
		{
			writeInt(map.size());
			java.util.Map.Entry entry;
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); writeString((String)entry.getValue()))
			{
				entry = (java.util.Map.Entry)iterator.next();
				writeString((String)entry.getKey());
			}

		}
	}

	public void writeStringSet(Set set)
		throws IOException
	{
		if (set == null)
		{
			writeInt(0);
		} else
		{
			writeInt(set.size());
			String value;
			for (Iterator iterator = set.iterator(); iterator.hasNext(); writeString(value))
				value = (String)iterator.next();

		}
	}

}
