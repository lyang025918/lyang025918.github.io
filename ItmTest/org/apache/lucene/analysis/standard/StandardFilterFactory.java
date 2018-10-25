// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardFilterFactory.java

package org.apache.lucene.analysis.standard;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			StandardFilter

public class StandardFilterFactory extends TokenFilterFactory
{

	public StandardFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public StandardFilter create(TokenStream input)
	{
		return new StandardFilter(luceneMatchVersion, input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
