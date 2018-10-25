// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternReplaceFilterFactory.java

package org.apache.lucene.analysis.pattern;

import java.util.Map;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.pattern:
//			PatternReplaceFilter

public class PatternReplaceFilterFactory extends TokenFilterFactory
{

	Pattern p;
	String replacement;
	boolean all;

	public PatternReplaceFilterFactory()
	{
		all = true;
	}

	public void init(Map args)
	{
		super.init(args);
		p = getPattern("pattern");
		replacement = (String)args.get("replacement");
		String r = (String)args.get("replace");
		if (null != r)
			if (r.equals("all"))
				all = true;
			else
			if (r.equals("first"))
				all = false;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: 'replace' must be 'first' or 'all' in ").append(getClass().getName()).toString());
	}

	public PatternReplaceFilter create(TokenStream input)
	{
		return new PatternReplaceFilter(input, p, replacement, all);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
