// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IKAnalzyerDemo.java

package org.wltea.analyzer.sample;

import java.io.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalzyerDemo
{

	public IKAnalzyerDemo()
	{
	}

	public static void main(String args[])
	{
		Analyzer analyzer;
		TokenStream ts;
		analyzer = new IKAnalyzer(true);
		ts = null;
		try
		{
			ts = analyzer.tokenStream("myfield", new StringReader("这是一个中文分词的例子，你可以直接运行它！IKAnalyer can analysis english text too"));
			OffsetAttribute offset = (OffsetAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
			CharTermAttribute term = (CharTermAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			TypeAttribute type = (TypeAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
			ts.reset();
			for (; ts.incrementToken(); System.out.println((new StringBuilder(String.valueOf(offset.startOffset()))).append(" - ").append(offset.endOffset()).append(" : ").append(term.toString()).append(" | ").append(type.type()).toString()));
			ts.end();
			break MISSING_BLOCK_LABEL_198;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (ts != null)
			try
			{
				ts.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_216;
		Exception exception;
		exception;
		if (ts != null)
			try
			{
				ts.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception;
		if (ts != null)
			try
			{
				ts.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}
}
