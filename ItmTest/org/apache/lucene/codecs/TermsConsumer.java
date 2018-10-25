// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermsConsumer.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.FixedBitSet;

// Referenced classes of package org.apache.lucene.codecs:
//			MappingMultiDocsEnum, MappingMultiDocsAndPositionsEnum, PostingsConsumer, TermStats

public abstract class TermsConsumer
{

	private MappingMultiDocsEnum docsEnum;
	private MappingMultiDocsEnum docsAndFreqsEnum;
	private MappingMultiDocsAndPositionsEnum postingsEnum;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/TermsConsumer.desiredAssertionStatus();

	protected TermsConsumer()
	{
	}

	public abstract PostingsConsumer startTerm(BytesRef bytesref)
		throws IOException;

	public abstract void finishTerm(BytesRef bytesref, TermStats termstats)
		throws IOException;

	public abstract void finish(long l, long l1, int i)
		throws IOException;

	public abstract Comparator getComparator()
		throws IOException;

	public void merge(MergeState mergeState, TermsEnum termsEnum)
		throws IOException
	{
		if (!$assertionsDisabled && termsEnum == null)
			throw new AssertionError();
		long sumTotalTermFreq = 0L;
		long sumDocFreq = 0L;
		long sumDFsinceLastAbortCheck = 0L;
		FixedBitSet visitedDocs = new FixedBitSet(mergeState.segmentInfo.getDocCount());
		org.apache.lucene.index.FieldInfo.IndexOptions indexOptions = mergeState.fieldInfo.getIndexOptions();
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
		{
			if (docsEnum == null)
				docsEnum = new MappingMultiDocsEnum();
			docsEnum.setMergeState(mergeState);
			MultiDocsEnum docsEnumIn = null;
			do
			{
				BytesRef term;
				if ((term = termsEnum.next()) == null)
					break;
				docsEnumIn = (MultiDocsEnum)termsEnum.docs(null, docsEnumIn, 0);
				if (docsEnumIn != null)
				{
					docsEnum.reset(docsEnumIn);
					PostingsConsumer postingsConsumer = startTerm(term);
					TermStats stats = postingsConsumer.merge(mergeState, docsEnum, visitedDocs);
					if (stats.docFreq > 0)
					{
						finishTerm(term, stats);
						sumTotalTermFreq += stats.docFreq;
						sumDFsinceLastAbortCheck += stats.docFreq;
						sumDocFreq += stats.docFreq;
						if (sumDFsinceLastAbortCheck > 60000L)
						{
							mergeState.checkAbort.work((double)sumDFsinceLastAbortCheck / 5D);
							sumDFsinceLastAbortCheck = 0L;
						}
					}
				}
			} while (true);
		} else
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS)
		{
			if (docsAndFreqsEnum == null)
				docsAndFreqsEnum = new MappingMultiDocsEnum();
			docsAndFreqsEnum.setMergeState(mergeState);
			MultiDocsEnum docsAndFreqsEnumIn = null;
			do
			{
				BytesRef term;
				if ((term = termsEnum.next()) == null)
					break;
				docsAndFreqsEnumIn = (MultiDocsEnum)termsEnum.docs(null, docsAndFreqsEnumIn);
				if (!$assertionsDisabled && docsAndFreqsEnumIn == null)
					throw new AssertionError();
				docsAndFreqsEnum.reset(docsAndFreqsEnumIn);
				PostingsConsumer postingsConsumer = startTerm(term);
				TermStats stats = postingsConsumer.merge(mergeState, docsAndFreqsEnum, visitedDocs);
				if (stats.docFreq > 0)
				{
					finishTerm(term, stats);
					sumTotalTermFreq += stats.totalTermFreq;
					sumDFsinceLastAbortCheck += stats.docFreq;
					sumDocFreq += stats.docFreq;
					if (sumDFsinceLastAbortCheck > 60000L)
					{
						mergeState.checkAbort.work((double)sumDFsinceLastAbortCheck / 5D);
						sumDFsinceLastAbortCheck = 0L;
					}
				}
			} while (true);
		} else
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
		{
			if (postingsEnum == null)
				postingsEnum = new MappingMultiDocsAndPositionsEnum();
			postingsEnum.setMergeState(mergeState);
			MultiDocsAndPositionsEnum postingsEnumIn = null;
			do
			{
				BytesRef term;
				if ((term = termsEnum.next()) == null)
					break;
				postingsEnumIn = (MultiDocsAndPositionsEnum)termsEnum.docsAndPositions(null, postingsEnumIn, 2);
				if (!$assertionsDisabled && postingsEnumIn == null)
					throw new AssertionError();
				postingsEnum.reset(postingsEnumIn);
				PostingsConsumer postingsConsumer = startTerm(term);
				TermStats stats = postingsConsumer.merge(mergeState, postingsEnum, visitedDocs);
				if (stats.docFreq > 0)
				{
					finishTerm(term, stats);
					sumTotalTermFreq += stats.totalTermFreq;
					sumDFsinceLastAbortCheck += stats.docFreq;
					sumDocFreq += stats.docFreq;
					if (sumDFsinceLastAbortCheck > 60000L)
					{
						mergeState.checkAbort.work((double)sumDFsinceLastAbortCheck / 5D);
						sumDFsinceLastAbortCheck = 0L;
					}
				}
			} while (true);
		} else
		{
			if (!$assertionsDisabled && indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS)
				throw new AssertionError();
			if (postingsEnum == null)
				postingsEnum = new MappingMultiDocsAndPositionsEnum();
			postingsEnum.setMergeState(mergeState);
			MultiDocsAndPositionsEnum postingsEnumIn = null;
			do
			{
				BytesRef term;
				if ((term = termsEnum.next()) == null)
					break;
				postingsEnumIn = (MultiDocsAndPositionsEnum)termsEnum.docsAndPositions(null, postingsEnumIn);
				if (!$assertionsDisabled && postingsEnumIn == null)
					throw new AssertionError();
				postingsEnum.reset(postingsEnumIn);
				PostingsConsumer postingsConsumer = startTerm(term);
				TermStats stats = postingsConsumer.merge(mergeState, postingsEnum, visitedDocs);
				if (stats.docFreq > 0)
				{
					finishTerm(term, stats);
					sumTotalTermFreq += stats.totalTermFreq;
					sumDFsinceLastAbortCheck += stats.docFreq;
					sumDocFreq += stats.docFreq;
					if (sumDFsinceLastAbortCheck > 60000L)
					{
						mergeState.checkAbort.work((double)sumDFsinceLastAbortCheck / 5D);
						sumDFsinceLastAbortCheck = 0L;
					}
				}
			} while (true);
		}
		finish(indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY ? sumTotalTermFreq : -1L, sumDocFreq, visitedDocs.cardinality());
	}

}
