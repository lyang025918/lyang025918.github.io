// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyntaxParser.java

package org.apache.lucene.queryparser.flexible.core.parser;

import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

public interface SyntaxParser
{

	public abstract QueryNode parse(CharSequence charsequence, CharSequence charsequence1)
		throws QueryNodeParseException;
}
