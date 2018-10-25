// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Floats.java

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
//			FixedStraightBytesImpl

public class Floats
{
	static final class FloatsReader extends FixedStraightBytesImpl.FixedStraightReader
	{

		final DocValuesArraySource arrayTemplate;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Floats.desiredAssertionStatus();

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


		FloatsReader(Directory dir, String id, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type)
			throws IOException
		{
			super(dir, id, "Floats", 0, maxDoc, context, type);
			arrayTemplate = DocValuesArraySource.forType(type);
			if (!$assertionsDisabled && size != 4 && size != 8)
				throw new AssertionError((new StringBuilder()).append("wrong size=").append(size).append(" type=").append(type).append(" id=").append(id).toString());
			else
				return;
		}
	}

	static final class FloatsWriter extends FixedStraightBytesImpl.Writer
	{

		private final int size;
		private final DocValuesArraySource template;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/values/Floats.desiredAssertionStatus();

		protected boolean tryBulkMerge(DocValues docValues)
		{
			return super.tryBulkMerge(docValues) && docValues.getType() == template.getType();
		}

		public void add(int docID, IndexableField value)
			throws IOException
		{
			template.toBytes(value.numericValue().doubleValue(), bytesRef);
			bytesSpareField.setBytesValue(bytesRef);
			super.add(docID, bytesSpareField);
		}

		protected void setMergeBytes(org.apache.lucene.index.DocValues.Source source, int sourceDoc)
		{
			double value = source.getFloat(sourceDoc);
			template.toBytes(value, bytesRef);
		}


		public FloatsWriter(Directory dir, String id, Counter bytesUsed, IOContext context, org.apache.lucene.index.DocValues.Type type)
		{
			super(dir, id, "Floats", 0, bytesUsed, context);
			size = Floats.typeToSize(type);
			bytesRef = new BytesRef(size);
			bytesRef.length = size;
			template = DocValuesArraySource.forType(type);
			if (!$assertionsDisabled && template == null)
				throw new AssertionError();
			else
				return;
		}
	}


	protected static final String CODEC_NAME = "Floats";
	protected static final int VERSION_START = 0;
	protected static final int VERSION_CURRENT = 0;

	private Floats()
	{
	}

	public static DocValuesConsumer getWriter(Directory dir, String id, Counter bytesUsed, IOContext context, org.apache.lucene.index.DocValues.Type type)
	{
		return new FloatsWriter(dir, id, bytesUsed, context, type);
	}

	public static DocValues getValues(Directory dir, String id, int maxDoc, IOContext context, org.apache.lucene.index.DocValues.Type type)
		throws IOException
	{
		return new FloatsReader(dir, id, maxDoc, context, type);
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
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_32.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.FLOAT_64.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			return 4;

		case 2: // '\002'
			return 8;
		}
		throw new IllegalStateException((new StringBuilder()).append("illegal type ").append(type).toString());
	}

}
