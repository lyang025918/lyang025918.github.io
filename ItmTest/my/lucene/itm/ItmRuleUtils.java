// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItmRuleUtils.java

package my.lucene.itm;

import java.io.File;
import java.io.PrintStream;
import java.util.*;
import my.lucene.search.SearchUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.store.FSDirectory;

public class ItmRuleUtils extends SearchUtils
{

	public ItmRuleUtils()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		String index = "e:\\study\\source\\java\\lucene\\index\\10w";
		String queryString = "百度";
		SearchUtils idx_search = new ItmRuleUtils();
		idx_search.open(index);
		idx_search.doSearch(queryString);
		idx_search.close();
	}

	public void doSearch(String queryString)
		throws Exception
	{
		System.out.printf("index doc count = [%d]\n", new Object[] {
			Integer.valueOf(reader.maxDoc())
		});
		long startTime = System.currentTimeMillis();
		System.out.println(test2());
		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
	}

	private String test1()
		throws Exception
	{
		String str = testAnd("新闻", "晚报");
		return str;
	}

	private String test2()
		throws Exception
	{
		StringBuffer buffer = new StringBuffer(0x19000);
		buffer.append("检索：((投诉|举报)&(工号|核实|(值班#经理)|(找#领导)|(工号#多少)|(我要#投诉)|(领导#反映)|(登记#上去)|(时之内#会)|(工作人员)))\n");
		HashSet strArr = new HashSet();
		strArr.add("投诉");
		strArr.add("举报");
		strArr.add("工号");
		strArr.add("核实");
		strArr.add("值班");
		strArr.add("经理");
		strArr.add("找");
		strArr.add("领导");
		strArr.add("工号");
		strArr.add("多少");
		strArr.add("我要");
		strArr.add("投诉");
		strArr.add("领导");
		strArr.add("反映");
		strArr.add("登记");
		strArr.add("上去");
		strArr.add("时之内");
		strArr.add("会");
		strArr.add("工作人员");
		BooleanQuery bq1 = getBooleanOrQuery("投诉", "举报");
		BooleanQuery bq2 = new BooleanQuery();
		TermQuery tq1 = getTermQuery("工号");
		bq2.add(tq1, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		TermQuery tq2 = getTermQuery("核实");
		bq2.add(tq2, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery1 = getSpanQuery("值班", "经理");
		bq2.add(spanNearQuery1, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery2 = getSpanQuery("找", "领导");
		bq2.add(spanNearQuery2, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery3 = getSpanQuery("工号", "多少");
		bq2.add(spanNearQuery3, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery4 = getSpanQuery("我要", "投诉");
		bq2.add(spanNearQuery4, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery5 = getSpanQuery("领导", "反映");
		bq2.add(spanNearQuery5, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery6 = getSpanQuery("登记", "上去");
		bq2.add(spanNearQuery6, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		SpanNearQuery spanNearQuery7 = getSpanQuery("时之内", "会");
		bq2.add(spanNearQuery7, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		TermQuery tq3 = getTermQuery("工作人员");
		bq2.add(tq3, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		BooleanQuery bq0 = new BooleanQuery();
		bq0.add(bq1, org.apache.lucene.search.BooleanClause.Occur.MUST);
		bq0.add(bq2, org.apache.lucene.search.BooleanClause.Occur.MUST);
		int maxDoc = reader.maxDoc();
		TopDocs topDocs = searcher.search(bq0, maxDoc);
		int totalHits = topDocs.totalHits;
		buffer.append((new StringBuilder()).append("total hit docs=").append(totalHits).append("\n").toString());
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++)
		{
			int docID = scoreDocs[i].doc;
			Document doc = searcher.doc(docID);
			buffer.append((new StringBuilder()).append("rank=").append(i).append(", docid=").append(docID).append(", title=").append(doc.get("title")).append(", path=").append(doc.get("path")).append("\n").toString());
		}

		return buffer.toString();
	}

	private String testAnd(String str1, String str2)
		throws Exception
	{
		StringBuffer buffer = new StringBuffer(0x19000);
		buffer.append((new StringBuilder()).append("检索：(").append(str1).append("&").append(str2).append(")\n").toString());
		Term term1 = new Term("content", str1);
		TermQuery query1 = new TermQuery(term1);
		Term term2 = new Term("content", str2);
		TermQuery query2 = new TermQuery(term2);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, org.apache.lucene.search.BooleanClause.Occur.MUST);
		booleanQuery.add(query2, org.apache.lucene.search.BooleanClause.Occur.MUST);
		int maxDoc = reader.maxDoc();
		TopDocs topDocs = searcher.search(booleanQuery, maxDoc);
		int totalHits = topDocs.totalHits;
		buffer.append((new StringBuilder()).append("total hit docs=").append(totalHits).append("\n").toString());
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++)
		{
			int docID = scoreDocs[i].doc;
			Document doc = searcher.doc(docID);
			buffer.append((new StringBuilder()).append("rank=").append(i).append(", docid=").append(docID).append(", title=").append(doc.get("title")).append(", path=").append(doc.get("path")).append("\n").toString());
		}

		return buffer.toString();
	}

	protected String getPositionInfo(int docId, HashSet strArr)
		throws Exception
	{
		StringBuffer buffer = new StringBuffer(1024);
		List leaves = reader.leaves();
		for (Iterator it = strArr.iterator(); it.hasNext(); buffer.append("\n"))
		{
			String str = (String)it.next();
			buffer.append((new StringBuilder()).append("str=").append(str).append(", pos=").toString());
			ArrayList arrPos = getTermPositions(docId, new Term("content", str), index);
			for (int i = 0; i < arrPos.size(); i++)
				buffer.append((new StringBuilder()).append(arrPos.get(i)).append(",").toString());

		}

		return buffer.toString();
	}

	public ArrayList getTermPositions(int docId, Term term, String index)
		throws Exception
	{
		ArrayList arrPos = new ArrayList();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		List leaves = reader.leaves();
label0:
		for (int i = 0; i < leaves.size(); i++)
		{
			AtomicReader ar = ((AtomicReaderContext)leaves.get(i)).reader();
			DocsAndPositionsEnum dape = ar.termPositionsEnum(term);
			if (dape == null)
				break;
			do
			{
				int docState = dape.nextDoc();
				if (0x7fffffff == docState)
					continue label0;
				if (docId == dape.docID())
				{
					int freq = dape.freq();
					int ifreq = 0;
					while (ifreq < freq) 
					{
						int pos = dape.nextPosition();
						arrPos.add(Integer.valueOf(pos));
						ifreq++;
					}
				}
			} while (true);
		}

		return arrPos;
	}
}
