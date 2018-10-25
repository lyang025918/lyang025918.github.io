// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExactAnalyzer.java

package com.iflytek.itm.search;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;

public class ExactAnalyzer extends Analyzer
{
	class ExactTokenizer extends Tokenizer
	{

		private CharTermAttribute termAttr;
		private OffsetAttribute offsetAttr;
		private TypeAttribute typeAttr;
		StringBuilder buffer;
		final ExactAnalyzer this$0;

		public final boolean incrementToken()
			throws IOException
		{
			clearAttributes();
			if (buffer.length() > 0)
				buffer.delete(0, buffer.length());
			int a;
			if (-1 != (a = input.read()))
			{
				do
					buffer.append((char)a);
				while (-1 != (a = input.read()));
				termAttr.setEmpty().append(buffer.toString());
				offsetAttr.setOffset(0, input.toString().length());
				typeAttr.setType("word");
				return true;
			} else
			{
				return false;
			}
		}

		public ExactTokenizer(Reader input)
		{
			this.this$0 = ExactAnalyzer.this;
			super(input);
			buffer = new StringBuilder(1024);
			offsetAttr = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
			termAttr = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			typeAttr = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		}
	}


	public ExactAnalyzer()
	{
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new ExactTokenizer(reader));
	}
}
