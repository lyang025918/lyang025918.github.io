// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExtendableQueryParser.java

package org.apache.lucene.queryparser.ext;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.queryparser.ext:
//			ExtensionQuery, Extensions, ParserExtension

public class ExtendableQueryParser extends QueryParser
{

	private final String defaultField;
	private final Extensions extensions;
	private static final Extensions DEFAULT_EXTENSION = new Extensions();

	public ExtendableQueryParser(Version matchVersion, String f, Analyzer a)
	{
		this(matchVersion, f, a, DEFAULT_EXTENSION);
	}

	public ExtendableQueryParser(Version matchVersion, String f, Analyzer a, Extensions ext)
	{
		super(matchVersion, f, a);
		defaultField = f;
		extensions = ext;
	}

	public char getExtensionFieldDelimiter()
	{
		return extensions.getExtensionFieldDelimiter();
	}

	protected Query getFieldQuery(String field, String queryText, boolean quoted)
		throws ParseException
	{
		Extensions.Pair splitExtensionField = extensions.splitExtensionField(defaultField, field);
		ParserExtension extension = extensions.getExtension((String)splitExtensionField.cud);
		if (extension != null)
			return extension.parse(new ExtensionQuery(this, (String)splitExtensionField.cur, queryText));
		else
			return super.getFieldQuery(field, queryText, quoted);
	}

}
