// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParserMessages.java

package org.apache.lucene.queryparser.flexible.core.messages;

import org.apache.lucene.queryparser.flexible.messages.NLS;

public class QueryParserMessages extends NLS
{

	private static final String BUNDLE_NAME = org/apache/lucene/queryparser/flexible/core/messages/QueryParserMessages.getName();
	public static String INVALID_SYNTAX;
	public static String INVALID_SYNTAX_CANNOT_PARSE;
	public static String INVALID_SYNTAX_FUZZY_LIMITS;
	public static String INVALID_SYNTAX_FUZZY_EDITS;
	public static String INVALID_SYNTAX_ESCAPE_UNICODE_TRUNCATION;
	public static String INVALID_SYNTAX_ESCAPE_CHARACTER;
	public static String INVALID_SYNTAX_ESCAPE_NONE_HEX_UNICODE;
	public static String NODE_ACTION_NOT_SUPPORTED;
	public static String PARAMETER_VALUE_NOT_SUPPORTED;
	public static String LUCENE_QUERY_CONVERSION_ERROR;
	public static String EMPTY_MESSAGE;
	public static String WILDCARD_NOT_SUPPORTED;
	public static String TOO_MANY_BOOLEAN_CLAUSES;
	public static String LEADING_WILDCARD_NOT_ALLOWED;
	public static String COULD_NOT_PARSE_NUMBER;
	public static String NUMBER_CLASS_NOT_SUPPORTED_BY_NUMERIC_RANGE_QUERY;
	public static String UNSUPPORTED_NUMERIC_DATA_TYPE;
	public static String NUMERIC_CANNOT_BE_EMPTY;

	private QueryParserMessages()
	{
	}

	static 
	{
		NLS.initializeMessages(BUNDLE_NAME, org/apache/lucene/queryparser/flexible/core/messages/QueryParserMessages);
	}
}
