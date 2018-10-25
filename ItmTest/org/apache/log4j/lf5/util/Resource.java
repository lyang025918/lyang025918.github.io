// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Resource.java

package org.apache.log4j.lf5.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

// Referenced classes of package org.apache.log4j.lf5.util:
//			ResourceUtils

public class Resource
{

	protected String _name;

	public Resource()
	{
	}

	public Resource(String name)
	{
		_name = name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public String getName()
	{
		return _name;
	}

	public InputStream getInputStream()
	{
		InputStream in = ResourceUtils.getResourceAsStream(this, this);
		return in;
	}

	public InputStreamReader getInputStreamReader()
	{
		InputStream in = ResourceUtils.getResourceAsStream(this, this);
		if (in == null)
		{
			return null;
		} else
		{
			InputStreamReader reader = new InputStreamReader(in);
			return reader;
		}
	}

	public URL getURL()
	{
		return ResourceUtils.getResourceAsURL(this, this);
	}
}
