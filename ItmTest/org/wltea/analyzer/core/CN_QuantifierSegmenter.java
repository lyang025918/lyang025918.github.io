// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CN_QuantifierSegmenter.java

package org.wltea.analyzer.core;

import java.util.*;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.dic.Hit;

// Referenced classes of package org.wltea.analyzer.core:
//			ISegmenter, AnalyzeContext, Lexeme, QuickSortSet

class CN_QuantifierSegmenter
	implements ISegmenter
{

	static final String SEGMENTER_NAME = "QUAN_SEGMENTER";
	private static String Chn_Num;
	private static Set ChnNumberChars;
	private int nStart;
	private int nEnd;
	private List countHits;

	CN_QuantifierSegmenter()
	{
		nStart = -1;
		nEnd = -1;
		countHits = new LinkedList();
	}

	public void analyze(AnalyzeContext context)
	{
		processCNumber(context);
		processCount(context);
		if (nStart == -1 && nEnd == -1 && countHits.isEmpty())
			context.unlockBuffer("QUAN_SEGMENTER");
		else
			context.lockBuffer("QUAN_SEGMENTER");
	}

	public void reset()
	{
		nStart = -1;
		nEnd = -1;
		countHits.clear();
	}

	private void processCNumber(AnalyzeContext context)
	{
		if (nStart == -1 && nEnd == -1)
		{
			if (4 == context.getCurrentCharType() && ChnNumberChars.contains(Character.valueOf(context.getCurrentChar())))
			{
				nStart = context.getCursor();
				nEnd = context.getCursor();
			}
		} else
		if (4 == context.getCurrentCharType() && ChnNumberChars.contains(Character.valueOf(context.getCurrentChar())))
		{
			nEnd = context.getCursor();
		} else
		{
			outputNumLexeme(context);
			nStart = -1;
			nEnd = -1;
		}
		if (context.isBufferConsumed() && nStart != -1 && nEnd != -1)
		{
			outputNumLexeme(context);
			nStart = -1;
			nEnd = -1;
		}
	}

	private void processCount(AnalyzeContext context)
	{
		if (!needCountScan(context))
			return;
		if (4 == context.getCurrentCharType())
		{
			if (!countHits.isEmpty())
			{
				Hit tmpArray[] = (Hit[])countHits.toArray(new Hit[countHits.size()]);
				Hit ahit[];
				int j = (ahit = tmpArray).length;
				for (int i = 0; i < j; i++)
				{
					Hit hit = ahit[i];
					hit = Dictionary.getSingleton().matchWithHit(context.getSegmentBuff(), context.getCursor(), hit);
					if (hit.isMatch())
					{
						Lexeme newLexeme = new Lexeme(context.getBufferOffset(), hit.getBegin(), (context.getCursor() - hit.getBegin()) + 1, 32);
						context.addLexeme(newLexeme);
						if (!hit.isPrefix())
							countHits.remove(hit);
					} else
					if (hit.isUnmatch())
						countHits.remove(hit);
				}

			}
			Hit singleCharHit = Dictionary.getSingleton().matchInQuantifierDict(context.getSegmentBuff(), context.getCursor(), 1);
			if (singleCharHit.isMatch())
			{
				Lexeme newLexeme = new Lexeme(context.getBufferOffset(), context.getCursor(), 1, 32);
				context.addLexeme(newLexeme);
				if (singleCharHit.isPrefix())
					countHits.add(singleCharHit);
			} else
			if (singleCharHit.isPrefix())
				countHits.add(singleCharHit);
		} else
		{
			countHits.clear();
		}
		if (context.isBufferConsumed())
			countHits.clear();
	}

	private boolean needCountScan(AnalyzeContext context)
	{
		if (nStart != -1 && nEnd != -1 || !countHits.isEmpty())
			return true;
		if (!context.getOrgLexemes().isEmpty())
		{
			Lexeme l = context.getOrgLexemes().peekLast();
			if ((16 == l.getLexemeType() || 2 == l.getLexemeType()) && l.getBegin() + l.getLength() == context.getCursor())
				return true;
		}
		return false;
	}

	private void outputNumLexeme(AnalyzeContext context)
	{
		if (nStart > -1 && nEnd > -1)
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), nStart, (nEnd - nStart) + 1, 16);
			context.addLexeme(newLexeme);
		}
	}

	static 
	{
		Chn_Num = "Ò»¶þÁ½ÈýËÄÎåÁùÆß°Ë¾ÅÊ®ÁãÒ¼·¡ÈþËÁÎéÂ½Æâ°Æ¾ÁÊ°°ÙÇ§ÍòÒÚÊ°°ÛÇªÈfƒ|Õ×Ø¦Ø¥";
		ChnNumberChars = new HashSet();
		char ca[] = Chn_Num.toCharArray();
		char ac[];
		int j = (ac = ca).length;
		for (int i = 0; i < j; i++)
		{
			char nChar = ac[i];
			ChnNumberChars.add(Character.valueOf(nChar));
		}

	}
}
