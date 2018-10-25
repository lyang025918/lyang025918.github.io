// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SlowSynonymFilterFactory.java

package org.apache.lucene.analysis.synonym;

import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SlowSynonymMap, SlowSynonymFilter

/**
 * @deprecated Class SlowSynonymFilterFactory is deprecated
 */

final class SlowSynonymFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private SlowSynonymMap synMap;

	SlowSynonymFilterFactory()
	{
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String synonyms = (String)args.get("synonyms");
		if (synonyms == null)
			throw new IllegalArgumentException("Missing required argument 'synonyms'.");
		boolean ignoreCase = getBoolean("ignoreCase", false);
		boolean expand = getBoolean("expand", true);
		String tf = (String)args.get("tokenizerFactory");
		TokenizerFactory tokFactory = null;
		if (tf != null)
			tokFactory = loadTokenizerFactory(loader, tf);
		Iterable wlist = loadRules(synonyms, loader);
		synMap = new SlowSynonymMap(ignoreCase);
		parseRules(wlist, synMap, "=>", ",", expand, tokFactory);
	}

	protected Iterable loadRules(String synonyms, ResourceLoader loader)
		throws IOException
	{
		List wlist = null;
		File synonymFile = new File(synonyms);
		if (synonymFile.exists())
		{
			wlist = getLines(loader, synonyms);
		} else
		{
			List files = splitFileNames(synonyms);
			wlist = new ArrayList();
			List lines;
			for (Iterator i$ = files.iterator(); i$.hasNext(); wlist.addAll(lines))
			{
				String file = (String)i$.next();
				lines = getLines(loader, file.trim());
			}

		}
		return wlist;
	}

	static void parseRules(Iterable rules, SlowSynonymMap map, String mappingSep, String synSep, boolean expansion, TokenizerFactory tokFactory)
		throws IOException
	{
		int count = 0;
		for (Iterator i$ = rules.iterator(); i$.hasNext();)
		{
			String rule = (String)i$.next();
			List mapping = splitSmart(rule, mappingSep, false);
			if (mapping.size() > 2)
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid Synonym Rule:").append(rule).toString());
			List source;
			List target;
			if (mapping.size() == 2)
			{
				source = getSynList((String)mapping.get(0), synSep, tokFactory);
				target = getSynList((String)mapping.get(1), synSep, tokFactory);
			} else
			{
				source = getSynList((String)mapping.get(0), synSep, tokFactory);
				if (expansion)
				{
					target = source;
				} else
				{
					target = new ArrayList(1);
					target.add(source.get(0));
				}
			}
			boolean includeOrig = false;
			Iterator i$ = source.iterator();
			while (i$.hasNext()) 
			{
				List fromToks = (List)i$.next();
				count++;
				Iterator i$ = target.iterator();
				while (i$.hasNext()) 
				{
					List toToks = (List)i$.next();
					map.add(fromToks, SlowSynonymMap.makeTokens(toToks), includeOrig, true);
				}
			}
		}

	}

	private static List getSynList(String str, String separator, TokenizerFactory tokFactory)
		throws IOException
	{
		List strList = splitSmart(str, separator, false);
		List synList = new ArrayList();
		List tokList;
		for (Iterator i$ = strList.iterator(); i$.hasNext(); synList.add(tokList))
		{
			String toks = (String)i$.next();
			tokList = tokFactory != null ? splitByTokenizer(toks, tokFactory) : splitWS(toks, true);
		}

		return synList;
	}

	private static List splitByTokenizer(String source, TokenizerFactory tokFactory)
		throws IOException
	{
		StringReader reader;
		TokenStream ts;
		List tokList;
		reader = new StringReader(source);
		ts = loadTokenizer(tokFactory, reader);
		tokList = new ArrayList();
		CharTermAttribute termAtt = (CharTermAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		do
		{
			if (!ts.incrementToken())
				break;
			if (termAtt.length() > 0)
				tokList.add(termAtt.toString());
		} while (true);
		reader.close();
		break MISSING_BLOCK_LABEL_85;
		Exception exception;
		exception;
		reader.close();
		throw exception;
		return tokList;
	}

	private TokenizerFactory loadTokenizerFactory(ResourceLoader loader, String cname)
		throws IOException
	{
		TokenizerFactory tokFactory = (TokenizerFactory)loader.newInstance(cname, org/apache/lucene/analysis/util/TokenizerFactory);
		tokFactory.setLuceneMatchVersion(luceneMatchVersion);
		tokFactory.init(args);
		if (tokFactory instanceof ResourceLoaderAware)
			((ResourceLoaderAware)tokFactory).inform(loader);
		return tokFactory;
	}

	private static TokenStream loadTokenizer(TokenizerFactory tokFactory, Reader reader)
	{
		return tokFactory.create(reader);
	}

	public SlowSynonymMap getSynonymMap()
	{
		return synMap;
	}

	public SlowSynonymFilter create(TokenStream input)
	{
		return new SlowSynonymFilter(input, synMap);
	}

	public static List splitWS(String s, boolean decode)
	{
		ArrayList lst = new ArrayList(2);
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int end = s.length();
		do
		{
			if (pos >= end)
				break;
			char ch = s.charAt(pos++);
			if (Character.isWhitespace(ch))
			{
				if (sb.length() > 0)
				{
					lst.add(sb.toString());
					sb = new StringBuilder();
				}
				continue;
			}
			if (ch == '\\')
			{
				if (!decode)
					sb.append(ch);
				if (pos >= end)
					break;
				ch = s.charAt(pos++);
				if (decode)
					switch (ch)
					{
					case 110: // 'n'
						ch = '\n';
						break;

					case 116: // 't'
						ch = '\t';
						break;

					case 114: // 'r'
						ch = '\r';
						break;

					case 98: // 'b'
						ch = '\b';
						break;

					case 102: // 'f'
						ch = '\f';
						break;
					}
			}
			sb.append(ch);
		} while (true);
		if (sb.length() > 0)
			lst.add(sb.toString());
		return lst;
	}

	public static List splitSmart(String s, String separator, boolean decode)
	{
		ArrayList lst = new ArrayList(2);
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int end = s.length();
		do
		{
			if (pos >= end)
				break;
			if (s.startsWith(separator, pos))
			{
				if (sb.length() > 0)
				{
					lst.add(sb.toString());
					sb = new StringBuilder();
				}
				pos += separator.length();
				continue;
			}
			char ch = s.charAt(pos++);
			if (ch == '\\')
			{
				if (!decode)
					sb.append(ch);
				if (pos >= end)
					break;
				ch = s.charAt(pos++);
				if (decode)
					switch (ch)
					{
					case 110: // 'n'
						ch = '\n';
						break;

					case 116: // 't'
						ch = '\t';
						break;

					case 114: // 'r'
						ch = '\r';
						break;

					case 98: // 'b'
						ch = '\b';
						break;

					case 102: // 'f'
						ch = '\f';
						break;
					}
			}
			sb.append(ch);
		} while (true);
		if (sb.length() > 0)
			lst.add(sb.toString());
		return lst;
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
