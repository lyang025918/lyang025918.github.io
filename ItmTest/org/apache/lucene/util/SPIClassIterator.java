// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SPIClassIterator.java

package org.apache.lucene.util;

import java.io.*;
import java.net.URL;
import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			IOUtils

public final class SPIClassIterator
	implements Iterator
{

	private static final String META_INF_SERVICES = "META-INF/services/";
	private final Class clazz;
	private final ClassLoader loader;
	private final Enumeration profilesEnum;
	private Iterator linesIterator;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/SPIClassIterator.desiredAssertionStatus();

	public static SPIClassIterator get(Class clazz)
	{
		return new SPIClassIterator(clazz, Thread.currentThread().getContextClassLoader());
	}

	public static SPIClassIterator get(Class clazz, ClassLoader loader)
	{
		return new SPIClassIterator(clazz, loader);
	}

	private SPIClassIterator(Class clazz, ClassLoader loader)
	{
		if (loader == null)
			throw new IllegalArgumentException("You must provide a ClassLoader.");
		this.clazz = clazz;
		this.loader = loader;
		try
		{
			profilesEnum = loader.getResources((new StringBuilder()).append("META-INF/services/").append(clazz.getName()).toString());
		}
		catch (IOException ioe)
		{
			throw new ServiceConfigurationError((new StringBuilder()).append("Error loading SPI profiles for type ").append(clazz.getName()).append(" from classpath").toString(), ioe);
		}
		linesIterator = Collections.emptySet().iterator();
	}

	private boolean loadNextProfile()
	{
		ArrayList lines = null;
_L2:
		URL url;
		if (!profilesEnum.hasMoreElements())
			break MISSING_BLOCK_LABEL_242;
		if (lines != null)
			lines.clear();
		else
			lines = new ArrayList();
		url = (URL)profilesEnum.nextElement();
		InputStream in;
		IOException priorE;
		in = url.openStream();
		priorE = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, IOUtils.CHARSET_UTF_8));
		do
		{
			String line;
			if ((line = reader.readLine()) == null)
				break;
			int pos = line.indexOf('#');
			if (pos >= 0)
				line = line.substring(0, pos);
			line = line.trim();
			if (line.length() > 0)
				lines.add(line);
		} while (true);
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			in
		});
		continue; /* Loop/switch isn't completed */
		IOException ioe;
		ioe;
		priorE = ioe;
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			in
		});
		continue; /* Loop/switch isn't completed */
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(priorE, new Closeable[] {
			in
		});
		throw exception;
		IOException ioe;
		ioe;
		throw new ServiceConfigurationError((new StringBuilder()).append("Error loading SPI class list from URL: ").append(url).toString(), ioe);
		if (lines.isEmpty()) goto _L2; else goto _L1
_L1:
		linesIterator = lines.iterator();
		return true;
		return false;
	}

	public boolean hasNext()
	{
		return linesIterator.hasNext() || loadNextProfile();
	}

	public Class next()
	{
		String c;
		if (!hasNext())
			throw new NoSuchElementException();
		if (!$assertionsDisabled && !linesIterator.hasNext())
			throw new AssertionError();
		c = (String)linesIterator.next();
		return Class.forName(c, false, loader).asSubclass(clazz);
		ClassNotFoundException cnfe;
		cnfe;
		throw new ServiceConfigurationError(String.format(Locale.ROOT, "A SPI class of type %s with classname %s does not exist, please fix the file '%s%1$s' in your classpath.", new Object[] {
			clazz.getName(), c, "META-INF/services/"
		}));
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	public volatile Object next()
	{
		return next();
	}

}
