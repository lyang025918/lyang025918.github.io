// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Writer.java

package org.apache.lucene.codecs.lucene40.values;

import java.util.Comparator;
import org.apache.lucene.codecs.DocValuesConsumer;
import org.apache.lucene.index.DocValues;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Counter;

// Referenced classes of package org.apache.lucene.codecs.lucene40.values:
//			Ints, Floats, Bytes

abstract class Writer extends DocValuesConsumer
{

	protected final Counter bytesUsed;
	protected org.apache.lucene.index.DocValues.Type type;

	protected Writer(Counter bytesUsed, org.apache.lucene.index.DocValues.Type type)
	{
		this.bytesUsed = bytesUsed;
		this.type = type;
	}

	protected org.apache.lucene.index.DocValues.Type getType()
	{
		return type;
	}

	public static DocValuesConsumer create(org.apache.lucene.index.DocValues.Type type, String id, Directory directory, Comparator comp, Counter bytesUsed, IOContext context, float acceptableOverheadRatio)
	{
		if (comp == null)
			comp = BytesRef.getUTF8SortedAsUnicodeComparator();
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
			return Ints.getWriter(directory, id, bytesUsed, type, context);

		case 6: // '\006'
			return Floats.getWriter(directory, id, bytesUsed, context, type);

		case 7: // '\007'
			return Floats.getWriter(directory, id, bytesUsed, context, type);

		case 8: // '\b'
			return Bytes.getWriter(directory, id, Bytes.Mode.STRAIGHT, true, comp, bytesUsed, context, acceptableOverheadRatio);

		case 9: // '\t'
			return Bytes.getWriter(directory, id, Bytes.Mode.DEREF, true, comp, bytesUsed, context, acceptableOverheadRatio);

		case 10: // '\n'
			return Bytes.getWriter(directory, id, Bytes.Mode.SORTED, true, comp, bytesUsed, context, acceptableOverheadRatio);

		case 11: // '\013'
			return Bytes.getWriter(directory, id, Bytes.Mode.STRAIGHT, false, comp, bytesUsed, context, acceptableOverheadRatio);

		case 12: // '\f'
			return Bytes.getWriter(directory, id, Bytes.Mode.DEREF, false, comp, bytesUsed, context, acceptableOverheadRatio);

		case 13: // '\r'
			return Bytes.getWriter(directory, id, Bytes.Mode.SORTED, false, comp, bytesUsed, context, acceptableOverheadRatio);
		}
		throw new IllegalArgumentException((new StringBuilder()).append("Unknown Values: ").append(type).toString());
	}
}
