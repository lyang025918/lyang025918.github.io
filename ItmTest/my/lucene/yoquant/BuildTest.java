// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BuildTest.java

package my.lucene.yoquant;

import java.io.*;
import java.util.*;
import mylib.file.DelDir;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class BuildTest
{
	class QA
	{

		String question;
		String answer;
		final BuildTest this$0;

		QA()
		{
			this.this$0 = BuildTest.this;
			super();
		}
	}


	private static BuildTest inst = new BuildTest();
	ArrayList qaArrayList;

	private BuildTest()
	{
		qaArrayList = new ArrayList();
	}

	public static BuildTest inst()
	{
		return inst;
	}

	public static void main(String args[])
	{
		String indexPath = "c:/yoquant";
		String docPath = "e:\\study\\工作\\云量科技\\服务端\\机器人\\lucene\\text\\test.txt";
		BuildTest idx = inst();
		idx.doBuild(docPath, indexPath);
	}

	public void doBuild(String docPath, String indexPath)
	{
		File docDir = new File(docPath);
		if (!docDir.exists() || !docDir.canRead())
		{
			System.out.println((new StringBuilder()).append("Document directory '").append(docDir.getAbsolutePath()).append("' does not exist or is not readable, please check the path").toString());
			System.exit(1);
		}
		File idxDir = new File(indexPath);
		if (idxDir.exists())
		{
			boolean beret = DelDir.doDel(indexPath);
			if (beret)
				System.out.printf("DelDir.doDel | success, indexpath=[%s]\n", new Object[] {
					indexPath
				});
			else
				System.out.printf("DelDir.doDel | fail, indexpath=[%s]\n", new Object[] {
					indexPath
				});
		}
		Date start = new Date();
		try
		{
			System.out.println((new StringBuilder()).append("Indexing to directory '").append(indexPath).append("'...").toString());
			Directory dir = FSDirectory.open(new File(indexPath));
			Analyzer analyzer = new IKAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
			boolean create = true;
			if (create)
				iwc.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE);
			else
				iwc.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs(writer, docDir);
			Map userData = new HashMap();
			userData.put("test", "这只是一个测试");
			writer.commit(userData);
			writer.close();
			Date end = new Date();
			System.out.println((new StringBuilder()).append(end.getTime() - start.getTime()).append(" total milliseconds").toString());
		}
		catch (IOException e)
		{
			System.out.println((new StringBuilder()).append(" caught a ").append(e.getClass()).append("\n with message: ").append(e.getMessage()).toString());
		}
	}

	void indexDocs(IndexWriter writer, File file)
		throws IOException
	{
		parseFile(file);
		for (int i = 0; i < qaArrayList.size(); i++)
		{
			QA qa = (QA)qaArrayList.get(i);
			Document doc = new Document();
			Field idField = new StringField("id", String.valueOf(i), org.apache.lucene.document.Field.Store.YES);
			doc.add(idField);
			Field pathField = new StringField("answer", qa.answer, org.apache.lucene.document.Field.Store.YES);
			doc.add(pathField);
			Field contentField = new TextField("question", qa.question, org.apache.lucene.document.Field.Store.YES);
			doc.add(contentField);
			if (writer.getConfig().getOpenMode() == org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE)
			{
				System.out.println((new StringBuilder()).append("adding ").append(file).toString());
				writer.addDocument(doc);
			} else
			{
				System.out.println((new StringBuilder()).append("updating ").append(file).toString());
				writer.updateDocument(new Term("path", file.getPath()), doc);
			}
		}

	}

	void parseFile(File file)
	{
		try
		{
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "gbk"));
			String line = null;
			do
			{
				line = br.readLine();
				if (null == line)
					break;
				String tmp[] = line.split("\t");
				if (tmp.length != 2)
				{
					System.out.println((new StringBuilder()).append("line length not 2, line=").append(line).toString());
				} else
				{
					QA qa = new QA();
					qa.question = tmp[0];
					qa.answer = tmp[1];
					qaArrayList.add(qa);
				}
			} while (true);
			fis.close();
		}
		catch (Exception e)
		{
			System.out.printf("Error: %s", new Object[] {
				e.getMessage()
			});
		}
	}

}
