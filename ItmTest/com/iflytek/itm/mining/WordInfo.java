// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordInfo.java

package com.iflytek.itm.mining;

import java.util.List;
import org.apache.log4j.Logger;

public class WordInfo
{

	public String word;
	public String topField;
	public float score;
	public int docFreq;
	public int termFreq;
	public int totalTermCount;
	public int totalDocCount;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/WordInfo);

	public WordInfo()
	{
	}

	public static void logWordInfoList(List finalWords)
	{
		for (int i = 0; i < finalWords.size(); i++)
		{
			WordInfo info = (WordInfo)finalWords.get(i);
			logger.info((new StringBuilder()).append("word=").append(info.word).append(", topField=").append(info.topField).append(", totalWordCount=").append(info.totalTermCount).append(", termFreq=").append(info.termFreq).append(", totalDocCount=").append(info.totalDocCount).append(", docFreq=").append(info.docFreq).append(", score=").append(info.score).toString());
		}

	}

}
