// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WhitespaceTokenizerFactory.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.core:
//			WhitespaceTokenizer

public class WhitespaceTokenizerFactory extends TokenizerFactory
{

	public WhitespaceTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public WhitespaceTokenizer create(Reader input)
	{
		return new WhitespaceTokenizer(luceneMatchVersion, input);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
