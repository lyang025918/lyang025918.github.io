// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PathQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class PathQueryNode extends QueryNodeImpl
{
	public static class QueryText
		implements Cloneable
	{

		CharSequence value;
		int begin;
		int end;

		public QueryText clone()
			throws CloneNotSupportedException
		{
			QueryText clone = (QueryText)super.clone();
			clone.value = value;
			clone.begin = begin;
			clone.end = end;
			return clone;
		}

		public CharSequence getValue()
		{
			return value;
		}

		public int getBegin()
		{
			return begin;
		}

		public int getEnd()
		{
			return end;
		}

		public String toString()
		{
			return (new StringBuilder()).append(value).append(", ").append(begin).append(", ").append(end).toString();
		}

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}

		public QueryText(CharSequence value, int begin, int end)
		{
			this.value = null;
			this.value = value;
			this.begin = begin;
			this.end = end;
		}
	}


	private List values;

	public PathQueryNode(List pathElements)
	{
		values = null;
		values = pathElements;
		if (pathElements.size() <= 1)
			throw new RuntimeException("PathQuerynode requires more 2 or more path elements.");
		else
			return;
	}

	public List getPathElements()
	{
		return values;
	}

	public void setPathElements(List elements)
	{
		values = elements;
	}

	public QueryText getPathElement(int index)
	{
		return (QueryText)values.get(index);
	}

	public CharSequence getFirstPathElement()
	{
		return ((QueryText)values.get(0)).value;
	}

	public List getPathElements(int startIndex)
	{
		List rValues = new ArrayList();
		for (int i = startIndex; i < values.size(); i++)
			try
			{
				rValues.add(((QueryText)values.get(i)).clone());
			}
			catch (CloneNotSupportedException e) { }

		return rValues;
	}

	private CharSequence getPathString()
	{
		StringBuilder path = new StringBuilder();
		QueryText pathelement;
		for (Iterator i$ = values.iterator(); i$.hasNext(); path.append("/").append(pathelement.value))
			pathelement = (QueryText)i$.next();

		return path.toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		StringBuilder path = new StringBuilder();
		path.append("/").append(getFirstPathElement());
		CharSequence value;
		for (Iterator i$ = getPathElements(1).iterator(); i$.hasNext(); path.append("/\"").append(value).append("\""))
		{
			QueryText pathelement = (QueryText)i$.next();
			value = escaper.escape(pathelement.value, Locale.getDefault(), org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax.Type.STRING);
		}

		return path.toString();
	}

	public String toString()
	{
		QueryText text = (QueryText)values.get(0);
		return (new StringBuilder()).append("<path start='").append(text.begin).append("' end='").append(text.end).append("' path='").append(getPathString()).append("'/>").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		PathQueryNode clone = (PathQueryNode)super.cloneTree();
		if (values != null)
		{
			List localValues = new ArrayList();
			QueryText value;
			for (Iterator i$ = values.iterator(); i$.hasNext(); localValues.add(value.clone()))
				value = (QueryText)i$.next();

			clone.values = localValues;
		}
		return clone;
	}
}
