// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonQueryParserConfiguration.java

package org.apache.lucene.queryparser.flexible.standard;

import java.util.Locale;
import java.util.TimeZone;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.MultiTermQuery;

public interface CommonQueryParserConfiguration
{

	public abstract void setLowercaseExpandedTerms(boolean flag);

	public abstract boolean getLowercaseExpandedTerms();

	public abstract void setAllowLeadingWildcard(boolean flag);

	public abstract void setEnablePositionIncrements(boolean flag);

	public abstract boolean getEnablePositionIncrements();

	public abstract void setMultiTermRewriteMethod(org.apache.lucene.search.MultiTermQuery.RewriteMethod rewritemethod);

	public abstract org.apache.lucene.search.MultiTermQuery.RewriteMethod getMultiTermRewriteMethod();

	public abstract void setFuzzyPrefixLength(int i);

	public abstract void setLocale(Locale locale);

	public abstract Locale getLocale();

	public abstract void setTimeZone(TimeZone timezone);

	public abstract TimeZone getTimeZone();

	public abstract void setPhraseSlop(int i);

	public abstract Analyzer getAnalyzer();

	public abstract boolean getAllowLeadingWildcard();

	public abstract float getFuzzyMinSim();

	public abstract int getFuzzyPrefixLength();

	public abstract int getPhraseSlop();

	public abstract void setFuzzyMinSim(float f);

	public abstract void setDateResolution(org.apache.lucene.document.DateTools.Resolution resolution);
}
