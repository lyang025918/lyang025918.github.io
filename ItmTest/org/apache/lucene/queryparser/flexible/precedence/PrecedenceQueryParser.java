// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrecedenceQueryParser.java

package org.apache.lucene.queryparser.flexible.precedence;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.flexible.precedence.processors.PrecedenceQueryNodeProcessorPipeline;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;

public class PrecedenceQueryParser extends StandardQueryParser
{

	public PrecedenceQueryParser()
	{
		setQueryNodeProcessor(new PrecedenceQueryNodeProcessorPipeline(getQueryConfigHandler()));
	}

	public PrecedenceQueryParser(Analyzer analyer)
	{
		super(analyer);
		setQueryNodeProcessor(new PrecedenceQueryNodeProcessorPipeline(getQueryConfigHandler()));
	}
}
