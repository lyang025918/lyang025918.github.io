// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonGramsQueryFilterFactory.java

package org.apache.lucene.analysis.commongrams;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.commongrams:
//			CommonGramsFilter, CommonGramsQueryFilter

public class CommonGramsQueryFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArraySet commonWords;
	private boolean ignoreCase;

	public CommonGramsQueryFilterFactory()
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
		String commonWordFiles = (String)args.get("words");
		ignoreCase = getBoolean("ignoreCase", false);
		if (commonWordFiles != null)
		{
			if ("snowball".equalsIgnoreCase((String)args.get("format")))
				commonWords = getSnowballWordSet(loader, commonWordFiles, ignoreCase);
			else
				commonWords = getWordSet(loader, commonWordFiles, ignoreCase);
		} else
		{
			commonWords = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
		}
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	public CharArraySet getCommonWords()
	{
		return commonWords;
	}

	public CommonGramsQueryFilter create(TokenStream input)
	{
		CommonGramsFilter commonGrams = new CommonGramsFilter(luceneMatchVersion, input, commonWords);
		CommonGramsQueryFilter commonGramsQuery = new CommonGramsQueryFilter(commonGrams);
		return commonGramsQuery;
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
