// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Pattern.java

package com.iflytek.itm.mining;

import com.iflytek.itm.util.ITMUtils;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Pattern
{

	public Map m_Synonym;
	public int dim;

	public Pattern(InputStream bussinessInputStream)
		throws IOException
	{
		m_Synonym = new HashMap();
		loadResource(bussinessInputStream);
	}

	public static void main(String args[])
		throws Exception
	{
		String text = "最近一直在做一个项目的前期设计工作，考虑到后期系统的扩展和性能问题也找了很多解决方法，有一个就是用到了数据库的缓存工具memcached（当然该工具并不仅仅局限于数据库的缓存）。先简单的介绍下什么是memcached。";
		Analyzer analyzer = new IKAnalyzer();
		Pattern pattern = new Pattern(ITMUtils.getResourceFile("business.txt"));
		String strFeature = pattern.feature("", text, analyzer);
		System.out.println((new StringBuilder()).append("feature=").append(strFeature).toString());
	}

	public String feature(String field, String onebest, Analyzer analyzer)
		throws IOException
	{
		double scores[] = featureArray(field, onebest, analyzer);
		return toString(scores);
	}

	public double[] featureArray(String field, String onebest, Analyzer analyzer)
		throws IOException
	{
		double scores[] = new double[dim + 1];
		TokenStream stream = analyzer.tokenStream(field, new StringReader(onebest));
		CharTermAttribute termTextAttr = (CharTermAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		int wordNum;
		for (wordNum = 0; stream.incrementToken(); wordNum++)
		{
			String word = termTextAttr.toString();
			Integer id = null;
			if ((id = (Integer)m_Synonym.get(word)) != null)
				scores[id.intValue()]++;
		}

		for (int i = 0; i < scores.length; i++)
			scores[i] /= wordNum;

		return scores;
	}

	private int loadResource(InputStream bussinessInputStream)
		throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(bussinessInputStream, "gbk"));
		String line = null;
		int iCount = 0;
		do
		{
			line = br.readLine();
			if (null != line)
			{
				String words[] = line.split(" ");
				for (int i = 0; i < words.length; i++)
				{
					String tmp = words[i].trim();
					if (tmp.isEmpty())
						continue;
					if (m_Synonym.containsKey(tmp))
						System.out.println((new StringBuilder()).append("Pattern.loadResource | Error: word already existed, word=").append(words[i]).toString());
					else
						m_Synonym.put(tmp, Integer.valueOf(iCount));
				}

				iCount++;
			} else
			{
				dim = iCount <= 0 ? 0 : iCount - 1;
				return 0;
			}
		} while (true);
	}

	private String toString(double scores[])
	{
		StringBuffer buffer = new StringBuffer(1024);
		for (int i = 0; i < scores.length; i++)
			buffer.append(scores[i]).append(" ");

		return buffer.toString();
	}
}
