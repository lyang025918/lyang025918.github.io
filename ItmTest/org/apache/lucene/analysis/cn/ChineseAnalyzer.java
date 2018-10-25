// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChineseAnalyzer.java

package org.apache.lucene.analysis.cn;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;

// Referenced classes of package org.apache.lucene.analysis.cn:
//			ChineseTokenizer, ChineseFilter

/**
 * @deprecated Class ChineseAnalyzer is deprecated
 */

public final class ChineseAnalyzer extends Analyzer
{

	public ChineseAnalyzer()
	{
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		org.apache.lucene.analysis.Tokenizer source = new ChineseTokenizer(reader);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(source, new ChineseFilter(source));
	}
}
