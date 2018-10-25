// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QuietWriter.java

package org.apache.log4j.helpers;

import java.io.FilterWriter;
import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

public class QuietWriter extends FilterWriter
{

	protected ErrorHandler errorHandler;

	public QuietWriter(Writer writer, ErrorHandler errorHandler)
	{
		super(writer);
		setErrorHandler(errorHandler);
	}

	public void write(String string)
	{
		if (string != null)
			try
			{
				out.write(string);
			}
			catch (Exception e)
			{
				errorHandler.error("Failed to write [" + string + "].", e, 1);
			}
	}

	public void flush()
	{
		try
		{
			out.flush();
		}
		catch (Exception e)
		{
			errorHandler.error("Failed to flush writer,", e, 2);
		}
	}

	public void setErrorHandler(ErrorHandler eh)
	{
		if (eh == null)
		{
			throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
		} else
		{
			errorHandler = eh;
			return;
		}
	}
}
