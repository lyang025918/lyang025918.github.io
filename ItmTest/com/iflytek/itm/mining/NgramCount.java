// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NgramCount.java

package com.iflytek.itm.mining;

import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

// Referenced classes of package com.iflytek.itm.mining:
//			ITMMiningResource, HotUtil

public class NgramCount
{

	public Map ngrams;
	private int gap;
	private int n;

	public NgramCount(int n, int gap)
		throws IOException
	{
		ngrams = new HashMap();
		this.gap = 0;
		this.n = 0;
		this.n = n;
		this.gap = gap;
	}

	private void add(String word, Map tmpNgrams)
	{
		if (tmpNgrams.containsKey(word))
		{
			int cnt = ((Integer)tmpNgrams.get(word)).intValue();
			tmpNgrams.put(word, Integer.valueOf(cnt + 1));
		} else
		{
			tmpNgrams.put(word, Integer.valueOf(1));
		}
	}

	public int generate(String field, String fieldValue, Analyzer analyzer, Set featureWords)
		throws IOException
	{
		List words = new ArrayList();
		TokenStream stream = analyzer.tokenStream(field, new StringReader(fieldValue));
		CharTermAttribute termTextAttr = (CharTermAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		do
		{
			if (!stream.incrementToken())
				break;
			String str = termTextAttr.toString();
			if (!ITMMiningResource.inst().isNoiseWord(str))
				words.add(str);
		} while (true);
		Map tmpNgrams = new HashMap();
		StringBuilder sb = null;
		for (ListIterator it = words.listIterator(); it.hasNext();)
		{
			String word = (String)it.next();
			sb = new StringBuilder(word);
			add(word, tmpNgrams);
			int nowGram = 1;
			it.previous();
			for (; it.hasPrevious() && nowGram < n + gap; nowGram++)
			{
				sb.insert(0, HotUtil.NGRAM_WORD_SPLIT_CHAR);
				sb.insert(0, (String)it.previous());
				add(sb.toString(), tmpNgrams);
			}

			while (nowGram > 0) 
			{
				nowGram--;
				it.next();
			}
		}

		Set keys = tmpNgrams.keySet();
		Iterator it = keys.iterator();
		do
		{
			if (!it.hasNext())
				break;
			String word = (String)it.next();
			String wordList[] = word.split(HotUtil.NGRAM_WORD_SPLIT_CHAR);
			if (hasFeature(wordList, featureWords))
				if (wordList.length == n)
					add(word, ngrams);
				else
				if (wordList.length > n)
				{
					word = (new StringBuilder()).append(wordList[0]).append(HotUtil.NGRAM_WORD_SPLIT_CHAR).append(wordList[wordList.length - 1]).toString();
					add(word, ngrams);
				}
		} while (true);
		return 0;
	}

	private boolean hasFeature(String wordList[], Set featureWords)
	{
		boolean ans = false;
		int i = 0;
		do
		{
			if (i >= wordList.length)
				break;
			if (featureWords.contains(wordList[i]))
			{
				ans = true;
				break;
			}
			i++;
		} while (true);
		return ans;
	}

	public void print()
	{
		System.out.println((new StringBuilder()).append("n-gram=").append(n).toString());
		Set keys = ngrams.keySet();
		String word;
		int cnt;
		for (Iterator it = keys.iterator(); it.hasNext(); System.out.println((new StringBuilder()).append(word).append(" ").append(cnt).toString()))
		{
			word = (String)it.next();
			cnt = ((Integer)ngrams.get(word)).intValue();
		}

	}

	public static void main(String args[])
	{
		String text = "我是 一个 中国人 大家 有 什么 问题 可以 找 我！";
		System.out.println((new StringBuilder()).append("text=").append(text).toString());
	}
}
