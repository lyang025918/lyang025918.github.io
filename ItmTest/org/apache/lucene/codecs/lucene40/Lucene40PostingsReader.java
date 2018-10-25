// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40PostingsReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40SkipListReader

public class Lucene40PostingsReader extends PostingsReaderBase
{
	private class SegmentFullPositionsEnum extends DocsAndPositionsEnum
	{

		final IndexInput startFreqIn;
		private final IndexInput freqIn;
		private final IndexInput proxIn;
		int limit;
		int ord;
		int doc;
		int accum;
		int freq;
		int position;
		Bits liveDocs;
		long freqOffset;
		int skipOffset;
		long proxOffset;
		int posPendingCount;
		int payloadLength;
		boolean payloadPending;
		boolean skipped;
		Lucene40SkipListReader skipper;
		private BytesRef payload;
		private long lazyProxPointer;
		boolean storePayloads;
		boolean storeOffsets;
		int offsetLength;
		int startOffset;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();
		final Lucene40PostingsReader this$0;

		public SegmentFullPositionsEnum reset(FieldInfo fieldInfo, StandardTermState termState, Bits liveDocs)
			throws IOException
		{
			storeOffsets = fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
			storePayloads = fieldInfo.hasPayloads();
			if (!$assertionsDisabled && fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
				throw new AssertionError();
			if (!$assertionsDisabled && !storePayloads && !storeOffsets)
				throw new AssertionError();
			if (payload == null)
			{
				payload = new BytesRef();
				payload.bytes = new byte[1];
			}
			this.liveDocs = liveDocs;
			freqIn.seek(termState.freqOffset);
			lazyProxPointer = termState.proxOffset;
			limit = termState.docFreq;
			ord = 0;
			doc = -1;
			accum = 0;
			position = 0;
			startOffset = 0;
			skipped = false;
			posPendingCount = 0;
			payloadPending = false;
			freqOffset = termState.freqOffset;
			proxOffset = termState.proxOffset;
			skipOffset = termState.skipOffset;
			return this;
		}

		public int nextDoc()
			throws IOException
		{
			do
			{
				if (ord == limit)
					return doc = 0x7fffffff;
				ord++;
				int code = freqIn.readVInt();
				accum += code >>> 1;
				if ((code & 1) != 0)
					freq = 1;
				else
					freq = freqIn.readVInt();
				posPendingCount += freq;
			} while (liveDocs != null && !liveDocs.get(accum));
			position = 0;
			startOffset = 0;
			return doc = accum;
		}

		public int docID()
		{
			return doc;
		}

		public int freq()
			throws IOException
		{
			return freq;
		}

		public int advance(int target)
			throws IOException
		{
			if (target - skipInterval >= doc && limit >= skipMinimum)
			{
				if (skipper == null)
					skipper = new Lucene40SkipListReader(freqIn.clone(), maxSkipLevels, skipInterval);
				if (!skipped)
				{
					skipper.init(freqOffset + (long)skipOffset, freqOffset, proxOffset, limit, storePayloads, storeOffsets);
					skipped = true;
				}
				int newOrd = skipper.skipTo(target);
				if (newOrd > ord)
				{
					ord = newOrd;
					doc = accum = skipper.getDoc();
					freqIn.seek(skipper.getFreqPointer());
					lazyProxPointer = skipper.getProxPointer();
					posPendingCount = 0;
					position = 0;
					startOffset = 0;
					payloadPending = false;
					payloadLength = skipper.getPayloadLength();
					offsetLength = skipper.getOffsetLength();
				}
			}
			do
				nextDoc();
			while (target > doc);
			return doc;
		}

		public int nextPosition()
			throws IOException
		{
			if (lazyProxPointer != -1L)
			{
				proxIn.seek(lazyProxPointer);
				lazyProxPointer = -1L;
			}
			if (payloadPending && payloadLength > 0)
			{
				proxIn.seek(proxIn.getFilePointer() + (long)payloadLength);
				payloadPending = false;
			}
			int code;
			while (posPendingCount > freq) 
			{
				code = proxIn.readVInt();
				if (storePayloads)
				{
					if ((code & 1) != 0)
					{
						payloadLength = proxIn.readVInt();
						if (!$assertionsDisabled && payloadLength < 0)
							throw new AssertionError();
					}
					if (!$assertionsDisabled && payloadLength == -1)
						throw new AssertionError();
				}
				if (storeOffsets && (proxIn.readVInt() & 1) != 0)
					offsetLength = proxIn.readVInt();
				if (storePayloads)
					proxIn.seek(proxIn.getFilePointer() + (long)payloadLength);
				posPendingCount--;
				position = 0;
				startOffset = 0;
				payloadPending = false;
			}
			if (payloadPending && payloadLength > 0)
				proxIn.seek(proxIn.getFilePointer() + (long)payloadLength);
			code = proxIn.readVInt();
			if (storePayloads)
			{
				if ((code & 1) != 0)
				{
					payloadLength = proxIn.readVInt();
					if (!$assertionsDisabled && payloadLength < 0)
						throw new AssertionError();
				}
				if (!$assertionsDisabled && payloadLength == -1)
					throw new AssertionError();
				payloadPending = true;
				code >>>= 1;
			}
			position += code;
			if (storeOffsets)
			{
				int offsetCode = proxIn.readVInt();
				if ((offsetCode & 1) != 0)
					offsetLength = proxIn.readVInt();
				startOffset += offsetCode >>> 1;
			}
			posPendingCount--;
			if (!$assertionsDisabled && posPendingCount < 0)
				throw new AssertionError((new StringBuilder()).append("nextPosition() was called too many times (more than freq() times) posPendingCount=").append(posPendingCount).toString());
			else
				return position;
		}

		public int startOffset()
			throws IOException
		{
			return storeOffsets ? startOffset : -1;
		}

		public int endOffset()
			throws IOException
		{
			return storeOffsets ? startOffset + offsetLength : -1;
		}

		public BytesRef getPayload()
			throws IOException
		{
			if (storePayloads)
			{
				if (payloadLength <= 0)
					return null;
				if (!$assertionsDisabled && lazyProxPointer != -1L)
					throw new AssertionError();
				if (!$assertionsDisabled && posPendingCount >= freq)
					throw new AssertionError();
				if (payloadPending)
				{
					if (payloadLength > payload.bytes.length)
						payload.grow(payloadLength);
					proxIn.readBytes(payload.bytes, 0, payloadLength);
					payload.length = payloadLength;
					payloadPending = false;
				}
				return payload;
			} else
			{
				return null;
			}
		}


		public SegmentFullPositionsEnum(IndexInput freqIn, IndexInput proxIn)
		{
			this$0 = Lucene40PostingsReader.this;
			super();
			doc = -1;
			startFreqIn = freqIn;
			this.freqIn = freqIn.clone();
			this.proxIn = proxIn.clone();
		}
	}

	private final class SegmentDocsAndPositionsEnum extends DocsAndPositionsEnum
	{

		final IndexInput startFreqIn;
		private final IndexInput freqIn;
		private final IndexInput proxIn;
		int limit;
		int ord;
		int doc;
		int accum;
		int freq;
		int position;
		Bits liveDocs;
		long freqOffset;
		int skipOffset;
		long proxOffset;
		int posPendingCount;
		boolean skipped;
		Lucene40SkipListReader skipper;
		private long lazyProxPointer;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();
		final Lucene40PostingsReader this$0;

		public SegmentDocsAndPositionsEnum reset(FieldInfo fieldInfo, StandardTermState termState, Bits liveDocs)
			throws IOException
		{
			if (!$assertionsDisabled && fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
				throw new AssertionError();
			if (!$assertionsDisabled && fieldInfo.hasPayloads())
				throw new AssertionError();
			this.liveDocs = liveDocs;
			freqIn.seek(termState.freqOffset);
			lazyProxPointer = termState.proxOffset;
			limit = termState.docFreq;
			if (!$assertionsDisabled && limit <= 0)
			{
				throw new AssertionError();
			} else
			{
				ord = 0;
				doc = -1;
				accum = 0;
				position = 0;
				skipped = false;
				posPendingCount = 0;
				freqOffset = termState.freqOffset;
				proxOffset = termState.proxOffset;
				skipOffset = termState.skipOffset;
				return this;
			}
		}

		public int nextDoc()
			throws IOException
		{
			do
			{
				if (ord == limit)
					return doc = 0x7fffffff;
				ord++;
				int code = freqIn.readVInt();
				accum += code >>> 1;
				if ((code & 1) != 0)
					freq = 1;
				else
					freq = freqIn.readVInt();
				posPendingCount += freq;
			} while (liveDocs != null && !liveDocs.get(accum));
			position = 0;
			return doc = accum;
		}

		public int docID()
		{
			return doc;
		}

		public int freq()
		{
			return freq;
		}

		public int advance(int target)
			throws IOException
		{
			if (target - skipInterval >= doc && limit >= skipMinimum)
			{
				if (skipper == null)
					skipper = new Lucene40SkipListReader(freqIn.clone(), maxSkipLevels, skipInterval);
				if (!skipped)
				{
					skipper.init(freqOffset + (long)skipOffset, freqOffset, proxOffset, limit, false, false);
					skipped = true;
				}
				int newOrd = skipper.skipTo(target);
				if (newOrd > ord)
				{
					ord = newOrd;
					doc = accum = skipper.getDoc();
					freqIn.seek(skipper.getFreqPointer());
					lazyProxPointer = skipper.getProxPointer();
					posPendingCount = 0;
					position = 0;
				}
			}
			do
				nextDoc();
			while (target > doc);
			return doc;
		}

		public int nextPosition()
			throws IOException
		{
			if (lazyProxPointer != -1L)
			{
				proxIn.seek(lazyProxPointer);
				lazyProxPointer = -1L;
			}
			if (posPendingCount > freq)
			{
				position = 0;
				do
				{
					if (posPendingCount == freq)
						break;
					if ((proxIn.readByte() & 0x80) == 0)
						posPendingCount--;
				} while (true);
			}
			position += proxIn.readVInt();
			posPendingCount--;
			if (!$assertionsDisabled && posPendingCount < 0)
				throw new AssertionError((new StringBuilder()).append("nextPosition() was called too many times (more than freq() times) posPendingCount=").append(posPendingCount).toString());
			else
				return position;
		}

		public int startOffset()
		{
			return -1;
		}

		public int endOffset()
		{
			return -1;
		}

		public BytesRef getPayload()
			throws IOException
		{
			return null;
		}


		public SegmentDocsAndPositionsEnum(IndexInput freqIn, IndexInput proxIn)
		{
			this$0 = Lucene40PostingsReader.this;
			super();
			doc = -1;
			startFreqIn = freqIn;
			this.freqIn = freqIn.clone();
			this.proxIn = proxIn.clone();
		}
	}

	private final class LiveDocsSegmentDocsEnum extends SegmentDocsEnumBase
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();
		final Lucene40PostingsReader this$0;

		public final int nextDoc()
			throws IOException
		{
			Bits liveDocs = this.liveDocs;
			for (int i = start + 1; i < count; i++)
			{
				int d = docs[i];
				if (liveDocs.get(d))
				{
					start = i;
					freq = freqs[i];
					return doc = d;
				}
			}

			start = count;
			return doc = refill();
		}

		protected final int linearScan(int scanTo)
			throws IOException
		{
			int docs[] = this.docs;
			int upTo = count;
			Bits liveDocs = this.liveDocs;
			for (int i = start; i < upTo; i++)
			{
				int d = docs[i];
				if (scanTo <= d && liveDocs.get(d))
				{
					start = i;
					freq = freqs[i];
					return doc = docs[i];
				}
			}

			return doc = refill();
		}

		protected int scanTo(int target)
			throws IOException
		{
			int docAcc = accum;
			int frq = 1;
			IndexInput freqIn = this.freqIn;
			boolean omitTF = indexOmitsTF;
			int loopLimit = limit;
			Bits liveDocs = this.liveDocs;
			for (int i = ord; i < loopLimit; i++)
			{
				int code = freqIn.readVInt();
				if (omitTF)
				{
					docAcc += code;
				} else
				{
					docAcc += code >>> 1;
					frq = readFreq(freqIn, code);
				}
				if (docAcc >= target && liveDocs.get(docAcc))
				{
					freq = frq;
					ord = i + 1;
					return accum = docAcc;
				}
			}

			ord = limit;
			freq = frq;
			accum = docAcc;
			return 0x7fffffff;
		}

		protected final int nextUnreadDoc()
			throws IOException
		{
			int docAcc = accum;
			int frq = 1;
			IndexInput freqIn = this.freqIn;
			boolean omitTF = indexOmitsTF;
			int loopLimit = limit;
			Bits liveDocs = this.liveDocs;
			for (int i = ord; i < loopLimit; i++)
			{
				int code = freqIn.readVInt();
				if (omitTF)
				{
					docAcc += code;
				} else
				{
					docAcc += code >>> 1;
					frq = readFreq(freqIn, code);
				}
				if (liveDocs.get(docAcc))
				{
					freq = frq;
					ord = i + 1;
					return accum = docAcc;
				}
			}

			ord = limit;
			freq = frq;
			accum = docAcc;
			return 0x7fffffff;
		}


		LiveDocsSegmentDocsEnum(IndexInput startFreqIn, Bits liveDocs)
		{
			this$0 = Lucene40PostingsReader.this;
			super(startFreqIn, liveDocs);
			if (!$assertionsDisabled && liveDocs == null)
				throw new AssertionError();
			else
				return;
		}
	}

	private final class AllDocsSegmentDocsEnum extends SegmentDocsEnumBase
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();
		final Lucene40PostingsReader this$0;

		public final int nextDoc()
			throws IOException
		{
			if (++start < count)
			{
				freq = freqs[start];
				return doc = docs[start];
			} else
			{
				return doc = refill();
			}
		}

		protected final int linearScan(int scanTo)
			throws IOException
		{
			int docs[] = this.docs;
			int upTo = count;
			for (int i = start; i < upTo; i++)
			{
				int d = docs[i];
				if (scanTo <= d)
				{
					start = i;
					freq = freqs[i];
					return doc = docs[i];
				}
			}

			return doc = refill();
		}

		protected int scanTo(int target)
			throws IOException
		{
			int docAcc = accum;
			int frq = 1;
			IndexInput freqIn = this.freqIn;
			boolean omitTF = indexOmitsTF;
			int loopLimit = limit;
			for (int i = ord; i < loopLimit; i++)
			{
				int code = freqIn.readVInt();
				if (omitTF)
				{
					docAcc += code;
				} else
				{
					docAcc += code >>> 1;
					frq = readFreq(freqIn, code);
				}
				if (docAcc >= target)
				{
					freq = frq;
					ord = i + 1;
					return accum = docAcc;
				}
			}

			ord = limit;
			freq = frq;
			accum = docAcc;
			return 0x7fffffff;
		}

		protected final int nextUnreadDoc()
			throws IOException
		{
			if (ord++ < limit)
			{
				int code = freqIn.readVInt();
				if (indexOmitsTF)
				{
					accum += code;
				} else
				{
					accum += code >>> 1;
					freq = readFreq(freqIn, code);
				}
				return accum;
			} else
			{
				return 0x7fffffff;
			}
		}


		AllDocsSegmentDocsEnum(IndexInput startFreqIn)
		{
			this$0 = Lucene40PostingsReader.this;
			super(startFreqIn, null);
			if (!$assertionsDisabled && liveDocs != null)
				throw new AssertionError();
			else
				return;
		}
	}

	private abstract class SegmentDocsEnumBase extends DocsEnum
	{

		protected final int docs[] = new int[64];
		protected final int freqs[] = new int[64];
		final IndexInput freqIn;
		final IndexInput startFreqIn;
		Lucene40SkipListReader skipper;
		protected boolean indexOmitsTF;
		protected boolean storePayloads;
		protected boolean storeOffsets;
		protected int limit;
		protected int ord;
		protected int doc;
		protected int accum;
		protected int freq;
		protected int maxBufferedDocId;
		protected int start;
		protected int count;
		protected long freqOffset;
		protected int skipOffset;
		protected boolean skipped;
		protected final Bits liveDocs;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();
		final Lucene40PostingsReader this$0;

		DocsEnum reset(FieldInfo fieldInfo, StandardTermState termState)
			throws IOException
		{
			indexOmitsTF = fieldInfo.getIndexOptions() == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY;
			storePayloads = fieldInfo.hasPayloads();
			storeOffsets = fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
			freqOffset = termState.freqOffset;
			skipOffset = termState.skipOffset;
			freqIn.seek(termState.freqOffset);
			limit = termState.docFreq;
			if (!$assertionsDisabled && limit <= 0)
				throw new AssertionError();
			ord = 0;
			doc = -1;
			accum = 0;
			skipped = false;
			start = -1;
			count = 0;
			freq = 1;
			if (indexOmitsTF)
				Arrays.fill(freqs, 1);
			maxBufferedDocId = -1;
			return this;
		}

		public final int freq()
		{
			return freq;
		}

		public final int docID()
		{
			return doc;
		}

		public final int advance(int target)
			throws IOException
		{
			if (++start < count && maxBufferedDocId >= target)
			{
				if (count - start > 32)
				{
					start = binarySearch(count - 1, start, target, docs);
					return nextDoc();
				} else
				{
					return linearScan(target);
				}
			} else
			{
				start = count;
				return doc = skipTo(target);
			}
		}

		private final int binarySearch(int hi, int low, int target, int docs[])
		{
label0:
			{
				int mid;
				do
				{
					if (low > hi)
						break label0;
					mid = hi + low >>> 1;
					int doc = docs[mid];
					if (doc < target)
					{
						low = mid + 1;
						continue;
					}
					if (doc <= target)
						break;
					hi = mid - 1;
				} while (true);
				low = mid;
			}
			return low - 1;
		}

		final int readFreq(IndexInput freqIn, int code)
			throws IOException
		{
			if ((code & 1) != 0)
				return 1;
			else
				return freqIn.readVInt();
		}

		protected abstract int linearScan(int i)
			throws IOException;

		protected abstract int scanTo(int i)
			throws IOException;

		protected final int refill()
			throws IOException
		{
			int doc = nextUnreadDoc();
			count = 0;
			start = -1;
			if (doc == 0x7fffffff)
				return 0x7fffffff;
			int numDocs = Math.min(docs.length, limit - ord);
			ord += numDocs;
			if (indexOmitsTF)
				count = fillDocs(numDocs);
			else
				count = fillDocsAndFreqs(numDocs);
			maxBufferedDocId = count <= 0 ? 0x7fffffff : docs[count - 1];
			return doc;
		}

		protected abstract int nextUnreadDoc()
			throws IOException;

		private final int fillDocs(int size)
			throws IOException
		{
			IndexInput freqIn = this.freqIn;
			int docs[] = this.docs;
			int docAc = accum;
			for (int i = 0; i < size; i++)
			{
				docAc += freqIn.readVInt();
				docs[i] = docAc;
			}

			accum = docAc;
			return size;
		}

		private final int fillDocsAndFreqs(int size)
			throws IOException
		{
			IndexInput freqIn = this.freqIn;
			int docs[] = this.docs;
			int freqs[] = this.freqs;
			int docAc = accum;
			for (int i = 0; i < size; i++)
			{
				int code = freqIn.readVInt();
				docAc += code >>> 1;
				freqs[i] = readFreq(freqIn, code);
				docs[i] = docAc;
			}

			accum = docAc;
			return size;
		}

		private final int skipTo(int target)
			throws IOException
		{
			if (target - skipInterval >= accum && limit >= skipMinimum)
			{
				if (skipper == null)
					skipper = new Lucene40SkipListReader(freqIn.clone(), maxSkipLevels, skipInterval);
				if (!skipped)
				{
					skipper.init(freqOffset + (long)skipOffset, freqOffset, 0L, limit, storePayloads, storeOffsets);
					skipped = true;
				}
				int newOrd = skipper.skipTo(target);
				if (newOrd > ord)
				{
					ord = newOrd;
					accum = skipper.getDoc();
					freqIn.seek(skipper.getFreqPointer());
				}
			}
			return scanTo(target);
		}


		SegmentDocsEnumBase(IndexInput startFreqIn, Bits liveDocs)
		{
			this$0 = Lucene40PostingsReader.this;
			super();
			this.startFreqIn = startFreqIn;
			freqIn = startFreqIn.clone();
			this.liveDocs = liveDocs;
		}
	}

	private static final class StandardTermState extends BlockTermState
	{

		long freqOffset;
		long proxOffset;
		int skipOffset;
		ByteArrayDataInput bytesReader;
		byte bytes[];

		public StandardTermState clone()
		{
			StandardTermState other = new StandardTermState();
			other.copyFrom(this);
			return other;
		}

		public void copyFrom(TermState _other)
		{
			super.copyFrom(_other);
			StandardTermState other = (StandardTermState)_other;
			freqOffset = other.freqOffset;
			proxOffset = other.proxOffset;
			skipOffset = other.skipOffset;
		}

		public String toString()
		{
			return (new StringBuilder()).append(super.toString()).append(" freqFP=").append(freqOffset).append(" proxFP=").append(proxOffset).append(" skipOffset=").append(skipOffset).toString();
		}

		public volatile TermState clone()
		{
			return clone();
		}

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}

		private StandardTermState()
		{
		}

	}


	private final IndexInput freqIn;
	private final IndexInput proxIn;
	int skipInterval;
	int maxSkipLevels;
	int skipMinimum;
	static final int BUFFERSIZE = 64;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsReader.desiredAssertionStatus();

	public Lucene40PostingsReader(Directory dir, FieldInfos fieldInfos, SegmentInfo segmentInfo, IOContext ioContext, String segmentSuffix)
		throws IOException
	{
		boolean success;
		IndexInput freqIn;
		IndexInput proxIn;
		success = false;
		freqIn = null;
		proxIn = null;
		freqIn = dir.openInput(IndexFileNames.segmentFileName(segmentInfo.name, segmentSuffix, "frq"), ioContext);
		CodecUtil.checkHeader(freqIn, "Lucene40PostingsWriterFrq", 0, 0);
		if (fieldInfos.hasProx())
		{
			proxIn = dir.openInput(IndexFileNames.segmentFileName(segmentInfo.name, segmentSuffix, "prx"), ioContext);
			CodecUtil.checkHeader(proxIn, "Lucene40PostingsWriterPrx", 0, 0);
		} else
		{
			proxIn = null;
		}
		this.freqIn = freqIn;
		this.proxIn = proxIn;
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				freqIn, proxIn
			});
		break MISSING_BLOCK_LABEL_151;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				freqIn, proxIn
			});
		throw exception;
	}

	public void init(IndexInput termsIn)
		throws IOException
	{
		CodecUtil.checkHeader(termsIn, "Lucene40PostingsWriterTerms", 0, 0);
		skipInterval = termsIn.readInt();
		maxSkipLevels = termsIn.readInt();
		skipMinimum = termsIn.readInt();
	}

	public BlockTermState newTermState()
	{
		return new StandardTermState();
	}

	public void close()
		throws IOException
	{
		if (freqIn != null)
			freqIn.close();
		if (proxIn != null)
			proxIn.close();
		break MISSING_BLOCK_LABEL_48;
		Exception exception;
		exception;
		if (proxIn != null)
			proxIn.close();
		throw exception;
	}

	public void readTermsBlock(IndexInput termsIn, FieldInfo fieldInfo, BlockTermState _termState)
		throws IOException
	{
		StandardTermState termState = (StandardTermState)_termState;
		int len = termsIn.readVInt();
		if (termState.bytes == null)
		{
			termState.bytes = new byte[ArrayUtil.oversize(len, 1)];
			termState.bytesReader = new ByteArrayDataInput();
		} else
		if (termState.bytes.length < len)
			termState.bytes = new byte[ArrayUtil.oversize(len, 1)];
		termsIn.readBytes(termState.bytes, 0, len);
		termState.bytesReader.reset(termState.bytes, 0, len);
	}

	public void nextTerm(FieldInfo fieldInfo, BlockTermState _termState)
		throws IOException
	{
		StandardTermState termState = (StandardTermState)_termState;
		boolean isFirstTerm = termState.termBlockOrd == 0;
		if (isFirstTerm)
			termState.freqOffset = termState.bytesReader.readVLong();
		else
			termState.freqOffset += termState.bytesReader.readVLong();
		if (!$assertionsDisabled && termState.freqOffset >= freqIn.length())
			throw new AssertionError();
		if (termState.docFreq >= skipMinimum)
		{
			termState.skipOffset = termState.bytesReader.readVInt();
			if (!$assertionsDisabled && termState.freqOffset + (long)termState.skipOffset >= freqIn.length())
				throw new AssertionError();
		}
		if (fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0)
			if (isFirstTerm)
				termState.proxOffset = termState.bytesReader.readVLong();
			else
				termState.proxOffset += termState.bytesReader.readVLong();
	}

	public DocsEnum docs(FieldInfo fieldInfo, BlockTermState termState, Bits liveDocs, DocsEnum reuse, int flags)
		throws IOException
	{
		if (canReuse(reuse, liveDocs))
			return ((SegmentDocsEnumBase)reuse).reset(fieldInfo, (StandardTermState)termState);
		else
			return newDocsEnum(liveDocs, fieldInfo, (StandardTermState)termState);
	}

	private boolean canReuse(DocsEnum reuse, Bits liveDocs)
	{
		if (reuse != null && (reuse instanceof SegmentDocsEnumBase))
		{
			SegmentDocsEnumBase docsEnum = (SegmentDocsEnumBase)reuse;
			if (docsEnum.startFreqIn == freqIn)
				return liveDocs == docsEnum.liveDocs;
		}
		return false;
	}

	private DocsEnum newDocsEnum(Bits liveDocs, FieldInfo fieldInfo, StandardTermState termState)
		throws IOException
	{
		if (liveDocs == null)
			return (new AllDocsSegmentDocsEnum(freqIn)).reset(fieldInfo, termState);
		else
			return (new LiveDocsSegmentDocsEnum(freqIn, liveDocs)).reset(fieldInfo, termState);
	}

	public DocsAndPositionsEnum docsAndPositions(FieldInfo fieldInfo, BlockTermState termState, Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
		throws IOException
	{
		boolean hasOffsets = fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
		SegmentDocsAndPositionsEnum docsEnum;
		if (fieldInfo.hasPayloads() || hasOffsets)
		{
			if (reuse == null || !(reuse instanceof SegmentFullPositionsEnum))
			{
				docsEnum = new SegmentFullPositionsEnum(freqIn, proxIn);
			} else
			{
				docsEnum = (SegmentFullPositionsEnum)reuse;
				if (((SegmentFullPositionsEnum) (docsEnum)).startFreqIn != freqIn)
					docsEnum = new SegmentFullPositionsEnum(freqIn, proxIn);
			}
			return docsEnum.reset(fieldInfo, (StandardTermState)termState, liveDocs);
		}
		if (reuse == null || !(reuse instanceof SegmentDocsAndPositionsEnum))
		{
			docsEnum = new SegmentDocsAndPositionsEnum(freqIn, proxIn);
		} else
		{
			docsEnum = (SegmentDocsAndPositionsEnum)reuse;
			if (docsEnum.startFreqIn != freqIn)
				docsEnum = new SegmentDocsAndPositionsEnum(freqIn, proxIn);
		}
		return docsEnum.reset(fieldInfo, (StandardTermState)termState, liveDocs);
	}

}
