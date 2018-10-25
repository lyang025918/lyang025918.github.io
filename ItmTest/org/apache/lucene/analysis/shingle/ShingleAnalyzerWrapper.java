// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShingleAnalyzerWrapper.java

package org.apache.lucene.analysis.shingle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.shingle:
//			ShingleFilter

public final class ShingleAnalyzerWrapper extends AnalyzerWrapper
{

	private final Analyzer defaultAnalyzer;
	private final int maxShingleSize;
	private final int minShingleSize;
	private final String tokenSeparator;
	private final boolean outputUnigrams;
	private final boolean outputUnigramsIfNoShingles;

	public ShingleAnalyzerWrapper(Analyzer defaultAnalyzer)
	{
		this(defaultAnalyzer, 2);
	}

	public ShingleAnalyzerWrapper(Analyzer defaultAnalyzer, int maxShingleSize)
	{
		this(defaultAnalyzer, 2, maxShingleSize);
	}

	public ShingleAnalyzerWrapper(Analyzer defaultAnalyzer, int minShingleSize, int maxShingleSize)
	{
		this(defaultAnalyzer, minShingleSize, maxShingleSize, " ", true, false);
	}

	public ShingleAnalyzerWrapper(Analyzer defaultAnalyzer, int minShingleSize, int maxShingleSize, String tokenSeparator, boolean outputUnigrams, boolean outputUnigramsIfNoShingles)
	{
		this.defaultAnalyzer = defaultAnalyzer;
		if (maxShingleSize < 2)
			throw new IllegalArgumentException("Max shingle size must be >= 2");
		this.maxShingleSize = maxShingleSize;
		if (minShingleSize < 2)
			throw new IllegalArgumentException("Min shingle size must be >= 2");
		if (minShingleSize > maxShingleSize)
		{
			throw new IllegalArgumentException("Min shingle size must be <= max shingle size");
		} else
		{
			this.minShingleSize = minShingleSize;
			this.tokenSeparator = tokenSeparator != null ? tokenSeparator : "";
			this.outputUnigrams = outputUnigrams;
			this.outputUnigramsIfNoShingles = outputUnigramsIfNoShingles;
			return;
		}
	}

	public ShingleAnalyzerWrapper(Version matchVersion)
	{
		this(matchVersion, 2, 2);
	}

	public ShingleAnalyzerWrapper(Version matchVersion, int minShingleSize, int maxShingleSize)
	{
		this(((Analyzer) (new StandardAnalyzer(matchVersion))), minShingleSize, maxShingleSize);
	}

	public int getMaxShingleSize()
	{
		return maxShingleSize;
	}

	public int getMinShingleSize()
	{
		return minShingleSize;
	}

	public String getTokenSeparator()
	{
		return tokenSeparator;
	}

	public boolean isOutputUnigrams()
	{
		return outputUnigrams;
	}

	public boolean isOutputUnigramsIfNoShingles()
	{
		return outputUnigramsIfNoShingles;
	}

	protected Analyzer getWrappedAnalyzer(String fieldName)
	{
		return defaultAnalyzer;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents wrapComponents(String fieldName, org.apache.lucene.analysis.Analyzer.TokenStreamComponents components)
	{
		ShingleFilter filter = new ShingleFilter(components.getTokenStream(), minShingleSize, maxShingleSize);
		filter.setMinShingleSize(minShingleSize);
		filter.setMaxShingleSize(maxShingleSize);
		filter.setTokenSeparator(tokenSeparator);
		filter.setOutputUnigrams(outputUnigrams);
		filter.setOutputUnigramsIfNoShingles(outputUnigramsIfNoShingles);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(components.getTokenizer(), filter);
	}
}
