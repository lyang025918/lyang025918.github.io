// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Log4jEntityResolver.java

package org.apache.log4j.xml;

import java.io.ByteArrayInputStream;
import org.apache.log4j.helpers.LogLog;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class Log4jEntityResolver
	implements EntityResolver
{

	private static final String PUBLIC_ID = "-//APACHE//DTD LOG4J 1.2//EN";

	public Log4jEntityResolver()
	{
	}

	public InputSource resolveEntity(String publicId, String systemId)
	{
		if (systemId.endsWith("log4j.dtd") || "-//APACHE//DTD LOG4J 1.2//EN".equals(publicId))
		{
			Class clazz = getClass();
			java.io.InputStream in = clazz.getResourceAsStream("/org/apache/log4j/xml/log4j.dtd");
			if (in == null)
			{
				LogLog.warn("Could not find [log4j.dtd] using [" + clazz.getClassLoader() + "] class loader, parsed without DTD.");
				in = new ByteArrayInputStream(new byte[0]);
			}
			return new InputSource(in);
		} else
		{
			return null;
		}
	}
}
