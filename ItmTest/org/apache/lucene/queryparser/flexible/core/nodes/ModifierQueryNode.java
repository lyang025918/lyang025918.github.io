// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ModifierQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode, BooleanQueryNode

public class ModifierQueryNode extends QueryNodeImpl
{
	public static final class Modifier extends Enum
	{

		public static final Modifier MOD_NONE;
		public static final Modifier MOD_NOT;
		public static final Modifier MOD_REQ;
		private static final Modifier $VALUES[];

		public static Modifier[] values()
		{
			return (Modifier[])$VALUES.clone();
		}

		public static Modifier valueOf(String name)
		{
			return (Modifier)Enum.valueOf(org/apache/lucene/queryparser/flexible/core/nodes/ModifierQueryNode$Modifier, name);
		}

		public String toString()
		{
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$queryparser$flexible$core$nodes$ModifierQueryNode$Modifier[];

				static 
				{
					$SwitchMap$org$apache$lucene$queryparser$flexible$core$nodes$ModifierQueryNode$Modifier = new int[Modifier.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$queryparser$flexible$core$nodes$ModifierQueryNode$Modifier[Modifier.MOD_NONE.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$queryparser$flexible$core$nodes$ModifierQueryNode$Modifier[Modifier.MOD_NOT.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$queryparser$flexible$core$nodes$ModifierQueryNode$Modifier[Modifier.MOD_REQ.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier[ordinal()])
			{
			case 1: // '\001'
				return "MOD_NONE";

			case 2: // '\002'
				return "MOD_NOT";

			case 3: // '\003'
				return "MOD_REQ";
			}
			return "MOD_DEFAULT";
		}

		public String toDigitString()
		{
			switch (1..SwitchMap.org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier[ordinal()])
			{
			case 1: // '\001'
				return "";

			case 2: // '\002'
				return "-";

			case 3: // '\003'
				return "+";
			}
			return "";
		}

		public String toLargeString()
		{
			switch (1..SwitchMap.org.apache.lucene.queryparser.flexible.core.nodes.ModifierQueryNode.Modifier[ordinal()])
			{
			case 1: // '\001'
				return "";

			case 2: // '\002'
				return "NOT ";

			case 3: // '\003'
				return "+";
			}
			return "";
		}

		static 
		{
			MOD_NONE = new Modifier("MOD_NONE", 0);
			MOD_NOT = new Modifier("MOD_NOT", 1);
			MOD_REQ = new Modifier("MOD_REQ", 2);
			$VALUES = (new Modifier[] {
				MOD_NONE, MOD_NOT, MOD_REQ
			});
		}

		private Modifier(String s, int i)
		{
			super(s, i);
		}
	}


	private Modifier modifier;

	public ModifierQueryNode(QueryNode query, Modifier mod)
	{
		modifier = Modifier.MOD_NONE;
		if (query == null)
		{
			throw new QueryNodeError(new MessageImpl(QueryParserMessages.PARAMETER_VALUE_NOT_SUPPORTED, new Object[] {
				"query", "null"
			}));
		} else
		{
			allocate();
			setLeaf(false);
			add(query);
			modifier = mod;
			return;
		}
	}

	public QueryNode getChild()
	{
		return (QueryNode)getChildren().get(0);
	}

	public Modifier getModifier()
	{
		return modifier;
	}

	public String toString()
	{
		return (new StringBuilder()).append("<modifier operation='").append(modifier.toString()).append("'>").append("\n").append(getChild().toString()).append("\n</modifier>").toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChild() == null)
			return "";
		String leftParenthensis = "";
		String rightParenthensis = "";
		if (getChild() != null && (getChild() instanceof ModifierQueryNode))
		{
			leftParenthensis = "(";
			rightParenthensis = ")";
		}
		if (getChild() instanceof BooleanQueryNode)
			return (new StringBuilder()).append(modifier.toLargeString()).append(leftParenthensis).append(getChild().toQueryString(escapeSyntaxParser)).append(rightParenthensis).toString();
		else
			return (new StringBuilder()).append(modifier.toDigitString()).append(leftParenthensis).append(getChild().toQueryString(escapeSyntaxParser)).append(rightParenthensis).toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		ModifierQueryNode clone = (ModifierQueryNode)super.cloneTree();
		clone.modifier = modifier;
		return clone;
	}

	public void setChild(QueryNode child)
	{
		List list = new ArrayList();
		list.add(child);
		set(list);
	}
}
