// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericTokenStream.java

package org.apache.lucene.analysis;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.analysis:
//			TokenStream

public final class NumericTokenStream extends TokenStream
{
	public static final class NumericTermAttributeImpl extends AttributeImpl
		implements NumericTermAttribute, TermToBytesRefAttribute
	{

		private long value;
		private int valueSize;
		private int shift;
		private int precisionStep;
		private BytesRef bytes;
		static final boolean $assertionsDisabled = !org/apache/lucene/analysis/NumericTokenStream.desiredAssertionStatus();

		public BytesRef getBytesRef()
		{
			return bytes;
		}

		public int fillBytesRef()
		{
			if (!$assertionsDisabled && valueSize != 64 && valueSize != 32)
				throw new AssertionError();
			return valueSize != 64 ? NumericUtils.intToPrefixCoded((int)value, shift, bytes) : NumericUtils.longToPrefixCoded(value, shift, bytes);
			IllegalArgumentException iae;
			iae;
			bytes.length = 0;
			return 0;
		}

		public int getShift()
		{
			return shift;
		}

		public void setShift(int shift)
		{
			this.shift = shift;
		}

		public int incShift()
		{
			return shift += precisionStep;
		}

		public long getRawValue()
		{
			return value & ~((1L << shift) - 1L);
		}

		public int getValueSize()
		{
			return valueSize;
		}

		public void init(long value, int valueSize, int precisionStep, int shift)
		{
			this.value = value;
			this.valueSize = valueSize;
			this.precisionStep = precisionStep;
			this.shift = shift;
		}

		public void clear()
		{
		}

		public void reflectWith(AttributeReflector reflector)
		{
			fillBytesRef();
			reflector.reflect(org/apache/lucene/analysis/tokenattributes/TermToBytesRefAttribute, "bytes", BytesRef.deepCopyOf(bytes));
			reflector.reflect(org/apache/lucene/analysis/NumericTokenStream$NumericTermAttribute, "shift", Integer.valueOf(shift));
			reflector.reflect(org/apache/lucene/analysis/NumericTokenStream$NumericTermAttribute, "rawValue", Long.valueOf(getRawValue()));
			reflector.reflect(org/apache/lucene/analysis/NumericTokenStream$NumericTermAttribute, "valueSize", Integer.valueOf(valueSize));
		}

		public void copyTo(AttributeImpl target)
		{
			NumericTermAttribute a = (NumericTermAttribute)target;
			a.init(value, valueSize, precisionStep, shift);
		}


		public NumericTermAttributeImpl()
		{
			value = 0L;
			valueSize = 0;
			shift = 0;
			precisionStep = 0;
			bytes = new BytesRef();
		}
	}

	private static final class NumericAttributeFactory extends org.apache.lucene.util.AttributeSource.AttributeFactory
	{

		private final org.apache.lucene.util.AttributeSource.AttributeFactory delegate;

		public AttributeImpl createAttributeInstance(Class attClass)
		{
			if (org/apache/lucene/analysis/tokenattributes/CharTermAttribute.isAssignableFrom(attClass))
				throw new IllegalArgumentException("NumericTokenStream does not support CharTermAttribute.");
			else
				return delegate.createAttributeInstance(attClass);
		}

		NumericAttributeFactory(org.apache.lucene.util.AttributeSource.AttributeFactory delegate)
		{
			this.delegate = delegate;
		}
	}

	public static interface NumericTermAttribute
		extends Attribute
	{

		public abstract int getShift();

		public abstract long getRawValue();

		public abstract int getValueSize();

		public abstract void init(long l, int i, int j, int k);

		public abstract void setShift(int i);

		public abstract int incShift();
	}


	public static final String TOKEN_TYPE_FULL_PREC = "fullPrecNumeric";
	public static final String TOKEN_TYPE_LOWER_PREC = "lowerPrecNumeric";
	private final NumericTermAttribute numericAtt;
	private final TypeAttribute typeAtt;
	private final PositionIncrementAttribute posIncrAtt;
	private int valSize;
	private final int precisionStep;

	public NumericTokenStream()
	{
		this(org.apache.lucene.util.AttributeSource.AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, 4);
	}

	public NumericTokenStream(int precisionStep)
	{
		this(org.apache.lucene.util.AttributeSource.AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, precisionStep);
	}

	public NumericTokenStream(org.apache.lucene.util.AttributeSource.AttributeFactory factory, int precisionStep)
	{
		super(new NumericAttributeFactory(factory));
		numericAtt = (NumericTermAttribute)addAttribute(org/apache/lucene/analysis/NumericTokenStream$NumericTermAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		valSize = 0;
		if (precisionStep < 1)
		{
			throw new IllegalArgumentException("precisionStep must be >=1");
		} else
		{
			this.precisionStep = precisionStep;
			numericAtt.setShift(-precisionStep);
			return;
		}
	}

	public NumericTokenStream setLongValue(long value)
	{
		numericAtt.init(value, valSize = 64, precisionStep, -precisionStep);
		return this;
	}

	public NumericTokenStream setIntValue(int value)
	{
		numericAtt.init(value, valSize = 32, precisionStep, -precisionStep);
		return this;
	}

	public NumericTokenStream setDoubleValue(double value)
	{
		numericAtt.init(NumericUtils.doubleToSortableLong(value), valSize = 64, precisionStep, -precisionStep);
		return this;
	}

	public NumericTokenStream setFloatValue(float value)
	{
		numericAtt.init(NumericUtils.floatToSortableInt(value), valSize = 32, precisionStep, -precisionStep);
		return this;
	}

	public void reset()
	{
		if (valSize == 0)
		{
			throw new IllegalStateException("call set???Value() before usage");
		} else
		{
			numericAtt.setShift(-precisionStep);
			return;
		}
	}

	public boolean incrementToken()
	{
		if (valSize == 0)
		{
			throw new IllegalStateException("call set???Value() before usage");
		} else
		{
			clearAttributes();
			int shift = numericAtt.incShift();
			typeAtt.setType(shift != 0 ? "lowerPrecNumeric" : "fullPrecNumeric");
			posIncrAtt.setPositionIncrement(shift != 0 ? 0 : 1);
			return shift < valSize;
		}
	}

	public int getPrecisionStep()
	{
		return precisionStep;
	}
}
