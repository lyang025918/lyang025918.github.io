// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldInfos.java

package org.apache.lucene.index;

import java.util.*;

// Referenced classes of package org.apache.lucene.index:
//			FieldInfo, IndexableFieldType, DocValues

public class FieldInfos
	implements Iterable
{
	static final class Builder
	{

		private final HashMap byName;
		final FieldNumbers globalFieldNumbers;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/FieldInfos.desiredAssertionStatus();

		public void add(FieldInfos other)
		{
			FieldInfo fieldInfo;
			for (Iterator i$ = other.iterator(); i$.hasNext(); add(fieldInfo))
				fieldInfo = (FieldInfo)i$.next();

		}

		private void putInternal(FieldInfo fi)
		{
			if (!$assertionsDisabled && byName.containsKey(fi.name))
				throw new AssertionError();
			if (!$assertionsDisabled && !globalFieldNumbers.containsConsistent(Integer.valueOf(fi.number), fi.name))
			{
				throw new AssertionError();
			} else
			{
				byName.put(fi.name, fi);
				return;
			}
		}

		FieldInfo addOrUpdate(String name, boolean isIndexed, boolean storeTermVector, boolean omitNorms, boolean storePayloads, FieldInfo.IndexOptions indexOptions, DocValues.Type docValues, 
				DocValues.Type normType)
		{
			return addOrUpdateInternal(name, -1, isIndexed, storeTermVector, omitNorms, storePayloads, indexOptions, docValues, normType);
		}

		public FieldInfo addOrUpdate(String name, IndexableFieldType fieldType)
		{
			return addOrUpdateInternal(name, -1, fieldType.indexed(), false, fieldType.omitNorms(), false, fieldType.indexOptions(), null, null);
		}

		private FieldInfo addOrUpdateInternal(String name, int preferredFieldNumber, boolean isIndexed, boolean storeTermVector, boolean omitNorms, boolean storePayloads, FieldInfo.IndexOptions indexOptions, 
				DocValues.Type docValues, DocValues.Type normType)
		{
			FieldInfo fi = fieldInfo(name);
			if (fi == null)
			{
				int fieldNumber = globalFieldNumbers.addOrGet(name, preferredFieldNumber);
				fi = addInternal(name, fieldNumber, isIndexed, storeTermVector, omitNorms, storePayloads, indexOptions, docValues, normType);
			} else
			{
				fi.update(isIndexed, storeTermVector, omitNorms, storePayloads, indexOptions);
				if (docValues != null)
					fi.setDocValuesType(docValues);
				if (!fi.omitsNorms() && normType != null)
					fi.setNormValueType(normType);
			}
			return fi;
		}

		public FieldInfo add(FieldInfo fi)
		{
			return addOrUpdateInternal(fi.name, fi.number, fi.isIndexed(), fi.hasVectors(), fi.omitsNorms(), fi.hasPayloads(), fi.getIndexOptions(), fi.getDocValuesType(), fi.getNormType());
		}

		private FieldInfo addInternal(String name, int fieldNumber, boolean isIndexed, boolean storeTermVector, boolean omitNorms, boolean storePayloads, FieldInfo.IndexOptions indexOptions, 
				DocValues.Type docValuesType, DocValues.Type normType)
		{
			globalFieldNumbers.setIfNotSet(fieldNumber, name);
			FieldInfo fi = new FieldInfo(name, isIndexed, fieldNumber, storeTermVector, omitNorms, storePayloads, indexOptions, docValuesType, normType, null);
			putInternal(fi);
			return fi;
		}

		public FieldInfo fieldInfo(String fieldName)
		{
			return (FieldInfo)byName.get(fieldName);
		}

		final FieldInfos finish()
		{
			return new FieldInfos((FieldInfo[])byName.values().toArray(new FieldInfo[byName.size()]));
		}


		Builder()
		{
			this(new FieldNumbers());
		}

		Builder(FieldNumbers globalFieldNumbers)
		{
			byName = new HashMap();
			if (!$assertionsDisabled && globalFieldNumbers == null)
			{
				throw new AssertionError();
			} else
			{
				this.globalFieldNumbers = globalFieldNumbers;
				return;
			}
		}
	}

	static final class FieldNumbers
	{

		private final Map numberToName = new HashMap();
		private final Map nameToNumber = new HashMap();
		private int lowestUnassignedFieldNumber;
		static final boolean $assertionsDisabled = !org/apache/lucene/index/FieldInfos.desiredAssertionStatus();

		synchronized int addOrGet(String fieldName, int preferredFieldNumber)
		{
			Integer fieldNumber = (Integer)nameToNumber.get(fieldName);
			if (fieldNumber == null)
			{
				Integer preferredBoxed = Integer.valueOf(preferredFieldNumber);
				if (preferredFieldNumber != -1 && !numberToName.containsKey(preferredBoxed))
				{
					fieldNumber = preferredBoxed;
				} else
				{
					while (numberToName.containsKey(Integer.valueOf(++lowestUnassignedFieldNumber))) ;
					fieldNumber = Integer.valueOf(lowestUnassignedFieldNumber);
				}
				numberToName.put(fieldNumber, fieldName);
				nameToNumber.put(fieldName, fieldNumber);
			}
			return fieldNumber.intValue();
		}

		synchronized void setIfNotSet(int fieldNumber, String fieldName)
		{
			Integer boxedFieldNumber = Integer.valueOf(fieldNumber);
			if (!numberToName.containsKey(boxedFieldNumber) && !nameToNumber.containsKey(fieldName))
			{
				numberToName.put(boxedFieldNumber, fieldName);
				nameToNumber.put(fieldName, boxedFieldNumber);
			} else
			if (!$assertionsDisabled && !containsConsistent(boxedFieldNumber, fieldName))
				throw new AssertionError();
		}

		synchronized boolean containsConsistent(Integer number, String name)
		{
			return name.equals(numberToName.get(number)) && number.equals(nameToNumber.get(name));
		}


		FieldNumbers()
		{
			lowestUnassignedFieldNumber = -1;
		}
	}


	private final boolean hasFreq;
	private final boolean hasProx;
	private final boolean hasPayloads;
	private final boolean hasOffsets;
	private final boolean hasVectors;
	private final boolean hasNorms;
	private final boolean hasDocValues;
	private final SortedMap byNumber = new TreeMap();
	private final HashMap byName = new HashMap();
	private final Collection values;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FieldInfos.desiredAssertionStatus();

	public FieldInfos(FieldInfo infos[])
	{
		boolean hasVectors = false;
		boolean hasProx = false;
		boolean hasPayloads = false;
		boolean hasOffsets = false;
		boolean hasFreq = false;
		boolean hasNorms = false;
		boolean hasDocValues = false;
		FieldInfo arr$[] = infos;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			FieldInfo info = arr$[i$];
			FieldInfo previous = (FieldInfo)byNumber.put(Integer.valueOf(info.number), info);
			if (previous != null)
				throw new IllegalArgumentException((new StringBuilder()).append("duplicate field numbers: ").append(previous.name).append(" and ").append(info.name).append(" have: ").append(info.number).toString());
			previous = (FieldInfo)byName.put(info.name, info);
			if (previous != null)
				throw new IllegalArgumentException((new StringBuilder()).append("duplicate field names: ").append(previous.number).append(" and ").append(info.number).append(" have: ").append(info.name).toString());
			hasVectors |= info.hasVectors();
			hasProx |= info.isIndexed() && info.getIndexOptions().compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
			hasFreq |= info.isIndexed() && info.getIndexOptions() != FieldInfo.IndexOptions.DOCS_ONLY;
			hasOffsets |= info.isIndexed() && info.getIndexOptions().compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
			hasNorms |= info.hasNorms();
			hasDocValues |= info.hasDocValues();
			hasPayloads |= info.hasPayloads();
		}

		this.hasVectors = hasVectors;
		this.hasProx = hasProx;
		this.hasPayloads = hasPayloads;
		this.hasOffsets = hasOffsets;
		this.hasFreq = hasFreq;
		this.hasNorms = hasNorms;
		this.hasDocValues = hasDocValues;
		values = Collections.unmodifiableCollection(byNumber.values());
	}

	public boolean hasFreq()
	{
		return hasFreq;
	}

	public boolean hasProx()
	{
		return hasProx;
	}

	public boolean hasPayloads()
	{
		return hasPayloads;
	}

	public boolean hasOffsets()
	{
		return hasOffsets;
	}

	public boolean hasVectors()
	{
		return hasVectors;
	}

	public boolean hasNorms()
	{
		return hasNorms;
	}

	public boolean hasDocValues()
	{
		return hasDocValues;
	}

	public int size()
	{
		if (!$assertionsDisabled && byNumber.size() != byName.size())
			throw new AssertionError();
		else
			return byNumber.size();
	}

	public Iterator iterator()
	{
		return values.iterator();
	}

	public FieldInfo fieldInfo(String fieldName)
	{
		return (FieldInfo)byName.get(fieldName);
	}

	public FieldInfo fieldInfo(int fieldNumber)
	{
		return fieldNumber < 0 ? null : (FieldInfo)byNumber.get(Integer.valueOf(fieldNumber));
	}

}
