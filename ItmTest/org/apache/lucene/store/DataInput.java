// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DataInput.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.IOUtils;

public abstract class DataInput
	implements Cloneable
{

	public DataInput()
	{
	}

	public abstract byte readByte()
		throws IOException;

	public abstract void readBytes(byte abyte0[], int i, int j)
		throws IOException;

	public void readBytes(byte b[], int offset, int len, boolean useBuffer)
		throws IOException
	{
		readBytes(b, offset, len);
	}

	public short readShort()
		throws IOException
	{
		return (short)((readByte() & 0xff) << 8 | readByte() & 0xff);
	}

	public int readInt()
		throws IOException
	{
		return (readByte() & 0xff) << 24 | (readByte() & 0xff) << 16 | (readByte() & 0xff) << 8 | readByte() & 0xff;
	}

	public int readVInt()
		throws IOException
	{
		byte b = readByte();
		if (b >= 0)
			return b;
		int i = b & 0x7f;
		b = readByte();
		i |= (b & 0x7f) << 7;
		if (b >= 0)
			return i;
		b = readByte();
		i |= (b & 0x7f) << 14;
		if (b >= 0)
			return i;
		b = readByte();
		i |= (b & 0x7f) << 21;
		if (b >= 0)
			return i;
		b = readByte();
		i |= (b & 0xf) << 28;
		if ((b & 0xf0) == 0)
			return i;
		else
			throw new IOException("Invalid vInt detected (too many bits)");
	}

	public long readLong()
		throws IOException
	{
		return (long)readInt() << 32 | (long)readInt() & 0xffffffffL;
	}

	public long readVLong()
		throws IOException
	{
		byte b = readByte();
		if (b >= 0)
			return (long)b;
		long i = (long)b & 127L;
		b = readByte();
		i |= ((long)b & 127L) << 7;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 14;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 21;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 28;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 35;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 42;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 49;
		if (b >= 0)
			return i;
		b = readByte();
		i |= ((long)b & 127L) << 56;
		if (b >= 0)
			return i;
		else
			throw new IOException("Invalid vLong detected (negative values disallowed)");
	}

	public String readString()
		throws IOException
	{
		int length = readVInt();
		byte bytes[] = new byte[length];
		readBytes(bytes, 0, length);
		return new String(bytes, 0, length, IOUtils.CHARSET_UTF_8);
	}

	public DataInput clone()
	{
		return (DataInput)super.clone();
		CloneNotSupportedException e;
		e;
		throw new Error("This cannot happen: Failing to clone DataInput");
	}

	public Map readStringStringMap()
		throws IOException
	{
		Map map = new HashMap();
		int count = readInt();
		for (int i = 0; i < count; i++)
		{
			String key = readString();
			String val = readString();
			map.put(key, val);
		}

		return map;
	}

	public Set readStringSet()
		throws IOException
	{
		Set set = new HashSet();
		int count = readInt();
		for (int i = 0; i < count; i++)
			set.add(readString());

		return set;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
