// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanStemFilterFactory.java

package org.apache.lucene.analysis.de;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.de:
//			GermanStemFilter

public class GermanStemFilterFactory extends TokenFilterFactory
{

	public GermanStemFilterFactory()
	{
	}

	public GermanStemFilter create(TokenStream in)
	{
		return new GermanStemFilter(in);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
