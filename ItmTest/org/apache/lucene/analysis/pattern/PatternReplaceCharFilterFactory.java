// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternReplaceCharFilterFactory.java

package org.apache.lucene.analysis.pattern;

import java.io.Reader;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.analysis.util.CharFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.pattern:
//			PatternReplaceCharFilter

public class PatternReplaceCharFilterFactory extends CharFilterFactory
{

	private Pattern p;
	private String replacement;
	private int maxBlockChars;
	private String blockDelimiters;

	public PatternReplaceCharFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		p = getPattern("pattern");
		replacement = (String)args.get("replacement");
		if (replacement == null)
			replacement = "";
		maxBlockChars = getInt("maxBlockChars", 10000);
		blockDelimiters = (String)args.get("blockDelimiters");
	}

	public CharFilter create(Reader input)
	{
		return new PatternReplaceCharFilter(p, replacement, maxBlockChars, blockDelimiters, input);
	}

	public volatile Reader create(Reader x0)
	{
		return create(x0);
	}
}
