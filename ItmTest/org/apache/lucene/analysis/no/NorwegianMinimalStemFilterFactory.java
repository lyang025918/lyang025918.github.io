// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NorwegianMinimalStemFilterFactory.java

package org.apache.lucene.analysis.no;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.no:
//			NorwegianMinimalStemFilter

public class NorwegianMinimalStemFilterFactory extends TokenFilterFactory
{

	public NorwegianMinimalStemFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new NorwegianMinimalStemFilter(input);
	}
}
