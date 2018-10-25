// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchLightStemFilterFactory.java

package org.apache.lucene.analysis.fr;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.fr:
//			FrenchLightStemFilter

public class FrenchLightStemFilterFactory extends TokenFilterFactory
{

	public FrenchLightStemFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new FrenchLightStemFilter(input);
	}
}
