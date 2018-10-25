// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LimitTokenCountFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			LimitTokenCountFilter

public class LimitTokenCountFilterFactory extends TokenFilterFactory
{

	int maxTokenCount;

	public LimitTokenCountFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		String maxTokenCountArg = (String)args.get("maxTokenCount");
		if (maxTokenCountArg == null)
		{
			throw new IllegalArgumentException("maxTokenCount is mandatory.");
		} else
		{
			maxTokenCount = Integer.parseInt((String)args.get(maxTokenCountArg));
			return;
		}
	}

	public TokenStream create(TokenStream input)
	{
		return new LimitTokenCountFilter(input, maxTokenCount);
	}
}
