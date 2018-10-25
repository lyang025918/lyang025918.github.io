// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MessageImpl.java

package org.apache.lucene.queryparser.flexible.messages;

import java.util.Locale;

// Referenced classes of package org.apache.lucene.queryparser.flexible.messages:
//			Message, NLS

public class MessageImpl
	implements Message
{

	private String key;
	private Object arguments[];

	public MessageImpl(String key)
	{
		arguments = new Object[0];
		this.key = key;
	}

	public transient MessageImpl(String key, Object args[])
	{
		this(key);
		arguments = args;
	}

	public Object[] getArguments()
	{
		return arguments;
	}

	public String getKey()
	{
		return key;
	}

	public String getLocalizedMessage()
	{
		return getLocalizedMessage(Locale.getDefault());
	}

	public String getLocalizedMessage(Locale locale)
	{
		return NLS.getLocalizedMessage(getKey(), locale, getArguments());
	}

	public String toString()
	{
		Object args[] = getArguments();
		StringBuilder sb = new StringBuilder(getKey());
		if (args != null)
		{
			for (int i = 0; i < args.length; i++)
				sb.append(i != 0 ? ", " : " ").append(args[i]);

		}
		return sb.toString();
	}
}
