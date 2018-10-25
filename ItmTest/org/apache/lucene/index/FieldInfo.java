// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldInfo.java

package org.apache.lucene.index;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package org.apache.lucene.index:
//			DocValues

public final class FieldInfo
{
	public static final class IndexOptions extends Enum
	{

		public static final IndexOptions DOCS_ONLY;
		public static final IndexOptions DOCS_AND_FREQS;
		public static final IndexOptions DOCS_AND_FREQS_AND_POSITIONS;
		public static final IndexOptions DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
		private static final IndexOptions $VALUES[];

		public static IndexOptions[] values()
		{
			return (IndexOptions[])$VALUES.clone();
		}

		public static IndexOptions valueOf(String name)
		{
			return (IndexOptions)Enum.valueOf(org/apache/lucene/index/FieldInfo$IndexOptions, name);
		}

		static 
		{
			DOCS_ONLY = new IndexOptions("DOCS_ONLY", 0);
			DOCS_AND_FREQS = new IndexOptions("DOCS_AND_FREQS", 1);
			DOCS_AND_FREQS_AND_POSITIONS = new IndexOptions("DOCS_AND_FREQS_AND_POSITIONS", 2);
			DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS = new IndexOptions("DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS", 3);
			$VALUES = (new IndexOptions[] {
				DOCS_ONLY, DOCS_AND_FREQS, DOCS_AND_FREQS_AND_POSITIONS, DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS
			});
		}

		private IndexOptions(String s, int i)
		{
			super(s, i);
		}
	}


	public final String name;
	public final int number;
	private boolean indexed;
	private DocValues.Type docValueType;
	private boolean storeTermVector;
	private DocValues.Type normType;
	private boolean omitNorms;
	private IndexOptions indexOptions;
	private boolean storePayloads;
	private Map attributes;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FieldInfo.desiredAssertionStatus();

	public FieldInfo(String name, boolean indexed, int number, boolean storeTermVector, boolean omitNorms, boolean storePayloads, IndexOptions indexOptions, 
			DocValues.Type docValues, DocValues.Type normsType, Map attributes)
	{
		this.name = name;
		this.indexed = indexed;
		this.number = number;
		docValueType = docValues;
		if (indexed)
		{
			this.storeTermVector = storeTermVector;
			this.storePayloads = storePayloads;
			this.omitNorms = omitNorms;
			this.indexOptions = indexOptions;
			normType = omitNorms ? null : normsType;
		} else
		{
			this.storeTermVector = false;
			this.storePayloads = false;
			this.omitNorms = false;
			this.indexOptions = null;
			normType = null;
		}
		this.attributes = attributes;
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	private boolean checkConsistency()
	{
		if (!indexed)
		{
			if (!$assertionsDisabled && storeTermVector)
				throw new AssertionError();
			if (!$assertionsDisabled && storePayloads)
				throw new AssertionError();
			if (!$assertionsDisabled && omitNorms)
				throw new AssertionError();
			if (!$assertionsDisabled && normType != null)
				throw new AssertionError();
			if (!$assertionsDisabled && indexOptions != null)
				throw new AssertionError();
		} else
		{
			if (!$assertionsDisabled && indexOptions == null)
				throw new AssertionError();
			if (omitNorms && !$assertionsDisabled && normType != null)
				throw new AssertionError();
			if (!$assertionsDisabled && indexOptions.compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0 && storePayloads)
				throw new AssertionError();
		}
		return true;
	}

	void update(boolean indexed, boolean storeTermVector, boolean omitNorms, boolean storePayloads, IndexOptions indexOptions)
	{
		if (this.indexed != indexed)
			this.indexed = true;
		if (indexed)
		{
			if (this.storeTermVector != storeTermVector)
				this.storeTermVector = true;
			if (this.storePayloads != storePayloads)
				this.storePayloads = true;
			if (this.omitNorms != omitNorms)
			{
				this.omitNorms = true;
				normType = null;
			}
			if (this.indexOptions != indexOptions)
			{
				if (this.indexOptions == null)
					this.indexOptions = indexOptions;
				else
					this.indexOptions = this.indexOptions.compareTo(indexOptions) >= 0 ? indexOptions : this.indexOptions;
				if (this.indexOptions.compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
					this.storePayloads = false;
			}
		}
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	void setDocValuesType(DocValues.Type type)
	{
		docValueType = type;
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	public IndexOptions getIndexOptions()
	{
		return indexOptions;
	}

	public boolean hasDocValues()
	{
		return docValueType != null;
	}

	public DocValues.Type getDocValuesType()
	{
		return docValueType;
	}

	public DocValues.Type getNormType()
	{
		return normType;
	}

	void setStoreTermVectors()
	{
		storeTermVector = true;
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	void setStorePayloads()
	{
		if (indexed && indexOptions.compareTo(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0)
			storePayloads = true;
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	void setNormValueType(DocValues.Type type)
	{
		normType = type;
		if (!$assertionsDisabled && !checkConsistency())
			throw new AssertionError();
		else
			return;
	}

	public boolean omitsNorms()
	{
		return omitNorms;
	}

	public boolean hasNorms()
	{
		return normType != null;
	}

	public boolean isIndexed()
	{
		return indexed;
	}

	public boolean hasPayloads()
	{
		return storePayloads;
	}

	public boolean hasVectors()
	{
		return storeTermVector;
	}

	public String getAttribute(String key)
	{
		if (attributes == null)
			return null;
		else
			return (String)attributes.get(key);
	}

	public String putAttribute(String key, String value)
	{
		if (attributes == null)
			attributes = new HashMap();
		return (String)attributes.put(key, value);
	}

	public Map attributes()
	{
		return attributes;
	}

}
