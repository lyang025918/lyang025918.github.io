// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermVectorsConsumerPerField.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.codecs.TermVectorsWriter;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsHashConsumerPerField, IndexableField, ByteSliceReader, TermsHashPerField, 
//			IndexableFieldType, TermVectorsConsumer, FieldInfo, FieldInvertState, 
//			DocumentsWriterPerThread, ParallelPostingsArray

final class TermVectorsConsumerPerField extends TermsHashConsumerPerField
{
	static final class TermVectorsPostingsArray extends ParallelPostingsArray
	{

		int freqs[];
		int lastOffsets[];
		int lastPositions[];
		static final boolean $assertionsDisabled = !org/apache/lucene/index/TermVectorsConsumerPerField.desiredAssertionStatus();

		ParallelPostingsArray newInstance(int size)
		{
			return new TermVectorsPostingsArray(size);
		}

		void copyTo(ParallelPostingsArray toArray, int numToCopy)
		{
			if (!$assertionsDisabled && !(toArray instanceof TermVectorsPostingsArray))
			{
				throw new AssertionError();
			} else
			{
				TermVectorsPostingsArray to = (TermVectorsPostingsArray)toArray;
				super.copyTo(toArray, numToCopy);
				System.arraycopy(freqs, 0, to.freqs, 0, size);
				System.arraycopy(lastOffsets, 0, to.lastOffsets, 0, size);
				System.arraycopy(lastPositions, 0, to.lastPositions, 0, size);
				return;
			}
		}

		int bytesPerPosting()
		{
			return super.bytesPerPosting() + 12;
		}


		public TermVectorsPostingsArray(int size)
		{
			super(size);
			freqs = new int[size];
			lastOffsets = new int[size];
			lastPositions = new int[size];
		}
	}


	final TermsHashPerField termsHashPerField;
	final TermVectorsConsumer termsWriter;
	final FieldInfo fieldInfo;
	final DocumentsWriterPerThread.DocState docState;
	final FieldInvertState fieldState;
	boolean doVectors;
	boolean doVectorPositions;
	boolean doVectorOffsets;
	boolean doVectorPayloads;
	int maxNumPostings;
	OffsetAttribute offsetAttribute;
	PayloadAttribute payloadAttribute;
	boolean hasPayloads;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TermVectorsConsumerPerField.desiredAssertionStatus();

	public TermVectorsConsumerPerField(TermsHashPerField termsHashPerField, TermVectorsConsumer termsWriter, FieldInfo fieldInfo)
	{
		this.termsHashPerField = termsHashPerField;
		this.termsWriter = termsWriter;
		this.fieldInfo = fieldInfo;
		docState = termsHashPerField.docState;
		fieldState = termsHashPerField.fieldState;
	}

	int getStreamCount()
	{
		return 2;
	}

	boolean start(IndexableField fields[], int count)
	{
		doVectors = false;
		doVectorPositions = false;
		doVectorOffsets = false;
		doVectorPayloads = false;
		hasPayloads = false;
		for (int i = 0; i < count; i++)
		{
			IndexableField field = fields[i];
			if (field.fieldType().indexed())
			{
				if (field.fieldType().storeTermVectors())
				{
					doVectors = true;
					doVectorPositions |= field.fieldType().storeTermVectorPositions();
					doVectorOffsets |= field.fieldType().storeTermVectorOffsets();
					if (doVectorPositions)
					{
						doVectorPayloads |= field.fieldType().storeTermVectorPayloads();
						continue;
					}
					if (field.fieldType().storeTermVectorPayloads())
						throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector payloads for field: ").append(field).append(" without term vector positions").toString());
					continue;
				}
				if (field.fieldType().storeTermVectorOffsets())
					throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector offsets when term vectors are not indexed (field=\"").append(field.name()).toString());
				if (field.fieldType().storeTermVectorPositions())
					throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector positions when term vectors are not indexed (field=\"").append(field.name()).toString());
				if (field.fieldType().storeTermVectorPayloads())
					throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector payloads when term vectors are not indexed (field=\"").append(field.name()).toString());
				continue;
			}
			if (field.fieldType().storeTermVectors())
				throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vectors when field is not indexed (field=\"").append(field.name()).toString());
			if (field.fieldType().storeTermVectorOffsets())
				throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector offsets when field is not indexed (field=\"").append(field.name()).toString());
			if (field.fieldType().storeTermVectorPositions())
				throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector positions when field is not indexed (field=\"").append(field.name()).toString());
			if (field.fieldType().storeTermVectorPayloads())
				throw new IllegalArgumentException((new StringBuilder()).append("cannot index term vector payloads when field is not indexed (field=\"").append(field.name()).toString());
		}

		if (doVectors)
		{
			termsWriter.hasVectors = true;
			if (termsHashPerField.bytesHash.size() != 0)
				termsHashPerField.reset();
		}
		return doVectors;
	}

	public void abort()
	{
	}

	void finish()
	{
		if (!doVectors || termsHashPerField.bytesHash.size() == 0)
		{
			return;
		} else
		{
			termsWriter.addFieldToFlush(this);
			return;
		}
	}

	void finishDocument()
		throws IOException
	{
		if (!$assertionsDisabled && !docState.testPoint("TermVectorsTermsWriterPerField.finish start"))
			throw new AssertionError();
		int numPostings = termsHashPerField.bytesHash.size();
		BytesRef flushTerm = termsWriter.flushTerm;
		if (!$assertionsDisabled && numPostings < 0)
			throw new AssertionError();
		if (numPostings > maxNumPostings)
			maxNumPostings = numPostings;
		if (!$assertionsDisabled && !termsWriter.vectorFieldsInOrder(fieldInfo))
			throw new AssertionError();
		TermVectorsPostingsArray postings = (TermVectorsPostingsArray)termsHashPerField.postingsArray;
		TermVectorsWriter tv = termsWriter.writer;
		int termIDs[] = termsHashPerField.sortPostings(tv.getComparator());
		tv.startField(fieldInfo, numPostings, doVectorPositions, doVectorOffsets, hasPayloads);
		ByteSliceReader posReader = doVectorPositions ? termsWriter.vectorSliceReaderPos : null;
		ByteSliceReader offReader = doVectorOffsets ? termsWriter.vectorSliceReaderOff : null;
		ByteBlockPool termBytePool = termsHashPerField.termBytePool;
		for (int j = 0; j < numPostings; j++)
		{
			int termID = termIDs[j];
			int freq = postings.freqs[termID];
			termBytePool.setBytesRef(flushTerm, postings.textStarts[termID]);
			tv.startTerm(flushTerm, freq);
			if (!doVectorPositions && !doVectorOffsets)
				continue;
			if (posReader != null)
				termsHashPerField.initReader(posReader, termID, 0);
			if (offReader != null)
				termsHashPerField.initReader(offReader, termID, 1);
			tv.addProx(freq, posReader, offReader);
		}

		termsHashPerField.reset();
		fieldInfo.setStoreTermVectors();
	}

	void shrinkHash()
	{
		termsHashPerField.shrinkHash(maxNumPostings);
		maxNumPostings = 0;
	}

	void start(IndexableField f)
	{
		if (doVectorOffsets)
			offsetAttribute = (OffsetAttribute)fieldState.attributeSource.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		else
			offsetAttribute = null;
		if (doVectorPayloads && fieldState.attributeSource.hasAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute))
			payloadAttribute = (PayloadAttribute)fieldState.attributeSource.getAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
		else
			payloadAttribute = null;
	}

	void writeProx(TermVectorsPostingsArray postings, int termID)
	{
		if (doVectorOffsets)
		{
			int startOffset = fieldState.offset + offsetAttribute.startOffset();
			int endOffset = fieldState.offset + offsetAttribute.endOffset();
			termsHashPerField.writeVInt(1, startOffset - postings.lastOffsets[termID]);
			termsHashPerField.writeVInt(1, endOffset - startOffset);
			postings.lastOffsets[termID] = endOffset;
		}
		if (doVectorPositions)
		{
			BytesRef payload;
			if (payloadAttribute == null)
				payload = null;
			else
				payload = payloadAttribute.getPayload();
			int pos = fieldState.position - postings.lastPositions[termID];
			if (payload != null && payload.length > 0)
			{
				termsHashPerField.writeVInt(0, pos << 1 | 1);
				termsHashPerField.writeVInt(0, payload.length);
				termsHashPerField.writeBytes(0, payload.bytes, payload.offset, payload.length);
				hasPayloads = true;
			} else
			{
				termsHashPerField.writeVInt(0, pos << 1);
			}
			postings.lastPositions[termID] = fieldState.position;
		}
	}

	void newTerm(int termID)
	{
		if (!$assertionsDisabled && !docState.testPoint("TermVectorsTermsWriterPerField.newTerm start"))
		{
			throw new AssertionError();
		} else
		{
			TermVectorsPostingsArray postings = (TermVectorsPostingsArray)termsHashPerField.postingsArray;
			postings.freqs[termID] = 1;
			postings.lastOffsets[termID] = 0;
			postings.lastPositions[termID] = 0;
			writeProx(postings, termID);
			return;
		}
	}

	void addTerm(int termID)
	{
		if (!$assertionsDisabled && !docState.testPoint("TermVectorsTermsWriterPerField.addTerm start"))
		{
			throw new AssertionError();
		} else
		{
			TermVectorsPostingsArray postings = (TermVectorsPostingsArray)termsHashPerField.postingsArray;
			postings.freqs[termID]++;
			writeProx(postings, termID);
			return;
		}
	}

	void skippingLongTerm()
	{
	}

	ParallelPostingsArray createPostingsArray(int size)
	{
		return new TermVectorsPostingsArray(size);
	}

}
