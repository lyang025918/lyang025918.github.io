// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrintStreamInfoStream.java

package org.apache.lucene.util;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

// Referenced classes of package org.apache.lucene.util:
//			InfoStream

public class PrintStreamInfoStream extends InfoStream
{

	private static final AtomicInteger MESSAGE_ID = new AtomicInteger();
	protected final int messageID;
	protected final PrintStream stream;

	public PrintStreamInfoStream(PrintStream stream)
	{
		this(stream, MESSAGE_ID.getAndIncrement());
	}

	public PrintStreamInfoStream(PrintStream stream, int messageID)
	{
		this.stream = stream;
		this.messageID = messageID;
	}

	public void message(String component, String message)
	{
		stream.println((new StringBuilder()).append(component).append(" ").append(messageID).append(" [").append(new Date()).append("; ").append(Thread.currentThread().getName()).append("]: ").append(message).toString());
	}

	public boolean isEnabled(String component)
	{
		return true;
	}

	public void close()
		throws IOException
	{
		if (!isSystemStream())
			stream.close();
	}

	public boolean isSystemStream()
	{
		return stream == System.out || stream == System.err;
	}

}
