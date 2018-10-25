// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileAppender.java

package org.apache.log4j;

import java.io.*;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.ErrorHandler;

// Referenced classes of package org.apache.log4j:
//			WriterAppender, Layout

public class FileAppender extends WriterAppender
{

	protected boolean fileAppend;
	protected String fileName;
	protected boolean bufferedIO;
	protected int bufferSize;

	public FileAppender()
	{
		fileAppend = true;
		fileName = null;
		bufferedIO = false;
		bufferSize = 8192;
	}

	public FileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize)
		throws IOException
	{
		fileAppend = true;
		fileName = null;
		this.bufferedIO = false;
		this.bufferSize = 8192;
		this.layout = layout;
		setFile(filename, append, bufferedIO, bufferSize);
	}

	public FileAppender(Layout layout, String filename, boolean append)
		throws IOException
	{
		fileAppend = true;
		fileName = null;
		bufferedIO = false;
		bufferSize = 8192;
		this.layout = layout;
		setFile(filename, append, false, bufferSize);
	}

	public FileAppender(Layout layout, String filename)
		throws IOException
	{
		this(layout, filename, true);
	}

	public void setFile(String file)
	{
		String val = file.trim();
		fileName = val;
	}

	public boolean getAppend()
	{
		return fileAppend;
	}

	public String getFile()
	{
		return fileName;
	}

	public void activateOptions()
	{
		if (fileName != null)
		{
			try
			{
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
			}
			catch (IOException e)
			{
				errorHandler.error("setFile(" + fileName + "," + fileAppend + ") call failed.", e, 4);
			}
		} else
		{
			LogLog.warn("File option not set for appender [" + name + "].");
			LogLog.warn("Are you using FileAppender instead of ConsoleAppender?");
		}
	}

	protected void closeFile()
	{
		if (qw != null)
			try
			{
				qw.close();
			}
			catch (IOException e)
			{
				if (e instanceof InterruptedIOException)
					Thread.currentThread().interrupt();
				LogLog.error("Could not close " + qw, e);
			}
	}

	public boolean getBufferedIO()
	{
		return bufferedIO;
	}

	public int getBufferSize()
	{
		return bufferSize;
	}

	public void setAppend(boolean flag)
	{
		fileAppend = flag;
	}

	public void setBufferedIO(boolean bufferedIO)
	{
		this.bufferedIO = bufferedIO;
		if (bufferedIO)
			immediateFlush = false;
	}

	public void setBufferSize(int bufferSize)
	{
		this.bufferSize = bufferSize;
	}

	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
		throws IOException
	{
		LogLog.debug("setFile called: " + fileName + ", " + append);
		if (bufferedIO)
			setImmediateFlush(false);
		reset();
		FileOutputStream ostream = null;
		try
		{
			ostream = new FileOutputStream(fileName, append);
		}
		catch (FileNotFoundException ex)
		{
			String parentName = (new File(fileName)).getParent();
			if (parentName != null)
			{
				File parentDir = new File(parentName);
				if (!parentDir.exists() && parentDir.mkdirs())
					ostream = new FileOutputStream(fileName, append);
				else
					throw ex;
			} else
			{
				throw ex;
			}
		}
		Writer fw = createWriter(ostream);
		if (bufferedIO)
			fw = new BufferedWriter(fw, bufferSize);
		setQWForFiles(fw);
		this.fileName = fileName;
		fileAppend = append;
		this.bufferedIO = bufferedIO;
		this.bufferSize = bufferSize;
		writeHeader();
		LogLog.debug("setFile ended");
	}

	protected void setQWForFiles(Writer writer)
	{
		qw = new QuietWriter(writer, errorHandler);
	}

	protected void reset()
	{
		closeFile();
		fileName = null;
		super.reset();
	}
}
