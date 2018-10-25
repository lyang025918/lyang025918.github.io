// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SortField.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.StringHelper;

// Referenced classes of package org.apache.lucene.search:
//			FieldCache, FieldComparator, FieldComparatorSource, IndexSearcher

public class SortField
{
	public static final class Type extends Enum
	{

		public static final Type SCORE;
		public static final Type DOC;
		public static final Type STRING;
		public static final Type INT;
		public static final Type FLOAT;
		public static final Type LONG;
		public static final Type DOUBLE;
		public static final Type SHORT;
		public static final Type CUSTOM;
		public static final Type BYTE;
		public static final Type STRING_VAL;
		public static final Type BYTES;
		public static final Type REWRITEABLE;
		private static final Type $VALUES[];

		public static Type[] values()
		{
			return (Type[])$VALUES.clone();
		}

		public static Type valueOf(String name)
		{
			return (Type)Enum.valueOf(org/apache/lucene/search/SortField$Type, name);
		}

		static 
		{
			SCORE = new Type("SCORE", 0);
			DOC = new Type("DOC", 1);
			STRING = new Type("STRING", 2);
			INT = new Type("INT", 3);
			FLOAT = new Type("FLOAT", 4);
			LONG = new Type("LONG", 5);
			DOUBLE = new Type("DOUBLE", 6);
			SHORT = new Type("SHORT", 7);
			CUSTOM = new Type("CUSTOM", 8);
			BYTE = new Type("BYTE", 9);
			STRING_VAL = new Type("STRING_VAL", 10);
			BYTES = new Type("BYTES", 11);
			REWRITEABLE = new Type("REWRITEABLE", 12);
			$VALUES = (new Type[] {
				SCORE, DOC, STRING, INT, FLOAT, LONG, DOUBLE, SHORT, CUSTOM, BYTE, 
				STRING_VAL, BYTES, REWRITEABLE
			});
		}

		private Type(String s, int i)
		{
			super(s, i);
		}
	}


	public static final SortField FIELD_SCORE;
	public static final SortField FIELD_DOC;
	private String field;
	private Type type;
	boolean reverse;
	private FieldCache.Parser parser;
	private FieldComparatorSource comparatorSource;
	public Object missingValue;
	private boolean useIndexValues;
	private Comparator bytesComparator;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/SortField.desiredAssertionStatus();

	public SortField(String field, Type type)
	{
		reverse = false;
		missingValue = null;
		bytesComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		initFieldType(field, type);
	}

	public SortField(String field, Type type, boolean reverse)
	{
		this.reverse = false;
		missingValue = null;
		bytesComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		initFieldType(field, type);
		this.reverse = reverse;
	}

	public SortField(String field, FieldCache.Parser parser)
	{
		this(field, parser, false);
	}

	public SortField(String field, FieldCache.Parser parser, boolean reverse)
	{
		this.reverse = false;
		missingValue = null;
		bytesComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		if (parser instanceof FieldCache.IntParser)
			initFieldType(field, Type.INT);
		else
		if (parser instanceof FieldCache.FloatParser)
			initFieldType(field, Type.FLOAT);
		else
		if (parser instanceof FieldCache.ShortParser)
			initFieldType(field, Type.SHORT);
		else
		if (parser instanceof FieldCache.ByteParser)
			initFieldType(field, Type.BYTE);
		else
		if (parser instanceof FieldCache.LongParser)
			initFieldType(field, Type.LONG);
		else
		if (parser instanceof FieldCache.DoubleParser)
			initFieldType(field, Type.DOUBLE);
		else
			throw new IllegalArgumentException((new StringBuilder()).append("Parser instance does not subclass existing numeric parser from FieldCache (got ").append(parser).append(")").toString());
		this.reverse = reverse;
		this.parser = parser;
	}

	public SortField setMissingValue(Object missingValue)
	{
		if (type != Type.BYTE && type != Type.SHORT && type != Type.INT && type != Type.FLOAT && type != Type.LONG && type != Type.DOUBLE)
		{
			throw new IllegalArgumentException("Missing value only works for numeric types");
		} else
		{
			this.missingValue = missingValue;
			return this;
		}
	}

	public SortField(String field, FieldComparatorSource comparator)
	{
		reverse = false;
		missingValue = null;
		bytesComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		initFieldType(field, Type.CUSTOM);
		comparatorSource = comparator;
	}

	public SortField(String field, FieldComparatorSource comparator, boolean reverse)
	{
		this.reverse = false;
		missingValue = null;
		bytesComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
		initFieldType(field, Type.CUSTOM);
		this.reverse = reverse;
		comparatorSource = comparator;
	}

	private void initFieldType(String field, Type type)
	{
		this.type = type;
		if (field == null)
		{
			if (type != Type.SCORE && type != Type.DOC)
				throw new IllegalArgumentException("field can only be null when type is SCORE or DOC");
		} else
		{
			this.field = field;
		}
	}

	public String getField()
	{
		return field;
	}

	public Type getType()
	{
		return type;
	}

	public FieldCache.Parser getParser()
	{
		return parser;
	}

	public boolean getReverse()
	{
		return reverse;
	}

	public FieldComparatorSource getComparatorSource()
	{
		return comparatorSource;
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		String dv = useIndexValues ? " [dv]" : "";
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$search$SortField$Type[];

			static 
			{
				$SwitchMap$org$apache$lucene$search$SortField$Type = new int[Type.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.SCORE.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.DOC.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.STRING.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.STRING_VAL.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.BYTE.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.SHORT.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.INT.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.LONG.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.FLOAT.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.DOUBLE.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.CUSTOM.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$search$SortField$Type[Type.REWRITEABLE.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.search.SortField.Type[type.ordinal()])
		{
		case 1: // '\001'
			buffer.append("<score>");
			break;

		case 2: // '\002'
			buffer.append("<doc>");
			break;

		case 3: // '\003'
			buffer.append((new StringBuilder()).append("<string").append(dv).append(": \"").toString()).append(field).append("\">");
			break;

		case 4: // '\004'
			buffer.append((new StringBuilder()).append("<string_val").append(dv).append(": \"").toString()).append(field).append("\">");
			break;

		case 5: // '\005'
			buffer.append("<byte: \"").append(field).append("\">");
			break;

		case 6: // '\006'
			buffer.append("<short: \"").append(field).append("\">");
			break;

		case 7: // '\007'
			buffer.append((new StringBuilder()).append("<int").append(dv).append(": \"").toString()).append(field).append("\">");
			break;

		case 8: // '\b'
			buffer.append("<long: \"").append(field).append("\">");
			break;

		case 9: // '\t'
			buffer.append((new StringBuilder()).append("<float").append(dv).append(": \"").toString()).append(field).append("\">");
			break;

		case 10: // '\n'
			buffer.append((new StringBuilder()).append("<double").append(dv).append(": \"").toString()).append(field).append("\">");
			break;

		case 11: // '\013'
			buffer.append("<custom:\"").append(field).append("\": ").append(comparatorSource).append('>');
			break;

		case 12: // '\f'
			buffer.append("<rewriteable: \"").append(field).append("\">");
			break;

		default:
			buffer.append("<???: \"").append(field).append("\">");
			break;
		}
		if (reverse)
			buffer.append('!');
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SortField))
		{
			return false;
		} else
		{
			SortField other = (SortField)o;
			return StringHelper.equals(other.field, field) && other.type == type && other.reverse == reverse && (other.comparatorSource != null ? other.comparatorSource.equals(comparatorSource) : comparatorSource == null);
		}
	}

	public int hashCode()
	{
		int hash = type.hashCode() ^ 0x346565dd + Boolean.valueOf(reverse).hashCode() ^ 0xaf5998bb;
		if (field != null)
			hash += field.hashCode() ^ 0xff5685dd;
		if (comparatorSource != null)
			hash += comparatorSource.hashCode();
		return hash;
	}

	public void setUseIndexValues(boolean b)
	{
		useIndexValues = b;
	}

	public boolean getUseIndexValues()
	{
		return useIndexValues;
	}

	public void setBytesComparator(Comparator b)
	{
		bytesComparator = b;
	}

	public Comparator getBytesComparator()
	{
		return bytesComparator;
	}

	public FieldComparator getComparator(int numHits, int sortPos)
		throws IOException
	{
		switch (1..SwitchMap.org.apache.lucene.search.SortField.Type[type.ordinal()])
		{
		case 1: // '\001'
			return new FieldComparator.RelevanceComparator(numHits);

		case 2: // '\002'
			return new FieldComparator.DocComparator(numHits);

		case 7: // '\007'
			if (useIndexValues)
				return new FieldComparator.IntDocValuesComparator(numHits, field);
			else
				return new FieldComparator.IntComparator(numHits, field, parser, (Integer)missingValue);

		case 9: // '\t'
			if (useIndexValues)
				return new FieldComparator.FloatDocValuesComparator(numHits, field);
			else
				return new FieldComparator.FloatComparator(numHits, field, parser, (Float)missingValue);

		case 8: // '\b'
			return new FieldComparator.LongComparator(numHits, field, parser, (Long)missingValue);

		case 10: // '\n'
			return new FieldComparator.DoubleComparator(numHits, field, parser, (Double)missingValue);

		case 5: // '\005'
			return new FieldComparator.ByteComparator(numHits, field, parser, (Byte)missingValue);

		case 6: // '\006'
			return new FieldComparator.ShortComparator(numHits, field, parser, (Short)missingValue);

		case 11: // '\013'
			if (!$assertionsDisabled && comparatorSource == null)
				throw new AssertionError();
			else
				return comparatorSource.newComparator(field, numHits, sortPos, reverse);

		case 3: // '\003'
			if (useIndexValues)
				return new FieldComparator.TermOrdValDocValuesComparator(numHits, field);
			else
				return new FieldComparator.TermOrdValComparator(numHits, field);

		case 4: // '\004'
			if (useIndexValues)
				return new FieldComparator.TermValDocValuesComparator(numHits, field);
			else
				return new FieldComparator.TermValComparator(numHits, field);

		case 12: // '\f'
			throw new IllegalStateException("SortField needs to be rewritten through Sort.rewrite(..) and SortField.rewrite(..)");
		}
		throw new IllegalStateException((new StringBuilder()).append("Illegal sort type: ").append(type).toString());
	}

	public SortField rewrite(IndexSearcher searcher)
		throws IOException
	{
		return this;
	}

	static 
	{
		FIELD_SCORE = new SortField(null, Type.SCORE);
		FIELD_DOC = new SortField(null, Type.DOC);
	}
}
