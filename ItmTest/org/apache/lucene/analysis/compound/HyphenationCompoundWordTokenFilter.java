// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HyphenationCompoundWordTokenFilter.java

package org.apache.lucene.analysis.compound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.compound.hyphenation.Hyphenation;
import org.apache.lucene.analysis.compound.hyphenation.HyphenationTree;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;
import org.xml.sax.InputSource;

// Referenced classes of package org.apache.lucene.analysis.compound:
//			CompoundWordTokenFilterBase

public class HyphenationCompoundWordTokenFilter extends CompoundWordTokenFilterBase
{

	private HyphenationTree hyphenator;

	public HyphenationCompoundWordTokenFilter(Version matchVersion, TokenStream input, HyphenationTree hyphenator, CharArraySet dictionary)
	{
		this(matchVersion, input, hyphenator, dictionary, 5, 2, 15, false);
	}

	public HyphenationCompoundWordTokenFilter(Version matchVersion, TokenStream input, HyphenationTree hyphenator, CharArraySet dictionary, int minWordSize, int minSubwordSize, int maxSubwordSize, 
			boolean onlyLongestMatch)
	{
		super(matchVersion, input, dictionary, minWordSize, minSubwordSize, maxSubwordSize, onlyLongestMatch);
		this.hyphenator = hyphenator;
	}

	public HyphenationCompoundWordTokenFilter(Version matchVersion, TokenStream input, HyphenationTree hyphenator, int minWordSize, int minSubwordSize, int maxSubwordSize)
	{
		this(matchVersion, input, hyphenator, null, minWordSize, minSubwordSize, maxSubwordSize, false);
	}

	public HyphenationCompoundWordTokenFilter(Version matchVersion, TokenStream input, HyphenationTree hyphenator)
	{
		this(matchVersion, input, hyphenator, 5, 2, 15);
	}

	public static HyphenationTree getHyphenationTree(String hyphenationFilename)
		throws IOException
	{
		return getHyphenationTree(new InputSource(hyphenationFilename));
	}

	public static HyphenationTree getHyphenationTree(File hyphenationFile)
		throws IOException
	{
		return getHyphenationTree(new InputSource(hyphenationFile.toURL().toExternalForm()));
	}

	public static HyphenationTree getHyphenationTree(InputSource hyphenationSource)
		throws IOException
	{
		HyphenationTree tree = new HyphenationTree();
		tree.loadPatterns(hyphenationSource);
		return tree;
	}

	protected void decompose()
	{
		Hyphenation hyphens = hyphenator.hyphenate(termAtt.buffer(), 0, termAtt.length(), 1, 1);
		if (hyphens == null)
			return;
		int hyp[] = hyphens.getHyphenationPoints();
		for (int i = 0; i < hyp.length; i++)
		{
			int remaining = hyp.length - i;
			int start = hyp[i];
			CompoundWordTokenFilterBase.CompoundToken longestMatchToken = null;
			for (int j = 1; j < remaining; j++)
			{
				int partLength = hyp[i + j] - start;
				if (partLength > maxSubwordSize)
					break;
				if (partLength < minSubwordSize)
					continue;
				if (dictionary == null || dictionary.contains(termAtt.buffer(), start, partLength))
				{
					if (onlyLongestMatch)
					{
						if (longestMatchToken != null)
						{
							if (longestMatchToken.txt.length() < partLength)
								longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength);
						} else
						{
							longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength);
						}
					} else
					{
						tokens.add(new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength));
					}
					continue;
				}
				if (!dictionary.contains(termAtt.buffer(), start, partLength - 1))
					continue;
				if (onlyLongestMatch)
				{
					if (longestMatchToken != null)
					{
						if (longestMatchToken.txt.length() < partLength - 1)
							longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength - 1);
					} else
					{
						longestMatchToken = new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength - 1);
					}
				} else
				{
					tokens.add(new CompoundWordTokenFilterBase.CompoundToken(this, start, partLength - 1));
				}
			}

			if (onlyLongestMatch && longestMatchToken != null)
				tokens.add(longestMatchToken);
		}

	}
}
