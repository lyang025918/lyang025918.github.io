// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExactPhraseScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.index.DocsAndPositionsEnum;
import org.apache.lucene.search.similarities.Similarity;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Weight, PhraseQuery

final class ExactPhraseScorer extends Scorer
{
	private static final class ChunkState
	{

		final DocsAndPositionsEnum posEnum;
		final int offset;
		final boolean useAdvance;
		int posUpto;
		int posLimit;
		int pos;
		int lastPos;

		public ChunkState(DocsAndPositionsEnum posEnum, int offset, boolean useAdvance)
		{
			this.posEnum = posEnum;
			this.offset = offset;
			this.useAdvance = useAdvance;
		}
	}


	private final int endMinus1;
	private static final int CHUNK = 4096;
	private int gen;
	private final int counts[] = new int[4096];
	private final int gens[] = new int[4096];
	boolean noDocs;
	private final ChunkState chunkStates[];
	private int docID;
	private int freq;
	private final org.apache.lucene.search.similarities.Similarity.ExactSimScorer docScorer;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/ExactPhraseScorer.desiredAssertionStatus();

	ExactPhraseScorer(Weight weight, PhraseQuery.PostingsAndFreq postings[], org.apache.lucene.search.similarities.Similarity.ExactSimScorer docScorer)
		throws IOException
	{
		super(weight);
		docID = -1;
		this.docScorer = docScorer;
		chunkStates = new ChunkState[postings.length];
		endMinus1 = postings.length - 1;
		for (int i = 0; i < postings.length; i++)
		{
			boolean useAdvance = postings[i].docFreq > 5 * postings[0].docFreq;
			chunkStates[i] = new ChunkState(postings[i].postings, -postings[i].position, useAdvance);
			if (i > 0 && postings[i].postings.nextDoc() == 0x7fffffff)
			{
				noDocs = true;
				return;
			}
		}

	}

	public int nextDoc()
		throws IOException
	{
		do
		{
			int doc;
			int i;
			do
			{
				doc = chunkStates[0].posEnum.nextDoc();
				if (doc == 0x7fffffff)
				{
					docID = doc;
					return doc;
				}
				i = 1;
				do
				{
					if (i >= chunkStates.length)
						break;
					ChunkState cs = chunkStates[i];
					int doc2 = cs.posEnum.docID();
					if (cs.useAdvance)
					{
						if (doc2 < doc)
							doc2 = cs.posEnum.advance(doc);
					} else
					{
						int iter = 0;
						do
						{
							if (doc2 >= doc)
								break;
							if (++iter == 50)
							{
								doc2 = cs.posEnum.advance(doc);
								break;
							}
							doc2 = cs.posEnum.nextDoc();
						} while (true);
					}
					if (doc2 > doc)
						break;
					i++;
				} while (true);
			} while (i != chunkStates.length);
			docID = doc;
			freq = phraseFreq();
		} while (freq == 0);
		return docID;
	}

	public int advance(int target)
		throws IOException
	{
		int doc = chunkStates[0].posEnum.advance(target);
		if (doc == 0x7fffffff)
		{
			docID = 0x7fffffff;
			return doc;
		}
		do
		{
			int i = 1;
			do
			{
				if (i >= chunkStates.length)
					break;
				int doc2 = chunkStates[i].posEnum.docID();
				if (doc2 < doc)
					doc2 = chunkStates[i].posEnum.advance(doc);
				if (doc2 > doc)
					break;
				i++;
			} while (true);
			if (i == chunkStates.length)
			{
				docID = doc;
				freq = phraseFreq();
				if (freq != 0)
					return docID;
			}
			doc = chunkStates[0].posEnum.nextDoc();
		} while (doc != 0x7fffffff);
		docID = doc;
		return doc;
	}

	public String toString()
	{
		return (new StringBuilder()).append("ExactPhraseScorer(").append(weight).append(")").toString();
	}

	public float freq()
	{
		return (float)freq;
	}

	public int docID()
	{
		return docID;
	}

	public float score()
	{
		return docScorer.score(docID, freq);
	}

	private int phraseFreq()
		throws IOException
	{
		freq = 0;
		for (int i = 0; i < chunkStates.length; i++)
		{
			ChunkState cs = chunkStates[i];
			cs.posLimit = cs.posEnum.freq();
			cs.pos = cs.offset + cs.posEnum.nextPosition();
			cs.posUpto = 1;
			cs.lastPos = -1;
		}

		int chunkStart = 0;
		int chunkEnd = 4096;
		boolean end = false;
		while (!end) 
		{
			gen++;
			if (gen == 0)
			{
				Arrays.fill(gens, 0);
				gen++;
			}
			ChunkState cs = chunkStates[0];
			do
			{
				if (cs.pos >= chunkEnd)
					break;
				if (cs.pos > cs.lastPos)
				{
					cs.lastPos = cs.pos;
					int posIndex = cs.pos - chunkStart;
					counts[posIndex] = 1;
					if (!$assertionsDisabled && gens[posIndex] == gen)
						throw new AssertionError();
					gens[posIndex] = gen;
				}
				if (cs.posUpto == cs.posLimit)
				{
					end = true;
					break;
				}
				cs.posUpto++;
				cs.pos = cs.offset + cs.posEnum.nextPosition();
			} while (true);
			boolean any = true;
			int t = 1;
			do
			{
				if (t >= endMinus1)
					break;
				ChunkState cs = chunkStates[t];
				any = false;
				do
				{
					if (cs.pos >= chunkEnd)
						break;
					if (cs.pos > cs.lastPos)
					{
						cs.lastPos = cs.pos;
						int posIndex = cs.pos - chunkStart;
						if (posIndex >= 0 && gens[posIndex] == gen && counts[posIndex] == t)
						{
							counts[posIndex]++;
							any = true;
						}
					}
					if (cs.posUpto == cs.posLimit)
					{
						end = true;
						break;
					}
					cs.posUpto++;
					cs.pos = cs.offset + cs.posEnum.nextPosition();
				} while (true);
				if (!any)
					break;
				t++;
			} while (true);
			if (!any)
			{
				chunkStart += 4096;
				chunkEnd += 4096;
			} else
			{
				ChunkState cs = chunkStates[endMinus1];
				do
				{
					if (cs.pos >= chunkEnd)
						break;
					if (cs.pos > cs.lastPos)
					{
						cs.lastPos = cs.pos;
						int posIndex = cs.pos - chunkStart;
						if (posIndex >= 0 && gens[posIndex] == gen && counts[posIndex] == endMinus1)
							freq++;
					}
					if (cs.posUpto == cs.posLimit)
					{
						end = true;
						break;
					}
					cs.posUpto++;
					cs.pos = cs.offset + cs.posEnum.nextPosition();
				} while (true);
				chunkStart += 4096;
				chunkEnd += 4096;
			}
		}
		return freq;
	}

}
