// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CapitalizationFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.util.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			CapitalizationFilter

public class CapitalizationFilterFactory extends TokenFilterFactory
{

	public static final String KEEP = "keep";
	public static final String KEEP_IGNORE_CASE = "keepIgnoreCase";
	public static final String OK_PREFIX = "okPrefix";
	public static final String MIN_WORD_LENGTH = "minWordLength";
	public static final String MAX_WORD_COUNT = "maxWordCount";
	public static final String MAX_TOKEN_LENGTH = "maxTokenLength";
	public static final String ONLY_FIRST_WORD = "onlyFirstWord";
	public static final String FORCE_FIRST_LETTER = "forceFirstLetter";
	CharArraySet keep;
	Collection okPrefix;
	int minWordLength;
	int maxWordCount;
	int maxTokenLength;
	boolean onlyFirstWord;
	boolean forceFirstLetter;

	public CapitalizationFilterFactory()
	{
		okPrefix = Collections.emptyList();
		minWordLength = 0;
		maxWordCount = 0x7fffffff;
		maxTokenLength = 0x7fffffff;
		onlyFirstWord = true;
		forceFirstLetter = true;
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		String k = (String)args.get("keep");
		if (k != null)
		{
			StringTokenizer st = new StringTokenizer(k);
			boolean ignoreCase = false;
			String ignoreStr = (String)args.get("keepIgnoreCase");
			if ("true".equalsIgnoreCase(ignoreStr))
				ignoreCase = true;
			keep = new CharArraySet(luceneMatchVersion, 10, ignoreCase);
			for (; st.hasMoreTokens(); keep.add(k.toCharArray()))
				k = st.nextToken().trim();

		}
		k = (String)args.get("okPrefix");
		if (k != null)
		{
			okPrefix = new ArrayList();
			for (StringTokenizer st = new StringTokenizer(k); st.hasMoreTokens(); okPrefix.add(st.nextToken().trim().toCharArray()));
		}
		k = (String)args.get("minWordLength");
		if (k != null)
			minWordLength = Integer.valueOf(k).intValue();
		k = (String)args.get("maxWordCount");
		if (k != null)
			maxWordCount = Integer.valueOf(k).intValue();
		k = (String)args.get("maxTokenLength");
		if (k != null)
			maxTokenLength = Integer.valueOf(k).intValue();
		k = (String)args.get("onlyFirstWord");
		if (k != null)
			onlyFirstWord = Boolean.valueOf(k).booleanValue();
		k = (String)args.get("forceFirstLetter");
		if (k != null)
			forceFirstLetter = Boolean.valueOf(k).booleanValue();
	}

	public CapitalizationFilter create(TokenStream input)
	{
		return new CapitalizationFilter(input, onlyFirstWord, keep, forceFirstLetter, okPrefix, minWordLength, maxWordCount, maxTokenLength);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
