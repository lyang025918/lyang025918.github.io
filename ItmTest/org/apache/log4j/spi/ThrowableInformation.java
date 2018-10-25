// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThrowableInformation.java

package org.apache.log4j.spi;

import java.io.Serializable;
import org.apache.log4j.Category;
import org.apache.log4j.DefaultThrowableRenderer;

// Referenced classes of package org.apache.log4j.spi:
//			ThrowableRendererSupport, ThrowableRenderer

public class ThrowableInformation
	implements Serializable
{

	static final long serialVersionUID = 0xbe18fee081720f51L;
	private transient Throwable throwable;
	private transient Category category;
	private String rep[];

	public ThrowableInformation(Throwable throwable)
	{
		this.throwable = throwable;
	}

	public ThrowableInformation(Throwable throwable, Category category)
	{
		this.throwable = throwable;
		this.category = category;
	}

	public ThrowableInformation(String r[])
	{
		if (r != null)
			rep = (String[])(String[])r.clone();
	}

	public Throwable getThrowable()
	{
		return throwable;
	}

	public synchronized String[] getThrowableStrRep()
	{
		if (rep == null)
		{
			ThrowableRenderer renderer = null;
			if (category != null)
			{
				LoggerRepository repo = category.getLoggerRepository();
				if (repo instanceof ThrowableRendererSupport)
					renderer = ((ThrowableRendererSupport)repo).getThrowableRenderer();
			}
			if (renderer == null)
				rep = DefaultThrowableRenderer.render(throwable);
			else
				rep = renderer.doRender(throwable);
		}
		return (String[])(String[])rep.clone();
	}
}
