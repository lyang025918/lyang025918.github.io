// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40DocValuesProducer.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.PerDocProducerBase;
import org.apache.lucene.codecs.lucene40.values.Bytes;
import org.apache.lucene.codecs.lucene40.values.Floats;
import org.apache.lucene.codecs.lucene40.values.Ints;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.IOUtils;

public class Lucene40DocValuesProducer extends PerDocProducerBase
{

	protected final TreeMap docValues;
	private final Directory cfs;

	public Lucene40DocValuesProducer(SegmentReadState state, String segmentSuffix)
		throws IOException
	{
		if (anyDocValuesFields(state.fieldInfos))
		{
			cfs = new CompoundFileDirectory(state.dir, IndexFileNames.segmentFileName(state.segmentInfo.name, segmentSuffix, "cfs"), state.context, false);
			docValues = load(state.fieldInfos, state.segmentInfo.name, state.segmentInfo.getDocCount(), cfs, state.context);
		} else
		{
			cfs = null;
			docValues = new TreeMap();
		}
	}

	protected Map docValues()
	{
		return docValues;
	}

	protected void closeInternal(Collection closeables)
		throws IOException
	{
		if (cfs != null)
		{
			ArrayList list = new ArrayList(closeables);
			list.add(cfs);
			IOUtils.close(list);
		} else
		{
			IOUtils.close(closeables);
		}
	}

	protected DocValues loadDocValues(int docCount, Directory dir, String id, org.apache.lucene.index.DocValues.Type type, IOContext context)
		throws IOException
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
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.VAR_INTS.ordinal()] = 5;
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
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
			return Ints.getValues(dir, id, docCount, type, context);

		case 6: // '\006'
			return Floats.getValues(dir, id, docCount, context, type);

		case 7: // '\007'
			return Floats.getValues(dir, id, docCount, context, type);

		case 8: // '\b'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.STRAIGHT, true, docCount, getComparator(), context);

		case 9: // '\t'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.DEREF, true, docCount, getComparator(), context);

		case 10: // '\n'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.SORTED, true, docCount, getComparator(), context);

		case 11: // '\013'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.STRAIGHT, false, docCount, getComparator(), context);

		case 12: // '\f'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.DEREF, false, docCount, getComparator(), context);

		case 13: // '\r'
			return Bytes.getValues(dir, id, org.apache.lucene.codecs.lucene40.values.Bytes.Mode.SORTED, false, docCount, getComparator(), context);
		}
		throw new IllegalStateException((new StringBuilder()).append("unrecognized index values mode ").append(type).toString());
	}
}
