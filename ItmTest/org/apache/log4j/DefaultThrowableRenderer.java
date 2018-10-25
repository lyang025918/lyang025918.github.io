// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultThrowableRenderer.java

package org.apache.log4j;

import java.io.*;
import java.util.ArrayList;
import org.apache.log4j.spi.ThrowableRenderer;

public final class DefaultThrowableRenderer
	implements ThrowableRenderer
{

	public DefaultThrowableRenderer()
	{
	}

	public String[] doRender(Throwable throwable)
	{
		return render(throwable);
	}

	public static String[] render(Throwable throwable)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try
		{
			throwable.printStackTrace(pw);
		}
		catch (RuntimeException ex) { }
		pw.flush();
		LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
		ArrayList lines = new ArrayList();
		try
		{
			for (String line = reader.readLine(); line != null; line = reader.readLine())
				lines.add(line);

		}
		catch (IOException ex)
		{
			if (ex instanceof InterruptedIOException)
				Thread.currentThread().interrupt();
			lines.add(ex.toString());
		}
		String tempRep[] = new String[lines.size()];
		lines.toArray(tempRep);
		return tempRep;
	}
}
