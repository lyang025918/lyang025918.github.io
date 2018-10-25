// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputStreamDataInput.java

package org.apache.lucene.store;

import java.io.*;

// Referenced classes of package org.apache.lucene.store:
//			DataInput

public class InputStreamDataInput extends DataInput
	implements Closeable
{

	private final InputStream is;

	public InputStreamDataInput(InputStream is)
	{
		this.is = is;
	}

	public byte readByte()
		throws IOException
	{
		int v = is.read();
		if (v == -1)
			throw new EOFException();
		else
			return (byte)v;
	}

	public void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		while (len > 0) 
		{
			int cnt = is.read(b, offset, len);
			if (cnt < 0)
				throw new EOFException();
			len -= cnt;
			offset += cnt;
		}
	}

	public void close()
		throws IOException
	{
		is.close();
	}
}
