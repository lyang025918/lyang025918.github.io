// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultRepositorySelector.java

package org.apache.log4j.spi;


// Referenced classes of package org.apache.log4j.spi:
//			RepositorySelector, LoggerRepository

public class DefaultRepositorySelector
	implements RepositorySelector
{

	final LoggerRepository repository;

	public DefaultRepositorySelector(LoggerRepository repository)
	{
		this.repository = repository;
	}

	public LoggerRepository getLoggerRepository()
	{
		return repository;
	}
}
