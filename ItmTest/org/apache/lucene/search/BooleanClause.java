// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanClause.java

package org.apache.lucene.search;


// Referenced classes of package org.apache.lucene.search:
//			Query

public class BooleanClause
{
	public static class Occur extends Enum
	{

		public static final Occur MUST;
		public static final Occur SHOULD;
		public static final Occur MUST_NOT;
		private static final Occur $VALUES[];

		public static Occur[] values()
		{
			return (Occur[])$VALUES.clone();
		}

		public static Occur valueOf(String name)
		{
			return (Occur)Enum.valueOf(org/apache/lucene/search/BooleanClause$Occur, name);
		}

		static 
		{
			MUST = new Occur("MUST", 0) {

				public String toString()
				{
					return "+";
				}

			};
			SHOULD = new Occur("SHOULD", 1) {

				public String toString()
				{
					return "";
				}

			};
			MUST_NOT = new Occur("MUST_NOT", 2) {

				public String toString()
				{
					return "-";
				}

			};
			$VALUES = (new Occur[] {
				MUST, SHOULD, MUST_NOT
			});
		}

		private Occur(String s, int i)
		{
			super(s, i);
		}

	}


	private Query query;
	private Occur occur;

	public BooleanClause(Query query, Occur occur)
	{
		this.query = query;
		this.occur = occur;
	}

	public Occur getOccur()
	{
		return occur;
	}

	public void setOccur(Occur occur)
	{
		this.occur = occur;
	}

	public Query getQuery()
	{
		return query;
	}

	public void setQuery(Query query)
	{
		this.query = query;
	}

	public boolean isProhibited()
	{
		return Occur.MUST_NOT == occur;
	}

	public boolean isRequired()
	{
		return Occur.MUST == occur;
	}

	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof BooleanClause))
		{
			return false;
		} else
		{
			BooleanClause other = (BooleanClause)o;
			return query.equals(other.query) && occur == other.occur;
		}
	}

	public int hashCode()
	{
		return query.hashCode() ^ (Occur.MUST != occur ? 0 : 1) ^ (Occur.MUST_NOT != occur ? 0 : 2);
	}

	public String toString()
	{
		return (new StringBuilder()).append(occur.toString()).append(query.toString()).toString();
	}
}
