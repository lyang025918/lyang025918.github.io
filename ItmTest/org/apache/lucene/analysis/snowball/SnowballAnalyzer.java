// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnowballAnalyzer.java

package org.apache.lucene.analysis.snowball;

import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tr.TurkishLowerCaseFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.snowball:
//			SnowballFilter

/**
 * @deprecated Class SnowballAnalyzer is deprecated
 */

public final class SnowballAnalyzer extends Analyzer
{

	private String name;
	private CharArraySet stopSet;
	private final Version matchVersion;

	public SnowballAnalyzer(Version matchVersion, String name)
	{
		this.name = name;
		this.matchVersion = matchVersion;
	}

	public SnowballAnalyzer(Version matchVersion, String name, CharArraySet stopWords)
	{
		this(matchVersion, name);
		stopSet = CharArraySet.unmodifiableSet(CharArraySet.copy(matchVersion, stopWords));
	}

	public org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		Tokenizer tokenizer = new StandardTokenizer(matchVersion, reader);
		TokenStream result = new StandardFilter(matchVersion, tokenizer);
		if (matchVersion.onOrAfter(Version.LUCENE_31) && (name.equals("English") || name.equals("Porter") || name.equals("Lovins")))
			result = new EnglishPossessiveFilter(result);
		if (matchVersion.onOrAfter(Version.LUCENE_31) && name.equals("Turkish"))
			result = new TurkishLowerCaseFilter(result);
		else
			result = new LowerCaseFilter(matchVersion, result);
		if (stopSet != null)
			result = new StopFilter(matchVersion, result, stopSet);
		result = new SnowballFilter(result, name);
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tokenizer, result);
	}
}
