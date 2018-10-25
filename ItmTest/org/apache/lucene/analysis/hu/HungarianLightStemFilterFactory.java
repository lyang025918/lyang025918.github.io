// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HungarianLightStemFilterFactory.java

package org.apache.lucene.analysis.hu;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.hu:
//			HungarianLightStemFilter

public class HungarianLightStemFilterFactory extends TokenFilterFactory
{

	public HungarianLightStemFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new HungarianLightStemFilter(input);
	}
}
