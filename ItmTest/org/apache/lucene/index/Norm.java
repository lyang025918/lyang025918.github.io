// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Norm.java

package org.apache.lucene.index;

import org.apache.lucene.document.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			DocValues, IndexableField

public final class Norm
{

	private Field field;
	private BytesRef spare;

	public Norm()
	{
	}

	public IndexableField field()
	{
		return field;
	}

	public DocValues.Type type()
	{
		return field != null ? field.fieldType().docValueType() : null;
	}

	public BytesRef getSpare()
	{
		if (spare == null)
			spare = new BytesRef();
		return spare;
	}

	public void setFloat(float norm)
	{
		setType(DocValues.Type.FLOAT_32);
		field.setFloatValue(norm);
	}

	public void setDouble(double norm)
	{
		setType(DocValues.Type.FLOAT_64);
		field.setDoubleValue(norm);
	}

	public void setShort(short norm)
	{
		setType(DocValues.Type.FIXED_INTS_16);
		field.setShortValue(norm);
	}

	public void setInt(int norm)
	{
		setType(DocValues.Type.FIXED_INTS_32);
		field.setIntValue(norm);
	}

	public void setLong(long norm)
	{
		setType(DocValues.Type.FIXED_INTS_64);
		field.setLongValue(norm);
	}

	public void setByte(byte norm)
	{
		setType(DocValues.Type.FIXED_INTS_8);
		field.setByteValue(norm);
	}

	public void setBytes(BytesRef norm)
	{
		setType(DocValues.Type.BYTES_FIXED_STRAIGHT);
		field.setBytesValue(norm);
	}

	private void setType(DocValues.Type type)
	{
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[DocValues.Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.VAR_INTS.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_8.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_16.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_32.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_64.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_32.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_64.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		if (field != null)
		{
			if (type != field.fieldType().docValueType())
				throw new IllegalArgumentException((new StringBuilder()).append("FieldType missmatch - expected ").append(type).append(" but was ").append(field.fieldType().docValueType()).toString());
		} else
		{
			switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
			{
			case 1: // '\001'
				field = new PackedLongDocValuesField("", 0L);
				break;

			case 2: // '\002'
				field = new ByteDocValuesField("", (byte)0);
				break;

			case 3: // '\003'
				field = new ShortDocValuesField("", (short)0);
				break;

			case 4: // '\004'
				field = new IntDocValuesField("", 0);
				break;

			case 5: // '\005'
				field = new LongDocValuesField("", 0L);
				break;

			case 6: // '\006'
				field = new FloatDocValuesField("", 0.0F);
				break;

			case 7: // '\007'
				field = new DoubleDocValuesField("", 0.0D);
				break;

			case 8: // '\b'
				field = new StraightBytesDocValuesField("", new BytesRef(), true);
				break;

			case 9: // '\t'
				field = new StraightBytesDocValuesField("", new BytesRef(), false);
				break;

			case 10: // '\n'
				field = new DerefBytesDocValuesField("", new BytesRef(), true);
				break;

			case 11: // '\013'
				field = new DerefBytesDocValuesField("", new BytesRef(), false);
				break;

			case 12: // '\f'
				field = new SortedBytesDocValuesField("", new BytesRef(), true);
				break;

			case 13: // '\r'
				field = new SortedBytesDocValuesField("", new BytesRef(), false);
				break;

			default:
				throw new IllegalArgumentException((new StringBuilder()).append("unknown Type: ").append(type).toString());
			}
		}
	}
}
