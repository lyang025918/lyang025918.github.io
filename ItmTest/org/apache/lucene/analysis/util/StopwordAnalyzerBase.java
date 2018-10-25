// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StopwordAnalyzerBase.java

package org.apache.lucene.analysis.util;

import java.io.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.IOUtils;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.util:
//			CharArraySet, WordlistLoader

public abstract class StopwordAnalyzerBase extends Analyzer
{

	protected final CharArraySet stopwords;
	protected final Version matchVersion;

	public CharArraySet getStopwordSet()
	{
		return stopwords;
	}

	protected StopwordAnalyzerBase(Version version, CharArraySet stopwords)
	{
		matchVersion = version;
		this.stopwords = stopwords != null ? CharArraySet.unmodifiableSet(CharArraySet.copy(version, stopwords)) : CharArraySet.EMPTY_SET;
	}

	protected StopwordAnalyzerBase(Version version)
	{
		this(version, null);
	}

	protected static CharArraySet loadStopwordSet(boolean ignoreCase, Class aClass, String resource, String comment)
		throws IOException
	{
		Reader reader = null;
		CharArraySet chararrayset;
		reader = IOUtils.getDecodingReader(aClass.getResourceAsStream(resource), IOUtils.CHARSET_UTF_8);
		chararrayset = WordlistLoader.getWordSet(reader, comment, new CharArraySet(Version.LUCENE_31, 16, ignoreCase));
		IOUtils.close(new Closeable[] {
			reader
		});
		return chararrayset;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			reader
		});
		throw exception;
	}

	protected static CharArraySet loadStopwordSet(File stopwords, Version matchVersion)
		throws IOException
	{
		Reader reader = null;
		CharArraySet chararrayset;
		reader = IOUtils.getDecodingReader(stopwords, IOUtils.CHARSET_UTF_8);
		chararrayset = WordlistLoader.getWordSet(reader, matchVersion);
		IOUtils.close(new Closeable[] {
			reader
		});
		return chararrayset;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			reader
		});
		throw exception;
	}

	protected static CharArraySet loadStopwordSet(Reader stopwords, Version matchVersion)
		throws IOException
	{
		CharArraySet chararrayset = WordlistLoader.getWordSet(stopwords, matchVersion);
		IOUtils.close(new Closeable[] {
			stopwords
		});
		return chararrayset;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			stopwords
		});
		throw exception;
	}
}
