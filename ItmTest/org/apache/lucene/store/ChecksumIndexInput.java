// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChecksumIndexInput.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

// Referenced classes of package org.apache.lucene.store:
//			IndexInput

public class ChecksumIndexInput extends IndexInput
{

	IndexInput main;
	Checksum digest;

	public ChecksumIndexInput(IndexInput main)
	{
		super((new StringBuilder()).append("ChecksumIndexInput(").append(main).append(")").toString());
		this.main = main;
		digest = new CRC32();
	}

	public byte readByte()
		throws IOException
	{
		byte b = main.readByte();
		digest.update(b);
		return b;
	}

	public void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		main.readBytes(b, offset, len);
		digest.update(b, offset, len);
	}

	public long getChecksum()
	{
		return digest.getValue();
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

	public long length()
	{
		return main.length();
	}
}
