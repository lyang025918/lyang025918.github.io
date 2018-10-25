// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKTokenizerFactory.java

package org.apache.lucene.analysis.cjk;

import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.cjk:
//			CJKTokenizer

/**
 * @deprecated Class CJKTokenizerFactory is deprecated
 */

public class CJKTokenizerFactory extends TokenizerFactory
{

	public CJKTokenizerFactory()
	{
	}

	public CJKTokenizer create(Reader in)
	{
		return new CJKTokenizer(in);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
