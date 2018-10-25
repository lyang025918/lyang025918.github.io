// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InfoStream.java

package org.apache.lucene.util;

import java.io.Closeable;

public abstract class InfoStream
	implements Closeable
{
	private static final class NoOutput extends InfoStream
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/util/InfoStream.desiredAssertionStatus();

		public void message(String component, String message)
		{
			if (!$assertionsDisabled)
				throw new AssertionError("message() should not be called when isEnabled returns false");
			else
				return;
		}

		public boolean isEnabled(String component)
		{
			return false;
		}

		public void close()
		{
		}


		private NoOutput()
		{
		}

	}


	public static final InfoStream NO_OUTPUT;
	private static InfoStream defaultInfoStream;

	public InfoStream()
	{
	}

	public abstract void message(String s, String s1);

	public abstract boolean isEnabled(String s);

	public static synchronized InfoStream getDefault()
	{
		return defaultInfoStream;
	}

	public static synchronized void setDefault(InfoStream infoStream)
	{
		if (infoStream == null)
		{
			throw new IllegalArgumentException("Cannot set InfoStream default implementation to null. To disable logging use InfoStream.NO_OUTPUT");
		} else
		{
			defaultInfoStream = infoStream;
			return;
		}
	}

	static 
	{
		NO_OUTPUT = new NoOutput();
		defaultInfoStream = NO_OUTPUT;
	}
}
