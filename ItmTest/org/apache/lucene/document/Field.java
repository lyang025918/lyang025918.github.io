// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Field.java

package org.apache.lucene.document;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.document:
//			FieldType

public class Field
	implements IndexableField
{
	/**
	 * @deprecated Class TermVector is deprecated
	 */

	public static abstract class TermVector extends Enum
	{

		public static final TermVector NO;
		public static final TermVector YES;
		public static final TermVector WITH_POSITIONS;
		public static final TermVector WITH_OFFSETS;
		public static final TermVector WITH_POSITIONS_OFFSETS;
		private static final TermVector $VALUES[];

		public static TermVector[] values()
		{
			return (TermVector[])$VALUES.clone();
		}

		public static TermVector valueOf(String name)
		{
			return (TermVector)Enum.valueOf(org/apache/lucene/document/Field$TermVector, name);
		}

		public static TermVector toTermVector(boolean stored, boolean withOffsets, boolean withPositions)
		{
			if (!stored)
				return NO;
			if (withOffsets)
				if (withPositions)
					return WITH_POSITIONS_OFFSETS;
				else
					return WITH_OFFSETS;
			if (withPositions)
				return WITH_POSITIONS;
			else
				return YES;
		}

		public abstract boolean isStored();

		public abstract boolean withPositions();

		public abstract boolean withOffsets();

		static 
		{
			NO = new TermVector("NO", 0) {

				public boolean isStored()
				{
					return false;
				}

				public boolean withPositions()
				{
					return false;
				}

				public boolean withOffsets()
				{
					return false;
				}

			};
			YES = new TermVector("YES", 1) {

				public boolean isStored()
				{
					return true;
				}

				public boolean withPositions()
				{
					return false;
				}

				public boolean withOffsets()
				{
					return false;
				}

			};
			WITH_POSITIONS = new TermVector("WITH_POSITIONS", 2) {

				public boolean isStored()
				{
					return true;
				}

				public boolean withPositions()
				{
					return true;
				}

				public boolean withOffsets()
				{
					return false;
				}

			};
			WITH_OFFSETS = new TermVector("WITH_OFFSETS", 3) {

				public boolean isStored()
				{
					return true;
				}

				public boolean withPositions()
				{
					return false;
				}

				public boolean withOffsets()
				{
					return true;
				}

			};
			WITH_POSITIONS_OFFSETS = new TermVector("WITH_POSITIONS_OFFSETS", 4) {

				public boolean isStored()
				{
					return true;
				}

				public boolean withPositions()
				{
					return true;
				}

				public boolean withOffsets()
				{
					return true;
				}

			};
			$VALUES = (new TermVector[] {
				NO, YES, WITH_POSITIONS, WITH_OFFSETS, WITH_POSITIONS_OFFSETS
			});
		}

		private TermVector(String s, int i)
		{
			super(s, i);
		}

	}

	/**
	 * @deprecated Class Index is deprecated
	 */

	public static abstract class Index extends Enum
	{

		public static final Index NO;
		public static final Index ANALYZED;
		public static final Index NOT_ANALYZED;
		public static final Index NOT_ANALYZED_NO_NORMS;
		public static final Index ANALYZED_NO_NORMS;
		private static final Index $VALUES[];

		public static Index[] values()
		{
			return (Index[])$VALUES.clone();
		}

		public static Index valueOf(String name)
		{
			return (Index)Enum.valueOf(org/apache/lucene/document/Field$Index, name);
		}

		public static Index toIndex(boolean indexed, boolean analyzed)
		{
			return toIndex(indexed, analyzed, false);
		}

		public static Index toIndex(boolean indexed, boolean analyzed, boolean omitNorms)
		{
			if (!indexed)
				return NO;
			if (!omitNorms)
				if (analyzed)
					return ANALYZED;
				else
					return NOT_ANALYZED;
			if (analyzed)
				return ANALYZED_NO_NORMS;
			else
				return NOT_ANALYZED_NO_NORMS;
		}

		public abstract boolean isIndexed();

		public abstract boolean isAnalyzed();

		public abstract boolean omitNorms();

		static 
		{
			NO = new Index("NO", 0) {

				public boolean isIndexed()
				{
					return false;
				}

				public boolean isAnalyzed()
				{
					return false;
				}

				public boolean omitNorms()
				{
					return true;
				}

			};
			ANALYZED = new Index("ANALYZED", 1) {

				public boolean isIndexed()
				{
					return true;
				}

				public boolean isAnalyzed()
				{
					return true;
				}

				public boolean omitNorms()
				{
					return false;
				}

			};
			NOT_ANALYZED = new Index("NOT_ANALYZED", 2) {

				public boolean isIndexed()
				{
					return true;
				}

				public boolean isAnalyzed()
				{
					return false;
				}

				public boolean omitNorms()
				{
					return false;
				}

			};
			NOT_ANALYZED_NO_NORMS = new Index("NOT_ANALYZED_NO_NORMS", 3) {

				public boolean isIndexed()
				{
					return true;
				}

				public boolean isAnalyzed()
				{
					return false;
				}

				public boolean omitNorms()
				{
					return true;
				}

			};
			ANALYZED_NO_NORMS = new Index("ANALYZED_NO_NORMS", 4) {

				public boolean isIndexed()
				{
					return true;
				}

				public boolean isAnalyzed()
				{
					return true;
				}

				public boolean omitNorms()
				{
					return true;
				}

			};
			$VALUES = (new Index[] {
				NO, ANALYZED, NOT_ANALYZED, NOT_ANALYZED_NO_NORMS, ANALYZED_NO_NORMS
			});
		}

		private Index(String s, int i)
		{
			super(s, i);
		}

	}

	public static final class Store extends Enum
	{

		public static final Store YES;
		public static final Store NO;
		private static final Store $VALUES[];

		public static Store[] values()
		{
			return (Store[])$VALUES.clone();
		}

		public static Store valueOf(String name)
		{
			return (Store)Enum.valueOf(org/apache/lucene/document/Field$Store, name);
		}

		static 
		{
			YES = new Store("YES", 0);
			NO = new Store("NO", 1);
			$VALUES = (new Store[] {
				YES, NO
			});
		}

		private Store(String s, int i)
		{
			super(s, i);
		}
	}

	static final class StringTokenStream extends TokenStream
	{

		private final CharTermAttribute termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		private final OffsetAttribute offsetAttribute = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		private boolean used;
		private String value;

		void setValue(String value)
		{
			this.value = value;
		}

		public boolean incrementToken()
		{
			if (used)
			{
				return false;
			} else
			{
				clearAttributes();
				termAttribute.append(value);
				offsetAttribute.setOffset(0, value.length());
				used = true;
				return true;
			}
		}

		public void end()
		{
			int finalOffset = value.length();
			offsetAttribute.setOffset(finalOffset, finalOffset);
		}

		public void reset()
		{
			used = false;
		}

		public void close()
		{
			value = null;
		}

		StringTokenStream()
		{
			used = false;
			value = null;
		}
	}

	static final class ReusableStringReader extends Reader
	{

		private int pos;
		private int size;
		private String s;

		void setValue(String s)
		{
			this.s = s;
			size = s.length();
			pos = 0;
		}

		public int read()
		{
			if (pos < size)
			{
				return s.charAt(pos++);
			} else
			{
				s = null;
				return -1;
			}
		}

		public int read(char c[], int off, int len)
		{
			if (pos < size)
			{
				len = Math.min(len, size - pos);
				s.getChars(pos, pos + len, c, off);
				pos += len;
				return len;
			} else
			{
				s = null;
				return -1;
			}
		}

		public void close()
		{
			pos = size;
			s = null;
		}

		ReusableStringReader()
		{
			pos = 0;
			size = 0;
			s = null;
		}
	}


	protected final FieldType type;
	protected final String name;
	protected Object fieldsData;
	protected TokenStream tokenStream;
	private transient TokenStream internalTokenStream;
	private transient ReusableStringReader internalReader;
	protected float boost;

	protected Field(String name, FieldType type)
	{
		boost = 1.0F;
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		this.name = name;
		if (type == null)
		{
			throw new IllegalArgumentException("type cannot be null");
		} else
		{
			this.type = type;
			return;
		}
	}

	public Field(String name, Reader reader, FieldType type)
	{
		boost = 1.0F;
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (type == null)
			throw new IllegalArgumentException("type cannot be null");
		if (reader == null)
			throw new NullPointerException("reader cannot be null");
		if (type.stored())
			throw new IllegalArgumentException("fields with a Reader value cannot be stored");
		if (type.indexed() && !type.tokenized())
		{
			throw new IllegalArgumentException("non-tokenized fields must use String values");
		} else
		{
			this.name = name;
			fieldsData = reader;
			this.type = type;
			return;
		}
	}

	public Field(String name, TokenStream tokenStream, FieldType type)
	{
		boost = 1.0F;
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (tokenStream == null)
			throw new NullPointerException("tokenStream cannot be null");
		if (!type.indexed() || !type.tokenized())
			throw new IllegalArgumentException("TokenStream fields must be indexed and tokenized");
		if (type.stored())
		{
			throw new IllegalArgumentException("TokenStream fields cannot be stored");
		} else
		{
			this.name = name;
			fieldsData = null;
			this.tokenStream = tokenStream;
			this.type = type;
			return;
		}
	}

	public Field(String name, byte value[], FieldType type)
	{
		this(name, value, 0, value.length, type);
	}

	public Field(String name, byte value[], int offset, int length, FieldType type)
	{
		this(name, new BytesRef(value, offset, length), type);
	}

	public Field(String name, BytesRef bytes, FieldType type)
	{
		boost = 1.0F;
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (type.indexed())
		{
			throw new IllegalArgumentException("Fields with BytesRef values cannot be indexed");
		} else
		{
			fieldsData = bytes;
			this.type = type;
			this.name = name;
			return;
		}
	}

	public Field(String name, String value, FieldType type)
	{
		boost = 1.0F;
		if (name == null)
			throw new IllegalArgumentException("name cannot be null");
		if (value == null)
			throw new IllegalArgumentException("value cannot be null");
		if (!type.stored() && !type.indexed())
			throw new IllegalArgumentException("it doesn't make sense to have a field that is neither indexed nor stored");
		if (!type.indexed() && type.storeTermVectors())
		{
			throw new IllegalArgumentException("cannot store term vector information for a field that is not indexed");
		} else
		{
			this.type = type;
			this.name = name;
			fieldsData = value;
			return;
		}
	}

	public String stringValue()
	{
		if ((fieldsData instanceof String) || (fieldsData instanceof Number))
			return fieldsData.toString();
		else
			return null;
	}

	public Reader readerValue()
	{
		return (fieldsData instanceof Reader) ? (Reader)fieldsData : null;
	}

	public TokenStream tokenStreamValue()
	{
		return tokenStream;
	}

	public void setStringValue(String value)
	{
		if (!(fieldsData instanceof String))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to String").toString());
		} else
		{
			fieldsData = value;
			return;
		}
	}

	public void setReaderValue(Reader value)
	{
		if (!(fieldsData instanceof Reader))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Reader").toString());
		} else
		{
			fieldsData = value;
			return;
		}
	}

	public void setBytesValue(byte value[])
	{
		setBytesValue(new BytesRef(value));
	}

	public void setBytesValue(BytesRef value)
	{
		if (!(fieldsData instanceof BytesRef))
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to BytesRef").toString());
		if (type.indexed())
		{
			throw new IllegalArgumentException("cannot set a Reader value on an indexed field");
		} else
		{
			fieldsData = value;
			return;
		}
	}

	public void setByteValue(byte value)
	{
		if (!(fieldsData instanceof Byte))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Byte").toString());
		} else
		{
			fieldsData = Byte.valueOf(value);
			return;
		}
	}

	public void setShortValue(short value)
	{
		if (!(fieldsData instanceof Short))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Short").toString());
		} else
		{
			fieldsData = Short.valueOf(value);
			return;
		}
	}

	public void setIntValue(int value)
	{
		if (!(fieldsData instanceof Integer))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Integer").toString());
		} else
		{
			fieldsData = Integer.valueOf(value);
			return;
		}
	}

	public void setLongValue(long value)
	{
		if (!(fieldsData instanceof Long))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Long").toString());
		} else
		{
			fieldsData = Long.valueOf(value);
			return;
		}
	}

	public void setFloatValue(float value)
	{
		if (!(fieldsData instanceof Float))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Float").toString());
		} else
		{
			fieldsData = Float.valueOf(value);
			return;
		}
	}

	public void setDoubleValue(double value)
	{
		if (!(fieldsData instanceof Double))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("cannot change value type from ").append(fieldsData.getClass().getSimpleName()).append(" to Double").toString());
		} else
		{
			fieldsData = Double.valueOf(value);
			return;
		}
	}

	public void setTokenStream(TokenStream tokenStream)
	{
		if (!type.indexed() || !type.tokenized())
			throw new IllegalArgumentException("TokenStream fields must be indexed and tokenized");
		if (type.numericType() != null)
		{
			throw new IllegalArgumentException("cannot set private TokenStream on numeric fields");
		} else
		{
			this.tokenStream = tokenStream;
			return;
		}
	}

	public String name()
	{
		return name;
	}

	public float boost()
	{
		return boost;
	}

	public void setBoost(float boost)
	{
		if (boost != 1.0F && (!type.indexed() || type.omitNorms()))
		{
			throw new IllegalArgumentException("You cannot set an index-time boost on an unindexed field, or one that omits norms");
		} else
		{
			this.boost = boost;
			return;
		}
	}

	public Number numericValue()
	{
		if (fieldsData instanceof Number)
			return (Number)fieldsData;
		else
			return null;
	}

	public BytesRef binaryValue()
	{
		if (fieldsData instanceof BytesRef)
			return (BytesRef)fieldsData;
		else
			return null;
	}

	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append(type.toString());
		result.append('<');
		result.append(name);
		result.append(':');
		if (fieldsData != null)
			result.append(fieldsData);
		result.append('>');
		return result.toString();
	}

	public FieldType fieldType()
	{
		return type;
	}

	public TokenStream tokenStream(Analyzer analyzer)
		throws IOException
	{
		if (!fieldType().indexed())
			return null;
		FieldType.NumericType numericType = fieldType().numericType();
		if (numericType != null)
		{
			if (!(internalTokenStream instanceof NumericTokenStream))
				internalTokenStream = new NumericTokenStream(type.numericPrecisionStep());
			NumericTokenStream nts = (NumericTokenStream)internalTokenStream;
			Number val = (Number)fieldsData;
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$document$FieldType$NumericType[];
				static final int $SwitchMap$org$apache$lucene$document$Field$Index[];
				static final int $SwitchMap$org$apache$lucene$document$Field$TermVector[];

				static 
				{
					$SwitchMap$org$apache$lucene$document$Field$TermVector = new int[TermVector.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$TermVector[TermVector.NO.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$TermVector[TermVector.YES.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$TermVector[TermVector.WITH_POSITIONS.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$TermVector[TermVector.WITH_OFFSETS.ordinal()] = 4;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$TermVector[TermVector.WITH_POSITIONS_OFFSETS.ordinal()] = 5;
					}
					catch (NoSuchFieldError ex) { }
					$SwitchMap$org$apache$lucene$document$Field$Index = new int[Index.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$Index[Index.ANALYZED.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$Index[Index.ANALYZED_NO_NORMS.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$Index[Index.NOT_ANALYZED.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$Index[Index.NOT_ANALYZED_NO_NORMS.ordinal()] = 4;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$Field$Index[Index.NO.ordinal()] = 5;
					}
					catch (NoSuchFieldError ex) { }
					$SwitchMap$org$apache$lucene$document$FieldType$NumericType = new int[FieldType.NumericType.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$document$FieldType$NumericType[FieldType.NumericType.INT.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$FieldType$NumericType[FieldType.NumericType.LONG.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$FieldType$NumericType[FieldType.NumericType.FLOAT.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$document$FieldType$NumericType[FieldType.NumericType.DOUBLE.ordinal()] = 4;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.org.apache.lucene.document.FieldType.NumericType[numericType.ordinal()])
			{
			case 1: // '\001'
				nts.setIntValue(val.intValue());
				break;

			case 2: // '\002'
				nts.setLongValue(val.longValue());
				break;

			case 3: // '\003'
				nts.setFloatValue(val.floatValue());
				break;

			case 4: // '\004'
				nts.setDoubleValue(val.doubleValue());
				break;

			default:
				throw new AssertionError("Should never get here");
			}
			return internalTokenStream;
		}
		if (!fieldType().tokenized())
		{
			if (stringValue() == null)
				throw new IllegalArgumentException("Non-Tokenized Fields must have a String value");
			if (!(internalTokenStream instanceof StringTokenStream))
				internalTokenStream = new StringTokenStream();
			((StringTokenStream)internalTokenStream).setValue(stringValue());
			return internalTokenStream;
		}
		if (tokenStream != null)
			return tokenStream;
		if (readerValue() != null)
			return analyzer.tokenStream(name(), readerValue());
		if (stringValue() != null)
		{
			if (internalReader == null)
				internalReader = new ReusableStringReader();
			internalReader.setValue(stringValue());
			return analyzer.tokenStream(name(), internalReader);
		} else
		{
			throw new IllegalArgumentException("Field must have either TokenStream, String, Reader or Number value");
		}
	}

	/**
	 * @deprecated Method translateFieldType is deprecated
	 */

	public static final FieldType translateFieldType(Store store, Index index, TermVector termVector)
	{
		FieldType ft = new FieldType();
		ft.setStored(store == Store.YES);
		switch (1..SwitchMap.org.apache.lucene.document.Field.Index[index.ordinal()])
		{
		case 1: // '\001'
			ft.setIndexed(true);
			ft.setTokenized(true);
			break;

		case 2: // '\002'
			ft.setIndexed(true);
			ft.setTokenized(true);
			ft.setOmitNorms(true);
			break;

		case 3: // '\003'
			ft.setIndexed(true);
			ft.setTokenized(false);
			break;

		case 4: // '\004'
			ft.setIndexed(true);
			ft.setTokenized(false);
			ft.setOmitNorms(true);
			break;
		}
		switch (1..SwitchMap.org.apache.lucene.document.Field.TermVector[termVector.ordinal()])
		{
		case 2: // '\002'
			ft.setStoreTermVectors(true);
			break;

		case 3: // '\003'
			ft.setStoreTermVectors(true);
			ft.setStoreTermVectorPositions(true);
			break;

		case 4: // '\004'
			ft.setStoreTermVectors(true);
			ft.setStoreTermVectorOffsets(true);
			break;

		case 5: // '\005'
			ft.setStoreTermVectors(true);
			ft.setStoreTermVectorPositions(true);
			ft.setStoreTermVectorOffsets(true);
			break;
		}
		ft.freeze();
		return ft;
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, String value, Store store, Index index)
	{
		this(name, value, translateFieldType(store, index, TermVector.NO));
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, String value, Store store, Index index, TermVector termVector)
	{
		this(name, value, translateFieldType(store, index, termVector));
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, Reader reader)
	{
		this(name, reader, TermVector.NO);
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, Reader reader, TermVector termVector)
	{
		this(name, reader, translateFieldType(Store.NO, Index.ANALYZED, termVector));
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, TokenStream tokenStream)
	{
		this(name, tokenStream, TermVector.NO);
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, TokenStream tokenStream, TermVector termVector)
	{
		this(name, tokenStream, translateFieldType(Store.NO, Index.ANALYZED, termVector));
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, byte value[])
	{
		this(name, value, translateFieldType(Store.YES, Index.NO, TermVector.NO));
	}

	/**
	 * @deprecated Method Field is deprecated
	 */

	public Field(String name, byte value[], int offset, int length)
	{
		this(name, value, offset, length, translateFieldType(Store.YES, Index.NO, TermVector.NO));
	}

	public volatile IndexableFieldType fieldType()
	{
		return fieldType();
	}
}
