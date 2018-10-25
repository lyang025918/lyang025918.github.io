// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChecksumIndexOutput.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

// Referenced classes of package org.apache.lucene.store:
//			IndexOutput

public class ChecksumIndexOutput extends IndexOutput
{

	IndexOutput main;
	Checksum digest;

	public ChecksumIndexOutput(IndexOutput main)
	{
		this.main = main;
		digest = new CRC32();
	}

	public void writeByte(byte b)
		throws IOException
	{
		digest.update(b);
		main.writeByte(b);
	}

	public void writeBytes(byte b[], int offset, int length)
		throws IOException
	{
		digest.update(b, offset, length);
		main.writeBytes(b, offset, length);
	}

	public long getChecksum()
	{
		return digest.getValue();
	}

	public void flush()
		throws IOException
	{
		main.flush();
	}

	public void close()
		throws IOException
	{
		main.close();
	}

	public long getFilePointer()
	{
		return main.getFilePointer();
	}

	public void seek(long pos)
	{
		throw new UnsupportedOperationException();
	}

	public void finishCommit()
		throws IOException
	{
		main.writeLong(getChecksum());
	}

	public long length()
		throws IOException
	{
		return main.length();
	}
}
