// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicLetterTokenizerFactory.java

package org.apache.lucene.analysis.ar;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

// Referenced classes of package org.apache.lucene.analysis.ar:
//			ArabicLetterTokenizer

/**
 * @deprecated Class ArabicLetterTokenizerFactory is deprecated
 */

public class ArabicLetterTokenizerFactory extends TokenizerFactory
{

	public ArabicLetterTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public ArabicLetterTokenizer create(Reader input)
	{
		return new ArabicLetterTokenizer(luceneMatchVersion, input);
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
