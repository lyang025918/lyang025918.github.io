// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReverseStringFilterFactory.java

package org.apache.lucene.analysis.reverse;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.reverse:
//			ReverseStringFilter

public class ReverseStringFilterFactory extends TokenFilterFactory
{

	public ReverseStringFilterFactory()
	{
	}

	public ReverseStringFilter create(TokenStream in)
	{
		assureMatchVersion();
		return new ReverseStringFilter(luceneMatchVersion, in);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
