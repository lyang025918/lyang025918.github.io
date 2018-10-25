// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IKArbitrator.java

package org.wltea.analyzer.core;

import java.util.Stack;
import java.util.TreeSet;

// Referenced classes of package org.wltea.analyzer.core:
//			AnalyzeContext, QuickSortSet, LexemePath, Lexeme

class IKArbitrator
{

	IKArbitrator()
	{
	}

	void process(AnalyzeContext context, boolean useSmart)
	{
		QuickSortSet orgLexemes = context.getOrgLexemes();
		Lexeme orgLexeme = orgLexemes.pollFirst();
		LexemePath crossPath = new LexemePath();
		for (; orgLexeme != null; orgLexeme = orgLexemes.pollFirst())
			if (!crossPath.addCrossLexeme(orgLexeme))
			{
				if (crossPath.size() == 1 || !useSmart)
				{
					context.addLexemePath(crossPath);
				} else
				{
					QuickSortSet.Cell headCell = crossPath.getHead();
					LexemePath judgeResult = judge(headCell, crossPath.getPathLength());
					context.addLexemePath(judgeResult);
				}
				crossPath = new LexemePath();
				crossPath.addCrossLexeme(orgLexeme);
			}

		if (crossPath.size() == 1 || !useSmart)
		{
			context.addLexemePath(crossPath);
		} else
		{
			QuickSortSet.Cell headCell = crossPath.getHead();
			LexemePath judgeResult = judge(headCell, crossPath.getPathLength());
			context.addLexemePath(judgeResult);
		}
	}

	private LexemePath judge(QuickSortSet.Cell lexemeCell, int fullTextLength)
	{
		TreeSet pathOptions = new TreeSet();
		LexemePath option = new LexemePath();
		Stack lexemeStack = forwardPath(lexemeCell, option);
		pathOptions.add(option.copy());
		QuickSortSet.Cell c = null;
		for (; !lexemeStack.isEmpty(); pathOptions.add(option.copy()))
		{
			c = (QuickSortSet.Cell)lexemeStack.pop();
			backPath(c.getLexeme(), option);
			forwardPath(c, option);
		}

		return (LexemePath)pathOptions.first();
	}

	private Stack forwardPath(QuickSortSet.Cell lexemeCell, LexemePath option)
	{
		Stack conflictStack = new Stack();
		for (QuickSortSet.Cell c = lexemeCell; c != null && c.getLexeme() != null; c = c.getNext())
			if (!option.addNotCrossLexeme(c.getLexeme()))
				conflictStack.push(c);

		return conflictStack;
	}

	private void backPath(Lexeme l, LexemePath option)
	{
		for (; option.checkCross(l); option.removeTail());
	}
}
