// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DictionaryCompoundWordTokenFilter.java

package org.apache.lucene.analysis.compound;

import java.util.LinkedList;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.compound:
//			CompoundWordTokenFilterBase

public class DictionaryCompoundWordTokenFilter extends CompoundWordTokenFilterBase
{

	public DictionaryCompoundWordTokenFilter(Version matchVersion, TokenStream input, CharArraySet dictionary)
	{
		super(matchVersion, input, dictionary);
		if (dictionary == null)
			throw new IllegalArgumentException("dictionary cannot be null");
		else
			return;
	}

	public DictionaryCompoundWordTokenFilter(Version matchVersion, TokenStream input, CharArraySet dictionary, int minWordSize, int minSubwordSize, int maxSubwordSize, boolean onlyLongestMatch)
	{
		super(matchVersion, input, dictionary, minWordSize, minSubwordSize, maxSubwordSize, onlyLongestMatch);
		if (dictionary == null)
			throw new IllegalArgumentException("dictionary cannot be null");
		else
			return;
	}

	protected void decompose()
	{
		int len = termAtt.length();
		for (int i = 0; i <= len - minSubwordSize; i++)
		{
			CompoundWordTokenFilterBase.CompoundToken longestMatchToken = null;
			for (int j = minSubwordSize; j <= maxSubwordSize && i + j <= len; j++)
			{
				if (!dictionary.contains(termAtt.buffer(), i, j))
					continue;
				if (onlyLongestMatch)
				{
					if (longestMatchToken != null)
					{
						if (longestMatchToken.txt.length() < j)
							longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, i, j);
					} else
					{
						longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, i, j);
					}
				} else
				{
					tokens.add(new CompoundWordTokenFilterBase.CompoundToken(this, i, j));
				}
			}

			if (onlyLongestMatch && longestMatchToken != null)
				tokens.add(longestMatchToken);
		}

	}
}
