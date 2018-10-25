// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Ints.java

package org.apache.lucene.codecs.lucene40.values;

import java.io.Closeable;
import java.io.IOException;
import org.apache.lucene.codecs.DocValuesArraySource;
import org.apache.lucene.codecs.DocValuesConsumer;
import org.apache.lucene.document.StraightBytesDocValuesField;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			PackedIntValues, FixedStraightBytesImpl

public final class Ints
{
	static final class IntsReader extends FixedStraightBytesImpl.FixedStraightReader
	{

		private final DocValuesArraySource arrayTemplate;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Ints.desiredAssertionStatus();

		public org.apache.lucene.index.DocValues.Source load()
			throws IOException
		{
			IndexInput indexInput = cloneData();
			DocValuesArraySource docvaluesarraysource = arrayTemplate.newFromInput(indexInput, maxDoc);
			IOUtils.close(new Closeable[] {
				indexInput
			});
			return docvaluesarraysource;
			Exception exception;
			exception;
			IOUtils.close(new Closeable[] {
				indexInput
			});
			throw exception;
		}


		IntsReader(Directory dir, String id, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(dir, id, "Ints", 0, maxDoc, context, type);
			arrayTemplate = DocValuesArraySource.forType(type);
			if (!$assertionsDisabled && arrayTemplate == null)
				throw new AssertionError();
			if (!$assertionsDisabled && type != Ints.sizeToType(size))
				throw new AssertionError();
			else
				return;
		}
	}

	static class IntsWriter extends FixedStraightBytesImpl.Writer
	{

		private final DocValuesArraySource template;

		protected void setMergeBytes(org.apache.lucene.index.DocValues.Source source, int sourceDoc)
		{
			long value = source.getInt(sourceDoc);
			template.toBytes(value, bytesRef);
		}

		public void add(int docID, IndexableField value)
			throws IOException
		{
			template.toBytes(value.numericValue().longValue(), bytesRef);
			bytesSpareField.setBytesValue(bytesRef);
			super.add(docID, bytesSpareField);
		}

		protected boolean tryBulkMerge(DocValues docValues)
		{
			return super.tryBulkMerge(docValues) && docValues.getType() == template.getType();
		}

		public IntsWriter(Directory dir, String id, Counter bytesUsed, IOContext context, org.apache.lucene.index.DocValues.Type valueType)
		{
			this(dir, id, "Ints", 0, bytesUsed, context, valueType);
		}

		protected IntsWriter(Directory dir, String id, String codecName, int version, Counter bytesUsed, IOContext context, org.apache.lucene.index.DocValues.Type valueType)
		{
			super(dir, id, codecName, version, bytesUsed, context);
			size = Ints.typeToSize(valueType);
			bytesRef = new BytesRef(size);
			bytesRef.length = size;
			template = DocValuesArraySource.forType(valueType);
		}
	}


	protected static final String CODEC_NAME = "Ints";
	protected static final int VERSION_START = 0;
	protected static final int VERSION_CURRENT = 0;

	private Ints()
	{
	}

	public static DocValuesConsumer getWriter(Directory dir, String id, Counter bytesUsed, org.apache.lucene.index.DocValues.Type type, IOContext context)
	{
		return ((DocValuesConsumer) (type != org.apache.lucene.index.DocValues.Type.VAR_INTS ? new IntsWriter(dir, id, bytesUsed, context, type) : new PackedIntValues.PackedIntsWriter(dir, id, bytesUsed, context)));
	}

	public static DocValues getValues(Directory dir, String id, int numDocs, org.apache.lucene.index.DocValues.Type type, IOContext context)
		throws IOException
	{
		return ((DocValues) (type != org.apache.lucene.index.DocValues.Type.VAR_INTS ? new IntsReader(dir, id, numDocs, context, type) : new PackedIntValues.PackedIntsReader(dir, id, numDocs, context)));
	}

	private static org.apache.lucene.index.DocValues.Type sizeToType(int size)
	{
		switch (size)
		{
		case 1: // '\001'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_8;

		case 2: // '\002'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_16;

		case 4: // '\004'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_32;

		case 8: // '\b'
			return org.apache.lucene.index.DocValues.Type.FIXED_INTS_64;

		case 3: // '\003'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		default:
			throw new IllegalStateException((new StringBuilder()).append("illegal size ").append(size).toString());
		}
	}

	private static int typeToSize(org.apache.lucene.index.DocValues.Type type)
	{
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[org.apache.lucene.index.DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_16.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_32.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_64.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FIXED_INTS_8.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			return 2;

		case 2: // '\002'
			return 4;

		case 3: // '\003'
			return 8;

		case 4: // '\004'
			return 1;
		}
		throw new IllegalStateException((new StringBuilder()).append("illegal type ").append(type).toString());
	}


}
