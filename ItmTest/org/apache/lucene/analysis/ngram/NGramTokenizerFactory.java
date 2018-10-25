// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NGramTokenizerFactory.java

package org.apache.lucene.analysis.ngram;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.ngram:
//			NGramTokenizer

public class NGramTokenizerFactory extends TokenizerFactory
{

	private int maxGramSize;
	private int minGramSize;

	public NGramTokenizerFactory()
	{
		maxGramSize = 0;
		minGramSize = 0;
	}

	public void init(Map args)
	{
		super.init(args);
		String maxArg = (String)args.get("maxGramSize");
		maxGramSize = maxArg == null ? 2 : Integer.parseInt(maxArg);
		String minArg = (String)args.get("minGramSize");
		minGramSize = minArg == null ? 1 : Integer.parseInt(minArg);
	}

	public NGramTokenizer create(Reader input)
	{
		return new NGramTokenizer(input, minGramSize, maxGramSize);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
