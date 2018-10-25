// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LengthFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			LengthFilter

public class LengthFilterFactory extends TokenFilterFactory
{

	int min;
	int max;
	boolean enablePositionIncrements;
	public static final String MIN_KEY = "min";
	public static final String MAX_KEY = "max";

	public LengthFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		String minKey = (String)args.get("min");
		String maxKey = (String)args.get("max");
		if (minKey == null || maxKey == null)
		{
			throw new IllegalArgumentException("Both min and max are mandatory");
		} else
		{
			min = Integer.parseInt(minKey);
			max = Integer.parseInt(maxKey);
			enablePositionIncrements = getBoolean("enablePositionIncrements", false);
			return;
		}
	}

	public LengthFilter create(TokenStream input)
	{
		return new LengthFilter(enablePositionIncrements, input, min, max);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
