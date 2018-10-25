// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DictionaryCompoundWordTokenFilterFactory.java

package org.apache.lucene.analysis.compound;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.compound:
//			DictionaryCompoundWordTokenFilter

public class DictionaryCompoundWordTokenFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArraySet dictionary;
	private String dictFile;
	private int minWordSize;
	private int minSubwordSize;
	private int maxSubwordSize;
	private boolean onlyLongestMatch;

	public DictionaryCompoundWordTokenFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		dictFile = (String)args.get("dictionary");
		if (null == dictFile)
		{
			throw new IllegalArgumentException("Missing required parameter: dictionary");
		} else
		{
			minWordSize = getInt("minWordSize", 5);
			minSubwordSize = getInt("minSubwordSize", 2);
			maxSubwordSize = getInt("maxSubwordSize", 15);
			onlyLongestMatch = getBoolean("onlyLongestMatch", true);
			return;
		}
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		dictionary = super.getWordSet(loader, dictFile, false);
	}

	public TokenStream create(TokenStream input)
	{
		return ((TokenStream) (dictionary != null ? new DictionaryCompoundWordTokenFilter(luceneMatchVersion, input, dictionary, minWordSize, minSubwordSize, maxSubwordSize, onlyLongestMatch) : input));
	}
}
