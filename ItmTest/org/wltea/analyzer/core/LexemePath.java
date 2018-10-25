// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LexemePath.java

package org.wltea.analyzer.core;


// Referenced classes of package org.wltea.analyzer.core:
//			QuickSortSet, Lexeme

class LexemePath extends QuickSortSet
	implements Comparable
{

	private int pathBegin;
	private int pathEnd;
	private int payloadLength;

	LexemePath()
	{
		pathBegin = -1;
		pathEnd = -1;
		payloadLength = 0;
	}

	boolean addCrossLexeme(Lexeme lexeme)
	{
		if (isEmpty())
		{
			addLexeme(lexeme);
			pathBegin = lexeme.getBegin();
			pathEnd = lexeme.getBegin() + lexeme.getLength();
			payloadLength += lexeme.getLength();
			return true;
		}
		if (checkCross(lexeme))
		{
			addLexeme(lexeme);
			if (lexeme.getBegin() + lexeme.getLength() > pathEnd)
				pathEnd = lexeme.getBegin() + lexeme.getLength();
			payloadLength = pathEnd - pathBegin;
			return true;
		} else
		{
			return false;
		}
	}

	boolean addNotCrossLexeme(Lexeme lexeme)
	{
		if (isEmpty())
		{
			addLexeme(lexeme);
			pathBegin = lexeme.getBegin();
			pathEnd = lexeme.getBegin() + lexeme.getLength();
			payloadLength += lexeme.getLength();
			return true;
		}
		if (checkCross(lexeme))
		{
			return false;
		} else
		{
			addLexeme(lexeme);
			payloadLength += lexeme.getLength();
			Lexeme head = peekFirst();
			pathBegin = head.getBegin();
			Lexeme tail = peekLast();
			pathEnd = tail.getBegin() + tail.getLength();
			return true;
		}
	}

	Lexeme removeTail()
	{
		Lexeme tail = pollLast();
		if (isEmpty())
		{
			pathBegin = -1;
			pathEnd = -1;
			payloadLength = 0;
		} else
		{
			payloadLength -= tail.getLength();
			Lexeme newTail = peekLast();
			pathEnd = newTail.getBegin() + newTail.getLength();
		}
		return tail;
	}

	boolean checkCross(Lexeme lexeme)
	{
		return lexeme.getBegin() >= pathBegin && lexeme.getBegin() < pathEnd || pathBegin >= lexeme.getBegin() && pathBegin < lexeme.getBegin() + lexeme.getLength();
	}

	int getPathBegin()
	{
		return pathBegin;
	}

	int getPathEnd()
	{
		return pathEnd;
	}

	int getPayloadLength()
	{
		return payloadLength;
	}

	int getPathLength()
	{
		return pathEnd - pathBegin;
	}

	int getXWeight()
	{
		int product = 1;
		for (QuickSortSet.Cell c = getHead(); c != null && c.getLexeme() != null; c = c.getNext())
			product *= c.getLexeme().getLength();

		return product;
	}

	int getPWeight()
	{
		int pWeight = 0;
		int p = 0;
		for (QuickSortSet.Cell c = getHead(); c != null && c.getLexeme() != null; c = c.getNext())
		{
			p++;
			pWeight += p * c.getLexeme().getLength();
		}

		return pWeight;
	}

	LexemePath copy()
	{
		LexemePath theCopy = new LexemePath();
		theCopy.pathBegin = pathBegin;
		theCopy.pathEnd = pathEnd;
		theCopy.payloadLength = payloadLength;
		for (QuickSortSet.Cell c = getHead(); c != null && c.getLexeme() != null; c = c.getNext())
			theCopy.addLexeme(c.getLexeme());

		return theCopy;
	}

	public int compareTo(LexemePath o)
	{
		if (payloadLength > o.payloadLength)
			return -1;
		if (payloadLength < o.payloadLength)
			return 1;
		if (size() < o.size())
			return -1;
		if (size() > o.size())
			return 1;
		if (getPathLength() > o.getPathLength())
			return -1;
		if (getPathLength() < o.getPathLength())
			return 1;
		if (pathEnd > o.pathEnd)
			return -1;
		if (pathEnd < o.pathEnd)
			return 1;
		if (getXWeight() > o.getXWeight())
			return -1;
		if (getXWeight() < o.getXWeight())
			return 1;
		if (getPWeight() > o.getPWeight())
			return -1;
		return getPWeight() >= o.getPWeight() ? 0 : 1;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("pathBegin  : ").append(pathBegin).append("\r\n");
		sb.append("pathEnd  : ").append(pathEnd).append("\r\n");
		sb.append("payloadLength  : ").append(payloadLength).append("\r\n");
		for (QuickSortSet.Cell head = getHead(); head != null; head = head.getNext())
			sb.append("lexeme : ").append(head.getLexeme()).append("\r\n");

		return sb.toString();
	}

	public volatile int compareTo(Object obj)
	{
		return compareTo((LexemePath)obj);
	}
}
