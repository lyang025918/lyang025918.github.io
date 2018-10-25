// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StopFilterFactory.java

package org.apache.lucene.analysis.core;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.core:
//			StopFilter, StopAnalyzer

public class StopFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArraySet stopWords;
	private boolean ignoreCase;
	private boolean enablePositionIncrements;

	public StopFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String stopWordFiles = (String)args.get("words");
		ignoreCase = getBoolean("ignoreCase", false);
		enablePositionIncrements = getBoolean("enablePositionIncrements", false);
		if (stopWordFiles != null)
		{
			if ("snowball".equalsIgnoreCase((String)args.get("format")))
				stopWords = getSnowballWordSet(loader, stopWordFiles, ignoreCase);
			else
				stopWords = getWordSet(loader, stopWordFiles, ignoreCase);
		} else
		{
			stopWords = new CharArraySet(luceneMatchVersion, StopAnalyzer.ENGLISH_STOP_WORDS_SET, ignoreCase);
		}
	}

	public boolean isEnablePositionIncrements()
	{
		return enablePositionIncrements;
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	public CharArraySet getStopWords()
	{
		return stopWords;
	}

	public TokenStream create(TokenStream input)
	{
		StopFilter stopFilter = new StopFilter(luceneMatchVersion, input, stopWords);
		stopFilter.setEnablePositionIncrements(enablePositionIncrements);
		return stopFilter;
	}
}
