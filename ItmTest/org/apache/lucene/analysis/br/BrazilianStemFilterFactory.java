// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BrazilianStemFilterFactory.java

package org.apache.lucene.analysis.br;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.br:
//			BrazilianStemFilter

public class BrazilianStemFilterFactory extends TokenFilterFactory
{

	public BrazilianStemFilterFactory()
	{
	}

	public BrazilianStemFilter create(TokenStream in)
	{
		return new BrazilianStemFilter(in);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
