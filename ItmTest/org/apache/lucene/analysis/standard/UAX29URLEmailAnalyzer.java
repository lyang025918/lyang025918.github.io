// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UAX29URLEmailAnalyzer.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			UAX29URLEmailTokenizer, StandardFilter

public final class UAX29URLEmailAnalyzer extends StopwordAnalyzerBase
{

	public static final int DEFAULT_MAX_TOKEN_LENGTH = 255;
	private int maxTokenLength;
	public static final CharArraySet STOP_WORDS_SET;

	public UAX29URLEmailAnalyzer(Version matchVersion, CharArraySet stopWords)
	{
		super(matchVersion, stopWords);
		maxTokenLength = 255;
	}

	public UAX29URLEmailAnalyzer(Version matchVersion)
	{
		this(matchVersion, STOP_WORDS_SET);
	}

	public UAX29URLEmailAnalyzer(Version matchVersion, Reader stopwords)
		throws IOException
	{
		this(matchVersion, loadStopwordSet(stopwords, matchVersion));
	}

	public void setMaxTokenLength(int length)
	{
		maxTokenLength = length;
	}

	public int getMaxTokenLength()
	{
		return maxTokenLength;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		final UAX29URLEmailTokenizer src = new UAX29URLEmailTokenizer(matchVersion, reader);
		src.setMaxTokenLength(maxTokenLength);
		TokenStream tok = new StandardFilter(matchVersion, src);
		tok = new LowerCaseFilter(matchVersion, tok);
		tok = new StopFilter(matchVersion, tok, stopwords);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tok, src) {

			final UAX29URLEmailTokenizer val$src;
			final UAX29URLEmailAnalyzer this$0;

			protected void setReader(Reader reader)
				throws IOException
			{
				src.setMaxTokenLength(maxTokenLength);
				super.setReader(reader);
			}

			
			{
				this$0 = UAX29URLEmailAnalyzer.this;
				src = uax29urlemailtokenizer;
				super(x0, x1);
			}
		};
	}

	static 
	{
		STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
	}

}
