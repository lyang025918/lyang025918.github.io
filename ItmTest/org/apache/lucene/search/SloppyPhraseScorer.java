// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SloppyPhraseScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.OpenBitSet;

// Referenced classes of package org.apache.lucene.search:
//			PhraseScorer, PhraseQueue, PhrasePositions, Weight, 
//			DocIdSetIterator, PhraseQuery

final class SloppyPhraseScorer extends PhraseScorer
{

	private final int slop;
	private final int numPostings;
	private final PhraseQueue pq;
	private int end;
	private boolean hasRpts;
	private boolean checkedRpts;
	private boolean hasMultiTermRpts;
	private PhrasePositions rptGroups[][];
	private PhrasePositions rptStack[];
	static final boolean $assertionsDisabled = !org/apache/lucene/search/SloppyPhraseScorer.desiredAssertionStatus();

	SloppyPhraseScorer(Weight weight, PhraseQuery.PostingsAndFreq postings[], int slop, org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer)
	{
		super(weight, postings, docScorer);
		this.slop = slop;
		numPostings = postings != null ? postings.length : 0;
		pq = new PhraseQueue(postings.length);
	}

	protected float phraseFreq()
		throws IOException
	{
		if (!initPhrasePositions())
			return 0.0F;
		float freq = 0.0F;
		PhrasePositions pp = (PhrasePositions)pq.pop();
		int matchLength = end - pp.position;
		int next = ((PhrasePositions)pq.top()).position;
		do
		{
			if (!advancePP(pp) || hasRpts && !advanceRpts(pp))
				break;
			if (pp.position > next)
			{
				if (matchLength <= slop)
					freq += docScorer.computeSlopFactor(matchLength);
				pq.add(pp);
				pp = (PhrasePositions)pq.pop();
				next = ((PhrasePositions)pq.top()).position;
				matchLength = end - pp.position;
			} else
			{
				int matchLength2 = end - pp.position;
				if (matchLength2 < matchLength)
					matchLength = matchLength2;
			}
		} while (true);
		if (matchLength <= slop)
			freq += docScorer.computeSlopFactor(matchLength);
		return freq;
	}

	private boolean advancePP(PhrasePositions pp)
		throws IOException
	{
		if (!pp.nextPosition())
			return false;
		if (pp.position > end)
			end = pp.position;
		return true;
	}

	private boolean advanceRpts(PhrasePositions pp)
		throws IOException
	{
		if (pp.rptGroup < 0)
			return true;
		PhrasePositions rg[] = rptGroups[pp.rptGroup];
		OpenBitSet bits = new OpenBitSet(rg.length);
		int k0 = pp.rptInd;
		do
		{
			int k;
			if ((k = collide(pp)) < 0)
				break;
			pp = lesser(pp, rg[k]);
			if (!advancePP(pp))
				return false;
			if (k != k0)
				bits.set(k);
		} while (true);
		int n = 0;
		do
		{
			if (bits.cardinality() <= 0L)
				break;
			PhrasePositions pp2 = (PhrasePositions)pq.pop();
			rptStack[n++] = pp2;
			if (pp2.rptGroup >= 0 && bits.get(pp2.rptInd))
				bits.clear(pp2.rptInd);
		} while (true);
		for (int i = n - 1; i >= 0; i--)
			pq.add(rptStack[i]);

		return true;
	}

	private PhrasePositions lesser(PhrasePositions pp, PhrasePositions pp2)
	{
		if (pp.position < pp2.position || pp.position == pp2.position && pp.offset < pp2.offset)
			return pp;
		else
			return pp2;
	}

	private int collide(PhrasePositions pp)
	{
		int tpPos = tpPos(pp);
		PhrasePositions rg[] = rptGroups[pp.rptGroup];
		for (int i = 0; i < rg.length; i++)
		{
			PhrasePositions pp2 = rg[i];
			if (pp2 != pp && tpPos(pp2) == tpPos)
				return pp2.rptInd;
		}

		return -1;
	}

	private boolean initPhrasePositions()
		throws IOException
	{
		end = 0x80000000;
		if (!checkedRpts)
			return initFirstTime();
		if (!hasRpts)
		{
			initSimple();
			return true;
		} else
		{
			return initComplex();
		}
	}

	private void initSimple()
		throws IOException
	{
		pq.clear();
		PhrasePositions pp = min;
		for (PhrasePositions prev = null; prev != max;)
		{
			pp.firstPosition();
			if (pp.position > end)
				end = pp.position;
			pq.add(pp);
			pp = (prev = pp).next;
		}

	}

	private boolean initComplex()
		throws IOException
	{
		placeFirstPositions();
		if (!advanceRepeatGroups())
		{
			return false;
		} else
		{
			fillQueue();
			return true;
		}
	}

	private void placeFirstPositions()
		throws IOException
	{
		PhrasePositions pp = min;
		for (PhrasePositions prev = null; prev != max;)
		{
			pp.firstPosition();
			pp = (prev = pp).next;
		}

	}

	private void fillQueue()
	{
		pq.clear();
		PhrasePositions pp = min;
		for (PhrasePositions prev = null; prev != max;)
		{
			if (pp.position > end)
				end = pp.position;
			pq.add(pp);
			pp = (prev = pp).next;
		}

	}

	private boolean advanceRepeatGroups()
		throws IOException
	{
		PhrasePositions arr$[][] = rptGroups;
		int len$ = arr$.length;
label0:
		for (int i$ = 0; i$ < len$; i$++)
		{
			PhrasePositions rg[] = arr$[i$];
			if (hasMultiTermRpts)
			{
				int i = 0;
				do
				{
					if (i >= rg.length)
						continue label0;
					int incr = 1;
					PhrasePositions pp = rg[i];
					do
					{
						int k;
						if ((k = collide(pp)) < 0)
							break;
						PhrasePositions pp2 = lesser(pp, rg[k]);
						if (!advancePP(pp2))
							return false;
						if (pp2.rptInd >= i)
							continue;
						incr = 0;
						break;
					} while (true);
					i += incr;
				} while (true);
			} else
			{
				int j = 1;
				do
				{
					if (j >= rg.length)
						continue label0;
					for (int k = 0; k < j; k++)
						if (!rg[j].nextPosition())
							return false;

					j++;
				} while (true);
			}
		}

		return true;
	}

	private boolean initFirstTime()
		throws IOException
	{
		checkedRpts = true;
		placeFirstPositions();
		LinkedHashMap rptTerms = repeatingTerms();
		hasRpts = !rptTerms.isEmpty();
		if (hasRpts)
		{
			rptStack = new PhrasePositions[numPostings];
			ArrayList rgs = gatherRptGroups(rptTerms);
			sortRptGroups(rgs);
			if (!advanceRepeatGroups())
				return false;
		}
		fillQueue();
		return true;
	}

	private void sortRptGroups(ArrayList rgs)
	{
		rptGroups = new PhrasePositions[rgs.size()][];
		Comparator cmprtr = new Comparator() {

			final SloppyPhraseScorer this$0;

			public int compare(PhrasePositions pp1, PhrasePositions pp2)
			{
				return pp1.offset - pp2.offset;
			}

			public volatile int compare(Object x0, Object x1)
			{
				return compare((PhrasePositions)x0, (PhrasePositions)x1);
			}

			
			{
				this$0 = SloppyPhraseScorer.this;
				super();
			}
		};
		for (int i = 0; i < rptGroups.length; i++)
		{
			PhrasePositions rg[] = (PhrasePositions[])((ArrayList)rgs.get(i)).toArray(new PhrasePositions[0]);
			Arrays.sort(rg, cmprtr);
			rptGroups[i] = rg;
			for (int j = 0; j < rg.length; j++)
				rg[j].rptInd = j;

		}

	}

	private ArrayList gatherRptGroups(LinkedHashMap rptTerms)
		throws IOException
	{
		PhrasePositions rpp[] = repeatingPPs(rptTerms);
		ArrayList res = new ArrayList();
		if (!hasMultiTermRpts)
		{
			for (int i = 0; i < rpp.length; i++)
			{
				PhrasePositions pp = rpp[i];
				if (pp.rptGroup < 0)
				{
					int tpPos = tpPos(pp);
					for (int j = i + 1; j < rpp.length; j++)
					{
						PhrasePositions pp2 = rpp[j];
						if (pp2.rptGroup >= 0 || pp2.offset == pp.offset || tpPos(pp2) != tpPos)
							continue;
						int g = pp.rptGroup;
						if (g < 0)
						{
							g = res.size();
							pp.rptGroup = g;
							ArrayList rl = new ArrayList(2);
							rl.add(pp);
							res.add(rl);
						}
						pp2.rptGroup = g;
						((ArrayList)res.get(g)).add(pp2);
					}

				}
			}

		} else
		{
			ArrayList tmp = new ArrayList();
			ArrayList bb = ppTermsBitSets(rpp, rptTerms);
			unionTermGroups(bb);
			HashMap tg = termGroups(rptTerms, bb);
			HashSet distinctGroupIDs = new HashSet(tg.values());
			for (int i = 0; i < distinctGroupIDs.size(); i++)
				tmp.add(new HashSet());

			PhrasePositions arr$[] = rpp;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				PhrasePositions pp = arr$[i$];
				Term arr$[] = pp.terms;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Term t = arr$[i$];
					if (!rptTerms.containsKey(t))
						continue;
					int g = ((Integer)tg.get(t)).intValue();
					((HashSet)tmp.get(g)).add(pp);
					if (!$assertionsDisabled && pp.rptGroup != -1 && pp.rptGroup != g)
						throw new AssertionError();
					pp.rptGroup = g;
				}

			}

			HashSet hs;
			for (Iterator i$ = tmp.iterator(); i$.hasNext(); res.add(new ArrayList(hs)))
				hs = (HashSet)i$.next();

		}
		return res;
	}

	private final int tpPos(PhrasePositions pp)
	{
		return pp.position + pp.offset;
	}

	private LinkedHashMap repeatingTerms()
	{
		LinkedHashMap tord = new LinkedHashMap();
		HashMap tcnt = new HashMap();
		PhrasePositions pp = min;
		for (PhrasePositions prev = null; prev != max;)
		{
			Term arr$[] = pp.terms;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Term t = arr$[i$];
				Integer cnt0 = (Integer)tcnt.get(t);
				Integer cnt = cnt0 != null ? new Integer(1 + cnt0.intValue()) : new Integer(1);
				tcnt.put(t, cnt);
				if (cnt.intValue() == 2)
					tord.put(t, Integer.valueOf(tord.size()));
			}

			pp = (prev = pp).next;
		}

		return tord;
	}

	private PhrasePositions[] repeatingPPs(HashMap rptTerms)
	{
		ArrayList rp = new ArrayList();
		PhrasePositions pp = min;
label0:
		for (PhrasePositions prev = null; prev != max; pp = (prev = pp).next)
		{
			Term arr$[] = pp.terms;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					continue label0;
				Term t = arr$[i$];
				if (rptTerms.containsKey(t))
				{
					rp.add(pp);
					hasMultiTermRpts |= pp.terms.length > 1;
					continue label0;
				}
				i$++;
			} while (true);
		}

		return (PhrasePositions[])rp.toArray(new PhrasePositions[0]);
	}

	private ArrayList ppTermsBitSets(PhrasePositions rpp[], HashMap tord)
	{
		ArrayList bb = new ArrayList(rpp.length);
		PhrasePositions arr$[] = rpp;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			PhrasePositions pp = arr$[i$];
			OpenBitSet b = new OpenBitSet(tord.size());
			Term arr$[] = pp.terms;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Term t = arr$[i$];
				Integer ord;
				if ((ord = (Integer)tord.get(t)) != null)
					b.set(ord.intValue());
			}

			bb.add(b);
		}

		return bb;
	}

	private void unionTermGroups(ArrayList bb)
	{
		int incr;
		for (int i = 0; i < bb.size() - 1; i += incr)
		{
			incr = 1;
			for (int j = i + 1; j < bb.size();)
				if (((OpenBitSet)bb.get(i)).intersects((OpenBitSet)bb.get(j)))
				{
					((OpenBitSet)bb.get(i)).union((OpenBitSet)bb.get(j));
					bb.remove(j);
					incr = 0;
				} else
				{
					j++;
				}

		}

	}

	private HashMap termGroups(LinkedHashMap tord, ArrayList bb)
		throws IOException
	{
		HashMap tg = new HashMap();
		Term t[] = (Term[])tord.keySet().toArray(new Term[0]);
		for (int i = 0; i < bb.size(); i++)
		{
			DocIdSetIterator bits = ((OpenBitSet)bb.get(i)).iterator();
			int ord;
			while ((ord = bits.nextDoc()) != 0x7fffffff) 
				tg.put(t[ord], Integer.valueOf(i));
		}

		return tg;
	}

}
