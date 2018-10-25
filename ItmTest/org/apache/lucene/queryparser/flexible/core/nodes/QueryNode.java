// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.List;
import java.util.Map;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

public interface QueryNode
{

	public abstract CharSequence toQueryString(EscapeQuerySyntax escapequerysyntax);

	public abstract String toString();

	public abstract List getChildren();

	public abstract boolean isLeaf();

	public abstract boolean containsTag(String s);

	public abstract Object getTag(String s);

	public abstract QueryNode getParent();

	public abstract QueryNode cloneTree()
		throws CloneNotSupportedException;

	public abstract void add(QueryNode querynode);

	public abstract void add(List list);

	public abstract void set(List list);

	public abstract void setTag(String s, Object obj);

	public abstract void unsetTag(String s);

	public abstract Map getTagMap();
}
