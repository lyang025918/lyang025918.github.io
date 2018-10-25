// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultCategoryFactory.java

package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;

// Referenced classes of package org.apache.log4j:
//			Logger

class DefaultCategoryFactory
	implements LoggerFactory
{

	DefaultCategoryFactory()
	{
	}

	public Logger makeNewLoggerInstance(String name)
	{
		return new Logger(name);
	}
}
