// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSTSynonymFilterFactory.java

package org.apache.lucene.analysis.synonym;

import java.io.*;
import java.nio.charset.*;
import java.text.ParseException;
import java.util.*;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SynonymFilter, SolrSynonymParser, WordnetSynonymParser, SynonymMap

/**
 * @deprecated Class FSTSynonymFilterFactory is deprecated
 */

final class FSTSynonymFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private SynonymMap map;
	private boolean ignoreCase;

	FSTSynonymFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return ((TokenStream) (map.fst != null ? new SynonymFilter(input, map, ignoreCase) : input));
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		final boolean ignoreCase = getBoolean("ignoreCase", false);
		this.ignoreCase = ignoreCase;
		String tf = (String)args.get("tokenizerFactory");
		final TokenizerFactory factory = tf != null ? loadTokenizerFactory(loader, tf) : null;
		Analyzer analyzer = new Analyzer() {

			final TokenizerFactory val$factory;
			final boolean val$ignoreCase;
			final FSTSynonymFilterFactory this$0;

			protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
			{
				Tokenizer tokenizer = ((Tokenizer) (factory != null ? factory.create(reader) : ((Tokenizer) (new WhitespaceTokenizer(Version.LUCENE_31, reader)))));
				TokenStream stream = ((TokenStream) (ignoreCase ? ((TokenStream) (new LowerCaseFilter(Version.LUCENE_31, tokenizer))) : ((TokenStream) (tokenizer))));
				return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(tokenizer, stream);
			}

			
			{
				this$0 = FSTSynonymFilterFactory.this;
				factory = tokenizerfactory;
				ignoreCase = flag;
				super();
			}
		};
		String format = (String)args.get("format");
		try
		{
			if (format == null || format.equals("solr"))
				map = loadSolrSynonyms(loader, true, analyzer);
			else
			if (format.equals("wordnet"))
				map = loadWordnetSynonyms(loader, true, analyzer);
			else
				throw new IllegalArgumentException((new StringBuilder()).append("Unrecognized synonyms format: ").append(format).toString());
		}
		catch (ParseException e)
		{
			throw new IOException("Exception thrown while loading synonyms", e);
		}
	}

	private SynonymMap loadSolrSynonyms(ResourceLoader loader, boolean dedup, Analyzer analyzer)
		throws IOException, ParseException
	{
		boolean expand = getBoolean("expand", true);
		String synonyms = (String)args.get("synonyms");
		if (synonyms == null)
			throw new IllegalArgumentException("Missing required argument 'synonyms'.");
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		SolrSynonymParser parser = new SolrSynonymParser(dedup, expand, analyzer);
		File synonymFile = new File(synonyms);
		if (synonymFile.exists())
		{
			decoder.reset();
			parser.add(new InputStreamReader(loader.openResource(synonyms), decoder));
		} else
		{
			List files = splitFileNames(synonyms);
			String file;
			for (Iterator i$ = files.iterator(); i$.hasNext(); parser.add(new InputStreamReader(loader.openResource(file), decoder)))
			{
				file = (String)i$.next();
				decoder.reset();
			}

		}
		return parser.build();
	}

	private SynonymMap loadWordnetSynonyms(ResourceLoader loader, boolean dedup, Analyzer analyzer)
		throws IOException, ParseException
	{
		boolean expand = getBoolean("expand", true);
		String synonyms = (String)args.get("synonyms");
		if (synonyms == null)
			throw new IllegalArgumentException("Missing required argument 'synonyms'.");
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		WordnetSynonymParser parser = new WordnetSynonymParser(dedup, expand, analyzer);
		File synonymFile = new File(synonyms);
		if (synonymFile.exists())
		{
			decoder.reset();
			parser.add(new InputStreamReader(loader.openResource(synonyms), decoder));
		} else
		{
			List files = splitFileNames(synonyms);
			String file;
			for (Iterator i$ = files.iterator(); i$.hasNext(); parser.add(new InputStreamReader(loader.openResource(file), decoder)))
			{
				file = (String)i$.next();
				decoder.reset();
			}

		}
		return parser.build();
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
}
