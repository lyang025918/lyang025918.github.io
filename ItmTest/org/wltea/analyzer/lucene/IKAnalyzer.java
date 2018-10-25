// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IKAnalyzer.java

package org.wltea.analyzer.lucene;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;

// Referenced classes of package org.wltea.analyzer.lucene:
//			IKTokenizer

public final class IKAnalyzer extends Analyzer
{

	private boolean useSmart;

	public boolean useSmart()
	{
		return useSmart;
	}

	public void setUseSmart(boolean useSmart)
	{
		this.useSmart = useSmart;
	}

	public IKAnalyzer()
	{
		this(false);
	}

	public IKAnalyzer(boolean useSmart)
	{
		this.useSmart = useSmart;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader in)
	{
		org.apache.lucene.analysis.Tokenizer _IKTokenizer = new IKTokenizer(in, useSmart());
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(_IKTokenizer);
	}
}
