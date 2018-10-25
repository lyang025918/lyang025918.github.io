// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProximityQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			BooleanQueryNode, QueryNode, FieldQueryNode

public class ProximityQueryNode extends BooleanQueryNode
{
	public static class ProximityType
	{

		int pDistance;
		Type pType;

		public ProximityType(Type type)
		{
			this(type, 0);
		}

		public ProximityType(Type type, int distance)
		{
			pDistance = 0;
			pType = null;
			pType = type;
			pDistance = distance;
		}
	}

	public static abstract class Type extends Enum
	{

		public static final Type PARAGRAPH;
		public static final Type SENTENCE;
		public static final Type NUMBER;
		private static final Type $VALUES[];

		public static Type[] values()
		{
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name)
		{
			return (Type)Enum.valueOf(org/apache/lucene/queryparser/flexible/core/nodes/ProximityQueryNode$Type, name);
		}

		abstract CharSequence toQueryString();

		static 
		{
			PARAGRAPH = new Type("PARAGRAPH", 0) {

				CharSequence toQueryString()
				{
					return "WITHIN PARAGRAPH";
				}

			};
			SENTENCE = new Type("SENTENCE", 1) {

				CharSequence toQueryString()
				{
					return "WITHIN SENTENCE";
				}

			};
			NUMBER = new Type("NUMBER", 2) {

				CharSequence toQueryString()
				{
					return "WITHIN";
				}

			};
			$VALUES = (new Type[] {
				PARAGRAPH, SENTENCE, NUMBER
			});
		}

		private Type(String s, int i)
		{
			super(s, i);
		}

	}


	private Type proximityType;
	private int distance;
	private boolean inorder;
	private CharSequence field;

	public ProximityQueryNode(List clauses, CharSequence field, Type type, int distance, boolean inorder)
	{
		super(clauses);
		proximityType = Type.SENTENCE;
		this.distance = -1;
		this.inorder = false;
		this.field = null;
		setLeaf(false);
		proximityType = type;
		this.inorder = inorder;
		this.field = field;
		if (type == Type.NUMBER)
		{
			if (distance <= 0)
				throw new QueryNodeError(new MessageImpl(QueryParserMessages.PARAMETER_VALUE_NOT_SUPPORTED, new Object[] {
					"distance", Integer.valueOf(distance)
				}));
			this.distance = distance;
		}
		clearFields(clauses, field);
	}

	public ProximityQueryNode(List clauses, CharSequence field, Type type, boolean inorder)
	{
		this(clauses, field, type, -1, inorder);
	}

	private static void clearFields(List nodes, CharSequence field)
	{
		if (nodes == null || nodes.size() == 0)
			return;
		Iterator i$ = nodes.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			QueryNode clause = (QueryNode)i$.next();
			if (clause instanceof FieldQueryNode)
			{
				((FieldQueryNode)clause).toQueryStringIgnoreFields = true;
				((FieldQueryNode)clause).setField(field);
			}
		} while (true);
	}

	public Type getProximityType()
	{
		return proximityType;
	}

	public String toString()
	{
		String distanceSTR = distance != -1 ? (new StringBuilder()).append(" distance='").append(distance).append("'").toString() : "";
		if (getChildren() == null || getChildren().size() == 0)
			return (new StringBuilder()).append("<proximity field='").append(field).append("' inorder='").append(inorder).append("' type='").append(proximityType.toString()).append("'").append(distanceSTR).append("/>").toString();
		StringBuilder sb = new StringBuilder();
		sb.append((new StringBuilder()).append("<proximity field='").append(field).append("' inorder='").append(inorder).append("' type='").append(proximityType.toString()).append("'").append(distanceSTR).append(">").toString());
		QueryNode child;
		for (Iterator i$ = getChildren().iterator(); i$.hasNext(); sb.append(child.toString()))
		{
			child = (QueryNode)i$.next();
			sb.append("\n");
		}

		sb.append("\n</proximity>");
		return sb.toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		String withinSTR = (new StringBuilder()).append(proximityType.toQueryString()).append(distance != -1 ? (new StringBuilder()).append(" ").append(distance).toString() : "").append(inorder ? " INORDER" : "").toString();
		StringBuilder sb = new StringBuilder();
		if (getChildren() != null && getChildren().size() != 0)
		{
			String filler = "";
			for (Iterator i$ = getChildren().iterator(); i$.hasNext();)
			{
				QueryNode child = (QueryNode)i$.next();
				sb.append(filler).append(child.toQueryString(escapeSyntaxParser));
				filler = " ";
			}

		}
		if (isDefaultField(field))
			return (new StringBuilder()).append("( ").append(sb.toString()).append(" ) ").append(withinSTR).toString();
		else
			return (new StringBuilder()).append(field).append(":(( ").append(sb.toString()).append(" ) ").append(withinSTR).append(")").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		ProximityQueryNode clone = (ProximityQueryNode)super.cloneTree();
		clone.proximityType = proximityType;
		clone.distance = distance;
		clone.field = field;
		return clone;
	}

	public int getDistance()
	{
		return distance;
	}

	public CharSequence getField()
	{
		return field;
	}

	public String getFieldAsString()
	{
		if (field == null)
			return null;
		else
			return field.toString();
	}

	public void setField(CharSequence field)
	{
		this.field = field;
	}

	public boolean isInOrder()
	{
		return inorder;
	}
}
