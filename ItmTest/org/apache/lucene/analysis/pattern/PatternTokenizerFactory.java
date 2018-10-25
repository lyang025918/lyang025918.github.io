// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternTokenizerFactory.java

package org.apache.lucene.analysis.pattern;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.pattern:
//			PatternTokenizer

public class PatternTokenizerFactory extends TokenizerFactory
{

	public static final String PATTERN = "pattern";
	public static final String GROUP = "group";
	protected Pattern pattern;
	protected int group;

	public PatternTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		pattern = getPattern("pattern");
		group = -1;
		String g = (String)args.get("group");
		if (g != null)
			group = Integer.parseInt(g);
	}

	public Tokenizer create(Reader in)
	{
		return new PatternTokenizer(in, pattern, group);
		IOException ex;
		ex;
		throw new RuntimeException("IOException thrown creating PatternTokenizer instance", ex);
	}
}
