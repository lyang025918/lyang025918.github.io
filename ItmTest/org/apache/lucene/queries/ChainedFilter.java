// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChainedFilter.java

package org.apache.lucene.queries;

import java.io.IOException;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;
import org.apache.lucene.util.*;

public class ChainedFilter extends Filter
{

	public static final int OR = 0;
	public static final int AND = 1;
	public static final int ANDNOT = 2;
	public static final int XOR = 3;
	public static final int DEFAULT = 0;
	private Filter chain[];
	private int logicArray[];
	private int logic;

	public ChainedFilter(Filter chain[])
	{
		this.chain = null;
		logic = -1;
		this.chain = chain;
	}

	public ChainedFilter(Filter chain[], int logicArray[])
	{
		this.chain = null;
		logic = -1;
		this.chain = chain;
		this.logicArray = logicArray;
	}

	public ChainedFilter(Filter chain[], int logic)
	{
		this.chain = null;
		this.logic = -1;
		this.chain = chain;
		this.logic = logic;
	}

	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
		throws IOException
	{
		int index[] = new int[1];
		index[0] = 0;
		if (logic != -1)
			return BitsFilteredDocIdSet.wrap(getDocIdSet(context, logic, index), acceptDocs);
		if (logicArray != null)
			return BitsFilteredDocIdSet.wrap(getDocIdSet(context, logicArray, index), acceptDocs);
		else
			return BitsFilteredDocIdSet.wrap(getDocIdSet(context, 0, index), acceptDocs);
	}

	private DocIdSetIterator getDISI(Filter filter, AtomicReaderContext context)
		throws IOException
	{
		DocIdSet docIdSet = filter.getDocIdSet(context, null);
		if (docIdSet == null)
			return DocIdSet.EMPTY_DOCIDSET.iterator();
		DocIdSetIterator iter = docIdSet.iterator();
		if (iter == null)
			return DocIdSet.EMPTY_DOCIDSET.iterator();
		else
			return iter;
	}

	private OpenBitSetDISI initialResult(AtomicReaderContext context, int logic, int index[])
		throws IOException
	{
		AtomicReader reader = context.reader();
		OpenBitSetDISI result;
		if (logic == 1)
		{
			result = new OpenBitSetDISI(getDISI(chain[index[0]], context), reader.maxDoc());
			index[0]++;
		} else
		if (logic == 2)
		{
			result = new OpenBitSetDISI(getDISI(chain[index[0]], context), reader.maxDoc());
			result.flip(0L, reader.maxDoc());
			index[0]++;
		} else
		{
			result = new OpenBitSetDISI(reader.maxDoc());
		}
		return result;
	}

	private DocIdSet getDocIdSet(AtomicReaderContext context, int logic, int index[])
		throws IOException
	{
		OpenBitSetDISI result = initialResult(context, logic, index);
		for (; index[0] < chain.length; index[0]++)
			doChain(result, logic, chain[index[0]].getDocIdSet(context, null));

		return result;
	}

	private DocIdSet getDocIdSet(AtomicReaderContext context, int logic[], int index[])
		throws IOException
	{
		if (logic.length != chain.length)
			throw new IllegalArgumentException("Invalid number of elements in logic array");
		OpenBitSetDISI result = initialResult(context, logic[0], index);
		for (; index[0] < chain.length; index[0]++)
			doChain(result, logic[index[0]], chain[index[0]].getDocIdSet(context, null));

		return result;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ChainedFilter: [");
		Filter arr$[] = chain;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Filter aChain = arr$[i$];
			sb.append(aChain);
			sb.append(' ');
		}

		sb.append(']');
		return sb.toString();
	}

	private void doChain(OpenBitSetDISI result, int logic, DocIdSet dis)
		throws IOException
	{
		if (dis instanceof OpenBitSet)
		{
			switch (logic)
			{
			case 0: // '\0'
				result.or((OpenBitSet)dis);
				break;

			case 1: // '\001'
				result.and((OpenBitSet)dis);
				break;

			case 2: // '\002'
				result.andNot((OpenBitSet)dis);
				break;

			case 3: // '\003'
				result.xor((OpenBitSet)dis);
				break;

			default:
				doChain(result, 0, dis);
				break;
			}
		} else
		{
			DocIdSetIterator disi;
			if (dis == null)
			{
				disi = DocIdSet.EMPTY_DOCIDSET.iterator();
			} else
			{
				disi = dis.iterator();
				if (disi == null)
					disi = DocIdSet.EMPTY_DOCIDSET.iterator();
			}
			switch (logic)
			{
			case 0: // '\0'
				result.inPlaceOr(disi);
				break;

			case 1: // '\001'
				result.inPlaceAnd(disi);
				break;

			case 2: // '\002'
				result.inPlaceNot(disi);
				break;

			case 3: // '\003'
				result.inPlaceXor(disi);
				break;

			default:
				doChain(result, 0, dis);
				break;
			}
		}
	}
}
