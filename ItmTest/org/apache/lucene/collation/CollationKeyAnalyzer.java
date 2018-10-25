// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollationKeyAnalyzer.java

package org.apache.lucene.collation;

import java.io.Reader;
import java.text.Collator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.collation:
//			CollationAttributeFactory, CollationKeyFilter

public final class CollationKeyAnalyzer extends Analyzer
{

	private final Collator collator;
	private final CollationAttributeFactory factory;
	private final Version matchVersion;

	public CollationKeyAnalyzer(Version matchVersion, Collator collator)
	{
		this.matchVersion = matchVersion;
		this.collator = collator;
		factory = new CollationAttributeFactory(collator);
	}

	/**
	 * @deprecated Method CollationKeyAnalyzer is deprecated
	 */

	public CollationKeyAnalyzer(Collator collator)
	{
		this(Version.LUCENE_31, collator);
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		if (matchVersion.onOrAfter(Version.LUCENE_40))
		{
			KeywordTokenizer tokenizer = new KeywordTokenizer(factory, reader, 256);
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tokenizer, tokenizer);
		} else
		{
			KeywordTokenizer tokenizer = new KeywordTokenizer(reader);
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tokenizer, new CollationKeyFilter(tokenizer, collator));
		}
	}
}
