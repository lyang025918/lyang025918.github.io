// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CoreParser.java

package org.apache.lucene.queryparser.xml;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.BoostingTermBuilder;
import org.apache.lucene.queryparser.xml.builders.CachedFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.ConstantScoreQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.DisjunctionMaxQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.FilteredQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.MatchAllDocsQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.NumericRangeFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.NumericRangeQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanFirstBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanNearBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanNotBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanOrBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanOrTermsBuilder;
import org.apache.lucene.queryparser.xml.builders.SpanQueryBuilderFactory;
import org.apache.lucene.queryparser.xml.builders.SpanTermBuilder;
import org.apache.lucene.queryparser.xml.builders.TermQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.TermsQueryBuilder;
import org.apache.lucene.queryparser.xml.builders.UserInputQueryBuilder;
import org.apache.lucene.search.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// Referenced classes of package org.apache.lucene.queryparser.xml:
//			FilterBuilderFactory, QueryBuilderFactory, ParserException, QueryBuilder, 
//			FilterBuilder

public class CoreParser
	implements QueryBuilder
{

	protected Analyzer analyzer;
	protected QueryParser parser;
	protected QueryBuilderFactory queryFactory;
	protected FilterBuilderFactory filterFactory;
	public static int maxNumCachedFilters = 20;

	public CoreParser(Analyzer analyzer, QueryParser parser)
	{
		this(null, analyzer, parser);
	}

	public CoreParser(String defaultField, Analyzer analyzer)
	{
		this(defaultField, analyzer, null);
	}

	protected CoreParser(String defaultField, Analyzer analyzer, QueryParser parser)
	{
		this.analyzer = analyzer;
		this.parser = parser;
		filterFactory = new FilterBuilderFactory();
		filterFactory.addBuilder("RangeFilter", new RangeFilterBuilder());
		filterFactory.addBuilder("NumericRangeFilter", new NumericRangeFilterBuilder());
		queryFactory = new QueryBuilderFactory();
		queryFactory.addBuilder("TermQuery", new TermQueryBuilder());
		queryFactory.addBuilder("TermsQuery", new TermsQueryBuilder(analyzer));
		queryFactory.addBuilder("MatchAllDocsQuery", new MatchAllDocsQueryBuilder());
		queryFactory.addBuilder("BooleanQuery", new BooleanQueryBuilder(queryFactory));
		queryFactory.addBuilder("NumericRangeQuery", new NumericRangeQueryBuilder());
		queryFactory.addBuilder("DisjunctionMaxQuery", new DisjunctionMaxQueryBuilder(queryFactory));
		if (parser != null)
			queryFactory.addBuilder("UserQuery", new UserInputQueryBuilder(parser));
		else
			queryFactory.addBuilder("UserQuery", new UserInputQueryBuilder(defaultField, analyzer));
		queryFactory.addBuilder("FilteredQuery", new FilteredQueryBuilder(filterFactory, queryFactory));
		queryFactory.addBuilder("ConstantScoreQuery", new ConstantScoreQueryBuilder(filterFactory));
		filterFactory.addBuilder("CachedFilter", new CachedFilterBuilder(queryFactory, filterFactory, maxNumCachedFilters));
		SpanQueryBuilderFactory sqof = new SpanQueryBuilderFactory();
		SpanNearBuilder snb = new SpanNearBuilder(sqof);
		sqof.addBuilder("SpanNear", snb);
		queryFactory.addBuilder("SpanNear", snb);
		BoostingTermBuilder btb = new BoostingTermBuilder();
		sqof.addBuilder("BoostingTermQuery", btb);
		queryFactory.addBuilder("BoostingTermQuery", btb);
		SpanTermBuilder snt = new SpanTermBuilder();
		sqof.addBuilder("SpanTerm", snt);
		queryFactory.addBuilder("SpanTerm", snt);
		SpanOrBuilder sot = new SpanOrBuilder(sqof);
		sqof.addBuilder("SpanOr", sot);
		queryFactory.addBuilder("SpanOr", sot);
		SpanOrTermsBuilder sots = new SpanOrTermsBuilder(analyzer);
		sqof.addBuilder("SpanOrTerms", sots);
		queryFactory.addBuilder("SpanOrTerms", sots);
		SpanFirstBuilder sft = new SpanFirstBuilder(sqof);
		sqof.addBuilder("SpanFirst", sft);
		queryFactory.addBuilder("SpanFirst", sft);
		SpanNotBuilder snot = new SpanNotBuilder(sqof);
		sqof.addBuilder("SpanNot", snot);
		queryFactory.addBuilder("SpanNot", snot);
	}

	public Query parse(InputStream xmlStream)
		throws ParserException
	{
		return getQuery(parseXML(xmlStream).getDocumentElement());
	}

	public void addQueryBuilder(String nodeName, QueryBuilder builder)
	{
		queryFactory.addBuilder(nodeName, builder);
	}

	public void addFilterBuilder(String nodeName, FilterBuilder builder)
	{
		filterFactory.addBuilder(nodeName, builder);
	}

	private static Document parseXML(InputStream pXmlFile)
		throws ParserException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try
		{
			db = dbf.newDocumentBuilder();
		}
		catch (Exception se)
		{
			throw new ParserException("XML Parser configuration error", se);
		}
		Document doc = null;
		try
		{
			doc = db.parse(pXmlFile);
		}
		catch (Exception se)
		{
			throw new ParserException((new StringBuilder()).append("Error parsing XML stream:").append(se).toString(), se);
		}
		return doc;
	}

	public Query getQuery(Element e)
		throws ParserException
	{
		return queryFactory.getQuery(e);
	}

}
