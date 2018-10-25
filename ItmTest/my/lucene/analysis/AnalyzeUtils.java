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
		String text = "���һֱ����һ����Ŀ��ǰ����ƹ��������ǵ�����ϵͳ����չ����������Ҳ���˺ܶ�����������һ�������õ������ݿ�Ļ��湤��memcached����Ȼ�ù��߲����������������ݿ�Ļ��棩���ȼ򵥵Ľ�����ʲô��memcached��";
		text = "�á���Ҫ����ѯ������ʾ�����룬��һ�£����������ԣ��ѣ����ģ��Ǹ������룬ʮ�ˣ��������ǣ��ǣ����ſ����������ģ����壬��ʽ���ԣ����ţ��������������ֻ��ϣ�������һ�£����ѣ�����һ�£���Ѷ���ģ��ᣬ��ʲô�����ǣ�����ֹͣ���ϣ������ˣ�Ҫ��������ת�����ߵ㣬�Ժ󣬲ţ����ԡ����𣬰����õġ�������������Ҳ�����ԣ�����������ȥ���ˣ�������һ�£����ţ��٣��Եȣ����棬�ᣬ��ʾ���ʷѣ��ġ���ã��뽲�������������У����������⣬�ء�������������û�ˣ��š���л���硣����������";
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
			System.out.println("�ִʺ�:");
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
