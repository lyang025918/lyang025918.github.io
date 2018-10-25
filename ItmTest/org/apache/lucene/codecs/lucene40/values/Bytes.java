// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Bytes.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.DocValuesConsumer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			FixedStraightBytesImpl, FixedDerefBytesImpl, FixedSortedBytesImpl, VarStraightBytesImpl, 
//			VarDerefBytesImpl, VarSortedBytesImpl, Writer

public final class Bytes
{
	static abstract class BytesSortedSourceBase extends org.apache.lucene.index.DocValues.SortedSource
	{

		private final PagedBytes pagedBytes;
		protected final org.apache.lucene.util.packed.PackedInts.Reader docToOrdIndex;
		protected final org.apache.lucene.util.packed.PackedInts.Reader ordToOffsetIndex;
		protected final IndexInput datIn;
		protected final IndexInput idxIn;
		protected final BytesRef defaultValue;
		protected static final int PAGED_BYTES_BITS = 15;
		protected final org.apache.lucene.util.PagedBytes.Reader data;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Bytes.desiredAssertionStatus();

		public boolean hasPackedDocToOrd()
		{
			return true;
		}

		public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
		{
			return docToOrdIndex;
		}

		public int ord(int docID)
		{
			if (!$assertionsDisabled && docToOrdIndex.get(docID) >= (long)getValueCount())
				throw new AssertionError();
			else
				return (int)docToOrdIndex.get(docID);
		}

		protected void closeIndexInput()
			throws IOException
		{
			IOUtils.close(new Closeable[] {
				datIn, idxIn
			});
		}


		protected BytesSortedSourceBase(IndexInput datIn, IndexInput idxIn, Comparator comp, long bytesToRead, org.apache.lucene.index.DocValues.Type type, boolean hasOffsets)
			throws IOException
		{
			this(datIn, idxIn, comp, new PagedBytes(15), bytesToRead, type, hasOffsets);
		}

		protected BytesSortedSourceBase(IndexInput datIn, IndexInput idxIn, Comparator comp, PagedBytes pagedBytes, long bytesToRead, org.apache.lucene.index.DocValues.Type type, 
				boolean hasOffsets)
			throws IOException
		{
			super(type, comp);
			defaultValue = new BytesRef();
			if (!$assertionsDisabled && bytesToRead > datIn.length())
			{
				throw new AssertionError((new StringBuilder()).append(" file size is less than the expected size diff: ").append(bytesToRead - datIn.length()).append(" pos: ").append(datIn.getFilePointer()).toString());
			} else
			{
				this.datIn = datIn;
				this.pagedBytes = pagedBytes;
				this.pagedBytes.copy(datIn, bytesToRead);
				data = pagedBytes.freeze(true);
				this.idxIn = idxIn;
				ordToOffsetIndex = hasOffsets ? PackedInts.getReader(idxIn) : null;
				docToOrdIndex = PackedInts.getReader(idxIn);
				return;
			}
		}
	}

	static abstract class DerefBytesWriterBase extends BytesWriterBase
	{

		protected int size;
		protected int lastDocId;
		protected int docToEntry[];
		protected final BytesRefHash hash;
		protected final float acceptableOverheadRatio;
		protected long maxBytes;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Bytes.desiredAssertionStatus();

		protected static int writePrefixLength(DataOutput datOut, BytesRef bytes)
			throws IOException
		{
			if (bytes.length < 128)
			{
				datOut.writeByte((byte)bytes.length);
				return 1;
			} else
			{
				datOut.writeByte((byte)(0x80 | bytes.length >> 8));
				datOut.writeByte((byte)(bytes.length & 0xff));
				return 2;
			}
		}

		public void add(int docID, IndexableField value)
			throws IOException
		{
			BytesRef bytes = value.binaryValue();
			if (!$assertionsDisabled && bytes == null)
				throw new AssertionError();
			if (bytes.length == 0)
				return;
			checkSize(bytes);
			fillDefault(docID);
			int ord = hash.add(bytes);
			if (ord < 0)
				ord = -ord - 1;
			else
				maxBytes += bytes.length;
			docToEntry[docID] = ord;
			lastDocId = docID;
		}

		protected void fillDefault(int docID)
		{
			if (docID >= docToEntry.length)
			{
				int size = docToEntry.length;
				docToEntry = ArrayUtil.grow(docToEntry, 1 + docID);
				bytesUsed.addAndGet((docToEntry.length - size) * 4);
			}
			if (!$assertionsDisabled && this.size < 0)
				throw new AssertionError();
			BytesRef ref = new BytesRef(this.size);
			ref.length = this.size;
			int ord = hash.add(ref);
			if (ord < 0)
				ord = -ord - 1;
			for (int i = lastDocId + 1; i < docID; i++)
				docToEntry[i] = ord;

		}

		protected void checkSize(BytesRef bytes)
		{
			if (size == -1)
				size = bytes.length;
			else
			if (bytes.length != size)
				throw new IllegalArgumentException((new StringBuilder()).append("expected bytes size=").append(size).append(" but got ").append(bytes.length).toString());
		}

		public int getValueSize()
		{
			return size;
		}

		public void finish(int docCount)
			throws IOException
		{
			boolean success = false;
			finishInternal(docCount);
			success = true;
			releaseResources();
			if (success)
				IOUtils.close(new Closeable[] {
					getIndexOut(), getDataOut()
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					getIndexOut(), getDataOut()
				});
			break MISSING_BLOCK_LABEL_121;
			Exception exception;
			exception;
			releaseResources();
			if (success)
				IOUtils.close(new Closeable[] {
					getIndexOut(), getDataOut()
				});
			else
				IOUtils.closeWhileHandlingException(new Closeable[] {
					getIndexOut(), getDataOut()
				});
			throw exception;
		}

		protected abstract void finishInternal(int i)
			throws IOException;

		protected void releaseResources()
		{
			hash.close();
			bytesUsed.addAndGet(-docToEntry.length * 4);
			docToEntry = null;
		}

		protected void writeIndex(IndexOutput idxOut, int docCount, long maxValue, int toEntry[])
			throws IOException
		{
			writeIndex(idxOut, docCount, maxValue, (int[])null, toEntry);
		}

		protected void writeIndex(IndexOutput idxOut, int docCount, long maxValue, int addresses[], int toEntry[])
			throws IOException
		{
			org.apache.lucene.util.packed.PackedInts.Writer w = PackedInts.getWriter(idxOut, docCount, PackedInts.bitsRequired(maxValue), acceptableOverheadRatio);
			int limit = docCount <= docToEntry.length ? docCount : docToEntry.length;
			if (!$assertionsDisabled && toEntry.length < limit - 1)
				throw new AssertionError();
			if (addresses != null)
			{
				for (int i = 0; i < limit; i++)
				{
					if (!$assertionsDisabled && addresses[toEntry[i]] < 0)
						throw new AssertionError();
					w.add(addresses[toEntry[i]]);
				}

			} else
			{
				for (int i = 0; i < limit; i++)
				{
					if (!$assertionsDisabled && toEntry[i] < 0)
						throw new AssertionError();
					w.add(toEntry[i]);
				}

			}
			for (int i = limit; i < docCount; i++)
				w.add(0L);

			w.finish();
		}

		protected void writeIndex(IndexOutput idxOut, int docCount, long maxValue, long addresses[], int toEntry[])
			throws IOException
		{
			org.apache.lucene.util.packed.PackedInts.Writer w = PackedInts.getWriter(idxOut, docCount, PackedInts.bitsRequired(maxValue), acceptableOverheadRatio);
			int limit = docCount <= docToEntry.length ? docCount : docToEntry.length;
			if (!$assertionsDisabled && toEntry.length < limit - 1)
				throw new AssertionError();
			if (addresses != null)
			{
				for (int i = 0; i < limit; i++)
				{
					if (!$assertionsDisabled && addresses[toEntry[i]] < 0L)
						throw new AssertionError();
					w.add(addresses[toEntry[i]]);
				}

			} else
			{
				for (int i = 0; i < limit; i++)
				{
					if (!$assertionsDisabled && toEntry[i] < 0)
						throw new AssertionError();
					w.add(toEntry[i]);
				}

			}
			for (int i = limit; i < docCount; i++)
				w.add(0L);

			w.finish();
		}


		protected DerefBytesWriterBase(Directory dir, String id, String codecNameIdx, String codecNameDat, int codecVersion, Counter bytesUsed, IOContext context, 
				org.apache.lucene.index.DocValues.Type type)
		{
			this(dir, id, codecNameIdx, codecNameDat, codecVersion, ((org.apache.lucene.util.ByteBlockPool.Allocator) (new org.apache.lucene.util.ByteBlockPool.DirectTrackingAllocator(32768, bytesUsed))), bytesUsed, context, 0.2F, type);
		}

		protected DerefBytesWriterBase(Directory dir, String id, String codecNameIdx, String codecNameDat, int codecVersion, Counter bytesUsed, IOContext context, 
				float acceptableOverheadRatio, org.apache.lucene.index.DocValues.Type type)
		{
			this(dir, id, codecNameIdx, codecNameDat, codecVersion, ((org.apache.lucene.util.ByteBlockPool.Allocator) (new org.apache.lucene.util.ByteBlockPool.DirectTrackingAllocator(32768, bytesUsed))), bytesUsed, context, acceptableOverheadRatio, type);
		}

		protected DerefBytesWriterBase(Directory dir, String id, String codecNameIdx, String codecNameDat, int codecVersion, org.apache.lucene.util.ByteBlockPool.Allocator allocator, Counter bytesUsed, 
				IOContext context, float acceptableOverheadRatio, org.apache.lucene.index.DocValues.Type type)
		{
			super(dir, id, codecNameIdx, codecNameDat, codecVersion, bytesUsed, context, type);
			size = -1;
			lastDocId = -1;
			maxBytes = 0L;
			hash = new BytesRefHash(new ByteBlockPool(allocator), 16, new org.apache.lucene.util.BytesRefHash.TrackingDirectBytesStartArray(16, bytesUsed));
			docToEntry = new int[1];
			bytesUsed.addAndGet(4L);
			this.acceptableOverheadRatio = acceptableOverheadRatio;
		}
	}

	static abstract class BytesReaderBase extends DocValues
	{

		protected final IndexInput idxIn;
		protected final IndexInput datIn;
		protected final int version;
		protected final String id;
		protected final org.apache.lucene.index.DocValues.Type type;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Bytes.desiredAssertionStatus();

		protected final IndexInput cloneData()
		{
			if (!$assertionsDisabled && datIn == null)
				throw new AssertionError();
			else
				return datIn.clone();
		}

		protected final IndexInput cloneIndex()
		{
			if (!$assertionsDisabled && idxIn == null)
				throw new AssertionError();
			else
				return idxIn.clone();
		}

		public void close()
			throws IOException
		{
			super.close();
			IOUtils.close(new Closeable[] {
				datIn, idxIn
			});
			break MISSING_BLOCK_LABEL_52;
			Exception exception;
			exception;
			IOUtils.close(new Closeable[] {
				datIn, idxIn
			});
			throw exception;
		}

		public org.apache.lucene.index.DocValues.Type getType()
		{
			return type;
		}


		protected BytesReaderBase(Directory dir, String id, String codecNameIdx, String codecNameDat, int maxVersion, boolean doIndex, IOContext context, 
				org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			IndexInput dataIn;
			IndexInput indexIn;
			boolean success;
			dataIn = null;
			indexIn = null;
			success = false;
			dataIn = dir.openInput(IndexFileNames.segmentFileName(id, "dv", "dat"), context);
			version = CodecUtil.checkHeader(dataIn, codecNameDat, maxVersion, maxVersion);
			if (doIndex)
			{
				indexIn = dir.openInput(IndexFileNames.segmentFileName(id, "dv", "idx"), context);
				int version2 = CodecUtil.checkHeader(indexIn, codecNameIdx, maxVersion, maxVersion);
				if (!$assertionsDisabled && version != version2)
					throw new AssertionError();
			}
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					dataIn, indexIn
				});
			break MISSING_BLOCK_LABEL_155;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					dataIn, indexIn
				});
			throw exception;
			datIn = dataIn;
			idxIn = indexIn;
			this.type = type;
			this.id = id;
			return;
		}
	}

	static abstract class BytesWriterBase extends Writer
	{

		private final String id;
		private IndexOutput idxOut;
		private IndexOutput datOut;
		protected BytesRef bytesRef;
		private final Directory dir;
		private final String codecNameIdx;
		private final String codecNameDat;
		private final int version;
		private final IOContext context;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Bytes.desiredAssertionStatus();

		protected IndexOutput getOrCreateDataOut()
			throws IOException
		{
			boolean success;
			if (datOut != null)
				break MISSING_BLOCK_LABEL_115;
			success = false;
			if (!$assertionsDisabled && codecNameDat == null)
				throw new AssertionError();
			datOut = dir.createOutput(IndexFileNames.segmentFileName(id, "dv", "dat"), context);
			CodecUtil.writeHeader(datOut, codecNameDat, version);
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			break MISSING_BLOCK_LABEL_115;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					datOut
				});
			throw exception;
			return datOut;
		}

		protected IndexOutput getIndexOut()
		{
			return idxOut;
		}

		protected IndexOutput getDataOut()
		{
			return datOut;
		}

		protected IndexOutput getOrCreateIndexOut()
			throws IOException
		{
			boolean success = false;
			if (idxOut == null)
			{
				if (!$assertionsDisabled && codecNameIdx == null)
					throw new AssertionError();
				idxOut = dir.createOutput(IndexFileNames.segmentFileName(id, "dv", "idx"), context);
				CodecUtil.writeHeader(idxOut, codecNameIdx, version);
			}
			success = true;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					idxOut
				});
			break MISSING_BLOCK_LABEL_115;
			Exception exception;
			exception;
			if (!success)
				IOUtils.closeWhileHandlingException(new Closeable[] {
					idxOut
				});
			throw exception;
			return idxOut;
		}

		public abstract void finish(int i)
			throws IOException;


		protected BytesWriterBase(Directory dir, String id, String codecNameIdx, String codecNameDat, int version, Counter bytesUsed, IOContext context, 
				org.apache.lucene.index.DocValues.Type type)
		{
			super(bytesUsed, type);
			bytesRef = new BytesRef();
			this.id = id;
			this.dir = dir;
			this.codecNameIdx = codecNameIdx;
			this.codecNameDat = codecNameDat;
			this.version = version;
			this.context = context;
			if (!$assertionsDisabled && codecNameDat == null && codecNameIdx == null)
				throw new AssertionError("both codec names are null");
			if (!$assertionsDisabled && (codecNameDat == null || codecNameDat.equals(codecNameIdx)) && (codecNameIdx == null || codecNameIdx.equals(codecNameDat)))
				throw new AssertionError("index and data codec names must not be equal");
			else
				return;
		}
	}

	static abstract class BytesSourceBase extends org.apache.lucene.index.DocValues.Source
	{

		private final PagedBytes pagedBytes;
		protected final IndexInput datIn;
		protected final IndexInput idxIn;
		protected static final int PAGED_BYTES_BITS = 15;
		protected final org.apache.lucene.util.PagedBytes.Reader data;
		protected final long totalLengthInBytes;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Bytes.desiredAssertionStatus();


		protected BytesSourceBase(IndexInput datIn, IndexInput idxIn, PagedBytes pagedBytes, long bytesToRead, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(type);
			if (!$assertionsDisabled && bytesToRead > datIn.length())
			{
				throw new AssertionError((new StringBuilder()).append(" file size is less than the expected size diff: ").append(bytesToRead - datIn.length()).append(" pos: ").append(datIn.getFilePointer()).toString());
			} else
			{
				this.datIn = datIn;
				totalLengthInBytes = bytesToRead;
				this.pagedBytes = pagedBytes;
				this.pagedBytes.copy(datIn, bytesToRead);
				data = pagedBytes.freeze(true);
				this.idxIn = idxIn;
				return;
			}
		}
	}

	public static final class Mode extends Enum
	{

		public static final Mode STRAIGHT;
		public static final Mode DEREF;
		public static final Mode SORTED;
		private static final Mode $VALUES[];

		public static Mode[] values()
		{
			return (Mode[])$VALUES.clone();
		}

		public static Mode valueOf(String name)
		{
			return (Mode)Enum.valueOf(org/apache/lucene/codecs/lucene40/values/Bytes$Mode, name);
		}

		static 
		{
			STRAIGHT = new Mode("STRAIGHT", 0);
			DEREF = new Mode("DEREF", 1);
			SORTED = new Mode("SORTED", 2);
			$VALUES = (new Mode[] {
				STRAIGHT, DEREF, SORTED
			});
		}

		private Mode(String s, int i)
		{
			super(s, i);
		}
	}


	static final String DV_SEGMENT_SUFFIX = "dv";

	private Bytes()
	{
	}

	public static DocValuesConsumer getWriter(Directory dir, String id, Mode mode, boolean fixedSize, Comparator sortComparator, Counter bytesUsed, IOContext context, float acceptableOverheadRatio)
	{
		if (sortComparator == null)
			sortComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		if (fixedSize)
		{
			if (mode == Mode.STRAIGHT)
				return new FixedStraightBytesImpl.Writer(dir, id, bytesUsed, context);
			if (mode == Mode.DEREF)
				return new FixedDerefBytesImpl.Writer(dir, id, bytesUsed, context);
			if (mode == Mode.SORTED)
				return new FixedSortedBytesImpl.Writer(dir, id, sortComparator, bytesUsed, context, acceptableOverheadRatio);
		} else
		{
			if (mode == Mode.STRAIGHT)
				return new VarStraightBytesImpl.Writer(dir, id, bytesUsed, context);
			if (mode == Mode.DEREF)
				return new VarDerefBytesImpl.Writer(dir, id, bytesUsed, context);
			if (mode == Mode.SORTED)
				return new VarSortedBytesImpl.Writer(dir, id, sortComparator, bytesUsed, context, acceptableOverheadRatio);
		}
		throw new IllegalArgumentException("");
	}

	public static DocValues getValues(Directory dir, String id, Mode mode, boolean fixedSize, int maxDoc, Comparator sortComparator, IOContext context)
		throws IOException
	{
		if (sortComparator == null)
			sortComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		if (fixedSize)
		{
			if (mode == Mode.STRAIGHT)
				return new FixedStraightBytesImpl.FixedStraightReader(dir, id, maxDoc, context);
			if (mode == Mode.DEREF)
				return new FixedDerefBytesImpl.FixedDerefReader(dir, id, maxDoc, context);
			if (mode == Mode.SORTED)
				return new FixedSortedBytesImpl.Reader(dir, id, maxDoc, context, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED, sortComparator);
		} else
		{
			if (mode == Mode.STRAIGHT)
				return new VarStraightBytesImpl.VarStraightReader(dir, id, maxDoc, context);
			if (mode == Mode.DEREF)
				return new VarDerefBytesImpl.VarDerefReader(dir, id, maxDoc, context);
			if (mode == Mode.SORTED)
				return new VarSortedBytesImpl.Reader(dir, id, maxDoc, context, org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED, sortComparator);
		}
		throw new IllegalArgumentException((new StringBuilder()).append("Illegal Mode: ").append(mode).toString());
	}
}
