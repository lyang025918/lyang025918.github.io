// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekStemFilterFactory.java

package org.apache.lucene.analysis.el;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.el:
//			GreekStemFilter

public class GreekStemFilterFactory extends TokenFilterFactory
{

	public GreekStemFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new GreekStemFilter(input);
	}
}
