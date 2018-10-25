// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypePromoter.java

package org.apache.lucene.index;

import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			DocValues

class TypePromoter
{
	static class TypeCompatibility
	{

		private final TypePromoter base;
		private final TypePromoter spare = TypePromoter.newPromoter();

		boolean isCompatible(DocValues.Type type, int valueSize)
		{
			TypePromoter reset = TypePromoter.reset(type, valueSize, spare);
			if (base.isTypeCompatible(reset))
			{
				if (base.isBytesCompatible(reset))
					return base.valueSize == -1 || base.valueSize == valueSize;
				if (base.flags == reset.flags)
					return true;
				if (base.isNumericSizeCompatible(reset))
					return base.valueSize == -1 || base.valueSize > valueSize && valueSize > 0;
			}
			return false;
		}

		DocValues.Type getBaseType()
		{
			return base.type();
		}

		int getBaseSize()
		{
			return base.valueSize;
		}

		TypeCompatibility(DocValues.Type type, int valueSize)
		{
			base = TypePromoter.create(type, valueSize);
		}
	}

	private static class IdentityTypePromoter extends TypePromoter
	{

		protected TypePromoter set(DocValues.Type type, int flags, int valueSize)
		{
			throw new UnsupportedOperationException("can not reset IdendityPromotoer");
		}

		public TypePromoter promote(TypePromoter promoter)
		{
			return promoter;
		}

		public IdentityTypePromoter()
		{
			super(null, 0, -1);
		}
	}


	private static final Map FLAGS_MAP;
	private static final TypePromoter IDENTITY_PROMOTER = new IdentityTypePromoter();
	public static final int VAR_TYPE_VALUE_SIZE = -1;
	private static final int IS_INT = 5;
	private static final int IS_BYTE = 2;
	private static final int IS_FLOAT = 4;
	private static final int IS_VAR = 8;
	private static final int IS_FIXED = 24;
	private static final int PROMOTE_TO_VAR_SIZE_MASK = -9;
	private static final int IS_STRAIGHT = 32;
	private static final int IS_DEREF = 96;
	private static final int IS_SORTED = 128;
	private static final int IS_8_BIT = 16128;
	private static final int IS_16_BIT = 15872;
	private static final int IS_32_BIT = 11264;
	private static final int IS_64_BIT = 2048;
	private static final int IS_32_BIT_FLOAT = 12288;
	private static final int IS_64_BIT_FLOAT = 8192;
	private DocValues.Type type;
	private int flags;
	private int valueSize;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/TypePromoter.desiredAssertionStatus();

	public int getValueSize()
	{
		return valueSize;
	}

	protected TypePromoter()
	{
	}

	protected TypePromoter(DocValues.Type type, int flags, int valueSize)
	{
		this.type = type;
		this.flags = flags;
		this.valueSize = valueSize;
	}

	protected TypePromoter set(DocValues.Type type, int flags, int valueSize)
	{
		this.type = type;
		this.flags = flags;
		this.valueSize = valueSize;
		return this;
	}

	public TypePromoter promote(TypePromoter promoter)
	{
		return promote(promoter, newPromoter());
	}

	private TypePromoter promote(TypePromoter promoter, TypePromoter spare)
	{
		int promotedFlags = promoter.flags & flags;
		TypePromoter promoted = reset((DocValues.Type)FLAGS_MAP.get(Integer.valueOf(promotedFlags)), valueSize, spare);
		if (promoted == null)
			return create(DocValues.Type.BYTES_VAR_STRAIGHT, -1);
		if ((promoted.flags & 2) != 0 && (promoted.flags & 0x18) == 24)
		{
			if (valueSize == promoter.valueSize)
				return promoted;
			else
				return reset((DocValues.Type)FLAGS_MAP.get(Integer.valueOf(promoted.flags & -9)), -1, spare);
		} else
		{
			return promoted;
		}
	}

	public DocValues.Type type()
	{
		return type;
	}

	private boolean isTypeCompatible(TypePromoter promoter)
	{
		int promotedFlags = promoter.flags & flags;
		return (promotedFlags & 7) > 0;
	}

	private boolean isBytesCompatible(TypePromoter promoter)
	{
		int promotedFlags = promoter.flags & flags;
		return (promotedFlags & 2) > 0 && (promotedFlags & 0x18) > 0;
	}

	private boolean isNumericSizeCompatible(TypePromoter promoter)
	{
		int promotedFlags = promoter.flags & flags;
		return (promotedFlags & 2) == 0 && ((promotedFlags & 0x18) > 0 && (promotedFlags & 0x3f00) > 0 || (promotedFlags & 8) > 0);
	}

	public String toString()
	{
		return (new StringBuilder()).append("TypePromoter [type=").append(type).append(", sizeInBytes=").append(valueSize).append("]").toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + flags;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + valueSize;
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypePromoter other = (TypePromoter)obj;
		if (flags != other.flags)
			return false;
		if (type != other.type)
			return false;
		return valueSize == other.valueSize;
	}

	public static TypePromoter create(DocValues.Type type, int valueSize)
	{
		return reset(type, valueSize, new TypePromoter());
	}

	private static TypePromoter reset(DocValues.Type type, int valueSize, TypePromoter promoter)
	{
		if (type == null)
			return null;
		static class 1
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
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 6;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_16.ordinal()] = 7;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_32.ordinal()] = 8;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_64.ordinal()] = 9;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_8.ordinal()] = 10;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_32.ordinal()] = 11;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_64.ordinal()] = 12;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.VAR_INTS.ordinal()] = 13;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 1: // '\001'
			return promoter.set(type, 122, valueSize);

		case 2: // '\002'
			return promoter.set(type, 154, valueSize);

		case 3: // '\003'
			return promoter.set(type, 58, valueSize);

		case 4: // '\004'
			return promoter.set(type, 106, -1);

		case 5: // '\005'
			return promoter.set(type, 138, -1);

		case 6: // '\006'
			return promoter.set(type, 42, -1);

		case 7: // '\007'
			return promoter.set(type, 15933, valueSize);

		case 8: // '\b'
			return promoter.set(type, 11325, valueSize);

		case 9: // '\t'
			return promoter.set(type, 2109, valueSize);

		case 10: // '\n'
			return promoter.set(type, 16189, valueSize);

		case 11: // '\013'
			return promoter.set(type, 12348, valueSize);

		case 12: // '\f'
			return promoter.set(type, 8252, valueSize);

		case 13: // '\r'
			return promoter.set(type, 45, -1);
		}
		throw new IllegalStateException();
	}

	public static int getValueSize(DocValues.Type type, BytesRef ref)
	{
		switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
		{
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 13: // '\r'
			return -1;

		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			if (!$assertionsDisabled && ref == null)
				throw new AssertionError();
			else
				return ref.length;

		case 7: // '\007'
			return 2;

		case 8: // '\b'
		case 11: // '\013'
			return 4;

		case 9: // '\t'
		case 12: // '\f'
			return 8;

		case 10: // '\n'
			return 1;
		}
		throw new IllegalArgumentException((new StringBuilder()).append("unknonw docvalues type: ").append(type.name()).toString());
	}

	public static TypePromoter getIdentityPromoter()
	{
		return IDENTITY_PROMOTER;
	}

	private static TypePromoter newPromoter()
	{
		return new TypePromoter(null, 0, -1);
	}

	static 
	{
		FLAGS_MAP = new HashMap();
		DocValues.Type arr$[] = DocValues.Type.values();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			DocValues.Type type = arr$[i$];
			TypePromoter create = create(type, -1);
			FLAGS_MAP.put(Integer.valueOf(create.flags), type);
		}

	}







}
