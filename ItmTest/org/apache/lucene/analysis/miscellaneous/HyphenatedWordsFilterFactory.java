// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HyphenatedWordsFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			HyphenatedWordsFilter

public class HyphenatedWordsFilterFactory extends TokenFilterFactory
{

	public HyphenatedWordsFilterFactory()
	{
	}

	public HyphenatedWordsFilter create(TokenStream input)
	{
		return new HyphenatedWordsFilter(input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
