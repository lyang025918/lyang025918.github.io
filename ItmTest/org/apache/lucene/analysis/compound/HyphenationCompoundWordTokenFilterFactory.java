// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HyphenationCompoundWordTokenFilterFactory.java

package org.apache.lucene.analysis.compound;

import java.io.*;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.compound.hyphenation.HyphenationTree;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;
import org.xml.sax.InputSource;

// Referenced classes of package org.apache.lucene.analysis.compound:
//			HyphenationCompoundWordTokenFilter

public class HyphenationCompoundWordTokenFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArraySet dictionary;
	private HyphenationTree hyphenator;
	private String dictFile;
	private String hypFile;
	private String encoding;
	private int minWordSize;
	private int minSubwordSize;
	private int maxSubwordSize;
	private boolean onlyLongestMatch;

	public HyphenationCompoundWordTokenFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		dictFile = (String)args.get("dictionary");
		if (args.containsKey("encoding"))
			encoding = (String)args.get("encoding");
		hypFile = (String)args.get("hyphenator");
		if (null == hypFile)
		{
			throw new IllegalArgumentException("Missing required parameter: hyphenator");
		} else
		{
			minWordSize = getInt("minWordSize", 5);
			minSubwordSize = getInt("minSubwordSize", 2);
			maxSubwordSize = getInt("maxSubwordSize", 15);
			onlyLongestMatch = getBoolean("onlyLongestMatch", false);
			return;
		}
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		InputStream stream = null;
		if (dictFile != null)
			dictionary = getWordSet(loader, dictFile, false);
		stream = loader.openResource(hypFile);
		InputSource is = new InputSource(stream);
		is.setEncoding(encoding);
		is.setSystemId(hypFile);
		hyphenator = HyphenationCompoundWordTokenFilter.getHyphenationTree(is);
		IOUtils.closeWhileHandlingException(new Closeable[] {
			stream
		});
		break MISSING_BLOCK_LABEL_97;
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(new Closeable[] {
			stream
		});
		throw exception;
	}

	public HyphenationCompoundWordTokenFilter create(TokenStream input)
	{
		return new HyphenationCompoundWordTokenFilter(luceneMatchVersion, input, hyphenator, dictionary, minWordSize, minSubwordSize, maxSubwordSize, onlyLongestMatch);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
