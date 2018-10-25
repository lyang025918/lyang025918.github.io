// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Extensions.java

package org.apache.lucene.queryparser.ext;

import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.queryparser.classic.QueryParserBase;

// Referenced classes of package org.apache.lucene.queryparser.ext:
//			ParserExtension

public class Extensions
{
	public static class Pair
	{

		public final Object cur;
		public final Object cud;

		public Pair(Object cur, Object cud)
		{
			this.cur = cur;
			this.cud = cud;
		}
	}


	private final Map extensions;
	private final char extensionFieldDelimiter;
	public static final char DEFAULT_EXTENSION_FIELD_DELIMITER = 58;

	public Extensions()
	{
		this(':');
	}

	public Extensions(char extensionFieldDelimiter)
	{
		extensions = new HashMap();
		this.extensionFieldDelimiter = extensionFieldDelimiter;
	}

	public void add(String key, ParserExtension extension)
	{
		extensions.put(key, extension);
	}

	public final ParserExtension getExtension(String key)
	{
		return (ParserExtension)extensions.get(key);
	}

	public char getExtensionFieldDelimiter()
	{
		return extensionFieldDelimiter;
	}

	public Pair splitExtensionField(String defaultField, String field)
	{
		int indexOf = field.indexOf(extensionFieldDelimiter);
		if (indexOf < 0)
		{
			return new Pair(field, null);
		} else
		{
			String indexField = indexOf != 0 ? field.substring(0, indexOf) : defaultField;
			String extensionKey = field.substring(indexOf + 1);
			return new Pair(indexField, extensionKey);
		}
	}

	public String escapeExtensionField(String extfield)
	{
		return QueryParserBase.escape(extfield);
	}

	public String buildExtensionField(String extensionKey)
	{
		return buildExtensionField(extensionKey, "");
	}

	public String buildExtensionField(String extensionKey, String field)
	{
		StringBuilder builder = new StringBuilder(field);
		builder.append(extensionFieldDelimiter);
		builder.append(extensionKey);
		return escapeExtensionField(builder.toString());
	}
}
