// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WikipediaTokenizerFactory.java

package org.apache.lucene.analysis.wikipedia;

import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.wikipedia:
//			WikipediaTokenizer

public class WikipediaTokenizerFactory extends TokenizerFactory
{

	public WikipediaTokenizerFactory()
	{
	}

	public Tokenizer create(Reader input)
	{
		return new WikipediaTokenizer(input);
	}
}
