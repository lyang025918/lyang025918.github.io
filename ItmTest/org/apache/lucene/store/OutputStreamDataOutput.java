// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputStreamDataOutput.java

package org.apache.lucene.store;

import java.io.*;

// Referenced classes of package org.apache.lucene.store:
//			DataOutput

public class OutputStreamDataOutput extends DataOutput
	implements Closeable
{

	private final OutputStream os;

	public OutputStreamDataOutput(OutputStream os)
	{
		this.os = os;
	}

	public void writeByte(byte b)
		throws IOException
	{
		os.write(b);
	}

	public void writeBytes(byte b[], int offset, int length)
		throws IOException
	{
		os.write(b, offset, length);
	}

	public void close()
		throws IOException
	{
		os.close();
	}
}
