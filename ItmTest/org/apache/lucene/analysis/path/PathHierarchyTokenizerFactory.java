// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PathHierarchyTokenizerFactory.java

package org.apache.lucene.analysis.path;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.path:
//			ReversePathHierarchyTokenizer, PathHierarchyTokenizer

public class PathHierarchyTokenizerFactory extends TokenizerFactory
{

	private char delimiter;
	private char replacement;
	private boolean reverse;
	private int skip;

	public PathHierarchyTokenizerFactory()
	{
		reverse = false;
		skip = 0;
	}

	public void init(Map args)
	{
		super.init(args);
		String v = (String)args.get("delimiter");
		if (v != null)
		{
			if (v.length() != 1)
				throw new IllegalArgumentException((new StringBuilder()).append("delimiter should be a char. \"").append(v).append("\" is invalid").toString());
			delimiter = v.charAt(0);
		} else
		{
			delimiter = '/';
		}
		v = (String)args.get("replace");
		if (v != null)
		{
			if (v.length() != 1)
				throw new IllegalArgumentException((new StringBuilder()).append("replace should be a char. \"").append(v).append("\" is invalid").toString());
			replacement = v.charAt(0);
		} else
		{
			replacement = delimiter;
		}
		v = (String)args.get("reverse");
		if (v != null)
			reverse = "true".equals(v);
		v = (String)args.get("skip");
		if (v != null)
			skip = Integer.parseInt(v);
	}

	public Tokenizer create(Reader input)
	{
		if (reverse)
			return new ReversePathHierarchyTokenizer(input, delimiter, replacement, skip);
		else
			return new PathHierarchyTokenizer(input, delimiter, replacement, skip);
	}
}
