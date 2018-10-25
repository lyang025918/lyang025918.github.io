// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PostingsConsumer.java

package org.apache.lucene.codecs;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.FixedBitSet;

// Referenced classes of package org.apache.lucene.codecs:
//			TermStats

public abstract class PostingsConsumer
{

	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/PostingsConsumer.desiredAssertionStatus();

	protected PostingsConsumer()
	{
	}

	public abstract void startDoc(int i, int j)
		throws IOException;

	public abstract void addPosition(int i, BytesRef bytesref, int j, int k)
		throws IOException;

	public abstract void finishDoc()
		throws IOException;

	public TermStats merge(MergeState mergeState, DocsEnum postings, FixedBitSet visitedDocs)
		throws IOException
	{
		int df = 0;
		long totTF = 0L;
		org.apache.lucene.index.FieldInfo.IndexOptions indexOptions = mergeState.fieldInfo.getIndexOptions();
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
		{
			do
			{
				int doc = postings.nextDoc();
				if (doc == 0x7fffffff)
					break;
				visitedDocs.set(doc);
				startDoc(doc, -1);
				finishDoc();
				df++;
			} while (true);
			totTF = -1L;
		} else
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS)
			do
			{
				int doc = postings.nextDoc();
				if (doc == 0x7fffffff)
					break;
				visitedDocs.set(doc);
				int freq = postings.freq();
				startDoc(doc, freq);
				finishDoc();
				df++;
				totTF += freq;
			} while (true);
		else
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
		{
			DocsAndPositionsEnum postingsEnum = (DocsAndPositionsEnum)postings;
			do
			{
				int doc = postingsEnum.nextDoc();
				if (doc == 0x7fffffff)
					break;
				visitedDocs.set(doc);
				int freq = postingsEnum.freq();
				startDoc(doc, freq);
				totTF += freq;
				for (int i = 0; i < freq; i++)
				{
					int position = postingsEnum.nextPosition();
					BytesRef payload = postingsEnum.getPayload();
					addPosition(position, payload, -1, -1);
				}

				finishDoc();
				df++;
			} while (true);
		} else
		{
			if (!$assertionsDisabled && indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS)
				throw new AssertionError();
			DocsAndPositionsEnum postingsEnum = (DocsAndPositionsEnum)postings;
			do
			{
				int doc = postingsEnum.nextDoc();
				if (doc == 0x7fffffff)
					break;
				visitedDocs.set(doc);
				int freq = postingsEnum.freq();
				startDoc(doc, freq);
				totTF += freq;
				for (int i = 0; i < freq; i++)
				{
					int position = postingsEnum.nextPosition();
					BytesRef payload = postingsEnum.getPayload();
					addPosition(position, payload, postingsEnum.startOffset(), postingsEnum.endOffset());
				}

				finishDoc();
				df++;
			} while (true);
		}
		return new TermStats(df, indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY ? totTF : -1L);
	}

}
