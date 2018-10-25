// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermVectorsWriter.java

package org.apache.lucene.codecs;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

public abstract class TermVectorsWriter
	implements Closeable
{

	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/TermVectorsWriter.desiredAssertionStatus();

	protected TermVectorsWriter()
	{
	}

	public abstract void startDocument(int i)
		throws IOException;

	public abstract void startField(FieldInfo fieldinfo, int i, boolean flag, boolean flag1, boolean flag2)
		throws IOException;

	public abstract void startTerm(BytesRef bytesref, int i)
		throws IOException;

	public abstract void addPosition(int i, int j, int k, BytesRef bytesref)
		throws IOException;

	public abstract void abort();

	public abstract void finish(FieldInfos fieldinfos, int i)
		throws IOException;

	public void addProx(int numProx, DataInput positions, DataInput offsets)
		throws IOException
	{
		int position = 0;
		int lastOffset = 0;
		BytesRef payload = null;
		for (int i = 0; i < numProx; i++)
		{
			BytesRef thisPayload;
			if (positions == null)
			{
				position = -1;
				thisPayload = null;
			} else
			{
				int code = positions.readVInt();
				position += code >>> 1;
				if ((code & 1) != 0)
				{
					int payloadLength = positions.readVInt();
					if (payload == null)
					{
						payload = new BytesRef();
						payload.bytes = new byte[payloadLength];
					} else
					if (payload.bytes.length < payloadLength)
						payload.grow(payloadLength);
					positions.readBytes(payload.bytes, 0, payloadLength);
					payload.length = payloadLength;
					thisPayload = payload;
				} else
				{
					thisPayload = null;
				}
			}
			int startOffset;
			int endOffset;
			if (offsets == null)
			{
				startOffset = endOffset = -1;
			} else
			{
				startOffset = lastOffset + offsets.readVInt();
				endOffset = startOffset + offsets.readVInt();
				lastOffset = endOffset;
			}
			addPosition(position, startOffset, endOffset, thisPayload);
		}

	}

	public int merge(MergeState mergeState)
		throws IOException
	{
		int docCount = 0;
		for (int i = 0; i < mergeState.readers.size(); i++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(i);
			int maxDoc = reader.maxDoc();
			Bits liveDocs = reader.getLiveDocs();
			for (int docID = 0; docID < maxDoc; docID++)
				if (liveDocs == null || liveDocs.get(docID))
				{
					Fields vectors = reader.getTermVectors(docID);
					addAllDocVectors(vectors, mergeState);
					docCount++;
					mergeState.checkAbort.work(300D);
				}

		}

		finish(mergeState.fieldInfos, docCount);
		return docCount;
	}

	protected final void addAllDocVectors(Fields vectors, MergeState mergeState)
		throws IOException
	{
		if (vectors == null)
		{
			startDocument(0);
			return;
		}
		int numFields = vectors.size();
		if (numFields == -1)
		{
			numFields = 0;
			for (Iterator it = vectors.iterator(); it.hasNext();)
			{
				it.next();
				numFields++;
			}

		}
		startDocument(numFields);
		String lastFieldName = null;
		TermsEnum termsEnum = null;
		DocsAndPositionsEnum docsAndPositionsEnum = null;
		int fieldCount = 0;
		for (Iterator i$ = vectors.iterator(); i$.hasNext();)
		{
			String fieldName = (String)i$.next();
			fieldCount++;
			FieldInfo fieldInfo = mergeState.fieldInfos.fieldInfo(fieldName);
			if (!$assertionsDisabled && lastFieldName != null && fieldName.compareTo(lastFieldName) <= 0)
				throw new AssertionError((new StringBuilder()).append("lastFieldName=").append(lastFieldName).append(" fieldName=").append(fieldName).toString());
			lastFieldName = fieldName;
			Terms terms = vectors.terms(fieldName);
			if (terms != null)
			{
				boolean hasPositions = terms.hasPositions();
				boolean hasOffsets = terms.hasOffsets();
				boolean hasPayloads = terms.hasPayloads();
				if (!$assertionsDisabled && hasPayloads && !hasPositions)
					throw new AssertionError();
				int numTerms = (int)terms.size();
				if (numTerms == -1)
				{
					numTerms = 0;
					for (termsEnum = terms.iterator(termsEnum); termsEnum.next() != null;)
						numTerms++;

				}
				startField(fieldInfo, numTerms, hasPositions, hasOffsets, hasPayloads);
				termsEnum = terms.iterator(termsEnum);
				int termCount = 0;
				do
				{
					if (termsEnum.next() == null)
						break;
					termCount++;
					int freq = (int)termsEnum.totalTermFreq();
					startTerm(termsEnum.term(), freq);
					if (hasPositions || hasOffsets)
					{
						docsAndPositionsEnum = termsEnum.docsAndPositions(null, docsAndPositionsEnum);
						if (!$assertionsDisabled && docsAndPositionsEnum == null)
							throw new AssertionError();
						int docID = docsAndPositionsEnum.nextDoc();
						if (!$assertionsDisabled && docID == 0x7fffffff)
							throw new AssertionError();
						if (!$assertionsDisabled && docsAndPositionsEnum.freq() != freq)
							throw new AssertionError();
						int posUpto = 0;
						while (posUpto < freq) 
						{
							int pos = docsAndPositionsEnum.nextPosition();
							int startOffset = docsAndPositionsEnum.startOffset();
							int endOffset = docsAndPositionsEnum.endOffset();
							BytesRef payload = docsAndPositionsEnum.getPayload();
							if (!$assertionsDisabled && hasPositions && pos < 0)
								throw new AssertionError();
							addPosition(pos, startOffset, endOffset, payload);
							posUpto++;
						}
					}
				} while (true);
				if (!$assertionsDisabled && termCount != numTerms)
					throw new AssertionError();
			}
		}

		if (!$assertionsDisabled && fieldCount != numFields)
			throw new AssertionError();
		else
			return;
	}

	public abstract Comparator getComparator()
		throws IOException;

	public abstract void close()
		throws IOException;

}
