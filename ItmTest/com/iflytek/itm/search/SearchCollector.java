// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearchCollector.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.util.ITMUtils;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.NumericUtils;

// Referenced classes of package com.iflytek.itm.search:
//			ITMSearchWrapper, MyQueryParser

public class SearchCollector extends Collector
{
	class StringValueComparator
		implements Comparator
	{

		final SearchCollector this$0;

		public int compare(ValueDoc value0, ValueDoc value1)
		{
			String strValue0 = (String)value0.value;
			String strValue1 = (String)value1.value;
			if (isSortReverse)
				return strValue0.compareTo(strValue1);
			else
				return -strValue0.compareTo(strValue1);
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((ValueDoc)obj, (ValueDoc)obj1);
		}

		StringValueComparator()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}

	class FloatValueComparator
		implements Comparator
	{

		final SearchCollector this$0;

		public int compare(ValueDoc value0, ValueDoc value1)
		{
			if (isSortReverse)
			{
				if (((Float)value0.value).floatValue() > ((Float)value1.value).floatValue())
					return -1;
				if (((Float)value0.value).floatValue() < ((Float)value1.value).floatValue())
					return 1;
			} else
			{
				if (((Float)value0.value).floatValue() > ((Float)value1.value).floatValue())
					return -1;
				if (((Float)value0.value).floatValue() < ((Float)value1.value).floatValue())
					return 1;
			}
			return 0;
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((ValueDoc)obj, (ValueDoc)obj1);
		}

		FloatValueComparator()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}

	class DoubleValueComparator
		implements Comparator
	{

		final SearchCollector this$0;

		public int compare(ValueDoc value0, ValueDoc value1)
		{
			if (isSortReverse)
			{
				if (((Double)value0.value).doubleValue() > ((Double)value1.value).doubleValue())
					return -1;
				if (((Double)value0.value).doubleValue() < ((Double)value1.value).doubleValue())
					return 1;
			} else
			{
				if (((Double)value0.value).doubleValue() > ((Double)value1.value).doubleValue())
					return -1;
				if (((Double)value0.value).doubleValue() < ((Double)value1.value).doubleValue())
					return 1;
			}
			return 0;
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((ValueDoc)obj, (ValueDoc)obj1);
		}

		DoubleValueComparator()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}

	class LongValueComparator
		implements Comparator
	{

		final SearchCollector this$0;

		public int compare(ValueDoc value0, ValueDoc value1)
		{
			if (isSortReverse)
			{
				if (((Long)value0.value).longValue() > ((Long)value1.value).longValue())
					return -1;
				if (((Long)value0.value).longValue() < ((Long)value1.value).longValue())
					return 1;
			} else
			{
				if (((Long)value0.value).longValue() > ((Long)value1.value).longValue())
					return -1;
				if (((Long)value0.value).longValue() < ((Long)value1.value).longValue())
					return 1;
			}
			return 0;
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((ValueDoc)obj, (ValueDoc)obj1);
		}

		LongValueComparator()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}

	class IntValueComparator
		implements Comparator
	{

		final SearchCollector this$0;

		public int compare(ValueDoc value0, ValueDoc value1)
		{
			if (isSortReverse)
			{
				if (((Integer)value0.value).intValue() > ((Integer)value1.value).intValue())
					return -1;
				if (((Integer)value0.value).intValue() < ((Integer)value1.value).intValue())
					return 1;
			} else
			{
				if (((Integer)value0.value).intValue() > ((Integer)value1.value).intValue())
					return 1;
				if (((Integer)value0.value).intValue() < ((Integer)value1.value).intValue())
					return -1;
			}
			return 0;
		}

		public volatile int compare(Object obj, Object obj1)
		{
			return compare((ValueDoc)obj, (ValueDoc)obj1);
		}

		IntValueComparator()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}

	class ValueDoc
	{

		Object value;
		int doc;
		float score;
		final SearchCollector this$0;

		ValueDoc()
		{
			this.this$0 = SearchCollector.this;
			super();
		}
	}


	public String name;
	public boolean isSortReverse;
	public String type;
	ITMSearchWrapper searcherWrapper;
	org.apache.lucene.search.FieldCache.DocTerms docTerms;
	private int docBase;
	private BytesRef bytesRef;
	private List valueDocs;
	private int totalDocCount;

	public SearchCollector(String sortField, boolean isSortReverse, ITMSearchWrapper searcherWrapper)
	{
		name = null;
		this.isSortReverse = false;
		type = null;
		this.searcherWrapper = null;
		docTerms = null;
		bytesRef = new BytesRef(1024);
		valueDocs = null;
		totalDocCount = 0;
		name = ITMUtils.isValidStr(sortField) ? sortField : "id";
		this.isSortReverse = isSortReverse;
		this.searcherWrapper = searcherWrapper;
		totalDocCount = 0;
		type = MyQueryParser.getFieldType(searcherWrapper.itmUserData, name);
		valueDocs = new ArrayList();
	}

	public int getTopDocs(int pageSize, int currentPage, TopDocs topDocs)
	{
		doSort();
		int begin = pageSize * (currentPage - 1);
		int end = Math.min(begin + pageSize, valueDocs.size());
		topDocs.scoreDocs = new ScoreDoc[end - begin];
		for (int i = begin; i < end; i++)
		{
			ValueDoc valueDoc = (ValueDoc)valueDocs.get(i);
			ScoreDoc tmp = new ScoreDoc(valueDoc.doc, valueDoc.score);
			topDocs.scoreDocs[i - begin] = tmp;
		}

		topDocs.totalHits = totalDocCount;
		return 0;
	}

	public boolean isValid()
	{
		com.iflytek.itm.api.ITMBuilder.ITMField itmField = searcherWrapper.itmUserData.getField(name);
		return ITMUtils.isValidStr(name) && (!ITMUtils.isValidStr(itmField.analyzer) || itmField.analyzer.equals("null"));
	}

	public void setScorer(Scorer scorer1)
		throws IOException
	{
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		org.apache.lucene.index.AtomicReader ar = context.reader();
		docTerms = FieldCache.DEFAULT.getTerms(ar, name);
		docBase = context.docBase;
	}

	public void collect(int doc)
		throws IOException
	{
		int docId = doc + docBase;
		bytesRef = docTerms.getTerm(doc, bytesRef);
		addValueDoc(docId, bytesRef, 0.0F);
		totalDocCount++;
	}

	public boolean acceptsDocsOutOfOrder()
	{
		return true;
	}

	public void addValueDoc(int doc, BytesRef value, float score)
	{
		ValueDoc valueDoc = new ValueDoc();
		valueDoc.doc = doc;
		valueDoc.score = score;
		if (type.equals("int"))
			valueDoc.value = Integer.valueOf(NumericUtils.prefixCodedToInt(value));
		else
		if (type.equals("long"))
			valueDoc.value = Long.valueOf(NumericUtils.prefixCodedToLong(value));
		else
		if (type.equals("float"))
			valueDoc.value = Float.valueOf(NumericUtils.sortableIntToFloat(NumericUtils.prefixCodedToInt(value)));
		else
		if (type.equals("double"))
			valueDoc.value = Double.valueOf(NumericUtils.sortableLongToDouble(NumericUtils.prefixCodedToLong(value)));
		else
		if (type.equals("string"))
			valueDoc.value = value.utf8ToString();
		valueDocs.add(valueDoc);
	}

	private void doSort()
	{
		if (type.equals("int"))
			Collections.sort(valueDocs, new IntValueComparator());
		else
		if (type.equals("long"))
			Collections.sort(valueDocs, new LongValueComparator());
		else
		if (type.equals("float"))
			Collections.sort(valueDocs, new FloatValueComparator());
		else
		if (type.equals("double"))
			Collections.sort(valueDocs, new DoubleValueComparator());
		else
		if (type.equals("string"))
			Collections.sort(valueDocs, new StringValueComparator());
	}
}
