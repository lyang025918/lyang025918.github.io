// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordAnalyzer.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;

// Referenced classes of package org.apache.lucene.analysis.core:
//			KeywordTokenizer

public final class KeywordAnalyzer extends Analyzer
{

	public KeywordAnalyzer()
	{
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new KeywordTokenizer(reader));
	}
}
