// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CorePlusExtensionsParser.java

package org.apache.lucene.queryparser.xml;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.BoostingQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.DuplicateFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.FuzzyLikeThisQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.LikeThisQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.TermsFilterBuilder;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			CoreParser, FilterBuilderFactory, QueryBuilderFactory

public class CorePlusExtensionsParser extends CoreParser
{

	public CorePlusExtensionsParser(Analyzer analyzer, QueryParser parser)
	{
		this(null, analyzer, parser);
	}

	public CorePlusExtensionsParser(String defaultField, Analyzer analyzer)
	{
		this(defaultField, analyzer, null);
	}

	private CorePlusExtensionsParser(String defaultField, Analyzer analyzer, QueryParser parser)
	{
		super(defaultField, analyzer, parser);
		filterFactory.addBuilder("TermsFilter", new TermsFilterBuilder(analyzer));
		filterFactory.addBuilder("BooleanFilter", new BooleanFilterBuilder(filterFactory));
		filterFactory.addBuilder("DuplicateFilter", new DuplicateFilterBuilder());
		String fields[] = {
			"contents"
		};
		queryFactory.addBuilder("LikeThisQuery", new LikeThisQueryBuilder(analyzer, fields));
		queryFactory.addBuilder("BoostingQuery", new BoostingQueryBuilder(queryFactory));
		queryFactory.addBuilder("FuzzyLikeThisQuery", new FuzzyLikeThisQueryBuilder(analyzer));
	}
}
