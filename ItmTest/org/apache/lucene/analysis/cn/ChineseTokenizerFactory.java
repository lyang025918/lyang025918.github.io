// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChineseTokenizerFactory.java

package org.apache.lucene.analysis.cn;

import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.cn:
//			ChineseTokenizer

/**
 * @deprecated Class ChineseTokenizerFactory is deprecated
 */

public class ChineseTokenizerFactory extends TokenizerFactory
{

	public ChineseTokenizerFactory()
	{
	}

	public ChineseTokenizer create(Reader in)
	{
		return new ChineseTokenizer(in);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
