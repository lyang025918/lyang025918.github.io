// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexUtils.java

package my.lucene.build;

import java.io.*;
import java.util.*;
import mylib.file.DelDir;
import mylib.file.FileInfo;
import mylib.utils.MobilePhone;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexUtils
{

	private static IndexUtils inst = new IndexUtils();

	private IndexUtils()
	{
	}

	public static IndexUtils inst()
	{
		return inst;
	}

	public static void main(String args[])
	{
		String usage = "java org.apache.lucene.demo.IndexFiles [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\nThis indexes the documents in DOCS_PATH, creating a Lucene indexin INDEX_PATH that can be searched with SearchFiles";
		String indexPath = "e:\\study\\source\\java\\lucene\\index\\4.0.0";
		indexPath = "e:\\study\\source\\java\\lucene\\index\\itm\\usr\\20121222";
		String docsPath = "e:\\study\\source\\java\\lucene\\data\\test\\";
		boolean create = true;
		boolean isCmd = true;
		if (isCmd)
		{
			for (int i = 0; i < args.length; i++)
			{
				if ("-index".equals(args[i]))
				{
					indexPath = args[i + 1];
					i++;
					continue;
				}
				if ("-docs".equals(args[i]))
				{
					docsPath = args[i + 1];
					i++;
					continue;
				}
				if ("-update".equals(args[i]))
					create = false;
			}

		}
		if (docsPath == null)
		{
			System.err.println((new StringBuilder()).append("Usage: ").append(usage).toString());
			System.exit(1);
		}
		IndexUtils idx = inst();
		idx.doBuild(docsPath, indexPath);
	}

	public void doBuild(String docsPath, String indexPath)
	{
		File docDir = new File(docsPath);
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

	static void indexDocs(IndexWriter writer, File file)
		throws IOException
	{
		if (file.canRead())
			if (file.isDirectory())
			{
				String files[] = file.list();
				if (files != null)
				{
					for (int i = 0; i < files.length; i++)
						indexDocs(writer, new File(file, files[i]));

				}
			} else
			if (FileInfo.getExt(file).equals("txt"))
			{
				Document doc = new Document();
				String id = FileInfo.getNameNoExt(file);
				Field idField = new StringField("id", id, org.apache.lucene.document.Field.Store.YES);
				doc.add(idField);
				Field pathField = new StringField("path", file.getPath(), org.apache.lucene.document.Field.Store.YES);
				doc.add(pathField);
				String fileTitle = FileInfo.getNameNoExt(file);
				Field titleField = new StringField("title", fileTitle, org.apache.lucene.document.Field.Store.YES);
				doc.add(titleField);
				doc.add(new LongField("modified", file.lastModified(), org.apache.lucene.document.Field.Store.NO));
				String content = getContent(file);
				Field contentField = new TextField("content", content, org.apache.lucene.document.Field.Store.YES);
				doc.add(contentField);
				String mobilePhone = MobilePhone.randomGenerate();
				Field mobilePhoneField = new StringField("phone", mobilePhone, org.apache.lucene.document.Field.Store.YES);
				doc.add(mobilePhoneField);
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

	public static String getContent(File file)
	{
		StringBuffer brContent = new StringBuffer();
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
				brContent.append((new StringBuilder()).append(line).append("<br>").toString());
			} while (true);
			fis.close();
		}
		catch (Exception e)
		{
			System.out.printf("Error: %s", new Object[] {
				e.getMessage()
			});
		}
		return brContent.toString();
	}

}
