// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UAX29URLEmailTokenizerFactory.java

package org.apache.lucene.analysis.standard;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			UAX29URLEmailTokenizer

public class UAX29URLEmailTokenizerFactory extends TokenizerFactory
{

	private int maxTokenLength;

	public UAX29URLEmailTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		maxTokenLength = getInt("maxTokenLength", 255);
	}

	public UAX29URLEmailTokenizer create(Reader input)
	{
		UAX29URLEmailTokenizer tokenizer = new UAX29URLEmailTokenizer(luceneMatchVersion, input);
		tokenizer.setMaxTokenLength(maxTokenLength);
		return tokenizer;
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
