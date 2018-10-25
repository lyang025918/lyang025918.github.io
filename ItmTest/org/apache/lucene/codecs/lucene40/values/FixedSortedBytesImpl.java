// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FixedSortedBytesImpl.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			Bytes

class FixedSortedBytesImpl
{
	static final class DirectFixedSortedSource extends org.apache.lucene.index.DocValues.SortedSource
	{

		final org.apache.lucene.util.packed.PackedInts.Reader docToOrdIndex;
		private final IndexInput datIn;
		private final long basePointer;
		private final int size;
		private final int valueCount;

		public int ord(int docID)
		{
			return (int)docToOrdIndex.get(docID);
		}

		public boolean hasPackedDocToOrd()
		{
			return true;
		}

		public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
		{
			return docToOrdIndex;
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			datIn.seek(basePointer + (long)(size * ord));
			bytesRef.offset = 0;
			bytesRef.grow(size);
			datIn.readBytes(bytesRef.bytes, 0, size);
			bytesRef.length = size;
			return bytesRef;
			IOException ex;
			ex;
			throw new IllegalStateException("failed to getByOrd", ex);
		}

		public int getValueCount()
		{
			return valueCount;
		}

		DirectFixedSortedSource(IndexInput datIn, IndexInput idxIn, int size, int valueCount, Comparator comp, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(type, comp);
			docToOrdIndex = PackedInts.getDirectReader(idxIn);
			basePointer = datIn.getFilePointer();
			this.datIn = datIn;
			this.size = size;
			this.valueCount = valueCount;
		}
	}

	static final class FixedSortedSource extends Bytes.BytesSortedSourceBase
	{

		private final int valueCount;
		private final int size;

		public int getValueCount()
		{
			return valueCount;
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			return data.fillSlice(bytesRef, ord * size, size);
		}

		FixedSortedSource(IndexInput datIn, IndexInput idxIn, int size, int numValues, Comparator comp)
			throws IOException
		{
			super(datIn, idxIn, comp, size * numValues, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED, false);
			this.size = size;
			valueCount = numValues;
			closeIndexInput();
		}
	}

	static final class Reader extends Bytes.BytesReaderBase
	{

		private final int size;
		private final int valueCount;
		private final Comparator comparator;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new FixedSortedSource(cloneData(), cloneIndex(), size, valueCount, comparator);
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectFixedSortedSource(cloneData(), cloneIndex(), size, valueCount, comparator, type);
		}

		public int getValueSize()
		{
			return size;
		}

		public Reader(Directory dir, String id, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type, Comparator comparator)
			throws IOException
		{
			super(dir, id, "FixedSortedBytesIdx", "FixedSortedBytesDat", 0, true, context, type);
			size = datIn.readInt();
			valueCount = idxIn.readInt();
			this.comparator = comparator;
		}
	}

	static final class Writer extends Bytes.DerefBytesWriterBase
	{

		private final Comparator comp;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/FixedSortedBytesImpl.desiredAssertionStatus();

		public void merge(MergeState mergeState, DocValues docValues[])
			throws IOException
		{
			boolean success = false;
			org.apache.lucene.index.SortedBytesMergeUtils.MergeContext ctx = SortedBytesMergeUtils.init(org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED, docValues, comp, mergeState.segmentInfo.getDocCount());
			List slices = SortedBytesMergeUtils.buildSlices(mergeState.docBase, mergeState.docMaps, docValues, ctx);
			IndexOutput datOut = getOrCreateDataOut();
			datOut.writeInt(ctx.sizePerValues);
			int maxOrd = SortedBytesMergeUtils.mergeRecords(ctx, new org.apache.lucene.index.SortedBytesMergeUtils.IndexOutputBytesRefConsumer(datOut), slices);
			IndexOutput idxOut = getOrCreateIndexOut();
			idxOut.writeInt(maxOrd);
			org.apache.lucene.util.packed.PackedInts.Writer ordsWriter = PackedInts.getWriter(idxOut, ctx.docToEntry.length, PackedInts.bitsRequired(maxOrd), 0.2F);
			org.apache.lucene.index.SortedBytesMergeUtils.SortedSourceSlice slice;
			for (Iterator i$ = slices.iterator(); i$.hasNext(); slice.writeOrds(ordsWriter))
				slice = (org.apache.lucene.index.SortedBytesMergeUtils.SortedSourceSlice)i$.next();

			ordsWriter.finish();
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
			break MISSING_BLOCK_LABEL_268;
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

		public void finishInternal(int docCount)
			throws IOException
		{
			fillDefault(docCount);
			IndexOutput datOut = getOrCreateDataOut();
			int count = hash.size();
			int address[] = new int[count];
			datOut.writeInt(size);
			if (size != -1)
			{
				int sortedEntries[] = hash.sort(comp);
				BytesRef spare = new BytesRef(size);
				for (int i = 0; i < count; i++)
				{
					int e = sortedEntries[i];
					BytesRef bytes = hash.get(e, spare);
					if (!$assertionsDisabled && bytes.length != size)
						throw new AssertionError();
					datOut.writeBytes(bytes.bytes, bytes.offset, bytes.length);
					address[e] = i;
				}

			}
			IndexOutput idxOut = getOrCreateIndexOut();
			idxOut.writeInt(count);
			writeIndex(idxOut, docCount, count, address, docToEntry);
		}


		public Writer(Directory dir, String id, Comparator comp, Counter bytesUsed, IOContext context, float acceptableOverheadRatio)
		{
			super(dir, id, "FixedSortedBytesIdx", "FixedSortedBytesDat", 0, bytesUsed, context, acceptableOverheadRatio, org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED);
			this.comp = comp;
		}
	}


	static final String CODEC_NAME_IDX = "FixedSortedBytesIdx";
	static final String CODEC_NAME_DAT = "FixedSortedBytesDat";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	FixedSortedBytesImpl()
	{
	}
}
