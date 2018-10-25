// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileLock.java

package IceUtilInternal;

import IceUtil.FileLockException;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.channels.FileChannel;

public final class FileLock
{

	private File _file;
	private RandomAccessFile _randFile;

	public FileLock(String path)
	{
		_file = new File(path);
		FileChannel channel;
		try
		{
			_randFile = new RandomAccessFile(_file, "rw");
			channel = _randFile.getChannel();
		}
		catch (FileNotFoundException e)
		{
			throw new FileLockException(path);
		}
		java.nio.channels.FileLock lock;
		try
		{
			lock = channel.tryLock();
		}
		catch (Exception ex)
		{
			throw new FileLockException(path, ex);
		}
		if (lock == null)
			throw new FileLockException(path);
		if (!System.getProperty("os.name").startsWith("Windows"))
			try
			{
				_randFile.writeUTF(ManagementFactory.getRuntimeMXBean().getName());
			}
			catch (IOException ex)
			{
				release();
				throw new FileLockException(path);
			}
	}

	public void release()
	{
		if (System.getProperty("os.name").startsWith("Windows") && _randFile != null)
		{
			try
			{
				_randFile.close();
			}
			catch (IOException ex) { }
			_randFile = null;
		}
		if (_file != null)
		{
			_file.delete();
			_file = null;
		}
	}
}
