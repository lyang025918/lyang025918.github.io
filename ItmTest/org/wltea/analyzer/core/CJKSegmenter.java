// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKSegmenter.java

package org.wltea.analyzer.core;

import java.util.LinkedList;
import java.util.List;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.dic.Hit;

// Referenced classes of package org.wltea.analyzer.core:
//			ISegmenter, AnalyzeContext, Lexeme

class CJKSegmenter
	implements ISegmenter
{

	static final String SEGMENTER_NAME = "CJK_SEGMENTER";
	private List tmpHits;

	CJKSegmenter()
	{
		tmpHits = new LinkedList();
	}

	public void analyze(AnalyzeContext context)
	{
		if (context.getCurrentCharType() != 0)
		{
			if (!tmpHits.isEmpty())
			{
				Hit tmpArray[] = (Hit[])tmpHits.toArray(new Hit[tmpHits.size()]);
				Hit ahit[];
				int j = (ahit = tmpArray).length;
				for (int i = 0; i < j; i++)
				{
					Hit hit = ahit[i];
					hit = Dictionary.getSingleton().matchWithHit(context.getSegmentBuff(), context.getCursor(), hit);
					if (hit.isMatch())
					{
						Lexeme newLexeme = new Lexeme(context.getBufferOffset(), hit.getBegin(), (context.getCursor() - hit.getBegin()) + 1, 4);
						context.addLexeme(newLexeme);
						if (!hit.isPrefix())
							tmpHits.remove(hit);
					} else
					if (hit.isUnmatch())
						tmpHits.remove(hit);
				}

			}
			Hit singleCharHit = Dictionary.getSingleton().matchInMainDict(context.getSegmentBuff(), context.getCursor(), 1);
			if (singleCharHit.isMatch())
			{
				Lexeme newLexeme = new Lexeme(context.getBufferOffset(), context.getCursor(), 1, 4);
				context.addLexeme(newLexeme);
				if (singleCharHit.isPrefix())
					tmpHits.add(singleCharHit);
			} else
			if (singleCharHit.isPrefix())
				tmpHits.add(singleCharHit);
		} else
		{
			tmpHits.clear();
		}
		if (context.isBufferConsumed())
			tmpHits.clear();
		if (tmpHits.size() == 0)
			context.unlockBuffer("CJK_SEGMENTER");
		else
			context.lockBuffer("CJK_SEGMENTER");
	}

	public void reset()
	{
		tmpHits.clear();
	}
}
