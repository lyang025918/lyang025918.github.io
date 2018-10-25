// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			FieldQueryNode, QueryNode

public class FuzzyQueryNode extends FieldQueryNode
{

	private float similarity;
	private int prefixLength;

	public FuzzyQueryNode(CharSequence field, CharSequence term, float minSimilarity, int begin, int end)
	{
		super(field, term, begin, end);
		similarity = minSimilarity;
		setLeaf(true);
	}

	public void setPrefixLength(int prefixLength)
	{
		this.prefixLength = prefixLength;
	}

	public int getPrefixLength()
	{
		return prefixLength;
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		if (isDefaultField(field))
			return (new StringBuilder()).append(getTermEscaped(escaper)).append("~").append(similarity).toString();
		else
			return (new StringBuilder()).append(field).append(":").append(getTermEscaped(escaper)).append("~").append(similarity).toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("<fuzzy field='").append(field).append("' similarity='").append(similarity).append("' term='").append(text).append("'/>").toString();
	}

	public void setSimilarity(float similarity)
	{
		this.similarity = similarity;
	}

	public FuzzyQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		FuzzyQueryNode clone = (FuzzyQueryNode)super.cloneTree();
		clone.similarity = similarity;
		return clone;
	}

	public float getSimilarity()
	{
		return similarity;
	}

	public volatile FieldQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}
}
