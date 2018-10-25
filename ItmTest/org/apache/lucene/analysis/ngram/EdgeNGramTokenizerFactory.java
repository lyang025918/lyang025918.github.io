// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EdgeNGramTokenizerFactory.java

package org.apache.lucene.analysis.ngram;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.ngram:
//			EdgeNGramTokenizer

public class EdgeNGramTokenizerFactory extends TokenizerFactory
{

	private int maxGramSize;
	private int minGramSize;
	private String side;

	public EdgeNGramTokenizerFactory()
	{
		maxGramSize = 0;
		minGramSize = 0;
	}

	public void init(Map args)
	{
		super.init(args);
		String maxArg = (String)args.get("maxGramSize");
		maxGramSize = maxArg == null ? 1 : Integer.parseInt(maxArg);
		String minArg = (String)args.get("minGramSize");
		minGramSize = minArg == null ? 1 : Integer.parseInt(minArg);
		side = (String)args.get("side");
		if (side == null)
			side = EdgeNGramTokenizer.Side.FRONT.getLabel();
	}

	public EdgeNGramTokenizer create(Reader input)
	{
		return new EdgeNGramTokenizer(input, side, minGramSize, maxGramSize);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
