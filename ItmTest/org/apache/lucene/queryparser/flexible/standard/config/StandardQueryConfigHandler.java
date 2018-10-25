// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardQueryConfigHandler.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.util.*;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryparser.flexible.core.config.ConfigurationKey;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.search.MultiTermQuery;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.config:
//			FieldBoostMapFCListener, FieldDateResolutionFCListener, NumericFieldConfigListener, FuzzyConfig

public class StandardQueryConfigHandler extends QueryConfigHandler
{
	public static final class Operator extends Enum
	{

		public static final Operator AND;
		public static final Operator OR;
		private static final Operator $VALUES[];

		public static Operator[] values()
		{
			return (Operator[])$VALUES.clone();
		}

		public static Operator valueOf(String name)
		{
			return (Operator)Enum.valueOf(org/apache/lucene/queryparser/flexible/standard/config/StandardQueryConfigHandler$Operator, name);
		}

		static 
		{
			AND = new Operator("AND", 0);
			OR = new Operator("OR", 1);
			$VALUES = (new Operator[] {
				AND, OR
			});
		}

		private Operator(String s, int i)
		{
			super(s, i);
		}
	}

	public static final class ConfigurationKeys
	{

		public static final ConfigurationKey ENABLE_POSITION_INCREMENTS = ConfigurationKey.newInstance();
		public static final ConfigurationKey LOWERCASE_EXPANDED_TERMS = ConfigurationKey.newInstance();
		public static final ConfigurationKey ALLOW_LEADING_WILDCARD = ConfigurationKey.newInstance();
		public static final ConfigurationKey ANALYZER = ConfigurationKey.newInstance();
		public static final ConfigurationKey DEFAULT_OPERATOR = ConfigurationKey.newInstance();
		public static final ConfigurationKey PHRASE_SLOP = ConfigurationKey.newInstance();
		public static final ConfigurationKey LOCALE = ConfigurationKey.newInstance();
		public static final ConfigurationKey TIMEZONE = ConfigurationKey.newInstance();
		public static final ConfigurationKey MULTI_TERM_REWRITE_METHOD = ConfigurationKey.newInstance();
		public static final ConfigurationKey MULTI_FIELDS = ConfigurationKey.newInstance();
		public static final ConfigurationKey FIELD_BOOST_MAP = ConfigurationKey.newInstance();
		public static final ConfigurationKey FIELD_DATE_RESOLUTION_MAP = ConfigurationKey.newInstance();
		public static final ConfigurationKey FUZZY_CONFIG = ConfigurationKey.newInstance();
		public static final ConfigurationKey DATE_RESOLUTION = ConfigurationKey.newInstance();
		public static final ConfigurationKey BOOST = ConfigurationKey.newInstance();
		public static final ConfigurationKey NUMERIC_CONFIG = ConfigurationKey.newInstance();
		public static final ConfigurationKey NUMERIC_CONFIG_MAP = ConfigurationKey.newInstance();


		public ConfigurationKeys()
		{
		}
	}


	public StandardQueryConfigHandler()
	{
		addFieldConfigListener(new FieldBoostMapFCListener(this));
		addFieldConfigListener(new FieldDateResolutionFCListener(this));
		addFieldConfigListener(new NumericFieldConfigListener(this));
		set(ConfigurationKeys.ALLOW_LEADING_WILDCARD, Boolean.valueOf(false));
		set(ConfigurationKeys.ANALYZER, null);
		set(ConfigurationKeys.DEFAULT_OPERATOR, Operator.OR);
		set(ConfigurationKeys.PHRASE_SLOP, Integer.valueOf(0));
		set(ConfigurationKeys.LOWERCASE_EXPANDED_TERMS, Boolean.valueOf(true));
		set(ConfigurationKeys.ENABLE_POSITION_INCREMENTS, Boolean.valueOf(false));
		set(ConfigurationKeys.FIELD_BOOST_MAP, new LinkedHashMap());
		set(ConfigurationKeys.FUZZY_CONFIG, new FuzzyConfig());
		set(ConfigurationKeys.LOCALE, Locale.getDefault());
		set(ConfigurationKeys.MULTI_TERM_REWRITE_METHOD, MultiTermQuery.CONSTANT_SCORE_AUTO_REWRITE_DEFAULT);
		set(ConfigurationKeys.FIELD_DATE_RESOLUTION_MAP, new HashMap());
	}
}
