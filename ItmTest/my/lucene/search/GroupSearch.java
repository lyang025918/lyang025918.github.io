// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupSearch.java

package my.lucene.search;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package my.lucene.search:
//			SearchUtils

public class GroupSearch extends SearchUtils
{
	public class GroupField
	{
		class ValueComparator
			implements Comparator
		{

			final GroupField this$1;

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
				this.this$1 = GroupField.this;
				super();
			}
		}


		private String name;
		private List values;
		private Map countMap;
		final GroupSearch this$0;

		public Map getCountMap()
		{
			return countMap;
		}

		public List getValues()
		{
			Collections.sort(values, new ValueComparator());
			return values;
		}

		public void addValue(String value)
		{
			if (value == null || "".equals(value))
				return;
			String temp[] = value.split(" ");
			String as[] = temp;
			int i = as.length;
			for (int j = 0; j < i; j++)
			{
				String str = as[j];
				if (countMap.get(str) == null)
				{
					countMap.put(str, Integer.valueOf(1));
					values.add(str);
				} else
				{
					countMap.put(str, Integer.valueOf(((Integer)countMap.get(str)).intValue() + 1));
				}
			}

		}


		public GroupField()
		{
			this.this$0 = GroupSearch.this;
			super();
			values = new ArrayList();
			countMap = new HashMap();
		}
	}

	public class GroupCollector extends Collector
	{

		org.apache.lucene.search.FieldCache.DocTerms docTerms;
		private GroupField groupField;
		private int docBase;
		final GroupSearch this$0;

		public GroupField getGroupField()
		{
			return groupField;
		}

		public void setScorer(Scorer scorer1)
			throws IOException
		{
		}

		public void setNextReader(AtomicReaderContext context)
			throws IOException
		{
			org.apache.lucene.index.AtomicReader ar = context.reader();
			docTerms = FieldCache.DEFAULT.getTerms(ar, "phone");
			docBase = context.docBase;
		}

		public void collect(int doc)
			throws IOException
		{
			int docId = doc + docBase;
			groupField.addValue(docTerms.getTerm(doc, new BytesRef()).utf8ToString());
		}

		public boolean acceptsDocsOutOfOrder()
		{
			return true;
		}

		public GroupCollector()
		{
			this.this$0 = GroupSearch.this;
			super();
			docTerms = null;
			groupField = new GroupField();
		}
	}


	public GroupSearch()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		String index = "e:\\study\\source\\java\\lucene\\index\\10w";
		String queryString = "ฤ๚าช";
		if (args.length >= 2)
		{
			index = args[0];
			queryString = args[1];
		}
		GroupSearch inst = new GroupSearch();
		inst.open(index);
		inst.doSearch(queryString);
		inst.close();
	}

	public void doSearch(String queryString)
		throws Exception
	{
		long startTime = System.currentTimeMillis();
		GroupCollector myCollector = new GroupCollector();
		searcher.search(new TermQuery(new Term("content", queryString)), myCollector);
		GroupField groupField = myCollector.getGroupField();
		List classifications = groupField.getValues();
		System.out.println((new StringBuilder()).append("class count=").append(classifications.size()).toString());
		String cf;
		for (Iterator iterator = classifications.iterator(); iterator.hasNext();)
			cf = (String)iterator.next();

		long endTime = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append("time=").append(endTime - startTime).toString());
	}
}
