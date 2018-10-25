// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractAnalysisFactory.java

package org.apache.lucene.analysis.util;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharArraySet, ResourceLoader, WordlistLoader

public abstract class AbstractAnalysisFactory
{

	protected Map args;
	protected Version luceneMatchVersion;

	public AbstractAnalysisFactory()
	{
		luceneMatchVersion = null;
	}

	public void init(Map args)
	{
		this.args = args;
	}

	public Map getArgs()
	{
		return args;
	}

	protected final void assureMatchVersion()
	{
		if (luceneMatchVersion == null)
			throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: Factory '").append(getClass().getName()).append("' needs a 'luceneMatchVersion' parameter").toString());
		else
			return;
	}

	public void setLuceneMatchVersion(Version luceneMatchVersion)
	{
		this.luceneMatchVersion = luceneMatchVersion;
	}

	public Version getLuceneMatchVersion()
	{
		return luceneMatchVersion;
	}

	protected int getInt(String name)
	{
		return getInt(name, -1, false);
	}

	protected int getInt(String name, int defaultVal)
	{
		return getInt(name, defaultVal, true);
	}

	protected int getInt(String name, int defaultVal, boolean useDefault)
	{
		String s = (String)args.get(name);
		if (s == null)
		{
			if (useDefault)
				return defaultVal;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: missing parameter '").append(name).append("'").toString());
		} else
		{
			return Integer.parseInt(s);
		}
	}

	protected boolean getBoolean(String name, boolean defaultVal)
	{
		return getBoolean(name, defaultVal, true);
	}

	protected boolean getBoolean(String name, boolean defaultVal, boolean useDefault)
	{
		String s = (String)args.get(name);
		if (s == null)
		{
			if (useDefault)
				return defaultVal;
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: missing parameter '").append(name).append("'").toString());
		} else
		{
			return Boolean.parseBoolean(s);
		}
	}

	protected Pattern getPattern(String name)
	{
		String pat = (String)args.get(name);
		if (null == pat)
			throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: missing parameter '").append(name).append("'").toString());
		return Pattern.compile((String)args.get(name));
		PatternSyntaxException e;
		e;
		throw new IllegalArgumentException((new StringBuilder()).append("Configuration Error: '").append(name).append("' can not be parsed in ").append(getClass().getSimpleName()).toString(), e);
	}

	protected CharArraySet getWordSet(ResourceLoader loader, String wordFiles, boolean ignoreCase)
		throws IOException
	{
		assureMatchVersion();
		List files = splitFileNames(wordFiles);
		CharArraySet words = null;
		if (files.size() > 0)
		{
			words = new CharArraySet(luceneMatchVersion, files.size() * 10, ignoreCase);
			List wlist;
			for (Iterator i$ = files.iterator(); i$.hasNext(); words.addAll(StopFilter.makeStopSet(luceneMatchVersion, wlist, ignoreCase)))
			{
				String file = (String)i$.next();
				wlist = getLines(loader, file.trim());
			}

		}
		return words;
	}

	protected List getLines(ResourceLoader loader, String resource)
		throws IOException
	{
		return WordlistLoader.getLines(loader.openResource(resource), IOUtils.CHARSET_UTF_8);
	}

	protected CharArraySet getSnowballWordSet(ResourceLoader loader, String wordFiles, boolean ignoreCase)
		throws IOException
	{
		List files;
		CharArraySet words;
		assureMatchVersion();
		files = splitFileNames(wordFiles);
		words = null;
		if (files.size() <= 0) goto _L2; else goto _L1
_L1:
		Iterator i$;
		words = new CharArraySet(luceneMatchVersion, files.size() * 10, ignoreCase);
		i$ = files.iterator();
_L3:
		String file;
		InputStream stream;
		Reader reader;
		if (!i$.hasNext())
			break; /* Loop/switch isn't completed */
		file = (String)i$.next();
		stream = null;
		reader = null;
		stream = loader.openResource(file.trim());
		CharsetDecoder decoder = IOUtils.CHARSET_UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		reader = new InputStreamReader(stream, decoder);
		WordlistLoader.getSnowballWordSet(reader, words);
		IOUtils.closeWhileHandlingException(new Closeable[] {
			reader, stream
		});
		if (true) goto _L3; else goto _L2
		Exception exception;
		exception;
		IOUtils.closeWhileHandlingException(new Closeable[] {
			reader, stream
		});
		throw exception;
_L2:
		return words;
	}

	protected List splitFileNames(String fileNames)
	{
		if (fileNames == null)
			return Collections.emptyList();
		List result = new ArrayList();
		String arr$[] = fileNames.split("(?<!\\\\),");
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String file = arr$[i$];
			result.add(file.replaceAll("\\\\(?=,)", ""));
		}

		return result;
	}
}
