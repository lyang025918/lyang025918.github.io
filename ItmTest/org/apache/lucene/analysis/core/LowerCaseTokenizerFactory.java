// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LowerCaseTokenizerFactory.java

package org.apache.lucene.analysis.core;

import java.io.Reader;
import java.util.Map;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.core:
//			LowerCaseTokenizer, LowerCaseFilterFactory

public class LowerCaseTokenizerFactory extends TokenizerFactory
	implements MultiTermAwareComponent
{

	public LowerCaseTokenizerFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public LowerCaseTokenizer create(Reader input)
	{
		return new LowerCaseTokenizer(luceneMatchVersion, input);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		LowerCaseFilterFactory filt = new LowerCaseFilterFactory();
		filt.setLuceneMatchVersion(luceneMatchVersion);
		filt.init(args);
		return filt;
	}

	public volatile Tokenizer create(Reader x0)
	{
		return create(x0);
	}
}
