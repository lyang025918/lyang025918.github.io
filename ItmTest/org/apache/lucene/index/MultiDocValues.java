// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiDocValues.java

package org.apache.lucene.index;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.index:
//			DocValues, AtomicReader, CompositeReader, AtomicReaderContext, 
//			TypePromoter, IndexReader, SortedBytesMergeUtils, ReaderUtil, 
//			MergeState, FieldInfos, FieldInfo

class MultiDocValues extends DocValues
{
	private static class EmptyFixedSource extends EmptySource
	{

		private final int valueSize;
		private final byte valueArray[];

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			ref.grow(valueSize);
			ref.length = valueSize;
			Arrays.fill(ref.bytes, ref.offset, ref.offset + valueSize, (byte)0);
			return ref;
		}

		public double getFloat(int docID)
		{
			return 0.0D;
		}

		public long getInt(int docID)
		{
			return 0L;
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			bytesRef.bytes = valueArray;
			bytesRef.length = valueSize;
			bytesRef.offset = 0;
			return bytesRef;
		}

		public EmptyFixedSource(DocValues.Type type, int valueSize)
		{
			super(type);
			this.valueSize = valueSize;
			valueArray = new byte[valueSize];
		}
	}

	private static class EmptySource extends DocValues.SortedSource
	{

		public BytesRef getBytes(int docID, BytesRef ref)
		{
			ref.length = 0;
			return ref;
		}

		public double getFloat(int docID)
		{
			return 0.0D;
		}

		public long getInt(int docID)
		{
			return 0L;
		}

		public DocValues.SortedSource asSortedSource()
		{
			if (getType() != DocValues.Type.BYTES_FIXED_SORTED)
				if (getType() != DocValues.Type.BYTES_VAR_SORTED);
			return super.asSortedSource();
		}

		public int ord(int docID)
		{
			return 0;
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			bytesRef.length = 0;
			bytesRef.offset = 0;
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

		public EmptySource(DocValues.Type type)
		{
			super(type, BytesRef.getUTF8SortedAsUnicodeComparator());
		}
	}

	private static final class MultiSortedSource extends DocValues.SortedSource
	{

		private final org.apache.lucene.util.PagedBytes.Reader data;
		private final int docToOrd[];
		private final long ordToOffset[];
		private int size;
		private int valueCount;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiDocValues.desiredAssertionStatus();

		public int ord(int docID)
		{
			return docToOrd[docID];
		}

		public BytesRef getByOrd(int ord, BytesRef bytesRef)
		{
			int size = this.size;
			long offset = ord * size;
			if (ordToOffset != null)
			{
				offset = ordToOffset[ord];
				size = (int)(ordToOffset[1 + ord] - offset);
			}
			if (!$assertionsDisabled && size < 0)
				throw new AssertionError();
			else
				return data.fillSlice(bytesRef, offset, size);
		}

		public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
		{
			return null;
		}

		public int getValueCount()
		{
			return valueCount;
		}


		public MultiSortedSource(DocValues.Type type, Comparator comparator, PagedBytes pagedBytes, int size, int numValues, int docToOrd[], long ordToOffset[])
		{
			super(type, comparator);
			data = pagedBytes.freeze(true);
			this.size = size;
			valueCount = numValues;
			this.docToOrd = docToOrd;
			this.ordToOffset = ordToOffset;
		}
	}

	private static final class RecordingBytesRefConsumer
		implements SortedBytesMergeUtils.BytesRefConsumer
	{

		private static final int PAGED_BYTES_BITS = 15;
		final PagedBytes pagedBytes = new PagedBytes(15);
		long ordToOffset[];

		public void consume(BytesRef ref, int ord, long offset)
		{
			pagedBytes.copy(ref);
			if (ordToOffset != null)
			{
				if (ord + 1 >= ordToOffset.length)
					ordToOffset = ArrayUtil.grow(ordToOffset, ord + 2);
				ordToOffset[ord + 1] = offset;
			}
		}

		public RecordingBytesRefConsumer(DocValues.Type type)
		{
			ordToOffset = type != DocValues.Type.BYTES_VAR_SORTED ? null : new long[2];
		}
	}

	private static class MultiSource extends DocValues.Source
	{

		private int numDocs;
		private int start;
		private DocValues.Source current;
		private final int starts[];
		private final DocValuesSlice slices[];
		private boolean direct;
		private Object cachedArray;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiDocValues.desiredAssertionStatus();

		public long getInt(int docID)
		{
			int doc = ensureSource(docID);
			return current.getInt(doc);
		}

		private final int ensureSource(int docID)
		{
			if (docID >= start && docID < start + numDocs)
				return docID - start;
			int idx = ReaderUtil.subIndex(docID, starts);
			if (!$assertionsDisabled && (idx < 0 || idx >= slices.length))
				throw new AssertionError((new StringBuilder()).append("idx was ").append(idx).append(" for doc id: ").append(docID).append(" slices : ").append(Arrays.toString(starts)).toString());
			if (!$assertionsDisabled && slices[idx] == null)
				throw new AssertionError();
			try
			{
				if (direct)
					current = slices[idx].docValues.getDirectSource();
				else
					current = slices[idx].docValues.getSource();
			}
			catch (IOException e)
			{
				throw new RuntimeException("load failed", e);
			}
			start = slices[idx].start;
			numDocs = slices[idx].length;
			return docID - start;
		}

		public double getFloat(int docID)
		{
			int doc = ensureSource(docID);
			return current.getFloat(doc);
		}

		public BytesRef getBytes(int docID, BytesRef bytesRef)
		{
			int doc = ensureSource(docID);
			return current.getBytes(doc, bytesRef);
		}

		public DocValues.SortedSource asSortedSource()
		{
			Comparator comp;
			SortedBytesMergeUtils.MergeContext ctx;
			RecordingBytesRefConsumer consumer;
			int maxOrd;
			int docToOrd[];
			if (type != DocValues.Type.BYTES_FIXED_SORTED && type != DocValues.Type.BYTES_VAR_SORTED)
				break MISSING_BLOCK_LABEL_277;
			DocValues values[] = new DocValues[this.slices.length];
			comp = null;
			for (int i = 0; i < values.length; i++)
			{
				values[i] = this.slices[i].docValues;
				if (values[i] instanceof EmptyDocValues)
					continue;
				Comparator comparator = values[i].getDirectSource().asSortedSource().getComparator();
				if (!$assertionsDisabled && comp != null && comp != comparator)
					throw new AssertionError();
				comp = comparator;
			}

			if (!$assertionsDisabled && comp == null)
				throw new AssertionError();
			int globalNumDocs = globalNumDocs();
			ctx = SortedBytesMergeUtils.init(type, values, comp, globalNumDocs);
			List slices = SortedBytesMergeUtils.buildSlices(docBases(), new MergeState.DocMap[values.length], values, ctx);
			consumer = new RecordingBytesRefConsumer(type);
			maxOrd = SortedBytesMergeUtils.mergeRecords(ctx, consumer, slices);
			docToOrd = new int[globalNumDocs];
			SortedBytesMergeUtils.SortedSourceSlice slice;
			for (Iterator i$ = slices.iterator(); i$.hasNext(); slice.toAbsolutOrds(docToOrd))
				slice = (SortedBytesMergeUtils.SortedSourceSlice)i$.next();

			return new MultiSortedSource(type, comp, consumer.pagedBytes, ctx.sizePerValues, maxOrd, docToOrd, consumer.ordToOffset);
			IOException e;
			e;
			throw new RuntimeException("load failed", e);
			return super.asSortedSource();
		}

		private int globalNumDocs()
		{
			int docs = 0;
			for (int i = 0; i < slices.length; i++)
				docs += slices[i].length;

			return docs;
		}

		private int[] docBases()
		{
			int docBases[] = new int[slices.length];
			for (int i = 0; i < slices.length; i++)
				docBases[i] = slices[i].start;

			return docBases;
		}

		public boolean hasArray()
		{
			boolean oneRealSource;
			DocValuesSlice arr$[];
			int len$;
			int i$;
			oneRealSource = false;
			arr$ = slices;
			len$ = arr$.length;
			i$ = 0;
_L3:
			if (i$ >= len$) goto _L2; else goto _L1
_L1:
			DocValuesSlice slice = arr$[i$];
			DocValues.Source source;
			try
			{
				source = slice.docValues.getSource();
				if (source instanceof EmptySource)
					continue; /* Loop/switch isn't completed */
			}
			catch (IOException e)
			{
				throw new RuntimeException("load failed", e);
			}
			oneRealSource = true;
			if (!source.hasArray())
				return false;
			i$++;
			  goto _L3
_L2:
			return oneRealSource;
		}

		public Object getArray()
		{
			if (!hasArray())
				return null;
			Class componentType;
			Object arrays[];
			int numDocs;
			componentType = null;
			arrays = new Object[slices.length];
			numDocs = 0;
			for (int i = 0; i < slices.length; i++)
			{
				DocValuesSlice slice = slices[i];
				DocValues.Source source = slice.docValues.getSource();
				Object array = null;
				if (!(source instanceof EmptySource))
					array = source.getArray();
				numDocs += slice.length;
				if (array != null)
				{
					if (componentType == null)
						componentType = array.getClass().getComponentType();
					if (!$assertionsDisabled && componentType != array.getClass().getComponentType())
						throw new AssertionError();
				}
				arrays[i] = array;
			}

			if (!$assertionsDisabled && componentType == null)
				throw new AssertionError();
			MultiSource multisource = this;
			JVM INSTR monitorenter ;
			if (cachedArray != null)
				return cachedArray;
			Object globalArray;
			globalArray = Array.newInstance(componentType, numDocs);
			for (int i = 0; i < slices.length; i++)
			{
				DocValuesSlice slice = slices[i];
				if (arrays[i] == null)
					continue;
				if (!$assertionsDisabled && slice.length != Array.getLength(arrays[i]))
					throw new AssertionError();
				System.arraycopy(arrays[i], 0, globalArray, slice.start, slice.length);
			}

			cachedArray = globalArray;
			multisource;
			JVM INSTR monitorexit ;
			return;
			Exception exception;
			exception;
			throw exception;
			IOException e;
			e;
			throw new RuntimeException("load failed", e);
		}


		public MultiSource(DocValuesSlice slices[], int starts[], boolean direct, DocValues.Type type)
		{
			super(type);
			numDocs = 0;
			start = 0;
			this.slices = slices;
			this.starts = starts;
			if (!$assertionsDisabled && slices.length == 0)
			{
				throw new AssertionError();
			} else
			{
				this.direct = direct;
				return;
			}
		}
	}

	public static class EmptyFixedDocValues extends DocValues
	{

		final int maxDoc;
		final DocValues.Source emptyFixedSource;
		final int valueSize;

		public DocValues.Source load()
			throws IOException
		{
			return emptyFixedSource;
		}

		public DocValues.Type getType()
		{
			return emptyFixedSource.getType();
		}

		public int getValueSize()
		{
			return valueSize;
		}

		public DocValues.Source getDirectSource()
			throws IOException
		{
			return emptyFixedSource;
		}

		public EmptyFixedDocValues(int maxDoc, DocValues.Type type, int valueSize)
		{
			this.maxDoc = maxDoc;
			emptyFixedSource = new EmptyFixedSource(type, valueSize);
			this.valueSize = valueSize;
		}
	}

	public static class EmptyDocValues extends DocValues
	{

		final int maxDoc;
		final DocValues.Source emptySource;

		public DocValues.Source load()
			throws IOException
		{
			return emptySource;
		}

		public DocValues.Type getType()
		{
			return emptySource.getType();
		}

		public DocValues.Source getDirectSource()
			throws IOException
		{
			return emptySource;
		}

		public EmptyDocValues(int maxDoc, DocValues.Type type)
		{
			this.maxDoc = maxDoc;
			emptySource = new EmptySource(type);
		}
	}

	private static class DocValuesPuller
	{

		public DocValues pull(AtomicReader reader, String field)
			throws IOException
		{
			return reader.docValues(field);
		}

		public boolean stopLoadingOnNull(AtomicReader reader, String field)
		{
			return false;
		}

		public DocValuesPuller()
		{
		}
	}

	public static class DocValuesSlice
	{

		public static final DocValuesSlice EMPTY_ARRAY[] = new DocValuesSlice[0];
		final int start;
		final int length;
		DocValues docValues;


		public DocValuesSlice(DocValues docValues, int start, int length)
		{
			this.docValues = docValues;
			this.start = start;
			this.length = length;
		}
	}


	private static DocValuesPuller DEFAULT_PULLER = new DocValuesPuller();
	private static final DocValuesPuller NORMS_PULLER = new DocValuesPuller() {

		public DocValues pull(AtomicReader reader, String field)
			throws IOException
		{
			return reader.normValues(field);
		}

		public boolean stopLoadingOnNull(AtomicReader reader, String field)
		{
			FieldInfos fieldInfos = reader.getFieldInfos();
			FieldInfo fieldInfo = fieldInfos.fieldInfo(field);
			return fieldInfo != null && fieldInfo.omitsNorms();
		}

	};
	private DocValuesSlice slices[];
	private int starts[];
	private DocValues.Type type;
	private int valueSize;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiDocValues.desiredAssertionStatus();

	private MultiDocValues(DocValuesSlice slices[], int starts[], TypePromoter promotedType)
	{
		this.starts = starts;
		this.slices = slices;
		type = promotedType.type();
		valueSize = promotedType.getValueSize();
	}

	public static DocValues getDocValues(IndexReader r, String field)
		throws IOException
	{
		return getDocValues(r, field, DEFAULT_PULLER);
	}

	public static DocValues getNormDocValues(IndexReader r, String field)
		throws IOException
	{
		return getDocValues(r, field, NORMS_PULLER);
	}

	private static DocValues getDocValues(IndexReader reader, String field, DocValuesPuller puller)
		throws IOException
	{
		if (reader instanceof AtomicReader)
			return puller.pull((AtomicReader)reader, field);
		if (!$assertionsDisabled && !(reader instanceof CompositeReader))
			throw new AssertionError();
		List leaves = reader.leaves();
		switch (leaves.size())
		{
		case 0: // '\0'
			return null;

		case 1: // '\001'
			return getDocValues(((IndexReader) (((AtomicReaderContext)leaves.get(0)).reader())), field, puller);
		}
		List slices = new ArrayList();
		TypePromoter promotedType = TypePromoter.getIdentityPromoter();
		AtomicReaderContext ctx;
		AtomicReader r;
		DocValues d;
		for (Iterator i$ = leaves.iterator(); i$.hasNext(); slices.add(new DocValuesSlice(d, ctx.docBase, r.maxDoc())))
		{
			ctx = (AtomicReaderContext)i$.next();
			r = ctx.reader();
			d = puller.pull(r, field);
			if (d != null)
			{
				TypePromoter incoming = TypePromoter.create(d.getType(), d.getValueSize());
				promotedType = promotedType.promote(incoming);
				continue;
			}
			if (puller.stopLoadingOnNull(r, field))
				return null;
		}

		if (promotedType == TypePromoter.getIdentityPromoter())
			return null;
		int starts[] = new int[slices.size()];
		for (int i = 0; i < slices.size(); i++)
		{
			DocValuesSlice slice = (DocValuesSlice)slices.get(i);
			starts[i] = slice.start;
			if (slice.docValues != null)
				continue;
			DocValues.Type promoted = promotedType.type();
			static class 2
			{

				static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

				static 
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[DocValues.Type.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (2..SwitchMap.org.apache.lucene.index.DocValues.Type[promoted.ordinal()])
			{
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
				if (!$assertionsDisabled && promotedType.getValueSize() < 0)
					throw new AssertionError();
				slice.docValues = new EmptyFixedDocValues(slice.length, promoted, promotedType.getValueSize());
				break;

			default:
				slice.docValues = new EmptyDocValues(slice.length, promoted);
				break;
			}
		}

		return new MultiDocValues((DocValuesSlice[])slices.toArray(new DocValuesSlice[slices.size()]), starts, promotedType);
	}

	public DocValues.Source load()
		throws IOException
	{
		return new MultiSource(slices, starts, false, type);
	}

	public DocValues.Type getType()
	{
		return type;
	}

	public int getValueSize()
	{
		return valueSize;
	}

	public DocValues.Source getDirectSource()
		throws IOException
	{
		return new MultiSource(slices, starts, true, type);
	}

}
