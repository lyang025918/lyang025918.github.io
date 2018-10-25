// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShingleFilterFactory.java

package org.apache.lucene.analysis.shingle;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.shingle:
//			ShingleFilter

public class ShingleFilterFactory extends TokenFilterFactory
{

	private int minShingleSize;
	private int maxShingleSize;
	private boolean outputUnigrams;
	private boolean outputUnigramsIfNoShingles;
	private String tokenSeparator;

	public ShingleFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		maxShingleSize = getInt("maxShingleSize", 2);
		if (maxShingleSize < 2)
			throw new IllegalArgumentException((new StringBuilder()).append("Invalid maxShingleSize (").append(maxShingleSize).append(") - must be at least 2").toString());
		minShingleSize = getInt("minShingleSize", 2);
		if (minShingleSize < 2)
			throw new IllegalArgumentException((new StringBuilder()).append("Invalid minShingleSize (").append(minShingleSize).append(") - must be at least 2").toString());
		if (minShingleSize > maxShingleSize)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("Invalid minShingleSize (").append(minShingleSize).append(") - must be no greater than maxShingleSize (").append(maxShingleSize).append(")").toString());
		} else
		{
			outputUnigrams = getBoolean("outputUnigrams", true);
			outputUnigramsIfNoShingles = getBoolean("outputUnigramsIfNoShingles", false);
			tokenSeparator = args.containsKey("tokenSeparator") ? (String)args.get("tokenSeparator") : " ";
			return;
		}
	}

	public ShingleFilter create(TokenStream input)
	{
		ShingleFilter r = new ShingleFilter(input, minShingleSize, maxShingleSize);
		r.setOutputUnigrams(outputUnigrams);
		r.setOutputUnigramsIfNoShingles(outputUnigramsIfNoShingles);
		r.setTokenSeparator(tokenSeparator);
		return r;
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
