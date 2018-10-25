// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchMinimalStemFilterFactory.java

package org.apache.lucene.analysis.fr;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.fr:
//			FrenchMinimalStemFilter

public class FrenchMinimalStemFilterFactory extends TokenFilterFactory
{

	public FrenchMinimalStemFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new FrenchMinimalStemFilter(input);
	}
}
