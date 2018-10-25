// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBufferedInput.java

package com.iflytek.itm.search.sil;

import java.io.*;
import org.apache.lucene.store.DataInput;

// Referenced classes of package com.iflytek.itm.search.sil:
//			ItemBufferedOutput

public class ItemBufferedInput extends DataInput
{

	protected byte buffer[];
	private int bufferPosition;

	public ItemBufferedInput(byte buffer[])
	{
		this.buffer = null;
		bufferPosition = 0;
		this.buffer = buffer;
	}

	public byte readByte()
		throws IOException
	{
		check();
		return buffer[bufferPosition++];
	}

	public void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		int bytesLeft = buffer.length - bufferPosition;
		if (len <= bytesLeft)
		{
			if (len > 0)
				System.arraycopy(buffer, bufferPosition, b, offset, len);
			bufferPosition += len;
		} else
		{
			throw new EOFException((new StringBuilder()).append("Error, come on, read past over the end !! available=").append(bytesLeft).append(", length=").append(len).toString());
		}
	}

	protected void check()
		throws IOException
	{
		if (buffer == null)
			throw new EOFException("Error, come on, the buffer you put in is null !!");
		if (bufferPosition >= buffer.length)
			throw new EOFException((new StringBuilder()).append("Error, come on, read past over the end !! position=").append(bufferPosition).append(", length=").append(buffer.length).toString());
		else
			return;
	}

	public static void main(String args[])
		throws Exception
	{
		int a = 198;
		String b = "wgggfiy is my name";
		ItemBufferedOutput buffer = new ItemBufferedOutput();
		buffer.writeVInt(a);
		buffer.writeString(b);
		byte bytes[] = buffer.data();
		ItemBufferedInput input = new ItemBufferedInput(bytes);
		int a_in = input.readVInt();
		String b_in = input.readString();
		System.out.println((new StringBuilder()).append("a_in=").append(a_in).append(", b_in=").append(b_in).toString());
	}
}
