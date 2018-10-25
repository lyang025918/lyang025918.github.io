// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSIndexOutput.java

package mylib.file;

import java.io.*;
import mylib.file.store.BufferedIndexOutput;

public class FSIndexOutput extends BufferedIndexOutput
{

	private final String path;
	private final RandomAccessFile file;
	private volatile boolean isOpen;

	public FSIndexOutput(String path)
		throws IOException
	{
		this.path = path;
		file = new RandomAccessFile(new File(path), "rw");
		file.setLength(0L);
		isOpen = true;
	}

	public void flushBuffer(byte b[], int offset, int size)
		throws IOException
	{
		file.write(b, offset, size);
	}

	public void close()
		throws IOException
	{
		boolean success;
		if (!isOpen)
			break MISSING_BLOCK_LABEL_82;
		success = false;
		super.close();
		success = true;
		isOpen = false;
		if (!success)
			try
			{
				file.close();
			}
			catch (Throwable t) { }
		else
			file.close();
		break MISSING_BLOCK_LABEL_82;
		Exception exception;
		exception;
		isOpen = false;
		if (!success)
			try
			{
				file.close();
			}
			catch (Throwable t) { }
		else
			file.close();
		throw exception;
	}

	public void seek(long pos)
		throws IOException
	{
		super.seek(pos);
		file.seek(pos);
	}

	public long length()
		throws IOException
	{
		return file.length();
	}

	public void setLength(long length)
		throws IOException
	{
		file.setLength(length);
	}

	public static int getByteCnt(String str)
	{
		int cnt = 0;
		try
		{
			byte bytes[] = str.getBytes("UTF-8");
			cnt += getByteCnt(bytes.length);
			cnt += bytes.length;
		}
		catch (Exception e) { }
		return cnt;
	}

	public static int getByteCnt(int i)
	{
		int cnt = 0;
		for (; (i & 0xffffff80) != 0; i >>>= 7)
			cnt++;

		return ++cnt;
	}

	public static void main(String args[])
	{
		try
		{
			FSIndexOutput fio = new FSIndexOutput("test.dat");
			String str = "Œ“";
			fio.writeString(str);
			int cnt = getByteCnt(str);
			fio.setLength(1024L);
			fio.seek(1024L);
			fio.close();
		}
		catch (IOException e)
		{
			System.out.printf("Error: %s\n", new Object[] {
				e.getMessage()
			});
			e.printStackTrace();
		}
	}
}
