// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearchUtils.java

package my.lucene.search;

import java.io.File;
import java.io.PrintStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchUtils
{

	protected String index;
	public IndexReader reader;
	protected IndexSearcher searcher;
	protected Analyzer analyzer;

	public SearchUtils()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		String index = "e:\\study\\source\\java\\lucene\\index\\10w";
		index = "e:\\test_home\\index\\itm\\usr\\1.01.01";
		String queryString = "你好";
		SearchUtils idx_search = new SearchUtils();
		idx_search.open(index);
		System.out.printf("doc count = [%d]\n", new Object[] {
			Integer.valueOf(idx_search.reader.maxDoc())
		});
		idx_search.doSearch(queryString);
		idx_search.close();
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
		Query query = getQuery(queryString);
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
			printDoc(i, docID, doc);
		}

		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
	}

	public void doGroup()
		throws Exception
	{
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		long timeSpan = endTime - startTime;
		System.out.println((new StringBuilder()).append(timeSpan).append(" total milliseconds").toString());
	}

	protected void printDoc(int rank, int docid, Document doc)
	{
		System.out.printf("id=%s, path=%s, rand=%s\n", new Object[] {
			doc.get("id"), doc.get("path"), doc.get("rand")
		});
	}

	protected Query getQuery(String queryString)
		throws Exception
	{
		Query query = null;
		query = getRegexpQuery("");
		return query;
	}

	protected Query getParseQuery(String queryString)
		throws Exception
	{
		queryString = "content:你好";
		queryString = "phone:135*";
		queryString = "phone:[13517878856 TO 13517879455]";
		queryString = "modified:[1351354043024 TO 1352354043059]";
		QueryParser parser = new QueryParser(Version.LUCENE_40, "content", analyzer);
		Query query = parser.parse(queryString);
		String str2 = "modified:[1351354043024 TO 1352354043059] AND phone:1356*";
		Query query2 = parser.parse(str2);
		query = new FilteredQuery(query, new QueryWrapperFilter(query2));
		return query;
	}

	protected TermQuery getTermQuery(String str)
		throws Exception
	{
		str = "你好.{1,10}来电";
		TermQuery query = null;
		query = new TermQuery(new Term("content", str));
		return query;
	}

	protected RegexpQuery getRegexpQuery(String str)
		throws Exception
	{
		str = "服.";
		RegexpQuery query = null;
		query = new RegexpQuery(new Term("content", str));
		System.out.println((new StringBuilder()).append("str=").append(query.toString()).toString());
		return query;
	}

	protected Query getPhraseQuery(String queryString)
		throws Exception
	{
		PhraseQuery query = new PhraseQuery();
		query.add(new Term("content", "新闻"));
		query.add(new Term("content", "早晚"));
		query.setSlop(5);
		return query;
	}

	protected WildcardQuery getWildcardQuery(String queryString)
		throws Exception
	{
		WildcardQuery query = null;
		query = new WildcardQuery(new Term("phone", "135*"));
		return query;
	}

	protected SpanNearQuery getSpanQuery(String str1, String str2)
		throws Exception
	{
		SpanTermQuery query1 = new SpanTermQuery(new Term("content", "套餐"));
		SpanTermQuery query2 = new SpanTermQuery(new Term("content", "上网"));
		SpanNearQuery query = new SpanNearQuery(new SpanQuery[] {
			query1, query2
		}, 10, true);
		System.out.println((new StringBuilder()).append("SpanTermQuery=").append(query1.toString()).toString());
		System.out.println((new StringBuilder()).append("SpanNearQuery=").append(query.toString()).toString());
		return query;
	}

	protected BooleanQuery getBooleanOrQuery(String str1, String str2)
		throws Exception
	{
		TermQuery query1 = getTermQuery(str1);
		TermQuery query2 = getTermQuery(str2);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		return booleanQuery;
	}

	protected BooleanQuery getBooleanAndQuery(String str1, String str2)
		throws Exception
	{
		TermQuery query1 = getTermQuery(str1);
		TermQuery query2 = getTermQuery(str2);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, org.apache.lucene.search.BooleanClause.Occur.MUST);
		booleanQuery.add(query2, org.apache.lucene.search.BooleanClause.Occur.MUST);
		return booleanQuery;
	}

	protected Query getOtherQuery(String queryString)
		throws Exception
	{
		TermQuery query1 = getTermQuery(queryString);
		WildcardQuery query2 = getWildcardQuery(queryString);
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(query1, org.apache.lucene.search.BooleanClause.Occur.MUST);
		booleanQuery.add(query2, org.apache.lucene.search.BooleanClause.Occur.MUST);
		return booleanQuery;
	}

	protected boolean isFileExisted(String path)
		throws Exception
	{
		boolean ret = false;
		int fid = getFileId(path);
		if (-1 != fid)
			ret = true;
		System.out.printf("isFileExisted ? path=[%s], fid=[%d], answer=[%s]\n", new Object[] {
			path, Integer.valueOf(fid), ret ? "true" : "false"
		});
		return ret;
	}

	protected int getFileId(String path)
		throws Exception
	{
		int fid = -1;
		int maxFileId = reader.maxDoc() - 1;
		int i = 0;
		do
		{
			if (i > maxFileId)
				break;
			Document doc = searcher.doc(i);
			String tmpPath = doc.get("lrcPath");
			if (tmpPath.equals(path))
			{
				fid = i;
				break;
			}
			i++;
		} while (true);
		return fid;
	}

	protected String getFileContent(int fid)
		throws Exception
	{
		String ret = "";
		Document doc = searcher.doc(fid);
		ret = doc.get("content");
		System.out.printf("fid=[%d], content=[%s]\n", new Object[] {
			Integer.valueOf(fid), ret
		});
		return ret;
	}
}
