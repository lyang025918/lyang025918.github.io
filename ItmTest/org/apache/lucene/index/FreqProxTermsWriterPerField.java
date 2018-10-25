// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FreqProxTermsWriterPerField.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.codecs.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsHashConsumerPerField, ByteSliceReader, Term, SegmentWriteState, 
//			TermsHashPerField, FieldInfo, IndexableField, IndexableFieldType, 
//			FieldInvertState, BufferedDeletes, SegmentInfo, DocumentsWriterPerThread, 
//			FreqProxTermsWriter, ParallelPostingsArray

final class FreqProxTermsWriterPerField extends TermsHashConsumerPerField
	implements Comparable
{
	static final class FreqProxPostingsArray extends ParallelPostingsArray
	{

		int termFreqs[];
		int lastDocIDs[];
		int lastDocCodes[];
		int lastPositions[];
		int lastOffsets[];
		static final boolean $assertionsDisabled = !org/apache/lucene/index/FreqProxTermsWriterPerField.desiredAssertionStatus();

		ParallelPostingsArray newInstance(int size)
		{
			return new FreqProxPostingsArray(size, termFreqs != null, lastPositions != null, lastOffsets != null);
		}

		void copyTo(ParallelPostingsArray toArray, int numToCopy)
		{
			if (!$assertionsDisabled && !(toArray instanceof FreqProxPostingsArray))
				throw new AssertionError();
			FreqProxPostingsArray to = (FreqProxPostingsArray)toArray;
			super.copyTo(toArray, numToCopy);
			System.arraycopy(lastDocIDs, 0, to.lastDocIDs, 0, numToCopy);
			System.arraycopy(lastDocCodes, 0, to.lastDocCodes, 0, numToCopy);
			if (lastPositions != null)
			{
				if (!$assertionsDisabled && to.lastPositions == null)
					throw new AssertionError();
				System.arraycopy(lastPositions, 0, to.lastPositions, 0, numToCopy);
			}
			if (lastOffsets != null)
			{
				if (!$assertionsDisabled && to.lastOffsets == null)
					throw new AssertionError();
				System.arraycopy(lastOffsets, 0, to.lastOffsets, 0, numToCopy);
			}
			if (termFreqs != null)
			{
				if (!$assertionsDisabled && to.termFreqs == null)
					throw new AssertionError();
				System.arraycopy(termFreqs, 0, to.termFreqs, 0, numToCopy);
			}
		}

		int bytesPerPosting()
		{
			int bytes = 20;
			if (lastPositions != null)
				bytes += 4;
			if (lastOffsets != null)
				bytes += 4;
			if (termFreqs != null)
				bytes += 4;
			return bytes;
		}


		public FreqProxPostingsArray(int size, boolean writeFreqs, boolean writeProx, boolean writeOffsets)
		{
			super(size);
			if (writeFreqs)
				termFreqs = new int[size];
			lastDocIDs = new int[size];
			lastDocCodes = new int[size];
			if (writeProx)
			{
				lastPositions = new int[size];
				if (writeOffsets)
					lastOffsets = new int[size];
			} else
			if (!$assertionsDisabled && writeOffsets)
				throw new AssertionError();
		}
	}


	final FreqProxTermsWriter parent;
	final TermsHashPerField termsHashPerField;
	final FieldInfo fieldInfo;
	final DocumentsWriterPerThread.DocState docState;
	final FieldInvertState fieldState;
	private boolean hasFreq;
	private boolean hasProx;
	private boolean hasOffsets;
	PayloadAttribute payloadAttribute;
	OffsetAttribute offsetAttribute;
	boolean hasPayloads;
	BytesRef payload;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FreqProxTermsWriterPerField.desiredAssertionStatus();

	public FreqProxTermsWriterPerField(TermsHashPerField termsHashPerField, FreqProxTermsWriter parent, FieldInfo fieldInfo)
	{
		this.termsHashPerField = termsHashPerField;
		this.parent = parent;
		this.fieldInfo = fieldInfo;
		docState = termsHashPerField.docState;
		fieldState = termsHashPerField.fieldState;
		setIndexOptions(fieldInfo.getIndexOptions());
	}

	int getStreamCount()
	{
		return hasProx ? 2 : 1;
	}

	void finish()
	{
		if (hasPayloads)
			fieldInfo.setStorePayloads();
	}

	void skippingLongTerm()
	{
	}

	public int compareTo(FreqProxTermsWriterPerField other)
	{
		return fieldInfo.name.compareTo(other.fieldInfo.name);
	}

	void reset()
	{
		setIndexOptions(fieldInfo.getIndexOptions());
		payloadAttribute = null;
	}

	private void setIndexOptions(FieldInfo.IndexOptions indexOptions)
	{
		if (indexOptions == null)
		{
			hasFreq = hasProx = hasOffsets = true;
		} else
		{
			hasFreq = indexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS) >= 0;
			hasProx = indexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
			hasOffsets = indexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
		}
	}

	boolean start(IndexableField fields[], int count)
	{
		for (int i = 0; i < count; i++)
			if (fields[i].fieldType().indexed())
				return true;

		return false;
	}

	void start(IndexableField f)
	{
		if (fieldState.attributeSource.hasAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute))
			payloadAttribute = (PayloadAttribute)fieldState.attributeSource.getAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
		else
			payloadAttribute = null;
		if (hasOffsets)
			offsetAttribute = (OffsetAttribute)fieldState.attributeSource.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		else
			offsetAttribute = null;
	}

	void writeProx(int termID, int proxCode)
	{
		if (!$assertionsDisabled && !hasProx)
			throw new AssertionError();
		BytesRef payload;
		if (payloadAttribute == null)
			payload = null;
		else
			payload = payloadAttribute.getPayload();
		if (payload != null && payload.length > 0)
		{
			termsHashPerField.writeVInt(1, proxCode << 1 | 1);
			termsHashPerField.writeVInt(1, payload.length);
			termsHashPerField.writeBytes(1, payload.bytes, payload.offset, payload.length);
			hasPayloads = true;
		} else
		{
			termsHashPerField.writeVInt(1, proxCode << 1);
		}
		FreqProxPostingsArray postings = (FreqProxPostingsArray)termsHashPerField.postingsArray;
		postings.lastPositions[termID] = fieldState.position;
	}

	void writeOffsets(int termID, int offsetAccum)
	{
		if (!$assertionsDisabled && !hasOffsets)
			throw new AssertionError();
		int startOffset = offsetAccum + offsetAttribute.startOffset();
		int endOffset = offsetAccum + offsetAttribute.endOffset();
		FreqProxPostingsArray postings = (FreqProxPostingsArray)termsHashPerField.postingsArray;
		if (!$assertionsDisabled && startOffset - postings.lastOffsets[termID] < 0)
		{
			throw new AssertionError();
		} else
		{
			termsHashPerField.writeVInt(1, startOffset - postings.lastOffsets[termID]);
			termsHashPerField.writeVInt(1, endOffset - startOffset);
			postings.lastOffsets[termID] = startOffset;
			return;
		}
	}

	void newTerm(int termID)
	{
		if (!$assertionsDisabled && !docState.testPoint("FreqProxTermsWriterPerField.newTerm start"))
			throw new AssertionError();
		FreqProxPostingsArray postings = (FreqProxPostingsArray)termsHashPerField.postingsArray;
		postings.lastDocIDs[termID] = docState.docID;
		if (!hasFreq)
		{
			postings.lastDocCodes[termID] = docState.docID;
		} else
		{
			postings.lastDocCodes[termID] = docState.docID << 1;
			postings.termFreqs[termID] = 1;
			if (hasProx)
			{
				writeProx(termID, fieldState.position);
				if (hasOffsets)
					writeOffsets(termID, fieldState.offset);
			} else
			if (!$assertionsDisabled && hasOffsets)
				throw new AssertionError();
		}
		fieldState.maxTermFrequency = Math.max(1, fieldState.maxTermFrequency);
		fieldState.uniqueTermCount++;
	}

	void addTerm(int termID)
	{
		if (!$assertionsDisabled && !docState.testPoint("FreqProxTermsWriterPerField.addTerm start"))
			throw new AssertionError();
		FreqProxPostingsArray postings = (FreqProxPostingsArray)termsHashPerField.postingsArray;
		if (!$assertionsDisabled && hasFreq && postings.termFreqs[termID] <= 0)
			throw new AssertionError();
		if (!hasFreq)
		{
			if (!$assertionsDisabled && postings.termFreqs != null)
				throw new AssertionError();
			if (docState.docID != postings.lastDocIDs[termID])
			{
				if (!$assertionsDisabled && docState.docID <= postings.lastDocIDs[termID])
					throw new AssertionError();
				termsHashPerField.writeVInt(0, postings.lastDocCodes[termID]);
				postings.lastDocCodes[termID] = docState.docID - postings.lastDocIDs[termID];
				postings.lastDocIDs[termID] = docState.docID;
				fieldState.uniqueTermCount++;
			}
		} else
		if (docState.docID != postings.lastDocIDs[termID])
		{
			if (!$assertionsDisabled && docState.docID <= postings.lastDocIDs[termID])
				throw new AssertionError((new StringBuilder()).append("id: ").append(docState.docID).append(" postings ID: ").append(postings.lastDocIDs[termID]).append(" termID: ").append(termID).toString());
			if (1 == postings.termFreqs[termID])
			{
				termsHashPerField.writeVInt(0, postings.lastDocCodes[termID] | 1);
			} else
			{
				termsHashPerField.writeVInt(0, postings.lastDocCodes[termID]);
				termsHashPerField.writeVInt(0, postings.termFreqs[termID]);
			}
			postings.termFreqs[termID] = 1;
			fieldState.maxTermFrequency = Math.max(1, fieldState.maxTermFrequency);
			postings.lastDocCodes[termID] = docState.docID - postings.lastDocIDs[termID] << 1;
			postings.lastDocIDs[termID] = docState.docID;
			if (hasProx)
			{
				writeProx(termID, fieldState.position);
				if (hasOffsets)
				{
					postings.lastOffsets[termID] = 0;
					writeOffsets(termID, fieldState.offset);
				}
			} else
			if (!$assertionsDisabled && hasOffsets)
				throw new AssertionError();
			fieldState.uniqueTermCount++;
		} else
		{
			fieldState.maxTermFrequency = Math.max(fieldState.maxTermFrequency, ++postings.termFreqs[termID]);
			if (hasProx)
				writeProx(termID, fieldState.position - postings.lastPositions[termID]);
			if (hasOffsets)
				writeOffsets(termID, fieldState.offset);
		}
	}

	ParallelPostingsArray createPostingsArray(int size)
	{
		return new FreqProxPostingsArray(size, hasFreq, hasProx, hasOffsets);
	}

	public void abort()
	{
	}

	void flush(String fieldName, FieldsConsumer consumer, SegmentWriteState state)
		throws IOException
	{
		if (!fieldInfo.isIndexed())
			return;
		TermsConsumer termsConsumer = consumer.addField(fieldInfo);
		Comparator termComp = termsConsumer.getComparator();
		FieldInfo.IndexOptions currentFieldIndexOptions = fieldInfo.getIndexOptions();
		if (!$assertionsDisabled && currentFieldIndexOptions == null)
			throw new AssertionError();
		boolean writeTermFreq = currentFieldIndexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS) >= 0;
		boolean writePositions = currentFieldIndexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
		boolean writeOffsets = currentFieldIndexOptions.compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
		boolean readTermFreq = hasFreq;
		boolean readPositions = hasProx;
		boolean readOffsets = hasOffsets;
		if (!$assertionsDisabled && writeTermFreq && !readTermFreq)
			throw new AssertionError();
		if (!$assertionsDisabled && writePositions && !readPositions)
			throw new AssertionError();
		if (!$assertionsDisabled && writeOffsets && !readOffsets)
			throw new AssertionError();
		if (!$assertionsDisabled && writeOffsets && !writePositions)
			throw new AssertionError();
		Map segDeletes;
		if (state.segDeletes != null && state.segDeletes.terms.size() > 0)
			segDeletes = state.segDeletes.terms;
		else
			segDeletes = null;
		int termIDs[] = termsHashPerField.sortPostings(termComp);
		int numTerms = termsHashPerField.bytesHash.size();
		BytesRef text = new BytesRef();
		FreqProxPostingsArray postings = (FreqProxPostingsArray)termsHashPerField.postingsArray;
		ByteSliceReader freq = new ByteSliceReader();
		ByteSliceReader prox = new ByteSliceReader();
		FixedBitSet visitedDocs = new FixedBitSet(state.segmentInfo.getDocCount());
		long sumTotalTermFreq = 0L;
		long sumDocFreq = 0L;
		for (int i = 0; i < numTerms; i++)
		{
			int termID = termIDs[i];
			int textStart = postings.textStarts[termID];
			termsHashPerField.bytePool.setBytesRef(text, textStart);
			termsHashPerField.initReader(freq, termID, 0);
			if (readPositions || readOffsets)
				termsHashPerField.initReader(prox, termID, 1);
			PostingsConsumer postingsConsumer = termsConsumer.startTerm(text);
			int delDocLimit;
			if (segDeletes != null)
			{
				Integer docIDUpto = (Integer)segDeletes.get(new Term(fieldName, text));
				if (docIDUpto != null)
					delDocLimit = docIDUpto.intValue();
				else
					delDocLimit = 0;
			} else
			{
				delDocLimit = 0;
			}
			int docFreq = 0;
			long totTF = 0L;
			int docID = 0;
			do
			{
				int termFreq;
				if (freq.eof())
				{
					if (postings.lastDocCodes[termID] == -1)
						break;
					docID = postings.lastDocIDs[termID];
					if (readTermFreq)
						termFreq = postings.termFreqs[termID];
					else
						termFreq = -1;
					postings.lastDocCodes[termID] = -1;
				} else
				{
					int code = freq.readVInt();
					if (!readTermFreq)
					{
						docID += code;
						termFreq = -1;
					} else
					{
						docID += code >>> 1;
						if ((code & 1) != 0)
							termFreq = 1;
						else
							termFreq = freq.readVInt();
					}
					if (!$assertionsDisabled && docID == postings.lastDocIDs[termID])
						throw new AssertionError();
				}
				docFreq++;
				if (!$assertionsDisabled && docID >= state.segmentInfo.getDocCount())
					throw new AssertionError((new StringBuilder()).append("doc=").append(docID).append(" maxDoc=").append(state.segmentInfo.getDocCount()).toString());
				visitedDocs.set(docID);
				postingsConsumer.startDoc(docID, writeTermFreq ? termFreq : -1);
				if (docID < delDocLimit)
				{
					if (state.liveDocs == null)
						state.liveDocs = docState.docWriter.codec.liveDocsFormat().newLiveDocs(state.segmentInfo.getDocCount());
					if (state.liveDocs.get(docID))
					{
						state.delCountOnFlush++;
						state.liveDocs.clear(docID);
					}
				}
				totTF += termFreq;
				if (readPositions || readOffsets)
				{
					int position = 0;
					int offset = 0;
					for (int j = 0; j < termFreq; j++)
					{
						if (!readPositions)
							continue;
						int code = prox.readVInt();
						position += code >>> 1;
						BytesRef thisPayload;
						if ((code & 1) != 0)
						{
							int payloadLength = prox.readVInt();
							if (payload == null)
							{
								payload = new BytesRef();
								payload.bytes = new byte[payloadLength];
							} else
							if (payload.bytes.length < payloadLength)
								payload.grow(payloadLength);
							prox.readBytes(payload.bytes, 0, payloadLength);
							payload.length = payloadLength;
							thisPayload = payload;
						} else
						{
							thisPayload = null;
						}
						if (readOffsets)
						{
							int startOffset = offset + prox.readVInt();
							int endOffset = startOffset + prox.readVInt();
							if (writePositions)
								if (writeOffsets)
								{
									if (!$assertionsDisabled && (startOffset < 0 || endOffset < startOffset))
										throw new AssertionError((new StringBuilder()).append("startOffset=").append(startOffset).append(",endOffset=").append(endOffset).append(",offset=").append(offset).toString());
									postingsConsumer.addPosition(position, thisPayload, startOffset, endOffset);
								} else
								{
									postingsConsumer.addPosition(position, thisPayload, -1, -1);
								}
							offset = startOffset;
							continue;
						}
						if (writePositions)
							postingsConsumer.addPosition(position, thisPayload, -1, -1);
					}

				}
				postingsConsumer.finishDoc();
			} while (true);
			termsConsumer.finishTerm(text, new TermStats(docFreq, writeTermFreq ? totTF : -1L));
			sumTotalTermFreq += totTF;
			sumDocFreq += docFreq;
		}

		termsConsumer.finish(writeTermFreq ? sumTotalTermFreq : -1L, sumDocFreq, visitedDocs.cardinality());
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((FreqProxTermsWriterPerField)x0);
	}

}
