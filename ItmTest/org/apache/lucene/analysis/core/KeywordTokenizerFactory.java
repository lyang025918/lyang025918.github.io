// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordTokenizerFactory.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.core:
//			KeywordTokenizer

public class KeywordTokenizerFactory extends TokenizerFactory
{

	public KeywordTokenizerFactory()
	{
	}

	public KeywordTokenizer create(Reader input)
	{
		return new KeywordTokenizer(input);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
