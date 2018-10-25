// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeepWordFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			KeepWordFilter

public class KeepWordFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArraySet words;
	private boolean ignoreCase;
	private boolean enablePositionIncrements;

	public KeepWordFilterFactory()
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
		String wordFiles = (String)args.get("words");
		ignoreCase = getBoolean("ignoreCase", false);
		enablePositionIncrements = getBoolean("enablePositionIncrements", false);
		if (wordFiles != null)
			words = getWordSet(loader, wordFiles, ignoreCase);
	}

	public void setWords(Set words)
	{
		this.words = new CharArraySet(luceneMatchVersion, words, ignoreCase);
	}

	public void setIgnoreCase(boolean ignoreCase)
	{
		if (words != null && this.ignoreCase != ignoreCase)
			words = new CharArraySet(luceneMatchVersion, words, ignoreCase);
		this.ignoreCase = ignoreCase;
	}

	public boolean isEnablePositionIncrements()
	{
		return enablePositionIncrements;
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	public CharArraySet getWords()
	{
		return words;
	}

	public TokenStream create(TokenStream input)
	{
		return ((TokenStream) (words != null ? new KeepWordFilter(enablePositionIncrements, input, words) : input));
	}
}
