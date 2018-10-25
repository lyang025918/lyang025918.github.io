// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicStemFilterFactory.java

package org.apache.lucene.analysis.ar;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.ar:
//			ArabicStemFilter

public class ArabicStemFilterFactory extends TokenFilterFactory
{

	public ArabicStemFilterFactory()
	{
	}

	public ArabicStemFilter create(TokenStream input)
	{
		return new ArabicStemFilter(input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
