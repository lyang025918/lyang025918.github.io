// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RuleAnalyzer.java

package com.iflytek.itm.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.*;

public class RuleAnalyzer extends Analyzer
{
	static class RuleTokenizer extends Tokenizer
	{

		private CharTermAttribute termAttr;
		private OffsetAttribute offsetAttr;
		private TypeAttribute typeAttr;
		StringBuilder buffer;

		public final boolean incrementToken()
			throws IOException
		{
			clearAttributes();
			if (buffer.length() > 0)
				buffer.delete(0, buffer.length());
			int a = -1;
			if (-1 != (a = input.read()))
			{
				buffer.append((char)a);
				termAttr.setEmpty().append(buffer.toString().toLowerCase());
				offsetAttr.setOffset(0, input.toString().length());
				typeAttr.setType("char");
				return true;
			} else
			{
				return false;
			}
		}

		protected boolean isTokenChar(int c)
		{
			String regexStr = "[\\S]";
			Pattern pattern = Pattern.compile(regexStr);
			char ch = (char)c;
			Matcher matcher = pattern.matcher(String.valueOf(ch));
			return matcher.matches();
		}

		public RuleTokenizer(Reader input)
		{
			super(input);
			buffer = new StringBuilder(1024);
			offsetAttr = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
			termAttr = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			typeAttr = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		}
	}


	public RuleAnalyzer()
	{
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new RuleTokenizer(reader));
	}

	public static List analyze(String str)
	{
		List wordList = new ArrayList();
		try
		{
			RuleTokenizer tokenizer = new RuleTokenizer(new StringReader(str));
			CharTermAttribute termTextAttr = (CharTermAttribute)tokenizer.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			String word;
			for (; tokenizer.incrementToken(); wordList.add(word))
				word = termTextAttr.toString();

		}
		catch (Exception e) { }
		return wordList;
	}

	public static void main(String args[])
		throws IOException
	{
		String text = "c   hinese我是GPRS套餐50m流量，嗯，！P在";
		Analyzer analyzer = new RuleAnalyzer();
		TokenStream stream = analyzer.tokenStream("", new StringReader(text));
		CharTermAttribute termTextAttr = (CharTermAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		OffsetAttribute termOffsetAttr = (OffsetAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		for (int i = 0; stream.incrementToken(); i++)
		{
			String str = termTextAttr.toString();
			int bos = termOffsetAttr.startOffset();
			int eos = termOffsetAttr.endOffset();
			System.out.printf("pos=%d, [%s:(%d->%d)]\n", new Object[] {
				Integer.valueOf(i), str, Integer.valueOf(bos), Integer.valueOf(eos)
			});
		}

	}
}
