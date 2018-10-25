// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldType.java

package org.apache.lucene.document;

import org.apache.lucene.index.*;

public class FieldType
	implements IndexableFieldType
{
	public static final class NumericType extends Enum
	{

		public static final NumericType INT;
		public static final NumericType LONG;
		public static final NumericType FLOAT;
		public static final NumericType DOUBLE;
		private static final NumericType $VALUES[];

		public static NumericType[] values()
		{
			return (NumericType[])$VALUES.clone();
		}

		public static NumericType valueOf(String name)
		{
			return (NumericType)Enum.valueOf(org/apache/lucene/document/FieldType$NumericType, name);
		}

		static 
		{
			INT = new NumericType("INT", 0);
			LONG = new NumericType("LONG", 1);
			FLOAT = new NumericType("FLOAT", 2);
			DOUBLE = new NumericType("DOUBLE", 3);
			$VALUES = (new NumericType[] {
				INT, LONG, FLOAT, DOUBLE
			});
		}

		private NumericType(String s, int i)
		{
			super(s, i);
		}
	}


	private boolean indexed;
	private boolean stored;
	private boolean tokenized;
	private boolean storeTermVectors;
	private boolean storeTermVectorOffsets;
	private boolean storeTermVectorPositions;
	private boolean storeTermVectorPayloads;
	private boolean omitNorms;
	private org.apache.lucene.index.FieldInfo.IndexOptions indexOptions;
	private org.apache.lucene.index.DocValues.Type docValueType;
	private NumericType numericType;
	private boolean frozen;
	private int numericPrecisionStep;

	public FieldType(FieldType ref)
	{
		tokenized = true;
		indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
		numericPrecisionStep = 4;
		indexed = ref.indexed();
		stored = ref.stored();
		tokenized = ref.tokenized();
		storeTermVectors = ref.storeTermVectors();
		storeTermVectorOffsets = ref.storeTermVectorOffsets();
		storeTermVectorPositions = ref.storeTermVectorPositions();
		storeTermVectorPayloads = ref.storeTermVectorPayloads();
		omitNorms = ref.omitNorms();
		indexOptions = ref.indexOptions();
		docValueType = ref.docValueType();
		numericType = ref.numericType();
	}

	public FieldType()
	{
		tokenized = true;
		indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
		numericPrecisionStep = 4;
	}

	private void checkIfFrozen()
	{
		if (frozen)
			throw new IllegalStateException("this FieldType is already frozen and cannot be changed");
		else
			return;
	}

	public void freeze()
	{
		frozen = true;
	}

	public boolean indexed()
	{
		return indexed;
	}

	public void setIndexed(boolean value)
	{
		checkIfFrozen();
		indexed = value;
	}

	public boolean stored()
	{
		return stored;
	}

	public void setStored(boolean value)
	{
		checkIfFrozen();
		stored = value;
	}

	public boolean tokenized()
	{
		return tokenized;
	}

	public void setTokenized(boolean value)
	{
		checkIfFrozen();
		tokenized = value;
	}

	public boolean storeTermVectors()
	{
		return storeTermVectors;
	}

	public void setStoreTermVectors(boolean value)
	{
		checkIfFrozen();
		storeTermVectors = value;
	}

	public boolean storeTermVectorOffsets()
	{
		return storeTermVectorOffsets;
	}

	public void setStoreTermVectorOffsets(boolean value)
	{
		checkIfFrozen();
		storeTermVectorOffsets = value;
	}

	public boolean storeTermVectorPositions()
	{
		return storeTermVectorPositions;
	}

	public void setStoreTermVectorPositions(boolean value)
	{
		checkIfFrozen();
		storeTermVectorPositions = value;
	}

	public boolean storeTermVectorPayloads()
	{
		return storeTermVectorPayloads;
	}

	public void setStoreTermVectorPayloads(boolean value)
	{
		checkIfFrozen();
		storeTermVectorPayloads = value;
	}

	public boolean omitNorms()
	{
		return omitNorms;
	}

	public void setOmitNorms(boolean value)
	{
		checkIfFrozen();
		omitNorms = value;
	}

	public org.apache.lucene.index.FieldInfo.IndexOptions indexOptions()
	{
		return indexOptions;
	}

	public void setIndexOptions(org.apache.lucene.index.FieldInfo.IndexOptions value)
	{
		checkIfFrozen();
		indexOptions = value;
	}

	public void setDocValueType(org.apache.lucene.index.DocValues.Type type)
	{
		checkIfFrozen();
		docValueType = type;
	}

	public org.apache.lucene.index.DocValues.Type docValueType()
	{
		return docValueType;
	}

	public void setNumericType(NumericType type)
	{
		checkIfFrozen();
		numericType = type;
	}

	public NumericType numericType()
	{
		return numericType;
	}

	public void setNumericPrecisionStep(int precisionStep)
	{
		checkIfFrozen();
		if (precisionStep < 1)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("precisionStep must be >= 1 (got ").append(precisionStep).append(")").toString());
		} else
		{
			numericPrecisionStep = precisionStep;
			return;
		}
	}

	public int numericPrecisionStep()
	{
		return numericPrecisionStep;
	}

	public final String toString()
	{
		StringBuilder result = new StringBuilder();
		if (stored())
			result.append("stored");
		if (indexed())
		{
			if (result.length() > 0)
				result.append(",");
			result.append("indexed");
			if (tokenized())
				result.append(",tokenized");
			if (storeTermVectors())
				result.append(",termVector");
			if (storeTermVectorOffsets())
				result.append(",termVectorOffsets");
			if (storeTermVectorPositions())
			{
				result.append(",termVectorPosition");
				if (storeTermVectorPayloads())
					result.append(",termVectorPayloads");
			}
			if (omitNorms())
				result.append(",omitNorms");
			if (indexOptions != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
			{
				result.append(",indexOptions=");
				result.append(indexOptions);
			}
			if (numericType != null)
			{
				result.append(",numericType=");
				result.append(numericType);
				result.append(",numericPrecisionStep=");
				result.append(numericPrecisionStep);
			}
		}
		if (docValueType != null)
		{
			if (result.length() > 0)
				result.append(",");
			result.append("docValueType=");
			result.append(docValueType);
		}
		return result.toString();
	}
}
