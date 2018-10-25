// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleAnalyzer.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.core:
//			LowerCaseTokenizer

public final class SimpleAnalyzer extends Analyzer
{

	private final Version matchVersion;

	public SimpleAnalyzer(Version matchVersion)
	{
		this.matchVersion = matchVersion;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new LowerCaseTokenizer(matchVersion, reader));
	}
}
