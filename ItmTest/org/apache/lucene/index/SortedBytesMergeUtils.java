// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SortedBytesMergeUtils.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.index:
//			DocValues, MergeState

public final class SortedBytesMergeUtils
{
	private static final class MergeQueue extends PriorityQueue
	{

		final Comparator comp;

		protected boolean lessThan(SortedSourceSlice a, SortedSourceSlice b)
		{
			int cmp = comp.compare(a.current, b.current);
			if (cmp != 0)
				return cmp < 0;
			else
				return a.docToOrdStart < b.docToOrdStart;
		}

		protected volatile boolean lessThan(Object x0, Object x1)
		{
			return lessThan((SortedSourceSlice)x0, (SortedSourceSlice)x1);
		}

		public MergeQueue(int maxSize, Comparator comp)
		{
			super(maxSize);
			this.comp = comp;
		}
	}

	private static final class MissingValueSource extends DocValues.SortedSource
	{

		private BytesRef missingValue;

		public int ord(int docID)
		{
			return 0;
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			bytesRef.copyBytes(missingValue);
			return bytesRef;
		}

		public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
		{
			return null;
		}

		public int getValueCount()
		{
			return 1;
		}

		public MissingValueSource(MergeContext ctx)
		{
			super(ctx.type, ctx.comp);
			missingValue = ctx.missingValue;
		}
	}

	public static class SortedSourceSlice
	{

		final DocValues.SortedSource source;
		final int readerIdx;
		final int docIDToRelativeOrd[];
		final int ordMapping[];
		final int docToOrdStart;
		final int docToOrdEnd;
		BytesRef current;
		int relativeOrd;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/SortedBytesMergeUtils.desiredAssertionStatus();

		private static int numDocs(int docBase[], int mergedDocCount, int readerIndex)
		{
			if (readerIndex == docBase.length - 1)
				return mergedDocCount - docBase[readerIndex];
			else
				return docBase[readerIndex + 1] - docBase[readerIndex];
		}

		BytesRef next()
		{
			for (int i = relativeOrd + 1; i < ordMapping.length; i++)
				if (ordMapping[i] != 0)
				{
					source.getByOrd(i, current);
					relativeOrd = i;
					return current;
				}

			return null;
		}

		public int[] toAbsolutOrds(int docToOrd[])
		{
			for (int i = docToOrdStart; i < docToOrdEnd; i++)
			{
				int mappedOrd = docIDToRelativeOrd[i];
				if (!$assertionsDisabled && mappedOrd >= ordMapping.length)
					throw new AssertionError();
				if (!$assertionsDisabled && ordMapping[mappedOrd] <= 0)
					throw new AssertionError("illegal mapping ord maps to an unreferenced value");
				docToOrd[i] = ordMapping[mappedOrd] - 1;
			}

			return docToOrd;
		}

		public void writeOrds(org.apache.lucene.util.packed.PackedInts.Writer writer)
			throws IOException
		{
			for (int i = docToOrdStart; i < docToOrdEnd; i++)
			{
				int mappedOrd = docIDToRelativeOrd[i];
				if (!$assertionsDisabled && mappedOrd >= ordMapping.length)
					throw new AssertionError();
				if (!$assertionsDisabled && ordMapping[mappedOrd] <= 0)
					throw new AssertionError("illegal mapping ord maps to an unreferenced value");
				writer.add(ordMapping[mappedOrd] - 1);
			}

		}


		SortedSourceSlice(int readerIdx, DocValues.SortedSource source, int docBase[], int mergeDocCount, int docToOrd[])
		{
			current = new BytesRef();
			relativeOrd = -1;
			this.readerIdx = readerIdx;
			this.source = source;
			docIDToRelativeOrd = docToOrd;
			ordMapping = new int[source.getValueCount()];
			docToOrdStart = docBase[readerIdx];
			docToOrdEnd = docToOrdStart + numDocs(docBase, mergeDocCount, readerIdx);
		}
	}

	private static final class RecordMerger
	{

		private final MergeQueue queue;
		private final SortedSourceSlice top[];
		private int numTop;
		BytesRef current;
		int currentOrd;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/SortedBytesMergeUtils.desiredAssertionStatus();

		private void pullTop()
		{
			if (!$assertionsDisabled && numTop != 0)
				throw new AssertionError();
			if (!$assertionsDisabled && currentOrd < 0)
				throw new AssertionError();
			do
			{
				SortedSourceSlice popped = top[numTop++] = (SortedSourceSlice)queue.pop();
				popped.ordMapping[popped.relativeOrd] = currentOrd + 1;
			} while (queue.size() != 0 && ((SortedSourceSlice)queue.top()).current.bytesEquals(top[0].current));
			current = top[0].current;
		}

		private void pushTop()
		{
			for (int i = 0; i < numTop; i++)
			{
				top[i].current = top[i].next();
				if (top[i].current != null)
					queue.add(top[i]);
			}

			currentOrd++;
			numTop = 0;
		}





		RecordMerger(MergeQueue queue, SortedSourceSlice top[])
		{
			currentOrd = -1;
			this.queue = queue;
			this.top = top;
			numTop = top.length;
		}
	}

	public static final class IndexOutputBytesRefConsumer
		implements BytesRefConsumer
	{

		private final IndexOutput datOut;

		public void consume(BytesRef currentMergedBytes, int ord, long offset)
			throws IOException
		{
			datOut.writeBytes(currentMergedBytes.bytes, currentMergedBytes.offset, currentMergedBytes.length);
		}

		public IndexOutputBytesRefConsumer(IndexOutput datOut)
		{
			this.datOut = datOut;
		}
	}

	public static interface BytesRefConsumer
	{

		public abstract void consume(BytesRef bytesref, int i, long l)
			throws IOException;
	}

	public static final class MergeContext
	{

		private final Comparator comp;
		private final BytesRef missingValue = new BytesRef();
		public final int sizePerValues;
		final DocValues.Type type;
		public final int docToEntry[];
		public long offsets[];
		static final boolean $assertionsDisabled = !org/apache/lucene/index/SortedBytesMergeUtils.desiredAssertionStatus();

		public int getMergeDocCount()
		{
			return docToEntry.length;
		}




		public MergeContext(Comparator comp, int mergeDocCount, int size, DocValues.Type type)
		{
			if (!$assertionsDisabled && type != DocValues.Type.BYTES_FIXED_SORTED && type != DocValues.Type.BYTES_VAR_SORTED)
				throw new AssertionError();
			this.comp = comp;
			sizePerValues = size;
			this.type = type;
			if (size > 0)
			{
				missingValue.grow(size);
				missingValue.length = size;
			}
			docToEntry = new int[mergeDocCount];
		}
	}


	static final boolean $assertionsDisabled = !org/apache/lucene/index/SortedBytesMergeUtils.desiredAssertionStatus();

	private SortedBytesMergeUtils()
	{
	}

	public static MergeContext init(DocValues.Type type, DocValues docValues[], Comparator comp, int mergeDocCount)
	{
		int size = -1;
		if (type == DocValues.Type.BYTES_FIXED_SORTED)
		{
			DocValues arr$[] = docValues;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				DocValues indexDocValues = arr$[i$];
				if (indexDocValues != null)
				{
					size = indexDocValues.getValueSize();
					break;
				}
				i$++;
			} while (true);
			if (!$assertionsDisabled && size < 0)
				throw new AssertionError();
		}
		return new MergeContext(comp, mergeDocCount, size, type);
	}

	public static List buildSlices(int docBases[], MergeState.DocMap docMaps[], DocValues docValues[], MergeContext ctx)
		throws IOException
	{
		List slices = new ArrayList();
		for (int i = 0; i < docValues.length; i++)
		{
			SortedSourceSlice nextSlice;
			DocValues.Source directSource;
			if (docValues[i] != null && (directSource = docValues[i].getDirectSource()) != null)
			{
				SortedSourceSlice slice = new SortedSourceSlice(i, directSource.asSortedSource(), docBases, ctx.getMergeDocCount(), ctx.docToEntry);
				nextSlice = slice;
			} else
			{
				nextSlice = new SortedSourceSlice(i, new MissingValueSource(ctx), docBases, ctx.getMergeDocCount(), ctx.docToEntry);
			}
			createOrdMapping(docBases, docMaps, nextSlice);
			slices.add(nextSlice);
		}

		return Collections.unmodifiableList(slices);
	}

	private static void createOrdMapping(int docBases[], MergeState.DocMap docMaps[], SortedSourceSlice currentSlice)
	{
		int readerIdx = currentSlice.readerIdx;
		MergeState.DocMap currentDocMap = docMaps[readerIdx];
		int docBase = currentSlice.docToOrdStart;
		if (!$assertionsDisabled && docBase != docBases[readerIdx])
			throw new AssertionError();
		if (currentDocMap != null && currentDocMap.hasDeletions())
		{
			for (int i = 0; i < currentDocMap.maxDoc(); i++)
			{
				int doc = currentDocMap.get(i);
				if (doc != -1)
				{
					int ord = currentSlice.source.ord(i);
					currentSlice.docIDToRelativeOrd[docBase + doc] = ord;
					currentSlice.ordMapping[ord] = ord + 1;
				}
			}

		} else
		{
			int numDocs = currentSlice.docToOrdEnd - currentSlice.docToOrdStart;
			for (int doc = 0; doc < numDocs; doc++)
			{
				int ord = currentSlice.source.ord(doc);
				currentSlice.docIDToRelativeOrd[docBase + doc] = ord;
				currentSlice.ordMapping[ord] = ord + 1;
			}

		}
	}

	public static int mergeRecords(MergeContext ctx, BytesRefConsumer consumer, List slices)
		throws IOException
	{
		RecordMerger merger = new RecordMerger(new MergeQueue(slices.size(), ctx.comp), (SortedSourceSlice[])slices.toArray(new SortedSourceSlice[0]));
		long offsets[] = ctx.offsets;
		boolean recordOffsets = offsets != null;
		long offset = 0L;
		merger.pushTop();
		for (; merger.queue.size() > 0; merger.pushTop())
		{
			merger.pullTop();
			BytesRef currentMergedBytes = merger.current;
			if (!$assertionsDisabled && ctx.sizePerValues != -1 && ctx.sizePerValues != currentMergedBytes.length)
				throw new AssertionError((new StringBuilder()).append("size: ").append(ctx.sizePerValues).append(" spare: ").append(currentMergedBytes.length).toString());
			offset += currentMergedBytes.length;
			if (recordOffsets)
			{
				if (merger.currentOrd >= offsets.length)
					offsets = ArrayUtil.grow(offsets, merger.currentOrd + 1);
				offsets[merger.currentOrd] = offset;
			}
			consumer.consume(currentMergedBytes, merger.currentOrd, offset);
		}

		ctx.offsets = offsets;
		if (!$assertionsDisabled && offsets != null && offsets[merger.currentOrd - 1] != offset)
			throw new AssertionError();
		else
			return merger.currentOrd;
	}

}
