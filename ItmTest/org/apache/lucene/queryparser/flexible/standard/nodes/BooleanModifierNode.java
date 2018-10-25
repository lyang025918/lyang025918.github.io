// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanModifierNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

public class BooleanModifierNode extends ModifierQueryNode
{

	public BooleanModifierNode(QueryNode node, org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier mod)
	{
		super(node, mod);
	}
}
