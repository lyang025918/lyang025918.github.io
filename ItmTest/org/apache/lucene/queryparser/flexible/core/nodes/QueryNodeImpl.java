// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeImpl.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.messages.NLS;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNode

public abstract class QueryNodeImpl
	implements QueryNode, Cloneable
{

	public static final String PLAINTEXT_FIELD_NAME = "_plain";
	private boolean isLeaf;
	private Hashtable tags;
	private List clauses;
	private QueryNode parent;
	protected boolean toQueryStringIgnoreFields;

	public QueryNodeImpl()
	{
		isLeaf = true;
		tags = new Hashtable();
		clauses = null;
		parent = null;
		toQueryStringIgnoreFields = false;
	}

	protected void allocate()
	{
		if (clauses == null)
			clauses = new ArrayList();
		else
			clauses.clear();
	}

	public final void add(QueryNode child)
	{
		if (isLeaf() || clauses == null || child == null)
		{
			throw new IllegalArgumentException(NLS.getLocalizedMessage(QueryParserMessages.NODE_ACTION_NOT_SUPPORTED));
		} else
		{
			clauses.add(child);
			((QueryNodeImpl)child).setParent(this);
			return;
		}
	}

	public final void add(List children)
	{
		if (isLeaf() || clauses == null)
			throw new IllegalArgumentException(NLS.getLocalizedMessage(QueryParserMessages.NODE_ACTION_NOT_SUPPORTED));
		QueryNode child;
		for (Iterator i$ = children.iterator(); i$.hasNext(); add(child))
			child = (QueryNode)i$.next();

	}

	public boolean isLeaf()
	{
		return isLeaf;
	}

	public final void set(List children)
	{
		if (isLeaf() || clauses == null)
		{
			ResourceBundle bundle = ResourceBundle.getBundle("org.apache.lucene.queryParser.messages.QueryParserMessages");
			String message = bundle.getObject("Q0008E.NODE_ACTION_NOT_SUPPORTED").toString();
			throw new IllegalArgumentException(message);
		}
		QueryNode child;
		for (Iterator i$ = children.iterator(); i$.hasNext(); ((QueryNodeImpl)child).setParent(null))
			child = (QueryNode)i$.next();

		allocate();
		QueryNode child;
		for (Iterator i$ = children.iterator(); i$.hasNext(); add(child))
			child = (QueryNode)i$.next();

	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		QueryNodeImpl clone = (QueryNodeImpl)super.clone();
		clone.isLeaf = isLeaf;
		clone.tags = new Hashtable();
		if (clauses != null)
		{
			List localClauses = new ArrayList();
			QueryNode clause;
			for (Iterator i$ = clauses.iterator(); i$.hasNext(); localClauses.add(clause.cloneTree()))
				clause = (QueryNode)i$.next();

			clone.clauses = localClauses;
		}
		return clone;
	}

	public QueryNode clone()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}

	protected void setLeaf(boolean isLeaf)
	{
		this.isLeaf = isLeaf;
	}

	public final List getChildren()
	{
		if (isLeaf() || clauses == null)
			return null;
		else
			return clauses;
	}

	public void setTag(String tagName, Object value)
	{
		tags.put(tagName.toLowerCase(Locale.ROOT), value);
	}

	public void unsetTag(String tagName)
	{
		tags.remove(tagName.toLowerCase(Locale.ROOT));
	}

	public boolean containsTag(String tagName)
	{
		return tags.containsKey(tagName.toLowerCase(Locale.ROOT));
	}

	public Object getTag(String tagName)
	{
		return tags.get(tagName.toLowerCase(Locale.ROOT));
	}

	private void setParent(QueryNode parent)
	{
		this.parent = parent;
	}

	public QueryNode getParent()
	{
		return parent;
	}

	protected boolean isRoot()
	{
		return getParent() == null;
	}

	protected boolean isDefaultField(CharSequence fld)
	{
		if (toQueryStringIgnoreFields)
			return true;
		if (fld == null)
			return true;
		return "_plain".equals(StringUtils.toString(fld));
	}

	public String toString()
	{
		return super.toString();
	}

	public Map getTagMap()
	{
		return (Map)tags.clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
