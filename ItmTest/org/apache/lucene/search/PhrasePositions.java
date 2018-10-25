// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhrasePositions.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.index.Term;

final class PhrasePositions
{

	int doc;
	int position;
	int count;
	int offset;
	final int ord;
	final DocsAndPositionsEnum postings;
	PhrasePositions next;
	int rptGroup;
	int rptInd;
	final Term terms[];

	PhrasePositions(DocsAndPositionsEnum postings, int o, int ord, Term terms[])
	{
		rptGroup = -1;
		this.postings = postings;
		offset = o;
		this.ord = ord;
		this.terms = terms;
	}

	final boolean next()
		throws IOException
	{
		doc = postings.nextDoc();
		return doc != 0x7fffffff;
	}

	final boolean skipTo(int target)
		throws IOException
	{
		doc = postings.advance(target);
		return doc != 0x7fffffff;
	}

	final void firstPosition()
		throws IOException
	{
		count = postings.freq();
		nextPosition();
	}

	final boolean nextPosition()
		throws IOException
	{
		if (count-- > 0)
		{
			position = postings.nextPosition() - offset;
			return true;
		} else
		{
			return false;
		}
	}

	public String toString()
	{
		String s = (new StringBuilder()).append("d:").append(doc).append(" o:").append(offset).append(" p:").append(position).append(" c:").append(count).toString();
		if (rptGroup >= 0)
			s = (new StringBuilder()).append(s).append(" rpt:").append(rptGroup).append(",i").append(rptInd).toString();
		return s;
	}
}
