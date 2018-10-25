// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VarSortedBytesImpl.java

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

final class VarSortedBytesImpl
{
	private static final class DirectSortedSource extends org.apache.lucene.index.DocValues.SortedSource
	{

		private final org.apache.lucene.util.packed.PackedInts.Reader docToOrdIndex;
		private final org.apache.lucene.util.packed.PackedInts.Reader ordToOffsetIndex;
		private final IndexInput datIn;
		private final long basePointer;
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
			long offset = ordToOffsetIndex.get(ord);
			long nextOffset = ordToOffsetIndex.get(1 + ord);
			datIn.seek(basePointer + offset);
			int length = (int)(nextOffset - offset);
			bytesRef.offset = 0;
			bytesRef.grow(length);
			datIn.readBytes(bytesRef.bytes, 0, length);
			bytesRef.length = length;
			return bytesRef;
			IOException ex;
			ex;
			throw new IllegalStateException("failed", ex);
		}

		public int getValueCount()
		{
			return valueCount;
		}

		DirectSortedSource(IndexInput datIn, IndexInput idxIn, Comparator comparator, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(type, comparator);
			idxIn.readLong();
			ordToOffsetIndex = PackedInts.getDirectReader(idxIn);
			valueCount = ordToOffsetIndex.size() - 1;
			ordToOffsetIndex.get(valueCount);
			docToOrdIndex = PackedInts.getDirectReader(idxIn.clone());
			basePointer = datIn.getFilePointer();
			this.datIn = datIn;
		}
	}

	private static final class VarSortedSource extends Bytes.BytesSortedSourceBase
	{

		private final int valueCount;

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			long offset = ordToOffsetIndex.get(ord);
			long nextOffset = ordToOffsetIndex.get(1 + ord);
			data.fillSlice(bytesRef, offset, (int)(nextOffset - offset));
			return bytesRef;
		}

		public int getValueCount()
		{
			return valueCount;
		}

		VarSortedSource(IndexInput datIn, IndexInput idxIn, Comparator comp)
			throws IOException
		{
			super(datIn, idxIn, comp, idxIn.readLong(), org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED, true);
			valueCount = ordToOffsetIndex.size() - 1;
			closeIndexInput();
		}
	}

	public static class Reader extends Bytes.BytesReaderBase
	{

		private final Comparator comparator;

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			return new VarSortedSource(cloneData(), cloneIndex(), comparator);
		}

		public org.apache.lucene.index.DocValues.Source getDirectSource()
			throws IOException
		{
			return new DirectSortedSource(cloneData(), cloneIndex(), comparator, getType());
		}

		public volatile org.apache.lucene.index.DocValues.Type getType()
		{
			return super.getType();
		}

		public volatile void close()
			throws IOException
		{
			super.close();
		}

		Reader(Directory dir, String id, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type, Comparator comparator)
			throws IOException
		{
			super(dir, id, "VarDerefBytesIdx", "VarDerefBytesDat", 0, true, context, type);
			this.comparator = comparator;
		}
	}

	static final class Writer extends Bytes.DerefBytesWriterBase
	{

		private final Comparator comp;

		public void merge(MergeState mergeState, DocValues docValues[])
			throws IOException
		{
			boolean success = false;
			org.apache.lucene.index.SortedBytesMergeUtils.MergeContext ctx = SortedBytesMergeUtils.init(org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED, docValues, comp, mergeState.segmentInfo.getDocCount());
			List slices = SortedBytesMergeUtils.buildSlices(mergeState.docBase, mergeState.docMaps, docValues, ctx);
			IndexOutput datOut = getOrCreateDataOut();
			ctx.offsets = new long[1];
			int maxOrd = SortedBytesMergeUtils.mergeRecords(ctx, new org.apache.lucene.index.SortedBytesMergeUtils.IndexOutputBytesRefConsumer(datOut), slices);
			long offsets[] = ctx.offsets;
			maxBytes = offsets[maxOrd - 1];
			IndexOutput idxOut = getOrCreateIndexOut();
			idxOut.writeLong(maxBytes);
			org.apache.lucene.util.packed.PackedInts.Writer offsetWriter = PackedInts.getWriter(idxOut, maxOrd + 1, PackedInts.bitsRequired(maxBytes), 0.2F);
			offsetWriter.add(0L);
			for (int i = 0; i < maxOrd; i++)
				offsetWriter.add(offsets[i]);

			offsetWriter.finish();
			org.apache.lucene.util.packed.PackedInts.Writer ordsWriter = PackedInts.getWriter(idxOut, ctx.docToEntry.length, PackedInts.bitsRequired(maxOrd - 1), 0.2F);
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
			break MISSING_BLOCK_LABEL_345;
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

		protected void checkSize(BytesRef bytesref)
		{
		}

		public void finishInternal(int docCount)
			throws IOException
		{
			fillDefault(docCount);
			int count = hash.size();
			IndexOutput datOut = getOrCreateDataOut();
			IndexOutput idxOut = getOrCreateIndexOut();
			long offset = 0L;
			int index[] = new int[count];
			int sortedEntries[] = hash.sort(comp);
			idxOut.writeLong(maxBytes);
			org.apache.lucene.util.packed.PackedInts.Writer offsetWriter = PackedInts.getWriter(idxOut, count + 1, PackedInts.bitsRequired(maxBytes), 0.2F);
			BytesRef spare = new BytesRef();
			for (int i = 0; i < count; i++)
			{
				int e = sortedEntries[i];
				offsetWriter.add(offset);
				index[e] = i;
				BytesRef bytes = hash.get(e, spare);
				datOut.writeBytes(bytes.bytes, bytes.offset, bytes.length);
				offset += bytes.length;
			}

			offsetWriter.add(offset);
			offsetWriter.finish();
			writeIndex(idxOut, docCount, count, index, docToEntry);
		}

		public Writer(Directory dir, String id, Comparator comp, Counter bytesUsed, IOContext context, float acceptableOverheadRatio)
		{
			super(dir, id, "VarDerefBytesIdx", "VarDerefBytesDat", 0, bytesUsed, context, acceptableOverheadRatio, org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED);
			this.comp = comp;
			size = 0;
		}
	}


	static final String CODEC_NAME_IDX = "VarDerefBytesIdx";
	static final String CODEC_NAME_DAT = "VarDerefBytesDat";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;

	VarSortedBytesImpl()
	{
	}
}
