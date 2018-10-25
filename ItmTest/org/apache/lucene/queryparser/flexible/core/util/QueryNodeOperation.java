// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeOperation.java

package org.apache.lucene.queryparser.flexible.core.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.nodes.AndQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;

public final class QueryNodeOperation
{
	private static final class ANDOperation extends Enum
	{

		public static final ANDOperation BOTH;
		public static final ANDOperation Q1;
		public static final ANDOperation Q2;
		public static final ANDOperation NONE;
		private static final ANDOperation $VALUES[];

		public static ANDOperation[] values()
		{
			return (ANDOperation[])$VALUES.clone();
		}

		public static ANDOperation valueOf(String name)
		{
			return (ANDOperation)Enum.valueOf(org/apache/lucene/queryparser/flexible/core/util/QueryNodeOperation$ANDOperation, name);
		}

		static 
		{
			BOTH = new ANDOperation("BOTH", 0);
			Q1 = new ANDOperation("Q1", 1);
			Q2 = new ANDOperation("Q2", 2);
			NONE = new ANDOperation("NONE", 3);
			$VALUES = (new ANDOperation[] {
				BOTH, Q1, Q2, NONE
			});
		}

		private ANDOperation(String s, int i)
		{
			super(s, i);
		}
	}


	private QueryNodeOperation()
	{
	}

	public static final QueryNode logicalAnd(QueryNode q1, QueryNode q2)
	{
		ANDOperation op;
		if (q1 == null)
			return q2;
		if (q2 == null)
			return q1;
		op = null;
		if ((q1 instanceof AndQueryNode) && (q2 instanceof AndQueryNode))
			op = ANDOperation.BOTH;
		else
		if (q1 instanceof AndQueryNode)
			op = ANDOperation.Q1;
		else
		if (q1 instanceof AndQueryNode)
			op = ANDOperation.Q2;
		else
			op = ANDOperation.NONE;
		QueryNode result = null;
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation[];

			static 
			{
				$SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation = new int[ANDOperation.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation[ANDOperation.NONE.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation[ANDOperation.Q1.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation[ANDOperation.Q2.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$queryparser$flexible$core$util$QueryNodeOperation$ANDOperation[ANDOperation.BOTH.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		1..SwitchMap.org.apache.lucene.queryparser.flexible.core.util.QueryNodeOperation.ANDOperation[op.ordinal()];
		JVM INSTR tableswitch 1 4: default 225
	//	               1 108
	//	               2 157
	//	               3 178
	//	               4 199;
		   goto _L1 _L2 _L3 _L4 _L5
_L2:
		List children = new ArrayList();
		children.add(q1.cloneTree());
		children.add(q2.cloneTree());
		result = new AndQueryNode(children);
		return result;
_L3:
		result = q1.cloneTree();
		result.add(q2.cloneTree());
		return result;
_L4:
		result = q2.cloneTree();
		result.add(q1.cloneTree());
		return result;
_L5:
		result = q1.cloneTree();
		result.add(q2.cloneTree().getChildren());
		return result;
		CloneNotSupportedException e;
		e;
		throw new QueryNodeError(e);
_L1:
		return null;
	}
}
