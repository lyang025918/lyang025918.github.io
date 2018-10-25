// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalyzeUtils.java

package my.lucene.analysis;

import java.io.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzeUtils
{

	public AnalyzeUtils()
	{
	}

	public static void main(String args[])
	{
		String text = "最近一直在做一个项目的前期设计工作，考虑到后期系统的扩展和性能问题也找了很多解决方法，有一个就是用到了数据库的缓存工具memcached（当然该工具并不仅仅局限于数据库的缓存）。先简单的介绍下什么是memcached。";
		text = "好。您要。查询，不显示。我想，问一下，啊，唉，对，把，您的，那个，号码，十八，啊。不是，那，这张卡，您看，的，具体，方式，以，短信，给您，发到，手机上，您看，一下，提醒，您，一下，腾讯，的，会，是什么，还是，您，停止，上，讲，了，要，帮您，转到，七点，以后，才，可以。好吗，啊，好的。啊，不客气，也，可以，给您，发过去，了，您看，一下，短信，再，稍等，上面，会，提示，资费，的。你好，请讲。不客气，还有，其他，问题，呢。啊，啊，啊，没了，嗯。感谢来电。先生，好吗。";
		Analyzer smcn = new IKAnalyzer();
		try
		{
			boolean isCmd = false;
			if (isCmd && args.length >= 1)
			{
				String path = args[0];
				text = getContent(path);
			}
			System.out.println((new StringBuilder()).append("text=").append(text).toString());
			System.out.println("分词后:");
			displayTokens(smcn, text);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void displayTokens(Analyzer analyzer, String text)
		throws IOException
	{
		AnalyzeUtils au = new AnalyzeUtils();
		TokenStream stream = au.getTokenStream(analyzer, "", text);
		au.printTokenStream(stream);
	}

	private TokenStream getTokenStream(Analyzer analyzer, String field, String fieldValue)
		throws IOException
	{
		TokenStream stream = analyzer.tokenStream(field, new StringReader(fieldValue));
		return stream;
	}

	private void printTokenStream(TokenStream stream)
		throws IOException
	{
		CharTermAttribute termTextAttr = (CharTermAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		OffsetAttribute termOffsetAttr = (OffsetAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		TypeAttribute termTypeAttr = (TypeAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		for (int i = 0; stream.incrementToken(); i++)
		{
			String str = termTextAttr.toString();
			int bos = termOffsetAttr.startOffset();
			int eos = termOffsetAttr.endOffset();
			String type = termTypeAttr.type();
			System.out.printf("pos=%d, [%s:(%d->%d):%s]\n", new Object[] {
				Integer.valueOf(i), str, Integer.valueOf(bos), Integer.valueOf(eos), type
			});
		}

	}

	private static String getContent(String path)
		throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "gbk"));
		String line = null;
		StringBuffer brContent = new StringBuffer();
		do
		{
			line = br.readLine();
			if (null != line)
				brContent.append((new StringBuilder()).append(line).append("<br>").toString());
			else
				return brContent.toString();
		} while (true);
	}
}
