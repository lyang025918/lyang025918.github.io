// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RussianLetterTokenizerFactory.java

package org.apache.lucene.analysis.ru;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.ru:
//			RussianLetterTokenizer

/**
 * @deprecated Class RussianLetterTokenizerFactory is deprecated
 */

public class RussianLetterTokenizerFactory extends TokenizerFactory
{

	public RussianLetterTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		if (args.containsKey("charset"))
		{
			throw new IllegalArgumentException("The charset parameter is no longer supported.  Please process your documents as Unicode instead.");
		} else
		{
			assureMatchVersion();
			return;
		}
	}

	public RussianLetterTokenizer create(Reader in)
	{
		return new RussianLetterTokenizer(luceneMatchVersion, in);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
