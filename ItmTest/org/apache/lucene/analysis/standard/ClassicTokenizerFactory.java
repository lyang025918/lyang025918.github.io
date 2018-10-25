// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassicTokenizerFactory.java

package org.apache.lucene.analysis.standard;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			ClassicTokenizer

public class ClassicTokenizerFactory extends TokenizerFactory
{

	private int maxTokenLength;

	public ClassicTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		maxTokenLength = getInt("maxTokenLength", 255);
	}

	public Tokenizer create(Reader input)
	{
		ClassicTokenizer tokenizer = new ClassicTokenizer(luceneMatchVersion, input);
		tokenizer.setMaxTokenLength(maxTokenLength);
		return tokenizer;
	}
}
