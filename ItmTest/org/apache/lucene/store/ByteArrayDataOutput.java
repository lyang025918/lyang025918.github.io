// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteArrayDataOutput.java

package org.apache.lucene.store;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.store:
//			DataOutput

public class ByteArrayDataOutput extends DataOutput
{

	private byte bytes[];
	private int pos;
	private int limit;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/ByteArrayDataOutput.desiredAssertionStatus();

	public ByteArrayDataOutput(byte bytes[])
	{
		reset(bytes);
	}

	public ByteArrayDataOutput(byte bytes[], int offset, int len)
	{
		reset(bytes, offset, len);
	}

	public ByteArrayDataOutput()
	{
		reset(BytesRef.EMPTY_BYTES);
	}

	public void reset(byte bytes[])
	{
		reset(bytes, 0, bytes.length);
	}

	public void reset(byte bytes[], int offset, int len)
	{
		this.bytes = bytes;
		pos = offset;
		limit = offset + len;
	}

	public int getPosition()
	{
		return pos;
	}

	public void writeByte(byte b)
	{
		if (!$assertionsDisabled && pos >= limit)
		{
			throw new AssertionError();
		} else
		{
			bytes[pos++] = b;
			return;
		}
	}

	public void writeBytes(byte b[], int offset, int length)
	{
		if (!$assertionsDisabled && pos + length > limit)
		{
			throw new AssertionError();
		} else
		{
			System.arraycopy(b, offset, bytes, pos, length);
			pos += length;
			return;
		}
	}

}
