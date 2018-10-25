// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteArrayDataInput.java

package org.apache.lucene.store;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.store:
//			DataInput

public final class ByteArrayDataInput extends DataInput
{

	private byte bytes[];
	private int pos;
	private int limit;

	public ByteArrayDataInput(byte bytes[])
	{
		reset(bytes);
	}

	public ByteArrayDataInput(byte bytes[], int offset, int len)
	{
		reset(bytes, offset, len);
	}

	public ByteArrayDataInput()
	{
		reset(BytesRef.EMPTY_BYTES);
	}

	public void reset(byte bytes[])
	{
		reset(bytes, 0, bytes.length);
	}

	public void rewind()
	{
		pos = 0;
	}

	public int getPosition()
	{
		return pos;
	}

	public void setPosition(int pos)
	{
		this.pos = pos;
	}

	public void reset(byte bytes[], int offset, int len)
	{
		this.bytes = bytes;
		pos = offset;
		limit = offset + len;
	}

	public int length()
	{
		return limit;
	}

	public boolean eof()
	{
		return pos == limit;
	}

	public void skipBytes(int count)
	{
		pos += count;
	}

	public short readShort()
	{
		return (short)((bytes[pos++] & 0xff) << 8 | bytes[pos++] & 0xff);
	}

	public int readInt()
	{
		return (bytes[pos++] & 0xff) << 24 | (bytes[pos++] & 0xff) << 16 | (bytes[pos++] & 0xff) << 8 | bytes[pos++] & 0xff;
	}

	public long readLong()
	{
		int i1 = (bytes[pos++] & 0xff) << 24 | (bytes[pos++] & 0xff) << 16 | (bytes[pos++] & 0xff) << 8 | bytes[pos++] & 0xff;
		int i2 = (bytes[pos++] & 0xff) << 24 | (bytes[pos++] & 0xff) << 16 | (bytes[pos++] & 0xff) << 8 | bytes[pos++] & 0xff;
		return (long)i1 << 32 | (long)i2 & 0xffffffffL;
	}

	public int readVInt()
	{
		byte b = bytes[pos++];
		if (b >= 0)
			return b;
		int i = b & 0x7f;
		b = bytes[pos++];
		i |= (b & 0x7f) << 7;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= (b & 0x7f) << 14;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= (b & 0x7f) << 21;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= (b & 0xf) << 28;
		if ((b & 0xf0) == 0)
			return i;
		else
			throw new RuntimeException("Invalid vInt detected (too many bits)");
	}

	public long readVLong()
	{
		byte b = bytes[pos++];
		if (b >= 0)
			return (long)b;
		long i = (long)b & 127L;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 7;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 14;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 21;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 28;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 35;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 42;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 49;
		if (b >= 0)
			return i;
		b = bytes[pos++];
		i |= ((long)b & 127L) << 56;
		if (b >= 0)
			return i;
		else
			throw new RuntimeException("Invalid vLong detected (negative values disallowed)");
	}

	public byte readByte()
	{
		return bytes[pos++];
	}

	public void readBytes(byte b[], int offset, int len)
	{
		System.arraycopy(bytes, pos, b, offset, len);
		pos += len;
	}
}
