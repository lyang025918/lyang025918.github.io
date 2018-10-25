// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassicFilterFactory.java

package org.apache.lucene.analysis.standard;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			ClassicFilter

public class ClassicFilterFactory extends TokenFilterFactory
{

	public ClassicFilterFactory()
	{
	}

	public TokenFilter create(TokenStream input)
	{
		return new ClassicFilter(input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
