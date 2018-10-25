// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ThaiWordFilterFactory.java

package org.apache.lucene.analysis.th;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.th:
//			ThaiWordFilter

public class ThaiWordFilterFactory extends TokenFilterFactory
{

	public ThaiWordFilterFactory()
	{
	}

	public ThaiWordFilter create(TokenStream input)
	{
		assureMatchVersion();
		return new ThaiWordFilter(luceneMatchVersion, input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
