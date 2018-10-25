// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellStemFilterFactory.java

package org.apache.lucene.analysis.hunspell;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.analysis.hunspell:
//			HunspellDictionary, HunspellStemFilter

public class HunspellStemFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private static final String PARAM_DICTIONARY = "dictionary";
	private static final String PARAM_AFFIX = "affix";
	private static final String PARAM_IGNORE_CASE = "ignoreCase";
	private static final String PARAM_STRICT_AFFIX_PARSING = "strictAffixParsing";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private HunspellDictionary dictionary;
	private boolean ignoreCase;

	public HunspellStemFilterFactory()
	{
		ignoreCase = false;
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		List dictionaries;
		Exception exception;
		assureMatchVersion();
		String dictionaryArg = (String)args.get("dictionary");
		if (dictionaryArg == null)
			throw new IllegalArgumentException("Parameter dictionary is mandatory.");
		String dictionaryFiles[] = ((String)args.get("dictionary")).split(",");
		String affixFile = (String)args.get("affix");
		String pic = (String)args.get("ignoreCase");
		if (pic != null)
			if (pic.equalsIgnoreCase("true"))
				ignoreCase = true;
			else
			if (pic.equalsIgnoreCase("false"))
				ignoreCase = false;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Unknown value for ignoreCase: ").append(pic).append(". Must be true or false").toString());
		String strictAffixParsingParam = (String)args.get("strictAffixParsing");
		boolean strictAffixParsing = true;
		if (strictAffixParsingParam != null)
			if (strictAffixParsingParam.equalsIgnoreCase("false"))
				strictAffixParsing = false;
			else
			if (strictAffixParsingParam.equalsIgnoreCase("true"))
				strictAffixParsing = true;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Unknown value for strictAffixParsing: ").append(strictAffixParsingParam).append(". Must be true or false").toString());
		InputStream affix = null;
		dictionaries = new ArrayList();
		try
		{
			dictionaries = new ArrayList();
			String arr$[] = dictionaryFiles;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String file = arr$[i$];
				dictionaries.add(loader.openResource(file));
			}

			affix = loader.openResource(affixFile);
			dictionary = new HunspellDictionary(affix, dictionaries, luceneMatchVersion, ignoreCase, strictAffixParsing);
		}
		catch (ParseException e)
		{
			throw new IOException((new StringBuilder()).append("Unable to load hunspell data! [dictionary=").append((String)args.get("dictionary")).append(",affix=").append(affixFile).append("]").toString(), e);
		}
		finally
		{
			IOUtils.closeWhileHandlingException(new Closeable[] {
				affix
			});
		}
		IOUtils.closeWhileHandlingException(new Closeable[] {
			affix
		});
		IOUtils.closeWhileHandlingException(dictionaries);
		break MISSING_BLOCK_LABEL_452;
		IOUtils.closeWhileHandlingException(dictionaries);
		throw exception;
	}

	public TokenStream create(TokenStream tokenStream)
	{
		return new HunspellStemFilter(tokenStream, dictionary);
	}
}
