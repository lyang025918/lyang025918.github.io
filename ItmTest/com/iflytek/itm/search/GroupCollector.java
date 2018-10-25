// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupCollector.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.ITMSearchContext;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

// Referenced classes of package com.iflytek.itm.search:
//			ITMSearchResultImpl

public abstract class GroupCollector extends Collector
{
	class ScopeValues
	{

		public Set paramGroups;
		final GroupCollector this$0;

		public String strValue(String fieldValue)
		{
			if (paramGroups.contains(fieldValue))
				return fieldValue;
			else
				return null;
		}

		public String intValue(int num)
		{
			for (Iterator it = paramGroups.iterator(); it.hasNext();)
			{
				String tmp = (String)it.next();
				int pos = tmp.indexOf("-");
				if (-1 == pos)
				{
					if (num == Integer.valueOf(tmp).intValue())
						return tmp;
				} else
				{
					String minstr = tmp.substring(0, pos);
					String maxstr = tmp.substring(pos + 1, tmp.length());
					int min = 0x80000000;
					int max = 0x7fffffff;
					if (!minstr.equals("*"))
						min = Integer.valueOf(minstr).intValue();
					if (!maxstr.equals("*"))
						max = Integer.valueOf(maxstr).intValue();
					if (num >= min && num <= max)
						return tmp;
				}
			}

			return null;
		}

		public String longValue(long num)
		{
			for (Iterator it = paramGroups.iterator(); it.hasNext();)
			{
				String tmp = (String)it.next();
				int pos = tmp.indexOf("-");
				if (-1 == pos)
				{
					if (num == Long.valueOf(tmp).longValue())
						return tmp;
				} else
				{
					String minstr = tmp.substring(0, pos);
					String maxstr = tmp.substring(pos + 1, tmp.length());
					long min = 0x8000000000000000L;
					long max = 0x7fffffffffffffffL;
					if (!minstr.equals("*"))
						min = Long.valueOf(minstr).longValue();
					if (!maxstr.equals("*"))
						max = Long.valueOf(maxstr).longValue();
					if (num >= min && num <= max)
						return tmp;
				}
			}

			return null;
		}

		public ScopeValues(String groups[])
		{
			this.this$0 = GroupCollector.this;
			super();
			paramGroups = new HashSet();
			for (int i = 0; i < groups.length; i++)
				if (!paramGroups.contains(groups[i].trim()))
					paramGroups.add(groups[i]);

		}
	}

	class ValueComparator
		implements Comparator
	{

		final GroupCollector this$0;

		public int compare(String value0, String value1)
		{
			if (((Integer)countMap.get(value0)).intValue() > ((Integer)countMap.get(value1)).intValue())
				return -1;
			return ((Integer)countMap.get(value0)).intValue() >= ((Integer)countMap.get(value1)).intValue() ? 0 : 1;
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((String)obj, (String)obj1);
		}

		ValueComparator()
		{
			this.this$0 = GroupCollector.this;
			super();
		}
	}


	public String name;
	public int docBase;
	public List values;
	public Map countMap;
	public int totalDocCount;
	public ScopeValues scopeValues;

	public GroupCollector(String name, String groups[])
	{
		this.name = null;
		values = new ArrayList();
		countMap = new HashMap();
		totalDocCount = 0;
		scopeValues = null;
		this.name = name;
		totalDocCount = 0;
		if (groups != null)
			scopeValues = new ScopeValues(groups);
	}

	public int getTopGroups(int topn, ITMSearchResultImpl.ITMTopGroup topGroups)
	{
		Collections.sort(values, new ValueComparator());
		int len = values.size() <= topn ? values.size() : topn;
		topGroups.groups = new com.iflytek.itm.api.ITMSearchContext.ITMGroup[len];
		for (int i = 0; i < len; i++)
		{
			com.iflytek.itm.api.ITMSearchContext.ITMGroup group = new com.iflytek.itm.api.ITMSearchContext.ITMGroup();
			group.value = (String)values.get(i);
			group.docCount = ((Integer)countMap.get(group.value)).intValue();
			topGroups.groups[i] = group;
		}

		topGroups.docTotalHits = totalDocCount;
		topGroups.groupTotalHits = values.size();
		return 0;
	}

	public void addValue(String value)
	{
		if (countMap.get(value) == null)
		{
			countMap.put(value, Integer.valueOf(1));
			values.add(value);
		} else
		{
			countMap.put(value, Integer.valueOf(((Integer)countMap.get(value)).intValue() + 1));
		}
	}

	public void setScorer(Scorer scorer1)
		throws IOException
	{
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return true;
	}
}
