// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EdgeNGramFilterFactory.java

package org.apache.lucene.analysis.ngram;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.ngram:
//			EdgeNGramTokenFilter

public class EdgeNGramFilterFactory extends TokenFilterFactory
{

	private int maxGramSize;
	private int minGramSize;
	private String side;

	public EdgeNGramFilterFactory()
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
			side = EdgeNGramTokenFilter.Side.FRONT.getLabel();
	}

	public EdgeNGramTokenFilter create(TokenStream input)
	{
		return new EdgeNGramTokenFilter(input, side, minGramSize, maxGramSize);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
