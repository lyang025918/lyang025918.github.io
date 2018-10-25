// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.core.processors;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

public interface QueryNodeProcessor
{

	public abstract QueryNode process(QueryNode querynode)
		throws QueryNodeException;

	public abstract void setQueryConfigHandler(QueryConfigHandler queryconfighandler);

	public abstract QueryConfigHandler getQueryConfigHandler();
}
