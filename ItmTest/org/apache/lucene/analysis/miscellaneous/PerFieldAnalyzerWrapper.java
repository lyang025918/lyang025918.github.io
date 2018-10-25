// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PerFieldAnalyzerWrapper.java

package org.apache.lucene.analysis.miscellaneous;

import java.util.Collections;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;

public final class PerFieldAnalyzerWrapper extends AnalyzerWrapper
{

	private final Analyzer defaultAnalyzer;
	private final Map fieldAnalyzers;

	public PerFieldAnalyzerWrapper(Analyzer defaultAnalyzer)
	{
		this(defaultAnalyzer, null);
	}

	public PerFieldAnalyzerWrapper(Analyzer defaultAnalyzer, Map fieldAnalyzers)
	{
		this.defaultAnalyzer = defaultAnalyzer;
		this.fieldAnalyzers = fieldAnalyzers == null ? Collections.emptyMap() : fieldAnalyzers;
	}

	protected Analyzer getWrappedAnalyzer(String fieldName)
	{
		Analyzer analyzer = (Analyzer)fieldAnalyzers.get(fieldName);
		return analyzer == null ? defaultAnalyzer : analyzer;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents wrapComponents(String fieldName, org.apache.lucene.analysis.Analyzer.TokenStreamComponents components)
	{
		return components;
	}

	public String toString()
	{
		return (new StringBuilder()).append("PerFieldAnalyzerWrapper(").append(fieldAnalyzers).append(", default=").append(defaultAnalyzer).append(")").toString();
	}
}
