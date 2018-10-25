// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearchTest.java

package my.lucene.yoquant;

import java.io.File;
import java.io.PrintStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchTest
{

	protected String index;
	public IndexReader reader;
	protected IndexSearcher searcher;
	protected Analyzer analyzer;

	public SearchTest()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		String index = "c:/yoquant";
		String queryString = "这是个励志的故事";
		SearchTest idx_search = new SearchTest();
		idx_search.open(index);
		System.out.printf("doc count = [%d]\n", new Object[] {
			Integer.valueOf(idx_search.reader.maxDoc())
		});
		idx_search.doSearch(queryString);
		idx_search.close();
	}

	protected Query getQuery(String queryString, String fieldName)
		throws Exception
	{
		QueryParser parser = new QueryParser(Version.LUCENE_40, fieldName, analyzer);
		Query query = parser.parse(queryString);
		System.out.println((new StringBuilder()).append("query=").append(query.toString()).toString());
		return query;
	}

	public void open(String index)
		throws Exception
	{
		this.index = index;
		reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		searcher = new IndexSearcher(reader);
		analyzer = new IKAnalyzer();
	}

	public void close()
		throws Exception
	{
		reader.close();
		index = "";
		analyzer = null;
	}

	public void doSearch(String queryString)
		throws Exception
	{
		long startTime = System.currentTimeMillis();
		Query query = getQuery(queryString, "question");
		int maxDoc = reader.maxDoc();
		TopDocs topDocs = searcher.search(query, maxDoc);
		int totalHits = topDocs.totalHits;
		System.out.printf("total hit docs=[%d]\n", new Object[] {
			Integer.valueOf(totalHits)
		});
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++)
		{
			int docID = scoreDocs[i].doc;
			Document doc = searcher.doc(docID);
			if (isRight(queryString, doc))
				System.out.printf("id=%s, q=%s, a=%s\n", new Object[] {
					doc.get("id"), doc.get("question"), doc.get("answer")
				});
		}

		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
	}

	boolean isRight(String str, Document doc)
	{
		String arr[] = doc.get("question").split("&");
		boolean is_right = true;
		int i = 0;
		do
		{
			if (i >= arr.length)
				break;
			if (!str.contains(arr[i]))
			{
				is_right = false;
				break;
			}
			i++;
		} while (true);
		return is_right;
	}
}
