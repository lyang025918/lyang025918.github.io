// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnowballPorterFilterFactory.java

package org.apache.lucene.analysis.snowball;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.miscellaneous.KeywordMarkerFilter;
import org.apache.lucene.analysis.util.*;
import org.tartarus.snowball.SnowballProgram;

// Referenced classes of package org.apache.lucene.analysis.snowball:
//			SnowballFilter

public class SnowballPorterFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	public static final String PROTECTED_TOKENS = "protected";
	private String language;
	private Class stemClass;
	private CharArraySet protectedWords;

	public SnowballPorterFilterFactory()
	{
		language = "English";
		protectedWords = null;
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String cfgLanguage = (String)args.get("language");
		if (cfgLanguage != null)
			language = cfgLanguage;
		String className = (new StringBuilder()).append("org.tartarus.snowball.ext.").append(language).append("Stemmer").toString();
		stemClass = ((SnowballProgram)loader.newInstance(className, org/tartarus/snowball/SnowballProgram)).getClass();
		String wordFiles = (String)args.get("protected");
		if (wordFiles != null)
			protectedWords = getWordSet(loader, wordFiles, false);
	}

	public TokenFilter create(TokenStream input)
	{
		SnowballProgram program;
		try
		{
			program = (SnowballProgram)stemClass.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException((new StringBuilder()).append("Error instantiating stemmer for language ").append(language).append("from class ").append(stemClass).toString(), e);
		}
		if (protectedWords != null)
			input = new KeywordMarkerFilter(input, protectedWords);
		return new SnowballFilter(input, program);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
