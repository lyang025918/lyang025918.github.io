// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KStemFilterFactory.java

package org.apache.lucene.analysis.en;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.en:
//			KStemFilter

public class KStemFilterFactory extends TokenFilterFactory
{

	public KStemFilterFactory()
	{
	}

	public TokenFilter create(TokenStream input)
	{
		return new KStemFilter(input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
