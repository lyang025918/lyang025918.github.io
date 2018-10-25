// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ResourceUtils.java

package org.apache.log4j.lf5.util;

import java.io.InputStream;
import java.net.URL;

// Referenced classes of package org.apache.log4j.lf5.util:
//			Resource

public class ResourceUtils
{

	public ResourceUtils()
	{
	}

	public static InputStream getResourceAsStream(Object object, Resource resource)
	{
		ClassLoader loader = object.getClass().getClassLoader();
		InputStream in = null;
		if (loader != null)
			in = loader.getResourceAsStream(resource.getName());
		else
			in = ClassLoader.getSystemResourceAsStream(resource.getName());
		return in;
	}

	public static URL getResourceAsURL(Object object, Resource resource)
	{
		ClassLoader loader = object.getClass().getClassLoader();
		URL url = null;
		if (loader != null)
			url = loader.getResource(resource.getName());
		else
			url = ClassLoader.getSystemResource(resource.getName());
		return url;
	}
}
