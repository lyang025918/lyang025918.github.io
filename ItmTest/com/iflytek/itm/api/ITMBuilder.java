// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMBuilder.java

package com.iflytek.itm.api;

import java.util.*;

public interface ITMBuilder
{
	public static class ITMDocument
	{

		private List fields;
		private int primaryIndex;

		public void add(ITMField field)
		{
			if (field.isPrimaryKey)
				primaryIndex = fields.size();
			fields.add(field);
		}

		public ITMField get(String name)
		{
			for (Iterator iterator = fields.iterator(); iterator.hasNext();)
			{
				ITMField field = (ITMField)iterator.next();
				if (field.name.equals(name))
					return field;
			}

			return null;
		}

		public void del(String name)
		{
			for (int i = 0; i < fields.size(); i++)
			{
				ITMField field = (ITMField)fields.get(i);
				if (field.name.equals(name))
					fields.remove(i);
			}

		}

		public List getFields()
		{
			return fields;
		}

		public int getPrimaryIndex()
		{
			return primaryIndex;
		}

		public ITMField getPrimary()
		{
			if (primaryIndex != -1)
				return (ITMField)fields.get(primaryIndex);
			else
				return null;
		}

		public ITMDocument()
		{
			fields = new ArrayList();
			primaryIndex = -1;
		}
	}

	public static class ITMField
	{

		public String name;
		public String type;
		public String value;
		public String analyzer;
		public boolean isPrimaryKey;

		public String toString()
		{
			return (new StringBuilder()).append("name=").append(name).append(";type=").append(type).append(";analyzer=").append(analyzer).append(";isPrimaryKey=").append(isPrimaryKey).toString();
		}

		public ITMField(String name, String type, String value, String analyzer, boolean isPrimaryKey)
		{
			this.name = name != null ? name : "null";
			this.type = type != null ? type : "null";
			this.value = value != null ? value : "null";
			this.analyzer = analyzer != null ? analyzer : "null";
			this.isPrimaryKey = isPrimaryKey;
		}
	}


	public abstract ITMDocument read();

	public abstract void event(String s, int i, String s1);
}
