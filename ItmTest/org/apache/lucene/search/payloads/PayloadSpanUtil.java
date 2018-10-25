// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadSpanUtil.java

package org.apache.lucene.search.payloads;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.*;

public class PayloadSpanUtil
{

	private IndexReaderContext context;

	public PayloadSpanUtil(IndexReaderContext context)
	{
		this.context = context;
	}

	public Collection getPayloadsForQuery(Query query)
		throws IOException
	{
		Collection payloads = new ArrayList();
		queryToSpanQuery(query, payloads);
		return payloads;
	}

	private void queryToSpanQuery(Query query, Collection payloads)
		throws IOException
	{
		if (query instanceof BooleanQuery)
		{
			BooleanClause queryClauses[] = ((BooleanQuery)query).getClauses();
			for (int i = 0; i < queryClauses.length; i++)
				if (!queryClauses[i].isProhibited())
					queryToSpanQuery(queryClauses[i].getQuery(), payloads);

		} else
		if (query instanceof PhraseQuery)
		{
			Term phraseQueryTerms[] = ((PhraseQuery)query).getTerms();
			SpanQuery clauses[] = new SpanQuery[phraseQueryTerms.length];
			for (int i = 0; i < phraseQueryTerms.length; i++)
				clauses[i] = new SpanTermQuery(phraseQueryTerms[i]);

			int slop = ((PhraseQuery)query).getSlop();
			boolean inorder = false;
			if (slop == 0)
				inorder = true;
			SpanNearQuery sp = new SpanNearQuery(clauses, slop, inorder);
			sp.setBoost(query.getBoost());
			getPayloads(payloads, sp);
		} else
		if (query instanceof TermQuery)
		{
			SpanTermQuery stq = new SpanTermQuery(((TermQuery)query).getTerm());
			stq.setBoost(query.getBoost());
			getPayloads(payloads, stq);
		} else
		if (query instanceof SpanQuery)
			getPayloads(payloads, (SpanQuery)query);
		else
		if (query instanceof FilteredQuery)
			queryToSpanQuery(((FilteredQuery)query).getQuery(), payloads);
		else
		if (query instanceof DisjunctionMaxQuery)
		{
			for (Iterator iterator = ((DisjunctionMaxQuery)query).iterator(); iterator.hasNext(); queryToSpanQuery((Query)iterator.next(), payloads));
		} else
		if (query instanceof MultiPhraseQuery)
		{
			MultiPhraseQuery mpq = (MultiPhraseQuery)query;
			List termArrays = mpq.getTermArrays();
			int positions[] = mpq.getPositions();
			if (positions.length > 0)
			{
				int maxPosition = positions[positions.length - 1];
				for (int i = 0; i < positions.length - 1; i++)
					if (positions[i] > maxPosition)
						maxPosition = positions[i];

				List disjunctLists[] = new List[maxPosition + 1];
				int distinctPositions = 0;
				for (int i = 0; i < termArrays.size(); i++)
				{
					Term termArray[] = (Term[])termArrays.get(i);
					List disjuncts = disjunctLists[positions[i]];
					if (disjuncts == null)
					{
						disjuncts = disjunctLists[positions[i]] = new ArrayList(termArray.length);
						distinctPositions++;
					}
					Term arr$[] = termArray;
					int len$ = arr$.length;
					for (int i$ = 0; i$ < len$; i$++)
					{
						Term term = arr$[i$];
						disjuncts.add(new SpanTermQuery(term));
					}

				}

				int positionGaps = 0;
				int position = 0;
				SpanQuery clauses[] = new SpanQuery[distinctPositions];
				for (int i = 0; i < disjunctLists.length; i++)
				{
					List disjuncts = disjunctLists[i];
					if (disjuncts != null)
						clauses[position++] = new SpanOrQuery((SpanQuery[])disjuncts.toArray(new SpanQuery[disjuncts.size()]));
					else
						positionGaps++;
				}

				int slop = mpq.getSlop();
				boolean inorder = slop == 0;
				SpanNearQuery sp = new SpanNearQuery(clauses, slop + positionGaps, inorder);
				sp.setBoost(query.getBoost());
				getPayloads(payloads, sp);
			}
		}
	}

	private void getPayloads(Collection payloads, SpanQuery query)
		throws IOException
	{
		Map termContexts = new HashMap();
		TreeSet terms = new TreeSet();
		query.extractTerms(terms);
		Term term;
		for (Iterator i$ = terms.iterator(); i$.hasNext(); termContexts.put(term, TermContext.build(context, term, true)))
			term = (Term)i$.next();

		for (Iterator i$ = context.leaves().iterator(); i$.hasNext();)
		{
			AtomicReaderContext atomicReaderContext = (AtomicReaderContext)i$.next();
			Spans spans = query.getSpans(atomicReaderContext, atomicReaderContext.reader().getLiveDocs(), termContexts);
			while (spans.next()) 
				if (spans.isPayloadAvailable())
				{
					Collection payload = spans.getPayload();
					Iterator i$ = payload.iterator();
					while (i$.hasNext()) 
					{
						byte bytes[] = (byte[])i$.next();
						payloads.add(bytes);
					}
				}
		}

	}
}
