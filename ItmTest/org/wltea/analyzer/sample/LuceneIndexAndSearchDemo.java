// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LuceneIndexAndSearchDemo.java

package org.wltea.analyzer.sample;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneIndexAndSearchDemo
{

	public LuceneIndexAndSearchDemo()
	{
	}

	public static void main(String args[])
	{
		String fieldName;
		String text;
		org.apache.lucene.analysis.Analyzer analyzer;
		Directory directory;
		IndexReader ireader;
		fieldName = "text";
		text = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
		analyzer = new IKAnalyzer(true);
		directory = null;
		IndexWriter iwriter = null;
		ireader = null;
		IndexSearcher isearcher = null;
		directory = new RAMDirectory();
		IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		iwConfig.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter iwriter = new IndexWriter(directory, iwConfig);
		Document doc = new Document();
		doc.add(new StringField("ID", "10000", org.apache.lucene.document.Field.Store.YES));
		doc.add(new TextField(fieldName, text, org.apache.lucene.document.Field.Store.YES));
		iwriter.addDocument(doc);
		iwriter.close();
		ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		String keyword = "中文分词工具包";
		QueryParser qp = new QueryParser(Version.LUCENE_40, fieldName, analyzer);
		qp.setDefaultOperator(QueryParser.AND_OPERATOR);
		org.apache.lucene.search.Query query = qp.parse(keyword);
		System.out.println((new StringBuilder("Query = ")).append(query).toString());
		TopDocs topDocs = isearcher.search(query, 5);
		System.out.println((new StringBuilder("命中：")).append(topDocs.totalHits).toString());
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		for (int i = 0; i < topDocs.totalHits; i++)
		{
			Document targetDoc = isearcher.doc(scoreDocs[i].doc);
			System.out.println((new StringBuilder("内容：")).append(targetDoc.toString()).toString());
		}

		break MISSING_BLOCK_LABEL_555;
		CorruptIndexException e;
		e;
		e.printStackTrace();
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_595;
		e;
		e.printStackTrace();
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_595;
		e;
		e.printStackTrace();
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_595;
		e;
		e.printStackTrace();
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_595;
		Exception exception;
		exception;
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception;
		if (ireader != null)
			try
			{
				ireader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (directory != null)
			try
			{
				directory.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
}
