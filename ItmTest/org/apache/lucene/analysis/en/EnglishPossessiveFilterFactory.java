// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnglishPossessiveFilterFactory.java

package org.apache.lucene.analysis.en;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.en:
//			EnglishPossessiveFilter

public class EnglishPossessiveFilterFactory extends TokenFilterFactory
{

	public EnglishPossessiveFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public TokenStream create(TokenStream input)
	{
		return new EnglishPossessiveFilter(luceneMatchVersion, input);
	}
}
