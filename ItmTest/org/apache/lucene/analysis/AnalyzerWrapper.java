// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalyzerWrapper.java

package org.apache.lucene.analysis;

import java.io.Reader;

// Referenced classes of package org.apache.lucene.analysis:
//			Analyzer

public abstract class AnalyzerWrapper extends Analyzer
{

	protected AnalyzerWrapper()
	{
		super(new Analyzer.PerFieldReuseStrategy());
	}

	protected abstract Analyzer getWrappedAnalyzer(String s);

	protected abstract Analyzer.TokenStreamComponents wrapComponents(String s, Analyzer.TokenStreamComponents tokenstreamcomponents);

	protected final Analyzer.TokenStreamComponents createComponents(String fieldName, Reader aReader)
	{
		return wrapComponents(fieldName, getWrappedAnalyzer(fieldName).createComponents(fieldName, aReader));
	}

	public final int getPositionIncrementGap(String fieldName)
	{
		return getWrappedAnalyzer(fieldName).getPositionIncrementGap(fieldName);
	}

	public final int getOffsetGap(String fieldName)
	{
		return getWrappedAnalyzer(fieldName).getOffsetGap(fieldName);
	}

	public final Reader initReader(String fieldName, Reader reader)
	{
		return getWrappedAnalyzer(fieldName).initReader(fieldName, reader);
	}
}
