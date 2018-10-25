// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DocValuesConsumer.java

package org.apache.lucene.codecs;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

public abstract class DocValuesConsumer
{

	protected final BytesRef spare = new BytesRef();
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/DocValuesConsumer.desiredAssertionStatus();

	protected abstract org.apache.lucene.index.DocValues.Type getType();

	protected DocValuesConsumer()
	{
	}

	public abstract void add(int i, IndexableField indexablefield)
		throws IOException;

	public abstract void finish(int i)
		throws IOException;

	public abstract int getValueSize();

	public void merge(MergeState mergeState, DocValues docValues[])
		throws IOException
	{
		if (!$assertionsDisabled && mergeState == null)
			throw new AssertionError();
		boolean hasMerged = false;
		for (int readerIDX = 0; readerIDX < mergeState.readers.size(); readerIDX++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(readerIDX);
			if (docValues[readerIDX] != null)
			{
				hasMerged = true;
				merge(docValues[readerIDX], mergeState.docBase[readerIDX], reader.maxDoc(), reader.getLiveDocs());
				mergeState.checkAbort.work(reader.maxDoc());
			}
		}

		if (hasMerged)
			finish(mergeState.segmentInfo.getDocCount());
	}

	protected void merge(DocValues reader, int docBase, int docCount, Bits liveDocs)
		throws IOException
	{
		org.apache.lucene.index.DocValues.Source source = reader.getDirectSource();
		if (!$assertionsDisabled && source == null)
			throw new AssertionError();
		int docID = docBase;
		org.apache.lucene.index.DocValues.Type type = getType();
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[org.apache.lucene.index.DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.VAR_INTS.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_8.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_16.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_32.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_64.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_32.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_64.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		Field scratchField;
		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			scratchField = new PackedLongDocValuesField("", 0L);
			break;

		case 2: // '\002'
			scratchField = new ByteDocValuesField("", (byte)0);
			break;

		case 3: // '\003'
			scratchField = new ShortDocValuesField("", (short)0);
			break;

		case 4: // '\004'
			scratchField = new IntDocValuesField("", 0);
			break;

		case 5: // '\005'
			scratchField = new LongDocValuesField("", 0L);
			break;

		case 6: // '\006'
			scratchField = new FloatDocValuesField("", 0.0F);
			break;

		case 7: // '\007'
			scratchField = new DoubleDocValuesField("", 0.0D);
			break;

		case 8: // '\b'
			scratchField = new StraightBytesDocValuesField("", new BytesRef(), true);
			break;

		case 9: // '\t'
			scratchField = new StraightBytesDocValuesField("", new BytesRef(), false);
			break;

		case 10: // '\n'
			scratchField = new DerefBytesDocValuesField("", new BytesRef(), true);
			break;

		case 11: // '\013'
			scratchField = new DerefBytesDocValuesField("", new BytesRef(), false);
			break;

		case 12: // '\f'
			scratchField = new SortedBytesDocValuesField("", new BytesRef(), true);
			break;

		case 13: // '\r'
			scratchField = new SortedBytesDocValuesField("", new BytesRef(), false);
			break;

		default:
			throw new IllegalStateException((new StringBuilder()).append("unknown Type: ").append(type).toString());
		}
		for (int i = 0; i < docCount; i++)
			if (liveDocs == null || liveDocs.get(i))
				mergeDoc(scratchField, source, docID++, i);

	}

	protected void mergeDoc(Field scratchField, org.apache.lucene.index.DocValues.Source source, int docID, int sourceDoc)
		throws IOException
	{
		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[getType().ordinal()])
		{
		case 8: // '\b'
		case 9: // '\t'
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
		case 13: // '\r'
			scratchField.setBytesValue(source.getBytes(sourceDoc, spare));
			break;

		case 2: // '\002'
			scratchField.setByteValue((byte)(int)source.getInt(sourceDoc));
			break;

		case 3: // '\003'
			scratchField.setShortValue((short)(int)source.getInt(sourceDoc));
			break;

		case 4: // '\004'
			scratchField.setIntValue((int)source.getInt(sourceDoc));
			break;

		case 5: // '\005'
			scratchField.setLongValue(source.getInt(sourceDoc));
			break;

		case 1: // '\001'
			scratchField.setLongValue(source.getInt(sourceDoc));
			break;

		case 6: // '\006'
			scratchField.setFloatValue((float)source.getFloat(sourceDoc));
			break;

		case 7: // '\007'
			scratchField.setDoubleValue(source.getFloat(sourceDoc));
			break;
		}
		add(docID, scratchField);
	}

}
