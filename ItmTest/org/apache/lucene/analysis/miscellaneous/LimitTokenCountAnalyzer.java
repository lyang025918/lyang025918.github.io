// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LimitTokenCountAnalyzer.java

package org.apache.lucene.analysis.miscellaneous;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			LimitTokenCountFilter

public final class LimitTokenCountAnalyzer extends AnalyzerWrapper
{

	private final Analyzer delegate;
	private final int maxTokenCount;

	public LimitTokenCountAnalyzer(Analyzer delegate, int maxTokenCount)
	{
		this.delegate = delegate;
		this.maxTokenCount = maxTokenCount;
	}

	protected Analyzer getWrappedAnalyzer(String fieldName)
	{
		return delegate;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents wrapComponents(String fieldName, org.apache.lucene.analysis.Analyzer.TokenStreamComponents components)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(components.getTokenizer(), new LimitTokenCountFilter(components.getTokenStream(), maxTokenCount));
	}

	public String toString()
	{
		return (new StringBuilder()).append("LimitTokenCountAnalyzer(").append(delegate.toString()).append(", maxTokenCount=").append(maxTokenCount).append(")").toString();
	}
}
