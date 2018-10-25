// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardQueryParser.java

package org.apache.lucene.queryparser.flexible.standard;

import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.QueryParserHelper;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.builders.StandardQueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.standard.config.FuzzyConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.parser.StandardSyntaxParser;
import org.apache.lucene.queryparser.flexible.standard.processors.StandardQueryNodeProcessorPipeline;
import org.apache.lucene.search.MultiTermQuery;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard:
//			CommonQueryParserConfiguration

public class StandardQueryParser extends QueryParserHelper
	implements CommonQueryParserConfiguration
{

	public StandardQueryParser()
	{
		super(new StandardQueryConfigHandler(), new StandardSyntaxParser(), new StandardQueryNodeProcessorPipeline(null), new StandardQueryTreeBuilder());
		setEnablePositionIncrements(true);
	}

	public StandardQueryParser(Analyzer analyzer)
	{
		this();
		setAnalyzer(analyzer);
	}

	public String toString()
	{
		return (new StringBuilder()).append("<StandardQueryParser config=\"").append(getQueryConfigHandler()).append("\"/>").toString();
	}

	public Query parse(String query, String defaultField)
		throws QueryNodeException
	{
		return (Query)super.parse(query, defaultField);
	}

	public org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator getDefaultOperator()
	{
		return (org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DEFAULT_OPERATOR);
	}

	public void setDefaultOperator(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.Operator operator)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DEFAULT_OPERATOR, operator);
	}

	public void setLowercaseExpandedTerms(boolean lowercaseExpandedTerms)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOWERCASE_EXPANDED_TERMS, Boolean.valueOf(lowercaseExpandedTerms));
	}

	public boolean getLowercaseExpandedTerms()
	{
		Boolean lowercaseExpandedTerms = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOWERCASE_EXPANDED_TERMS);
		if (lowercaseExpandedTerms == null)
			return true;
		else
			return lowercaseExpandedTerms.booleanValue();
	}

	public void setAllowLeadingWildcard(boolean allowLeadingWildcard)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ALLOW_LEADING_WILDCARD, Boolean.valueOf(allowLeadingWildcard));
	}

	public void setEnablePositionIncrements(boolean enabled)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ENABLE_POSITION_INCREMENTS, Boolean.valueOf(enabled));
	}

	public boolean getEnablePositionIncrements()
	{
		Boolean enablePositionsIncrements = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ENABLE_POSITION_INCREMENTS);
		if (enablePositionsIncrements == null)
			return false;
		else
			return enablePositionsIncrements.booleanValue();
	}

	public void setMultiTermRewriteMethod(org.apache.lucene.search.MultiTermQuery.RewriteMethod method)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_TERM_REWRITE_METHOD, method);
	}

	public org.apache.lucene.search.MultiTermQuery.RewriteMethod getMultiTermRewriteMethod()
	{
		return (org.apache.lucene.search.MultiTermQuery.RewriteMethod)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_TERM_REWRITE_METHOD);
	}

	public void setMultiFields(CharSequence fields[])
	{
		if (fields == null)
			fields = new CharSequence[0];
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_FIELDS, fields);
	}

	public void getMultiFields(CharSequence fields[])
	{
		getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.MULTI_FIELDS);
	}

	public void setFuzzyPrefixLength(int fuzzyPrefixLength)
	{
		QueryConfigHandler config = getQueryConfigHandler();
		FuzzyConfig fuzzyConfig = (FuzzyConfig)config.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG);
		if (fuzzyConfig == null)
		{
			fuzzyConfig = new FuzzyConfig();
			config.set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG, fuzzyConfig);
		}
		fuzzyConfig.setPrefixLength(fuzzyPrefixLength);
	}

	public void setNumericConfigMap(Map numericConfigMap)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG_MAP, numericConfigMap);
	}

	public Map getNumericConfigMap()
	{
		return (Map)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG_MAP);
	}

	public void setLocale(Locale locale)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOCALE, locale);
	}

	public Locale getLocale()
	{
		return (Locale)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOCALE);
	}

	public void setTimeZone(TimeZone timeZone)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.TIMEZONE, timeZone);
	}

	public TimeZone getTimeZone()
	{
		return (TimeZone)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.TIMEZONE);
	}

	/**
	 * @deprecated Method setDefaultPhraseSlop is deprecated
	 */

	public void setDefaultPhraseSlop(int defaultPhraseSlop)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.PHRASE_SLOP, Integer.valueOf(defaultPhraseSlop));
	}

	public void setPhraseSlop(int defaultPhraseSlop)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.PHRASE_SLOP, Integer.valueOf(defaultPhraseSlop));
	}

	public void setAnalyzer(Analyzer analyzer)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ANALYZER, analyzer);
	}

	public Analyzer getAnalyzer()
	{
		return (Analyzer)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ANALYZER);
	}

	public boolean getAllowLeadingWildcard()
	{
		Boolean allowLeadingWildcard = (Boolean)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.ALLOW_LEADING_WILDCARD);
		if (allowLeadingWildcard == null)
			return false;
		else
			return allowLeadingWildcard.booleanValue();
	}

	public float getFuzzyMinSim()
	{
		FuzzyConfig fuzzyConfig = (FuzzyConfig)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG);
		if (fuzzyConfig == null)
			return 2.0F;
		else
			return fuzzyConfig.getMinSimilarity();
	}

	public int getFuzzyPrefixLength()
	{
		FuzzyConfig fuzzyConfig = (FuzzyConfig)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG);
		if (fuzzyConfig == null)
			return 0;
		else
			return fuzzyConfig.getPrefixLength();
	}

	public int getPhraseSlop()
	{
		Integer phraseSlop = (Integer)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.PHRASE_SLOP);
		if (phraseSlop == null)
			return 0;
		else
			return phraseSlop.intValue();
	}

	public void setFuzzyMinSim(float fuzzyMinSim)
	{
		QueryConfigHandler config = getQueryConfigHandler();
		FuzzyConfig fuzzyConfig = (FuzzyConfig)config.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG);
		if (fuzzyConfig == null)
		{
			fuzzyConfig = new FuzzyConfig();
			config.set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FUZZY_CONFIG, fuzzyConfig);
		}
		fuzzyConfig.setMinSimilarity(fuzzyMinSim);
	}

	public void setFieldsBoost(Map boosts)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FIELD_BOOST_MAP, boosts);
	}

	public Map getFieldsBoost()
	{
		return (Map)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FIELD_BOOST_MAP);
	}

	public void setDateResolution(org.apache.lucene.document.DateTools.Resolution dateResolution)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DATE_RESOLUTION, dateResolution);
	}

	public org.apache.lucene.document.DateTools.Resolution getDateResolution()
	{
		return (org.apache.lucene.document.DateTools.Resolution)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DATE_RESOLUTION);
	}

	/**
	 * @deprecated Method setDateResolution is deprecated
	 */

	public void setDateResolution(Map dateRes)
	{
		setDateResolutionMap(dateRes);
	}

	public Map getDateResolutionMap()
	{
		return (Map)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FIELD_DATE_RESOLUTION_MAP);
	}

	public void setDateResolutionMap(Map dateRes)
	{
		getQueryConfigHandler().set(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.FIELD_DATE_RESOLUTION_MAP, dateRes);
	}

	public volatile Object parse(String x0, String x1)
		throws QueryNodeException
	{
		return parse(x0, x1);
	}
}
