// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilterSearch.java

package my.lucene.search;

import java.io.File;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

// Referenced classes of package my.lucene.search:
//			DocIdFilter

public class FilterSearch
{

	public FilterSearch()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		FilterSearch filterSearch = new FilterSearch();
		filterSearch.doSearch();
	}

	public void doSearch()
		throws Exception
	{
		String index = "e:\\study\\source\\java\\lucene\\index\\10w";
		String fieldName = "content";
		String queryString = "ÒÆ¶¯";
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new IKAnalyzer();
		System.out.printf("doc count = [%d]\n", new Object[] {
			Integer.valueOf(reader.maxDoc())
		});
		long startTime = System.currentTimeMillis();
		DocIdFilter filter = getFilter(1, 16208);
		Query query = getQuery(queryString);
		int maxDoc = reader.maxDoc();
		TopDocs topDocs = searcher.search(query, filter, maxDoc);
		int totalHits = topDocs.totalHits;
		System.out.printf("total hit docs=[%d]\n", new Object[] {
			Integer.valueOf(totalHits)
		});
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++)
		{
			int docID = scoreDocs[i].doc;
			Document doc = searcher.doc(docID);
			printDoc(i, docID, doc);
		}

		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
		reader.close();
	}

	private Query getQuery(String queryString)
	{
		TermQuery query1 = new TermQuery(new Term("content", queryString));
		WildcardQuery query2 = new WildcardQuery(new Term("phone", "135*"));
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, org.apache.lucene.search.BooleanClause.Occur.MUST);
		booleanQuery.add(query2, org.apache.lucene.search.BooleanClause.Occur.MUST);
		return booleanQuery;
	}

	private DocIdFilter getFilter(String index, String textField, String text, String idFieldName)
		throws Exception
	{
		Set docIds = doOldSearch(index, textField, text);
		System.out.println((new StringBuilder()).append("doc-id-set size=").append(docIds.size()).toString());
		DocIdFilter filter = new DocIdFilter(idFieldName, docIds);
		return filter;
	}

	private DocIdFilter getFilter(int begin, int end)
		throws Exception
	{
		Set docIds = new HashSet();
		for (int i = begin; i <= end; i++)
			docIds.add(Integer.valueOf(i));

		DocIdFilter filter = new DocIdFilter(docIds);
		return filter;
	}

	public Set doOldSearch(String index, String fieldName, String queryString)
		throws Exception
	{
		long startTime = System.currentTimeMillis();
		Set docPaths = new HashSet();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term(fieldName, queryString));
		int maxDoc = reader.maxDoc();
		TopDocs topDocs = searcher.search(query, maxDoc);
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < scoreDocs.length; i++)
		{
			int docID = scoreDocs[i].doc;
			Document doc = searcher.doc(docID);
			String path = doc.get("path");
			docPaths.add(path);
		}

		reader.close();
		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
		return docPaths;
	}

	protected void printDoc(int rank, int docid, Document doc)
	{
		System.out.printf("id=%d, path=%s, phone=%s\n", new Object[] {
			Integer.valueOf(docid), doc.get("path"), doc.get("phone")
		});
	}
}
