// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardBooleanQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.nodes.BooleanQueryNode;

public class StandardBooleanQueryNode extends BooleanQueryNode
{

	private boolean disableCoord;

	public StandardBooleanQueryNode(List clauses, boolean disableCoord)
	{
		super(clauses);
		this.disableCoord = disableCoord;
	}

	public boolean isDisableCoord()
	{
		return disableCoord;
	}
}
